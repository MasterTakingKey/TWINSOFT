package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class EditorBarajaDorsoController {
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

	ArrayList<File> imagenes = new ArrayList();
	File archivoImagen;
	Image imagen;
	
	private Stage primaryStage;
    
    private Stage thisStage;
    
    private String estilo;
    
    private ArrayList<Baraja> listaBarajas;

    public void iniciarEditorDorso(Stage stage, double anteriorX, double anteriorY, String estilo, ArrayList<Baraja> lista){
        primaryStage = stage;
        listaBarajas = lista;
        inicializarVariables();
        corregirTamanyoVentana();
        corregirPosicionVentana(anteriorX, anteriorY);
        //actualizarEstilo(estilo);
    }
    
    public void inicializarVariables() {
        thisStage = (Stage) siguienteButton.getScene().getWindow();
    }
    
	@FXML
	public void buscarImagen(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Elige Imagen");
	    fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG", "*.png"));
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
            primaryStage.setTitle("Elige tus parejas");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            editorParejas.iniciarEditorParejas(primaryStage, thisStage.getX(), thisStage.getY(), estilo, listaBarajas, imagenes);
            primaryStage.show();
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
	    	thisStage.setWidth(900);
	    	thisStage.setHeight(650);
	    }
	    
	    public void corregirPosicionVentana(double anteriorX, double anteriorY) {
	    	thisStage.setX(anteriorX);
	    	thisStage.setY(anteriorY);
	    }
	    
	    
	 /*   public void actualizarEstilo(String nuevoEstilo) {
	    	estilo = nuevoEstilo;
	    	String temaAzul = getClass().getResource("estiloAzul.css").toExternalForm();
	        String temaRojo = getClass().getResource("estiloRojo.css").toExternalForm();
	        String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
	    	if(estilo.equals("Azul")) {
	    		anchorPane.getStylesheets().remove(temaRojo);
	    		anchorPane.getStylesheets().remove(temaVerde);
	    		anchorPane.getStylesheets().add(temaAzul);
	    		circuloSonido.getStylesheets().remove(temaRojo);
	    		circuloSonido.getStylesheets().remove(temaVerde);
	    		circuloSonido.getStylesheets().add(temaAzul);
	    	} else if(estilo.equals("Rojo")) {
	    		anchorPane.getStylesheets().remove(temaAzul);
				anchorPane.getStylesheets().remove(temaVerde);
				anchorPane.getStylesheets().add(temaRojo);
				circuloSonido.getStylesheets().remove(temaAzul);
				circuloSonido.getStylesheets().remove(temaVerde);
				circuloSonido.getStylesheets().add(temaRojo);
	    	} else {
	    		anchorPane.getStylesheets().remove(temaAzul);
				anchorPane.getStylesheets().remove(temaRojo);
				anchorPane.getStylesheets().add(temaVerde);
				circuloSonido.getStylesheets().remove(temaAzul);
				circuloSonido.getStylesheets().remove(temaRojo);
				circuloSonido.getStylesheets().add(temaVerde);
	    	}
	    } */
}
