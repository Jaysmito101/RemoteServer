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


public class MouseService{
	private MouseService(){}

	public static void clickRelative(int x, int y, int w, int h){
		//System.out.println(x + " " + y + " " + w + " " + h);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		x = (int)(((double)x / (double)w) * (double) width );
		y = (int)(((double)y / (double)h) * (double) height );
		try{
			Robot bot = new Robot();
			bot.mouseMove(x, y);    
			bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			bot.delay(50);
			bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			System.out.println("Clicked (" + x + ", " + y + ")");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void shiftMouse(int x, int y){
		try{
			Robot bot = new Robot();
			int mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX();
			int mouseY = (int)MouseInfo.getPointerInfo().getLocation().getY();
			bot.mouseMove(mouseX + x, mouseY + y);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}