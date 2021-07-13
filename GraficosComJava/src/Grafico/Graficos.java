
package Grafico;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Graficos extends Canvas implements Runnable {

    public static JFrame frame;
    private Thread thread;
    private boolean isRunning = true;
    private final int WIDTH = 160;
    private final int HEIGHT = 120;
    private final int SCALE = 4;
    private BufferedImage image;
    
    private Spritesheet sheet;
    private BufferedImage[] player;
    private int frames = 0;
    private int maxFrames = 20;
    private int curAnimation = 0,maxAnimation = 3;
    
    public Graficos(){
    	sheet = new Spritesheet("/spritesheet.png");
    	player = new BufferedImage[3];
    	player[0] = sheet.getSprite(0, 0, 16, 16);
    	player[1] = sheet.getSprite(16, 0, 16, 16);
    	player[2] = sheet.getSprite(32, 0, 16, 16);
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
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
        } catch (InterruptedException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        Graficos graficoscomjava = new Graficos();
        graficoscomjava.start();
    }
    
    public void initFrame(){
        frame = new JFrame("Uma Tela!!!");
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void Tick(){
    		/*x++;*/
    		frames++;
    		if(frames > maxFrames){
    			frames = 0;
    			curAnimation++;
    			if(curAnimation >= maxAnimation) {
    				curAnimation = 0;
    			}
    		}
    }
    
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(100,0,100));
        g.fillRect(0,0,WIDTH,HEIGHT);
        
        /*Renderização do jogo*/
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(player[curAnimation],20,20,null); 
        
        	/*      Rotacionar		
		        	g2.rotate(Math.toRadians(90),20+8,20+8);*/
        
        
			/*      Diminuir a iluminação  
			 	 	g2.rotate(Math.toRadians(-90),20+8,20+8);
			        g2.setColor(new Color(0,0,0,120));
			        g2.fillRect(0, 0, WIDTH, HEIGHT);*/
        
        
        
        
        
        
        /*g.drawImage(player,x,20,null);*/
        /**/
        g.dispose();
        g.setColor(Color.CYAN);
        g.fillRect(20,20,80,80);
        
        g.setColor(Color.GREEN);
        g.fillOval(0,0,50,50);
        
        g.setFont(new Font("Arial",Font.BOLD,14));
        g.setColor(Color.white);
        g.drawString("Olá Mundo",20,20);
        
        g = bs.getDrawGraphics();        
        g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
        bs.show();
    }
    public void run() {
       long lastTime = System.nanoTime();
       double amountOfTicks = 60.0;
       double ns = 1000000000 / amountOfTicks;
       double delta = 0;
       int frames = 0;
       double timer = System.currentTimeMillis();
       while(isRunning){
           long now = System.nanoTime();
           delta += (now - lastTime) / ns;
           lastTime = now;
           if(delta >= 1){
               Tick();
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
    
}

