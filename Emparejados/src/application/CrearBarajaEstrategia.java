package application;

import javafx.scene.image.Image;

public interface CrearBarajaEstrategia {
	
	public String nombre();
	
	public Image dorso();
	
	public int tamanyoBaraja();
	
    public Carta[] crearBaraja(int tamanyoPartida);
    
}