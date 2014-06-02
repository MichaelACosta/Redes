/**
 * Michael Costa
 * UFPel-2014/1
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.Object.*;

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
        porta=80;	//garante sempre porta 80;
    };
    
    public void run(){        
	try{
		if(profundidade>0){
		    //cria conexão
		    Socket socket=new Socket(site.getHost(),porta);
		    socket.setSoTimeout(4000);	//fecha conexao
		    //cabeçalho
		    BufferedWriter escrita = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
		    escrita.write("GET"+site.getPath()+" HTTP/1.1\n");
		    escrita.write("Host: "+site.getHost()+"\n\n");
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
