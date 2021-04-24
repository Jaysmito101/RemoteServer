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


public class ScreenService{
	private ScreenService(){}

	public static String captureSnapshot(){
		try {
			Robot r = new Robot();
			Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage image = r.createScreenCapture(capture);
			int mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX();
       		int mouseY = (int)MouseInfo.getPointerInfo().getLocation().getY();
			Graphics2D g = (Graphics2D) image.getGraphics();
			Color red = new Color(255, 0, 0, 160);
    		g.setColor(red);
    		g.fillOval(mouseX - 10, mouseY - 10, 20, 20);
			return imgToBase64String(image, "gif");
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
