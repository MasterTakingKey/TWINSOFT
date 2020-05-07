package application;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class CrearBarajaAnimalesEstrategia implements CrearBarajaEstrategia {
	
	String nombre;
	
	int repeticiones;
	
	public CrearBarajaAnimalesEstrategia(int repeticiones) {
		nombre = "Estrategia Baraja Animales";
		this.repeticiones = repeticiones;
		
	}
	
	public String nombre() {
		return "Animales";
	}
	
	public Image dorso() {
		return new Image("/imagenes/dorso_aldeano.png");
	}
	
	public int tamanyoBaraja(int tamanyo) {
		return tamanyo;
	}
	
    public Carta[] crearBaraja(int tamanyo) {
    	Carta[] baraja = new Carta[tamanyo];
    	Image imagenDorso = new Image("/imagenes/dorso_aldeano.png");
    	ArrayList<Image> imagenes = new ArrayList<Image>();
    	imagenes.add(new Image("/imagenes/elefante.png"));
    	imagenes.add(new Image("/imagenes/hipopotamo.png"));
    	imagenes.add(new Image("/imagenes/jirafa.png"));
    	imagenes.add(new Image("/imagenes/leon.png"));
    	imagenes.add(new Image("/imagenes/mono.png"));
    	imagenes.add(new Image("/imagenes/rinoceronte.png"));
    	imagenes.add(new Image("/imagenes/serpiente.png"));
    	imagenes.add(new Image("/imagenes/zebra.png"));
    	int index = 0;
    	for(int i = 0; i < repeticiones; i++) {
    		for(int j = 0; j < tamanyo/2; j++) {
    			baraja[index++] = (new Carta(imagenDorso, imagenes.get(j), j));
    		}
    	}
    	return baraja;
    }
}
