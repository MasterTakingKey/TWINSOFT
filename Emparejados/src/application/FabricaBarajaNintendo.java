package application;

public class FabricaBarajaNintendo extends FabricaBarajas{

	@Override
	public BarajaTematica barajaMetodoFabrica() {
		return new BarajaNintendo();
	}
	
}
