package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ControladorElegirNombresMultijugador {

	@FXML
	private Pane pane;
	 
    @FXML
    private TextField nombreJ1;

    @FXML
    private TextField nombreJ2;
    
    @FXML
    private StackPane circuloSonido;

    @FXML
    private ImageView iconoSonido;
    
    private ConfiguracionPartida singleton;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private long tiempoMusica;
    
    private Musica musicaFondo;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Image icon;

    public void iniciarElegirNombresMultijugador(Stage stage, ConfiguracionPartida nuevoSingleton, Musica cancion, long tiempoCancion){
        primaryStage = stage;
        singleton = nuevoSingleton;
        musicaFondo = cancion;
        tiempoMusica = tiempoCancion;
        primaryStage = stage;
        singleton = nuevoSingleton;
        inicializarVariables();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana();
        actualizarEstilo();
    }

    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        thisStage = (Stage) nombreJ1.getScene().getWindow();
    }
    
    @FXML
    void jugarHandler(ActionEvent event) {
    	if(nombreJ1.getText().length() != 3 || nombreJ2.getText().length() != 3) {
    		Alert alert = new Alert(AlertType.ERROR, "Los nombres tiene que ser de tres letras.");
         	alert.showAndWait();
    	} else {
    		
    	}
    }

    @FXML
	public void sonidoHandler(MouseEvent event) {
		if(singleton.soundOn) {
    		singleton.soundOn = false;
    		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
    	} else {
    		singleton.soundOn = true;
    	}
    	actualizarSonido();
    	actualizarImagenSonido();
    }   
	
	 public void actualizarSonido() {
	    	if(singleton.soundOn) {
	    		musicaFondo.getClip().setMicrosecondPosition(tiempoMusica);
	    		musicaFondo.playMusic();
	    	}
	    	else {
	    		musicaFondo.stopMusic();
	    	}
	    }
	    
	    public void actualizarImagenSonido() {
	        if(singleton.soundOn) {
	        	iconoSonido.setImage(Sound1);
	        } else {
	        	iconoSonido.setImage(Sound0);
	        }
	    }
    
    
    public void actualizarEstilo() {
    	String temaAzul = getClass().getResource("estiloAzul.css").toExternalForm();
    	String temaRojo = getClass().getResource("estiloRojo.css").toExternalForm();
    	String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
    	if(singleton.estilo.equals("Azul")) {
    		pane.getStylesheets().remove(temaRojo);
    		pane.getStylesheets().remove(temaVerde);
    		pane.getStylesheets().add(temaAzul);
	    } else if(singleton.estilo.equals("Rojo")) {
			pane.getStylesheets().remove(temaAzul);
			pane.getStylesheets().remove(temaVerde);
			pane.getStylesheets().add(temaRojo);
		} else {
			pane.getStylesheets().remove(temaAzul);
			pane.getStylesheets().remove(temaRojo);
			pane.getStylesheets().add(temaVerde);
		}
    }
    
    public void corregirTamanyoVentana() {
    	thisStage.setWidth(650);
    	thisStage.setHeight(750);
    }
    
    public void corregirPosicionVentana() {
    	thisStage.setX(singleton.posicionX);
    	thisStage.setY(singleton.posicionY);
    }
    
    public void anyadirIcono() {
        icon = new Image("/imagenes/Icon.png");
        thisStage.getIcons().add(icon);
    }
}
