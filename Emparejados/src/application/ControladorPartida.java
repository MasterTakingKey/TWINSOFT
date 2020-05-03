package application;

import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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

public class ControladorPartida {
    
	private static final int TIEMPO_PART_ESTANDAR = 60;
	
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
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Tablero tableroPartida;
    
    private Baraja barajaPartida;
  
    private Carta primeraCarta;
    
    private Carta segundaCarta;
    
    private Carta cartaSeleccionada;
    
    private ImageView primeraImagen;
    
    private ImageView segundaImagen;
    
    private ImageView imagenSeleccionada;

    private Image Sound0;
    
    private Image Sound1;
    
    private Musica musicaFondo;
    
    private int cartasGiradas;
    
	private int puntuacion;
	
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

    public void iniciarPartidaEstandar(Stage stage, boolean soundOn, double anteriorX, double anteriorY){
        primaryStage = stage;
        SoundOn = soundOn;
    	inicializarVariables();
    	inicializarAudioClips();
    	actualizarSonido();
    	actualizarImagenSonido();
    	barajaPartida.crearBarajaAnimales(2);
    	tableroPartida.llenarTablero(barajaPartida);
    	corregirTamañoVentana();
    	corregirPosicionVentana(anteriorX, anteriorY);
    	tiempo.textProperty().bind(Time);
    	iniciaTiempo(TIEMPO_PART_ESTANDAR);
    }

    public void inicializarAudioClips() {
    	voltearCarta = new AudioClip(getClass().getResource("/sonidos/Voltear.mp3").toString());
        error = new AudioClip(getClass().getResource("/sonidos/error1.mp3").toString());
        acierto = new AudioClip(getClass().getResource("/sonidos/acierto.mp3").toString());
        mismaCarta = new AudioClip(getClass().getResource("/sonidos/error2.mp3").toString());
    }
    
    public void inicializarVariables() {
    	barajaPartida = new Baraja();
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
        thisStage = (Stage) carta00.getScene().getWindow();
    }
    
    @FXML
    void muestraCarta(MouseEvent event) {    	
    	cartasGiradas++;
    	imagenSeleccionada = (ImageView) event.getSource();
    	cartaSeleccionada = deImagenACarta(imagenSeleccionada);
    	if(esPrimeraCarta) {
    		voltearCarta.play();
    		restarPuntosTiempoEntreTurnos(1);
    		primeraCarta = cartaSeleccionada;
    		primeraImagen = imagenSeleccionada;
    		clickCartaAnimacion(imagenSeleccionada);
    		esPrimeraCarta = false;
    	} else {
    		restarPuntosTiempoEntreTurnos(2);
    		segundaCarta = cartaSeleccionada;
    		segundaImagen = imagenSeleccionada;
    		if(primeraCarta == segundaCarta) {
    			mismaCarta.play();
    		} else {
				voltearCarta.play();
				clickCartaAnimacion(imagenSeleccionada);
				PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
				stackPane.setDisable(true);
    			if(primeraCarta.getId() == segundaCarta.getId()) {
    				//stackPane.setDisable(true);
                    pause.setOnFinished(e -> {
                        parejaCorrecta();
                        stackPane.setDisable(false);
                    });  
    			} else {
    				//stackPane.setDisable(true);
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
    	parejaCorrectaAnimacion(primeraImagen, segundaImagen);
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
    	parejaIncorrectaAnimacion(primeraImagen, segundaImagen);
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
    	//System.out.println(puntosAn);
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
            	controladorResultadoPartida.iniciarResultado(primaryStage, SoundOn, puntuacionFinal, tiempoSobrante, true, "estandar", thisStage.getX(), thisStage.getY());
        	} else {
        		controladorResultadoPartida.iniciarResultado(primaryStage, SoundOn, puntuacionFinal, tiempoSobrante, false, "estandar", thisStage.getX(), thisStage.getY());
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
        	stage.setOnCloseRequest((WindowEvent event1) -> {controladorMenuPausa.reanudarPartidaEstandar();});
        	primaryStage.hide();
        	controladorMenuPausa.initDataPartidaEstandar(primaryStage, this, SoundOn, thisStage.getX(), thisStage.getY());
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
    
    private RotateTransition createFirstRotator(Node card) {
        RotateTransition firstRotator = new RotateTransition(Duration.millis(200), card);
        firstRotator.setAxis(Rotate.Y_AXIS);
        firstRotator.setFromAngle(0);
        firstRotator.setToAngle(89);
        firstRotator.setInterpolator(Interpolator.LINEAR);
        firstRotator.setCycleCount(1);

        return firstRotator;
    }
    private RotateTransition createSecondRotator(Node card) {
        RotateTransition secondRotator = new RotateTransition(Duration.millis(200), card);
        secondRotator.setAxis(Rotate.Y_AXIS);
        secondRotator.setFromAngle(90);
        secondRotator.setToAngle(180);
        secondRotator.setInterpolator(Interpolator.LINEAR);
        secondRotator.setCycleCount(1);

        return secondRotator;
    }
    
    private TranslateTransition createIncorrectTranslation(Node card) {
    	TranslateTransition translation = new TranslateTransition(Duration.millis(300), card);

    	translation.setByY(50);
    	translation.setByY(-50);
    	translation.setAutoReverse(true);
    	translation.setCycleCount(2);

    	return translation;
    }
    private TranslateTransition createCorrectTranslation(Node card) {
    	TranslateTransition translation = new TranslateTransition(Duration.millis(200), card);

    	translation.setByY(50);
    	translation.setByY(-50);
    	translation.setAutoReverse(true);
    	translation.setCycleCount(4);

    	return translation;

    }

    private void clickCartaAnimacion(ImageView carta) {
    	stackPane.setDisable(true);
    	Node card = FlipAdelanteCard(carta);

        RotateTransition firstRotator = createFirstRotator(card);
        RotateTransition secondRotator = createSecondRotator(card);

        firstRotator.play();
        firstRotator.setOnFinished(e -> {
            imagenSeleccionada.setImage(cartaSeleccionada.imagenFrente);
               secondRotator.play();
        });
        secondRotator.setOnFinished(e -> {
            stackPane.setDisable(false);
     });
    }

    private void parejaCorrectaAnimacion(ImageView carta1, ImageView carta2) {
    	stackPane.setDisable(true);
    	Node card1 = FlipAdelanteCard(carta1);
	    Node card2 = FlipAdelanteCard(carta2);

	    TranslateTransition firstTranslation1 = createCorrectTranslation(card1);
	    TranslateTransition firstTranslation2 = createCorrectTranslation(card2);

	    firstTranslation1.play();
	    firstTranslation2.play();

	    firstTranslation2.setOnFinished(e -> {
	    	stackPane.setDisable(false);
	    });

	  }


    private void parejaIncorrectaAnimacion(ImageView carta1, ImageView carta2){
    	stackPane.setDisable(true);
	    Node card1 = FlipAdelanteCard(carta1);
	    Node card2 = FlipAdelanteCard(carta2);

	    RotateTransition firstRotator1 = createFirstRotator(card1);
	    RotateTransition secondRotator1 = createSecondRotator(card1);

	    RotateTransition firstRotator2 = createFirstRotator(card2);
	    RotateTransition secondRotator2 = createSecondRotator(card2);

	    firstRotator1.play();
	    firstRotator1.setOnFinished(e -> {
	        carta1.setImage(barajaPartida.getImagenDorso());
	           secondRotator1.play();
	    });	    

	    firstRotator2.play();
	    firstRotator2.setOnFinished(e -> {
	        carta2.setImage(barajaPartida.getImagenDorso());
	           secondRotator2.play();
	    });

	    TranslateTransition firstTranslation1 = createIncorrectTranslation(card1);
	    TranslateTransition firstTranslation2 = createIncorrectTranslation(card2);

	    firstTranslation1.play();
	    firstTranslation2.play();

	    firstTranslation2.setOnFinished(e -> {
	    	stackPane.setDisable(false);
	    });

    }	
    
    public void corregirTamañoVentana() {
    	thisStage.setWidth(910);
    	thisStage.setHeight(623);
    }

    public void corregirPosicionVentana(double anteriorX, double anteriorY) {
    	thisStage.setX(anteriorX);
    	thisStage.setY(anteriorY);
    }

}