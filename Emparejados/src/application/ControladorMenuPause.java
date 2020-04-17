package application;

import java.io.IOException;

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
            Stage newStage = new Stage();
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/ControladorPartida.fxml"));
            Parent root = myLoader.load();
            
            Scene scene = new Scene(root, 470, 420);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            newStage.setScene(scene);
            newStage.setTitle("Program's information");
            newStage.setResizable(false);
            newStage.show();  
    	} catch (IOException e) {
                e.printStackTrace();
        }
    	
    }

    @FXML
    void clickPlay(MouseEvent event) {
    	Stage stage = (Stage) imagePlay.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void clickRetry(MouseEvent event) {
    	try {
	    	Stage stage = (Stage) imageRetry.getScene().getWindow();
	    	stage.close();
	    	partidaStage.close();
	    	Stage newStage = new Stage();
	        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/partida.fxml"));
	        ControladorPartida controlador = myLoader.<ControladorPartida>getController();
	        Parent root = myLoader.load();
	         
	        Scene scene = new Scene(root, 400, 400);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        newStage.setScene(scene);
	        newStage.setTitle("Menu de Pausa");
	        newStage.setResizable(false);
	        newStage.initModality(Modality.APPLICATION_MODAL);
	        newStage.show();
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    void clickSound(MouseEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/partida.fxml"));
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
