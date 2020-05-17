package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControladorConfirmacionSalirMenuP {

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Button aceptar;

    @FXML
    private Button cancelar;
    
    private ControladorMenuPause menuPausa;
    
    private Image icon;
    
    private Stage thisStage;
    
    private ConfiguracionPartida singleton;
    
    public void inicializarDatos(ControladorMenuPause menuP, double anteriorWidth, double anteriorHeight, ConfiguracionPartida nuevoSingleton) {
    	menuPausa = menuP;
    	singleton = nuevoSingleton;
    	thisStage = (Stage) cancelar.getScene().getWindow();
    	anyadirIcono();
    	corregirTamanyoVentana();
    	corregirPosicionVentana(anteriorWidth, anteriorHeight);
    	actualizarEstilo();
    }
    
    public void anyadirIcono() {
        icon = new Image("/imagenes/Icon.png");
        thisStage.getIcons().add(icon);
    }
    
    @FXML
    void aceptarHandler(ActionEvent event) {
    	menuPausa.volverMenuPrincipal();
    	thisStage.close();
    }

    @FXML
    void cancelarHandler(ActionEvent event) {
    	thisStage.close();
    }
    
    public void corregirTamanyoVentana() {
    	thisStage.setWidth(600);
    	thisStage.setHeight(220);
    }
    
    public void corregirPosicionVentana(double anteriorWidth, double anteriorHeight) {
    	thisStage.setX(singleton.posicionX + (anteriorWidth/6));
    	thisStage.setY(singleton.posicionY + (anteriorHeight/3));
    }
    
    public void actualizarEstilo() {
    	String temaAzul = getClass().getResource("estiloAzul.css").toExternalForm();
        String temaRojo = getClass().getResource("estiloRojo.css").toExternalForm();
        String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
    	if(singleton.estilo.equals("Azul")) {
    		anchorPane.getStylesheets().remove(temaRojo);
    		anchorPane.getStylesheets().remove(temaVerde);
    		anchorPane.getStylesheets().add(temaAzul);
    	} else if(singleton.estilo.equals("Rojo")) {
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
