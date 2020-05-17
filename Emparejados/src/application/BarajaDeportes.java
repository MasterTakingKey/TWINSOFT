package application;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class BarajaDeportes extends BarajaTematica {

	public BarajaDeportes() {
		nombre = "Deportes";
		imagenDorso = new Image("/imagenes/baraja_deportes/dorso_deportes.png");
		tamanyo = 36;
		baraja = new Carta[tamanyo];
	}
	
	public void crearBaraja() {
		ArrayList<Image> imagenes = new ArrayList<Image>();
		imagenes.add(new Image("/imagenes/baraja_deportes/futbol.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/basquet.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/voleibol.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/beisbol.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/arco.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/karate.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/patinaje.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/tenis.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/ciclismo.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/correr.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/gimnasia.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/hockey.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/moto.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/natacion.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/patinaje2.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/skate.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/submarinismo.png"));
		imagenes.add(new Image("/imagenes/baraja_deportes/waterpolo.png"));
		
    	int index = 0;
    	for(int i = 0; i < 2; i++) {
    		for(int j = 0; j < 18; j++) {
    			baraja[index++] = (new Carta(imagenDorso, imagenes.get(j), j));
    		}
    	}
	}
	
}
