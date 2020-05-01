package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class ControladorMenuPrincipal {


    @FXML
    private Button jugar;
    
    @FXML
    private Button ajustes;

    @FXML
    private Button partidaRapida;

    @FXML
    private Button modoLibre;

    @FXML
    private Button porCarta;
    
    @FXML
    private Button salir;
    
    @FXML
    private Button volverAtras;
    
    @FXML
    private ImageView iconoSonido;
    
    private Stage primaryStage;
    
    private Musica musicaFondo;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private boolean SoundOn;
    
    private long tiempoMusica;

    public void iniciarMenuPrincipal(Stage stage, boolean soundOn){
        primaryStage = stage;
        SoundOn = soundOn;
        inicializarVariables();
		actualizarSonido();
        actualizarImagenSonido();
        muestraMenuP(true);
    }
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/Musica2.wav", 0L);
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
    
    public void muestraMenuP(boolean b) {
    	jugar.setVisible(b);
    	ajustes.setVisible(b);
    	salir.setVisible(b);
    	partidaRapida.setVisible(!b);
    	modoLibre.setVisible(!b);
    	porCarta.setVisible(!b);
    	volverAtras.setVisible(!b);
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
    	muestraMenuP(false);
    	
    }
    
    @FXML
    void ajustesHandler(ActionEvent event) {

    }
    
    @FXML
    void modoLibreHandler(ActionEvent event) {

    }

    @FXML
    void partidaRapidaHandler(ActionEvent event) throws IOException {
    	musicaFondo.stopMusic();
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/partida.fxml"));
        Parent root = (Parent) myLoader.load();
        ControladorPartida controladorPartida = myLoader.<ControladorPartida>getController();
        controladorPartida.iniciarPartida(primaryStage, SoundOn);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Partida Estándar");
        primaryStage.setResizable(false);
        primaryStage.show();
        centrarVentana();
    }

    @FXML
    void porCartaHandler(ActionEvent event) {
    	
    }

    
    @FXML
    void salirHandler(ActionEvent event) throws IOException {
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/ConfirmacionSalirApp.fxml"));
        Parent root = (Parent) myLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirmación de salida");
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    void volverAtrasHandler(ActionEvent event) {
    	muestraMenuP(true);
    }


    public void centrarVentana() {
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screen.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((screen.getHeight() - primaryStage.getHeight()) / 2);
    }
    
}
