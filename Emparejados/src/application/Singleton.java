package application;

import java.util.ArrayList;

public class Singleton {

	private static Singleton instance;
	
	public ArrayList<Baraja> listaBarajas;
	
	public String estilo;
	
	public String[] listaMusica;
	
	public boolean soundOn;
	
	public double posicionX;
	
	public double posicionY;
	
	public Baraja barajaPartida;
	
	public int filasPartida;
	
	public int columnasPartida;
	
	public boolean limiteTiempoOn;

	public int tiempoPartida;
	
	public boolean mostrarCartasOn;
	
	public int tiempoMostrarCartas;
	
	public String efectosSonorosVoltear;
	
	public String efectosSonorosPareja;
	
	public String efectosVisualesVoltear;
	
	public String efectosVisualesPareja;
	
	protected Singleton() {
		
	}
	
	public static Singleton Instance() {
		if(instance == null) {
			instance = new Singleton();
		}
		return instance;
	}
	
}

