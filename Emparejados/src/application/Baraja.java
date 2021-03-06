package application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.image.Image;

public class Baraja {
	
	String nombre;
	Image imagenDorso;
	int tamanyo;
	Carta[] baraja;
	
	public Baraja() {}
	
	public Baraja(String n, Image imagenD, int t) {
		nombre = n;
		imagenDorso = imagenD;
		tamanyo = t;
		baraja = new Carta[tamanyo];
	}
	
	public Baraja(int filas, int columnas) {
		nombre = "Baraja por Defecto";
		imagenDorso = new Image("/imagenes/dorso_aldeano.png");
		tamanyo = filas*columnas;
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
	
	public Carta[] getBaraja() {
		return baraja;
	}
	
	public void setNombre(String nuevoNombre) {
		nombre = nuevoNombre;
	}
	
	public void setImagenDorso(Image nuevaImagen) {
		imagenDorso = nuevaImagen;
	}
	
	public void setTamanyo(int nuevoTamanyo) {
		tamanyo = nuevoTamanyo;
		baraja = new Carta[tamanyo];

	}
	
	public void setCarta(Carta nuevaCarta, int posisicon) {
		baraja[posisicon] = nuevaCarta;
	}

	public void barajar() {
		List<Carta> listaCartas = Arrays.asList(baraja);
		Collections.shuffle(listaCartas);
		listaCartas.toArray(baraja);
	}
	
}
