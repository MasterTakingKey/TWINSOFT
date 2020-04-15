package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    
    private Stage partidaStage;

    void initData(Stage partida) {
    	partidaStage = partida;
    }
    
    @FXML
    void clickClose(MouseEvent event) {
    	Stage stage = (Stage) imageClose.getScene().getWindow();
    	stage.close();
    	partidaStage.close();
    	try {
            Stage newStage = new Stage();
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("ControladorPartida.fxml"));
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

    }

    @FXML
    void clickSound(MouseEvent event) {

    }

}
