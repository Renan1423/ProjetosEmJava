import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
	
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("alterebro-pixel-font.ttf");
	public Font newFont;
	public Font newFont2;
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, 120);
		
		try {
			newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(40f);
		} catch (FontFormatException e) {
			
			e.printStackTrace();
		
		} 
		  catch (IOException e) {
			
			e.printStackTrace();
		
		}
		g.setFont(newFont);
		
		//Vida
		
		g.setColor(Color.red);
		g.drawString("--L I F E--", Game.WIDTH - 150, 50);
		
		for(int i = 0; i < (int)(Player.maxLife); i++) {
			g.drawImage(Spritesheet.HeartDamage,Game.WIDTH - 175 + (i*20),80,16,16,null);
		}
		
		for(int i = 0; i < (int)(Player.Life); i++) {
			g.drawImage(Spritesheet.Heart,Game.WIDTH - 175 + (i*20),80,16,16,null);
		}
		
		//Armas
		
		try {
			newFont2 = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(80f);
		} catch (FontFormatException e) {
			
			e.printStackTrace();
		
		} 
		  catch (IOException e) {
			
			e.printStackTrace();
		
		}
		g.setFont(newFont2);
		
		g.setColor(Color.BLUE);
		g.fillRect(Game.WIDTH/2 + 10, 30, 60, 75);
		g.fillRect(Game.WIDTH/2 + 85, 30, 60, 75);
		
		g.setColor(Color.BLACK);
		g.fillRect(Game.WIDTH/2 + 15, 35, 50, 65);
		g.fillRect(Game.WIDTH/2 + 90, 35, 50, 65);
		
		g.setColor(Color.WHITE);
		g.drawString("B", Game.WIDTH/2 + 35, 45);
		
		g.setColor(Color.WHITE);
		g.drawString("A", Game.WIDTH/2 + 110, 45);
		
		g.drawImage(Spritesheet.boomerang[0],Game.WIDTH/2 + 20, 46,48,48,null);
		g.drawImage(Spritesheet.Sword,Game.WIDTH/2 + 105, 46,48,48,null);
		
		//Itens
		
		g.drawString("x0", 240, 108);
		g.drawImage(Spritesheet.bomb, 220, 86,20,26,null);
}
}
