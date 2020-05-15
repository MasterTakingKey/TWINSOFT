package application;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ControladorMenuPrincipal {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private StackPane circuloSonido;

	@FXML
	private ImageView iconoSonido;

	@FXML
	private Button salir;
	
	@FXML
	private Button partidaEstandar;

	@FXML
	private Button partidaCarta;

	@FXML
	private Button partidaNiveles;

	@FXML
	private Button partidaMultijugador;

	@FXML
	private Button editorBarajas;

	@FXML
	private Button ajustes;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Musica musicaFondo;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private long tiempoMusica;
    
    private Singleton singleton;

    public void iniciarMenuPrincipal(Stage stage, boolean primeraVez, Singleton nuevoSingleton){
        primaryStage = stage;
        if(primeraVez) { 
            inicializarSingleton();
        } else {
            singleton = nuevoSingleton;
        }
        inicializarVariables();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana();
        actualizarEstilo();
    }
    
	
	public void inicializarSingleton() {
		
    	singleton = Singleton.Instance();
    	
    	singleton.listaBarajas = new ArrayList<Baraja>();
		Baraja barajaAnimales = new Baraja(4, 4);
    	barajaAnimales.barajaTematica(new CrearBarajaAnimalesEstrategia(2, 8));
    	singleton.listaBarajas.add(barajaAnimales);
    	Baraja barajaDeportes = new Baraja(4, 4);
    	barajaDeportes.barajaTematica(new CrearBarajaDeportesEstrategia(2, 8));
    	singleton.listaBarajas.add(barajaDeportes);
    	Baraja barajaNintendo = new Baraja(4, 4);
    	barajaNintendo.barajaTematica(new CrearBarajaNintendoEstrategia(2, 8));
    	singleton.listaBarajas.add(barajaNintendo);
    	
    	singleton.estilo = "Azul";
    	
    	String[] musicas = new String[3];
        musicas[0] = "Musica1";
        musicas[1] = "Musica2";
        musicas[2] = "Musica3";
        singleton.listaMusica = musicas;
        
        singleton.soundOn = true;
    	
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        singleton.posicionX = (screen.getWidth() - 1050) / 2;
        singleton.posicionY = (screen.getHeight() - 900) / 2;
        	
    	singleton.barajaPartida = singleton.listaBarajas.get(0);
        
        singleton.filasPartida = 4;
    	singleton.columnasPartida = 4;
    	
    	singleton.limiteTiempoOn = true;
    	singleton.tiempoPartida = 60;
    	
    	singleton.mostrarCartasOn = true;
    	singleton.tiempoMostrarCartas = 2;
    	
    	singleton.efectosSonorosVoltear = "Voltear";
    	singleton.efectosSonorosPareja = "Acierto";
    	singleton.efectosVisualesVoltear = "Giro";
    	singleton.efectosVisualesPareja = "Salto";
	}
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/"+ singleton.listaMusica[1] +".wav", 0L);
        thisStage = (Stage) anchorPane.getScene().getWindow();
    }

    @FXML
    void partidaEstandarHandler(ActionEvent event) {
      	musicaFondo.stopMusic();
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaEstandar.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaEstandar controladorPartida = myLoader.<ControladorPartidaEstandar>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Partida Estandar");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartida.iniciarPartidaEstandar(primaryStage, singleton);
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void partidaCartaHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaCarta.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaCarta controladorPartidaCarta = myLoader.<ControladorPartidaCarta>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Partida Por Carta");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartidaCarta.iniciarPartidaCarta(primaryStage, singleton);
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void partidaNivelesHandler(ActionEvent event) {

    }
    
    @FXML
    void partidaMultijugadorHandler(ActionEvent event) {

    }  
    
    @FXML
    void editorBarajasHandler(ActionEvent event) {
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
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
            editorDorso.iniciarEditorDorso(primaryStage, singleton, true);
            stage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }
    } 
    
    
    @FXML
    void ajustesHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
    	tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuAjustes.fxml"));
    		Parent root = myLoader.load();  
    		ControladorMenuAjustes menuAjustes = myLoader.<ControladorMenuAjustes>getController();
    		Scene scene = new Scene(root);
    		primaryStage.setTitle("Menu Ajustes");
    		primaryStage.setScene(scene);
    		primaryStage.setResizable(false);
        
    		Image icono = new Image("/imagenes/Icon.png");
    		primaryStage.getIcons().add(icono);
        
    		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
    		menuAjustes.iniciarMenuAjustes(primaryStage, singleton);
    		primaryStage.show();
    	} catch(IOException e) {}
    }

    
    @FXML
    void salirHandler(ActionEvent event) throws IOException {
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/ConfirmacionSalirApp.fxml"));
        Parent root = (Parent) myLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirmacion de salida");
        stage.setResizable(false);
        singleton.posicionX = thisStage.getX();
  		singleton.posicionY = thisStage.getY();
        ControladorConfirmacionSalirApp controladorConfirmacionSalirApp = myLoader.<ControladorConfirmacionSalirApp>getController();
        controladorConfirmacionSalirApp.inicializarDatos(thisStage.getWidth(), thisStage.getHeight(), singleton);
        stage.show();
    }
    
    @FXML
    void sonidoHandler(MouseEvent event) {
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
    	thisStage.setWidth(1050);
    	thisStage.setHeight(820);
    }
    
    public void corregirPosicionVentana() {
    	thisStage.setX(singleton.posicionX);
    	thisStage.setY(singleton.posicionY);
    }
    
    public void actualizarEstilo() {
    	String temaAzul = getClass().getResource("estiloAzul.css").toExternalForm();
        String temaRojo = getClass().getResource("estiloRojo.css").toExternalForm();
        String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
    	if(singleton.estilo.equals("Azul")) {
    		anchorPane.getStylesheets().remove(temaRojo);
    		anchorPane.getStylesheets().remove(temaVerde);
    		anchorPane.getStylesheets().add(temaAzul);
    		circuloSonido.getStylesheets().remove(temaRojo);
    		circuloSonido.getStylesheets().remove(temaVerde);
    		circuloSonido.getStylesheets().add(temaAzul);
    	} else if(singleton.estilo.equals("Rojo")) {
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
