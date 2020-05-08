package application;

import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
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
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ControladorPartidaLibre {
	
	@FXML
    private GridPane tablero;
	
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
    private ImageView carta40;

    @FXML
    private ImageView carta41;

    @FXML
    private ImageView carta34;

    @FXML
    private ImageView carta24;

    @FXML
    private ImageView carta14;

    @FXML
    private ImageView carta04;

    @FXML
    private ImageView carta43;

    @FXML
    private ImageView carta42;

    @FXML
    private ImageView carta25;

    @FXML
    private ImageView carta44;

    @FXML
    private ImageView carta05;

    @FXML
    private ImageView carta15;

    @FXML
    private ImageView carta45;

    @FXML
    private ImageView carta35;

    @FXML
    private ImageView carta51;

    @FXML
    private ImageView carta50;

    @FXML
    private ImageView carta55;

    @FXML
    private ImageView carta54;

    @FXML
    private ImageView carta53;

    @FXML
    private ImageView carta52;
    
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
    
    private int cartas;
    
    private int filas;
    
    private int columnas;
	
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
    
    private Animaciones animaciones;
    
    private String estilo;

    public void iniciarPartidaLibre(Stage stage, boolean soundOn, double anteriorX, double anteriorY, int filas, int columnas, String estilo){
    	primaryStage = stage;
        SoundOn = soundOn;
        cartas = filas*columnas;
        this.filas = filas;
        this.columnas = columnas;
        inicializarBarajaTablero(filas, columnas);
        inicializarTablero(filas, columnas);
        inicializarCartas();
    	inicializarVariables();
    	inicializarAudioClips();
    	inicializarContadorTiempo();
    	inicializarPuntuacion();
    	actualizarSonido();
    	actualizarImagenSonido();
    	corregirTamanyoVentana();
    	corregirPosicionVentana(anteriorX, anteriorY);
    	actualizarEstilo(estilo);
    }
     
    public void inicializarBarajaTablero(int filas, int columnas) {
    	barajaPartida = new Baraja(filas, columnas);
    	int tamanyo = filas*columnas;
    	//barajaPartida.barajaTematica(new CrearBarajaAnimalesEstrategia(2), tamanyo);
    	barajaPartida.barajar();
    	tableroPartida = new Tablero(filas, columnas);
    	tableroPartida.llenarTablero(barajaPartida);
    }
    
    private void inicializarTablero(int filas, int columnas) {
    	tablero.getColumnConstraints().clear();
    	tablero.getRowConstraints().clear();
    	tableroPartida.setTamanyo(filas, columnas);
        for (int i = 0; i < columnas; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setHalignment(HPos.CENTER);
            colConst.setPercentWidth(100.0 / columnas);
            tablero.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < filas; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setValignment(VPos.CENTER);
            rowConst.setPercentHeight(100.0 / filas);
            tablero.getRowConstraints().add(rowConst);         
        }
    }
    
    public void inicializarCartas() {
    	carta00.setImage(barajaPartida.getImagenDorso());
    	carta01.setImage(barajaPartida.getImagenDorso());
    	carta02.setImage(barajaPartida.getImagenDorso());
    	carta03.setImage(barajaPartida.getImagenDorso());
    	carta04.setImage(barajaPartida.getImagenDorso());
    	carta05.setImage(barajaPartida.getImagenDorso());
    	carta10.setImage(barajaPartida.getImagenDorso());
    	carta11.setImage(barajaPartida.getImagenDorso());
    	carta12.setImage(barajaPartida.getImagenDorso());
    	carta13.setImage(barajaPartida.getImagenDorso());
    	carta14.setImage(barajaPartida.getImagenDorso());
    	carta15.setImage(barajaPartida.getImagenDorso());
    	carta20.setImage(barajaPartida.getImagenDorso());
    	carta21.setImage(barajaPartida.getImagenDorso());
    	carta22.setImage(barajaPartida.getImagenDorso());
    	carta23.setImage(barajaPartida.getImagenDorso());
    	carta23.setImage(barajaPartida.getImagenDorso());
    	carta24.setImage(barajaPartida.getImagenDorso());
    	carta25.setImage(barajaPartida.getImagenDorso());
    	carta30.setImage(barajaPartida.getImagenDorso());
    	carta31.setImage(barajaPartida.getImagenDorso());
    	carta32.setImage(barajaPartida.getImagenDorso());
    	carta33.setImage(barajaPartida.getImagenDorso());
    	carta34.setImage(barajaPartida.getImagenDorso());
    	carta35.setImage(barajaPartida.getImagenDorso());
    	carta40.setImage(barajaPartida.getImagenDorso());
    	carta41.setImage(barajaPartida.getImagenDorso());
    	carta42.setImage(barajaPartida.getImagenDorso());
    	carta43.setImage(barajaPartida.getImagenDorso());
    	carta44.setImage(barajaPartida.getImagenDorso());
    	carta45.setImage(barajaPartida.getImagenDorso());
    	carta50.setImage(barajaPartida.getImagenDorso());
    	carta51.setImage(barajaPartida.getImagenDorso());
    	carta52.setImage(barajaPartida.getImagenDorso());
    	carta53.setImage(barajaPartida.getImagenDorso());
    	carta54.setImage(barajaPartida.getImagenDorso());
    	carta55.setImage(barajaPartida.getImagenDorso());
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
        animaciones = new Animaciones(stackPane, barajaPartida);
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
    		animaciones.clickCartaAnimacion(imagenSeleccionada, cartaSeleccionada);
    		esPrimeraCarta = false;
    	} else {
    		puntuacion.getTimeline().stop();
    		segundaCarta = cartaSeleccionada;
    		segundaImagen = imagenSeleccionada;
    		if(primeraCarta == segundaCarta) {
    			mismaCarta.play();
    		} else {
				voltearCarta.play();
				animaciones.clickCartaAnimacion(imagenSeleccionada, cartaSeleccionada);
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
    	animaciones.parejaCorrectaAnimacion(primeraImagen, segundaImagen);
    	primeraImagen.setDisable(true);
    	segundaImagen.setDisable(true);
    	if(cartasGiradas == cartas) {
    		victoria();
    	}
    }
    
    public void parejaIncorrecta() {
    	puntosAnteriores = puntuacion.getPuntos();
    	puntuacion.sumaPuntos(-1, true, numeroVecesGirada(primeraCarta));
    	parejasFalladas.add(primeraCarta);
    	parejasFalladas.add(segundaCarta);
    	error.play();
    	animaciones.parejaIncorrectaAnimacion(primeraImagen, segundaImagen);
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
            	controladorResultadoPartida.iniciarResultado(primaryStage, SoundOn, puntuacionFinal, tiempoSobrante, true, "estandar", thisStage.getX(), thisStage.getY(), filas, columnas, estilo);
        	} else {
        		controladorResultadoPartida.iniciarResultado(primaryStage, SoundOn, puntuacionFinal, tiempoSobrante, false, "estandar", thisStage.getX(), thisStage.getY(), filas, columnas, estilo);
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
        	controladorMenuPausa.initDataPartidaLibre(primaryStage, this, SoundOn, thisStage.getX(), thisStage.getY(), estilo);
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
    
    public void corregirTamanyoVentana() {
    	if(filas <= 4 && columnas <= 4) {
    		thisStage.setHeight(800);
    		thisStage.setWidth(910);
    	}else {
    		thisStage.setHeight(860);
    		thisStage.setWidth(1400);
    	}
    }

    public void corregirPosicionVentana(double anteriorX, double anteriorY) {
    	thisStage.setX(0);
    	thisStage.setY(0);
    }
    
    public void actualizarEstilo(String nuevoEstilo) {
    	estilo = nuevoEstilo;
    }

}