package application;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class CrearBarajaDeportesEstrategia implements CrearBarajaEstrategia {
	
	String nombre;
	
	int repeticiones;
	
	private ArrayList<Image> imagenes = new ArrayList<Image>();
	
	public CrearBarajaDeportesEstrategia(int repeticiones) {
		nombre = "Estrategia Baraja Deportes";
		this.repeticiones = repeticiones;
		imagenes.add(new Image("/imagenes/baraja_deportes/futbol.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/basquet.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/voleibol.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/beisbol.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/arco.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/karate.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/patinaje.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/tenis.png"));
	}
	
	public String nombre() {
		return "Deportes";
	}
	
	public Image dorso() {
		return new Image("/imagenes/baraja_deportes/dorso_deportes.png");
	}
	
	public int tamanyoBaraja() {
		return repeticiones*imagenes.size();
	}
	
    public Carta[] crearBaraja(int tamanyoPartida) {
    	Carta[] baraja = new Carta[repeticiones*tamanyoPartida];
    	Image imagenDorso = new Image("/imagenes/baraja_deportes/dorso_deportes.png");
    	int index = 0;
    	for(int i = 0; i < repeticiones; i++) {
    		for(int j = 0; j < tamanyoPartida; j++) {
    			baraja[index++] = (new Carta(imagenDorso, imagenes.get(j), j));
    		}
    	}
    	return baraja;
    }
}
