package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import entities.Enemy;
import entities.Entity;
import entities.Mana;
import entities.ManaShoot;
import entities.Player;
import graficos.Spritesheet;
import graficos.UI;
import world.Camera;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

    private static final long serialVersionUID = 1L;
	public static JFrame frame;
    private Thread thread;
    private boolean isRunning = true;
    public static final int WIDTH = 240;
    public static final int HEIGHT = 160;
    public static final int SCALE = 3;
    private BufferedImage image;
    
    private int CUR_LEVEL = 1, MAX_LEVEL = 2;
    
    public static List<Entity> entities;
    public static List<Enemy> enemies;
    public static List<ManaShoot> mana;
    public static Spritesheet spritesheet;
    
    public static World world;
    
    public static Player player;
    
    public static Random rand;
    
    public UI ui;
    
    public static String gameState = "MENU";
    
    private boolean showMessageGameOver = true;
    
    private int framesGameOver = 0;
    
    private boolean restartGame = false;
    
    public Menu menu;
    
    public Game(){
    	Sound.musicBackground.loop();
    	rand = new Random();
    	addKeyListener(this);
    	addMouseListener(this);
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        //Inicializando objetos
        ui = new UI();
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        entities = new ArrayList<Entity>();
        enemies = new ArrayList<Enemy>();
        mana = new ArrayList<ManaShoot>();
        spritesheet = new Spritesheet("/spritesheet.png");
        player = new Player(0,0,16,16,spritesheet.getSprite(32, 0, 16, 16));
        entities.add(player);
        world = new World("/level1.png");
        menu = new Menu();
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
    
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
    
    public void initFrame(){
        frame = new JFrame("");
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void tick(){
    	if(gameState == "NORMAL"){
    		this.restartGame = false;
	    	for (int i = 0; i < entities.size(); i++) {
	    		Entity e = entities.get(i);
	    		e.tick();
	    	}
	    	
	    	for(int i = 0; i < mana.size(); i++) {
	    		mana.get(i).tick();
	    	}
	    	
	    	if(enemies.size() == 0) {
	    		CUR_LEVEL++;
	    		if(CUR_LEVEL > MAX_LEVEL) {
	    			CUR_LEVEL = 1;
	    		}
	    		String newWorld = "level"+CUR_LEVEL+".png";
	    		World.restartGame(newWorld);
	    	}
	    }
    	else if(gameState == "GAME_OVER") {
    		this.framesGameOver++;
    		if(this.framesGameOver == 15) {
    			this.framesGameOver = 0;
    			if(this.showMessageGameOver) {
    				this.showMessageGameOver = false;
    			}else {
    				this.showMessageGameOver = true;
    			}
    		}
    	}
    	else if(gameState == "MENU") {
    		menu.tick();
    	}
    	
    	if(restartGame) {
    		this.restartGame = false;
    		this.gameState = "MENU";
    		CUR_LEVEL = 1;
    		String newWorld = "level"+CUR_LEVEL+".png";
    		World.restartGame(newWorld);
    	}
    	
    }
    
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,WIDTH,HEIGHT);
        
        /*Renderiza??o do jogo*/
        //Graphics2D g2 = (Graphics2D) g;
        world.render(g);
        for (int i = 0; i < entities.size(); i++) {
    		Entity e = entities.get(i);
    		e.render(g);
    	}
        
        for(int i = 0; i < mana.size(); i++) {
    		mana.get(i).render(g);
    	}
        
        ui.render(g);
      
        g.dispose();
        
        g = bs.getDrawGraphics();        
        g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
        g.setFont(new Font("arial", Font.BOLD,20));
        g.setColor(Color.white);
        g.drawString("Muni??o:" + player.ammo, 600,20);
        if(gameState == "GAME_OVER") {
        	Graphics2D g2 = (Graphics2D) g;
        	g2.setColor(new Color(0,0,0,100));
        	g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
        	g.setFont(new Font("arial", Font.BOLD,28));
            g.setColor(Color.white);
            g.drawString("GAME OVER!", (WIDTH*SCALE)/2 - 40,(HEIGHT*SCALE)/2 - 20);
            if(showMessageGameOver) {
	            g.setFont(new Font("arial", Font.BOLD,28));
	            g.drawString(">Pressione ENTER para reiniciar<", (WIDTH*SCALE)/2 - 250,(HEIGHT*SCALE)/2 + 40);
            }
        }
        else if(gameState == "MENU") {
        	menu.render(g);
        }
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
           delta += (now - lastTime) / ns;
           lastTime = now;
           if(delta >= 1){
               tick();
               render();
               frames++;
               delta--;
           }
           if(System.currentTimeMillis() - timer >= 1000){
               System.out.println("FPS: " + frames);
               frames = 0;
               timer += 1000;
           }
       }
        stop();
    }
    private void createdBufferStrategy(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			player.jump = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
			player.right = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
			player.left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){ 
			player.up = true;
			if(gameState == "MENU") {
				menu.up = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
			player.down = true;
			if(gameState == "MENU") {
				menu.down = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_X) {
			player.shoot = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(gameState == "GAME_OVER") {
				this.restartGame = true;
			}
			if(gameState == "MENU") {
				menu.enter = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(gameState != "MENU") {
				gameState = "MENU";
				menu.pause = true;
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
			player.right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
			player.up = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
			player.down = false;
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		player.mouseShoot = true;
		player.mx = (e.getX() / SCALE);
		player.my = (e.getY() / SCALE);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}
