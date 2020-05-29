package application;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class EstrategiaEstiloAzul extends EstrategiaEstilos {

	@Override
	public void actualizarEstilo(Pane pane, AnchorPane anchorPane, StackPane circuloSonido) {
		String temaAzul = getClass().getResource("/application/estiloAzul.css").toExternalForm();
        if(pane != null) {
    		pane.getStylesheets().add(temaAzul);
        }
        if(anchorPane != null) {
    		anchorPane.getStylesheets().add(temaAzul);
        }
        if(circuloSonido != null) {
    		circuloSonido.getStylesheets().add(temaAzul);
        }
	}
	
}
