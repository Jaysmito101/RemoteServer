import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import java.awt.image.*;
import java.nio.charset.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

import com.sun.net.httpserver.*;


public class ScreenService{
	private ScreenService(){}
	
	public static String captureSnapshot(float quality, boolean showCursor){
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
			return toBase64(processImage(image, quality));
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static ByteArrayOutputStream processImage(RenderedImage image, float quality){
		try {
			if(quality < 0 || quality >1)
				quality = 1;
			ByteArrayOutputStream os =new ByteArrayOutputStream();
			Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("jpg");
			ImageWriter writer = (ImageWriter) writers.next();
			ImageOutputStream ios = ImageIO.createImageOutputStream(os);
			writer.setOutput(ios);
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality);
			writer.write(null, new IIOImage(image, null, null), param);
			ios.close();
			writer.dispose();
			return os;
		} catch (IOException e) {
			e.printStackTrace();
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
	
	private static String toBase64(ByteArrayOutputStream data){
		try {
			byte[] bytes = Base64.getEncoder().encode(data.toByteArray());
			return new String(bytes);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
