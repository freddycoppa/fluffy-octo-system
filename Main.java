package ca;

import javax.swing.JFrame;

public class Main {
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 675;
	public static final String TITLE = "Guess";
	
	public static void main(String[] args) {
		Display display = new Display(WIDTH, HEIGHT, 1);
		JFrame frame = new JFrame();
		frame.add(display);
		frame.addKeyListener(display);
		frame.pack();
		frame.setSize(WIDTH, HEIGHT+23);
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		display.start();
	}
}
