package com.bsstudios.world;

import com.bsstudios.entities.Entity;
import com.bsstudios.entities.Tubo;
import com.bsstudios.main.Game;

public class TuboGenerator {

	public int time = 0;
	public int targetTime = 60;
	
	public void tick() {
		time++;
		if(time == 60) {
			int altura1 = Entity.rand.nextInt(70 - 30) + 30;
			Tubo tubo1 = new Tubo(Game.WIDTH,0,20,altura1,1,null);
		
			Game.entities.add(tubo1);
			
			int altura2 = Entity.rand.nextInt(70 - 3) + 30;
			Tubo tubo2 = new Tubo(Game.WIDTH,Game.HEIGHT - altura2,20,altura2,1,null);
		
			Game.entities.add(tubo2);
			time = 0;
		}
	}
}
