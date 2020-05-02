package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ControladorConfirmacionSalirMenuP {

    @FXML
    private Button aceptar;

    @FXML
    private Button cancelar;
    
    private ControladorMenuPause menuPausa;
    
    private Image icon;
    
    public void inicializarDatos(ControladorMenuPause menuP) {
    	menuPausa = menuP;
    	icon = new Image("/imagenes/Icon.png");
    	Stage stage = (Stage) cancelar.getScene().getWindow();
        stage.getIcons().add(icon);
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
