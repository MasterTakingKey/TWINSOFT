package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class ControladorEditorBarajaOpciones {
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
	private Button nuevaBaraja;
	private Stage primaryStage;
    
    private Stage thisStage;
    
    private ConfiguracionPartida singleton;

    private Image icon;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private long tiempoMusica;
    
    private Musica musicaFondo;
    
    private String ventanaActual = "editorBarajaOpciones";
    
    private ArrayList<File> listaImagenes = new ArrayList<File>();

	
	 public void iniciarEditorOpciones(Stage stage, ConfiguracionPartida nuevoSingleton, boolean transicion, ArrayList<File> imagenes, Musica cancion, long tiempoCancion){
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

	@FXML
	public void BorrarBaraja(MouseEvent event) {
		if (singleton.listaBarajas.size() <= 3) {
			Alert alert = new Alert(AlertType.ERROR, "Aún no ha creado ninguna baraja que pueda borrar.");
         	alert.showAndWait();
		} else {
			try {
	    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/EditorBarajaBorrar.fxml"));
	            Parent root = myLoader.load();  
	            ControladorEditorBarajaBorrar editorBorrar = myLoader.<ControladorEditorBarajaBorrar>getController();
	            Scene scene = new Scene(root);
	            primaryStage.setTitle("Borra tus barajas");
	            primaryStage.setScene(scene);
	            primaryStage.setResizable(false);
	            singleton.posicionX = thisStage.getX();
	      		singleton.posicionY = thisStage.getY();
	      		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
	            editorBorrar.iniciarEditorBorrar(primaryStage, singleton, true , musicaFondo, tiempoMusica);
	            primaryStage.show();
	    	} catch (IOException e) {
	                e.printStackTrace();
	        }	
		}
		
	}
	
	@FXML
	public void nuevaBaraja(MouseEvent event) {
		try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/EditorBarajaDorso.fxml"));
            Parent root = myLoader.load();  
            ControladorEditorBarajaDorso editorDorso = myLoader.<ControladorEditorBarajaDorso>getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Elige un dorso");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
            editorDorso.iniciarEditorDorso(primaryStage, singleton, true , listaImagenes, musicaFondo, tiempoMusica);
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
	    thisStage.setWidth(600);
	    thisStage.setHeight(500);
	}
	    
	public void corregirPosicionVentana() {
		thisStage.setX(singleton.posicionX + 200);
		thisStage.setY(singleton.posicionY + 120);
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
