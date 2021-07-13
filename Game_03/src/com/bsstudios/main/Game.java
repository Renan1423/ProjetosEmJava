package com.bsstudios.main;

import java.awt.Canvas;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.bsstudios.entities.Enemy;
import com.bsstudios.entities.Entity;
import com.bsstudios.entities.Holder;
import com.bsstudios.entities.Player;
import com.bsstudios.graficos.Spritesheet;
import com.bsstudios.graficos.UI;
import com.bsstudios.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 432;
	public static final int HEIGHT = 240;
	public static final int SCALE = 3;
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static World world;
	public static Player player;
	public static Enemy enemy;
	public static UI ui;
	
	public static int itens_contagem = 0;
	public static int itens_atual = 0;
	
	//booleans para a movimentação do player:
	
	public static boolean Press1R,Press1L,Press1U,Press1D;
	
	private int index = 0, maxIndex = 2, frames = 0, maxFrames = 60;
	
	public Game(){
		//Sound.musicBackground.loop();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		
		//Inicializando objetos
		spritesheet = new Spritesheet("/spritesheet.png");
		entities = new ArrayList<Entity>();
		player = new Player(0,0,16,16,1.8,spritesheet.getSprite(32, 0,16,16));
		world = new World("/level1.png");
		ui = new UI();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		entities.add(player);
	}
	
	public void initFrame(){
		frame = new JFrame("Pac-Slime");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		Image icone = null;
		try {
			icone = ImageIO.read(getClass().getResource("/Icone2.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		frame.setIconImage(icone);
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
		if(player.dead == true) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
					player.dead = false;
					world.restartGame();
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
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		
		/*Renderização do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		if(player.dead == false) {
			world.render(g);
			Collections.sort(entities,Entity.nodeSorter);
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.render(g);
			}
		}
		
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		ui.render(g);
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
		if(!player.moving) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
					e.getKeyCode() == KeyEvent.VK_D){
				if(!Holder.isCollidingHolder) {
					player.moving = true;
					player.right = true;
					player.left = false;
					player.up = false;
					player.down = false;
				}else {
					if(!Press1R) {
						player.lastDir = 1;
						Press1R = true;
						Press1L = false;
						Press1U = false;
						Press1D = false;
					}
					else if(World.isFree((int)(player.getX()+10),player.getY())){
						player.setX(player.getX() + 10);
						Holder.isCollidingHolder = false;
						player.right = true;
						player.left = false;
						player.up = false;
						player.down = false;
						player.moving = true;
						Press1R = false;
					}
				}
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
					e.getKeyCode() == KeyEvent.VK_A){
				if(!Holder.isCollidingHolder) {
					player.moving = true;
					player.left = true;
					player.right = false;
					player.up = false;
					player.down = false;
				}else {
					if(!Press1L) {
						player.lastDir = -1;
						Press1R = false;
						Press1L = true;
						Press1U = false;
						Press1D = false;
					}
					else if(World.isFree((int)(player.getX()-10),player.getY())){
						player.setX(player.getX() - 10);
						Holder.isCollidingHolder = false;
						player.left = true;
						player.up = false;
						player.down = false;
						player.right = false;
						player.moving = true;
						Press1L = false;
					}
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP ||
					e.getKeyCode() == KeyEvent.VK_W){
				if(!Holder.isCollidingHolder) {
					player.moving = true;
					player.up = true;
					player.left = false;
					player.right = false;
					player.down = false;
				}else {
					if(!Press1U) {
						player.lastDir = 2;
						Press1R = false;
						Press1L = false;
						Press1U = true;
						Press1D = false;
					}
					else if(World.isFree(player.getX(),(int)(player.getY()-16))){
						player.setY(player.getY() - 16);
						Holder.isCollidingHolder = false;
						player.moving = true;
						player.up = true;
						player.left = false;
						player.down = false;
						player.right = false;
						Press1U = false;
					}
				}
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
					e.getKeyCode() == KeyEvent.VK_S) {
				if(!Holder.isCollidingHolder) {
					player.moving = true;
					player.down = true;
					player.left = false;
					player.up = false;
					player.right = false;
				}else {
					if(!Press1D) {
						player.lastDir = -2;
						Press1R = false;
						Press1L = false;
						Press1U = false;
						Press1D = true;
					}
					else if(World.isFree(player.getX(),(int)(player.getY()+1))){
						player.setY(player.getY() + 1);
						Holder.isCollidingHolder = false;
						player.moving = true;
						player.up = false;
						player.left = false;
						player.down = true;
						player.right = false;
						Press1D = false;
					}
				}
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_X){
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D){
			//player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A){
			//player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W){
			//player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			//player.down = false;
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
