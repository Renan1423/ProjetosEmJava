package main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	private AudioClip clip;
	
	public static final Sound musicBackground = new Sound("C:\\Users\\samir\\eclipse-workspace\\Game_01\\res\\Battle-6.mp3");
	public static final Sound hurtEffect = new Sound("/Hit_Hurt.wav");
	
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch(Throwable e) {
			
		}
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		}
		catch(Throwable e) {
			
		}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		}
		catch(Throwable e) {
			
		}
	}
	
	
	
}
