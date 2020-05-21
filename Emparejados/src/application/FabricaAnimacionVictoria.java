package application;


public class FabricaAnimacionVictoria extends FabricaAnimaciones {
	
	@Override
	public Animaciones animacionesMetodoFabrica() {
		return new AnimacionVictoria();
	}

}
