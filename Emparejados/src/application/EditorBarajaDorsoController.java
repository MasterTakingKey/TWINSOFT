package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class EditorBarajaDorsoController {
    @FXML
    private Pane pane;
	@FXML
	private Button buscar;
	@FXML
	private ImageView imagenCarta;
	@FXML
	private Button siguienteButton;
	@FXML
	private Button cancelarButton;
	@FXML
	private Label pathImagen;

	private ArrayList<File> imagenes = new ArrayList();
	
	private File archivoImagen;
	
	private Image imagen;
	
	private Stage primaryStage;
    
    private Stage thisStage;
    
    private Singleton singleton;

    public void iniciarEditorDorso(Stage stage, Singleton nuevoSingleton){
        primaryStage = stage;
        singleton = nuevoSingleton;
        inicializarVariables();
        corregirTamanyoVentana();
        corregirPosicionVentana();
        actualizarEstilo(singleton.estilo);
    }
    
    public void inicializarVariables() {
        thisStage = (Stage) siguienteButton.getScene().getWindow();
    }
    
	@FXML
	public void buscarImagen(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Elige Imagen");
	    fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG", "*.png", "JPG", "*.jpg"));
		archivoImagen = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (archivoImagen != null) {
        	imagen = new Image(archivoImagen.toURI().toString());
        	imagenCarta.setImage(imagen);
        	pathImagen.setText(archivoImagen.toURI().toString());
        }
	}

	@FXML
	public void elegirParejas(MouseEvent event) { //Ir a EditorBaraja-Parejas
		imagenes.add(archivoImagen);
		
		try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/EditorBarajaParejas.fxml"));
            Parent root = myLoader.load();  
            EditorBarajaParejasController editorParejas = myLoader.<EditorBarajaParejasController>getController();
            Scene scene = new Scene(root);
            thisStage.setTitle("Elige tus parejas");
            thisStage.setScene(scene);
            thisStage.setResizable(false);
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
            editorParejas.iniciarEditorParejas(primaryStage, singleton, imagenes);
            thisStage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }
		
	}

	@FXML
	public void cancelar(MouseEvent event) {
		imagenes.clear();
		thisStage.close();
	}
	
	public void corregirTamanyoVentana() {
	    thisStage.setWidth(620);
	    thisStage.setHeight(450);
	}
	    
	public void corregirPosicionVentana() {
		thisStage.setX(singleton.posicionX + 120);
		thisStage.setY(singleton.posicionY + 80);
	}
	    
	    
	    public void actualizarEstilo(String nuevoEstilo) {
	    	singleton.estilo = nuevoEstilo;
	    	String temaAzul = getClass().getResource("estiloAzul.css").toExternalForm();
	        String temaRojo = getClass().getResource("estiloRojo.css").toExternalForm();
	        String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
	    	if(singleton.estilo.equals("Azul")) {
	    		pane.getStylesheets().remove(temaRojo);
	    		pane.getStylesheets().remove(temaVerde);
	    		pane.getStylesheets().add(temaAzul);
	    	} else if(singleton.estilo.equals("Rojo")) {
	    		pane.getStylesheets().remove(temaAzul);
	    		pane.getStylesheets().remove(temaVerde);
				pane.getStylesheets().add(temaRojo);
	    	} else {
	    		pane.getStylesheets().remove(temaAzul);
	    		pane.getStylesheets().remove(temaRojo);
	    		pane.getStylesheets().add(temaVerde);
	    	}
	    } 
}
