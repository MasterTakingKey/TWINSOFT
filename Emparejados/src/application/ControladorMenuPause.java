package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
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
    
    private Stage partidaStage;
    
    private Scene primaryScene;
    
    private String primaryTitle;

    
    private boolean SoundOn = true;
    
    Image Sound1 = new Image("/imagenes/sonido_on.png");
    Image Sound0 = new Image("/imagenes/sonido_off.png");

    void initData(Stage partida) {
    	partidaStage = partida;
    	primaryScene = partidaStage.getScene();
        primaryTitle = partidaStage.getTitle();
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
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/partida.fxml"));
    	Parent root = (Parent) loader.load();
    	ControladorPartida controlador = loader.getController();
    	Stage stage = (Stage) imagePlay.getScene().getWindow();
    	stage.close();
    	controlador.reaunudarPartida();
    }

    @FXML
    void clickRetry(MouseEvent event) throws IOException{
    	
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/partida.fxml"));
        Parent root = (Parent) myLoader.load();
        ControladorPartida controladorPartida = myLoader.<ControladorPartida>getController();
        controladorPartida.iniciarPartida(partidaStage);
        Scene scene = new Scene(root);
        partidaStage.setScene(scene);
        partidaStage.setTitle("Partida Estándar");
        partidaStage.setResizable(false);
        partidaStage.show();
    }
    

    @FXML
    void clickSound(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/partida.fxml"));
    	Parent root = (Parent) loader.load();
    	ControladorPartida controlador = loader.<ControladorPartida>getController();
    	if(SoundOn) {
    		imageSound.setImage(Sound0);
    		controlador.mute();
    		SoundOn = false;
    	}else {
    		imageSound.setImage(Sound1);
    		controlador.unmute();
    		SoundOn = true;
    	}
    }

}
