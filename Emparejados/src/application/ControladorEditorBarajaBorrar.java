package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;

import javafx.scene.layout.Pane;

public class ControladorEditorBarajaBorrar {
	@FXML
	private Pane pane;
	@FXML
	private Button cancelarButton;
	@FXML
	private StackPane circuloSonido;
	@FXML
	private ImageView iconoSonido;
	@FXML
	private Button borrarBaraja;
	@FXML
	private ChoiceBox<String> listadoBarajas;
	
	private Stage primaryStage;
    
    private Stage thisStage;
    
    private ConfiguracionPartida singleton;

    private Image icon;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private long tiempoMusica;
    
    private Musica musicaFondo;
    
    private String currentDirectory = System.getProperty("user.dir");
    
    private String ventanaActual = "editorBarajaBorrar";

    public void iniciarEditorBorrar(Stage stage, ConfiguracionPartida nuevoSingleton, boolean transicion, Musica cancion, long tiempoCancion){
        primaryStage = stage;
        singleton = nuevoSingleton;
        musicaFondo = cancion;
        tiempoMusica = tiempoCancion;
        llenarChoiceBox();
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
    }
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        thisStage = (Stage) cancelarButton.getScene().getWindow();
        
    }
    
	@FXML
	public void cancelar(MouseEvent event) {
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
	
	public void llenarChoiceBox() {
        	int i = 3;        	
    		while(singleton.listaBarajas.size() > i) {
    			listadoBarajas.getItems().add(singleton.listaBarajas.get(i).getNombre());
    			i++;
    		}
    		listadoBarajas.getSelectionModel().select(0);
	}


	@FXML
	public void BorrarBaraja(MouseEvent event) {
		int i = 3;
    	while(singleton.listaBarajas.size() > i) {
    		if(listadoBarajas.getSelectionModel().getSelectedItem().equals(singleton.listaBarajas.get(i).getNombre())) {
    			if(singleton.barajaPartida.nombre.equals(listadoBarajas.getSelectionModel().getSelectedItem())) singleton.barajaPartida = singleton.listaBarajas.get(0);
    			File file = new File(currentDirectory + "/src/imagenes/barajasPersonalizadas/"+singleton.listaBarajas.get(i).getNombre());
    			deleteDirectory(file);
    			singleton.listaBarajas.remove(i);
    			listadoBarajas.getItems().remove(listadoBarajas.getSelectionModel().getSelectedIndex());
    			listadoBarajas.getSelectionModel().select(0);
    			break;
    		    }
    		i++;
    	}
    		
    }
	
    public void deleteDirectory(File directory){
    	    File[] allContents = directory.listFiles();
    	    if (allContents != null) {
    	        for (File file : allContents) {
    	            deleteDirectory(file);
    	        }
    	    }
    	   directory.delete();
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
	    thisStage.setHeight(450);
	}
	    
	public void corregirPosicionVentana() {
		thisStage.setX(singleton.posicionX - 25);
		thisStage.setY(singleton.posicionY + 25);
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
