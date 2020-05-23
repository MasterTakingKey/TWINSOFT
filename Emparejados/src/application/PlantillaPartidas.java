package application;

public abstract class PlantillaPartidas {
	public String ventana;
	
	public void setVentana(String ventana) {
		this.ventana = ventana;
	}
	
	public final void  InicializarPartida(){
		inicializarBaraja();
        inicializarTablero();
        inicializarCartas();
    	inicializarVariables();
    	inicializarAudioClips();
    	inicializarContadorTiempo();
    	inicializarPuntuacion();
    	inicializarAnimaciones();
    	actualizarSonido();
    	actualizarImagenSonido();
    	corregirTamanyoVentana();
    	corregirPosicionVentana(ventana);
    	actualizarEstilo();
    	inicializaMostrarCartas();
	}
	
	abstract void inicializarBaraja();
	abstract void inicializarTablero();
	abstract void inicializarCartas();
	abstract void inicializarVariables();
	abstract void inicializarAudioClips();
	abstract void inicializarContadorTiempo();
	abstract void inicializarPuntuacion();
	abstract void inicializarAnimaciones();
	abstract void actualizarSonido();
	abstract void actualizarImagenSonido();
	abstract void corregirTamanyoVentana();
	abstract void corregirPosicionVentana(String ventana);
	abstract void actualizarEstilo();
	abstract void inicializaMostrarCartas();
	
}
