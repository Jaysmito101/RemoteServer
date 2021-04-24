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


public class MouseMoveHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange he) throws IOException {
		String requestURI = (he.getRequestURI()).toString().substring(1);
		String authBody = (new String(he.getRequestBody().readAllBytes()));
		if(AuthService.isAuthentic(authBody)){
			String[] parts = requestURI.split("/");
			System.out.println(parts[1] + " " + parts[2]);
			if(parts.length!=3){
				String response = "{\"status\":404, \"msg\":\"Command not recognized\"}";
				he.sendResponseHeaders(404, response.length());
				he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));
			}else{
				try{
					int x = (parts[1].equals("left"))?-5:5;
					int y = (parts[2].equals("up"))?-5:5;
					if(!parts[1].equals("left") && !parts[1].equals("right"))
						x= 0;
					if(!parts[2].equals("up") && !parts[2].equals("down"))
						y= 0;
					MouseService.shiftMouse(x, y);
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
