package com.bsstudios.world;

import com.bsstudios.entities.Entity;
import com.bsstudios.entities.Tubo;
import com.bsstudios.main.Game;

public class TuboGenerator {

	public int time = 0;
	public int targetTime = Entity.rand.nextInt(180);
	
	public void tick() {
		time++;
		if(time == targetTime) {
			int altura1 = Entity.rand.nextInt(178);
			Tubo tubo1 = new Tubo(Game.WIDTH,altura1,48,32,1,Game.spritesheet.getSprite(0, 96, 48, 32));
		
			Game.entities.add(tubo1);
			time = 0;
		}
		
	}
}
