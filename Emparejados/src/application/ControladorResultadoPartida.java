package application;

import java.io.IOException;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

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
    
    @FXML
    private ImageView confetiVictoria;
    
    @FXML
    private ImageView imagenDerrota;
    
    @FXML
    private ImageView applauseVictoria;
    
    @FXML
    private Label puntuacionFinalJ2;

    @FXML
    private Label Ganador;
    
    private AudioClip victoria;
    
    private AudioClip applauseSound;
    
    private AudioClip derrota;
    
    private AudioClip explosion;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Image icon;
    
    private boolean isVictoria;
    
    private String tipoPartida;
    
    private ConfiguracionPartida singleton;
    
    private Animaciones animacionVictoria;
    
    private boolean esNiveles;
    
    private int nivel;
    
    private String nombreJ1;
    
    private String nombreJ2;
    
    public void iniciarResultado(Stage stage, String puntuacion, String puntuacionJ2,String tiempo, boolean isVictoria, String tipoPartida, ConfiguracionPartida nuevoSingleton, String ventanaAnterior, boolean niveles, int nuevoNivel, String nombreJ1, String nombreJ2){
    	primaryStage = stage;
        this.isVictoria = isVictoria;
        this.tipoPartida = tipoPartida;
        singleton = nuevoSingleton;
        esNiveles = niveles;
        nivel = nuevoNivel;
        this.nombreJ1 = nombreJ1;
        this.nombreJ2 = nombreJ2;
        inicializarVariables(puntuacion, tiempo);
        inicializarAnimaciones();
        actualizarNiveles();
        mostrarResultado();
        anyadirIcono();
        corregirTamanyoVentana();
        corregirPosicionVentana(ventanaAnterior);
        actualizarEstilo();
        if(tipoPartida == "multi") {
        	actualizarMulti(puntuacion, puntuacionJ2);
        }
    }
 
    private void inicializarAnimaciones() {
    	Image GifVictoria = new Image (getClass().getResource("/imagenes/confetti.gif").toExternalForm());
		confetiVictoria.setImage(GifVictoria);
    	confetiVictoria.setVisible(isVictoria);
    	Image ApplauseVictoria = new Image (getClass().getResource("/imagenes/applause.gif").toExternalForm());
    	applauseVictoria.setImage(ApplauseVictoria);
    	applauseVictoria.setVisible(isVictoria);
    	Image GifDerrota = new Image (getClass().getResource("/imagenes/explosion.gif").toExternalForm());
    	imagenDerrota.setImage(GifDerrota);
    	imagenDerrota.setVisible(!isVictoria);
    	FabricaAnimaciones[] fabrica;
       	fabrica = new FabricaAnimaciones[2];
       	fabrica[0] = new FabricaAnimacionVictoria();
       	animacionVictoria = fabrica[0].animacionesMetodoFabrica();
       	animacionVictoria.pane = pane;
    	animacionVictoria.imagen1 = confetiVictoria;
	}

	public void inicializarVariables(String puntuacion, String tiempo) {
    	thisStage = (Stage) jugar.getScene().getWindow();
        victoria = new AudioClip(getClass().getResource("/sonidos/victoria.mp3").toString());
        applauseSound = new AudioClip(getClass().getResource("/sonidos/applause.wav").toString());
        derrota = new AudioClip(getClass().getResource("/sonidos/derrota1.mp3").toString());
        explosion = new AudioClip(getClass().getResource("/sonidos/Explosion.wav").toString());
        puntuacionFinal.setText(puntuacionFinal.getText() + puntuacion);
        String minutos = tiempo.substring(0, tiempo.length() - 3);
        String segundos = tiempo.substring(tiempo.length() - 2);
        tiempoRestante.setText(tiempoRestante.getText() + minutos + " min y " + segundos + "s");
        if(esNiveles) {
        	salir.setText("Salir a Niveles");
        }
    }
	
	public void actualizarMulti(String puntuacion, String puntuacionJ2) {
		puntuacionFinal.setText("Puntos " + nombreJ1 + ": " + puntuacion);
		puntuacionFinalJ2.setText("Puntos " + nombreJ2 + ": " + puntuacionJ2);
		if(isVictoria) {
			if(Integer.parseInt(puntuacion) > Integer.parseInt(puntuacionJ2)) {
				Ganador.setText(nombreJ1 + " GANA!");
			} else if(Integer.parseInt(puntuacion) < Integer.parseInt(puntuacionJ2)) {
				Ganador.setText(nombreJ2 + " GANA!");
			} else {
				Ganador.setText("EMPATE!");
			}
		}
	}
    
    public void mostrarResultado() {
    	if(isVictoria) {
    		animacionVictoria.crearAnimacion();
    		if(singleton.soundOn) {victoria.play(); applauseSound.play();}
            resultado.setImage(new Image("/imagenes/resultado_victoria.png"));
            
    	} else {
            if(singleton.soundOn) {derrota.play(); explosion.play();}
            resultado.setImage(new Image("/imagenes/resultado_derrota.png"));
          
    	}
    	  PauseTransition pause = new PauseTransition(Duration.seconds(2));
          pause.setOnFinished(e -> {
              imagenDerrota.setVisible(false);
              applauseVictoria.setVisible(false);
              applauseSound.stop();
          });  
          pause.play();
    }

    @FXML
    void jugarHandler(ActionEvent event) {
    	if(tipoPartida == "estandar") {
    		jugarPartidaEstandar();
    	} else if(tipoPartida == "carta") {
    		jugarPartidaCarta();
    	} else if(tipoPartida == "multi") {
    		jugarMultijugador();
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
            controladorPartida.iniciarPartidaEstandar(primaryStage, singleton, "resultadoPartida", esNiveles, nivel);
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
            controladorPartidaCarta.iniciarPartidaCarta(primaryStage, singleton, "menuPrincipal", esNiveles, nivel);
            primaryStage.show();
        	thisStage.close();
    	} catch (IOException e) {}
    }
    
    public void jugarMultijugador() {
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/Multijugador.fxml"));
            Parent root = (Parent) myLoader.load();
            ControladorMultijugador controladorMultijugador = myLoader.<ControladorMultijugador>getController();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Partida Por Carta");
            primaryStage.setResizable(false);
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
            controladorMultijugador.iniciarMultijugador(primaryStage, singleton, "menuPrincipal", esNiveles, nivel, nombreJ1, nombreJ2);
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
    
    public void actualizarNiveles() {
    	if(isVictoria) {
    		if(nivel == singleton.nivelesDesbloqueados) {
    			singleton.nivelesDesbloqueados++;
    			 try {
    		            GuardarDatosPartida.save(singleton.nivelesDesbloqueados, "niveles.save");
    		        }
    		     catch (Exception e) {}
    		}
    	}
    }
    
    public void anyadirIcono() {
        icon = new Image("/imagenes/Icon.png");
        thisStage.getIcons().add(icon);
    }

    
    public void corregirTamanyoVentana() {
    	primaryStage.setWidth(895);
    	primaryStage.setHeight(627);
    }

    public void corregirPosicionVentana(String ventanaAnterior) {
    	if(ventanaAnterior.equals("partidaEstandar")) {
        	if(singleton.filasPartida <= 4 && singleton.columnasPartida <= 4) {
            	thisStage.setX(singleton.posicionX);
            	thisStage.setY(singleton.posicionY + 30);
        	} else {
        		thisStage.setX(singleton.posicionX + 200);
            	thisStage.setY(singleton.posicionY + 50);
        	}
    	} else if(ventanaAnterior.equals("partidaCarta")) {
    		if(singleton.filasPartida <= 4 && singleton.columnasPartida <= 4) {
    	    	thisStage.setX(singleton.posicionX);
    	    	thisStage.setY(singleton.posicionY + 30);
        	} else {
        		thisStage.setX(singleton.posicionX + 250);
            	thisStage.setY(singleton.posicionY + 200);
        	}
    	}
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
