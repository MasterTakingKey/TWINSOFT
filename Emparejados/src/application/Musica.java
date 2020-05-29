package application;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Musica {

	Clip clip;
	
	long tiempoMusica;
	
	public Musica(String musicPath, long tiempoMusica) {
		InputStream is= getClass().getResourceAsStream(musicPath);
		InputStream buffer = new BufferedInputStream(is);
    	try {
    		AudioInputStream audioInput = AudioSystem.getAudioInputStream(buffer);
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
