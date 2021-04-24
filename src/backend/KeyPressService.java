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


public class KeyPressService{
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
					case "LCLICK":{
						robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
						break;
					}
					case "RCLICK":{
						robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
    					robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
						break;
					}
					case "MCLICK":{
						robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
    					robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
						break;
					}
					case "WHEEL UP":{
						robot.mouseWheel(-1);
						break;
					}
					case "WHEEL DOWN":{
						robot.mouseWheel(1);
						break;
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}