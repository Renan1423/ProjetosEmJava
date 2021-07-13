package com.gcstudios.graficos;

import java.awt.Color;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.gcstudios.entities.Player;
import com.gcstudios.main.Game;

public class UI {

	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("alterebro-pixel-font.ttf");
	public InputStream stream2 = ClassLoader.getSystemClassLoader().getResourceAsStream("alterebro-pixel-font.ttf");
	public Font newFont;
	public Font newFont2;
 
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(36,36,72,4);
		g.setColor(Color.yellow);
		g.fillRect(37,36,(int)((Game.player.stamina/Game.player.maxstamina)*70),3);
		Color ManaBlue = new Color(0, 255, 255);
		g.setColor(Color.black);
		g.fillRect(36,25,72,12);
		g.setColor(ManaBlue);
		g.fillRect(37,26,(int)((Game.player.mana/Game.player.maxMana)*70),10);
		/*try {
			newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(14f);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//g.setFont(newFont);
		Font font1 = new Font("Segoe UI", Font.BOLD, 9);
		g.setFont(font1);
		g.setColor(Color.white);
		g.drawString((int)Game.player.mana+"/"+(int)Game.player.maxMana,60,34);
		
		g.setColor(Color.white);
		
		Image BarraHP = null;
		try {
			BarraHP = ImageIO.read(getClass().getResource("/Bamboo.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		g.drawImage(BarraHP, 30, 7, 120, 20, null);
		g.setColor(Color.black);
		g.fillRect(49,11,72,12);
		Color Green = new Color(0, 220, 0);
		g.setColor(Green);
		g.fillRect(50,12,(int)((Game.player.life/Game.player.maxlife)*70),10);
		g.setColor(Color.white);
		/*try {
			newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(14f);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		g.setFont(font1);
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxlife,69,20);
		
		Image Panda = null;
		try {
			if(!Game.player.isDamaged && !Game.player.isHealing && !Game.player.isHealingMana) {
				Panda = ImageIO.read(getClass().getResource("/Perfil_Panda.png"));
			}
			else if(Game.player.isDamaged){
				Panda = ImageIO.read(getClass().getResource("/Perfil_Panda_Damaged.png"));
			}
			else if(Game.player.isHealing) {
				Panda = ImageIO.read(getClass().getResource("/Perfil_Panda_Healed.png"));
			}
			else if(Game.player.isHealingMana) {
				Panda = ImageIO.read(getClass().getResource("/Perfil_Panda_HealedMana.png"));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		g.drawImage(Panda, 5, 5, 32, 25, null);
		
		Color Purple = new Color(127, 0, 110);
		g.setColor(Color.black);
		g.fillRect(170,144,62,10);
		g.setColor(Purple);
		g.fillRect(171,145,(int)((Game.player.exp/Game.player.maxExp)*60),8);
		/*try {
			newFont2 = Font.createFont(Font.TRUETYPE_FONT, stream2).deriveFont(12f);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		g.setFont(font1);
		g.setColor(Color.white);
		g.drawString("Lv " + Game.player.Level,193,152);
		
		Image BambooM = null;
		try {
			if(Player.magia[Player.magiaAtual] == "fogo") {
				BambooM = ImageIO.read(getClass().getResource("/MagicBambooFire.png"));
			}
			if(Player.magia[Player.magiaAtual] == "gelo") {
				BambooM = ImageIO.read(getClass().getResource("/MagicBambooIce.png"));
			}
			if(Player.magia[Player.magiaAtual] == "raio") {
				BambooM = ImageIO.read(getClass().getResource("/MagicBambooThunder.png"));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		g.drawImage(BambooM,10,(Game.HEIGHT) - 60,50,50,null);
		
	}
	
}
