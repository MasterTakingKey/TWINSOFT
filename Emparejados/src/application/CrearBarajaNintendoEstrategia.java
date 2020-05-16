package application;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class CrearBarajaNintendoEstrategia implements CrearBarajaEstrategia {
	
	String nombre;
	
	int repeticiones;
	
	int tamanyoPartida;
	
	private ArrayList<Image> imagenes = new ArrayList<Image>();
	
	public CrearBarajaNintendoEstrategia(int repeticiones, int tamanyoPartida) {
		nombre = "Estrategia Baraja Nintendo";
		this.repeticiones = repeticiones;
		this.tamanyoPartida = tamanyoPartida;
	}
	
	public String nombre() {
		return "Nintendo";
	}
	
	public Image dorso() {
		return new Image("/imagenes/baraja_nintendo/dorso_nintendo.png");
	}
	
	public int tamanyoBaraja() {
		return repeticiones*tamanyoPartida;
	}
	
    public Carta[] crearBaraja() {

    	Carta[] baraja = new Carta[repeticiones*tamanyoPartida];
    	Image imagenDorso = new Image("/imagenes/baraja_nintendo/dorso_nintendo.png");
    	
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
    	for(int i = 0; i < repeticiones; i++) {
    		for(int j = 0; j < tamanyoPartida; j++) {
    			baraja[index++] = (new Carta(imagenDorso, imagenes.get(j), j));
    		}
    	}
    	return baraja;
    }
}
