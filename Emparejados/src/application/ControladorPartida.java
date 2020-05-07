package application;

import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
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
    
    private int puntosAnteriores;
	
	private ArrayList<Carta> parejasFalladas;
	
	private long tiempoMusica;
    
    private boolean esPrimeraCarta;
     
    private boolean esVictoria;

	private boolean esDerrota;
	
    private boolean SoundOn;
    
    private AudioClip voltearCarta;
    
    private AudioClip error;
    
    private AudioClip acierto;
    
    private AudioClip mismaCarta;
	
    private ContadorTiempo contadorTiempo;
    
    private Puntuacion puntuacion;

    public void iniciarPartidaEstandar(Stage stage, boolean soundOn, double anteriorX, double anteriorY){
    	primaryStage = stage;
        SoundOn = soundOn;
        inicializarBarajaTablero();
        inicializarCartas();
    	inicializarVariables();
    	inicializarAudioClips();
    	inicializarContadorTiempo();
    	inicializarPuntuacion();
    	actualizarSonido();
    	actualizarImagenSonido();
    	corregirTamanyoVentana();
    	corregirPosicionVentana(anteriorX, anteriorY);
    }
    
    public void inicializarBarajaTablero() {
    	barajaPartida = new Baraja(4, 4);
    	barajaPartida.barajaTematica(new CrearBarajaNintendoEstrategia(2), 16);
    	barajaPartida.barajar();
    	tableroPartida = new Tablero(4, 4);
    	tableroPartida.llenarTablero(barajaPartida);
    }
    
    public void inicializarCartas() {
    	carta00.setImage(barajaPartida.getImagenDorso());
    	carta01.setImage(barajaPartida.getImagenDorso());
    	carta02.setImage(barajaPartida.getImagenDorso());
    	carta03.setImage(barajaPartida.getImagenDorso());
    	carta10.setImage(barajaPartida.getImagenDorso());
    	carta11.setImage(barajaPartida.getImagenDorso());
    	carta12.setImage(barajaPartida.getImagenDorso());
    	carta13.setImage(barajaPartida.getImagenDorso());
    	carta20.setImage(barajaPartida.getImagenDorso());
    	carta21.setImage(barajaPartida.getImagenDorso());
    	carta22.setImage(barajaPartida.getImagenDorso());
    	carta23.setImage(barajaPartida.getImagenDorso());
    	carta23.setImage(barajaPartida.getImagenDorso());
    	carta30.setImage(barajaPartida.getImagenDorso());
    	carta31.setImage(barajaPartida.getImagenDorso());
    	carta32.setImage(barajaPartida.getImagenDorso());
    	carta33.setImage(barajaPartida.getImagenDorso());
    }
    
    public void inicializarVariables() {
    	cartasGiradas = 0;
    	esPrimeraCarta = true;
    	esVictoria = false;
    	esDerrota = false;
    	parejasFalladas = new ArrayList<Carta>(tableroPartida.getNumParejas());
    	musicaFondo = new Musica("src/sonidos/Musica1.wav", 0L);
    	Sound0 = new Image("/imagenes/sonido_off_2.png");
        Sound1 = new Image("/imagenes/sonido_on_2.png");
        puntosAnyadidos.setVisible(false);
        thisStage = (Stage) carta00.getScene().getWindow();
    }

    public void inicializarAudioClips() {
    	voltearCarta = new AudioClip(getClass().getResource("/sonidos/Voltear.mp3").toString());
        error = new AudioClip(getClass().getResource("/sonidos/error1.mp3").toString());
        acierto = new AudioClip(getClass().getResource("/sonidos/acierto.mp3").toString());
        mismaCarta = new AudioClip(getClass().getResource("/sonidos/error2.mp3").toString());
    }
    
    public void inicializarContadorTiempo() {
        contadorTiempo = new ContadorTiempo();
    	contadorTiempo.iniciarTiempoPartidaEstandar(tiempo);
        tiempo.textProperty().addListener((ChangeListener<? super String>) (o, oldVal, newVal) -> {
        	int minutos = Integer.parseInt(tiempo.getText().substring(0, tiempo.getText().length()-3));
        	int segundos = Integer.parseInt( tiempo.getText().substring(tiempo.getText().length() - 2));
        	if(minutos + segundos == 0) derrota();
		});
    }
    
    public void inicializarPuntuacion() {
    	puntuacion = new Puntuacion();
    	puntuacion.getPuntosCambiados().addListener((ChangeListener<? super Boolean>) (o, oldVal, newVal) -> {
    		puntos.setText(Integer.toString(puntuacion.getPuntos()));
        	mostrarPuntos(puntuacion.getPuntos() - puntosAnteriores);
		});
    }
    
    @FXML
    void muestraCarta(MouseEvent event) {    	
    	cartasGiradas++;
    	imagenSeleccionada = (ImageView) event.getSource();
    	cartaSeleccionada = deImagenACarta(imagenSeleccionada);
    	if(esPrimeraCarta) {
    		voltearCarta.play();
    		puntosAnteriores = puntuacion.getPuntos();
    		puntuacion.iniciarTiempoEntreTurnos();
    		primeraCarta = cartaSeleccionada;
    		primeraImagen = imagenSeleccionada;
    		clickCartaAnimacion(imagenSeleccionada);
    		esPrimeraCarta = false;
    	} else {
    		puntuacion.getTimeline().stop();
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
                    pause.setOnFinished(e -> {
                        parejaCorrecta();
                        stackPane.setDisable(false);
                    });  
    			} else {
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
    
    public void parejaCorrecta() {
    	puntosAnteriores = puntuacion.getPuntos();
    	puntuacion.sumaPuntos(10, false, 0);
    	acierto.play();
    	parejaCorrectaAnimacion(primeraImagen, segundaImagen);
    	primeraImagen.setDisable(true);
    	segundaImagen.setDisable(true);
    	if(cartasGiradas == barajaPartida.getTamanyo()) {
    		victoria();
    	}
    }
    
    public void parejaIncorrecta() {
    	puntosAnteriores = puntuacion.getPuntos();
    	puntuacion.sumaPuntos(-1, true, numeroVecesGirada(primeraCarta));
    	parejasFalladas.add(primeraCarta);
    	parejasFalladas.add(segundaCarta);
    	error.play();
    	parejaIncorrectaAnimacion(primeraImagen, segundaImagen);
    	cartasGiradas-= 2;
    }
    
    public int numeroVecesGirada(Carta cartaGirada) {
        int res = 0;
 	   	if (parejasFalladas.contains(cartaGirada)) {
 	   		ArrayList<Carta> aux = (ArrayList<Carta>) parejasFalladas.clone();
 	   		while(aux.contains(cartaGirada)) {
 	   			aux.remove((Object)cartaGirada);
 	   			res++;
 	   		}   
 	   	}
 	   	return res;
    }
    
    public void mostrarPuntos(int puntosAMostrar) {
	    String puntosAn = "";
	    if (puntosAMostrar < 0) {
	    	puntosAn = Integer.toString(puntosAMostrar);
	    	puntosAnyadidos.setTextFill(Color.RED);
	    }else {
	    	puntosAn = "+" + Integer.toString(puntosAMostrar);
	    	puntosAnyadidos.setTextFill(Color.GREEN);
	    }
	    puntosAnyadidos.setText(puntosAn);
	    PauseTransition pause = new PauseTransition(Duration.millis(750));
	    pause.setOnFinished(e -> {
	    	puntosAnyadidos.setVisible(false);
	    }); 
	    puntosAnyadidos.setVisible(true);
	    pause.play();
    }
    
    public void victoria() {
    	contadorTiempo.parar();
    	puntuacion.sumarBonificacionVictoria(contadorTiempo.getTiempoRestante(), tableroPartida.getNumParejas());
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
        	puntuacion.getTimeline().stop();
    		String puntuacionFinal = Integer.toString(puntuacion.getPuntos());
        	String tiempoSobrante = tiempo.getText();
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/ResultadoPartida.fxml"));
    		Parent root = (Parent) myLoader.load();
    		ControladorResultadoPartida controladorResultadoPartida = myLoader.<ControladorResultadoPartida>getController();
    		Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		stage.setScene(scene);
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
    		puntuacion.stopTimeLine();
    		contadorTiempo.setEsPausa(true);
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
    	puntuacion.playTimeline();
    	corregirTamanyoVentana();
    	corregirPosicionVentana(anteriorX, anteriorY);
    	primaryStage.show();
    	contadorTiempo.setEsPausa(false);
    	contadorTiempo.continuar();
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
    
    private Node FlipAdelanteCard(ImageView card) {
        return card;
    }
    
    private RotateTransition createFirstRotator(Node card) {
        RotateTransition firstRotator = new RotateTransition(Duration.millis(200), card);
        firstRotator.setAxis(Rotate.Y_AXIS);
        firstRotator.setFromAngle(180);
        firstRotator.setToAngle(91);
        firstRotator.setInterpolator(Interpolator.LINEAR);
        firstRotator.setCycleCount(1);

        return firstRotator;
    }
    private RotateTransition createSecondRotator(Node card) {
        RotateTransition secondRotator = new RotateTransition(Duration.millis(200), card);
        secondRotator.setAxis(Rotate.Y_AXIS);
        secondRotator.setFromAngle(90);
        secondRotator.setToAngle(0);
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
    
    public void corregirTamanyoVentana() {
    	thisStage.setWidth(910);
    	thisStage.setHeight(623);
    }

    public void corregirPosicionVentana(double anteriorX, double anteriorY) {
    	thisStage.setX(anteriorX);
    	thisStage.setY(anteriorY);
    }

}