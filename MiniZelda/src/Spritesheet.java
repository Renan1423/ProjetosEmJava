import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	public static BufferedImage spritesheet;
	
	public static BufferedImage[] player_front;
	public static BufferedImage[] player_back;
	public static BufferedImage[] player_right;
	public static BufferedImage[] player_left;
	
	public static BufferedImage[] player_front_attack;
	public static BufferedImage[] player_back_attack;
	public static BufferedImage[] player_right_attack;

	public static BufferedImage[] enemySprite;
	public static BufferedImage[] enemySpriteDamage;
	public static BufferedImage[] enemySpriteDead;
	
	public static BufferedImage magic;
	
	public static BufferedImage[] boomerang;
	
	public static BufferedImage bomb;
	
	public static BufferedImage tileWall;
	public static BufferedImage tileWallGreen;
	public static BufferedImage tileWallTree;
	public static BufferedImage tileFloor;
	
	public static BufferedImage Heart;
	public static BufferedImage HeartDamage;
	public static BufferedImage Sword;
	
	public Spritesheet() {
		try {
			spritesheet = ImageIO.read(getClass().getResource("/spritesheet.png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		player_front = new BufferedImage[2];
		
		player_front[0] = Spritesheet.getSprite(0, 11, 16, 16);
		player_front[1] = Spritesheet.getSprite(16, 11, 16, 16);
		
		player_back = new BufferedImage[2];
		
		player_back[0] = Spritesheet.getSprite(67, 11, 16, 16);
		player_back[1] = Spritesheet.getSprite(84, 11, 16, 16);
		
		player_right = new BufferedImage[2];
		
		player_right[0] = Spritesheet.getSprite(34, 11, 16, 16);
		player_right[1] = Spritesheet.getSprite(50, 11, 16, 16);
		
		player_left = new BufferedImage[2];
		
		player_left[0] = Spritesheet.getSprite(34, 11, 16, 16);
		player_left[1] = Spritesheet.getSprite(50, 11, 16, 16);
		
		player_front_attack = new BufferedImage[4];
		
		player_front_attack[0] = Spritesheet.getSprite(94, 47, 16, 27);
		player_front_attack[1] = Spritesheet.getSprite(111, 47, 16, 27);
		player_front_attack[2] = Spritesheet.getSprite(128, 47, 16, 27);
		player_front_attack[3] = Spritesheet.getSprite(146, 47, 16, 27);
		
		player_back_attack = new BufferedImage[4];
		
		player_back_attack[0] = Spritesheet.getSprite(94, 96, 16, 27);
		player_back_attack[1] = Spritesheet.getSprite(111, 96, 16, 27);
		player_back_attack[2] = Spritesheet.getSprite(128, 96, 16, 27);
		player_back_attack[3] = Spritesheet.getSprite(146, 96, 16, 27);
		
		player_right_attack = new BufferedImage[4];
		
		player_right_attack[0] = Spritesheet.getSprite(94, 77, 27, 16);
		player_right_attack[1] = Spritesheet.getSprite(128, 77, 27, 16);
		player_right_attack[2] = Spritesheet.getSprite(177, 77, 27, 16);
		player_right_attack[3] = Spritesheet.getSprite(225, 77, 27, 16);
		
		enemySprite = new BufferedImage[2];
		
		enemySprite[0] = Spritesheet.getSprite(339, 294, 16, 16);
		enemySprite[1] = Spritesheet.getSprite(355, 294, 16, 16);
		
		enemySpriteDamage = new BufferedImage[2];
		
		enemySpriteDamage[0] = Spritesheet.getSprite(321, 294, 16, 16);
		enemySpriteDamage[1] = Spritesheet.getSprite(339, 294, 16, 16);
		
		enemySpriteDead = new BufferedImage[4];
		
		enemySpriteDead[0] = Spritesheet.getSprite(138, 185, 16, 16);
		enemySpriteDead[1] = Spritesheet.getSprite(155, 185, 16, 16);
		enemySpriteDead[2] = Spritesheet.getSprite(172, 185, 16, 16);
		enemySpriteDead[3] = Spritesheet.getSprite(151, 214, 16, 16);
		
		magic = Spritesheet.getSprite(190, 185, 16, 16);
		
		boomerang = new BufferedImage[3];
		
		boomerang[0] = Spritesheet.getSprite(64, 185, 8, 16);
		boomerang[1] = Spritesheet.getSprite(73, 185, 8, 16);
		boomerang[2] = Spritesheet.getSprite(82, 185, 8, 16);
		
		bomb = Spritesheet.getSprite(130, 185, 8, 16);
		
		tileWall = Spritesheet.getSprite(280, 221, 16, 16);
		tileWallGreen = Spritesheet.getSprite(312, 221, 16, 16);
		tileWallTree = Spritesheet.getSprite(280, 253, 16, 16);
		tileFloor = Spritesheet.getSprite(312, 253, 16, 16);
		
		//UI
		
		Heart = Spritesheet.getSprite(281, 299, 9, 11);
		HeartDamage = Spritesheet.getSprite(263, 299, 9, 11);
		Sword = Spritesheet.getSprite(36, 154,16, 16);
		
	}
	
	public static BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
}
