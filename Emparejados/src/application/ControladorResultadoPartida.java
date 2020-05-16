package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class ControladorResultadoPartida {

    @FXML
    private Pane pane;
    
    @FXML
    private ImageView resultado;

    @FXML
    private Label puntuacionFinal;

    @FXML
    private Label tiempoRestante;

    @FXML
    private Button jugar;

    @FXML
    private Button salir;
    
    private AudioClip victoria;
    
    private AudioClip derrota;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Image icon;
    
    private boolean isVictoria;
    
    private String tipoPartida;
    
    private Singleton singleton;
    
    private boolean esNiveles;
    
    public void iniciarResultado(Stage stage, String puntuacion, String tiempo, boolean isVictoria, String tipoPartida, Singleton nuevoSingleton, boolean niveles){
    	primaryStage = stage;
        this.isVictoria = isVictoria;
        this.tipoPartida = tipoPartida;
        singleton = nuevoSingleton;
        esNiveles = niveles;
        inicializarVariables(puntuacion, tiempo);
        mostrarResultado();
        anyadirIcono();
        corregirTamanyoVentana();
        corregirPosicionVentana();
        actualizarEstilo();
    }
 
    public void inicializarVariables(String puntuacion, String tiempo) {
    	thisStage = (Stage) jugar.getScene().getWindow();
        victoria = new AudioClip(getClass().getResource("/sonidos/victoria.mp3").toString());
        derrota = new AudioClip(getClass().getResource("/sonidos/derrota1.mp3").toString());
        puntuacionFinal.setText(puntuacionFinal.getText() + puntuacion);
        String minutos = tiempo.substring(0, tiempo.length() - 3);
        String segundos = tiempo.substring(tiempo.length() - 2);
        tiempoRestante.setText(tiempoRestante.getText() + minutos + " min y " + segundos + "s");
        if(esNiveles) {
        	salir.setText("Salir a Niveles");
        }
    }
    
    public void mostrarResultado() {
    	if(isVictoria) {
    		if(singleton.soundOn) victoria.play();
            resultado.setImage(new Image("/imagenes/resultado_victoria.png"));
    	} else {
            if(singleton.soundOn) derrota.play();
            resultado.setImage(new Image("/imagenes/resultado_derrota.png"));
    	}
    }

    @FXML
    void jugarHandler(ActionEvent event) {
    	if(tipoPartida == "estandar") {
    		jugarPartidaEstandar();
    	} else if(tipoPartida == "carta") {
    		jugarPartidaCarta();
    	}
    }
    
    public void jugarPartidaEstandar() {
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
            controladorPartida.iniciarPartidaEstandar(primaryStage, singleton, "resultadoPartida", esNiveles);
            primaryStage.show();
        	thisStage.close();
    	} catch (IOException e) {}
    }
    
    public void jugarPartidaCarta() {
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
            controladorPartidaCarta.iniciarPartidaCarta(primaryStage, singleton, esNiveles);
            primaryStage.show();
        	thisStage.close();
    	} catch (IOException e) {}
    }

    @FXML
    void salirHandler(ActionEvent event) throws IOException {
    	if(esNiveles) {
    		restablecerPredeterminados();
    		primaryStage.close();
        	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/SeleccionNiveles.fxml"));
            Parent root = myLoader.load();  
            ControladorSeleccionNiveles seleccionNiveles = myLoader.<ControladorSeleccionNiveles>getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Seleccion de Niveles");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
            seleccionNiveles.iniciarSeleccionNiveles(primaryStage, singleton, "resultadoPartida");
            primaryStage.show();
            thisStage.close(); 
    	} else {
    		primaryStage.close();
        	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
            Parent root = myLoader.load();  
            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Menu Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
            menuPrincipal.iniciarMenuPrincipal(primaryStage, false, singleton, "resultadoPartida");
            primaryStage.show();
            thisStage.close(); 
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
    
    public void anyadirIcono() {
        icon = new Image("/imagenes/Icon.png");
        thisStage.getIcons().add(icon);
    }

    
    public void corregirTamanyoVentana() {
    	primaryStage.setWidth(895);
    	primaryStage.setHeight(627);
    }

    public void corregirPosicionVentana() {
    	thisStage.setX(singleton.posicionX);
    	thisStage.setY(singleton.posicionY + 30);
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
