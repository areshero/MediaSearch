package hw3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * 
 * created by chong li
 * 
 * 
 */

class theYUV {
	public theYUV(double y, double u, double v) {
		this.y = y;
		this.u = u;
		this.v = v;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getU() {
		return u;
	}

	public void setU(double u) {
		this.u = u;
	}

	public double getV() {
		return v;
	}

	public void setV(double v) {
		this.v = v;
	}

	private double y = 0;
	private double u = 0;
	private double v = 0;
}

public class Main extends JPanel implements ChangeListener{
	
	
	private int frameNumber;
	
	public ImageIcon getImageIcon(int i ){
		return imageIcons[i];
	}
	
	public ImageIcon[] getImageIcons() {
		return imageIcons;
	}

	private ImageIcon [] imageIcons = null;
	static final int width = 352;
	static final int height = 288;
	
	public Main() {
		imageIcons = new ImageIcon[601];
		frameNumber = 0;
		
    this.setLayout(new BorderLayout());
    JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 600, 0);
    slider.setLayout(new BoxLayout(slider, BoxLayout.PAGE_AXIS));

    slider.setMinorTickSpacing(50);
    slider.setMajorTickSpacing(100);
    
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);

    slider.setLabelTable(slider.createStandardLabels(50));

    slider.addChangeListener(this);
    add(slider, BorderLayout.CENTER);
	}

	public ImageIcon createImageIcons(BufferedImage img1){
		return new ImageIcon(img1);
	}
	
	public static void main(String[] args) throws InterruptedException {

		Main search = new Main();
		File folder = new File(
				"/Users/chongli/Desktop/576/final/CSCI576_Project_Database/flowers");
		File files[] = folder.listFiles();

		JFrame frame = new JFrame("Display images");
		Main slider = new Main();
		frame.getContentPane().add(slider);
		JPanel panel = new JPanel();
		JLabel label = new JLabel();

		for (int i = 1; i <= 600; i++) {

			String fileName = files[i].getAbsolutePath();

			BufferedImage img = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);

			try {
				byte[] bytes = readFile(fileName);

				printOriginalImage(img, bytes);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			search.getImageIcons()[i] = search.createImageIcons(img);
		}
		
		
		for(int i = 1; i <= 600; i++){

			Thread.sleep(1000/60);
			//frame.getContentPane().removeAll();
			label.setIcon(search.getImageIcons()[i]);
			
			ImageIcon icon = search.getImageIcon(i);
			
			panel.add(label,BorderLayout.LINE_END);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			frame.getContentPane().add(panel);
			//frame.repaint();
			//frame.validate();
			frame.setSize(800, 800);
			frame.setVisible(true);

		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	private static void printOriginalImage(BufferedImage img1, byte[] bytes) {
		int ind1 = 0;
		for (int y = 0; y < height; y++) {

			for (int x = 0; x < width; x++) {

				byte a = 0;
				byte r = bytes[ind1];
				byte g = bytes[ind1 + height * width];
				byte b = bytes[ind1 + height * width * 2];

				// int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b &
				// 0xff);
				int pix = ((a << 24) + (r << 16) + (g << 8) + b);
				img1.setRGB(x, y, pix);
				ind1++;
			}
		}
	}

	private static byte[] readFile(String fileName) throws FileNotFoundException,
			IOException {
		File file = new File(fileName);
		InputStream is = new FileInputStream(file);

		long len = file.length();
		byte[] bytes = new byte[(int) len];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		is.close();
		return bytes;
	}

	@Override
	public void stateChanged(ChangeEvent e) {

		JSlider source = (JSlider)e.getSource();
    if (!source.getValueIsAdjusting()) {
        int count = (int)source.getValue();
        if (count == 0) {
            
        } else {
        	System.out.println(count);
        }
    }
		
	}

}