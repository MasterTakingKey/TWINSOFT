package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ControladorMenuAjustes {

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private StackPane circuloSonido;
    
	@FXML
	private Button editorBarajas;

	@FXML
	private ImageView iconoSonido;

	@FXML
	private ChoiceBox<String> musicaPartida;

	@FXML
	private ChoiceBox<String> musicaMenuP;

	@FXML
	private ChoiceBox<String> musicaPausa;

	@FXML
	private ChoiceBox<String> tema;
	
    @FXML
    private ChoiceBox<String> barajaPartida;

	@FXML
	private Button salir;
	
    @FXML
    private Button cancelarySalir;

    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Musica musicaFondo;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private boolean SoundOn;
    
    private long tiempoMusica;
    
    private String[] musicas; 
    
    //Nombres que se visualizaran en el selector
    String musica1 = "Musica1";
	String musica2 = "Musica2";
	String musica3 = "Musica3";
	String musica4 = "Super Mario Bros Theme";
	String musica5 = "Summer Remix";
	String musica6 = "Dubstep song ";
	String musica7 = "Wii Main Theme";
	String musica8 = "Wii Sports Theme";
    
    private String estilo;
    
    private ArrayList<Baraja> listaBarajas;
    
    private Baraja barajaP;

    public void iniciarMenuAjustes(Stage stage, boolean soundOn, double anteriorX, double anteriorY, String[] musicas, String estilo, ArrayList<Baraja> lista, Baraja nuevaBaraja){
    	this.musicas = musicas;
        primaryStage = stage;
        SoundOn = soundOn;
        listaBarajas = lista;
        barajaP = nuevaBaraja;
        inicializarVariables();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana(anteriorX, anteriorY);
        actualizarEstilo(estilo);
        inicializarChoiceBoxMusica();
        inicializarTemas();
        inicializarBarajaPartida();
    }

    public void inicializarChoiceBoxMusica() {
    	cargaMusica(musicaPartida, 0);
    	cargaMusica(musicaMenuP, 1);
    	cargaMusica(musicaPausa, 2); 
    }
      
    public void inicializarTemas() {
    	tema.getItems().add("Azul");
    	tema.getItems().add("Rojo");
    	tema.getItems().add("Verde");
    	if(estilo.equals("Azul")) {
        	tema.getSelectionModel().select(0);
    	} else if(estilo.equals("Rojo")) {
        	tema.getSelectionModel().select(1);
    	} else {
        	tema.getSelectionModel().select(2);
    	}
    }
    
    public void inicializarBarajaPartida() {
    	try {
        	int i = 0;
    		while(listaBarajas.get(i) != null) {
    			barajaPartida.getItems().add(listaBarajas.get(i).getNombre());
    			if(listaBarajas.get(i).getNombre().equals(barajaP.getNombre())) barajaPartida.getSelectionModel().select(i);
    			i++;
    		}	
    	} catch(Exception e) {}
    }
    
    
    public void cargaMusica(ChoiceBox<String> menu, int musica) { 
    	
    	menu.setItems(FXCollections.observableArrayList(
    			musica1, musica2, musica3, musica4, musica5, musica6, musica7, musica8)
    		);
    	menu.setTooltip(new Tooltip("Selecciona la cancion que quieres para este menu"));
    	int select;
    	switch (musicas[musica]) {
    		case "Musica1": select = 0; break;
    		case "Musica2": select = 1; break;
    		case "Musica3": select = 2; break;
    		case "Musica4": select = 3; break;
    		case "Musica5": select = 4; break;
    		case "Musica6": select = 5; break;
    		case "Musica7": select = 6; break;
    		default: select = 7;
    		
    	}
    	menu.getSelectionModel().select(select);
    }
    
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/"+ musicas[1] +".wav", 0L);
        thisStage = (Stage) salir.getScene().getWindow();
    }
    
    public void actualizarSonido() {
    	if(SoundOn) {
    		musicaFondo.getClip().setMicrosecondPosition(tiempoMusica);
    		musicaFondo.playMusic();
    	}
    	else {
    		musicaFondo.stopMusic();
    	}
    }
    
    public void actualizarImagenSonido() {
        if(SoundOn) {
        	iconoSonido.setImage(Sound1);
        } else {
        	iconoSonido.setImage(Sound0);
        }
    }

    @FXML
    void clickSound(MouseEvent event) {
    	if(SoundOn) {
    		SoundOn = false;
    		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
    	} else {
    		SoundOn = true;
    	}
    	actualizarSonido();
    	actualizarImagenSonido();
    }
    
    @FXML
    void editorBarajasHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/EditorBarajaDorso.fxml"));
            Parent root = myLoader.load();  
            Scene scene = new Scene(root); 
            Stage stage = new Stage();                       
            stage.setTitle("Seleccione el dorso");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            EditorBarajaDorsoController editorDorso = myLoader.<EditorBarajaDorsoController>getController(); 
            actualizaMusicas();
            editorDorso.iniciarEditorDorso(primaryStage, thisStage.getX(), thisStage.getY(), musicas, tema.getSelectionModel().getSelectedItem(), listaBarajas);
            stage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }
    }
    @FXML
    void cancelarYSalirHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
            Parent root = myLoader.load();  
            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Menu Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            menuPrincipal.iniciarMenuPrincipal(primaryStage, SoundOn, false, thisStage.getX(), thisStage.getY(),musicas, estilo, listaBarajas, barajaP);
            primaryStage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }
    }

    @FXML
    void salirHandler(ActionEvent event) throws IOException {
    	musicaFondo.stopMusic();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
            Parent root = myLoader.load();  
            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Menu Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            actualizaMusicas();
            menuPrincipal.iniciarMenuPrincipal(primaryStage, SoundOn, false, thisStage.getX(), thisStage.getY(),musicas, tema.getSelectionModel().getSelectedItem(), listaBarajas, deNombreABaraja());
            primaryStage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }
    }
    public void actualizaMusicas() {
    	musicas[0] = traduceMusica(musicaPartida.getSelectionModel().getSelectedItem());
    	musicas[1] = traduceMusica(musicaMenuP.getSelectionModel().getSelectedItem());
    	musicas[2] = traduceMusica(musicaPausa.getSelectionModel().getSelectedItem());
    }
    
    public String traduceMusica(String m) {
    	if (musica1 == m)
    		return "Musica1";
    	else if (musica2 == m)
    		return "Musica2";
    	else if (musica3 == m)
    		return "Musica3";
    	else if (musica4 == m)
    		return "Musica4";
    	else if (musica5 == m)
    		return "Musica5";
    	else if (musica6 == m)
    		return "Musica6";
    	else if (musica7 == m)
    		return "Musica7";
    	else
    		return "Musica8";
	}
    
    
    public Baraja deNombreABaraja() {
    	int i = 0;
    	while(listaBarajas.get(i) != null) {
    		if(barajaPartida.getSelectionModel().getSelectedItem().equals(listaBarajas.get(i).getNombre())) {
    			return listaBarajas.get(i);
    		}
    		i++;
    	}
    	return null;
    }
    
    public void corregirTamanyoVentana() {
    	thisStage.setWidth(900);
    	thisStage.setHeight(650);
    }
    
    public void corregirPosicionVentana(double anteriorX, double anteriorY) {
    	thisStage.setX(anteriorX);
    	thisStage.setY(anteriorY);
    }
    
    
    public void actualizarEstilo(String nuevoEstilo) {
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
    }
    
}
