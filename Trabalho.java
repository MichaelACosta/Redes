/**
 * Michael Costa
 * UFPel-2014/1
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

class Crawler extends Thread{

    private Integer profundidade;
    private Integer porta;
    private URL site;
    private Thread thread;

    public Crawler(Integer prof, URL url){
        profundidade=prof;
        site=url;
        porta=443;	//porta 443 para https;
    };
    
    public void run(){        
	try{
		if(profundidade>0){
		    //cria conexão socket http
		    //Socket socket=new Socket(site.getHost(),porta);

		    //cria conexão socket https
		    Socket socket = SSLSocketFactory.getDefault().createSocket(site.getHost(),porta);

		    //cabeçalho
		    /*BufferedWriter escrita = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
		    escrita.write("GET / HTTP/1.1\n");
		    escrita.write("Host: "+site.getHost()+"\n\n");*/

		    //cabeçalho para https
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
		    while((line=leitura.readLine()) != null) {
		        linha.append(line);
		    }
		    line=linha.toString();
		    
		    escrita.close();
		    leitura.close();
		    socket.close();			

		    Document page=Jsoup.parse(line);
		    Elements hosts;

		    hosts=page.select("a[href]");

		    //percorre com iterator todo elements hosts procurando URL
		    Iterator<Element> it=hosts.iterator();
		    while(it.hasNext()){
		        String temp=(it.next()).attr("href");
		        if(temp.startsWith("http")){
		            if(!(Trabalho.visitado.contains(temp))){
				Trabalho.visitado.add(temp);
				Crawler novo=new Crawler((profundidade-1),new URL(temp));
		        	novo.start();
			    }
		        }
		    }
		}
		else{
		    return;
		}
	}
	catch (IOException e) {
	    System.out.println("Erro URL "+site);
        }
    }
    
    public void start(){
	System.out.println("URL: "+site+", Profundidade= "+profundidade);
	if(thread==null){
		thread=new Thread(this,site.getHost());
		thread.start();
	}
    }    
}


public class Trabalho {

    public static Set<String> visitado = Collections.synchronizedSet(new HashSet<String>());

    public static void main(String[] args) throws UnknownHostException, IOException {
        
	try {
            Integer prof=new Integer(Integer.parseInt(args[0]));
            URL end=new URL(args[1]);
            Crawler obj=new Crawler(prof, end);
            obj.start();
        } 
        catch (MalformedURLException ex) {
            System.out.println("Erro.");
        }
        
    }
}
