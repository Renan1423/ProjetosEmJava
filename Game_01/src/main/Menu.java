package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {

	public String[] options = {"novo jogo", "carregar jogo", "sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up, down, enter;
	
	public boolean pause = false;
	
	public void tick() {
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0) {
				currentOption = maxOption;
			}
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption) {
				currentOption = 0;
			}
		}
		if(enter) {
			enter = false;
			if(options[currentOption] == "novo jogo" || options[currentOption] == "continuar") {
				Game.gameState = "NORMAL";
				pause = false;
			}else if(options[currentOption] == "sair") {
				System.exit(1);
			}
		}
	}
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString("Danki Code", ((Game.WIDTH*Game.SCALE)/2) -100, ((Game.HEIGHT * Game.SCALE)/2) - 150);
	
		//Opções do menu
		g.setFont(new Font("arial", Font.ITALIC, 24));
		if(pause == false) {
			g.drawString("Novo Jogo", ((Game.WIDTH*Game.SCALE)/2) -60, ((Game.HEIGHT * Game.SCALE)/2));
		}else {
			g.drawString("Continuar", ((Game.WIDTH*Game.SCALE)/2) -60, ((Game.HEIGHT * Game.SCALE)/2));
		}
		g.setFont(new Font("arial", Font.ITALIC, 24));
		g.drawString("Carregar Jogo", ((Game.WIDTH*Game.SCALE)/2) -60, ((Game.HEIGHT * Game.SCALE)/2) + 40);
		g.setFont(new Font("arial", Font.ITALIC, 24));
		g.drawString("Sair", ((Game.WIDTH*Game.SCALE)/2) -60, ((Game.HEIGHT * Game.SCALE)/2) + 80);
	
		if(options[currentOption] == "novo jogo") {
			g.drawString(">", ((Game.WIDTH*Game.SCALE)/2) -80, ((Game.HEIGHT * Game.SCALE)/2));
		}
		if(options[currentOption] == "carregar jogo") {
			g.drawString(">", ((Game.WIDTH*Game.SCALE)/2) -80, ((Game.HEIGHT * Game.SCALE)/2) + 40);
		}
		if(options[currentOption] == "sair") {
			g.drawString(">", ((Game.WIDTH*Game.SCALE)/2) -80, ((Game.HEIGHT * Game.SCALE)/2) + 80);
		}
	}
}
