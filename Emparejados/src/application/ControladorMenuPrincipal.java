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
    private Button jugar;
    
    @FXML
    private Button ajustes;

    @FXML
    private Button partidaEstandar;

    @FXML
    private Button modoLibre;

    @FXML
    private Button partidaCarta;
    
    @FXML
    private Button salir;
    
    @FXML
    private Button volverAtras;
    
    @FXML
    private ImageView iconoSonido;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Musica musicaFondo;
   
    private String[] musicas;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private boolean SoundOn;
    
    private long tiempoMusica;
    
    private String estilo;
    
    private ArrayList<Baraja> listaBarajas;
    
    private Baraja barajaPartida;

    public void iniciarMenuPrincipal(Stage stage, boolean soundOn, boolean primeraVez, double anteriorX, double anteriorY,String[] musicas, String estilo, ArrayList<Baraja> lista, Baraja nuevaBaraja){
        primaryStage = stage;
        SoundOn = soundOn;
        this.musicas = musicas;
        inicializarVariables();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        actualizarEstilo(estilo);
        muestraMenuP(true);
        if(primeraVez) { 
            centrarVentana();
            crearListaBarajas();
            barajaPartida = listaBarajas.get(0);
        } else {
            corregirPosicionVentana(anteriorX, anteriorY);
            listaBarajas = lista;
            barajaPartida = nuevaBaraja;
        }
    }
    
	public void crearListaBarajas() {
		listaBarajas = new ArrayList<Baraja>();
		Baraja barajaAnimales = new Baraja(4, 4);
    	barajaAnimales.barajaTematica(new CrearBarajaAnimalesEstrategia(2), 8);
    	listaBarajas.add(barajaAnimales);
    	Baraja barajaDeportes = new Baraja(4, 4);
    	barajaDeportes.barajaTematica(new CrearBarajaDeportesEstrategia(2), 8);
    	listaBarajas.add(barajaDeportes);
    	Baraja barajaNintendo = new Baraja(4, 4);
    	barajaNintendo.barajaTematica(new CrearBarajaNintendoEstrategia(2), 8);
    	listaBarajas.add(barajaNintendo);
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
    
    public void muestraMenuP(boolean b) {
    	jugar.setVisible(b);
    	ajustes.setVisible(b);
    	salir.setVisible(b);
    	partidaEstandar.setVisible(!b);
    	modoLibre.setVisible(!b);
    	partidaCarta.setVisible(!b);
    	volverAtras.setVisible(!b);
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
    void jugarHandler(ActionEvent event) {
    	muestraMenuP(false);
    	
    }
    
    @FXML
    void ajustesHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
    	tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuAjustes.fxml"));
    		Parent root = myLoader.load();  
    		ControladorMenuAjustes menuPrincipal = myLoader.<ControladorMenuAjustes>getController();
    		Scene scene = new Scene(root);
    		primaryStage.setTitle("Menu Ajustes");
    		primaryStage.setScene(scene);
    		primaryStage.setResizable(false);
        
    		Image icono = new Image("/imagenes/Icon.png");
    		primaryStage.getIcons().add(icono);
        
    		menuPrincipal.iniciarMenuAjustes(primaryStage, true, thisStage.getX(), thisStage.getY(),musicas, estilo, listaBarajas, barajaPartida);
    		primaryStage.show();
    	} catch(IOException e) {}
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
      		controladorPartida.iniciarPartidaEstandar(primaryStage, SoundOn, thisStage.getX(), thisStage.getY(), musicas, estilo, listaBarajas, barajaPartida);
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
      		controladorPartidaCarta.iniciarPartidaCarta(primaryStage, SoundOn, thisStage.getX(), thisStage.getY(), musicas, estilo, listaBarajas, barajaPartida);
      		primaryStage.show();
      	} catch (IOException e) {}
    }
    
    
    @FXML
    void modoLibreHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
    	tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
    	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/AjustesJuegoLibre.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorAjustesJuegoLibre controladorAjustesJLibre = myLoader.<ControladorAjustesJuegoLibre>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Ajustes del modo libre");
      		primaryStage.setResizable(false);
      		controladorAjustesJLibre.iniciarAjustesJLibre(primaryStage, SoundOn, tiempoMusica, thisStage.getX(), thisStage.getY(), musicas, estilo, listaBarajas, barajaPartida);
      		primaryStage.show();
      	} catch (IOException e) {
      		e.printStackTrace();
      	}
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
        ControladorConfirmacionSalirApp controladorConfirmacionSalirApp = myLoader.<ControladorConfirmacionSalirApp>getController();
        controladorConfirmacionSalirApp.inicializarDatos(thisStage.getX(), thisStage.getY(), thisStage.getWidth(), thisStage.getHeight(), estilo);
        stage.show();
    }
    
    @FXML
    void volverAtrasHandler(ActionEvent event) {
    	muestraMenuP(true);
    }

    public void corregirTamanyoVentana() {
    	thisStage.setWidth(910);
    	thisStage.setHeight(650);
    }
    
    public void corregirPosicionVentana(double anteriorX, double anteriorY) {
    	thisStage.setX(anteriorX);
    	thisStage.setY(anteriorY);
    }
    
    public void centrarVentana() {  
       Rectangle2D screen = Screen.getPrimary().getVisualBounds();
       primaryStage.setX((screen.getWidth() - primaryStage.getWidth()) / 2);
       primaryStage.setY((screen.getHeight() - primaryStage.getHeight()) / 2);
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
