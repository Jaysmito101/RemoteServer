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


public class FileHandler implements HttpHandler {
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
				String incomingRequest =  new String(dec.decode( java.net.URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name()) ));
				String[] commands = incomingRequest.split("/");
                if(commands.length == 2){
                    if(commands[0].equals("getpathdetails")){
                        String resData = FileService.getDetails(new String(dec.decode( java.net.URLDecoder.decode( commands[1] , StandardCharsets.UTF_8.name()) )));
                        if(resData == null)
                            resData = "";
                        String response = "{\"status\":200, \"msg\":\"Success\" , \"data\":\"" + new String(enc.encode(resData.getBytes())) +  "\"}";
                        he.sendResponseHeaders(200, response.length());
                        he.getResponseBody().write(response.getBytes(Charset.defaultCharset())); 
                    }else{
                        String response = "{\"status\":404, \"msg\":\"Command not recognized\"}";
                        he.sendResponseHeaders(404, response.length());
                        he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));                    
                    }
                }else if(commands.length == 1){
                    if(commands[0].equals("defpath")){
                        String resData = FileService.getDefaultPath();
                        if(resData == null)
                            resData = "";
                        String response = "{\"status\":200, \"msg\":\"Success\" , \"data\":\"" + new String(enc.encode(resData.getBytes())) +  "\"}";
                        he.sendResponseHeaders(200, response.length());
                        he.getResponseBody().write(response.getBytes(Charset.defaultCharset())); 
                    }else{
                        String response = "{\"status\":404, \"msg\":\"Command not recognized\"}";
                        he.sendResponseHeaders(404, response.length());
                        he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));                    
                    }
                }
                else{
                    String response = "{\"status\":404, \"msg\":\"Command not recognized\"}";
                    he.sendResponseHeaders(404, response.length());
                    he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));                    
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

