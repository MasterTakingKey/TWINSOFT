package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class ControladorEditorBarajaDorso {
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
	@FXML
	private ImageView iconoSonido;
	@FXML
	private StackPane circuloSonido;
	
	
	@FXML
	private Label dorsoLabel;

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

    private ArrayList<File> listaImagenes = new ArrayList<File>();
	
	private File archivoImagen;
	
	private Image imagen;
	
	private Stage primaryStage;
    
    private Stage thisStage;
    
    private ConfiguracionPartida singleton;

    private Image icon;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private long tiempoMusica;
    
    private Musica musicaFondo;
    
    private String ventanaActual = "editorBarajaDorso";
    
    public void iniciarEditorDorso(Stage stage, ConfiguracionPartida nuevoSingleton, boolean transicion, ArrayList<File> imagenes, Musica cancion, long tiempoCancion){
        primaryStage = stage;
        singleton = nuevoSingleton;
        listaImagenes = imagenes;
        musicaFondo = cancion;
        tiempoMusica = tiempoCancion;
        inicializarVariables();
        corregirTamanyoVentana();
        if(transicion) {
        	corregirPosicionVentana();
        }
        else { 
        	corregirPosicionVentana2();
        }
        actualizarSonido();
        actualizarImagenSonido();
        actualizarEstilo();
        anyadirIcono(); 
        if(!listaImagenes.isEmpty()) {  //Si ya hab�a un dorso puesto displayearlo
        	imagen = new Image(listaImagenes.get(0).toURI().toString());
            dorsoImage.setImage(imagen);
            imagenCarta.setImage(imagen);
            pathImagen.setText(listaImagenes.get(0).getName());
        }
        if(imagenCarta.getImage() == null) { //No puedes seguir hasta que no seleccionas un dorso
        	siguienteButton.setDisable(true);      
        } else {
        	siguienteButton.setDisable(false);
        }
        addImageButton.setDisable(true);
        actualizarBaraja();
    }
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        thisStage = (Stage) siguienteButton.getScene().getWindow();
        
    }
    
    
	@FXML
	public void buscarImagen(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Elige Imagen");
	    fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG", "*.png", "JPG", "*.jpg", "GIF", "*.gif"));
		archivoImagen = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (archivoImagen != null) {
        	imagen = new Image(archivoImagen.toURI().toString());
        	imagenCarta.setImage(imagen);
        	pathImagen.setText(archivoImagen.getName());
        	addImageButton.setDisable(false);
        }else  if(listaImagenes.size() < 1){
            	imagenCarta.setImage(null);
            	pathImagen.setText(null);
            	addImageButton.setDisable(true);
            }
        
	}
	
	@FXML
	public void anyadirImagen(MouseEvent event) {	
		if(listaImagenes.isEmpty()) {
			listaImagenes.add(archivoImagen);
		}else if(!imagenCarta.getImage().equals(dorsoImage.getImage())){
			listaImagenes.set(0, archivoImagen);
		}
		siguienteButton.setDisable(false);
		actualizarBaraja();
	}


	@FXML
	public void elegirParejas(MouseEvent event) {   //Ir a EditorBaraja-Parejas
		try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/EditorBarajaParejas.fxml"));
            Parent root = myLoader.load();  
            ControladorEditorBarajaParejas editorParejas = myLoader.<ControladorEditorBarajaParejas>getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Elige tus parejas");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
            editorParejas.iniciarEditorParejas(primaryStage, singleton, listaImagenes, musicaFondo, tiempoMusica);
            primaryStage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }		
	}
	
	public void actualizarBaraja() {
		for (int i = 0; i < listaImagenes.size(); i++) { 
            imagen = new Image(listaImagenes.get(i).toURI().toString());
            switch(i) {
            case 0: 
            dorsoImage.setImage(imagen);
            break;
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
	public void cancelar(MouseEvent event) {
		listaImagenes.clear();
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
      		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
      		musicaFondo.stopMusic();
            menuPrincipal.iniciarMenuPrincipalDesdeEditor(primaryStage, false, singleton, ventanaActual, tiempoMusica);
            primaryStage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }
	}
	
	@FXML
	public void sonidoHandler(MouseEvent event) {
		if(singleton.soundOn) {
    		singleton.soundOn = false;
    		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
    	} else {
    		singleton.soundOn = true;
    	}
    	actualizarSonido();
    	actualizarImagenSonido();
    }   
	
	 public void actualizarSonido() {
	    	if(singleton.soundOn) {
	    		musicaFondo.getClip().setMicrosecondPosition(tiempoMusica);
	    		musicaFondo.playMusic();
	    	}
	    	else {
	    		musicaFondo.stopMusic();
	    	}
	    }
	    
	    public void actualizarImagenSonido() {
	        if(singleton.soundOn) {
	        	iconoSonido.setImage(Sound1);
	        } else {
	        	iconoSonido.setImage(Sound0);
	        }
	    }
	
	public void corregirTamanyoVentana() {
	    thisStage.setWidth(650);
	    thisStage.setHeight(750);
	}
	    
	public void corregirPosicionVentana() {
		thisStage.setX(singleton.posicionX - 25);
		thisStage.setY(singleton.posicionY - 125);
	}
	public void corregirPosicionVentana2() {
		thisStage.setX(singleton.posicionX);
		thisStage.setY(singleton.posicionY);
	}
	
	public void anyadirIcono() {
        icon = new Image("/imagenes/Icon.png");
        thisStage.getIcons().add(icon);
    }
	    
	    
	    public void actualizarEstilo() {
	    	Estilo nuevoEstilo;
	        if(singleton.estilo.equals("Azul")) {
	            nuevoEstilo = new Estilo(new EstrategiaEstiloAzul());
	        } else if(singleton.estilo.equals("Rojo")) {
	            nuevoEstilo = new Estilo(new EstrategiaEstiloRojo());
	        } else {
	            nuevoEstilo = new Estilo(new EstrategiaEstiloVerde());
	        }
	        nuevoEstilo.cambiarEstilo(pane, null, circuloSonido);
	    }
}
