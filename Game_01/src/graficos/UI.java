package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entities.Player;
import main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(8, 8,50, 10);
		g.setColor(Color.green);
		g.fillRect(8, 8,(int)((Game.player.life/Game.player.maxlife)*50), 10);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,8));
		g.drawString((int)Game.player.life +"/"+ (int)Game.player.maxlife,18,16);
		
	}
	
}
