import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class World {

	public static List<WallBlocks> wallblocos = new ArrayList<WallBlocks>();
	
	public static Blocks[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 32;
	
	public World(String path) {
		
		//REMOVE "STRING PATH" TO USE THIS CODE
		/*for(int xx = 0; xx < 23; xx++) {
			wallblocos.add(new WallBlocks(xx * 32, 120,Blocks.TILE_WALLGREEN));
		}
		for(int xx = 0; xx < 23; xx++) {
			wallblocos.add(new WallBlocks(xx * 32, 480 - 32,Blocks.TILE_WALLGREEN));
		}
		
		for(int yy = 0; yy < 15; yy++) {
			wallblocos.add(new WallBlocks(0, yy * 32,Blocks.TILE_WALLGREEN));
		}
		for(int yy = 0; yy < 15; yy++) {
			wallblocos.add(new WallBlocks(720 - 32, yy * 32,Blocks.TILE_WALLGREEN));
		}*/
		
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Blocks[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++){
				for(int yy = 0; yy < map.getHeight(); yy++){
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorBlock(xx*32,yy*32,Blocks.TILE_FLOOR);
					if(pixelAtual == 0xFF000000){
						//Floor
						tiles[xx + (yy * WIDTH)] = new FloorBlock(xx*32,yy*32,Blocks.TILE_FLOOR);
					}else if(pixelAtual == 0xFFFFFFFF){
						//Parede
						tiles[xx + (yy * WIDTH)] = new WallBlocks(xx*32,yy*32,Blocks.TILE_WALLGREEN);
					}else if(pixelAtual == 0xFF0026FF) {
						//Player
						Game.player.setX(xx*32);
						Game.player.setY(yy*32);
					}else if(pixelAtual == 0xFFFF0000) {
						//Enemy
						Game.enemies.add(new Enemy(xx*32,yy*32));
						
					}		
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean isFree(int x, int y) {
		/*for(int i = 0; i < wallblocos.size(); i++) {
			WallBlocks blocoAtual = wallblocos.get(i);
			if(blocoAtual.intersects(new Rectangle(x,y,32,32))) {
				return false;
			}
		}
		return true;*/
		
		int x1 = x / TILE_SIZE;
		int y1 = y / TILE_SIZE;
		
		int x2 = (x + TILE_SIZE-1) / TILE_SIZE;
		int y2 = y / TILE_SIZE;
		
		int x3 = x / TILE_SIZE;
		int y3 = (y + TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (x + TILE_SIZE-1) / TILE_SIZE;
		int y4 = (y + TILE_SIZE-1) / TILE_SIZE;
		
		return !(tiles[x1 +(y1*World.WIDTH)] instanceof WallBlocks ||
				tiles[x2 +(y2*World.WIDTH)] instanceof WallBlocks ||
				tiles[x3 +(y3*World.WIDTH)] instanceof WallBlocks ||
				tiles[x4 +(y4*World.WIDTH)] instanceof WallBlocks);
	}
	
	public void renderMiniMap() {
		for(int i = 0; i < Game.minimapaPixels.length; i++) {
			Game.minimapaPixels[i] = 0x878787;
		}
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				if(tiles[xx + (yy * WIDTH)] instanceof WallBlocks) {
					Game.minimapaPixels[xx + (yy*WIDTH)] = 0xA9A9A9;
				} //Enemy(xx*16,yy*16,Entity.ENEMY_EN)
			}
		}
		for(int i = 0; i < Game.enemies.size(); i++){
		       Enemy en = Game.enemies.get(i);
		       int enX = en.x/32;
		       int enY = en.y/32;
		      
		       Game.minimapaPixels[enX + (enY*WIDTH)] = 0xff0000;
		       //mesma lógica do Player para mudar o pixel do minimapa para a cor desejada.
		}
		
		int xPlayer = Game.player.x/32;
		int yPlayer = Game.player.y/32;
		
		Game.minimapaPixels[xPlayer + (yPlayer*WIDTH)] = 0x0000ff;
 	}
	
	
	public void render(Graphics g) {
		int xstart = Camera.x >> (int)5.5;
		int ystart = Camera.y >> (int)5.5;
		
		int xfinal = xstart + (Game.WIDTH >> (int)5.5);
		int yfinal = ystart + (Game.HEIGHT >> (int)5.5);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Blocks tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
		
	}
	
}
