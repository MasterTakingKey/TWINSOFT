package application;


public class FabricaAnimacionVoltear extends FabricaAnimaciones {
	
	@Override
	public Animaciones animacionesMetodoFabrica() {
		return new AnimacionVoltear();
	}

}
