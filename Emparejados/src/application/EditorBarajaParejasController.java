package application;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class EditorBarajaParejasController {
    @FXML
    private Pane pane;
	@FXML
	private Label numPareja;
	@FXML
	private Button buscar;
	@FXML
	private ImageView imagenCarta;
	@FXML
	private Button crearBarajaButton;
	@FXML
	private Button siguienteButton;
	@FXML
	private Button cancelarButton;
	@FXML
	private Button anteriorButton;
	@FXML
	private Label pathImagen;
	@FXML
	private Pane nombrePane;
	@FXML
	private TextField nombre;
	@FXML
	private Button aceptarNombre;
	@FXML
	private Button cancelarNombre;

	private Stage primaryStage;
    
    private Stage thisStage;
	
	private int actual = 1;
	
	private Singleton singleton;
	
	List<File> listaImagenes = new ArrayList();
	
	File archivoImagen;
	
	Image imagen;
	
	String nombreBaraja;
	
	Baraja barajaCreada;
	
	Carta carta;

	public void iniciarEditorParejas(Stage stage, Singleton nuevoSingleton, ArrayList<File> imagenes){
		primaryStage = stage;
        singleton = nuevoSingleton;
        listaImagenes = imagenes;
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
	    fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG", "*.png","JPG", "*.jpg"));
		archivoImagen = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (archivoImagen != null) {
        	imagen = new Image(archivoImagen.toURI().toString());
        	imagenCarta.setImage(imagen);
        	pathImagen.setText(archivoImagen.toURI().toString());
        }
	}

	@FXML
	public void prepararBaraja(MouseEvent event) throws IOException {
		//Abre prompr para elegir nombre
		nombrePane.setDisable(false);
				
	}

	@FXML
	public void siguiente(MouseEvent event) {		
		//Checkear que la imagen no es ya una pareja
		
		if(listaImagenes.contains(archivoImagen)) {   //mensaje de no se puede tener 2 parejas iguales
			Alert alert = new Alert(AlertType.ERROR, "No puede haber 2 parejas con la misma imagen", ButtonType.OK);
			alert.showAndWait();
		} else {        
		//Si no falla vamos a la siguiente	
			actual++;
		
		//Guardar lo insertado en ventana anterior
			listaImagenes.add(archivoImagen);
		
		//Actualizar los labels
			numPareja.setText(String.valueOf(actual));
			imagenCarta.setImage(null);
			pathImagen.setText(null);
		
		//Activar boton de crearBaraja si estamos en pareja 5 en adelante
			if(actual>4) crearBarajaButton.setDisable(false);
		
		//Activar boton de anterior si estamos en pareja 1 en adelante
		
			numPareja.setText(String.valueOf(actual));
			imagenCarta.setImage(null);
		
		//Desactivar boton de seguimiento si estamos en pareja 16
			if(actual > 15) siguienteButton.setDisable(true);
		}
	}

	@FXML
	public void cancelar(MouseEvent event) {
		listaImagenes.clear();
		thisStage.close();
	}
	
	public void cancelarNombre(MouseEvent event) {
		nombrePane.setDisable(true);
	}
	
	public void aceptarNombre(MouseEvent event) throws IOException {
		nombreBaraja = nombre.getText();
		//nombrePane.setDisable(true);
		if(isValid(nombreBaraja)) {
			//Crear carpeta en imagenes con el nombre de la baraja
		
			new File("/imagenes/"+nombreBaraja).mkdirs();
				
			//Guardar todas las imagenes del arrayList en la carpeta anterior
				
			//String path = "C:/destination/";
			for(File file : listaImagenes) {
				  Files.copy(file.toPath(),
				        (new File("/imagenes/"+nombreBaraja + file.getName())).toPath(),
				        StandardCopyOption.REPLACE_EXISTING);
			}
				
				//Crear la baraja como tal
				//Completar
			imagen = new Image(listaImagenes.get(0).toURI().toString());
			barajaCreada.setImagenDorso(imagen);
			
			for (int i = 1; i < listaImagenes.size(); i++) { 
					int indice = 0;
					imagen = new Image(listaImagenes.get(i).toURI().toString());
					carta = new Carta(barajaCreada.getImagenDorso(), imagen, i-1);
					barajaCreada.getBaraja()[indice++] = carta;
					barajaCreada.getBaraja()[indice++] = carta;
		     }
			try {
	    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuAjustes.fxml"));
	            Parent root = myLoader.load();  
	            ControladorMenuAjustes menuAjustes = myLoader.<ControladorMenuAjustes>getController();
	            Scene scene = new Scene(root);
	            primaryStage.setTitle("Menu Ajustes");
	            primaryStage.setScene(scene);
	            primaryStage.setResizable(false);
	            
	            singleton.posicionX = thisStage.getX();
	      		singleton.posicionY = thisStage.getY();
	            menuAjustes.iniciarMenuAjustes(primaryStage, singleton);
	            primaryStage.show();
	    	} catch (IOException e) {
	                e.printStackTrace();
	        }
			
		} else {
			Alert alert = new Alert(AlertType.ERROR, "El nombre solamente puede contener caracteres alfanuméricos.", ButtonType.OK);
			alert.showAndWait();
		}
		
	}

	@FXML
	public void anterior(MouseEvent event) {
		//Desactivar boton de siguiente si estamos en pareja  en adelante
		//if(actual <= 15) siguienteButton.setDisable(false);
		
		//Actualizar los labels		
		actual--;

		if(actual == 0) { //volver a seleccion de dorso
			try {
	    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/EditorBarajaParejas.fxml"));
	            Parent root = myLoader.load();  
	            EditorBarajaDorsoController editorDorso = myLoader.<EditorBarajaDorsoController>getController();
	            Scene scene = new Scene(root);
	            primaryStage.setTitle("Elige el dorso");
	            primaryStage.setScene(scene);
	            primaryStage.setResizable(false);
	            primaryStage.show();
	    	} catch (IOException e) {
	                e.printStackTrace();
	        }
		}
		else {
			numPareja.setText(String.valueOf(actual));
			if(actual<5) crearBarajaButton.setDisable(true);
			listaImagenes.remove(archivoImagen);
		}
	}
	
	static boolean isValid(String str) 
	{ 
	  
	    for (int i = 0; i < str.length(); i++) 
	    { 
	        if (!((str.charAt(i) >= 'a' && str.charAt(i) <= 'z') 
	            || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') 
	            || (str.charAt(i) >= '0' && str.charAt(i) <= '9'))) 
	            return false; 
	    } 
	    return true; 
	} 
	public void corregirTamanyoVentana() {
    	thisStage.setWidth(620);
    	thisStage.setHeight(450);
    }
    
    public void corregirPosicionVentana() {
    	thisStage.setX(singleton.posicionX);
    	thisStage.setY(singleton.posicionY);
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
