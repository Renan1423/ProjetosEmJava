import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

	public static int WIDTH = 720, HEIGHT = 480;
	public static int SCALE = 3;
	
	private boolean isRunning = true;
	private Thread thread;
	
	public static Player player;
	public static UI ui;
	
	public World world;
	
	public static List<Enemy> enemies = new ArrayList<Enemy>();
	
	public static int[] minimapaPixels;
	
	public static BufferedImage minimapa;
	
	public Game() {
		Sound.musicBackground.loop();
		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		new Spritesheet();
		player = new Player(WIDTH/2,HEIGHT/2);
		world = new World("/level1.png");
		
		minimapa = new BufferedImage(World.WIDTH, World.HEIGHT, BufferedImage.TYPE_INT_RGB);
		minimapaPixels = ((DataBufferInt)minimapa.getRaster().getDataBuffer()).getData();
		
		ui = new UI();
	}
	
	public void tick() {
		player.tick();
		
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).tick();
		}
	}
	
	/*public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }*/
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
	
		//g.setColor(Color.gray);
		g.setColor(new Color(252,216,168,255));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		
		/*
		g.setColor(Color.red);
		g.fillRect(0,0,100,100);
		*/
		
		
		
		world.render(g);
		
		player.render(g);
		
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(g);
		}
		
		ui.render(g);
		
		world.renderMiniMap();
		g.drawImage(minimapa,50,10,World.WIDTH * 7,World.HEIGHT * 5,null);
		
		bs.show();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		
		frame.add(game);
		frame.setTitle("Mini Zelda");
		frame.setLocationRelativeTo(null);
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		
		new Thread(game).start();
	}
	
	@Override
	public void run() {
		Sound.musicBackground.play();
		/*
		while(true) {
			tick();
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		*/
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		stop();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && player.isAttacking == false) {
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT && player.isAttacking == false) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP && player.isAttacking == false) {
			player.up = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN && player.isAttacking == false) {
			player.down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Z && player.isAttacking == false) {
			player.shoot = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_X && player.isAttacking == false) {
			player.isAttacking = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = false;
		}
		
		
	}
	
}
