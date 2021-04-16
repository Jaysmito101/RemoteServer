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