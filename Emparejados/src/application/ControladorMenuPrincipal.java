package application;

import java.io.IOException;
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

public class ControladorMenuPrincipal {


    @FXML
    private Button jugar;
    
    @FXML
    private Button ajustes;

    @FXML
    private Button partidaEstandar;

    @FXML
    private Button modoLibre;

    @FXML
    private Button partidaCarta;
    
    @FXML
    private Button salir;
    
    @FXML
    private Button volverAtras;
    
    @FXML
    private ImageView iconoSonido;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Musica musicaFondo;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private boolean SoundOn;
    
    private long tiempoMusica;

    public void iniciarMenuPrincipal(Stage stage, boolean soundOn, boolean primeraVez, double anteriorX, double anteriorY){
        primaryStage = stage;
        SoundOn = soundOn;
        inicializarVariables();
		actualizarSonido();
        actualizarImagenSonido();
        muestraMenuP(true);
        corregirTamanyoVentana();
        if(primeraVez) { 
            centrarVentana();
        } else {
            corregirPosicionVentana(anteriorX, anteriorY);
        }
    }
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/Musica2.wav", 0L);
        thisStage = (Stage) salir.getScene().getWindow();
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
    	partidaEstandar.setVisible(!b);
    	modoLibre.setVisible(!b);
    	partidaCarta.setVisible(!b);
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
    void ajustesHandler(ActionEvent event) throws IOException {
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuAjustes.fxml"));
        Parent root = myLoader.load();  
        ControladorMenuAjustes menuPrincipal = myLoader.<ControladorMenuAjustes>getController();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Menu Ajustes");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        
        Image icono = new Image("/imagenes/Icon.png");
        primaryStage.getIcons().add(icono);
        
        menuPrincipal.iniciarMenuAjustes(primaryStage, true, true, 0, 0);
        primaryStage.show();
    }
    
    @FXML
    void modoLibreHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
    	tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
    	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/AjustesJuegoLibre.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorAjustesJuegoLibre controladorAjustesJLibre = myLoader.<ControladorAjustesJuegoLibre>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Ajustes del modo libre");
      		primaryStage.setResizable(false);
      		controladorAjustesJLibre.iniciarAjustesJLibre(primaryStage, SoundOn, tiempoMusica);
      		primaryStage.show();
      	} catch (IOException e) {
      		e.printStackTrace();
      	}
    }

    @FXML
    void partidaEstandarHandler(ActionEvent event) {
      	musicaFondo.stopMusic();
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/partida.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartida controladorPartida = myLoader.<ControladorPartida>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Partida Estandar");
      		primaryStage.setResizable(false);
      		controladorPartida.iniciarPartidaEstandar(primaryStage, SoundOn, thisStage.getX(), thisStage.getY());
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void partidaCartaHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaCarta.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaCarta controladorPartidaCarta = myLoader.<ControladorPartidaCarta>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Partida Por Carta");
      		primaryStage.setResizable(false);
      		controladorPartidaCarta.iniciarPartidaCarta(primaryStage, SoundOn, thisStage.getX(), thisStage.getY());
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    
    @FXML
    void salirHandler(ActionEvent event) throws IOException {
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/ConfirmacionSalirApp.fxml"));
        Parent root = (Parent) myLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirmacion de salida");
        stage.setResizable(false);
        ControladorConfirmacionSalirApp controladorConfirmacionSalirApp = myLoader.<ControladorConfirmacionSalirApp>getController();
        controladorConfirmacionSalirApp.inicializarDatos(thisStage.getX(), thisStage.getY(), thisStage.getWidth(), thisStage.getHeight());
        stage.show();
    }
    
    @FXML
    void volverAtrasHandler(ActionEvent event) {
    	muestraMenuP(true);
    }

    public void corregirTamanyoVentana() {
    	thisStage.setWidth(900);
    	thisStage.setHeight(650);
    }
    
    public void corregirPosicionVentana(double anteriorX, double anteriorY) {
    	thisStage.setX(anteriorX);
    	thisStage.setY(anteriorY);
    }
    
    public void centrarVentana() {  
       Rectangle2D screen = Screen.getPrimary().getVisualBounds();
       primaryStage.setX((screen.getWidth() - primaryStage.getWidth()) / 2);
       primaryStage.setY((screen.getHeight() - primaryStage.getHeight()) / 2);
    }
    
}
