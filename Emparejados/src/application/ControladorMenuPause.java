package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControladorMenuPause {

    @FXML
    private ImageView imagePlay;

    @FXML
    private ImageView imageRetry;

    @FXML
    private ImageView imageClose;

    @FXML
    private ImageView imageSound;
    
    private Musica musicaFondo;
    
    private Stage partidaStage;

    private boolean SoundOn;
    
    private ControladorPartida cPartida;
    
    Image Sound0 = new Image("/imagenes/sonido_off.png");
    
    Image Sound1 = new Image("/imagenes/sonido_on.png");
    
    void initData(Stage partida, boolean soundOn) {
    	partidaStage = partida;
        SoundOn = soundOn;
        if(SoundOn) imageSound.setImage(Sound1);
        else imageSound.setImage(Sound0);
        musicaFondo = new Musica("src/sonidos/Musica3.wav", 0L);
		musicaFondo.playMusic();
    }
    
    @FXML
    void clickClose(MouseEvent event) {
    	musicaFondo.stopMusic();
    	Stage stage = (Stage) imageClose.getScene().getWindow();
    	stage.close();
    	partidaStage.close();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
            Parent root = myLoader.load();  
            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
            menuPrincipal.iniciarMenuPrincipal(partidaStage);
            Scene scene = new Scene(root);
            partidaStage.setTitle("TWINS by Twinsoft");
            partidaStage.setScene(scene);
            partidaStage.setResizable(false);
            partidaStage.show();
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
    void clickRetry(MouseEvent event) throws IOException{ 	
    	musicaFondo.stopMusic();
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/partida.fxml"));
        Parent root = (Parent) myLoader.load();
        ControladorPartida controladorPartida = myLoader.<ControladorPartida>getController();
        controladorPartida.iniciarPartida(partidaStage);
        Scene scene = new Scene(root);
        partidaStage.setScene(scene);
        partidaStage.setTitle("Partida Estandar");
        partidaStage.setResizable(false);
        partidaStage.show();
        Stage stage = (Stage) imagePlay.getScene().getWindow();
    	stage.close();
    }
    

    @FXML
    void clickSound(MouseEvent event) throws IOException {
    	if(SoundOn) {
    		imageSound.setImage(Sound0);
    		SoundOn = false;
    	}else {
    		imageSound.setImage(Sound1);
    		SoundOn = true;
    	}
    }
    
    public void setControladorPartida(ControladorPartida partida) {
    	this.cPartida = partida;
    }

}
