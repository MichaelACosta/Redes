/*
*Trabalho de Redes
*Michael Costa
*UFPel 2014/1
*Versao para correcao de erros
*/

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.Object.*;

import javax.net.ssl.*;
import com.sun.net.ssl.*;
import java.security.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


class Crawler{
	private Integer profundidade;
	private Integer porta;
	private URL site;

	public Crawler(Integer prof, URL url){
		this.profundidade=prof;
		this.site=url;
		this.porta=443;
	};

	public void Vai(){
		try{
	
			//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			//System.setProperty("java.protocol.handler.pkgs","com.sun.net.ssl.internal.www.protocol"); 

			//cria um socket para conexao https com ssl

			Socket socket = SSLSocketFactory.getDefault().createSocket(site.getHost(),porta);

			System.out.println("Criou Socket");
			//cria cabeçalho


			Writer escrita = new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1");
         		escrita.write("GET / HTTP/1.1\r\n");  
         		escrita.write("Host: " + site.getHost() + ":" + porta + "\r\n");  
         		escrita.write("Agent: SSL-TEST\r\n");  
         		escrita.write("\r\n");

			//envia pacote
			escrita.flush();

			//recebe pacote
			BufferedReader leitura=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			StringBuilder linha=new StringBuilder();
			String line;
			while((line=leitura.readLine()) != null){
				linha.append(line);
//				System.out.println(line);
			}
			line=linha.toString();

			escrita.close();
			leitura.close();
			socket.close();

			Document page=Jsoup.parse(line);
//			System.out.println(page);
			Elements hosts;

			hosts=page.select("a[href]");

			//percorre com iterator todo emeents hosts procurando URL

			Iterator<Element> it=hosts.iterator();
			while(it.hasNext()){
				String temp=(it.next()).attr("href");
				if(temp.startsWith("http")){
					System.out.println("URL: "+temp);
				}
			}
		}
		catch (IOException e){
			System.out.println("Erro URL: "+site);
		}
	}
	
}

public class Redes{

	public static void main(String[] args){
		try{
			Integer prof=new Integer(Integer.parseInt(args[0]));
			URL end=new URL(args[1]);
			Crawler obj=new Crawler(prof, end);
			obj.Vai();
		}

		catch(MalformedURLException ex){
			System.out.println("Erro.");
		}
	}

}
