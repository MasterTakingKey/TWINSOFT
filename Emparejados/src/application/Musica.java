package application;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Musica {

	Clip clip;
	
	long tiempoMusica;
	
	public Musica(String musicPath, long tiempoMusica) {
    	File musicFile = new File(musicPath);
    	try {
    		AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
    		clip = AudioSystem.getClip();
    		clip.open(audioInput);
    		clip.setMicrosecondPosition(tiempoMusica);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		this.tiempoMusica = tiempoMusica;
	}
	
	public Clip getClip() {
		return clip;
	}
	
	public long getTiempoMusica() {
		return tiempoMusica;
	}
	
	public void setClip(Clip nuevoClip) {
		clip = nuevoClip;
	}
	
	public void setTiempoMusica(long nuevoTiempoMusica) {
		tiempoMusica = nuevoTiempoMusica;
	}
	
    public void playMusic(){
	    clip.start();
	    clip.loop(clip.LOOP_CONTINUOUSLY); 
	}
    
    public void stopMusic() {
    	clip.stop();
    }
}
