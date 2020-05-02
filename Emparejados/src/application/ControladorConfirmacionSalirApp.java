package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ControladorConfirmacionSalirApp {

    @FXML
    private Button aceptar;

    @FXML
    private Button cancelar;
    
    private Image icon;
    
    public void inicializarDatos(){
        icon = new Image("/imagenes/Icon.png");
        Stage stage = (Stage) cancelar.getScene().getWindow();
        stage.getIcons().add(icon);
    }
    
    @FXML
    void aceptarHandler(ActionEvent event) {
    	Platform.exit();
        System.exit(0);
    }

    @FXML
    void cancelarHandler(ActionEvent event) {
    	Stage stage = (Stage) cancelar.getScene().getWindow();
    	stage.close();
    }

}
