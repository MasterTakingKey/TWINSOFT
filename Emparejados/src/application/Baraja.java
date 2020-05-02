package application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.image.Image;

public class Baraja {
	
	String nombre;
	Image imagenDorso;
	Carta[] baraja;
	int tamanyo;
	
	public Baraja() {
		
	}
	
	public Baraja(String nombre, Image imagenDorso, int tamanyo) {
		this.nombre = nombre;
		this.imagenDorso = imagenDorso;
		this.tamanyo = tamanyo;
		baraja = new Carta[tamanyo];
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Image getImagenDorso() {
		return imagenDorso;
	}
	
	public int getTamanyo() {
		return tamanyo;
	}
	
	public Carta getCarta(int posicion) {
		return baraja[posicion];
	}
	
	public void setNombre(String nuevoNombre) {
		nombre = nuevoNombre;
	}
	
	public void setImagenDorso(Image nuevaImagen) {
		imagenDorso = nuevaImagen;
	}
	
	public void setTamanyo(int nuevoTamanyo) {
		tamanyo = nuevoTamanyo;

	}
	
	public void setCarta(Carta nuevaCarta, int posisicon) {
		baraja[posisicon] = nuevaCarta;
	}
	
	public void barajar() {
		List<Carta> listaCartas = Arrays.asList(baraja);
		Collections.shuffle(listaCartas);
		listaCartas.toArray(baraja);
	}
	
    public void crearBarajaAnimales(int repeticiones) {
    	nombre = "Animales";
    	tamanyo = 8*repeticiones;
    	imagenDorso = new Image("/imagenes/dorso_aldeano.png");
    	baraja = new Carta[tamanyo];
    	int index = 0;
    	for(int i = 0; i < repeticiones; i++) {
    		this.setCarta(new Carta(this.getImagenDorso(), new Image("/imagenes/elefante.png"), 0), index++);
    		this.setCarta(new Carta(this.getImagenDorso(), new Image("/imagenes/hipopotamo.png"), 1), index++);
    		this.setCarta(new Carta(this.getImagenDorso(), new Image("/imagenes/jirafa.png"), 2), index++);
    		this.setCarta(new Carta(this.getImagenDorso(), new Image("/imagenes/leon.png"), 3), index++);
    		this.setCarta(new Carta(this.getImagenDorso(), new Image("/imagenes/mono.png"), 4), index++);
    		this.setCarta(new Carta(this.getImagenDorso(), new Image("/imagenes/rinoceronte.png"), 5), index++);
    		this.setCarta(new Carta(this.getImagenDorso(), new Image("/imagenes/serpiente.png"), 6), index++);
    		this.setCarta(new Carta(this.getImagenDorso(), new Image("/imagenes/zebra.png"), 7), index++);
    	}
    	this.barajar();
    }
}
