package application;

public class FabricaAnimacionFinTurno extends FabricaAnimaciones {
	
	@Override
	public Animaciones animacionesMetodoFabrica() {
		return new AnimacionFinTurno();
	}
}
