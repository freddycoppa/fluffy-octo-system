package ca;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Display extends Canvas implements KeyListener, Runnable {
	private static final long serialVersionUID = 1L;

	private int width;
	private int height;
	int zoom;

	private Automaton automaton;
	private BufferedImage img;

	private boolean k;

	public Display(int width, int height, int zoom) {
		this.width = width / zoom;
		this.height = height / zoom;
		this.zoom = zoom;
		this.img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		this.automaton = new Automaton(this.width, this.height, ((DataBufferInt) img.getRaster().getDataBuffer()).getData());
		this.k = false;
	}

	private Thread thread;
	private boolean running;

	public void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	/*
	 * private void stop() { if (!running) return; running = false; try {
	 * thread.join(); } catch (InterruptedException e) { e.printStackTrace();
	 * System.exit(1); } }
	 */

	public void run() {
		long elapsedTime = 0;
		int frames = 0;
		
		while (running) {
			long time = System.nanoTime();
			this.render();
			frames++;
			elapsedTime += (System.nanoTime() - time);
			if (elapsedTime >= 1000000000) {
				System.out.println(frames + " fps");
				frames = 0;
				elapsedTime = 0;
			}
		}
	}
	
	/*public void run() {
		while(running) {
			render();
		}
	}*/

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		if (k)
			automaton.nextIteration4();
		Graphics gfx = bs.getDrawGraphics();
		gfx.drawImage(img, 0, 0, zoom * width, zoom * height, null);
		gfx.dispose();
		bs.show();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 'k')
			this.k = !this.k;
		else if (e.getKeyChar() == 'r')
			this.automaton = new Automaton(this.width, this.height, ((DataBufferInt) img.getRaster().getDataBuffer()).getData());
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
