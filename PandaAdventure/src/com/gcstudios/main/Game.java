package com.gcstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.gcstudios.entities.BulletShoot;
import com.gcstudios.entities.BulletShootEn;
import com.gcstudios.entities.ChargeShoot;
import com.gcstudios.entities.ChargeShootEn;
import com.gcstudios.entities.Enemy;
import com.gcstudios.entities.Enemy2;
import com.gcstudios.entities.Entity;
import com.gcstudios.entities.Npc;
import com.gcstudios.entities.Player;
import com.gcstudios.entities.StaffStrike;
import com.gcstudios.graficos.Spritesheet;
import com.gcstudios.graficos.UI;
import com.gcstudios.world.World;

public class Game extends Canvas implements Runnable,KeyListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 4;
	
	public static int CUR_LEVEL = 0,MAX_LEVEL = 2;
	private BufferedImage image;
	private String[] fraseEntrada = new String [12];
	
	private boolean GameZ = false;
	private int TextoEntrada = 0;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<Enemy2> enemies2;
	public static List<BulletShoot> bullets;
	public static List<BulletShootEn> bulletsen;
	public static List<ChargeShoot> chargebullets;
	public static List<ChargeShootEn> chargebulletsen;
	public static List<StaffStrike> staffStrike;
	public static List<Npc> NPc;
	
	public static Spritesheet Playerspritesheet;
	public static Spritesheet Enemyspritesheet;
	public static Spritesheet Itensspritesheet;
	public static Spritesheet TilesSpritesheet;
	public static Spritesheet CoalaSpritesheet;
	
	public static World world;
	
	public static Player player;
	
	public static Npc npc;
	
	public static Random rand;
	
	public UI ui;
	
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("");
	public Font newfont;
	
	
	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	
	public static int entrada = 1;
	public static int comecar = 2;
	public static int jogando = 3;
	public static int nomeando = 4;
	public static int tutorial = 5;
	public static int estado_cena = jogando;
	
	public Menu menu;
	
	public NameMenu nameMenu;
	
	public int pixels[];
	public BufferedImage lightmap;
	public int[] lightMapPixels;
	public static int[] minimapaPixels;
	
	public static BufferedImage minimapa;
	
	public int mx, my;
	
	public boolean saveGame = false;
	
	public int xx, yy;
	
	public boolean showMessageEnt = false;
	
	public int curIndex = 0;
	
	public int time = 0;
	public int maxTime = 5;
	
	public int curTutorial = 1;
	
	public Game(){
		rand = new Random();
		addKeyListener(this);
		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));//fullscreen
		//setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		//Inicializando objetos.
		ui = new UI();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		try {
			lightmap = ImageIO.read(getClass().getResource("/lightmap.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		lightMapPixels = new int[lightmap.getWidth() * lightmap.getHeight()];
		lightmap.getRGB(0, 0, lightmap.getWidth(), lightmap.getHeight(), lightMapPixels, 0, lightmap.getWidth());
		pixels =((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		enemies2 = new ArrayList<Enemy2>();
		bullets = new ArrayList<BulletShoot>();
		bulletsen = new ArrayList<BulletShootEn>();
		chargebullets = new ArrayList<ChargeShoot>();
		chargebulletsen = new ArrayList<ChargeShootEn>();
		staffStrike = new ArrayList<StaffStrike>();
		NPc = new ArrayList<Npc>();
		
		Playerspritesheet = new Spritesheet("/PandaSpritesheet.png");
		Enemyspritesheet = new Spritesheet("/EnemySpritesheet.png");
		Itensspritesheet = new Spritesheet("/ItensSpritesheet.png");
		TilesSpritesheet = new Spritesheet("/TilesSpritesheet.png");
		CoalaSpritesheet = new Spritesheet("/CoalaSpritesheet.png");
		player = new Player(0,0,25,25,Playerspritesheet.getSprite(0, 0,25,25));
		entities.add(player);
		world = new World("/level1.png");
		minimapa = new BufferedImage(World.WIDTH, World.HEIGHT, BufferedImage.TYPE_INT_RGB);
		minimapaPixels = ((DataBufferInt)minimapa.getRaster().getDataBuffer()).getData();
		
		menu = new Menu();
		nameMenu = new NameMenu();
	}
	
	public void initFrame(){
		frame = new JFrame("Panda Adventure");
		frame.add(this);
		frame.setUndecorated(true); //fullscreen
		frame.setResizable(false);
		frame.pack();
		Image icone = null;
		try {
			icone = ImageIO.read(getClass().getResource("/Icone2.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursor = toolkit.getImage(getClass().getResource("/Cursor.png"));
		Cursor c = toolkit.createCustomCursor(cursor, new Point(0,0), "img");
		frame.setCursor(c);
		frame.setIconImage(icone);
		frame.setAlwaysOnTop(true);
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
		if(gameState == "NORMAL") {
			xx++;
			if(this.saveGame == true) {
				this.saveGame = false;
				String[] opt1 = {"level"};
				int[] opt2 = {this.CUR_LEVEL, (int) player.life};
				Menu.saveGame(opt1, opt2, 10);
				System.out.println("Jogo salvo com sucesso!");
			}
		this.restartGame = false;
		if(Game.estado_cena == Game.jogando) {
			Sound.home.loop();
			Sound.name.stop();
			Sound.intro.stop();
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).tick();
			}
			for(int i = 0; i < bulletsen.size(); i++) {
				bulletsen.get(i).tick();
			}
			for(int i = 0; i < chargebullets.size(); i++) {
				chargebullets.get(i).tick();
			}
			for(int i = 0; i < staffStrike.size(); i++) {
				staffStrike.get(i).tick();
			}
		}else {
			if(Game.estado_cena == Game.entrada) {
				Sound.intro.stop();
				showMessageEnt = true;
				if(showMessageEnt) {
					time++;
					if(time >= maxTime) {
						time = 0;
						if(curIndex < fraseEntrada[TextoEntrada].length() - 1) {
							curIndex++;
						}else {  //Continuar o diálogo
							if(TextoEntrada < fraseEntrada.length - 1) {
									TextoEntrada++;
									curIndex = 0;
							}
							else {
								estado_cena = nomeando;
							}
						}
					}
				}
			}else if(estado_cena == nomeando) {
				Sound.entrada.stop();
				Sound.name.loop();
				gameState = "NAME";
			}
			else if(estado_cena == tutorial){
				if(curTutorial > 8) {
					estado_cena = comecar;
				}
			}
			else if(Game.estado_cena == Game.comecar) {
				Game.estado_cena = jogando;
			}
		}
		
		/*if(enemies.size() == 0) {
			//Avançar para o próximo level!
			CUR_LEVEL++;
			if(CUR_LEVEL > MAX_LEVEL){
				CUR_LEVEL = 1;
			}
			String newWorld = "level"+CUR_LEVEL+".png";
			//System.out.println(newWorld);
			World.restartGame(newWorld);
		}*/
		}else if(gameState == "GAME_OVER") {
			this.framesGameOver++;
			if(this.framesGameOver == 30) {
				this.framesGameOver = 0;
				if(this.showMessageGameOver)
					this.showMessageGameOver = false;
					else
						this.showMessageGameOver = true;
			}
			
			if(restartGame) {
				this.restartGame = false;
				this.gameState = "NORMAL";
				CUR_LEVEL = 0;
				String newWorld = "level"+CUR_LEVEL+".png";
				//System.out.println(newWorld);
				World.restartGame(newWorld);
			}
		}else if(gameState == "MENU") {
			player.updateCamera();
			menu.tick();
		}
		else if(gameState == "NAME") {
			player.updateCamera();
			nameMenu.tick();
		}
		if(GameZ == true) {
			GameZ = false;
			if(estado_cena == entrada) {
				maxTime = 1;
			}
			else if(estado_cena == tutorial) {
				curTutorial++;
				if(curTutorial > 8) {
					estado_cena = comecar;
				}
			}
		}
	}
	

	

	/*public void drawRectangleExample(int xoff, int yoff) {
		for(int xx = 0; xx < 50; xx++) {
			for(int yy = 0; yy < 50; yy++) {
				int xOFF = xx + xoff;
				int yOFF = yy + yoff;
				if(xOFF < 0 || yOFF < 0 || xOFF >= WIDTH || yOFF >= HEIGHT) {
					continue;
				}
				pixels[xOFF + (yOFF * WIDTH)] = 0xFF0000;
			}
		}
	}*/
	
	public void applyLight() {
		for(int xx = 0; xx < Toolkit.getDefaultToolkit().getScreenSize().width; xx++) {
			for(int yy = 0; yy < Toolkit.getDefaultToolkit().getScreenSize().height; yy++) {
				if(lightMapPixels[xx+(yy * Toolkit.getDefaultToolkit().getScreenSize().width)] == 0xffffffff) {
					int pixel = Pixel.getLightBlend(pixels[xx*yy*WIDTH], 0x080808, 0);
					pixels[xx + (yy*Game.WIDTH)] = 0;
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
		//g.setColor(new Color(0,0,0));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		
		
		/*Renderização do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		
		world.render(g);
		Collections.sort(entities,Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		for(int i = 0; i < bulletsen.size(); i++) {
			bulletsen.get(i).render(g);
		}
		for(int i = 0; i < chargebullets.size(); i++) {
			chargebullets.get(i).render(g);
		}
		for(int i = 0; i < chargebulletsen.size(); i++) {
			chargebulletsen.get(i).render(g);
		}
		for(int i = 0; i < staffStrike.size(); i++) {
			staffStrike.get(i).render(g);
		}
		//applyLight();
		if(!Npc.showMessage && estado_cena != entrada) {
			ui.render(g);
		}
		g.dispose();
		
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height, null); //fullscreen
		g.setFont(new Font("arial",Font.BOLD,20));
		if(!Npc.showMessage && estado_cena != entrada) {
			World.renderMiniMap();
			g.drawImage(minimapa, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.2),60,150,150,null);
		}
		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
			g.setFont(new Font("arial",Font.BOLD,36));
			g.setColor(Color.white);
			g.drawString("Game Over",(Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 90,(Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - 20);
			g.setFont(new Font("arial",Font.BOLD,32));
			if(showMessageGameOver)
				g.drawString(">Pressione Enter para reiniciar<",(Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 230,(Toolkit.getDefaultToolkit().getScreenSize().height) / 2 + 40);
		}else if(gameState == "MENU") {
			menu.render(g);
		}
		else if(gameState == "NAME") {
			nameMenu.render(g);
		}
		if(estado_cena == entrada) {
			Sound.entrada.loop();
			fraseEntrada[0] = "O Rei Panda Vermelho, o rei mais temido de todos...    ";
			fraseEntrada[1] = "ameaça dominar a Rainbow Island...    ";
			fraseEntrada[2] = "Para isso, ele usará seu temeroso exército...    ";
			fraseEntrada[3] = "E suas devastadoras magias das trevas...                                        ";
			fraseEntrada[4] = "Todos os moradores de Rainbow Island ficam assustados...                ";
			fraseEntrada[5] = "Entretanto...                             ";
			fraseEntrada[6] = "Nenhum deles perdeu a esperança, pois...                ";
			fraseEntrada[7] = "De acordo com a lenda, em momentos de escuridão...        ";
			fraseEntrada[8] = "Alguém encarregado de retornar a luz surgirá...                  ";
			fraseEntrada[9] = "E esse alguém enfrentará bravamente o mal...             ";
			fraseEntrada[10] = "Mas, antes de tudo...       ";
			fraseEntrada[11] = "Nossa heroína precisa de um nome                ";
			//g.setColor(Color.black);
			//g.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
			Image imagemEntrada = null;
			if(TextoEntrada < 8) {
				try {
					imagemEntrada = ImageIO.read(getClass().getResource("/EvilKing.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				try {
					imagemEntrada = ImageIO.read(getClass().getResource("/GoodHero.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			g.drawImage(imagemEntrada,0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height,null);
			Font font1 = new Font("Segoe UI", Font.BOLD, 46);
			g.setFont(font1);
			if(showMessageEnt) {
				g.setColor(Color.white);
				g.fillRect((int)(Toolkit.getDefaultToolkit().getScreenSize().width/14) - 1, (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.6) - 1,(int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.16) + 2,(int)(Toolkit.getDefaultToolkit().getScreenSize().height/3.5) + 2);
				g.setColor(Color.blue);
				g.fillRect((int)(Toolkit.getDefaultToolkit().getScreenSize().width/14),(int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.6),(int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.16),(int)(Toolkit.getDefaultToolkit().getScreenSize().height/3.5));
				g.setColor(Color.white);
				if(TextoEntrada < 10) {
					g.drawString(fraseEntrada[TextoEntrada].substring(0,curIndex), (int)(Toolkit.getDefaultToolkit().getScreenSize().width/12), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.4));
				}
				else if(TextoEntrada == 10){
					g.drawString(fraseEntrada[TextoEntrada].substring(0,curIndex), (int)(Toolkit.getDefaultToolkit().getScreenSize().width/3), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.30));
				}else if(TextoEntrada == 11) {
					g.drawString(fraseEntrada[TextoEntrada].substring(0,curIndex), (int)(Toolkit.getDefaultToolkit().getScreenSize().width/4), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.30));
				}
			}
		}
		else if(estado_cena == tutorial) {
			g.setColor(Color.black);
			g.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
			Image tutorial = null;
			try {
				 tutorial = ImageIO.read(getClass().getResource("/Tutorial"+curTutorial+".png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
			g.drawImage(tutorial, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		}
		bs.show();
	}
	public void run() {
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

	@Override
	public void keyPressed(KeyEvent e) {
		if(estado_cena != entrada && estado_cena != tutorial && CUR_LEVEL != 0) {
			if(e.getKeyCode() == KeyEvent.VK_Z) {
				if(!Npc.showMessage && Npc.show) {
					Npc.showMessage = true;
				}
				
				if(player.isCharging) {
					player.isCharging = false;
				}
				if(player.stamina == player.maxstamina && !player.isCharging && !player.movedR && !player.movedL && !player.movedU && !player.movedD && !Npc.show && !Npc.showMessage) {
					player.Attack = true;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_X) {
				//Carregar Magia
				if(!player.movedR && !player.movedL && !player.movedU && !player.movedD && !player.isDead && !Npc.showMessage) {
					player.isCharging = true;
				}
				
				if(gameState == "NAME") {
					nameMenu.x = true;
				}
				
				if(Npc.showMessage) {
					Npc.showMessage = false;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_RIGHT ){
				player.right = true;
				if(gameState == "MENU" && menu.loading == false) {
					menu.right = true;
				}
				if(gameState == "NAME") {
					nameMenu.right = true;
				}
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
				player.left = true;
				if(gameState == "MENU" && menu.loading == false) {
					menu.left = true;
				}
				if(gameState == "NAME") {
					nameMenu.left = true;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP){
				player.up = true;
				if(gameState == "NAME") {
					nameMenu.up = true;
				}
				
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				player.down = true;
				if(gameState == "NAME") {
					nameMenu.down = true;
				}
				
				
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				if(gameState == "NAME") {
					nameMenu.enter = true;
				}
				if(gameState == "GAME_OVER")
				this.restartGame = true;
				if(gameState == "MENU" && menu.loading == false) {
					menu.enter = true;
				}
				if(gameState == "NORMAL" && menu.loading == false) {
					gameState = "MENU";
					menu.pause = true;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				if(gameState == "NORMAL") {
					this.saveGame = true;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_A) {
				player.changeDirection = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_W) {
				if(!player.isCharging && bullets.size() == 0) {
					player.magiaAtual++;
					if(player.magiaAtual > 2) {
						player.magiaAtual = 0;
					}
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_Q) {
				if(!player.isCharging && bullets.size() == 0) {
					player.magiaAtual--;
					if(player.magiaAtual < 0) {
						player.magiaAtual = 2;
					}
					}
				}
		}else {
			if(e.getKeyCode() == KeyEvent.VK_Z) {
				if(estado_cena == entrada || estado_cena == tutorial) {
					GameZ = true;
				}
				if(gameState == "NAME") {
					nameMenu.z = true;
				}
				if(!Npc.showMessage && Npc.show) {
					Npc.showMessage = true;
				}
				
				if(estado_cena != entrada && estado_cena != tutorial){
					if(player.isCharging) {
						player.isCharging = false;
					}
					if(player.stamina == player.maxstamina && !player.isCharging && !player.movedR && !player.movedL && !player.movedU && !player.movedD && !Npc.show && !Npc.showMessage) {
						player.Attack = true;
					}
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_X) {
				player.speed = 3.5;
				if(estado_cena != entrada && estado_cena != tutorial){
					if(!player.movedR && !player.movedL && !player.movedU && !player.movedD && !player.isDead && !Npc.showMessage) {
						player.isCharging = true;
					}
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				player.right = true;
				if(gameState == "MENU" && menu.loading == false) {
					menu.right = true;
				}
				if(gameState == "NAME") {
					nameMenu.right = true;
				}
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
				player.left = true;
				if(gameState == "MENU" && menu.loading == false) {
					menu.left = true;
				}
				if(gameState == "NAME") {
					nameMenu.left = true;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP){
				player.up = true;
				if(gameState == "NAME") {
					nameMenu.up = true;
				}
				
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				player.down = true;
				if(gameState == "NAME") {
					nameMenu.down = true;
				}
				
				
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				if(gameState == "NAME") {
					nameMenu.enter = true;
				}
				if(gameState == "GAME_OVER")
				this.restartGame = true;
				if(gameState == "MENU" && menu.loading == false) {
					menu.enter = true;
				}
				if(gameState == "NORMAL" && menu.loading == false) {
					gameState = "MENU";
					menu.pause = true;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				if(gameState == "NORMAL") {
					this.saveGame = true;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_A) {
				player.changeDirection = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_W) {
				if(!player.isCharging && bullets.size() == 0) {
					player.magiaAtual++;
					if(player.magiaAtual > 2) {
						player.magiaAtual = 0;
					}
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_Q) {
				if(!player.isCharging && bullets.size() == 0) {
					player.magiaAtual--;
					if(player.magiaAtual < 0) {
						player.magiaAtual = 2;
					}
					}
				}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(estado_cena != entrada) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				player.right = false;
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
				player.left = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP){
				player.up = false;
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN ) {
				player.down = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_X) {
				//Atirar a Magia
				if(player.isCharging == true) {
					player.isCharging = false;
					if(ChargeShoot.Charged == true) {
						player.shoot = true;
					}
					ChargeShoot.Charging = 0;
					ChargeShoot.Charged = false;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_A) {
				player.changeDirection = false;
			}
		}else {
			if(e.getKeyCode() == KeyEvent.VK_Z) {
				if(estado_cena == entrada) {
					GameZ = false;
					maxTime = 5;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_X) {
				player.speed = 0.1;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	
}
