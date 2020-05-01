package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControladorConfirmacionSalirMenuP {

    @FXML
    private Button aceptar;

    @FXML
    private Button cancelar;
    
    private ControladorMenuPause menuPausa;
    
    public void inicializarDatos(ControladorMenuPause menuP) {
    	menuPausa = menuP;
    }
    
    @FXML
    void aceptarHandler(ActionEvent event) {
    	menuPausa.volverMenuPrincipal();
    	cerrarVentana();
    }

    @FXML
    void cancelarHandler(ActionEvent event) {
    	cerrarVentana();
    }
    
    public void cerrarVentana() {
    	Stage stage = (Stage) cancelar.getScene().getWindow();
    	stage.close();
    }

}
