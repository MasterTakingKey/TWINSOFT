package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorMenuPausaMultijugador {

    @FXML
    private Pane pane;

    @FXML
    private StackPane circuloHome;

    @FXML
    private ImageView imageHome;

    @FXML
    private StackPane circuloPlay;

    @FXML
    private ImageView imagePlay;

    @FXML
    private StackPane circuloSonido;

    @FXML
    private ImageView imageSound;

    @FXML
    private Label puntosJ2;

    @FXML
    private Label tiempo;

    @FXML
    private Label nombreJ2;

    @FXML
    private Label puntosJ1;

    @FXML
    private Label nombreJ1;
    
    private Musica musicaFondo;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private ControladorMultijugador multi;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private Image icon;
    
    private long tiempoMusica;
    
    private String tipoPartida;
    
    private ConfiguracionPartida singleton;
    
    private Boolean esNiveles;
    
    private int turnoActual;
 
    void initDataMultijugador(Stage partida, String tiempo, String puntosJ1, String puntosJ2, ControladorMultijugador multi, ConfiguracionPartida nuevoSingleton, String ventanaAnterior, Boolean niveles, int turno) {
    	esNiveles = niveles;
    	primaryStage = partida;
    	this.multi = multi;
    	singleton = nuevoSingleton;
        tipoPartida = "estandar";
        turnoActual = turno;
        actualizarTiempoYPuntos(tiempo, puntosJ1, puntosJ2);
        inicializarVariables();
        anyadirIcono();
        corregirTamanyoVentana();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana(ventanaAnterior);
        actualizarEstilo();
    }
    
    
    public void actualizarTiempoYPuntos(String tiempo, String puntosJ1, String puntosJ2) {
    	this.puntosJ1.setText(puntosJ1);
    	this.puntosJ2.setText(puntosJ2);
        this.tiempo.setText(tiempo);
    }
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("/sonidos/"+ singleton.listaMusica[2] +".wav", 0L);
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
        singleton.posicionX = thisStage.getX();
  		singleton.posicionY = thisStage.getY();
        controladorConfirmacionSalirMenuP.inicializarDatosMulti(this, thisStage.getWidth(), thisStage.getHeight(), singleton);
        stage.show();
    }
    	
    
    public void volverMenuPrincipal() {
    	if(esNiveles) restablecerPredeterminados();    	
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
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
            menuPrincipal.iniciarMenuPrincipal(primaryStage, false, singleton, "menuPausa");
            primaryStage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }
    }

    @FXML
    void clickPlay(MouseEvent event) throws IOException {
        	reanudarMulti();
    	
    }
    
    void reanudarMulti() {
    	musicaFondo.stopMusic();
    	boolean victoria = multi.isVictoria();
    	boolean derrota = multi.isDerrota();
    	if (!derrota && !victoria) {
    		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
	    	thisStage.close();
	    	multi.reanudarPartida(singleton.soundOn, "menuPausa", turnoActual);
    	}
    	
    }
    
    
    public void restablecerPredeterminados() {
    	
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

    @FXML
    void clickSound(MouseEvent event) {
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
        	imageSound.setImage(Sound1);
        } else {
        	imageSound.setImage(Sound0);
        }
    }
    
    public void corregirTamanyoVentana() {
    	thisStage.setWidth(900);
    	thisStage.setHeight(620);
    }
    
    public void corregirPosicionVentana(String ventanaAnterior) {
    	if(ventanaAnterior.equals("partidaEstandar")) {
        	if(singleton.filasPartida <= 4 && singleton.columnasPartida <= 4) {
            	thisStage.setX(singleton.posicionX);
            	thisStage.setY(singleton.posicionY + 50);
        	} else {
        		thisStage.setX(singleton.posicionX + 250);
            	thisStage.setY(singleton.posicionY + 100);
        	}
    	} else if(ventanaAnterior.equals("partidaCarta")) {
    		if(singleton.filasPartida <= 4 && singleton.columnasPartida <= 4) {
    	    	thisStage.setX(singleton.posicionX);
    	    	thisStage.setY(singleton.posicionY + 150);
        	} else {
        		thisStage.setX(singleton.posicionX + 250);
            	thisStage.setY(singleton.posicionY + 200);
        	}
    	}

    }
    
    public void actualizarEstilo() {
    	Estilo nuevoEstilo;
    	String temaAzul = getClass().getResource("estiloAzul.css").toExternalForm();
        String temaRojo = getClass().getResource("estiloRojo.css").toExternalForm();
        String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
        if(singleton.estilo.equals("Azul")) {
            nuevoEstilo = new Estilo(new EstrategiaEstiloAzul());
            circuloHome.getStylesheets().remove(temaRojo);
            circuloHome.getStylesheets().remove(temaVerde);
            circuloHome.getStylesheets().add(temaAzul);
            circuloPlay.getStylesheets().remove(temaRojo);
            circuloPlay.getStylesheets().remove(temaVerde);
            circuloPlay.getStylesheets().add(temaAzul);
        } else if(singleton.estilo.equals("Rojo")) {
            nuevoEstilo = new Estilo(new EstrategiaEstiloRojo());
            circuloHome.getStylesheets().remove(temaAzul);
            circuloHome.getStylesheets().remove(temaVerde);
            circuloHome.getStylesheets().add(temaRojo);
            circuloPlay.getStylesheets().remove(temaAzul);
            circuloPlay.getStylesheets().remove(temaVerde);
            circuloPlay.getStylesheets().add(temaRojo);
        } else {
            nuevoEstilo = new Estilo(new EstrategiaEstiloVerde());
            circuloHome.getStylesheets().remove(temaAzul);
            circuloHome.getStylesheets().remove(temaRojo);
            circuloHome.getStylesheets().add(temaVerde);
            circuloPlay.getStylesheets().remove(temaAzul);
            circuloPlay.getStylesheets().remove(temaRojo);
            circuloPlay.getStylesheets().add(temaVerde);
        }
        nuevoEstilo.cambiarEstilo(pane, null, circuloSonido);
    }

}

