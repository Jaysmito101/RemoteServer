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


public class CommandService{
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