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
    
    private Stage thisStage;
    
    public void inicializarDatos(double anteriorX, double anteriorY, double anteriorWidth, double anteriorHeight){
        thisStage = (Stage) cancelar.getScene().getWindow();
        añadirIcono();
        corregirTamañoVentana();
        corregirPosicionVentana(anteriorX, anteriorY, anteriorWidth, anteriorHeight);
    }
    
    public void añadirIcono() {
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
    
    public void corregirTamañoVentana() {
    	thisStage.setWidth(600);
    	thisStage.setHeight(220);
    }
    
    public void corregirPosicionVentana(double anteriorX, double anteriorY, double anteriorWidth, double anteriorHeight) {
    	thisStage.setX(anteriorX + (anteriorWidth/6));
    	thisStage.setY(anteriorY + (anteriorHeight/3));
    }

}
