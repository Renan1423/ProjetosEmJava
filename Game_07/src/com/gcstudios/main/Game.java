package com.gcstudios.main;

import java.awt.Canvas;

//Created by Renan Nunes
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


import com.gcstudios.entities.Entity;
import com.gcstudios.entities.Player;
import com.gcstudios.graficos.Spritesheet;
import com.gcstudios.graficos.UI;
import com.gcstudios.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	
	private BufferedImage image;
	
	public static World world;
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static Player player;
	
	public static Inventario inventario;
	
	public static EnemySpawn enemySpawn;

	public static UI ui;
	
	public int[] pixels;
	public BufferedImage lightmap;
	public int[] lightMapPixels;
	
	
	public Game(){
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		try {
			lightmap = ImageIO.read(getClass().getResource("/Lightmap.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		lightMapPixels = new int[lightmap.getWidth() * lightmap.getHeight()];
		lightmap.getRGB(0, 0, lightmap.getWidth(), lightmap.getHeight(), lightMapPixels, 0, lightmap.getWidth());
		
		pixels =((DataBufferInt)image.getRaster().getDataBuffer()).getData();

		//Inicializando objetos.
		spritesheet = new Spritesheet("/spritesheet.png");
		entities = new ArrayList<Entity>();
		player = new Player(16,16,16,16,1,Entity.PLAYER_SPRITE_RIGHT[0]);
		world = new World();
		ui = new UI();
		inventario = new Inventario();
		enemySpawn = new EnemySpawn();
		
		entities.add(player);
		
	}
	
	public void initFrame(){
		frame = new JFrame("Minecraft 2D");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursor = toolkit.getImage(getClass().getResource("/icon.png"));
		Cursor c = toolkit.createCustomCursor(cursor, new Point(0,0), "img");
		frame.setCursor(c);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public void tick(){

		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		
		inventario.tick();
		ui.tick();
		enemySpawn.tick();
		
	}
	

	public void applyLight() {
		if(world.CICLO == world.noite) {
			for(int xx = 0; xx < Game.WIDTH; xx++) {
				for(int yy = 0; yy < Game.HEIGHT; yy++) {
					if(lightMapPixels[xx+(yy * Game.WIDTH)] == 0xffffffff) {
						int pixel = Pixel.getLightBlend(pixels[xx+yy*WIDTH],0x191919, 0);
						pixels[xx+(yy*Game.WIDTH)] = pixel;
					}else if(lightMapPixels[xx+(yy * Game.WIDTH)] == 0xff000000) {
						int pixel = Pixel.getLightBlend(pixels[xx+yy*WIDTH],0x808080, 0);
						pixels[xx+(yy*Game.WIDTH)] = pixel;
					}
					else {
						continue;
					}
				}
			}
		}
	}

	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(122,102,255));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		
		/*Renderização do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		Collections.sort(entities,Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		applyLight();
		
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		ui.render(g);
		inventario.render(g);
		bs.show();
	}
	
	
	public void run() {
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

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_1) {
			inventario.selected = 0;
		}else if(e.getKeyCode() == KeyEvent.VK_2) {
			inventario.selected = 1;
		}else if(e.getKeyCode() == KeyEvent.VK_3) {
			inventario.selected = 2;
		}else if(e.getKeyCode() == KeyEvent.VK_4) {
			inventario.selected = 3;
		}else if(e.getKeyCode() == KeyEvent.VK_5) {
			inventario.selected = 4;
		}else if(e.getKeyCode() == KeyEvent.VK_6) {
			inventario.selected = 5;
		}else if(e.getKeyCode() == KeyEvent.VK_7) {
			inventario.selected = 6;
		}else if(e.getKeyCode() == KeyEvent.VK_8) {
			inventario.selected = 7;
		}
		
		
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			player.attack = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(ui.CraftOpen == true) {
				ui.CraftOpen = false;
				for(int i = 0; i < ui.Itens.length; i++) {
					ui.Itens[i] = 0;
					ui.craftArray[i] = "";
				}
				
				
			}else {
				ui.CraftOpen = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			inventario.isPressed = true;
			
		}else if(e.getButton() == MouseEvent.BUTTON3) {
			
			inventario.isPlaceItem = true;
		}
		
		inventario.mx = e.getX();
		inventario.my = e.getY();
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	
	}

	
}
