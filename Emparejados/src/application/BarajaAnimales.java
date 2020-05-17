package application;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class BarajaAnimales extends BarajaTematica {
	
	public BarajaAnimales() {
		nombre = "Animales";
		imagenDorso = new Image("/imagenes/baraja_animales/dorso_aldeano.png");
		tamanyo = 16;
		baraja = new Carta[tamanyo];
	}
	
	public void crearBaraja() {
		ArrayList<Image> imagenes = new ArrayList<Image>();
		imagenes.add(new Image("/imagenes/baraja_animales/elefante.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/hipopotamo.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/jirafa.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/leon.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/mono.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/rinoceronte.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/serpiente.png"));
		imagenes.add(new Image("/imagenes/baraja_animales/zebra.png"));
		
    	int index = 0;
    	for(int i = 0; i < 2; i++) {
    		for(int j = 0; j < 8; j++) {
    			baraja[index++] = (new Carta(imagenDorso, imagenes.get(j), j));
    		}
    	}
	}

}
