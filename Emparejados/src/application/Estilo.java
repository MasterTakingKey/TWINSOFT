package application;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Estilo {
	
	private EstrategiaEstilos estrategia;
	
	public Estilo(EstrategiaEstilos nuevaEstrategia) {
		estrategia = nuevaEstrategia;
	}
	
	public void cambiarEstilo(Pane pane, AnchorPane anchorPane, StackPane circuloSonido) {
		estrategia.actualizarEstilo(pane, anchorPane, circuloSonido);
	}
	
}
