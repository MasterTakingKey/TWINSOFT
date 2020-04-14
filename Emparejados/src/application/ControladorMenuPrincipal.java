package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControladorMenuPrincipal {

    @FXML
    private Button partidaEstandar;

    @FXML
    public void initialize() {
        
    } 
   
    
    @FXML
    void partidaEstandarHandler(ActionEvent event) {
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

}
