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


public class KeyPressHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange he) throws IOException {
		String requestURI = (he.getRequestURI()).toString().substring(1);
		String authBody = (new String(he.getRequestBody().readAllBytes()));
		if(AuthService.isAuthentic(authBody)){
			String[] parts = requestURI.split("/");
			parts[1] = java.net.URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name());
			if(parts.length!=2){
				String response = "{\"status\":404, \"msg\":\"Command not recognized\"}";
				he.sendResponseHeaders(404, response.length());
				he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));
			}else{
				KeyPressService.pressKey(parts[1]);
				String response = "{\"status\":200, \"msg\":\"Success\"}";
				he.sendResponseHeaders(200, response.length());
				he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));
			}
		}
		else{
			String response = "{\"status\":403, \"msg\":\"Authentication Failed\"}";
			he.sendResponseHeaders(403, response.length());
			he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));
		}
	}
}