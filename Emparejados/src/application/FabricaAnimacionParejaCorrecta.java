package application;


public class FabricaAnimacionParejaCorrecta extends FabricaAnimaciones {
	
	@Override
	public Animaciones animacionesMetodoFabrica() {
		return new AnimacionParejaCorrecta();
	}

}
