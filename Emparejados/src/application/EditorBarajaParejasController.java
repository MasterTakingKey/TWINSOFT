package application;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
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
import javafx.stage.Modality;
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
	
	private int actual;
	
	private Image icon;
	
	String currentDirectory = System.getProperty("user.dir");
	
	private Singleton singleton;
	
	List<File> listaImagenes = new ArrayList<File>();
	
	File archivoImagen;
	
	Image imagen;
	
	String nombreBaraja;
	
	Baraja barajaCreada = new Baraja();;
	
	Carta carta;

	public void iniciarEditorParejas(Stage stage, Singleton nuevoSingleton, ArrayList<File> imagenes){
		primaryStage = stage;
        singleton = nuevoSingleton;
        listaImagenes = imagenes;
        inicializarVariables();
        corregirTamanyoVentana();
        corregirPosicionVentana();
        actualizarEstilo();
        nombrePane.setVisible(false);
        siguienteButton.setDisable(true);
        crearBarajaButton.setDisable(true);
        anyadirIcono();
        actual = 1;
    }
	
    public void inicializarVariables() {
        thisStage = (Stage) siguienteButton.getScene().getWindow();
    }
	
	@FXML
	public void buscarImagen(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Elige Imagen");
	    fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG", "*.png","JPG", "*.jpg", "GIF", "*.gif"));
		archivoImagen = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (archivoImagen != null) {
        	imagen = new Image(archivoImagen.toURI().toString());
        	imagenCarta.setImage(imagen);
        	pathImagen.setText(archivoImagen.getName());
        	siguienteButton.setDisable(false);
        }
	}

	@FXML
	public void prepararBaraja(MouseEvent event) throws IOException {
		//Abre prompr para elegir nombre
	/*	if(archivoImagen.equals(listaImagenes.get(0))) {   //mensaje de no se puede tener 2 parejas iguales
			Alert alert = new Alert(AlertType.ERROR, "El dorso no puede ser una pareja.", ButtonType.OK);
			alert.showAndWait();
		/*}else if(listaImagenes.contains(archivoImagen)){			
			Alert alert = new Alert(AlertType.ERROR, "No puede haber 2 parejas con la misma imagen.", ButtonType.OK);
			alert.showAndWait();
		} else {*/
			nombrePane.setVisible(true);
			crearBarajaButton.setDisable(true);
			siguienteButton.setDisable(true);
			anteriorButton.setDisable(true);
			cancelarButton.setDisable(true);
		//}
	}
				

	@FXML
	public void siguiente(MouseEvent event) {		
		//Checkear que la imagen no es ya una pareja
		
		if(archivoImagen.equals(listaImagenes.get(0))) {   //mensaje de no se puede tener 2 parejas iguales
			Alert alert = new Alert(AlertType.ERROR, "El dorso no puede ser una pareja.", ButtonType.OK);
			alert.showAndWait();
		}else if(listaImagenes.contains(archivoImagen)){			
			Alert alert = new Alert(AlertType.ERROR, "No puede haber 2 parejas con la misma imagen.", ButtonType.OK);
			alert.showAndWait();
		} else {     
			siguienteButton.setDisable(true);
		//Si no falla vamos a la siguiente	
			actual++;
		
		//Guardar lo insertado en ventana anterior
			listaImagenes.add(archivoImagen);
		
		//Actualizar los labels
			numPareja.setText(String.valueOf(actual));
			imagenCarta.setImage(null);
			pathImagen.setText(null);
		
		//Activar boton de crearBaraja si estamos en pareja 9 en adelante
			if(actual < 9) {
				crearBarajaButton.setDisable(true);
			} else { 
				crearBarajaButton.setDisable(false);
			}
		
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
		nombrePane.setVisible(false);	
		crearBarajaButton.setDisable(false);
        siguienteButton.setDisable(false);
        anteriorButton.setDisable(false);
        cancelarButton.setDisable(false);
	}
	
	public void aceptarNombre(MouseEvent event) throws IOException {
		nombreBaraja = nombre.getText();
		//nombrePane.setDisable(true);
		if(isValid(nombreBaraja)) {
			//Crear carpeta en imagenes con el nombre de la baraja
		
			new File(currentDirectory + "/src/imagenes/" + nombreBaraja).mkdirs();
				
			//Guardar todas las imagenes del arrayList en la carpeta anterior
				
			for(File file : listaImagenes) {
				  Files.copy(file.toPath(),
				        (new File(currentDirectory + "/src/imagenes/"+nombreBaraja +"/"+ file.getName())).toPath(),
				        StandardCopyOption.REPLACE_EXISTING);
			}
				
				//Crear la baraja como tal
				//Completar
			barajaCreada.setNombre(nombreBaraja);
			barajaCreada.setTamanyo(2 * actual - 2 );
			imagen = new Image(listaImagenes.get(0).toURI().toString());
			barajaCreada.setImagenDorso(imagen);
			
			int indice = 0;
            for (int i = 1; i < listaImagenes.size(); i++) { 
                        imagen = new Image(listaImagenes.get(i).toURI().toString());
                        carta = new Carta(barajaCreada.getImagenDorso(), imagen, i-1);
                        barajaCreada.getBaraja()[indice++] = carta;
             }
			
			
			singleton.listaBarajas.add(barajaCreada);
			
            
			Alert alert = new Alert(AlertType.CONFIRMATION, "Baraja creada correctamente!", ButtonType.OK);
			alert.showAndWait();
			
			try {
	    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuAjustes.fxml"));
	            Parent root = myLoader.load();  
	            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
	            Scene scene = new Scene(root);
	            primaryStage.setTitle("Menu Ajustes");
	            primaryStage.setScene(scene);
	            primaryStage.setResizable(false);
	            
	            singleton.posicionX = thisStage.getX();
	      		singleton.posicionY = thisStage.getY();
	            menuPrincipal.iniciarMenuPrincipal(primaryStage, false, singleton, "");
	            primaryStage.show();
	            thisStage.close();
	    	} catch (IOException e) {
	                e.printStackTrace();
	        }
			
		} else {
			Alert alert = new Alert(AlertType.ERROR, "El nombre debe contener al menos un caracter alfanumerico.", ButtonType.OK);
			alert.showAndWait();
		}
		
	}

	@FXML
	public void anterior(MouseEvent event) {
		//Actualizar los labels		
		actual--;

		if(actual == 0) { //volver a seleccion de dorso
			try {
	    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/EditorBarajaDorso.fxml"));
	            Parent root = myLoader.load();  
	            EditorBarajaDorsoController editorDorso = myLoader.<EditorBarajaDorsoController>getController();
	            Scene scene = new Scene(root);
	            Stage stage = new Stage();
	            stage.setTitle("Seleccione el dorso");
	            stage.setScene(scene);
	            stage.initModality(Modality.APPLICATION_MODAL);
	            stage.setResizable(false);
	            singleton.posicionX = thisStage.getX();
	      		singleton.posicionY = thisStage.getY();
	            editorDorso.iniciarEditorDorso(primaryStage, singleton, false);
	            stage.show();
	            thisStage.hide();
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
		if(str.length() < 1) return false;
		  
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
    
    public void anyadirIcono() {
        icon = new Image("/imagenes/Icon.png");
        thisStage.getIcons().add(icon);
    }
    
    public void actualizarEstilo() {
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
