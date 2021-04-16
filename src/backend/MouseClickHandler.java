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


public class MouseClickHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange he) throws IOException {
		String requestURI = (he.getRequestURI()).toString().substring(1);
		String authBody = (new String(he.getRequestBody().readAllBytes()));
		if(AuthService.isAuthentic(authBody)){
			String[] parts = requestURI.split("/");
			if(parts.length!=5){
				String response = "{\"status\":404, \"msg\":\"Command not recognized\"}";
				he.sendResponseHeaders(404, response.length());
				he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));
			}else{
				try{
					int x = (int)Double.parseDouble(java.net.URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name()));
					int y = (int)Double.parseDouble(java.net.URLDecoder.decode(parts[2], StandardCharsets.UTF_8.name()));
					int w = (int)Double.parseDouble(java.net.URLDecoder.decode(parts[3], StandardCharsets.UTF_8.name()));
					int h = (int)Double.parseDouble(java.net.URLDecoder.decode(parts[4], StandardCharsets.UTF_8.name()));
					MouseService.clickRelative(x, y, w, h);
				}catch (Exception ex) {
					ex.printStackTrace();
					String response = "{\"status\":500, \"msg\":\"Failed to click\"}";
					he.sendResponseHeaders(500, response.length());
					he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));
					return;
				}
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
