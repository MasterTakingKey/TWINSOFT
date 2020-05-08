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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class ControladorPartidaCarta {
	
    @FXML
    private AnchorPane anchorPane;
    
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
	
	private int indiceBarajaAuxiliar;
	
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
    
    private Animaciones animaciones;
    
    private String estilo;

    public void iniciarPartidaCarta(Stage stage, boolean soundOn, double anteriorX, double anteriorY, String estilo){
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
    	actualizarEstilo(estilo);
    	mostrarSiguienteCarta();
    }
    
    public void inicializarBarajaTablero() {
    	barajaPartida = new Baraja(4, 4);
    	barajaPartida.barajaTematica(new CrearBarajaNintendoEstrategia(2), 8);
    	barajaPartida.barajar();
    	barajaAuxiliar = new Baraja(4, 4);
    	barajaAuxiliar.barajaTematica(new CrearBarajaNintendoEstrategia(1), 8);
    	barajaAuxiliar.barajar();
        indiceBarajaAuxiliar = 0;
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
    	esPrimeraCarta = true;
    	cartasGiradas = 0;
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
    	contadorTiempo.iniciarTiempoPartidaCarta(tiempo);
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

    public void parejaCorrecta() {
    	puntosAnteriores = puntuacion.getPuntos();
    	puntuacion.sumaPuntos(10, false, 0);
    	acierto.play();
    	animaciones.parejaCorrectaAnimacion(primeraImagen, segundaImagen);
    	primeraImagen.setDisable(true);
    	segundaImagen.setDisable(true);
    	if(cartasGiradas == barajaPartida.getTamanyo()) {
    		victoria();
    	} else {
        	indiceBarajaAuxiliar++;
    	}
    }
    
    public void parejaIncorrecta() {
    	puntosAnteriores = puntuacion.getPuntos();
    	puntuacion.sumaPuntos(-1, true, numeroVecesGirada(primeraCarta));
    	parejasFalladas.add(primeraCarta);
    	parejasFalladas.add(segundaCarta);
    	error.play();
    	animaciones.parejaIncorrectaAnimacion(primeraImagen, segundaImagen);
    	primeraImagen.setImage(barajaPartida.getImagenDorso());
    	segundaImagen.setImage(barajaPartida.getImagenDorso());
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
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.setResizable(false);
    		primaryStage.hide();
    		stage.setTitle("Resultado");
    		if(isVictoria()) {
            	controladorResultadoPartida.iniciarResultado(primaryStage, SoundOn, puntuacionFinal, tiempoSobrante, true, "carta", thisStage.getX(), thisStage.getY(), 4, 4, estilo);
        	} else {
        		controladorResultadoPartida.iniciarResultado(primaryStage, SoundOn, puntuacionFinal, tiempoSobrante, false, "carta", thisStage.getX(), thisStage.getY(), 4, 4, estilo);
        	}
    		stage.show();
    	} catch (IOException e) {
    		
    	}
    }
    
    @FXML
    void pausarPartida(MouseEvent event) {
    	try {
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
        	stage.setOnCloseRequest((WindowEvent event1) -> {controladorMenuPausa.reanudarPartidaCarta();});
        	primaryStage.hide();
        	controladorMenuPausa.initDataPartidaCarta(primaryStage, this, SoundOn, thisStage.getX(), thisStage.getY(), estilo);
        	stage.show();
        	stage.toFront();
    	} catch (IOException e) {
    		
    	}
    }
    
    public void reanudarPartida(boolean Sound, double anteriorX, double anteriorY) {
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
    
    public void corregirTamanyoVentana() {
    	thisStage.setWidth(900);
    	thisStage.setHeight(820);
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
    		anchorPane.getStylesheets().remove(temaRojo);
    		anchorPane.getStylesheets().remove(temaVerde);
    		anchorPane.getStylesheets().add(temaAzul);
    	} else if(estilo.equals("Rojo")) {
    		anchorPane.getStylesheets().remove(temaAzul);
			anchorPane.getStylesheets().remove(temaVerde);
			anchorPane.getStylesheets().add(temaRojo);
    	} else {
    		anchorPane.getStylesheets().remove(temaAzul);
			anchorPane.getStylesheets().remove(temaRojo);
			anchorPane.getStylesheets().add(temaVerde);
    	}
    }

}