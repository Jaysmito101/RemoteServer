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



public class RootHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange he) throws IOException {
		String requestURI = (he.getRequestURI()).toString().substring(1);
		String authBody = (new String(he.getRequestBody().readAllBytes()));
		if(AuthService.isAuthentic(authBody)){
			he.sendResponseHeaders(200, 1);
			he.getResponseBody().write(33);
		}
		else{
			String response = "{\"status\":403, \"msg\":\"Authentication Failed\"}";
			he.sendResponseHeaders(200, response.length());
			he.getResponseBody().write(response.getBytes(Charset.defaultCharset()));
		}
	}
}