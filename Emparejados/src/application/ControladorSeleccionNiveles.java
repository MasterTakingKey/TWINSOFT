package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorSeleccionNiveles {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private StackPane circuloSonido;

    @FXML
    private ImageView iconoSonido;

    @FXML
    private Button volver;

    @FXML
    private Button nivel1;

    @FXML
    private Button nivel2;

    @FXML
    private Button nivel3;

    @FXML
    private Button nivel4;

    @FXML
    private Button nivel5;

    @FXML
    private Button nivel6;

    @FXML
    private Button nivel7;

    @FXML
    private Button nivel8;

    @FXML
    private Button nivel9;

    @FXML
    private Button nivel10;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Musica musicaFondo;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private long tiempoMusica;
    
    private Singleton singleton;
    
    public void iniciarSeleccionNiveles(Stage stage, Singleton nuevoSingleton){
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
        musicaFondo = new Musica("src/sonidos/"+ singleton.listaMusica[2] +".wav", 0L);
    	thisStage = (Stage) nivel1.getScene().getWindow();
    }
    
    @FXML
    void nivel1Handler(ActionEvent event) {

    }

    @FXML
    void nivel2Handler(ActionEvent event) {

    }

    @FXML
    void nivel3Handler(ActionEvent event) {

    }

    @FXML
    void nivel4Handler(ActionEvent event) {

    }

    @FXML
    void nivel5Handler(ActionEvent event) {

    }

    @FXML
    void nivel6Handler(ActionEvent event) {

    }

    @FXML
    void nivel7Handler(ActionEvent event) {

    }

    @FXML
    void nivel8Handler(ActionEvent event) {

    }

    @FXML
    void nivel9Handler(ActionEvent event) {

    }

    @FXML
    void nivel10Handler(ActionEvent event) {

    }
    
    @FXML
    void volverHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
            Parent root = myLoader.load();  
            Scene scene = new Scene(root);                        
            primaryStage.setTitle("Menu Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController(); 
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
            menuPrincipal.iniciarMenuPrincipal(primaryStage, false, singleton, "seleccionNiveles");
            primaryStage.show();
    	} catch (IOException e) {}
    }

    @FXML
    void sonidoHandler(MouseEvent event) {
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

    public void corregirTamanyoVentana() {
    	thisStage.setWidth(1050);
    	thisStage.setHeight(700);
    }
    
    public void corregirPosicionVentana() {
    	thisStage.setX(singleton.posicionX);
    	thisStage.setY(singleton.posicionY + 100);
    }
    
    public void actualizarEstilo() {
    	String temaAzul = getClass().getResource("estiloAzul.css").toExternalForm();
        String temaRojo = getClass().getResource("estiloRojo.css").toExternalForm();
        String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
    	if(singleton.estilo.equals("Azul")) {
    		anchorPane.getStylesheets().remove(temaRojo);
    		anchorPane.getStylesheets().remove(temaVerde);
    		anchorPane.getStylesheets().add(temaAzul);
    		circuloSonido.getStylesheets().remove(temaRojo);
    		circuloSonido.getStylesheets().remove(temaVerde);
    		circuloSonido.getStylesheets().add(temaAzul);
    	} else if(singleton.estilo.equals("Rojo")) {
    		anchorPane.getStylesheets().remove(temaAzul);
			anchorPane.getStylesheets().remove(temaVerde);
			anchorPane.getStylesheets().add(temaRojo);
			circuloSonido.getStylesheets().remove(temaAzul);
			circuloSonido.getStylesheets().remove(temaVerde);
			circuloSonido.getStylesheets().add(temaRojo);
    	} else {
    		anchorPane.getStylesheets().remove(temaAzul);
			anchorPane.getStylesheets().remove(temaRojo);
			anchorPane.getStylesheets().add(temaVerde);
			circuloSonido.getStylesheets().remove(temaAzul);
			circuloSonido.getStylesheets().remove(temaRojo);
			circuloSonido.getStylesheets().add(temaVerde);
    	}
    }

}
