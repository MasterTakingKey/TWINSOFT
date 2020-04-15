package application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;

public class Baraja {
	
	String nombre;
	Image imagenDorso;
	Carta[] baraja;
	int tama�o;
	
	public Baraja(String n, Image imgD, int t) {
		nombre = n;
		imagenDorso = imgD;
		tama�o = t;
		inicializarBaraja(tama�o);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Image getImagenDorso() {
		return imagenDorso;
	}
	
	public int getTama�o() {
		return tama�o;
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
	
	public void setTama�o(int nuevoTama�o) {
		tama�o = nuevoTama�o;
		inicializarBaraja(tama�o);
	}
	
	public void setCarta(Carta nuevaCarta, int pos) {
		baraja[pos] = nuevaCarta;
	}
	
	public void inicializarBaraja(int tama�o) {
		for(int i = 0; i < tama�o; i++) {
			baraja[i] = null;
		}
	}
	
	public void barajar() {
		List<Carta> listaCartas = Arrays.asList(baraja);
		Collections.shuffle(listaCartas);
		listaCartas.toArray(baraja);
	}
}
