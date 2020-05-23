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
    
    private ControladorMenuPausaMultijugador menuPausaMulti;
    
    private Image icon;
    
    private Stage thisStage;
    
    private ConfiguracionPartida singleton;
    
    private String tipoPartida;
    
    public void inicializarDatos(ControladorMenuPause menuP, double anteriorWidth, double anteriorHeight, ConfiguracionPartida nuevoSingleton) {
    	menuPausa = menuP;
    	singleton = nuevoSingleton;
    	thisStage = (Stage) cancelar.getScene().getWindow();
    	tipoPartida = "UnJugador";
    	anyadirIcono();
    	corregirTamanyoVentana();
    	corregirPosicionVentana(anteriorWidth, anteriorHeight);
    	actualizarEstilo();
    }
    
    public void inicializarDatosMulti(ControladorMenuPausaMultijugador menuP, double anteriorWidth, double anteriorHeight, ConfiguracionPartida nuevoSingleton) {
    	menuPausaMulti = menuP;
    	singleton = nuevoSingleton;
    	tipoPartida = "Multijugador";
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
    	if(tipoPartida.equals("UnJugador")) {
            menuPausa.volverMenuPrincipal();
            thisStage.close();
        } else if(tipoPartida.equals("Multijugador")) {
            menuPausaMulti.volverMenuPrincipal();
            thisStage.close();
        }
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
    	Estilo nuevoEstilo;
        if(singleton.estilo.equals("Azul")) {
            nuevoEstilo = new Estilo(new EstrategiaEstiloAzul());
        } else if(singleton.estilo.equals("Rojo")) {
            nuevoEstilo = new Estilo(new EstrategiaEstiloRojo());
        } else {
            nuevoEstilo = new Estilo(new EstrategiaEstiloVerde());
        }
        nuevoEstilo.cambiarEstilo(null, anchorPane, null);
    }

}
