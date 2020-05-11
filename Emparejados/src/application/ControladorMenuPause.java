package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorMenuPause {

    @FXML
    private Pane pane;
    
    @FXML
    private StackPane circuloHome;

    @FXML
    private StackPane circuloPlay;

    @FXML
    private StackPane circuloSonido;
	
	@FXML
    private ImageView imagePlay;

    @FXML
    private ImageView imageHome;

    @FXML
    private ImageView imageSound;
    
    private Musica musicaFondo;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private ControladorPartidaEstandar partidaEstandar;
    
    private ControladorPartidaCarta partidaCarta;
    
    private ControladorPartidaLibre partidaLibre;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private Image icon;
    
    private long tiempoMusica;
    
    private String tipoPartida;
    
    private Optional<ButtonType> resultadoSalida;
    
    private Singleton singleton;
    
    void initDataPartidaEstandar(Stage partida, ControladorPartidaEstandar partidaEstandar, Singleton nuevoSingleton) {
    	primaryStage = partida;
    	this.partidaEstandar = partidaEstandar;
    	singleton = nuevoSingleton;
        tipoPartida = "estandar";
        inicializarVariables();
        anyadirIcono();
        corregirTamanyoVentana();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana();
        actualizarEstilo(singleton.estilo);
    }
    
    void initDataPartidaCarta(Stage partida, ControladorPartidaCarta partidaCarta, Singleton nuevoSingleton) {
    	primaryStage = partida;
    	this.partidaCarta = partidaCarta;
        singleton = nuevoSingleton;
        tipoPartida = "carta";
        inicializarVariables();
        anyadirIcono();
        corregirTamanyoVentana();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana();
        actualizarEstilo(singleton.estilo);
    }
  
    void initDataPartidaLibre(Stage partida, ControladorPartidaLibre partidaLibre, Singleton nuevoSingleton) {
    	primaryStage = partida;
    	this.partidaLibre = partidaLibre;
        singleton = nuevoSingleton;
        tipoPartida = "libre";
        inicializarVariables();
        anyadirIcono();
        corregirTamanyoVentana();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana();
        actualizarEstilo(singleton.estilo);
    }
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/"+ singleton.listaMusica[2] +".wav", 0L);
        thisStage = (Stage) imageSound.getScene().getWindow();
        thisStage.setTitle("Menu de Pausa");
    }
    
    public void anyadirIcono() {
        icon = new Image("/imagenes/Icon.png");
        thisStage.getIcons().add(icon);
    }
    
    @FXML
    void clickHome(MouseEvent event) throws IOException {
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/ConfirmacionSalirMenuP.fxml"));
        Parent root = (Parent) myLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirmacion de salida");
        stage.setResizable(false);
        ControladorConfirmacionSalirMenuP controladorConfirmacionSalirMenuP = myLoader.<ControladorConfirmacionSalirMenuP>getController();
        singleton.posicionX = thisStage.getX();
  		singleton.posicionY = thisStage.getY();
        controladorConfirmacionSalirMenuP.inicializarDatos(this, thisStage.getWidth(), thisStage.getHeight(), singleton);
        stage.show();
    }
    	
    
    public void volverMenuPrincipal() {
    	musicaFondo.stopMusic();
    	thisStage.close();
    	primaryStage.close();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
            Parent root = myLoader.load();  
            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Menu Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
            menuPrincipal.iniciarMenuPrincipal(primaryStage, false, singleton);
            primaryStage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }
    }

    @FXML
    void clickPlay(MouseEvent event) throws IOException {
    	if(tipoPartida == "estandar") {
        	reanudarPartidaEstandar();
    	} else if(tipoPartida == "carta") {
    		reanudarPartidaCarta();
    	}
    }
    
    void reanudarPartidaEstandar() {
    	musicaFondo.stopMusic();
    	boolean victoria = partidaEstandar.isVictoria();
    	boolean derrota = partidaEstandar.isDerrota();
    	if (!derrota && !victoria) {
    		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
	    	thisStage.close();
	    	partidaEstandar.reanudarPartida(singleton.soundOn);
    	}
    	
    }
    
    void reanudarPartidaCarta() {
    	musicaFondo.stopMusic();
    	boolean victoria = partidaCarta.isVictoria();
    	boolean derrota = partidaCarta.isDerrota();
    	if (!derrota && !victoria) {
    		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
	    	thisStage.close();
	    	partidaCarta.reanudarPartida(singleton.soundOn);
    	}
    }
    
    void reanudarPartidaLibre() {
    	musicaFondo.stopMusic();
    	boolean victoria = partidaLibre.isVictoria();
    	boolean derrota = partidaLibre.isDerrota();
    	if (!derrota && !victoria) {
    		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
	    	thisStage.close();
	    	partidaLibre.reanudarPartida(singleton.soundOn);
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
        	imageSound.setImage(Sound1);
        } else {
        	imageSound.setImage(Sound0);
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
    
    public void actualizarEstilo(String nuevoEstilo) {
    	singleton.estilo = nuevoEstilo;
    	String temaAzul = getClass().getResource("estiloAzul.css").toExternalForm();
        String temaRojo = getClass().getResource("estiloRojo.css").toExternalForm();
        String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
    	if(singleton.estilo.equals("Azul")) {
    		pane.getStylesheets().remove(temaRojo);
    		pane.getStylesheets().remove(temaVerde);
    		pane.getStylesheets().add(temaAzul);
    		circuloHome.getStylesheets().remove(temaRojo);
    		circuloHome.getStylesheets().remove(temaVerde);
    		circuloHome.getStylesheets().add(temaAzul);
    		circuloPlay.getStylesheets().remove(temaRojo);
    		circuloPlay.getStylesheets().remove(temaVerde);
    		circuloPlay.getStylesheets().add(temaAzul);
    		circuloSonido.getStylesheets().remove(temaRojo);
    		circuloSonido.getStylesheets().remove(temaVerde);
    		circuloSonido.getStylesheets().add(temaAzul);
    	} else if(singleton.estilo.equals("Rojo")) {
    		pane.getStylesheets().remove(temaAzul);
			pane.getStylesheets().remove(temaVerde);
			pane.getStylesheets().add(temaRojo);
			circuloHome.getStylesheets().remove(temaAzul);
			circuloHome.getStylesheets().remove(temaVerde);
			circuloHome.getStylesheets().add(temaRojo);
			circuloPlay.getStylesheets().remove(temaAzul);
			circuloPlay.getStylesheets().remove(temaVerde);
			circuloPlay.getStylesheets().add(temaRojo);
			circuloSonido.getStylesheets().remove(temaAzul);
			circuloSonido.getStylesheets().remove(temaVerde);
			circuloSonido.getStylesheets().add(temaRojo);
    	} else {
    		pane.getStylesheets().remove(temaAzul);
			pane.getStylesheets().remove(temaRojo);
			pane.getStylesheets().add(temaVerde);
			circuloHome.getStylesheets().remove(temaAzul);
			circuloHome.getStylesheets().remove(temaRojo);
			circuloHome.getStylesheets().add(temaVerde);
			circuloPlay.getStylesheets().remove(temaAzul);
			circuloPlay.getStylesheets().remove(temaRojo);
			circuloPlay.getStylesheets().add(temaVerde);
			circuloSonido.getStylesheets().remove(temaAzul);
			circuloSonido.getStylesheets().remove(temaRojo);
			circuloSonido.getStylesheets().add(temaVerde);
    	}
    }

}
