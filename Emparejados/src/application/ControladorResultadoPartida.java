package application;

import java.io.IOException;
import java.util.ArrayList;

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
    
    private boolean soundOn;
    
    private Image icon;
    
    private boolean isVictoria;
    
    private String tipoPartida;
    
    private String[] musicas; 
    
    private String estilo;
    
    private int filas;
    
    private int columnas;
    
    private ArrayList<Baraja> listaBarajas;
    
    private Baraja barajaPartida;
    
    public void iniciarResultado(Stage stage, boolean soundOn, String puntuacion, String tiempo, boolean isVictoria, String tipoPartida, double anteriorX, double anteriorY, int filas, int columnas, String[] musicas, String estilo, ArrayList<Baraja> lista, Baraja nuevaBaraja){
    	this.musicas = musicas;
    	primaryStage = stage;
        this.soundOn = soundOn;
        this.isVictoria = isVictoria;
        this.tipoPartida = tipoPartida;
        this.filas = filas;
        this.columnas = columnas;
        listaBarajas = lista;
        barajaPartida = nuevaBaraja;
        inicializarVariables(puntuacion, tiempo);
        mostrarResultado();
        anyadirIcono();
        corregirTamanyoVentana();
        corregirPosicionVentana(anteriorX, anteriorY);
        actualizarEstilo(estilo);
    }
 
    public void inicializarVariables(String puntuacion, String tiempo) {
    	thisStage = (Stage) jugar.getScene().getWindow();
        victoria = new AudioClip(getClass().getResource("/sonidos/victoria.mp3").toString());
        derrota = new AudioClip(getClass().getResource("/sonidos/derrota1.mp3").toString());
        puntuacionFinal.setText(puntuacionFinal.getText() + puntuacion);
        String minutos = tiempo.substring(0, tiempo.length() - 3);
        String segundos = tiempo.substring(tiempo.length() - 2);
        tiempoRestante.setText(tiempoRestante.getText() + minutos + " min y " + segundos + "s");
    }
    
    public void mostrarResultado() {
    	if(isVictoria) {
    		if(soundOn) victoria.play();
            resultado.setImage(new Image("/imagenes/resultado_victoria.png"));
    	} else {
            if(soundOn) derrota.play();
            resultado.setImage(new Image("/imagenes/resultado_derrota.png"));
    	}
    }
    
    public void anyadirIcono() {
        icon = new Image("/imagenes/Icon.png");
        thisStage.getIcons().add(icon);
    }

    @FXML
    void jugarHandler(ActionEvent event) {
    	if(tipoPartida == "estandar") {
    		jugarPartidaEstandar();
    	} else if(tipoPartida == "carta") {
    		jugarPartidaCarta();
    	}else if(tipoPartida == "libre") {
    		jugarPartidaLibre(filas, columnas);
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
            controladorPartida.iniciarPartidaEstandar(primaryStage, soundOn, thisStage.getX(), thisStage.getY(),musicas, estilo, listaBarajas, barajaPartida);
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
            controladorPartidaCarta.iniciarPartidaCarta(primaryStage, soundOn, thisStage.getX(), thisStage.getY(),musicas, estilo, listaBarajas, barajaPartida);
            primaryStage.show();
        	thisStage.close();
    	} catch (IOException e) {}
    }
    
    public void jugarPartidaLibre(int filas, int columnas) {
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaLibre.fxml"));
            Parent root = (Parent) myLoader.load();
            ControladorPartidaLibre controladorPartidaLibre = myLoader.<ControladorPartidaLibre>getController();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Partida Por Carta");
            primaryStage.setResizable(false);
            controladorPartidaLibre.iniciarPartidaLibre(primaryStage, soundOn, thisStage.getX(), thisStage.getY(), filas, columnas,musicas, estilo, listaBarajas, barajaPartida);
            primaryStage.show();
        	thisStage.close();
    	} catch (IOException e) {}
    }

    @FXML
    void salirHandler(ActionEvent event) throws IOException {
    	primaryStage.close();
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
        Parent root = myLoader.load();  
        ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Menu Principal");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        menuPrincipal.iniciarMenuPrincipal(primaryStage, soundOn, false, thisStage.getX(), thisStage.getY(),musicas, estilo, listaBarajas, barajaPartida);
        primaryStage.show();
        thisStage.close(); 
    }
    
    public void corregirTamanyoVentana() {
    	primaryStage.setWidth(895);
    	primaryStage.setHeight(627);
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
    	} else if(estilo.equals("Rojo")) {
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
