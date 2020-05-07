package application;

import javafx.scene.image.Image;

public class CrearBarajaNintendoEstrategia implements CrearBarajaEstrategia {
	
	String nombre;
	
	int repeticiones;
	
	public CrearBarajaNintendoEstrategia(int repeticiones) {
		nombre = "Estrategia Baraja Nintendo";
		this.repeticiones = repeticiones;
		
	}
	
	public String nombre() {
		return "Nintendo";
	}
	
	public Image dorso() {
		return new Image("/imagenes/baraja_nintendo/dorso_nintendo.png");
	}
	
	public int tamanyoBaraja() {
		return 8*repeticiones;
	}
	
    public Carta[] crearBaraja() {
    	Carta[] baraja = new Carta[8*repeticiones];
    	Image imagenDorso = new Image("/imagenes/baraja_nintendo/dorso_nintendo.png");
    	int index = 0;
    	for(int i = 0; i < repeticiones; i++) {
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_nintendo/mario.png"), 0));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_nintendo/luigi.png"), 1));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_nintendo/bowser.png"), 2));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_nintendo/link.png"), 3));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_nintendo/kirby.png"), 4));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_nintendo/pit.png"), 5));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_nintendo/samus.png"), 6));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_nintendo/pikachu.png"), 7));
    	}
    	return baraja;
    }
}
