package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
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
        menuPrincipal.iniciarMenuPrincipal(primaryStage, true);
        Image icono = new Image("/imagenes/Icon.png");
        primaryStage.getIcons().add(icono);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Menú Principal");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screen.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((screen.getHeight() - primaryStage.getHeight()) / 2);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
