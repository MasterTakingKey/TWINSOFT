package application;


public class FabricaAnimacionParejaIncorrecta extends FabricaAnimaciones {
	
	@Override
	public Animaciones animacionesMetodoFabrica() {
		return new AnimacionParejaIncorrecta();
	}

}
