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
	@FXML
	private Label dorsoLabel;
	@FXML
	private ImageView iconoSonido;

	@FXML
	private Label Pareja1Label;

	@FXML
	private Label Pareja2Label;

	@FXML
	private Label Pareja3Label;

	@FXML
	private Label Pareja4Label;
	 
	@FXML
	private Label Pareja5Label;
	 			
	@FXML
	private Label Pareja6Label;
	
	@FXML
    private ImageView Pareja1Image;

    @FXML
    private ImageView Pareja2Image;

    @FXML
    private ImageView Pareja3Image;

    @FXML
    private ImageView Pareja4Image;

    @FXML
    private ImageView dorsoImage;

    @FXML
    private ImageView Pareja5Image;

    @FXML
    private ImageView Pareja6Image;

    @FXML
    private Label Pareja7Label;

    @FXML
    private Label Pareja8Label;

    @FXML
    private Label Pareja9Label;

    @FXML
    private Label Pareja10Label;

    @FXML
    private Label Pareja11Label;

    @FXML
    private Label Pareja12label;

    @FXML
    private ImageView Pareja7Image;

    @FXML
    private ImageView Pareja12Image;

    @FXML
    private ImageView Pareja10Image;

    @FXML
    private ImageView Pareja9Image;

    @FXML
    private ImageView Pareja8Image;

    @FXML
    private ImageView Pareja11Image;

    @FXML
    private Label Pareja13Label;

    @FXML
    private Label Pareja14Label;

    @FXML
    private Label Pareja15Label;

    @FXML
    private Label Pareja16Label;

    @FXML
    private Label Pareja17Label;

    @FXML
    private Label Pareja18Label;

    @FXML
    private ImageView Pareja13Image;

    @FXML
    private ImageView Pareja14Image;

    @FXML
    private ImageView Pareja15Image;

    @FXML
    private ImageView Pareja16Image;

    @FXML
    private ImageView Pareja17Image;

    @FXML
    private ImageView Pareja18Image;
    
    @FXML
    private Button addImageButton;

	private Stage primaryStage;
    
    private Stage thisStage;
	
	private int actual;
	
	private Image icon;
	
	String currentDirectory = System.getProperty("user.dir");
	
	private Singleton singleton;
	
	private ArrayList<File> listaImagenes = new ArrayList<File>();
	
	File archivoImagen;
	
	Image imagen;
	
	String nombreBaraja;
	
	Baraja barajaCreada = new Baraja();
	
	Carta carta;
	
	String prueba = "";

	public void iniciarEditorParejas(Stage stage, Singleton nuevoSingleton, ArrayList<File> imagenes){
		primaryStage = stage;
        singleton = nuevoSingleton;
        listaImagenes = imagenes;
        actual = 1;
        inicializarVariables();
        corregirTamanyoVentana();
        corregirPosicionVentana();
        actualizarEstilo();
        nombrePane.setVisible(false);
        if(listaImagenes.size() > actual) {
        	siguienteButton.setDisable(false);
        } else { 
			siguienteButton.setDisable(true);
		}
        if(listaImagenes.size() > 8) {
			crearBarajaButton.setDisable(false);
		} else { 
			crearBarajaButton.setDisable(true);
		}
        addImageButton.setDisable(true);
        anyadirIcono();
        actualizarBaraja();
        if(Pareja1Image.getImage() != null) imagenCarta.setImage(Pareja1Image.getImage());
        imagen = new Image(listaImagenes.get(0).toURI().toString());
        dorsoImage.setImage(imagen);
        
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
        	addImageButton.setDisable(false);
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
			addImageButton.setDisable(true);
		//}
	}
	
	@FXML
	public void anyadirImagen(MouseEvent event) {	
		if(archivoImagen.equals(listaImagenes.get(0))) {   
			Alert alert = new Alert(AlertType.ERROR, "El dorso no puede ser una pareja.", ButtonType.OK);
			alert.showAndWait();
			return;
		}else if(listaImagenes.contains(archivoImagen)){			
			Alert alert = new Alert(AlertType.ERROR, "No puede haber 2 parejas con la misma imagen.", ButtonType.OK);
			alert.showAndWait();
			return;	
		} else if(listaImagenes.size() > actual){
			listaImagenes.set(actual, archivoImagen);
		} else {
			listaImagenes.add(archivoImagen);
		}		
		actualizarBaraja();
		siguienteButton.setDisable(false);
		if(listaImagenes.size() > 8) {
			crearBarajaButton.setDisable(false);
		} else { 
			crearBarajaButton.setDisable(true);
		}
			 
	}

	@FXML
	public void siguiente(MouseEvent event) {		    
		   		
			actual++;
			
			if(listaImagenes.size() > actual) {
		    	siguienteButton.setDisable(false);
		    }else {
		    	siguienteButton.setDisable(true);
		    }
				
		//Actualizar los labels
			numPareja.setText(String.valueOf(actual));
			addImageButton.setDisable(true);
			if(listaImagenes.size() > actual) {
				imagen = new Image(listaImagenes.get(actual).toURI().toString());
				imagenCarta.setImage(imagen);
				pathImagen.setText(listaImagenes.get(actual).getName());
			} else {
				imagenCarta.setImage(null);
				pathImagen.setText(null);
			}
			
		//Actualizar ImageViews de la baraja
			//actualizarBaraja();				//Creo que es inncesario ya
			
		//Activar boton de crearBaraja si estamos en pareja 9 en adelante
			if(listaImagenes.size() > 8) {
				crearBarajaButton.setDisable(false);
			} else { 
				crearBarajaButton.setDisable(true);
			}
		
		//Activar boton de anterior si estamos en pareja 1 en adelante
		
			numPareja.setText(String.valueOf(actual));
		
		//Desactivar boton de seguimiento si estamos en pareja 16
			if(actual > 18) {
				siguienteButton.setDisable(true);
				buscar.setDisable(true);
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
			barajaCreada.setTamanyo(2 * listaImagenes.size() - 2 );
			imagen = new Image(listaImagenes.get(0).toURI().toString());
			barajaCreada.setImagenDorso(imagen);
			
			int indice = 0;
            for (int i = 1; i < listaImagenes.size(); i++) { 
                        imagen = new Image(listaImagenes.get(i).toURI().toString());
                        carta = new Carta(barajaCreada.getImagenDorso(), imagen, i-1);
                        barajaCreada.getBaraja()[indice++] = carta;
             }
			
			
			singleton.listaBarajas.add(barajaCreada);
			
			
			try {
	    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
	            Parent root = myLoader.load();  
	            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
	            Scene scene = new Scene(root);
	            primaryStage.setTitle("Menu Principal");
	            primaryStage.setScene(scene);
	            primaryStage.setResizable(false);
	            
	            singleton.posicionX = thisStage.getX();
	      		singleton.posicionY = thisStage.getY();
	            menuPrincipal.iniciarMenuPrincipal(primaryStage, false, singleton, prueba);
	           // primaryStage.show();
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
	            editorDorso.iniciarEditorDorso(primaryStage, singleton, false, listaImagenes);
	            stage.show();
	            thisStage.hide();
	    	} catch (IOException e) {
	                e.printStackTrace();
	        }
		}
		else {
			numPareja.setText(String.valueOf(actual));
			//listaImagenes.remove(archivoImagen);
			imagen = new Image(listaImagenes.get(actual).toURI().toString());
        	imagenCarta.setImage(imagen);
        	if(imagenCarta != null) siguienteButton.setDisable(false);
			
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
	
	public void actualizarBaraja() {
		for (int i = 1; i < listaImagenes.size(); i++) { 
            imagen = new Image(listaImagenes.get(i).toURI().toString());
            switch(i) {
            case 1: 
           	 Pareja1Image.setImage(imagen);
           	 break;
            case 2: 
           	 Pareja2Image.setImage(imagen);
           	 break;
            case 3: 
           	 Pareja3Image.setImage(imagen);
           	 break;
            case 4: 
           	 Pareja4Image.setImage(imagen);
           	 break;
            case 5: 
           	 Pareja5Image.setImage(imagen);
           	 break;
            case 6: 
           	 Pareja6Image.setImage(imagen);
           	 break;
            case 7: 
           	 Pareja7Image.setImage(imagen);
           	 break;
            case 8: 
           	 Pareja8Image.setImage(imagen);
           	 break;
            case 9: 
           	 Pareja9Image.setImage(imagen);
           	 break;
            case 10: 
           	 Pareja10Image.setImage(imagen);
           	 break;
            case 11: 
           	 Pareja11Image.setImage(imagen);
           	 break;
            case 12: 
           	 Pareja12Image.setImage(imagen);
           	 break;
            case 13: 
           	 Pareja13Image.setImage(imagen);
           	 break;
            case 14: 
           	 Pareja14Image.setImage(imagen);
           	 break;
            case 15: 
           	 Pareja15Image.setImage(imagen);
           	 break;
            case 16: 
           	 Pareja16Image.setImage(imagen);
           	 break;
            case 17: 
           	 Pareja17Image.setImage(imagen);
           	 break;
            case 18: 
           	 Pareja18Image.setImage(imagen);
           	 break; 
            }
       }
	}
	
	@FXML
	public void sonidoHandler(MouseEvent event) {
    }
    
	
	public void corregirTamanyoVentana() {
    	thisStage.setWidth(620);
    	thisStage.setHeight(720);
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
