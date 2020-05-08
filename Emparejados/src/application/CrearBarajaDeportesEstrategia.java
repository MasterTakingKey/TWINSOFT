package application;

import javafx.scene.image.Image;

public class CrearBarajaDeportesEstrategia implements CrearBarajaEstrategia {
	
	String nombre;
	
	int repeticiones;
	
	public CrearBarajaDeportesEstrategia(int repeticiones) {
		nombre = "Estrategia Baraja Deportes";
		this.repeticiones = repeticiones;
		
	}
	
	public String nombre() {
		return "Deportes";
	}
	
	public Image dorso() {
		return new Image("/imagenes/baraja_deportes/dorso_deportes.png");
	}
	
	public int tamanyoBaraja() {
		return 8*repeticiones;
	}
	
    public Carta[] crearBaraja(int tamanyoPartida) {
    	Carta[] baraja = new Carta[8*repeticiones];
    	Image imagenDorso = new Image("/imagenes/baraja_deportes/dorso_deportes.png");
    	int index = 0;
    	for(int i = 0; i < repeticiones; i++) {
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_deportes/futbol.png"), 0));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_deportes/basquet.png"), 1));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_deportes/voleibol.png"), 2));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_deportes/beisbol.png"), 3));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_deportes/arco.png"), 4));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_deportes/karate.png"), 5));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_deportes/patinaje.png"), 6));
    		baraja[index++] = (new Carta(imagenDorso, new Image("/imagenes/baraja_deportes/tenis.png"), 7));
    	}
    	return baraja;
    }
}
