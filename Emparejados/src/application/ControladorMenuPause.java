package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorMenuPause {

    @FXML
    private Pane pane;
    
    @FXML
    private StackPane circuloHome;

    @FXML
    private StackPane circuloPlay;

    @FXML
    private StackPane circuloSonido;
	
	@FXML
    private ImageView imagePlay;

    @FXML
    private ImageView imageHome;

    @FXML
    private ImageView imageSound;
    
    private Musica musicaFondo;
    
    private Stage primaryStage;
    
    private Stage thisStage;

    private boolean SoundOn;
    
    private ControladorPartidaEstandar partidaEstandar;
    
    private ControladorPartidaCarta partidaCarta;
    
    private ControladorPartidaLibre partidaLibre;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private Image icon;
    
    private long tiempoMusica;
    
    private String tipoPartida;
    
    Optional<ButtonType> resultadoSalida;
    
    private String estilo;
    
    private ArrayList<Baraja> listaBarajas;
    
    private Baraja barajaPartida;
    
    void initDataPartidaEstandar(Stage partida, ControladorPartidaEstandar partidaEstandar, boolean soundOn, double anteriorX, double anteriorY, String estilo, ArrayList<Baraja> lista, Baraja nuevaBaraja) {
    	primaryStage = partida;
    	this.partidaEstandar = partidaEstandar;
        SoundOn = soundOn;
        tipoPartida = "estandar";
        listaBarajas = lista;
        barajaPartida = nuevaBaraja;
        inicializarVariables();
        anyadirIcono();
        corregirTamanyoVentana();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana(anteriorX, anteriorY);
        actualizarEstilo(estilo);
    }
    
    void initDataPartidaCarta(Stage partida, ControladorPartidaCarta partidaCarta, boolean soundOn, double anteriorX, double anteriorY, String estilo, ArrayList<Baraja> lista, Baraja nuevaBaraja) {
    	primaryStage = partida;
    	this.partidaCarta = partidaCarta;
        SoundOn = soundOn;
        tipoPartida = "carta";
        listaBarajas = lista;
        barajaPartida = nuevaBaraja;
        inicializarVariables();
        anyadirIcono();
        corregirTamanyoVentana();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana(anteriorX, anteriorY);
        actualizarEstilo(estilo);
    }
  
    void initDataPartidaLibre(Stage partida, ControladorPartidaLibre partidaLibre, boolean soundOn, double anteriorX, double anteriorY, String estilo, ArrayList<Baraja> lista, Baraja nuevaBaraja) {
    	primaryStage = partida;
    	this.partidaLibre = partidaLibre;
        SoundOn = soundOn;
        tipoPartida = "libre";
        listaBarajas = lista;
        barajaPartida = nuevaBaraja;
        inicializarVariables();
        anyadirIcono();
        corregirTamanyoVentana();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana(anteriorX, anteriorY);
        actualizarEstilo(estilo);
    }
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/Musica3.wav", 0L);
        thisStage = (Stage) imageSound.getScene().getWindow();
        thisStage.setTitle("Menu de Pausa");
    }
    
    public void anyadirIcono() {
        icon = new Image("/imagenes/Icon.png");
        thisStage.getIcons().add(icon);
    }
    
    @FXML
    void clickHome(MouseEvent event) throws IOException {
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/ConfirmacionSalirMenuP.fxml"));
        Parent root = (Parent) myLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirmacion de salida");
        stage.setResizable(false);
        ControladorConfirmacionSalirMenuP controladorConfirmacionSalirMenuP = myLoader.<ControladorConfirmacionSalirMenuP>getController();
        controladorConfirmacionSalirMenuP.inicializarDatos(this, thisStage.getX(), thisStage.getY(), thisStage.getWidth(), thisStage.getHeight(), estilo);
        stage.show();
    }
    	
    
    public void volverMenuPrincipal() {
    	musicaFondo.stopMusic();
    	thisStage.close();
    	primaryStage.close();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
            Parent root = myLoader.load();  
            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Menu Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            menuPrincipal.iniciarMenuPrincipal(primaryStage, SoundOn, false, thisStage.getX(), thisStage.getY(), estilo, listaBarajas, barajaPartida);
            primaryStage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }
    }

    @FXML
    void clickPlay(MouseEvent event) throws IOException {
    	if(tipoPartida == "estandar") {
        	reanudarPartidaEstandar();
    	} else if(tipoPartida == "carta") {
    		reanudarPartidaCarta();
    	}
    }
    
    void reanudarPartidaEstandar() {
    	musicaFondo.stopMusic();
    	boolean victoria = partidaEstandar.isVictoria();
    	boolean derrota = partidaEstandar.isDerrota();
    	if (!derrota && !victoria) {
	    	thisStage.close();
	    	partidaEstandar.reanudarPartida(SoundOn, thisStage.getX(), thisStage.getY());
    	}
    	
    }
    
    void reanudarPartidaCarta() {
    	musicaFondo.stopMusic();
    	boolean victoria = partidaCarta.isVictoria();
    	boolean derrota = partidaCarta.isDerrota();
    	if (!derrota && !victoria) {
	    	thisStage.close();
	    	partidaCarta.reanudarPartida(SoundOn, thisStage.getX(), thisStage.getY());
    	}
    }
    
    void reanudarPartidaLibre() {
    	musicaFondo.stopMusic();
    	boolean victoria = partidaLibre.isVictoria();
    	boolean derrota = partidaLibre.isDerrota();
    	if (!derrota && !victoria) {
	    	thisStage.close();
	    	partidaLibre.reanudarPartida(SoundOn, thisStage.getX(), thisStage.getY());
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
        	imageSound.setImage(Sound1);
        } else {
        	imageSound.setImage(Sound0);
        }
    }
    
    public void corregirTamanyoVentana() {
    	thisStage.setWidth(900);
    	thisStage.setHeight(620);
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
    		pane.getStylesheets().remove(temaRojo);
    		pane.getStylesheets().remove(temaVerde);
    		pane.getStylesheets().add(temaAzul);
    		circuloHome.getStylesheets().remove(temaRojo);
    		circuloHome.getStylesheets().remove(temaVerde);
    		circuloHome.getStylesheets().add(temaAzul);
    		circuloPlay.getStylesheets().remove(temaRojo);
    		circuloPlay.getStylesheets().remove(temaVerde);
    		circuloPlay.getStylesheets().add(temaAzul);
    		circuloSonido.getStylesheets().remove(temaRojo);
    		circuloSonido.getStylesheets().remove(temaVerde);
    		circuloSonido.getStylesheets().add(temaAzul);
    	} else if(estilo.equals("Rojo")) {
    		pane.getStylesheets().remove(temaAzul);
			pane.getStylesheets().remove(temaVerde);
			pane.getStylesheets().add(temaRojo);
			circuloHome.getStylesheets().remove(temaAzul);
			circuloHome.getStylesheets().remove(temaVerde);
			circuloHome.getStylesheets().add(temaRojo);
			circuloPlay.getStylesheets().remove(temaAzul);
			circuloPlay.getStylesheets().remove(temaVerde);
			circuloPlay.getStylesheets().add(temaRojo);
			circuloSonido.getStylesheets().remove(temaAzul);
			circuloSonido.getStylesheets().remove(temaVerde);
			circuloSonido.getStylesheets().add(temaRojo);
    	} else {
    		pane.getStylesheets().remove(temaAzul);
			pane.getStylesheets().remove(temaRojo);
			pane.getStylesheets().add(temaVerde);
			circuloHome.getStylesheets().remove(temaAzul);
			circuloHome.getStylesheets().remove(temaRojo);
			circuloHome.getStylesheets().add(temaVerde);
			circuloPlay.getStylesheets().remove(temaAzul);
			circuloPlay.getStylesheets().remove(temaRojo);
			circuloPlay.getStylesheets().add(temaVerde);
			circuloSonido.getStylesheets().remove(temaAzul);
			circuloSonido.getStylesheets().remove(temaRojo);
			circuloSonido.getStylesheets().add(temaVerde);
    	}
    }

}
