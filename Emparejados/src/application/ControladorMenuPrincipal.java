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
             FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/partida.fxml"));
             Parent root = myLoader.load();
             
             Stage newStage = new Stage();
             ControladorPartida controladorP = myLoader.<ControladorPartida>getController();
             controladorP.iniciarPartida(newStage);
             
             Scene scene = new Scene(root, 1000, 650);
             scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
             newStage.setScene(scene);
             newStage.setTitle("Partida Estándar");
             newStage.setResizable(false);
             newStage.show();  
             } catch (IOException e) {
                 e.printStackTrace();
             }
    }

}
