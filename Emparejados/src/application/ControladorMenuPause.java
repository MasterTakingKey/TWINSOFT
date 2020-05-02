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

    private boolean SoundOn;
    
    private ControladorPartida partidaEstandar;
    
    private ControladorPartidaCarta partidaCarta;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private Image icon;
    
    private long tiempoMusica;
    
    private String tipoPartida;
    
    Optional<ButtonType> resultadoSalida;
    
    void initDataPartidaEstandar(Stage partida, ControladorPartida partidaEstandar, boolean soundOn) {
    	primaryStage = partida;
    	this.partidaEstandar = partidaEstandar;
        SoundOn = soundOn;
        tipoPartida = "estandar";
        inicializarVariables();
		actualizarSonido();
        actualizarImagenSonido();
    }
    
    void initDataPartidaCarta(Stage partida, ControladorPartidaCarta partidaCarta, boolean soundOn) {
    	primaryStage = partida;
    	this.partidaCarta = partidaCarta;
        SoundOn = soundOn;
        tipoPartida = "carta";
        inicializarVariables();
		actualizarSonido();
        actualizarImagenSonido();
    }
  
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/Musica3.wav", 0L);
        icon = new Image("/imagenes/Icon.png");
        Stage stage = (Stage) imageSound.getScene().getWindow();
        stage.getIcons().add(icon);
        stage.setTitle("Men� de Pausa");
    }
    
    @FXML
    void clickHome(MouseEvent event) throws IOException {
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/ConfirmacionSalirMenuP.fxml"));
        Parent root = (Parent) myLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirmaci�n de salida");
        stage.setResizable(false);
        ControladorConfirmacionSalirMenuP controladorConfirmacionSalirMenuP = myLoader.<ControladorConfirmacionSalirMenuP>getController();
        controladorConfirmacionSalirMenuP.inicializarDatos(this);
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
            menuPrincipal.iniciarMenuPrincipal(primaryStage, SoundOn);
            Scene scene = new Scene(root, 882, 611);
            primaryStage.setTitle("Men� Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            centrarVentana();
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
	    	Stage stage = (Stage) imagePlay.getScene().getWindow();
	    	stage.close();
	    	partidaEstandar.reanudarPartida(SoundOn, stage.getX(), stage.getY());
    	}
    	
    }
    
    void reanudarPartidaCarta() {
    	musicaFondo.stopMusic();
    	boolean victoria = partidaCarta.isVictoria();
    	boolean derrota = partidaCarta.isDerrota();
    	if (!derrota && !victoria) {
	    	Stage stage = (Stage) imagePlay.getScene().getWindow();
	    	stage.close();
	    	partidaCarta.reanudarPartida(SoundOn, stage.getX(), stage.getY());
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

    public void centrarVentana() {
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screen.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((screen.getHeight() - primaryStage.getHeight()) / 2);
    }
    
}
