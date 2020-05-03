package application;

import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class ControladorPartidaCarta {
    
	private static final int TIEMPO_PART_ESTANDAR = 90;
	
	@FXML
    private Label tiempo = new Label();

    @FXML
    private Label puntos = new Label();
    
    @FXML
    private Label puntosAnyadidos;
    
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
    private ImageView siguienteImagenMostrada;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Tablero tableroPartida;
    
    private Baraja barajaPartida;
    
    private Baraja barajaAuxiliar;
  
    private Carta primeraCarta;
    
    private Carta segundaCarta;
    
    private Carta cartaSeleccionada;
    
    private Carta siguienteCartaMostrada;
    
    private ImageView primeraImagen;
    
    private ImageView segundaImagen;
    
    private ImageView imagenSeleccionada;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private Musica musicaFondo;
    
    private int cartasGiradas;
    
	private int puntuacion;
	
	private int indiceBarajaAuxiliar;
	
	private Integer counter;
	
	private ArrayList<Integer> parejasFalladas;
	
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
    
    private AudioClip mismaCarta;
	
	private Timeline timeline;
	
	private StringProperty Time; 

    public void iniciarPartidaCarta(Stage stage, boolean soundOn, double anteriorX, double anteriorY){
        primaryStage = stage;
        SoundOn = soundOn;
    	inicializarVariables();
    	inicializarAudioClips();
    	actualizarSonido();
    	actualizarImagenSonido();
    	barajaPartida.crearBarajaAnimales(2);
    	barajaAuxiliar.crearBarajaAnimales(1);
    	tableroPartida.llenarTablero(barajaPartida);
    	corregirTamañoVentana();
    	corregirPosicionVentana(anteriorX, anteriorY);
    	mostrarSiguienteCarta();
    	tiempo.textProperty().bind(Time);
    	iniciaTiempo(TIEMPO_PART_ESTANDAR);
    }
    
    public void inicializarVariables() {
    	barajaPartida = new Baraja();
    	barajaAuxiliar = new Baraja();
    	tableroPartida = new Tablero(4);
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
    	Sound0 = new Image("/imagenes/sonido_off_2.png");
        Sound1 = new Image("/imagenes/sonido_on_2.png");
        puntosAnyadidos.setVisible(false);
        puntosAnyadidos.setStyle(null);
        indiceBarajaAuxiliar = 0;
        thisStage = (Stage) carta00.getScene().getWindow();
    }
    
    public void inicializarAudioClips() {
    	voltearCarta = new AudioClip(getClass().getResource("/sonidos/Voltear.mp3").toString());
        error = new AudioClip(getClass().getResource("/sonidos/error1.mp3").toString());
        acierto = new AudioClip(getClass().getResource("/sonidos/acierto.mp3").toString());
        mismaCarta = new AudioClip(getClass().getResource("/sonidos/error2.mp3").toString());
    }

    @FXML
    void muestraCarta(MouseEvent event) {    	
    	cartasGiradas++;
    	imagenSeleccionada = (ImageView) event.getSource();
    	cartaSeleccionada = deImagenACarta(imagenSeleccionada);
    	Node card = FlipAdelanteCard(imagenSeleccionada);
        RotateTransition firstRotator = createFirstRotator(card);
        RotateTransition secondRotator = createSecondRotator(card);
        firstRotator.play();
        firstRotator.setOnFinished(e -> {
            imagenSeleccionada.setImage(cartaSeleccionada.imagenFrente);
               secondRotator.play();
        });
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
				if(primeraCarta.getId() == segundaCarta.getId() && primeraCarta.getId() == siguienteCartaMostrada.getId()) {
    				stackPane.setDisable(true);
                    pause.setOnFinished(e -> {
                        parejaCorrecta();
                        stackPane.setDisable(false);
                        mostrarSiguienteCarta();
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
    
    public void mostrarSiguienteCarta() {
        siguienteCartaMostrada = barajaAuxiliar.getCarta(indiceBarajaAuxiliar);
        siguienteImagenMostrada.setImage(siguienteCartaMostrada.getImagenFrente());
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
    	} else {
        	indiceBarajaAuxiliar++;
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
    	bonificacionVictoria();
    	PauseTransition pause = new PauseTransition(Duration.millis(750));
    	pause.setOnFinished(e -> {
    		esVictoria = true;
        	musicaFondo.stopMusic();
        	mostrarResultado();
          }); 
    	pause.play();
    	
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
    	musicaFondo.stopMusic();
    	mostrarResultado();
    }
    
    public boolean isDerrota() {
		return esDerrota;
	}
    
    public void mostrarResultado() {
    	try {
        	String puntuacionFinal = puntos.getText();
        	String tiempoSobrante = tiempo.getText();
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/ResultadoPartida.fxml"));
    		Parent root = (Parent) myLoader.load();
    		ControladorResultadoPartida controladorResultadoPartida = myLoader.<ControladorResultadoPartida>getController();
    		Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		stage.setScene(scene);
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.setResizable(false);
    		primaryStage.hide();
    		stage.setTitle("Resultado");
    		if(isVictoria()) {
            	controladorResultadoPartida.iniciarResultado(primaryStage, SoundOn, puntuacionFinal, tiempoSobrante, true, "carta", thisStage.getX(), thisStage.getY());
        	} else {
        		controladorResultadoPartida.iniciarResultado(primaryStage, SoundOn, puntuacionFinal, tiempoSobrante, false, "carta", thisStage.getX(), thisStage.getY());
        	}
    		stage.show();
    	} catch (IOException e) {
    		
    	}
    }
    
    @FXML
    void pausarPartida(MouseEvent event) {
    	try {
    		esPausa = true;
    		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
    		musicaFondo.stopMusic();
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPause.fxml"));
    		Parent root = (Parent) myLoader.load();
    		ControladorMenuPause controladorMenuPausa = myLoader.<ControladorMenuPause>getController();
    		Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		stage.setScene(scene);
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.setResizable(false);
        	stage.setOnCloseRequest((WindowEvent event1) -> {controladorMenuPausa.reanudarPartidaCarta();});
        	primaryStage.hide();
        	controladorMenuPausa.initDataPartidaCarta(primaryStage, this, SoundOn, thisStage.getX(), thisStage.getY());
        	stage.show();
        	stage.toFront();
    	} catch (IOException e) {
    		
    	}
    }
    
    public void reanudarPartida(boolean Sound, double anteriorX, double anteriorY) {
    	corregirTamañoVentana();
    	corregirPosicionVentana(anteriorX, anteriorY);
    	primaryStage.show();
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
    	}
    	else {
    		musicaFondo.stopMusic();
    		voltearCarta.setVolume(0);
    		error.setVolume(0);
    		acierto.setVolume(0);
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
    
    private Node FlipAdelanteCard(ImageView card) {
        return card;
    }

    private Node FlipAtrasCard(ImageView card) {
        return card;
    }
    
    private RotateTransition createFirstRotator(Node card) {
        RotateTransition firstRotator = new RotateTransition(Duration.millis(400), card);
        firstRotator.setAxis(Rotate.Y_AXIS);
        firstRotator.setFromAngle(0);
        firstRotator.setToAngle(89);
        firstRotator.setInterpolator(Interpolator.LINEAR);
        firstRotator.setCycleCount(1);

        return firstRotator;
    }
    private RotateTransition createSecondRotator(Node card) {
        RotateTransition secondRotator = new RotateTransition(Duration.millis(400), card);
        secondRotator.setAxis(Rotate.Y_AXIS);
        secondRotator.setFromAngle(90);
        secondRotator.setToAngle(180);
        secondRotator.setInterpolator(Interpolator.LINEAR);
        secondRotator.setCycleCount(1);

        return secondRotator;
    }
    
    public void corregirTamañoVentana() {
    	thisStage.setWidth(900);
    	thisStage.setHeight(820);
    }
    
    public void corregirPosicionVentana(double anteriorX, double anteriorY) {
    	thisStage.setX(anteriorX);
    	thisStage.setY(anteriorY);
    }
    
    public void delay(int segundos) {
    	try {
            Thread.sleep(segundos*1000);
        } catch (InterruptedException e) {
        }
    }

}