package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
        
		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
        Parent root = myLoader.load();  
        ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Menu Principal");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        
        Image icono = new Image("/imagenes/Icon.png");
        primaryStage.getIcons().add(icono);
        
        menuPrincipal.iniciarMenuPrincipal(primaryStage, true, true, 0, 0, "Azul", null, null);
        primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
