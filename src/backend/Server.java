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


public class Server implements Runnable{
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
			server.createContext("/mouseclick", new MouseClickHandler());
			server.createContext("/mousemove", new MouseMoveHandler());
			server.createContext("/shell", new ShellHandler());
			server.createContext("/fsview", new FileHandler());
			server.setExecutor(null);
			server.start();
			System.out.println("RemoteServer backend started at PORT : " + port);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
