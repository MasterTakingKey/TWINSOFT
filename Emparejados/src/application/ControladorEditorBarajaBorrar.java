package application;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;
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
        corregirPosicionVentana();
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
        	Iterator<Baraja> iterator = singleton.listaBarajas.listIterator();
        	for(int i = 0; i < 3; i++) iterator.next();
    		
        	while(iterator.hasNext()) {
    			listadoBarajas.getItems().add(iterator.next().getNombre());
    		}
    		listadoBarajas.getSelectionModel().select(0);
	}


	@FXML
	public void BorrarBaraja(MouseEvent event) {
		ListIterator<Baraja> iterator = singleton.listaBarajas.listIterator();
		
		for(int i = 0; i < 3; i++) iterator.next();
		
    	while(iterator.hasNext()) {
    		int indice = iterator.nextIndex();
    		
    		if(listadoBarajas.getSelectionModel().getSelectedItem().equals(iterator.next().getNombre())) {
    			
    			if(singleton.barajaPartida.nombre.equals(listadoBarajas.getSelectionModel().getSelectedItem())) {
    				singleton.barajaPartida = singleton.listaBarajas.get(0);
    			}
    			String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "TWINS" + File.separator + "barajasPersonalizadas" + File.separator;
    			File file = new File(path+singleton.listaBarajas.get(indice).getNombre());
    			deleteDirectory(file);
    			iterator.remove();
    			listadoBarajas.getItems().remove(listadoBarajas.getSelectionModel().getSelectedIndex());
    			listadoBarajas.getSelectionModel().select(0);
    			break;
    		}
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
