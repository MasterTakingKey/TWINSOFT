package application;

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
		return new Image("/imagenes/baraja_animales/dorso_aldeano.png");
	}
	
	public int tamanyoBaraja() {
		return 8*repeticiones;
	}
	
    public Carta[] crearBaraja() {
    	Carta[] baraja = new Carta[8*repeticiones];
    	Image imagenDorso = new Image("/imagenes/baraja_animales/dorso_aldeano.png");
    	int index = 0;
    	for(int i = 0; i < repeticiones; i++) {
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_animales/elefante.png"), 0));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_animales/hipopotamo.png"), 1));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_animales/jirafa.png"), 2));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_animales/leon.png"), 3));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_animales/mono.png"), 4));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_animales/rinoceronte.png"), 5));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_animales/serpiente.png"), 6));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_animales/zebra.png"), 7));
    	}
    	return baraja;
    }
}
