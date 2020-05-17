package application;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class BarajaNintendo extends BarajaTematica {

	public BarajaNintendo() {
		nombre = "Nintendo";
		imagenDorso = new Image("/imagenes/baraja_nintendo/dorso_nintendo.png");
		tamanyo = 30;
		baraja = new Carta[tamanyo];
	}
	
	public void crearBaraja() {
		ArrayList<Image> imagenes = new ArrayList<Image>();
		imagenes.add(new Image("/imagenes/baraja_nintendo/mario.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/luigi.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/bowser.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/link.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/kirby.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/pit.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/samus.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/pikachu.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/wario.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/yoshi.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/jigglypuff.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/lucario.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/chrom.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/donkey.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/toonLink.png"));
		
    	int index = 0;
    	for(int i = 0; i < 2; i++) {
    		for(int j = 0; j < 15; j++) {
    			baraja[index++] = (new Carta(imagenDorso, imagenes.get(j), j));
    		}
    	}
	}
	
}
