import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Blocks extends Rectangle{
	
	public static BufferedImage TILE_WALLGREEN = Spritesheet.tileWallGreen;
	public static BufferedImage TILE_WALLRED = Spritesheet.tileWall;
	public static BufferedImage TILE_WALLTREE = Spritesheet.tileWallTree;
	public static BufferedImage TILE_FLOOR = Spritesheet.tileFloor;
	
	private BufferedImage sprite;
	private int x,y;
	
	public Blocks(int x, int y, BufferedImage sprite) {
		//super(x,y,32,32);
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		//g.setColor(Color.magenta);
		//g.fillRect(x, y, width, height);
		//g.setColor(Color.black);
		//g.drawRect(x, y, width, height);
		
		g.drawImage(sprite, x - Camera.x, y - Camera.y, 32, 32, null);
	}
	
}
