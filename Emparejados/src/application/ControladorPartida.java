package application;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControladorPartida implements Initializable {
    
    @FXML
    private Label tiempo = new Label();

    @FXML
    private Label puntos = new Label();

    @FXML
    private Button pausa;
    
    @FXML
    private StackPane stackPane;

    @FXML
    private ImageView usuario;

    @FXML
    private ImageView carta00;

    @FXML
    private ImageView carta10;

    @FXML
    private ImageView carta20;

    @FXML
    private ImageView carta01;

    @FXML
    private ImageView carta30;

    @FXML
    private ImageView carta11;

    @FXML
    private ImageView carta21;

    @FXML
    private ImageView carta31;

    @FXML
    private ImageView carta02;

    @FXML
    private ImageView carta12;

    @FXML
    private ImageView carta22;

    @FXML
    private ImageView carta32;
    
    @FXML
    private ImageView carta03;

    @FXML
    private ImageView carta13;

    @FXML
    private ImageView carta23;

    @FXML
    private ImageView carta33;
    
    @FXML
    private ImageView resultado;
    
    private Baraja barajaPartida;
    
    private Tablero tableroPartida;
    
    private Carta primeraCarta;
    
    private Carta segundaCarta;
    
    private ImageView primeraImagen;
    
    private ImageView segundaImagen;
    
    private int cartasGiradas;
    
    private Image imagenDorso;
    
    private boolean esPrimeraCarta;
     
    private boolean victoria;

	private boolean derrota;
    
    private Stage primaryStage;
    
    private Scene primaryScene;
    
    private String primaryTitle;
    
    AudioClip voltearCarta;
    AudioClip Error;
    AudioClip Acierto;
    AudioClip Victoria;
    AudioClip Derrota;
    AudioClip MismaCarta;
    private Clip clip;

    private boolean soundOn;

	private boolean Pausa; 
	
	private long tiempoMusica = 0L;
	
	private int puntuacion;
	
	private Integer counter;
	
	private Timeline timeline;
	
	private StringProperty Time = new SimpleStringProperty("");
	
	private ArrayList<Integer> parejasFalladas;
	
	long tiempoPrimera;
	
	private static final int TIEMPO_PART_ESTANDAR = 60; //Tiempo para partida estándar, por defecto 1 minuto
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		crearBaraja();
    	crearTablero();
    	esPrimeraCarta = true;
    	cartasGiradas = 0;
    	timeline = new Timeline();
    	Pausa = false;
    	victoria = false;
    	derrota = false;
    	tiempoPrimera = 0;
    	parejasFalladas = new ArrayList<Integer>(tableroPartida.getNumParejas());
    	voltearCarta = new AudioClip(getClass().getResource("/sonidos/Voltear.mp3").toString());
        Error = new AudioClip(getClass().getResource("/sonidos/Error1.mp3").toString());
        Acierto = new AudioClip(getClass().getResource("/sonidos/Acierto.mp3").toString());
        Victoria = new AudioClip(getClass().getResource("/sonidos/Victoria.mp3").toString());
        Derrota = new AudioClip(getClass().getResource("/sonidos/Derrota1.mp3").toString());
        MismaCarta = new AudioClip(getClass().getResource("/sonidos/Error2.mp3").toString());
    	tiempo.textProperty().bind(Time);
    	try {
			playMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	iniciaTiempo(TIEMPO_PART_ESTANDAR);
    	resultado.setVisible(false);
	} 
    
    public void iniciarPartida(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        soundOn = true;
    }
    
    public void playMusic() throws Exception{
	    File Musica = new File("src/sonidos/Music2.wav");
	    AudioInputStream audioInput = AudioSystem.getAudioInputStream(Musica);
	    clip = AudioSystem.getClip();
	    clip.open(audioInput);
	    clip.setMicrosecondPosition(tiempoMusica);
	    clip.start();
	    clip.loop(clip.LOOP_CONTINUOUSLY); 
	}
    
    public void crearBaraja() {
    	imagenDorso = new Image("/imagenes/dorso_aldeano.png");
    	barajaPartida = new Baraja("Animales", imagenDorso, 16);
    	int index = 0;
    	for(int i = 0; i < 2; i++) {
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("/imagenes/elefante.png"), 0), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("/imagenes/hipopotamo.png"), 1), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("/imagenes/jirafa.png"), 2), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("/imagenes/leon.png"), 3), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("/imagenes/mono.png"), 4), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("/imagenes/rinoceronte.png"), 5), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("/imagenes/serpiente.png"), 6), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("/imagenes/zebra.png"), 7), index++);
    	}
    	barajaPartida.barajar();
    }
    
    public void crearTablero() {
    	tableroPartida = new Tablero(4);
    	tableroPartida.llenarTablero(barajaPartida);
    }

    @FXML
    void muestraCarta(MouseEvent event) {    	
    	cartasGiradas++;
    	ImageView imagenSeleccionada = (ImageView) event.getSource();
    	String nombreCarta = imagenSeleccionada.getId();
    	int posicionX = Integer.parseInt(nombreCarta.substring(5, 6));
    	int posicionY = Integer.parseInt(nombreCarta.substring(6, 7));
    	Carta cartaSeleccionada = tableroPartida.getCarta(posicionX, posicionY);
    	imagenSeleccionada.setImage(cartaSeleccionada.imagenFrente);
    	if(esPrimeraCarta) {
    		voltearCarta.play();
    		tiempoPrimera= System.currentTimeMillis();
    		primeraCarta = cartaSeleccionada;
    		primeraImagen = imagenSeleccionada;
    		esPrimeraCarta = false;
    	} else {
    		long tiempoSegunda= System.currentTimeMillis();
    		System.out.println("Tiempo de la primera: " + tiempoPrimera + " // Tiempo de la segunda:" + tiempoSegunda);
    		if (tiempoPrimera + 5000 <= tiempoSegunda) sumaPuntos(-5, false);
    		segundaCarta = cartaSeleccionada;
    		segundaImagen = imagenSeleccionada;
    		if(primeraCarta == segundaCarta) {
    			MismaCarta.play();
    		}else if(primeraCarta.getId() == segundaCarta.getId()) {
    				voltearCarta.play();
    				parejaCorrecta();
    				esPrimeraCarta = true;
    			} else {
    				voltearCarta.play();
    				parejaIncorrecta();
    				esPrimeraCarta = true;
    			}    			    	
    	}
    }
    
    public void parejaCorrecta() {
    	sumaPuntos(10, false);
    	Acierto.play();
    	//Sumar puntos
    	primeraImagen.setDisable(true);
    	segundaImagen.setDisable(true);
    	if(cartasGiradas == barajaPartida.getTamanyo()) {
    		victoria();
    	}
    }
    
    public void parejaIncorrecta() {
    	sumaPuntos(-1, true);
    	parejasFalladas.add(primeraCarta.getId());
    	Error.play();
    	//Restar puntos
    	primeraImagen.setImage(barajaPartida.getImagenDorso());
    	segundaImagen.setImage(barajaPartida.getImagenDorso());
    	cartasGiradas-= 2;
    }
    
    public int sumaPuntos(int p, boolean parInc) {
    	puntuacion += p;
    	if (parInc) puntuacion -= parejaIncRepetida(primeraCarta.getId());
    	if (puntuacion < 0) puntuacion = 0;
    	puntos.setText(Integer.toString(puntuacion));
    	return puntuacion;
    }
    
   public int parejaIncRepetida(int id) {
	   int res = 0;
	   if (parejasFalladas.contains(id)) {
		   ArrayList aux = (ArrayList) parejasFalladas.clone();
		   while(aux.contains(id)) {
			   aux.remove((Object)id);
			   res++;
		   }   
	   }
	   System.out.println("ha fallado la pareja: " + id + " Y resta un total de:" + res);
	   return res;
   }
    
    public void victoria() {
    	timeline.stop();
    	victoria = true;
    	Victoria.play();
    	clip.stop();
    	resultado.setImage(new Image("/imagenes/resultado_victoria.png"));
    	resultado.setVisible(true);
    	//Mensaje victoria
    }
    
    public boolean isVictoria() {
		return victoria;
	}
    
    public void derrota() {
    	derrota = true;
    	Derrota.play();
    	clip.stop();
    	resultado.setImage(new Image("/imagenes/resultado_derrota.png"));
    	resultado.setVisible(true);
    	stackPane.setDisable(true);
    	//Mensaje derrota
    }
    
    public boolean isDerrota() {
		return derrota;
	}

    @FXML
    void pausarPartida(ActionEvent event) throws Exception {
    	Pausa = true;
    	tiempoMusica = clip.getMicrosecondPosition();
    	clip.stop();
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPause.fxml"));
        Parent root = (Parent) myLoader.load();
        ControladorMenuPause controladorMenuPausa = myLoader.<ControladorMenuPause>getController();
        controladorMenuPausa.initData(primaryStage, soundOn);
        controladorMenuPausa.setControladorPartida(this);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("PAUSA");
        stage.setResizable(false);
        stage.show();
    }
    public void reanudarPartida(boolean Sound) {
    	Pausa = false;
    	//System.out.println("Reaunude la partida. Pausa: " + Pausa);
    	timeline.play();
    	//iniciaTiempo(tiempoPausa);
    	soundOn = Sound;
    	if(soundOn) {
    		clip.setMicrosecondPosition(tiempoMusica);
    		clip.start();
    		voltearCarta.setVolume(1.0);
    		Error.setVolume(1.0);
    		Acierto.setVolume(1.0);
    		Victoria.setVolume(1.0);
    		Derrota.setVolume(1.0);
    	}
    	else {
    		clip.stop();
    		voltearCarta.setVolume(0);
    		Error.setVolume(0);
    		Acierto.setVolume(0);
    		Victoria.setVolume(0);
    		Derrota.setVolume(0);
    	}
    }
    
    public void iniciaTiempo(int tpartida) {
    	 counter = tpartida; 
         // update timerLabel
         //tiempo.setText(counter.toString());
    	 setTimer(counter);
         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.getKeyFrames().add(
                 new KeyFrame(Duration.seconds(1),
                   new EventHandler() {
                     // KeyFrame event handler
                     public void handle(Event event) {
                    	 counter--;
                         // update timerLabel
                         //tiempo.setText(counter.toString());
                    	 setTimer(counter);
                         if (counter <= 0) {
                             timeline.stop();
                             derrota();
                         } else if(Pausa){
                        	 timeline.pause();
                         }
                       }
                 }));
         timeline.playFromStart();
     }
    
    public void setTimer(int seconds) {
    	int mins = seconds / 60;
    	int secs = seconds - mins * 60;
    	String Secs = String.format("%02d", secs);
    	Time.set(Integer.toString(mins) + ":" + Secs);
    	//System.out.println(Time);
    }


}
