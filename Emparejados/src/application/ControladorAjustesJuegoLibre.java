package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ControladorAjustesJuegoLibre {

    @FXML
    private StackPane stackPane;
    
    @FXML
    private StackPane circuloSonido;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<?> barajaChoice;

    @FXML
    private TextField textFilas;

    @FXML
    private TextField textColumnas;

    @FXML
    private Button buttonTiempo;

    @FXML
    private TextField textTiempoPartida;

    @FXML
    private ChoiceBox<?> choiceSonoroCarta;

    @FXML
    private ChoiceBox<?> choiceSonoroPareja;

    @FXML
    private ChoiceBox<?> choiceVisualCarta;

    @FXML
    private ChoiceBox<?> choiceVisualPareja;

    @FXML
    private Button buttonMostrarCartas;

    @FXML
    private TextField textTiempoMostrarCartas;

    @FXML
    private Button buttonJugar;
    
    @FXML
    private ImageView iconoSonido;
    
    private Musica musicaFondo;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private long tiempoMusica;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Singleton singleton;

    public void iniciarAjustesJLibre(Stage stage, long tiempoM, Singleton nuevoSingleton) {
    	primaryStage = stage;
    	tiempoMusica = tiempoM;
    	singleton = nuevoSingleton;
    	singleton.tiempoOn = false;
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
    	musicaFondo = new Musica("src/sonidos/"+ singleton.listaMusica[1] +".wav", 0L);
        thisStage = (Stage) buttonJugar.getScene().getWindow();
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
    
    @FXML
    void jugarHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
    	int filas = Integer.parseInt(textFilas.getText());
    	int columnas = Integer.parseInt(textColumnas.getText());
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaLibre.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaLibre controladorPartidaLibre = myLoader.<ControladorPartidaLibre>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Partida Estandar");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartidaLibre.iniciarPartidaLibre(primaryStage, filas, columnas, singleton);
      		primaryStage.show();
      	} catch (IOException e) {}
    }
    
    @FXML
    void handlerButtonMostrarCartas(ActionEvent event) {

    }

    @FXML
    void handlerButtonTiempo(ActionEvent event) {
    	if(singleton.tiempoOn) {
    		buttonTiempo.setText("Desactivado");
    		singleton.tiempoOn = false;
    	} else {
    		buttonTiempo.setText("Activado");
    		singleton.tiempoOn = true;
    	}
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
    
    public void corregirTamanyoVentana() {
    	thisStage.setWidth(900);
    	thisStage.setHeight(620);
    }
    
    public void corregirPosicionVentana() {
    	thisStage.setX(singleton.posicionX);
    	thisStage.setY(singleton.posicionY);
    }
    
    public void actualizarEstilo() {
    	String temaAzul = getClass().getResource("estiloAzul.css").toExternalForm();
        String temaRojo = getClass().getResource("estiloRojo.css").toExternalForm();
        String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
    	if(singleton.estilo.equals("Azul")) {
    		stackPane.getStylesheets().remove(temaRojo);
    		stackPane.getStylesheets().remove(temaVerde);
    		stackPane.getStylesheets().add(temaAzul);
    		circuloSonido.getStylesheets().remove(temaRojo);
    		circuloSonido.getStylesheets().remove(temaVerde);
    		circuloSonido.getStylesheets().add(temaAzul);
    	} else if(singleton.estilo.equals("Rojo")) {
    		stackPane.getStylesheets().remove(temaAzul);
    		stackPane.getStylesheets().remove(temaVerde);
    		stackPane.getStylesheets().add(temaRojo);
    		circuloSonido.getStylesheets().remove(temaAzul);
    		circuloSonido.getStylesheets().remove(temaVerde);
    		circuloSonido.getStylesheets().add(temaRojo);
    	} else {
    		stackPane.getStylesheets().remove(temaAzul);
    		stackPane.getStylesheets().remove(temaRojo);
    		stackPane.getStylesheets().add(temaVerde);
    		circuloSonido.getStylesheets().remove(temaAzul);
    		circuloSonido.getStylesheets().remove(temaRojo);
    		circuloSonido.getStylesheets().add(temaVerde);
    	}
    }
}
