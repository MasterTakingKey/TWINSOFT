package application;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ControladorMenuPause {
    
	@FXML
    private ImageView imagePlay;

    @FXML
    private ImageView imageHome;

    @FXML
    private ImageView imageSound;
    
    private Musica musicaFondo;
    
    private Stage primaryStage;
    
    private Stage thisStage;

    private boolean SoundOn;
    
    private ControladorPartida partidaEstandar;
    
    private ControladorPartidaCarta partidaCarta;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private Image icon;
    
    private long tiempoMusica;
    
    private String tipoPartida;
    
    Optional<ButtonType> resultadoSalida;
    
    void initDataPartidaEstandar(Stage partida, ControladorPartida partidaEstandar, boolean soundOn, double anteriorX, double anteriorY) {
    	primaryStage = partida;
    	this.partidaEstandar = partidaEstandar;
        SoundOn = soundOn;
        tipoPartida = "estandar";
        inicializarVariables();
        añadirIcono();
        corregirTamañoVentana();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamañoVentana();
        corregirPosicionVentana(anteriorX, anteriorY);
    }
    
    void initDataPartidaCarta(Stage partida, ControladorPartidaCarta partidaCarta, boolean soundOn, double anteriorX, double anteriorY) {
    	primaryStage = partida;
    	this.partidaCarta = partidaCarta;
        SoundOn = soundOn;
        tipoPartida = "carta";
        inicializarVariables();
        añadirIcono();
        corregirTamañoVentana();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamañoVentana();
        corregirPosicionVentana(anteriorX, anteriorY);
    }
  
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/Musica3.wav", 0L);
        thisStage = (Stage) imageSound.getScene().getWindow();
        thisStage.setTitle("Menú de Pausa");
    }
    
    public void añadirIcono() {
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
        stage.setTitle("Confirmación de salida");
        stage.setResizable(false);
        ControladorConfirmacionSalirMenuP controladorConfirmacionSalirMenuP = myLoader.<ControladorConfirmacionSalirMenuP>getController();
        controladorConfirmacionSalirMenuP.inicializarDatos(this, thisStage.getX(), thisStage.getY(), thisStage.getWidth(), thisStage.getHeight());
        stage.show();
    }
    	
    
    public void volverMenuPrincipal() {
    	musicaFondo.stopMusic();
    	Stage stage = (Stage) imageHome.getScene().getWindow();
    	stage.close();
    	primaryStage.close();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
            Parent root = myLoader.load();  
            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Menú Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            menuPrincipal.iniciarMenuPrincipal(primaryStage, SoundOn, false, thisStage.getX(), thisStage.getY());
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
	    	thisStage.close();
	    	partidaEstandar.reanudarPartida(SoundOn, thisStage.getX(), thisStage.getY());
    	}
    	
    }
    
    void reanudarPartidaCarta() {
    	musicaFondo.stopMusic();
    	boolean victoria = partidaCarta.isVictoria();
    	boolean derrota = partidaCarta.isDerrota();
    	if (!derrota && !victoria) {
	    	thisStage.close();
	    	partidaCarta.reanudarPartida(SoundOn, thisStage.getX(), thisStage.getY());
    	}
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
        	imageSound.setImage(Sound1);
        } else {
        	imageSound.setImage(Sound0);
        }
    }
    
    public void corregirTamañoVentana() {
    	thisStage.setWidth(900);
    	thisStage.setHeight(620);
    }
    
    public void corregirPosicionVentana(double anteriorX, double anteriorY) {
    	thisStage.setX(anteriorX);
    	thisStage.setY(anteriorY);
    }

}
