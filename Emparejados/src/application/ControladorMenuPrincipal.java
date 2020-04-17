package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControladorMenuPrincipal implements Initializable {

    @FXML
    private Button partidaEstandar;
    
    private Stage primaryStage;
    
    private Scene primaryScene;
    
    private String primaryTitle;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
   
    public void iniciarMenuPrincipal(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();

}
    
    @FXML
    void partidaEstandarHandler(ActionEvent event) throws IOException {
    	 /**try {
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
             */
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/partida.fxml"));
        Parent root = (Parent) myLoader.load();
        ControladorPartida controladorPartida = myLoader.<ControladorPartida>getController();
        controladorPartida.iniciarPartida(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Partida Estándar");
        primaryStage.show();
    }

}
