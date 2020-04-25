package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ControladorMenuPause {

    @FXML
    private ImageView imagePlay;

    @FXML
    private ImageView imageRetry;

    @FXML
    private ImageView imageClose;

    @FXML
    private ImageView imageSound;
    
    private Stage partidaStage;
    
    private Scene primaryScene;
    
    private String primaryTitle;

    
    private boolean SoundOn;
    
    private ControladorPartida cPartida;
    
    Image Sound1 = new Image("/imagenes/sonido_on.png");
    Image Sound0 = new Image("/imagenes/sonido_off.png");
    
    void initData(Stage partida, boolean soundOn) {
    	partidaStage = partida;
    	primaryScene = partidaStage.getScene();
        primaryTitle = partidaStage.getTitle();
        SoundOn = soundOn;
        if(SoundOn) imageSound.setImage(Sound1);
        else imageSound.setImage(Sound0);
    }
    
    @FXML
    void clickClose(MouseEvent event) {
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
