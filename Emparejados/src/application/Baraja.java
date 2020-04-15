package application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;

public class Baraja {
	
	String nombre;
	Image imagenDorso;
	Carta[] baraja;
	int tamaño;
	
	public Baraja(String n, Image imgD, int t) {
		nombre = n;
		imagenDorso = imgD;
		tamaño = t;
		inicializarBaraja(tamaño);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Image getImagenDorso() {
		return imagenDorso;
	}
	
	public int getTamaño() {
		return tamaño;
	}
	
	public Carta getCarta(int pos) {
		return baraja[pos];
	}
	
	public void setNombre(String nuevoNombre) {
		nombre = nuevoNombre;
	}
	
	public void setImagenDorso(Image nuevaImagen) {
		imagenDorso = nuevaImagen;
	}
	
	public void setTamaño(int nuevoTamaño) {
		tamaño = nuevoTamaño;
		inicializarBaraja(tamaño);
	}
	
	public void setCarta(Carta nuevaCarta, int pos) {
		baraja[pos] = nuevaCarta;
	}
	
	public void inicializarBaraja(int tamaño) {
		for(int i = 0; i < tamaño; i++) {
			baraja[i] = null;
		}
	}
	
	public void barajar() {
		List<Carta> listaCartas = Arrays.asList(baraja);
		Collections.shuffle(listaCartas);
		listaCartas.toArray(baraja);
	}
}
