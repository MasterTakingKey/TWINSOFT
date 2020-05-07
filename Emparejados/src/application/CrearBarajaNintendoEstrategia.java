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
		return new Image("/imagenes/dorso_nintendo.png");
	}
	
	public int tamanyoBaraja() {
		return 8*repeticiones;
	}
	
    public Carta[] crearBaraja() {
    	Carta[] baraja = new Carta[8*repeticiones];
    	Image imagenDorso = new Image("/imagenes/dorso_nintendo.png");
    	int index = 0;
    	for(int i = 0; i < repeticiones; i++) {
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/mario.png"), 0));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/luigi.png"), 1));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/bowser.png"), 2));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/link.png"), 3));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/kirby.png"), 4));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/pit.png"), 5));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/samus.png"), 6));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/pikachu.png"), 7));
    	}
    	return baraja;
    }
}
