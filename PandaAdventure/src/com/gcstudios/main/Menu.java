package com.gcstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.gcstudios.world.Camera;
import com.gcstudios.world.World;

public class Menu {

	
	public String[] options = {"novo jogo","carregar jogo","sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean left,right,enter;
	
	public static boolean loading = false;
	
	public static boolean pause = false;
	
	public static boolean saveExists = false;
	public static boolean saveGame = false;
	
	private BufferedImage[] loadPlayer;
	private BufferedImage[] standrightPlayer;
	private BufferedImage[] standleftPlayer;
	private BufferedImage[] standupPlayer;
	private BufferedImage[] standdownPlayer;
	private int loadframes = 0, loadMaxFrames = 30, standframes = 0, standmaxFrames = 60, standIndex = 0, maxstandIndex = 2;
	private int loadingIndex;
	
	private BufferedImage Staff;
	
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("alterebro-pixel-font.ttf");
	public Font newFont;
	
	public void tick() {
		File file = new File("save.txt");
		if(file.exists()) {
			saveExists = true;
		}else {
			saveExists = false;
		}
		
		standrightPlayer = new BufferedImage[3];
		standleftPlayer = new BufferedImage[3];
		standupPlayer = new BufferedImage[3];
		standdownPlayer = new BufferedImage[3];
		loadPlayer = new BufferedImage[2];
		
		for(int i =0; i < 2; i++){
			loadPlayer[i] = Game.Playerspritesheet.getSprite(50, 25 + (25 * i), 25, 25);
		}
		for(int i = 0; i < 3; i++){
			standrightPlayer[i] = Game.Playerspritesheet.getSprite(200 - (i*25), 0, 25, 25);
		}
		for(int i =0; i < 3; i++){
			standleftPlayer[i] = Game.Playerspritesheet.getSprite(75 + (i*25), 0, 25, 25);
		}
		for(int i =0; i < 3; i++){	
			standupPlayer[i] = Game.Playerspritesheet.getSprite(225, 0, 25, 25);
		}
		for(int i =0; i < 3; i++){
			standdownPlayer[i] = Game.Playerspritesheet.getSprite(0 + (25 * i), 0, 25, 25);
		}
		
		if(pause == false && loading == false) {
			Sound.intro.loop();
		}
		Staff = Game.Playerspritesheet.getSprite(150, 150,25,25);
		standframes++;
		if(standframes == standmaxFrames) {
			standframes = 0;
			standIndex++;
			if(standIndex > maxstandIndex)
				standIndex = 0;
		}
		if(loading) {
			loadframes++;
			if(loadframes == loadMaxFrames) {
				loadframes = 0;
				loadingIndex++;
				if(loadingIndex > 1) {
					loadingIndex = 0;
				}
			}
		}
		
		if(left) {
			left = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		if(right) {
			right = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		if(enter) {
			enter = false;
			if(options[currentOption] == "novo jogo" || options[currentOption] == "continuar") {
				Game.gameState = "NORMAL";
				Game.estado_cena = Game.entrada;
				pause = false;
				file = new File("save.txt");
				file.delete();
			}else if(options[currentOption] == "carregar jogo") {
				file = new File ("save.txt");
				if(file.exists()) {
					String saver = loadGame(10);
					applySave(saver);
				}
			}
			else if(options[currentOption] == "sair") {
				if(pause == false) {
					System.exit(1);
				}
				else {
					pause = false;
					Game.gameState = "MENU";
				}
			}
		}
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for (int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0]) {
				case "level":
					World.restartGame("level" + spl2[1] + ".png");
					Game.gameState = "NORMAL";
					pause = false;
					break;
				case "vida":
					Game.player.life = Integer.parseInt(spl2[1]);
					break;
			}
		}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for(int i = 0; i < val.length; i++) {
							val[i]-=encode;
							trans[1]+=val[i];
						}
						line += trans[0];
						line += ":";
						line += trans[1];
						line += "/";
					}
				}catch(IOException e){
					
				}
			}catch(FileNotFoundException e) {
				
			}
			
		}
		return line;
	}
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current+=":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int n = 0; n < value.length; n++){
				value[n]+=encode;
				current+=value[n];
			}
			try {
				write.write(current);
				if(i < val1.length - 1) {
					write.newLine();
				}
			}catch(IOException e) {
				
			}
		}
		try {
			write.flush();
			write.close();
		}catch(IOException e) {
			
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Image title = null;
		if(pause == false && loading == false) {
			try {
				title = ImageIO.read(getClass().getResource("/Titulo.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(pause == true && loading == false){
			try {
				title = ImageIO.read(getClass().getResource("/PauseScreen.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(pause == false && loading == true) {
			if(loading == true) {
				try {
					title = ImageIO.read(getClass().getResource("/LoadingScreen1.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				g.drawImage(title, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
			}
		}
		g2.drawImage(title, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		
		
		//Opcoes de menu
		if(loading == false) {
			g.setColor(Color.white);
			Font font2 = new Font("Segoe UI", Font.BOLD, 56);
			g.setFont(font2);
			if(pause == false)
				g.drawString("", Toolkit.getDefaultToolkit().getScreenSize().width / 4 - 110, 260);
			else {
				g.drawString("Resumir", Toolkit.getDefaultToolkit().getScreenSize().width / 4 - 210, 360);
				g.drawString("Carregar jogo", Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 170, 360);
				g.drawString("Sair", Toolkit.getDefaultToolkit().getScreenSize().width / 2 + 440, 360);
			}
			Font font3 = new Font("Segoe UI", Font.BOLD, 60);
			if(options[currentOption] == "novo jogo") {
				if(pause == false) {
					g.setFont(font3);
					g.drawString(">", (int)(Toolkit.getDefaultToolkit().getScreenSize().width / 3.25), (int)(Toolkit.getDefaultToolkit().getScreenSize().height / 2.2));
				}
					
				else {
					g.drawString(">", Toolkit.getDefaultToolkit().getScreenSize().width / 13, 360);
				}
			}else if(options[currentOption] == "carregar jogo") {
				if(pause == false) {
					g.setFont(font3);
					g.drawString(">", (int)(Toolkit.getDefaultToolkit().getScreenSize().width / 20), (int)(Toolkit.getDefaultToolkit().getScreenSize().height / 1.59));
				}
				else {
					g.drawString(">", (int)(Toolkit.getDefaultToolkit().getScreenSize().width / 2.8), 360);
				}
			}else if(options[currentOption] == "sair") {
				if(pause == false) {
					g.setFont(font3);
					g.drawString(">", (int)(Toolkit.getDefaultToolkit().getScreenSize().width/ 1.37), (int)(Toolkit.getDefaultToolkit().getScreenSize().height / 1.59));
				}
				else {
					g.drawString(">", (int)(Toolkit.getDefaultToolkit().getScreenSize().width / 1.32), 360);
				}
			}
			
			//Status da personagem
			if(pause == true) {
				g.setColor(Color.black);
				/*try {
					newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(48f);
				} catch (FontFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				//g.setFont(newFont);
				Font font1 = new Font("Segoe UI", Font.BOLD, 36);
				g.setFont(font1);
				g.drawString("" + Game.player.Level, (Game.WIDTH*Game.SCALE) / 2 - 30, 404);
				g.drawString((int)Game.player.mana + "/" + (int)Game.player.maxMana, Toolkit.getDefaultToolkit().getScreenSize().width / 2 + 10, 486);
				if(Game.player.life/ 100 > 0.2) {
					g.drawString((int)Game.player.life + "/" + (int)Game.player.maxlife, Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 146, 486);
				}
				else {
					g.setColor(Color.red);
					g.drawString((int)Game.player.life + "/" + (int)Game.player.maxlife, Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 130, 486);
				}
				if(Game.player.dir == Game.player.right_dir) {
					g.drawImage(standrightPlayer[standIndex], 341, 518,25*5,25*5, null);
				}
				else if(Game.player.dir == Game.player.left_dir) {
					g.drawImage(standleftPlayer[standIndex], 341, 518,25*5,25*5, null);
					
				}
				if(Game.player.dir == Game.player.up_dir) {
					g.drawImage(standupPlayer[standIndex], 341, 518,25*5,25*5, null);
				}
				else if(Game.player.dir == Game.player.down_dir) {
					g.drawImage(standdownPlayer[standIndex], 341, 518,25*5,25*5,null);
				}
				g.setColor(Color.black);
				g.drawString("" + Game.player.str, Toolkit.getDefaultToolkit().getScreenSize().width / 2 + 210, 406);
				
				g.drawString("" + Game.player.def, Toolkit.getDefaultToolkit().getScreenSize().width / 2 + 210, 466);
			}
		}
		
		else {
			g.drawImage(loadPlayer[loadingIndex], Toolkit.getDefaultToolkit().getScreenSize().width/2 + 520, Toolkit.getDefaultToolkit().getScreenSize().height/2 + 169,25*5,25*5, null);
		}
		
		
	}
	
}
