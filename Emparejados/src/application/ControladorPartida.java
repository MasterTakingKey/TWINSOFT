package application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControladorPartida implements Initializable {

    @FXML
    private Label tiempo = new Label();

    @FXML
    private Label puntos;

    @FXML
    private Button pausa;

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
    private Label resultado;
    
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
   AudioClip Musica = new AudioClip(getClass().getResource("/sonidos/Music.mp3").toString());

    private boolean soundOn;

	private boolean Pausa ;
	
	private int tiempoPausa;
	
	private Integer counter;
	
	private Timeline timeline;
	
	private StringProperty Time = new SimpleStringProperty("");
	
	
	private static final int TIEMPO_PART_ESTANDAR = 60; //Tiempo para partida estándar, por defecto 1 minuto
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		crearBaraja();
    	crearTablero();
    	esPrimeraCarta = true;
    	cartasGiradas = 0;
    	tiempoPausa = 0;
    	timeline = new Timeline();
    	Pausa = false;
    	victoria = false;
    	derrota = false;
    	voltearCarta = new AudioClip(getClass().getResource("/sonidos/Voltear.mp3").toString());
        Error = new AudioClip(getClass().getResource("/sonidos/Error1.mp3").toString());
        Acierto = new AudioClip(getClass().getResource("/sonidos/Acierto.mp3").toString());
        Victoria = new AudioClip(getClass().getResource("/sonidos/Victoria.mp3").toString());
        Derrota = new AudioClip(getClass().getResource("/sonidos/Derrota1.mp3").toString());
    	tiempo.textProperty().bind(Time);
    	Musica.setCycleCount(AudioClip.INDEFINITE);
    	Musica.play();
    	iniciaTiempo(TIEMPO_PART_ESTANDAR);
    	resultado.setVisible(false);
	} 
    
    public void iniciarPartida(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        soundOn = true;
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
    	voltearCarta.play();
    	cartasGiradas++;
    	ImageView imagenSeleccionada = (ImageView) event.getSource();
    	String nombreCarta = imagenSeleccionada.getId();
    	int posicionX = Integer.parseInt(nombreCarta.substring(5, 6));
    	int posicionY = Integer.parseInt(nombreCarta.substring(6, 7));
    	Carta cartaSeleccionada = tableroPartida.getCarta(posicionX, posicionY);
    	imagenSeleccionada.setImage(cartaSeleccionada.imagenFrente);
    	if(esPrimeraCarta) {
    		primeraCarta = cartaSeleccionada;
    		primeraImagen = imagenSeleccionada;
    		esPrimeraCarta = false;
    	} else {
    		segundaCarta = cartaSeleccionada;
    		segundaImagen = imagenSeleccionada;
    		if(primeraCarta == segundaCarta) {
    			Error.play();
    		}else if(primeraCarta.getId() == segundaCarta.getId()) {
    				parejaCorrecta();
    				esPrimeraCarta = true;
    			} else {
    				parejaIncorrecta();
    				esPrimeraCarta = true;
    			}    			    	
    	}
    }
    
    public void parejaCorrecta() {
    	Acierto.play();
    	//Sumar puntos
    	primeraImagen.setDisable(true);
    	segundaImagen.setDisable(true);
    	if(cartasGiradas == barajaPartida.getTamanyo()) {
    		victoria();
    	}
    }
    
    public void parejaIncorrecta() {
    	Error.play();
    	//Restar puntos
    	primeraImagen.setImage(barajaPartida.getImagenDorso());
    	segundaImagen.setImage(barajaPartida.getImagenDorso());
    	cartasGiradas-= 2;
    }
    
    public void victoria() {
    	victoria = true;
    	Victoria.play();
    	Musica.stop();
    	resultado.setVisible(true);
    	//Mensaje victoria
    }
    
    public boolean isVictoria() {
		return victoria;
	}
    
    public void derrota() {
    	derrota = true;
    	Derrota.play();
    	Musica.stop();
    	resultado.setText("DERROTA");
    	resultado.setVisible(true);
    	//Mensaje derrota
    }
    
    public boolean isDerrota() {
		return derrota;
	}

    @FXML
    void pausarPartida(ActionEvent event) throws Exception {
    	Pausa = true;
    	Musica.stop();
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
    		Musica.setVolume(1.0);
    		Musica.play();
    		voltearCarta.setVolume(1.0);
    		Error.setVolume(1.0);
    		Acierto.setVolume(1.0);
    		Victoria.setVolume(1.0);
    		Derrota.setVolume(1.0);
    	}
    	else {
    		Musica.stop();
    		Musica.setVolume(0);
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
                         } else if(victoria){
                        	 timeline.stop();
                         }else if(Pausa){
                        	 timeline.pause();
                        	 /*try {
								timeline.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
                             //Veremos como lo hacemos para pausar el tiempo, guardar en variable o lo que sea
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
