package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		/**try {
			
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
	        Parent root = myLoader.load();
	        
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("TWINS by TwinSoft");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		*/
		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
        Parent root = myLoader.load();  
        ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
        menuPrincipal.iniciarMenuPrincipal(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Menú principal");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
