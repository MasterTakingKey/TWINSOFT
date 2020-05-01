package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControladorConfirmacionSalirApp {

    @FXML
    private Button aceptar;

    @FXML
    private Button cancelar;
    
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
