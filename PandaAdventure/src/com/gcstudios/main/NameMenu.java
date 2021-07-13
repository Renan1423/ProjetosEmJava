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

public class NameMenu {

	
	public String[] options = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean left,right,up,down,z,x,enter;
	
	public static boolean loading = false;
	
	public static boolean pause = false;
	
	private int nameFrames = 0;
	private int nameMaxFrames = 60;
	private boolean image1 = true;
	private boolean image2 = false;
	
	public String playerName;
	public String pos1 = "";
	public String pos2 = "";
	public String pos3 = "";
	public String pos4 = "";
	public String pos5 = "";
	
	public int selectorPosX, selectorPosY;
	
	public static boolean saveExists = false;
	public static boolean saveGame = false;
	
	private BufferedImage[] loadPlayer;
	private BufferedImage[] standdownPlayer;
	private int loadframes = 0, loadMaxFrames = 30, standframes = 0, standmaxFrames = 60, standIndex = 0, maxstandIndex = 2;
	private int loadingIndex;
	
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("alterebro-pixel-font.ttf");
	public Font newFont;
	
	public void tick() {
		File file = new File("save.txt");
		if(file.exists()) {
			saveExists = true;
		}else {
			saveExists = false;
		}
		standdownPlayer = new BufferedImage[3];
		
		for(int i =0; i < 3; i++){
			standdownPlayer[i] = Game.Playerspritesheet.getSprite(0 + (25 * i), 0, 25, 25);
		}
			Sound.name.loop();
			Sound.intro.stop();
		standframes++;
		if(standframes == standmaxFrames) {
			standframes = 0;
			standIndex++;
			if(standIndex > maxstandIndex)
				standIndex = 0;
		}
		nameFrames++;
		if(nameFrames == nameMaxFrames) {
			nameFrames = 0;
			if(image1) {
				image1 = false;
				image2 = true;
			}
			else if(image2) {
				image1 = true;
				image2 = false;
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
		if(down) {
			down = false;
			currentOption+=10;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		if(up) {
			up = false;
			currentOption-=10;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		
		if(enter) {
			enter = false;
			playerName = pos1 + pos2 + pos3 + pos4 + pos5;
			if(playerName != "" && pos1 != "") {
				Game.gameState = "NORMAL";
				Game.estado_cena = Game.tutorial;
			}
		}
		
		if(x) {
			x = false;
			if(pos5 == "") {
				if(pos4 == "") {
					if(pos3 == "") {
						if(pos2 == "") {
							if(pos1 == "") {
								
							}else {
								pos1 = "";
							}
						}else {
							pos2 = "";
						}
					}else {
						pos3 = "";
					}
				}else {
					pos4 = "";
				}
			}else {
				pos5 = "";
			}
		}
		
		if(z) {
			z = false;
			if(options[currentOption] == "A") {
				if(pos1 == "") {
					pos1 = "A";
				}
				else if(pos2 == "") {
					pos2 = "A";
				}
				else if(pos3 == "") {
					pos3 = "A";
				}
				else if(pos4 == "") {
					pos4 = "A";
				}
				else{
					pos5 = "A";
				}
			}
			if(options[currentOption] == "B") {
				if(pos1 == "") {
					pos1 = "B";
				}
				else if(pos2 == "") {
					pos2 = "B";
				}
				else if(pos3 == "") {
					pos3 = "B";
				}
				else if(pos4 == "") {
					pos4 = "B";
				}
				else{
					pos5 = "B";
				}
			}
			if(options[currentOption] == "C") {
				if(pos1 == "") {
					pos1 = "C";
				}
				else if(pos2 == "") {
					pos2 = "C";
				}
				else if(pos3 == "") {
					pos3 = "C";
				}
				else if(pos4 == "") {
					pos4 = "C";
				}
				else{
					pos5 = "C";
				}
			}
			if(options[currentOption] == "D") {
				if(pos1 == "") {
					pos1 = "D";
				}
				else if(pos2 == "") {
					pos2 = "D";
				}
				else if(pos3 == "") {
					pos3 = "D";
				}
				else if(pos4 == "") {
					pos4 = "D";
				}
				else{
					pos5 = "D";
				}
			}
			if(options[currentOption] == "E") {
				if(pos1 == "") {
					pos1 = "E";
				}
				else if(pos2 == "") {
					pos2 = "E";
				}
				else if(pos3 == "") {
					pos3 = "E";
				}
				else if(pos4 == "") {
					pos4 = "E";
				}
				else{
					pos5 = "E";
				}
			}
			if(options[currentOption] == "F") {
				if(pos1 == "") {
					pos1 = "F";
				}
				else if(pos2 == "") {
					pos2 = "F";
				}
				else if(pos3 == "") {
					pos3 = "F";
				}
				else if(pos4 == "") {
					pos4 = "F";
				}
				else{
					pos5 = "F";
				}
			}
			if(options[currentOption] == "G") {
				if(pos1 == "") {
					pos1 = "G";
				}
				else if(pos2 == "") {
					pos2 = "G";
				}
				else if(pos3 == "") {
					pos3 = "G";
				}
				else if(pos4 == "") {
					pos4 = "G";
				}
				else{
					pos5 = "G";
				}
			}
			if(options[currentOption] == "H") {
				if(pos1 == "") {
					pos1 = "H";
				}
				else if(pos2 == "") {
					pos2 = "H";
				}
				else if(pos3 == "") {
					pos3 = "H";
				}
				else if(pos4 == "") {
					pos4 = "H";
				}
				else{
					pos5 = "H";
				}
			}
			if(options[currentOption] == "I") {
				if(pos1 == "") {
					pos1 = "I";
				}
				else if(pos2 == "") {
					pos2 = "I";
				}
				else if(pos3 == "") {
					pos3 = "I";
				}
				else if(pos4 == "") {
					pos4 = "I";
				}
				else{
					pos5 = "I";
				}
			}
			if(options[currentOption] == "J") {
				if(pos1 == "") {
					pos1 = "J";
				}
				else if(pos2 == "") {
					pos2 = "J";
				}
				else if(pos3 == "") {
					pos3 = "J";
				}
				else if(pos4 == "") {
					pos4 = "J";
				}
				else{
					pos5 = "J";
				}
			}
			if(options[currentOption] == "K") {
				if(pos1 == "") {
					pos1 = "K";
				}
				else if(pos2 == "") {
					pos2 = "K";
				}
				else if(pos3 == "") {
					pos3 = "K";
				}
				else if(pos4 == "") {
					pos4 = "K";
				}
				else{
					pos5 = "K";
				}
			}
			if(options[currentOption] == "L") {
				if(pos1 == "") {
					pos1 = "L";
				}
				else if(pos2 == "") {
					pos2 = "L";
				}
				else if(pos3 == "") {
					pos3 = "L";
				}
				else if(pos4 == "") {
					pos4 = "L";
				}
				else{
					pos5 = "L";
				}
			}
			if(options[currentOption] == "M") {
				if(pos1 == "") {
					pos1 = "M";
				}
				else if(pos2 == "") {
					pos2 = "M";
				}
				else if(pos3 == "") {
					pos3 = "M";
				}
				else if(pos4 == "") {
					pos4 = "M";
				}
				else{
					pos5 = "M";
				}
			}
			if(options[currentOption] == "N") {
				if(pos1 == "") {
					pos1 = "N";
				}
				else if(pos2 == "") {
					pos2 = "N";
				}
				else if(pos3 == "") {
					pos3 = "N";
				}
				else if(pos4 == "") {
					pos4 = "N";
				}
				else{
					pos5 = "N";
				}
			}
			if(options[currentOption] == "O") {
				if(pos1 == "") {
					pos1 = "O";
				}
				else if(pos2 == "") {
					pos2 = "O";
				}
				else if(pos3 == "") {
					pos3 = "O";
				}
				else if(pos4 == "") {
					pos4 = "O";
				}
				else{
					pos5 = "O";
				}
			}
			if(options[currentOption] == "P") {
				if(pos1 == "") {
					pos1 = "P";
				}
				else if(pos2 == "") {
					pos2 = "P";
				}
				else if(pos3 == "") {
					pos3 = "P";
				}
				else if(pos4 == "") {
					pos4 = "P";
				}
				else{
					pos5 = "P";
				}
			}
			if(options[currentOption] == "Q") {
				if(pos1 == "") {
					pos1 = "Q";
				}
				else if(pos2 == "") {
					pos2 = "Q";
				}
				else if(pos3 == "") {
					pos3 = "Q";
				}
				else if(pos4 == "") {
					pos4 = "Q";
				}
				else{
					pos5 = "Q";
				}
			}
			if(options[currentOption] == "R") {
				if(pos1 == "") {
					pos1 = "R";
				}
				else if(pos2 == "") {
					pos2 = "R";
				}
				else if(pos3 == "") {
					pos3 = "R";
				}
				else if(pos4 == "") {
					pos4 = "R";
				}
				else{
					pos5 = "R";
				}
			}
			if(options[currentOption] == "S") {
				if(pos1 == "") {
					pos1 = "S";
				}
				else if(pos2 == "") {
					pos2 = "S";
				}
				else if(pos3 == "") {
					pos3 = "S";
				}
				else if(pos4 == "") {
					pos4 = "S";
				}
				else{
					pos5 = "S";
				}
			}
			if(options[currentOption] == "T") {
				if(pos1 == "") {
					pos1 = "T";
				}
				else if(pos2 == "") {
					pos2 = "T";
				}
				else if(pos3 == "") {
					pos3 = "T";
				}
				else if(pos4 == "") {
					pos4 = "T";
				}
				else{
					pos5 = "T";
				}
			}
			if(options[currentOption] == "U") {
				if(pos1 == "") {
					pos1 = "U";
				}
				else if(pos2 == "") {
					pos2 = "U";
				}
				else if(pos3 == "") {
					pos3 = "U";
				}
				else if(pos4 == "") {
					pos4 = "U";
				}
				else{
					pos5 = "U";
				}
			}
			if(options[currentOption] == "V") {
				if(pos1 == "") {
					pos1 = "V";
				}
				else if(pos2 == "") {
					pos2 = "V";
				}
				else if(pos3 == "") {
					pos3 = "V";
				}
				else if(pos4 == "") {
					pos4 = "V";
				}
				else{
					pos5 = "V";
				}
			}
			if(options[currentOption] == "W") {
				if(pos1 == "") {
					pos1 = "W";
				}
				else if(pos2 == "") {
					pos2 = "W";
				}
				else if(pos3 == "") {
					pos3 = "W";
				}
				else if(pos4 == "") {
					pos4 = "W";
				}
				else{
					pos5 = "W";
				}
			}
			if(options[currentOption] == "X") {
				if(pos1 == "") {
					pos1 = "X";
				}
				else if(pos2 == "") {
					pos2 = "X";
				}
				else if(pos3 == "") {
					pos3 = "X";
				}
				else if(pos4 == "") {
					pos4 = "X";
				}
				else{
					pos5 = "X";
				}
			}
			if(options[currentOption] == "Y") {
				if(pos1 == "") {
					pos1 = "Y";
				}
				else if(pos2 == "") {
					pos2 = "Y";
				}
				else if(pos3 == "") {
					pos3 = "Y";
				}
				else if(pos4 == "") {
					pos4 = "Y";
				}
				else{
					pos5 = "Y";
				}
			}
			if(options[currentOption] == "Z") {
				if(pos1 == "") {
					pos1 = "Z";
				}
				else if(pos2 == "") {
					pos2 = "Z";
				}
				else if(pos3 == "") {
					pos3 = "Z";
				}
				else if(pos4 == "") {
					pos4 = "Z";
				}
				else{
					pos5 = "Z";
				}
			}
		}
	}
	
	
	public void render(Graphics g) {
		//Opcoes de menu
		g.setColor(Color.black);
		g.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		Image NameBox = null;
		try {
			NameBox = ImageIO.read(getClass().getResource("/NameScreen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image NameBox2 = null;
		try {
			NameBox2 = ImageIO.read(getClass().getResource("/NameScreen2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(image1) {
			g.drawImage(NameBox, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		}
		else if(image2){
			g.drawImage(NameBox2, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		}	
		Image SelectBox = null;
		try {
			SelectBox = ImageIO.read(getClass().getResource("/SelectorName.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Primeira Linha
		if(options[currentOption] == "A") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/4.5);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/3.2);
		}
		if(options[currentOption] == "B") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/3.4);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/3.2);
		}
		if(options[currentOption] == "C") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2.75);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/3.2);
		}
		if(options[currentOption] == "D") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2.28);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/3.2);
		}
		if(options[currentOption] == "E") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.98);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/3.2);
		}
		if(options[currentOption] == "F") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.77);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/3.2);
		}
		if(options[currentOption] == "G") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.58);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/3.2);
		}
		if(options[currentOption] == "H") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.41);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/3.2);
		}
		if(options[currentOption] == "I") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.3);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/3.2);
		}
		if(options[currentOption] == "J") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.23);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/3.2);
		}
		//Segunda Linha
		if(options[currentOption] == "K") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/4.5);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2.13);
		}
		if(options[currentOption] == "L") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/3.5);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2.13);
		}
		if(options[currentOption] == "M") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2.75);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2.13);
		}
		if(options[currentOption] == "N") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2.24);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2.13);
		}
		if(options[currentOption] == "O") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.9);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2.13);
		}
		if(options[currentOption] == "P") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.67);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2.13);
		}
		if(options[currentOption] == "Q") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.49);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2.13);
		}
		if(options[currentOption] == "R") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.34);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2.13);
		}
		if(options[currentOption] == "S") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.24);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2.13);
		}
		//Terceira Linha
		if(options[currentOption] == "T") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/4.5);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.6);
		}
		if(options[currentOption] == "U") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/3.45);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.6);
		}
		if(options[currentOption] == "V") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2.61);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.6);
		}
		if(options[currentOption] == "W") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2.15);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.6);
		}
		if(options[currentOption] == "X") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.83);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.6);
		}
		if(options[currentOption] == "Y") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.63);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.6);
		}
		if(options[currentOption] == "Z") {
			selectorPosX = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.47);
			selectorPosY = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.6);
		}
		g.drawImage(SelectBox, selectorPosX, selectorPosY, 120, 115,null);
		Font font1 = new Font("Segoe UI", Font.BOLD,74);
		g.setFont(font1);
		g.drawString(pos1, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2.6),(int) (int)(Toolkit.getDefaultToolkit().getScreenSize().height/4.2));
		g.drawString(pos2, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2.2),(int) (int)(Toolkit.getDefaultToolkit().getScreenSize().height/4.2));
		g.drawString(pos3, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.9),(int) (int)(Toolkit.getDefaultToolkit().getScreenSize().height/4.2));
		g.drawString(pos4, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.67),(int) (int)(Toolkit.getDefaultToolkit().getScreenSize().height/4.2));
		g.drawString(pos5, (int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.5),(int) (int)(Toolkit.getDefaultToolkit().getScreenSize().height/4.2));
	}
	
}
