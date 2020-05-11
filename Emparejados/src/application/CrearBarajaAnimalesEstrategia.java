package application;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class CrearBarajaAnimalesEstrategia implements CrearBarajaEstrategia {
	
	String nombre;
	
	int repeticiones;
	
	int tamanyoPartida;
	
	private ArrayList<Image> imagenes = new ArrayList<Image>();
	
	public CrearBarajaAnimalesEstrategia(int repeticiones, int tamanyoPartida) {
		nombre = "Estrategia Baraja Animales";
		this.repeticiones = repeticiones;
		this.tamanyoPartida = tamanyoPartida;
	}
	
	public String nombre() {
		return "Animales";
	}
	
	public Image dorso() {
		return new Image("/imagenes/baraja_animales/dorso_aldeano.png");
	}
	
	public int tamanyoBaraja() {
		return repeticiones*tamanyoPartida;
	}
	
    public Carta[] crearBaraja() {

    	Carta[] baraja = new Carta[repeticiones*tamanyoPartida];
    	Image imagenDorso = new Image("/imagenes/baraja_animales/dorso_aldeano.png");
    	
    	imagenes.add(new Image("/imagenes/baraja_animales/elefante.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/hipopotamo.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/jirafa.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/leon.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/mono.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/rinoceronte.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/serpiente.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/zebra.png"));
    	
    	int index = 0;
    	for(int i = 0; i < repeticiones; i++) {
    		for(int j = 0; j < tamanyoPartida; j++) {
    			baraja[index++] = (new Carta(imagenDorso, imagenes.get(j), j));
    		}
    	}
    	return baraja;
    }
}
