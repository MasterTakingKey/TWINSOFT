package application;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class EstrategiaEstiloVerde extends EstrategiaEstilos {

	@Override
	public void actualizarEstilo(Pane pane, AnchorPane anchorPane, StackPane circuloSonido) {
        String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
        if(pane != null) {
			pane.getStylesheets().add(temaVerde);
        }
        if(anchorPane != null) {
			anchorPane.getStylesheets().add(temaVerde);
        }
        if(circuloSonido != null) {
			circuloSonido.getStylesheets().add(temaVerde);
        }
	}
	
}
