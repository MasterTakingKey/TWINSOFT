package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private ToggleButton buttonTiempo;

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
    private ToggleButton buttonMostrarCartas;

    @FXML
    private TextField textTiempoMostrarCartas;

    @FXML
    private Button buttonJugar;
    
    @FXML
    private ImageView iconoSonido;
    
    private boolean SoundOn;
    
    private Musica musicaFondo;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private long tiempoMusica;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private String estilo;
    
    private ArrayList<Baraja> listaBarajas;
    
    private Baraja barajaPartida;

    public void iniciarAjustesJLibre(Stage stage, boolean Sound, long tiempoM, double anteriorX, double anteriorY, String estilo, ArrayList<Baraja> lista, Baraja nuevaBaraja) {
    	primaryStage = stage;
    	SoundOn = Sound;
    	tiempoMusica = tiempoM;
    	listaBarajas = lista;
    	barajaPartida = nuevaBaraja;
    	inicializarVariables();
    	actualizarSonido();
    	actualizarImagenSonido();
    	corregirTamanyoVentana();
    	corregirPosicionVentana(anteriorX, anteriorY);
    	actualizarEstilo(estilo);
    }
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/Musica2.wav", 0L);
        thisStage = (Stage) buttonJugar.getScene().getWindow();
    }
    
    @FXML
    void clickSound(MouseEvent event) {
    	if(SoundOn) {
    		SoundOn = false;
    		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
    	} else {
    		SoundOn = true;
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
      		controladorPartidaLibre.iniciarPartidaLibre(primaryStage, SoundOn, thisStage.getX(), thisStage.getY(), filas, columnas, estilo, listaBarajas, barajaPartida);
      		primaryStage.show();
      	} catch (IOException e) {}
    }
    
    public void actualizarSonido() {
    	if(SoundOn) {
    		musicaFondo.getClip().setMicrosecondPosition(tiempoMusica);
    		musicaFondo.playMusic();
    	}
    	else {
    		musicaFondo.stopMusic();
    	}
    }
    
    public void actualizarImagenSonido() {
        if(SoundOn) {
        	iconoSonido.setImage(Sound1);
        } else {
        	iconoSonido.setImage(Sound0);
        }
    }
    
    public void corregirTamanyoVentana() {
    	thisStage.setWidth(900);
    	thisStage.setHeight(620);
    }
    
    public void corregirPosicionVentana(double anteriorX, double anteriorY) {
    	thisStage.setX(anteriorX);
    	thisStage.setY(anteriorY);
    }
    
    public void actualizarEstilo(String nuevoEstilo) {
    	estilo = nuevoEstilo;
    	String temaAzul = getClass().getResource("estiloAzul.css").toExternalForm();
        String temaRojo = getClass().getResource("estiloRojo.css").toExternalForm();
        String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
    	if(estilo.equals("Azul")) {
    		stackPane.getStylesheets().remove(temaRojo);
    		stackPane.getStylesheets().remove(temaVerde);
    		stackPane.getStylesheets().add(temaAzul);
    		circuloSonido.getStylesheets().remove(temaRojo);
    		circuloSonido.getStylesheets().remove(temaVerde);
    		circuloSonido.getStylesheets().add(temaAzul);
    	} else if(estilo.equals("Rojo")) {
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
