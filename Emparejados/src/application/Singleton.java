package application;

import java.util.ArrayList;

public class Singleton {

	private static Singleton instance;
	
	public ArrayList<Baraja> listaBarajas;
	
	public Baraja barajaPartida;
	
	public String estilo;
	
	public String[] listaMusica;
	
	public boolean soundOn;
	
	public double posicionX;
	
	public double posicionY;
	
	public boolean tiempoOn;
	
	public int tiempoSegundos;
	
	protected Singleton() {
		
	}
	
	public static Singleton Instance() {
		if(instance == null) {
			instance = new Singleton();
		}
		return instance;
	}
	
}

