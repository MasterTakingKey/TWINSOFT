package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ContadorTiempo {
	
	private Timeline timeline;
	
	private StringProperty Time;
	
	private boolean esPausa;
	
	private Integer tiempoRestante;
	
	public ContadorTiempo() {
		timeline = new Timeline();
		Time = new SimpleStringProperty("");
		esPausa = false;
		
	}
	
	public Integer getTiempoRestante() {
		return tiempoRestante;
	}
	
	public boolean getEsPausa() {
		return esPausa;
	}
	
	public void setTiempoRestante(Integer nuevoTiempo) {
		tiempoRestante = nuevoTiempo;
	} 
	
	public void setEsPausa(boolean nuevaPausa) {
		esPausa = nuevaPausa;
	}
	
	public void parar() {
    	timeline.stop();
	}
	
	public void continuar() {
		timeline.play();
    }
	
	public void iniciarTiempoPartida(Label tiempo, int tiempoPartida) {
		tiempo.textProperty().bind(Time);
    	iniciaTiempo(tiempoPartida);
	}

    public void iniciaTiempo(int tiempoPartida) {
    	 tiempoRestante = tiempoPartida; 
    	 actualizarContadorTiempo(tiempoRestante);
         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.getKeyFrames().add(
                 new KeyFrame(Duration.seconds(1),
                   event -> {
					 tiempoRestante--;
					 actualizarContadorTiempo(tiempoRestante);
				     if (tiempoRestante <= 0 || esPausa) {
				         timeline.stop();
				     }
				   }));
         timeline.playFromStart();
     }
    
    public void actualizarContadorTiempo(int seconds) {
    	int mins = seconds / 60;
    	int secs = seconds - mins * 60;
    	String Secs = String.format("%02d", secs);
    	Time.set(Integer.toString(mins) + ":" + Secs);
    }

}
