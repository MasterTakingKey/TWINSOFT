package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class EditorBarajaController {
	@FXML
	private Label numPareja;
	@FXML
	private Pane nombrePane;
	@FXML
	private Label nombre;
	@FXML
	private Button buscar;
	@FXML
	private ImageView imagenCarta;
	@FXML
	private Button crearBarajaButton;
	@FXML
	private Button aceptarNombre;
	@FXML
	private Button cancelarNombre;
	@FXML
	private Button siguienteButton;
	@FXML
	private Button cancelarButton;
	@FXML
	private Button anteriorButton;
	@FXML
	private Label pathImagen;
	
	private int actual = 1;
	
	List<File> imagenes = new ArrayList();
	File archivoImagen;
	Image imagen;
	String nombreBaraja;

	
	@FXML
	public void buscarImagen(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Elige Imagen");
	    //fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG", "*.png"));
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
		
		if(imagenes.contains(archivoImagen)) {   //mensaje de no se puede tener 2 parejas iguales
			Alert alert = new Alert(AlertType.ERROR, "No puede haber 2 parejas con la misma imagen", ButtonType.OK);
			alert.showAndWait();
		} else {        
		//Si no falla vamos a la siguiente	
			actual++;
		
		//Guardar lo insertado en ventana anterior
			imagenes.add(archivoImagen);
		
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
		//Volver a menú
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
			for(File file : imagenes) {
				  Files.copy(file.toPath(),
				        (new File("/imagenes/"+nombreBaraja + file.getName())).toPath(),
				        StandardCopyOption.REPLACE_EXISTING);
			}
				
				//Crear la baraja como tal
				//Completar
			
		} else {
			Alert alert = new Alert(AlertType.ERROR, "El nombre solamente puede contener caracteres alfanuméricos.", ButtonType.OK);
			alert.showAndWait();
		}
		
	}

	@FXML
	public void anterior(MouseEvent event) {
		//Desactivar boton de siguiente si estamos en pareja  en adelante
		if(actual <= 15) siguienteButton.setDisable(false);
		//Actualizar los labels		
		actual--;

		if(actual == 0) //volver a seleccion de dorso
		//else
			numPareja.setText(String.valueOf(actual));
			if(actual<5) crearBarajaButton.setDisable(true);
			imagenes.remove(archivoImagen);
	}
	
	static boolean isValid(String str) 
	{ 
	  
	    // If first character is invalid 
	    if (!((str.charAt(0) >= 'a' && str.charAt(0) <= 'z') 
	        || (str.charAt(0)>= 'A' && str.charAt(1) <= 'Z')
	        || (str.charAt(0) >= '0' && str.charAt(0) <= '9'))) 
	        return false; 
	  
	    // Traverse the string for the rest of the characters 
	    for (int i = 1; i < str.length(); i++) 
	    { 
	        if (!((str.charAt(i) >= 'a' && str.charAt(i) <= 'z') 
	            || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') 
	            || (str.charAt(i) >= '0' && str.charAt(i) <= '9'))) 
	            return false; 
	    } 
	  
	    // String is a valid identifier 
	    return true; 
	} 
}
