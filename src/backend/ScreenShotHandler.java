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


public class ScreenShotHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange he) throws IOException {
		String requestURI = (he.getRequestURI()).toString().substring(1);
		String authBody = (new String(he.getRequestBody().readAllBytes()));
		if(AuthService.isAuthentic(authBody)){
			String[] parts = requestURI.split("/");
			if(parts.length!=1){
				String response = "{\"status\":404, \"msg\":\"Command not recognized\"}";
				he.sendResponseHeaders(404, response.length());
				he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));
			}else{
				String base64 = ScreenService.captureSnapshot();
				if(base64!=null){
					String response = "{\"status\":200, \"msg\":\"Success\", \"base64\":\"" + base64 + "\"}";
					he.sendResponseHeaders(200, response.length());
					he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));
				}else{
					String response = "{\"status\":500, \"msg\":\"Failed to capture screenshot\"}";
					he.sendResponseHeaders(500, response.length());
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
