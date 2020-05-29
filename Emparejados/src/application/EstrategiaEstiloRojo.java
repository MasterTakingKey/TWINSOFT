package application;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class EstrategiaEstiloRojo extends EstrategiaEstilos {

	@Override
	public void actualizarEstilo(Pane pane, AnchorPane anchorPane, StackPane circuloSonido) {
        String temaRojo = getClass().getResource("/application/estiloRojo.css").toExternalForm();
        if(pane != null) {
			pane.getStylesheets().add(temaRojo);
        }
        if(anchorPane != null) {
			anchorPane.getStylesheets().add(temaRojo);
        }
        if(circuloSonido != null) {
        	circuloSonido.getStylesheets().add(temaRojo);
        }
	}
	
}
