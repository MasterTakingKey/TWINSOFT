package application;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    
    private ControladorPartida cPartida;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private long tiempoMusica;
    
    private Alert confirmacionSalida;
    
    Optional<ButtonType> resultadoSalida;
    
    void initData(Stage partida, boolean soundOn) {
    	primaryStage = partida;
        SoundOn = soundOn;
        inicializarVariables();
		actualizarSonido();
        actualizarImagenSonido();
    }
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/Musica3.wav", 0L);
    }
    
    public void mostrarAlerta() {
        confirmacionSalida = new Alert(AlertType.CONFIRMATION);
        confirmacionSalida.setTitle("Confirmación");
        confirmacionSalida.setHeaderText("¿Estás seguro de que quieres volver al menú principal?");
        
        ButtonType aceptar = new ButtonType("Aceptar", ButtonData.OK_DONE);    
        ButtonType cancelar = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
        confirmacionSalida.getButtonTypes().setAll(aceptar, cancelar);
        
        Optional<ButtonType> resultadoSalida = confirmacionSalida.showAndWait();
        if (resultadoSalida.get() == aceptar){
        	volverMenuPrincipal();
        }
    }
    
    @FXML
    void clickHome(MouseEvent event) {
    	mostrarAlerta();
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
            Scene scene = new Scene(root);
            primaryStage.setTitle("TWINS by Twinsoft");
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
    	reanudar();
    }
    
    void reanudar() {
    	musicaFondo.stopMusic();
    	boolean victoria = cPartida.isVictoria();
    	boolean derrota = cPartida.isDerrota();
    	if (!derrota && !victoria) {
	    	Stage stage = (Stage) imagePlay.getScene().getWindow();
	    	stage.close();
	    	cPartida.reanudarPartida(SoundOn);
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
    
    public void setControladorPartida(ControladorPartida partida) {
    	this.cPartida = partida;
    }

    public void centrarVentana() {
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screen.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((screen.getHeight() - primaryStage.getHeight()) / 2);
    }
    
}
