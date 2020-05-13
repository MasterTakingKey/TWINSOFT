package application;


public class FabricaAnimacionParejaCorrecta2 extends FabricaAnimaciones {
	
	@Override
	public Animaciones animacionesMetodoFabrica() {
		return new AnimacionParejaCorrecta2();
	}

}
