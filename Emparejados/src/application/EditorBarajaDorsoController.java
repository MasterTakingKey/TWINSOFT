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
import javafx.stage.Modality;
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
	@FXML
	private ImageView iconoSonido;
	
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
    
    public void iniciarEditorDorso(Stage stage, ConfiguracionPartida nuevoSingleton, boolean transicion, ArrayList<File> imagenes){
        primaryStage = stage;
        singleton = nuevoSingleton;
        listaImagenes = imagenes;
        inicializarVariables();
        corregirTamanyoVentana();
        if(transicion) {corregirPosicionVentana();}
        else { corregirPosicionVentana2();}
        actualizarEstilo();
        anyadirIcono(); 
        if(!listaImagenes.isEmpty()) {  //Si ya había un dorso puesto displayearlo
        	imagen = new Image(listaImagenes.get(0).toURI().toString());
            dorsoImage.setImage(imagen);
            imagenCarta.setImage(imagen);
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
		//listaImagenes.add(archivoImagen);
		
		//System.out.print(listaImagenes.size());
		//System.out.println(listaImagenes.get(0).toString());
		
		
		try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/EditorBarajaParejas.fxml"));
            Parent root = myLoader.load();  
            EditorBarajaParejasController editorParejas = myLoader.<EditorBarajaParejasController>getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Elige tus parejas");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
            editorParejas.iniciarEditorParejas(primaryStage, singleton, listaImagenes);
            stage.show();
            thisStage.hide();
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
		thisStage.close();
	}
	
	@FXML
	public void sonidoHandler(MouseEvent event) {
		
    }   
	
	public void corregirTamanyoVentana() {
	    thisStage.setWidth(620);
	    thisStage.setHeight(720);
	}
	    
	public void corregirPosicionVentana() {
		thisStage.setX(singleton.posicionX + 120);
		thisStage.setY(singleton.posicionY + 80);
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
