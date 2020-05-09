package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControladorConfirmacionSalirApp {

    @FXML
    private AnchorPane anchorPane;
	
    @FXML
    private Button aceptar;

    @FXML
    private Button cancelar;
    
    private Image icon;
    
    private Stage thisStage;
    
    private String estilo;
    
    public void inicializarDatos(double anteriorX, double anteriorY, double anteriorWidth, double anteriorHeight, String estilo){
        thisStage = (Stage) cancelar.getScene().getWindow();
        anyadirIcono();
        corregirTamanyoVentana();
        corregirPosicionVentana(anteriorX, anteriorY, anteriorWidth, anteriorHeight);
        actualizarEstilo(estilo);
    }
    
    public void anyadirIcono() {
        icon = new Image("/imagenes/Icon.png");
        thisStage.getIcons().add(icon);
    }
    
    @FXML
    void aceptarHandler(ActionEvent event) {
    	Platform.exit();
        System.exit(0);
    }

    @FXML
    void cancelarHandler(ActionEvent event) {
    	thisStage.close();
    }
    
    public void corregirTamanyoVentana() {
    	thisStage.setWidth(600);
    	thisStage.setHeight(220);
    }
    
    public void corregirPosicionVentana(double anteriorX, double anteriorY, double anteriorWidth, double anteriorHeight) {
    	thisStage.setX(anteriorX + (anteriorWidth/6));
    	thisStage.setY(anteriorY + (anteriorHeight/3));
    }
    
    public void actualizarEstilo(String nuevoEstilo) {
    	estilo = nuevoEstilo;
    	String temaAzul = getClass().getResource("estiloAzul.css").toExternalForm();
        String temaRojo = getClass().getResource("estiloRojo.css").toExternalForm();
        String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
    	if(estilo.equals("Azul")) {
    		anchorPane.getStylesheets().remove(temaRojo);
    		anchorPane.getStylesheets().remove(temaVerde);
    		anchorPane.getStylesheets().add(temaAzul);
    	} else if(estilo.equals("Rojo")) {
    		anchorPane.getStylesheets().remove(temaAzul);
			anchorPane.getStylesheets().remove(temaVerde);
			anchorPane.getStylesheets().add(temaRojo);
    	} else {
    		anchorPane.getStylesheets().remove(temaAzul);
			anchorPane.getStylesheets().remove(temaRojo);
			anchorPane.getStylesheets().add(temaVerde);
    	}
    }

}
