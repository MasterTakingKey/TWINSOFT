package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private StackPane fondo;
    
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
    		musicaFondo.stopMusic();
          	try {
          		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/Multijugador.fxml"));
          		Parent root = (Parent) myLoader.load();
          		ControladorMultijugador controladorMulti = myLoader.<ControladorMultijugador>getController();
          		Scene scene = new Scene(root);
          		primaryStage.setScene(scene);
          		primaryStage.setTitle("Partida Multijugador");
          		primaryStage.setResizable(false);
          		singleton.posicionX = thisStage.getX();
          		singleton.posicionY = thisStage.getY();
          		controladorMulti.iniciarMultijugador(primaryStage, singleton, "menuPrincipal", false, 0, nombreJ1.getText().toUpperCase(), nombreJ2.getText().toUpperCase());
          		PlantillaPartidas plantillaPartida = controladorMulti;
          		plantillaPartida.ventana = "menuPrincipal";
          		plantillaPartida.InicializarPartida();
          		primaryStage.show();
          	} catch (IOException e) {}
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
	    
	    @FXML
	    void clickSound(MouseEvent event) {
	    	if(singleton.soundOn) {
	    		singleton.soundOn = false;
	    		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
	    	} else {
	    		singleton.soundOn = true;
	    	}
	    	actualizarSonido();
	    	actualizarImagenSonido();
	    }
    
    public void actualizarEstilo() {
    	String temaAzul = getClass().getResource("estiloAzul.css").toExternalForm();
    	String temaRojo = getClass().getResource("estiloRojo.css").toExternalForm();
    	String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
    	if(singleton.estilo.equals("Azul")) {
    		fondo.getStylesheets().remove(temaRojo);
    		fondo.getStylesheets().remove(temaVerde);
    		fondo.getStylesheets().add(temaAzul);
    		circuloSonido.getStylesheets().remove(temaRojo);
    		circuloSonido.getStylesheets().remove(temaVerde);
    		circuloSonido.getStylesheets().add(temaAzul);
	    } else if(singleton.estilo.equals("Rojo")) {
			fondo.getStylesheets().remove(temaAzul);
			fondo.getStylesheets().remove(temaVerde);
			fondo.getStylesheets().add(temaRojo);
			circuloSonido.getStylesheets().remove(temaAzul);
			circuloSonido.getStylesheets().remove(temaVerde);
			circuloSonido.getStylesheets().add(temaRojo);
		} else {
			fondo.getStylesheets().remove(temaAzul);
			fondo.getStylesheets().remove(temaRojo);
			fondo.getStylesheets().add(temaVerde);
			circuloSonido.getStylesheets().remove(temaAzul);
			circuloSonido.getStylesheets().remove(temaRojo);
			circuloSonido.getStylesheets().add(temaVerde);
		}
    }
    
    public void corregirTamanyoVentana() {
    	thisStage.setWidth(600);
    	thisStage.setHeight(425);
    }
    
    public void corregirPosicionVentana() {
    	thisStage.setX(singleton.posicionX + 250);
    	thisStage.setY(singleton.posicionY + 200);
    }
    
    public void anyadirIcono() {
        icon = new Image("/imagenes/Icon.png");
        thisStage.getIcons().add(icon);
    }
}
