package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.sound.sampled.Clip;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
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
    private ChoiceBox<String> choiceSonoroCarta;

    @FXML
    private ChoiceBox<String> choiceSonoroPareja;

    @FXML
    private ChoiceBox<String> choiceVisualCarta;

    @FXML
    private ChoiceBox<String> choiceVisualPareja;

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
    
    private boolean tiempoOn;
    
    private int tiempoPartida;
    
    private boolean mostrarCartas;
    
    private int tiempoMostrarCartas;
    
    private AudioClip voltearCarta;

    public void iniciarAjustesJLibre(Stage stage, long tiempoM, Singleton nuevoSingleton) {
    	primaryStage = stage;
    	tiempoMusica = tiempoM;
    	singleton = nuevoSingleton;
    	tiempoOn = false;
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
        ArrayList<String> clipsVoltear = new ArrayList<String>();
        clipsVoltear.add("Voltear");
        clipsVoltear.add("Voltear2");
        ObservableList<String> audioVoltear = FXCollections.observableArrayList(clipsVoltear); 
        choiceSonoroCarta.setItems(audioVoltear);
        choiceSonoroCarta.setValue("Voltear");
        ArrayList<String> clipsPareja = new ArrayList<String>();
        clipsPareja.add("Acierto");
        clipsPareja.add("Acierto2");
        ObservableList<String> audioPareja = FXCollections.observableArrayList(clipsPareja); 
        choiceSonoroPareja.setItems(audioPareja);
        choiceSonoroPareja.setValue("Acierto");
        ArrayList<String> animacionVoltear = new ArrayList<String>();
        animacionVoltear.add("Giro");
        ObservableList<String> animacionCarta = FXCollections.observableArrayList(animacionVoltear); 
        choiceVisualCarta.setItems(animacionCarta);
        choiceVisualCarta.setValue("Giro");
        ArrayList<String> animacionPareja = new ArrayList<String>();
        animacionPareja.add("Salto");
        animacionPareja.add("Salto doble");
        ObservableList<String> animacionCorrecta = FXCollections.observableArrayList(animacionPareja); 
        choiceVisualPareja.setItems(animacionCorrecta);
        choiceVisualPareja.setValue("Salto");
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
    	try {
      		int filas = Integer.parseInt(textFilas.getText());
    	    int columnas = Integer.parseInt(textColumnas.getText());
    	    int cartas = filas*columnas;
    	    if(tiempoOn) {
    	    	tiempoPartida = Integer.parseInt(textTiempoPartida.getText());
    	    }
    	    if(mostrarCartas) {
    	    	tiempoMostrarCartas = Integer.parseInt(textTiempoMostrarCartas.getText());
    	    }
    	    if(cartas%2 != 0) {
    	    	Alert alert = new Alert(AlertType.ERROR, "El número total de cartas (filas x columnas) debe ser par.");
            	alert.showAndWait();
    	    } else if(filas > 6 || columnas > 6) {
    	    	Alert alert = new Alert(AlertType.ERROR, "El número máximo tanto de filas como columnas es de 6.");
            	alert.showAndWait();
            	
    	    }else if (cartas > singleton.barajaPartida.getTamanyo()) {
    	    	Alert alert = new Alert(AlertType.ERROR, "La baraja seleccionada no es lo suficientemente grande para este tablero.");
            	alert.showAndWait();
    	    } else {
    	    	musicaFondo.stopMusic();
	      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaLibre.fxml"));
	      		Parent root = (Parent) myLoader.load();
	      		ControladorPartidaLibre controladorPartidaLibre = myLoader.<ControladorPartidaLibre>getController();
	      		Scene scene = new Scene(root);
	      		primaryStage.setScene(scene);
	      		primaryStage.setTitle("Partida Estandar");
	      		primaryStage.setResizable(false);
	      		singleton.posicionX = thisStage.getX();
	      		singleton.posicionY = thisStage.getY();
	      		controladorPartidaLibre.iniciarPartidaLibre(primaryStage, filas, columnas, singleton, tiempoOn, tiempoPartida, mostrarCartas, tiempoMostrarCartas, choiceSonoroCarta.getValue(), choiceSonoroPareja.getValue(), choiceVisualCarta.getValue(), choiceVisualPareja.getValue());
	      		primaryStage.show();
    	    }
      	} catch (NumberFormatException nfe) {
      		Alert alert = new Alert(AlertType.ERROR, "Asegúrate de introducir números en los campos correspondientes.");
        	alert.showAndWait();
      	} catch (IOException e) {}
    }
    
    @FXML
    void handlerButtonMostrarCartas(ActionEvent event) {
    	if(mostrarCartas) {
    		buttonMostrarCartas.setText("Desactivado");
    		mostrarCartas = false;
    	} else {
    		buttonMostrarCartas.setText("Activado");
    		mostrarCartas = true;
    	}
    }

    @FXML
    void handlerButtonTiempo(ActionEvent event) {
    	if(tiempoOn) {
    		buttonTiempo.setText("Desactivado");
    		tiempoOn = false;
    	} else {
    		buttonTiempo.setText("Activado");
    		tiempoOn = true;
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
