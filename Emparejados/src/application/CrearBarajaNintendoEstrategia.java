package application;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class CrearBarajaNintendoEstrategia implements CrearBarajaEstrategia {
	
	String nombre;
	
	int repeticiones;
	
	private ArrayList<Image> imagenes = new ArrayList<Image>();
	
	public CrearBarajaNintendoEstrategia(int repeticiones) {
		nombre = "Estrategia Baraja Nintendo";
		this.repeticiones = repeticiones;
		imagenes.add(new Image("/imagenes/baraja_nintendo/mario.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/luigi.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/bowser.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/link.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/kirby.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/pit.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/samus.png"));
		imagenes.add(new Image("/imagenes/baraja_nintendo/pikachu.png"));	
	}
	
	public String nombre() {
		return "Nintendo";
	}
	
	public Image dorso() {
		return new Image("/imagenes/baraja_nintendo/dorso_nintendo.png");
	}
	
	public int tamanyoBaraja() {
		return repeticiones*imagenes.size();
	}
	
    public Carta[] crearBaraja(int tamanyoPartida) {
    	Carta[] baraja = new Carta[repeticiones*tamanyoPartida];
    	Image imagenDorso = new Image("/imagenes/baraja_nintendo/dorso_nintendo.png");
    	int index = 0;
    	for(int i = 0; i < repeticiones; i++) {
    		for(int j = 0; j < tamanyoPartida; j++) {
    			baraja[index++] = (new Carta(imagenDorso, imagenes.get(j), j));
    		}
    	}
    	return baraja;
    }
}
