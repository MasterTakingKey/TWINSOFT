package application;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class ControladorPartida {
    
	private static final int TIEMPO_PART_ESTANDAR = 60;
	
	@FXML
    private Label tiempo = new Label();

    @FXML
    private Label puntos = new Label();
    
    @FXML
    private ImageView iconoSonido;

    @FXML
    private ImageView pausa;
    
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
    
    @FXML
    private Label puntosAnyadidos;
    
    private Stage primaryStage;
    
    private Tablero tableroPartida;
    
    private Baraja barajaPartida;
  
    private Carta primeraCarta;
    
    private Carta segundaCarta;
    
    private Carta cartaSeleccionada;
    
    private ImageView primeraImagen;
    
    private ImageView segundaImagen;
    
    private ImageView imagenSeleccionada;
    
    private Image imagenDorso;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private Musica musicaFondo;
    
    private int cartasGiradas;
    
	private int puntuacion;
	
	private Integer counter;
	
	private ArrayList<Integer> parejasFalladas;
	
	private double auxiliarX;
	
	private double auxiliarY;
	
	private long tiempoMusica;
	
	private long tiempoPrimera;
    
    private boolean esPrimeraCarta;
     
    private boolean esVictoria;

	private boolean esDerrota;
	
    private boolean SoundOn;

	private boolean esPausa;
    
    private AudioClip voltearCarta;
    
    private AudioClip error;
    
    private AudioClip acierto;
    
    private AudioClip victoria;
    
    private AudioClip derrota;
    
    private AudioClip mismaCarta;
	
	private Timeline timeline;
	
	private StringProperty Time; 

    public void iniciarPartida(Stage stage, boolean soundOn){
        primaryStage = stage;
        SoundOn = soundOn;
        crearBaraja();
    	crearTablero();
    	inicializarVariables();
    	inicializarAudioClips();
    	actualizarSonido();
    	actualizarImagenSonido();
    	tiempo.textProperty().bind(Time);
    	iniciaTiempo(TIEMPO_PART_ESTANDAR);
    	resultado.setVisible(false);
    	puntosAnyadidos.setVisible(false);
    }

    public void inicializarAudioClips() {
    	voltearCarta = new AudioClip(getClass().getResource("/sonidos/Voltear.mp3").toString());
        error = new AudioClip(getClass().getResource("/sonidos/error1.mp3").toString());
        acierto = new AudioClip(getClass().getResource("/sonidos/acierto.mp3").toString());
        victoria = new AudioClip(getClass().getResource("/sonidos/victoria.mp3").toString());
        derrota = new AudioClip(getClass().getResource("/sonidos/derrota1.mp3").toString());
        mismaCarta = new AudioClip(getClass().getResource("/sonidos/error2.mp3").toString());
    }
    
    public void inicializarVariables() {
    	esPrimeraCarta = true;
    	cartasGiradas = 0;
    	timeline = new Timeline();
    	esPausa = false;
    	esVictoria = false;
    	esDerrota = false;
    	tiempoPrimera = 0;
    	parejasFalladas = new ArrayList<Integer>(tableroPartida.getNumParejas());
    	Time = new SimpleStringProperty("");
    	musicaFondo = new Musica("src/sonidos/Musica1.wav", 0L);
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        auxiliarX = primaryStage.getX();
        auxiliarY = primaryStage.getY();
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
    	imagenSeleccionada = (ImageView) event.getSource();
    	cartaSeleccionada = deImagenACarta(imagenSeleccionada);
    	imagenSeleccionada.setImage(cartaSeleccionada.imagenFrente);
    	if(esPrimeraCarta) {
    		voltearCarta.play();
    		restarPuntosTiempoEntreTurnos(1);
    		primeraCarta = cartaSeleccionada;
    		primeraImagen = imagenSeleccionada;
    		esPrimeraCarta = false;
    	} else {
    		restarPuntosTiempoEntreTurnos(2);
    		segundaCarta = cartaSeleccionada;
    		segundaImagen = imagenSeleccionada;
    		if(primeraCarta == segundaCarta) {
    			mismaCarta.play();
    		} else {
				voltearCarta.play();
				PauseTransition pause = new PauseTransition(Duration.seconds(1));
    			if(primeraCarta.getId() == segundaCarta.getId()) {
    				stackPane.setDisable(true);
                    pause.setOnFinished(e -> {
                        parejaCorrecta();
                        stackPane.setDisable(false);
                    });  
    			} else {
    				stackPane.setDisable(true);
                    pause.setOnFinished(e -> {
                        parejaIncorrecta();
                        stackPane.setDisable(false);
                    });
    			}
    			pause.play();
				esPrimeraCarta = true;
    		}			    	
    	}
    }
    
    public Carta deImagenACarta(ImageView imgSeleccionada) {
    	String nombreCarta = imgSeleccionada.getId();
    	int posicionX = Integer.parseInt(nombreCarta.substring(5, 6));
    	int posicionY = Integer.parseInt(nombreCarta.substring(6, 7));
    	return tableroPartida.getCarta(posicionX, posicionY);
    }
    
    public void restarPuntosTiempoEntreTurnos(int turno) {
    	if(turno == 1) {
    		tiempoPrimera= System.currentTimeMillis();
    	} else {
    		long tiempoSegunda= System.currentTimeMillis();
    		if (tiempoPrimera + 5000 <= tiempoSegunda) sumaPuntos(-2, false);
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
    
    public int sumaPuntos(int p, boolean parInc) {
    	if (parInc) p -= parejaIncRepetida(primeraCarta.getId());
    	puntuacion += p;
    	//if (puntuacion < 0) puntuacion = 0; la puntuacion puede ser negativa
    	String puntosAn = "";
    	if (p < 0) {
    		puntosAn = Integer.toString(p);
    		puntosAnyadidos.setTextFill(Color.RED);
    	}else {
    		puntosAn = "+" + Integer.toString(p);
    		puntosAnyadidos.setTextFill(Color.GREEN);
    	}
    	System.out.println(puntosAn);
    	puntosAnyadidos.setText(puntosAn);
    	PauseTransition pause = new PauseTransition(Duration.millis(750));
    	pause.setOnFinished(e -> {
    		  puntosAnyadidos.setVisible(false);
          }); 
    	puntosAnyadidos.setVisible(true);
    	pause.play();
    	puntos.setText(Integer.toString(puntuacion));	
    	return puntuacion;
    }
    
    public void victoria() {
    	timeline.stop();
    	esVictoria = true;
    	victoria.play();
    	bonificacionVictoria();
    	musicaFondo.stopMusic();
    	resultado.setImage(new Image("/imagenes/resultado_victoria.png"));
    	resultado.setVisible(true);
    }
    
    public boolean isVictoria() {
		return esVictoria;
	}
    
    public void bonificacionVictoria() {
    	int bonif;
    	bonif = (int) (counter * 0.5) + tableroPartida.getNumParejas(); 
    	sumaPuntos(bonif, false);
    	
    }
    
    public void derrota() {
    	esDerrota = true;
    	derrota.play();
    	musicaFondo.stopMusic();
    	resultado.setImage(new Image("/imagenes/resultado_derrota.png"));
    	resultado.setVisible(true);
    	stackPane.setDisable(true);
    }
    
    public boolean isDerrota() {
		return esDerrota;
	}
 
    @FXML
    void pausarPartida(MouseEvent event) throws Exception {
    	esPausa = true;
    	tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
    	musicaFondo.stopMusic();
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPause.fxml"));
        Parent root = (Parent) myLoader.load();
        ControladorMenuPause controladorMenuPausa = myLoader.<ControladorMenuPause>getController();
        controladorMenuPausa.initData(primaryStage, SoundOn);
        controladorMenuPausa.setControladorPartida(this);
        auxiliarX = primaryStage.getX();
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("estilo1.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setOnCloseRequest((WindowEvent event1) -> {controladorMenuPausa.reanudar();});
        stage.show();
        stage.setX(auxiliarX + 3);
        stage.setY(auxiliarY + 12);    
    }
    
    public void reanudarPartida(boolean Sound) {
    	esPausa = false;
    	timeline.play();
    	SoundOn = Sound;
    	actualizarSonido();
    	actualizarImagenSonido();
    }
    
    public void actualizarSonido() {
    	if(SoundOn) {
    		musicaFondo.getClip().setMicrosecondPosition(tiempoMusica);
    		musicaFondo.playMusic();
    		voltearCarta.setVolume(1.0);
    		error.setVolume(1.0);
    		acierto.setVolume(1.0);
    		victoria.setVolume(1.0);
    		derrota.setVolume(1.0);
    	}
    	else {
    		musicaFondo.stopMusic();
    		voltearCarta.setVolume(0);
    		error.setVolume(0);
    		acierto.setVolume(0);
    		victoria.setVolume(0);
    		derrota.setVolume(0);
    	}
    }
    
    public void actualizarImagenSonido() {
        if(SoundOn) {
        	iconoSonido.setImage(Sound1);
        } else {
        	iconoSonido.setImage(Sound0);
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