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
