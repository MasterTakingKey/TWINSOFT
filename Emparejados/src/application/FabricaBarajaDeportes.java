package application;

public class FabricaBarajaDeportes extends FabricaBarajas{

	@Override
	public BarajaTematica barajaMetodoFabrica() {
		return new BarajaDeportes();
	}
	
}
