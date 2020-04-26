package application;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
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
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class ControladorPartida implements Initializable {
    
	private static final int TIEMPO_PART_ESTANDAR = 60; //Tiempo para partida estándar, por defecto 1 minuto
	
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
    
    private Stage primaryStage;
    
    private Tablero tableroPartida;
    
    private Baraja barajaPartida;
  
    private Carta primeraCarta;
    
    private Carta segundaCarta;
    
    private ImageView primeraImagen;
    
    private ImageView segundaImagen;
    
    private Image imagenDorso;
    
    private int cartasGiradas;
    
	private int puntuacion;
	
	private Integer counter;
	
	private ArrayList<Integer> parejasFalladas;
	
	private long tiempoMusica = 0L;
	
	private long tiempoPrimera;
    
    private boolean esPrimeraCarta;
     
    private boolean esVictoria;

	private boolean esDerrota;
	
    private boolean soundOn;

	private boolean esPausa;
    
    private AudioClip voltearCarta;
    
    private AudioClip error;
    
    private AudioClip acierto;
    
    private AudioClip victoria;
    
    private AudioClip derrota;
    
    private AudioClip mismaCarta;
    
    private Clip clip; 
	
	private Timeline timeline;
	
	private StringProperty Time = new SimpleStringProperty("");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		crearBaraja();
    	crearTablero();
    	esPrimeraCarta = true;
    	cartasGiradas = 0;
    	timeline = new Timeline();
    	esPausa = false;
    	esVictoria = false;
    	esDerrota = false;
    	tiempoPrimera = 0;
    	parejasFalladas = new ArrayList<Integer>(tableroPartida.getNumParejas());
    	inicializarAudioClips();
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
        soundOn = true;
    }
    
    public void inicializarAudioClips() {
    	voltearCarta = new AudioClip(getClass().getResource("/sonidos/Voltear.mp3").toString());
        error = new AudioClip(getClass().getResource("/sonidos/error1.mp3").toString());
        acierto = new AudioClip(getClass().getResource("/sonidos/acierto.mp3").toString());
        victoria = new AudioClip(getClass().getResource("/sonidos/victoria.mp3").toString());
        derrota = new AudioClip(getClass().getResource("/sonidos/derrota1.mp3").toString());
        mismaCarta = new AudioClip(getClass().getResource("/sonidos/error2.mp3").toString());
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
    		if (tiempoPrimera + 5000 <= tiempoSegunda) sumaPuntos(-5, false);
    		segundaCarta = cartaSeleccionada;
    		segundaImagen = imagenSeleccionada;
    		if(primeraCarta == segundaCarta) {
    			mismaCarta.play();
    		}else if(primeraCarta.getId() == segundaCarta.getId()) {
    				voltearCarta.play();
    				stackPane.setDisable(true);
    				PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(e -> {
                        parejaCorrecta();
                        stackPane.setDisable(false);
                    });
                    pause.play();
    				esPrimeraCarta = true;
    			} else {
    				voltearCarta.play();
    				stackPane.setDisable(true);
    				PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(e -> {
                        parejaIncorrecta();
                        stackPane.setDisable(false);
                    });
                    pause.play();
    				esPrimeraCarta = true;
    			}   			    	
    	}
    }
    
    public void parejaCorrecta() {
    	sumaPuntos(10, false);
    	acierto.play();
    	primeraImagen.setDisable(true);
    	segundaImagen.setDisable(true);
    	if(cartasGiradas == barajaPartida.getTamanyo()) {
    		victoria();
    	}
    }
    
    public void parejaIncorrecta() {
    	sumaPuntos(-1, true);
    	parejasFalladas.add(primeraCarta.getId());
    	error.play();
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
	   return res;
   }
    
    public void victoria() {
    	timeline.stop();
    	esVictoria = true;
    	victoria.play();
    	clip.stop();
    	resultado.setImage(new Image("/imagenes/resultado_victoria.png"));
    	resultado.setVisible(true);
    }
    
    public boolean isVictoria() {
		return esVictoria;
	}
    
    public void derrota() {
    	esDerrota = true;
    	derrota.play();
    	clip.stop();
    	resultado.setImage(new Image("/imagenes/resultado_derrota.png"));
    	resultado.setVisible(true);
    	stackPane.setDisable(true);
    }
    
    public boolean isDerrota() {
		return esDerrota;
	}

    @FXML
    void pausarPartida(ActionEvent event) throws Exception {
    	esPausa = true;
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
        stage.setOnCloseRequest((WindowEvent event1) -> {controladorMenuPausa.reanudar();});
        stage.show();
    }
    public void reanudarPartida(boolean Sound) {
    	esPausa = false;
    	timeline.play();
    	soundOn = Sound;
    	if(soundOn) {
    		clip.setMicrosecondPosition(tiempoMusica);
    		clip.start();
    		voltearCarta.setVolume(1.0);
    		error.setVolume(1.0);
    		acierto.setVolume(1.0);
    		victoria.setVolume(1.0);
    		derrota.setVolume(1.0);
    	}
    	else {
    		clip.stop();
    		voltearCarta.setVolume(0);
    		error.setVolume(0);
    		acierto.setVolume(0);
    		victoria.setVolume(0);
    		derrota.setVolume(0);
    	}
    }
    
    public void iniciaTiempo(int tpartida) {
    	 counter = tpartida; 
    	 setTimer(counter);
         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.getKeyFrames().add(
                 new KeyFrame(Duration.seconds(1),
                   event -> {
					 counter--;
					 setTimer(counter);
				     if (counter <= 0) {
				         timeline.stop();
				         derrota();
				     } else if(esPausa){
				    	 timeline.pause();
				     }
				   }));
         timeline.playFromStart();
     }
    
    public void setTimer(int seconds) {
    	int mins = seconds / 60;
    	int secs = seconds - mins * 60;
    	String Secs = String.format("%02d", secs);
    	Time.set(Integer.toString(mins) + ":" + Secs);
    }

}