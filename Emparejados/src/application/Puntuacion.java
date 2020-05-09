package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class Puntuacion {
	
	private static final long TIEMPO_ENTRE_TURNOS = 5;
	
	private int puntos;

	private long tiempoRestante;
	
	private SimpleBooleanProperty puntosCambiados;
	
	private Timeline timeline;
	
	private AudioClip error;
	
	public Puntuacion() {
		error = new AudioClip(getClass().getResource("/sonidos/error1.mp3").toString());
		timeline = new Timeline();
		puntosCambiados = new SimpleBooleanProperty();
		puntosCambiados.setValue(false);
	}
	
	public int getPuntos() {
		return puntos;
	}
	
	public SimpleBooleanProperty getPuntosCambiados() {
		return puntosCambiados;
	}
	
	public Timeline getTimeline() {
		return timeline;
	}
	
	public void setPuntos(int nuevosPuntos) {
		puntos = nuevosPuntos;
	}
	
	public void setPuntosCambiados(SimpleBooleanProperty nuevosPuntos) {
		puntosCambiados = nuevosPuntos;
	}
	
	public void setTimeline(Timeline newTimeline) {
		timeline = newTimeline;
	}
	
	public void sumaPuntos(int puntosASumar, boolean parejaIncorrecta, int numeroVecesGirada) {
		if (parejaIncorrecta) puntosASumar -= numeroVecesGirada;
		puntos += puntosASumar;
		puntosCambiados.setValue(!puntosCambiados.getValue());
	}  	
	    
	public void sumarBonificacionVictoria(int tiempoRestante, int numeroParejas) {
	    int bonificacion = (int) (tiempoRestante * 0.5) + numeroParejas; 
	    sumaPuntos(bonificacion, false, 0);
	}
	
	public void iniciarTiempoEntreTurnos() {
		tiempoRestante = TIEMPO_ENTRE_TURNOS;
		timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                  event -> {
                	 tiempoRestante--;
				     if (tiempoRestante < 0) {
				         timeline.stop();
				         error.play();
				         sumaPuntos(-2, false, 0);
				     }
				   }));
        timeline.playFromStart();
	}
	
	public void playTimeline() {
		timeline.play();
	}
	
	public void stopTimeLine() {
		timeline.stop();
	}
}
