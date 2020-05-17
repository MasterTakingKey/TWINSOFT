package application;

public class FabricaBarajaAnimales extends FabricaBarajas {

	@Override
	public BarajaTematica barajaMetodoFabrica() {
		return new BarajaAnimales();
	}
	
}
