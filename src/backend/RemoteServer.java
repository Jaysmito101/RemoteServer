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


public class RemoteServer{
	public static final int PORT = 8080;
	public static void main(String[] args) throws Exception{
		Server server = new Server(PORT);
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
}

class Server implements Runnable{
	int port;

	public Server(int port){
		this.port = port;
	}

	@Override
	public void run(){
		try{
			HttpServer server = HttpServer.create(new InetSocketAddress(this.port), 0);
			server.createContext("/", new RootHandler());
			server.createContext("/keypress", new KeyPressHandler());
			server.createContext("/screenshot", new ScreenShotHandler());
			server.createContext("/command", new CommandHandler());
			server.setExecutor(null);
			server.start();
			System.out.println("RemoteServer backend started at PORT : " + port);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}

class RootHandler implements HttpHandler {
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

class KeyPressHandler implements HttpHandler {
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


class CommandHandler implements HttpHandler {
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
				CommandService.doCommand(parts[1]);
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

class ScreenShotHandler implements HttpHandler {
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


class KeyPressService{
	private KeyPressService(){}

	public static void pressKey(String keyCode){
		System.out.println("KeyPress: " + keyCode);
		try{
			Robot robot = new Robot();
			if(keyCode.length() == 1){
				char c = keyCode.charAt(0);
				if(Character.isUpperCase(c))
					robot.keyPress(KeyEvent.VK_SHIFT);
				KeyStroke ks = KeyStroke.getKeyStroke(Character.toUpperCase(c), 0);
				robot.keyPress(ks.getKeyCode());
				robot.keyRelease(ks.getKeyCode());
				if(Character.isUpperCase(c))
					robot.keyRelease(KeyEvent.VK_SHIFT);
			}else{
				switch(keyCode.toUpperCase()){
					case "ENTER":{
						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);
						break;
					}
					case "BACKSPACE":{
						robot.keyPress(KeyEvent.VK_BACK_SPACE);
						robot.keyRelease(KeyEvent.VK_BACK_SPACE);
						break;
					}
					case "CONTEXTMENU":{
						robot.keyPress(KeyEvent.VK_CONTEXT_MENU);
						robot.keyRelease(KeyEvent.VK_CONTEXT_MENU);
						break;
					}
					case "DOWN":{
						robot.keyPress(KeyEvent.VK_DOWN);
						robot.keyRelease(KeyEvent.VK_DOWN);
						break;
					}
					case "UP":{
						robot.keyPress(KeyEvent.VK_UP);
						robot.keyRelease(KeyEvent.VK_UP);
						break;
					}
					case "LEFT":{
						robot.keyPress(KeyEvent.VK_LEFT);
						robot.keyRelease(KeyEvent.VK_LEFT);
						break;
					}
					case "RIGHT":{
						robot.keyPress(KeyEvent.VK_RIGHT);
						robot.keyRelease(KeyEvent.VK_RIGHT);
						break;
					}
					case "ESCAPE":{
						robot.keyPress(KeyEvent.VK_ESCAPE);
						robot.keyRelease(KeyEvent.VK_ESCAPE);
						break;
					}
					case "REFRESH":{
						robot.keyPress(KeyEvent.VK_F5);
						robot.keyRelease(KeyEvent.VK_F5);
						break;
					}
					case "HOME":{
						robot.keyPress(KeyEvent.VK_HOME);
						robot.keyRelease(KeyEvent.VK_HOME);
						break;
					}
					case "END":{
						robot.keyPress(KeyEvent.VK_END);
						robot.keyRelease(KeyEvent.VK_END);
						break;
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}

class CommandService{
	private CommandService(){}

	public static void doCommand(String keyCode){
		System.out.println("Command: " + keyCode);
		try{
			Robot robot = new Robot();
			switch(keyCode.toUpperCase()){
				case "SHUTDOWN":{
					String shutdownCommand ="";
					String osName = System.getProperty("os.name");        
					if (osName.startsWith("Win")) {
						shutdownCommand = "shutdown.exe -s -t 0";
					} else if (osName.startsWith("Linux") || osName.startsWith("Mac")) {
						shutdownCommand = "shutdown -h now";
					} else {
						System.err.println("Shutdown unsupported operating system ...");
					}
					System.out.println("Running command : " + shutdownCommand);
					Runtime.getRuntime().exec(shutdownCommand);
					break;
				}
				case "RESTART":{
					String shutdownCommand ="";
					String osName = System.getProperty("os.name");        
					if (osName.startsWith("Win")) {
						shutdownCommand = "shutdown.exe -r -t 0";
					} else if (osName.startsWith("Linux") || osName.startsWith("Mac")) {
						shutdownCommand = "shutdown -r now";
					} else {
						System.err.println("Restart unsupported operating system ...");
					}
					System.out.println("Running command : " + shutdownCommand);
					Runtime.getRuntime().exec(shutdownCommand);
					break;
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}



class ScreenService{
	private ScreenService(){}

	public static String captureSnapshot(){
		try {
			Robot r = new Robot();
			Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage image = r.createScreenCapture(capture);
			return imgToBase64String(image, "png");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private static String imgToBase64String(final RenderedImage img, final String formatName) {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, formatName, Base64.getEncoder().wrap(os));
			return os.toString(StandardCharsets.ISO_8859_1.name());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}

class AuthService{
	private AuthService(){}

	public static boolean isAuthentic(String authBody){
		return true;
	}
}