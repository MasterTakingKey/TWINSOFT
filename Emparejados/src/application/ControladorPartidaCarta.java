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
    
    private AudioClip voltearCarta;
    
    private AudioClip error;
    
    private AudioClip acierto;
    
    private AudioClip mismaCarta;
	
    private ContadorTiempo contadorTiempo;
    
    private Puntuacion puntuacion;
    
    private Singleton singleton;
    
    private Animaciones animacionVoltear;
    
    private Animaciones animacionParejaCorrecta;
    
    private Animaciones animacionParejaIncorrecta;

    public void iniciarPartidaCarta(Stage stage, Singleton nuevoSingleton){
    	primaryStage = stage;
        singleton = nuevoSingleton;
        inicializarBarajaTablero();
        inicializarCartas();
    	inicializarVariables();
    	inicializarAudioClips();
    	inicializarContadorTiempo();
    	inicializarPuntuacion();
    	inicializarAnimaciones();
    	actualizarSonido();
    	actualizarImagenSonido();
    	corregirTamanyoVentana();
    	corregirPosicionVentana();
    	actualizarEstilo();
    	mostrarSiguienteCarta();
    }
    
    public void inicializarAnimaciones() {
        FabricaAnimaciones[] fabrica;
    	
        fabrica = new FabricaAnimaciones[3];
        fabrica[0] = new FabricaAnimacionVoltear();
        fabrica[1] = new FabricaAnimacionParejaCorrecta();
        fabrica[2] = new FabricaAnimacionParejaIncorrecta();
        
        animacionVoltear = fabrica[0].animacionesMetodoFabrica();
        animacionVoltear.stackPane = stackPane;
        animacionVoltear.baraja = singleton.barajaPartida;
        
        animacionParejaCorrecta = fabrica[1].animacionesMetodoFabrica();
        animacionParejaCorrecta.stackPane = stackPane;
        animacionParejaCorrecta.baraja = singleton.barajaPartida;
        
        animacionParejaIncorrecta = fabrica[2].animacionesMetodoFabrica();
        animacionParejaIncorrecta.stackPane = stackPane;
        animacionParejaIncorrecta.baraja = singleton.barajaPartida;
    }
    
    public void inicializarBarajaTablero() {
    	barajaAuxiliar = new Baraja(singleton.barajaPartida.getNombre(), singleton.barajaPartida.getImagenDorso(), singleton.barajaPartida.getTamanyo()/2);
    	int cartasInsertadas = 0;
    	for(int i = 0; i < singleton.barajaPartida.getTamanyo() && cartasInsertadas < singleton.barajaPartida.getTamanyo()/2 ; i++) {
    		boolean noExiste = true;
    		for(int j = 0; j < cartasInsertadas; j++) {
        		if(singleton.barajaPartida.getCarta(i).getId() == barajaAuxiliar.getCarta(j).getId()) {
        			noExiste = false;
        		}
    		}
    		if(noExiste) {
    			barajaAuxiliar.setCarta(singleton.barajaPartida.getCarta(i), cartasInsertadas++);
    		}
    	}
    	singleton.barajaPartida.barajar();
    	barajaAuxiliar.barajar();
        indiceBarajaAuxiliar = 0;
    	tableroPartida = new Tablero(4, 4);
    	tableroPartida.llenarTablero(singleton.barajaPartida);
    }
    
    public void inicializarCartas() {
    	carta00.setImage(singleton.barajaPartida.getImagenDorso());
    	carta01.setImage(singleton.barajaPartida.getImagenDorso());
    	carta02.setImage(singleton.barajaPartida.getImagenDorso());
    	carta03.setImage(singleton.barajaPartida.getImagenDorso());
    	carta10.setImage(singleton.barajaPartida.getImagenDorso());
    	carta11.setImage(singleton.barajaPartida.getImagenDorso());
    	carta12.setImage(singleton.barajaPartida.getImagenDorso());
    	carta13.setImage(singleton.barajaPartida.getImagenDorso());
    	carta20.setImage(singleton.barajaPartida.getImagenDorso());
    	carta21.setImage(singleton.barajaPartida.getImagenDorso());
    	carta22.setImage(singleton.barajaPartida.getImagenDorso());
    	carta23.setImage(singleton.barajaPartida.getImagenDorso());
    	carta23.setImage(singleton.barajaPartida.getImagenDorso());
    	carta30.setImage(singleton.barajaPartida.getImagenDorso());
    	carta31.setImage(singleton.barajaPartida.getImagenDorso());
    	carta32.setImage(singleton.barajaPartida.getImagenDorso());
    	carta33.setImage(singleton.barajaPartida.getImagenDorso());
    }
    
    public void inicializarVariables() {
    	esPrimeraCarta = true;
    	cartasGiradas = 0;
    	esVictoria = false;
    	esDerrota = false;
    	parejasFalladas = new ArrayList<Carta>(tableroPartida.getNumParejas());
    	musicaFondo = new Musica("src/sonidos/"+ singleton.listaMusica[0] +".wav", 0L);
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
    		animacionVoltear.imagen1 = imagenSeleccionada;
    		animacionVoltear.carta = cartaSeleccionada;
    		animacionVoltear.crearAnimacion();
    		esPrimeraCarta = false;
    	} else {
    		puntuacion.getTimeline().stop();
    		segundaCarta = cartaSeleccionada;
    		segundaImagen = imagenSeleccionada;
    		if(primeraCarta == segundaCarta) {
    			mismaCarta.play();
    		} else {
				voltearCarta.play();
				animacionVoltear.imagen1 = imagenSeleccionada;
	    		animacionVoltear.carta = cartaSeleccionada;
	    		animacionVoltear.crearAnimacion();
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
    	animacionParejaCorrecta.imagen1 = primeraImagen;
		animacionParejaCorrecta.imagen2 = segundaImagen;
		animacionParejaCorrecta.crearAnimacion();
    	primeraImagen.setDisable(true);
    	segundaImagen.setDisable(true);
    	if(cartasGiradas == singleton.barajaPartida.getTamanyo()) {
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
    	animacionParejaIncorrecta.imagen1 = primeraImagen;
		animacionParejaIncorrecta.imagen2 = segundaImagen;
		animacionParejaIncorrecta.crearAnimacion();
    	primeraImagen.setImage(singleton.barajaPartida.getImagenDorso());
    	segundaImagen.setImage(singleton.barajaPartida.getImagenDorso());
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
    		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
    		if(isVictoria()) {
            	controladorResultadoPartida.iniciarResultado(primaryStage, puntuacionFinal, tiempoSobrante, true, "carta", 4, 4, singleton, true, 90, false, 0, "", "", "", "");
        	} else {
        		controladorResultadoPartida.iniciarResultado(primaryStage, puntuacionFinal, tiempoSobrante, false, "carta", 4, 4, singleton, true, 90, false, 0, "", "", "", "");
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
        	singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
        	controladorMenuPausa.initDataPartidaCarta(primaryStage, this, singleton);
        	stage.show();
        	stage.toFront();
    	} catch (IOException e) {
    		
    	}
    }
    
    public void reanudarPartida(boolean Sound) {
    	corregirTamanyoVentana();
    	corregirPosicionVentana();
    	primaryStage.show();
    	contadorTiempo.setEsPausa(false);
    	contadorTiempo.continuar();
    	singleton.soundOn = Sound;
    	actualizarSonido();
    	actualizarImagenSonido();
    }
    
    public void actualizarSonido() {
    	if(singleton.soundOn) {
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
        if(singleton.soundOn) {
        	iconoSonido.setImage(Sound1);
        } else {
        	iconoSonido.setImage(Sound0);
        }
    }
    
    @FXML
    void clickSound(MouseEvent event) {
    	if(singleton.soundOn) {
    		singleton.soundOn = false;
    		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
    	} else {
    		singleton.soundOn = true;
    	}
    	actualizarSonido();
    	actualizarImagenSonido();
    }
    
    public void corregirTamanyoVentana() {
    	thisStage.setWidth(900);
    	thisStage.setHeight(820);
    }
    
    public void corregirPosicionVentana() {
    	thisStage.setX(singleton.posicionX);
    	thisStage.setY(singleton.posicionY);
    }
    
    public void actualizarEstilo() {
    	String temaAzul = getClass().getResource("estiloAzul.css").toExternalForm();
        String temaRojo = getClass().getResource("estiloRojo.css").toExternalForm();
        String temaVerde = getClass().getResource("estiloVerde.css").toExternalForm();
    	if(singleton.estilo.equals("Azul")) {
    		anchorPane.getStylesheets().remove(temaRojo);
    		anchorPane.getStylesheets().remove(temaVerde);
    		anchorPane.getStylesheets().add(temaAzul);
    	} else if(singleton.estilo.equals("Rojo")) {
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