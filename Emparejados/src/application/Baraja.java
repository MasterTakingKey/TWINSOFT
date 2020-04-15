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
	
	public Baraja(String n, Image imgD, int t) {
		nombre = n;
		imagenDorso = imgD;
		tamanyo = t;
		inicializarBaraja(tamanyo);
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
	
	public Carta getCarta(int pos) {
		return baraja[pos];
	}
	
	public void setNombre(String nuevoNombre) {
		nombre = nuevoNombre;
	}
	
	public void setImagenDorso(Image nuevaImagen) {
		imagenDorso = nuevaImagen;
	}
	
	public void setTamanyo(int nuevoTamanyo) {
		tamanyo = nuevoTamanyo;
		inicializarBaraja(tamanyo);
	}
	
	public void setCarta(Carta nuevaCarta, int pos) {
		baraja[pos] = nuevaCarta;
	}
	
	public void inicializarBaraja(int tamanyo) {
		for(int i = 0; i < tamanyo; i++) {
			baraja[i] = null;
		}
	}
	
	public void barajar() {
		List<Carta> listaCartas = Arrays.asList(baraja);
		Collections.shuffle(listaCartas);
		listaCartas.toArray(baraja);
	}
}
