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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ControladorPartidaCarta {

    @FXML
    private ImageView iconoTiempo;

    @FXML
    private Label tiempo;

    @FXML
    private ImageView iconoPuntos;

    @FXML
    private Label puntos;

    @FXML
    private ImageView iconoSonido;

    @FXML
    private ImageView pausa;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView siguienteImagenMostrada;

    @FXML
    private StackPane stackPane;

    @FXML
    private GridPane tablero;

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

    @FXML
    private Label puntosAnyadidos;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Tablero tableroPartida;
    
    private Baraja barajaPartidaCarta;
    
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
    
    private int puntosAnteriores;
    
    private int indiceBarajaAuxiliar;
    
    private int cartas;
    
    private int nivel;
	
	private ArrayList<Carta> parejasFalladas;
	
	private long tiempoMusica;
    
    private boolean esPrimeraCarta;
     
    private boolean esVictoria;

	private boolean esDerrota;
	
	private boolean esNiveles;
    
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

    public void iniciarPartidaCarta(Stage stage, Singleton nuevoSingleton, String ventanaAnterior, boolean niveles, int nuevoNivel){
    	primaryStage = stage;
        singleton = nuevoSingleton;
        esNiveles = niveles;
        nivel = nuevoNivel;
        cartas = singleton.filasPartida*singleton.columnasPartida;
        inicializarBaraja();
        inicializarTablero();
        inicializarCartas();
    	inicializarVariables();
    	inicializarAudioClips();
    	inicializarContadorTiempo();
    	inicializarPuntuacion();
    	inicializarAnimaciones();
    	actualizarSonido();
    	actualizarImagenSonido();
    	corregirTamanyoVentana();
    	corregirPosicionVentana(ventanaAnterior);
    	actualizarEstilo();
    	mostrarSiguienteCarta();
    	if(singleton.mostrarCartasOn) {
    		mostrarCartas();
    	}
    }
    
    public void inicializarBaraja() {
    	barajaPartidaCarta = new Baraja(singleton.barajaPartida.getNombre(), singleton.barajaPartida.getImagenDorso(), cartas);
        int cartasInsertadas = 0;
        Carta aInsertar;
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < cartas/2; j++) {
                aInsertar = new Carta(singleton.barajaPartida.getCarta(j).getImagenDorso(), singleton.barajaPartida.getCarta(j).getImagenFrente(), j);
                barajaPartidaCarta.setCarta(aInsertar, cartasInsertadas++);
            }
        
         }
    	
    	barajaAuxiliar = new Baraja(barajaPartidaCarta.getNombre(), barajaPartidaCarta.getImagenDorso(), barajaPartidaCarta.getTamanyo()/2);
    	cartasInsertadas = 0;
    	for(int i = 0; i < barajaPartidaCarta.getTamanyo() && cartasInsertadas < barajaPartidaCarta.getTamanyo()/2 ; i++) {
    		boolean noExiste = true;
    		for(int j = 0; j < cartasInsertadas; j++) {
        		if(barajaPartidaCarta.getCarta(i).getId() == barajaAuxiliar.getCarta(j).getId()) {
        			noExiste = false;
        		}
    		}
    		if(noExiste) {
    			barajaAuxiliar.setCarta(barajaPartidaCarta.getCarta(i), cartasInsertadas++);
    		}
    	}
    	
    	barajaPartidaCarta.barajar();
    	barajaAuxiliar.barajar();
    }
    
    private void inicializarTablero() {
    	tableroPartida = new Tablero(singleton.filasPartida, singleton.columnasPartida);
    	tableroPartida.llenarTablero(barajaPartidaCarta);
    	tablero.getColumnConstraints().clear();
    	tablero.getRowConstraints().clear();
    	tableroPartida.setTamanyo(singleton.filasPartida, singleton.columnasPartida);
        for (int i = 0; i < singleton.columnasPartida; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setHalignment(HPos.CENTER);
            colConst.setPercentWidth(100.0 / singleton.columnasPartida);
            tablero.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < singleton.filasPartida; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setValignment(VPos.CENTER);
            rowConst.setPercentHeight(100.0 / singleton.filasPartida);
            tablero.getRowConstraints().add(rowConst);         
        }
    }
    
    public void inicializarCartas() {
    	carta00.setImage(singleton.barajaPartida.getImagenDorso());
    	carta01.setImage(singleton.barajaPartida.getImagenDorso());
    	carta02.setImage(singleton.barajaPartida.getImagenDorso());
    	carta03.setImage(singleton.barajaPartida.getImagenDorso());
    	carta04.setImage(singleton.barajaPartida.getImagenDorso());
    	carta05.setImage(singleton.barajaPartida.getImagenDorso());
    	carta10.setImage(singleton.barajaPartida.getImagenDorso());
    	carta11.setImage(singleton.barajaPartida.getImagenDorso());
    	carta12.setImage(singleton.barajaPartida.getImagenDorso());
    	carta13.setImage(singleton.barajaPartida.getImagenDorso());
    	carta14.setImage(singleton.barajaPartida.getImagenDorso());
    	carta15.setImage(singleton.barajaPartida.getImagenDorso());
    	carta20.setImage(singleton.barajaPartida.getImagenDorso());
    	carta21.setImage(singleton.barajaPartida.getImagenDorso());
    	carta22.setImage(singleton.barajaPartida.getImagenDorso());
    	carta23.setImage(singleton.barajaPartida.getImagenDorso());
    	carta23.setImage(singleton.barajaPartida.getImagenDorso());
    	carta24.setImage(singleton.barajaPartida.getImagenDorso());
    	carta25.setImage(singleton.barajaPartida.getImagenDorso());
    	carta30.setImage(singleton.barajaPartida.getImagenDorso());
    	carta31.setImage(singleton.barajaPartida.getImagenDorso());
    	carta32.setImage(singleton.barajaPartida.getImagenDorso());
    	carta33.setImage(singleton.barajaPartida.getImagenDorso());
    	carta34.setImage(singleton.barajaPartida.getImagenDorso());
    	carta35.setImage(singleton.barajaPartida.getImagenDorso());
    	carta40.setImage(singleton.barajaPartida.getImagenDorso());
    	carta41.setImage(singleton.barajaPartida.getImagenDorso());
    	carta42.setImage(singleton.barajaPartida.getImagenDorso());
    	carta43.setImage(singleton.barajaPartida.getImagenDorso());
    	carta44.setImage(singleton.barajaPartida.getImagenDorso());
    	carta45.setImage(singleton.barajaPartida.getImagenDorso());
    	carta50.setImage(singleton.barajaPartida.getImagenDorso());
    	carta51.setImage(singleton.barajaPartida.getImagenDorso());
    	carta52.setImage(singleton.barajaPartida.getImagenDorso());
    	carta53.setImage(singleton.barajaPartida.getImagenDorso());
    	carta54.setImage(singleton.barajaPartida.getImagenDorso());
    	carta55.setImage(singleton.barajaPartida.getImagenDorso());
    }
 
   public void inicializarVariables() {
   	   cartasGiradas = 0;
   	   esPrimeraCarta = true;
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
   	   voltearCarta = new AudioClip(getClass().getResource("/sonidos/" + singleton.efectosSonorosVoltear + ".mp3").toString());
       error = new AudioClip(getClass().getResource("/sonidos/error1.mp3").toString());
       acierto = new AudioClip(getClass().getResource("/sonidos/" + singleton.efectosSonorosPareja + ".mp3").toString());
       mismaCarta = new AudioClip(getClass().getResource("/sonidos/error2.mp3").toString());
   }
   
   public void inicializarContadorTiempo() {
   	    if(singleton.limiteTiempoOn) {
	        contadorTiempo = new ContadorTiempo();
	    	contadorTiempo.iniciarTiempoPartidaLibre(tiempo, singleton.tiempoPartida);
	        tiempo.textProperty().addListener((ChangeListener<? super String>) (o, oldVal, newVal) -> {
	        	int minutos = Integer.parseInt(tiempo.getText().substring(0, tiempo.getText().length()-3));
	        	int segundos = Integer.parseInt( tiempo.getText().substring(tiempo.getText().length() - 2));
	        	if(minutos + segundos == 0) derrota();
			});
   	   }else {
   		   tiempo.setText("0:00");
   	   }
   }
   
   public void inicializarPuntuacion() {
   	   puntuacion = new Puntuacion();
   	   puntuacion.getPuntosCambiados().addListener((ChangeListener<? super Boolean>) (o, oldVal, newVal) -> {
   		   puntos.setText(Integer.toString(puntuacion.getPuntos()));
   		   mostrarPuntos(puntuacion.getPuntos() - puntosAnteriores);
	   });
   }
   
	public void inicializarAnimaciones() {
       FabricaAnimaciones[] fabrica;
   	
       fabrica = new FabricaAnimaciones[3];
       fabrica[0] = new FabricaAnimacionVoltear();
       if(singleton.efectosVisualesPareja == "Salto") {
       	fabrica[1] = new FabricaAnimacionParejaCorrecta();
       } else if(singleton.efectosVisualesPareja == "Salto doble") {
       	fabrica[1] = new FabricaAnimacionParejaCorrecta2();       	
       }
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
   
    private void mostrarCartas() {
    	PauseTransition pause = new PauseTransition(Duration.seconds(singleton.tiempoMostrarCartas));
    	stackPane.setDisable(true);
    	carta00.setImage(deImagenACarta(carta00).getImagenFrente());
    	if(singleton.columnasPartida >= 1 && singleton.filasPartida >= 2) carta01.setImage(deImagenACarta(carta01).getImagenFrente());
    	if(singleton.columnasPartida >= 1 && singleton.filasPartida >= 3) carta02.setImage(deImagenACarta(carta02).getImagenFrente());
    	if(singleton.columnasPartida >= 1 && singleton.filasPartida >= 4) carta03.setImage(deImagenACarta(carta03).getImagenFrente());
    	if(singleton.columnasPartida >= 1 && singleton.filasPartida >= 5) carta04.setImage(deImagenACarta(carta04).getImagenFrente());
    	if(singleton.columnasPartida >= 1 && singleton.filasPartida >= 6)	carta05.setImage(deImagenACarta(carta05).getImagenFrente());
    	if(singleton.columnasPartida >= 2 && singleton.filasPartida >= 1) carta10.setImage(deImagenACarta(carta10).getImagenFrente());
    	if(singleton.columnasPartida >= 2 && singleton.filasPartida >= 2) carta11.setImage(deImagenACarta(carta11).getImagenFrente());
    	if(singleton.columnasPartida >= 2 && singleton.filasPartida >= 3) carta12.setImage(deImagenACarta(carta12).getImagenFrente());
    	if(singleton.columnasPartida >= 2 && singleton.filasPartida >= 4) carta13.setImage(deImagenACarta(carta13).getImagenFrente());
    	if(singleton.columnasPartida >= 2 && singleton.filasPartida >= 5) carta14.setImage(deImagenACarta(carta14).getImagenFrente());
    	if(singleton.columnasPartida >= 2 && singleton.filasPartida >= 6) carta15.setImage(deImagenACarta(carta15).getImagenFrente());
    	if(singleton.columnasPartida >= 3 && singleton.filasPartida >= 1) carta20.setImage(deImagenACarta(carta20).getImagenFrente());
    	if(singleton.columnasPartida >= 3 && singleton.filasPartida >= 2) carta21.setImage(deImagenACarta(carta21).getImagenFrente());
    	if(singleton.columnasPartida >= 3 && singleton.filasPartida >= 3) carta22.setImage(deImagenACarta(carta22).getImagenFrente());
    	if(singleton.columnasPartida >= 3 && singleton.filasPartida >= 4) carta23.setImage(deImagenACarta(carta23).getImagenFrente());
    	if(singleton.columnasPartida >= 3 && singleton.filasPartida >= 5) carta24.setImage(deImagenACarta(carta24).getImagenFrente());
    	if(singleton.columnasPartida >= 3 && singleton.filasPartida >= 6) carta25.setImage(deImagenACarta(carta25).getImagenFrente());
    	if(singleton.columnasPartida >= 4 && singleton.filasPartida >= 1) carta30.setImage(deImagenACarta(carta30).getImagenFrente());
    	if(singleton.columnasPartida >= 4 && singleton.filasPartida >= 2) carta31.setImage(deImagenACarta(carta31).getImagenFrente());
    	if(singleton.columnasPartida >= 4 && singleton.filasPartida >= 3) carta32.setImage(deImagenACarta(carta32).getImagenFrente());
    	if(singleton.columnasPartida >= 4 && singleton.filasPartida >= 4) carta33.setImage(deImagenACarta(carta33).getImagenFrente());
    	if(singleton.columnasPartida >= 4 && singleton.filasPartida >= 5) carta34.setImage(deImagenACarta(carta34).getImagenFrente());
    	if(singleton.columnasPartida >= 4 && singleton.filasPartida >= 6) carta35.setImage(deImagenACarta(carta35).getImagenFrente());
    	if(singleton.columnasPartida >= 5 && singleton.filasPartida >= 1) carta40.setImage(deImagenACarta(carta40).getImagenFrente());
    	if(singleton.columnasPartida >= 5 && singleton.filasPartida >= 2) carta41.setImage(deImagenACarta(carta41).getImagenFrente());
    	if(singleton.columnasPartida >= 5 && singleton.filasPartida >= 3) carta42.setImage(deImagenACarta(carta42).getImagenFrente());
    	if(singleton.columnasPartida >= 5 && singleton.filasPartida >= 4) carta43.setImage(deImagenACarta(carta43).getImagenFrente());
    	if(singleton.columnasPartida >= 5 && singleton.filasPartida >= 5) carta44.setImage(deImagenACarta(carta44).getImagenFrente());
    	if(singleton.columnasPartida >= 5 && singleton.filasPartida >= 6) carta45.setImage(deImagenACarta(carta45).getImagenFrente());
    	if(singleton.columnasPartida >= 6 && singleton.filasPartida >= 1) carta50.setImage(deImagenACarta(carta50).getImagenFrente());
    	if(singleton.columnasPartida >= 6 && singleton.filasPartida >= 2) carta51.setImage(deImagenACarta(carta51).getImagenFrente());
    	if(singleton.columnasPartida >= 6 && singleton.filasPartida >= 3) carta52.setImage(deImagenACarta(carta52).getImagenFrente());
    	if(singleton.columnasPartida >= 6 && singleton.filasPartida >= 4) carta53.setImage(deImagenACarta(carta53).getImagenFrente());
    	if(singleton.columnasPartida >= 6 && singleton.filasPartida >= 5) carta54.setImage(deImagenACarta(carta54).getImagenFrente());
    	if(singleton.columnasPartida >= 6 && singleton.filasPartida >= 6) carta55.setImage(deImagenACarta(carta55).getImagenFrente());
    	pause.setOnFinished(e -> {
    		if(singleton.columnasPartida >= 1 && singleton.filasPartida >= 1)carta00.setImage(singleton.barajaPartida.getImagenDorso());
    		if(singleton.columnasPartida >= 1 && singleton.filasPartida >= 2)carta01.setImage(singleton.barajaPartida.getImagenDorso());
    		if(singleton.columnasPartida >= 1 && singleton.filasPartida >= 3)carta02.setImage(singleton.barajaPartida.getImagenDorso());
    		if(singleton.columnasPartida >= 1 && singleton.filasPartida >= 4)carta03.setImage(singleton.barajaPartida.getImagenDorso());
    		if(singleton.columnasPartida >= 1 && singleton.filasPartida >= 5)carta04.setImage(singleton.barajaPartida.getImagenDorso());
    		if(singleton.columnasPartida >= 1 && singleton.filasPartida >= 6)carta05.setImage(singleton.barajaPartida.getImagenDorso());
    		if(singleton.columnasPartida >= 2 && singleton.filasPartida >= 1)carta10.setImage(singleton.barajaPartida.getImagenDorso());
    		if(singleton.columnasPartida >= 2 && singleton.filasPartida >= 2)carta11.setImage(singleton.barajaPartida.getImagenDorso());
    		if(singleton.columnasPartida >= 2 && singleton.filasPartida >= 3)carta12.setImage(singleton.barajaPartida.getImagenDorso());
    		if(singleton.columnasPartida >= 2 && singleton.filasPartida >= 4)carta13.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 2 && singleton.filasPartida >= 5)carta14.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 2 && singleton.filasPartida >= 6)carta15.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 3 && singleton.filasPartida >= 1)carta20.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 3 && singleton.filasPartida >= 2)carta21.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 3 && singleton.filasPartida >= 3)carta22.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 3 && singleton.filasPartida >= 4)carta23.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 3 && singleton.filasPartida >= 5)carta24.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 3 && singleton.filasPartida >= 6)carta25.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 4 && singleton.filasPartida >= 1)carta30.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 4 && singleton.filasPartida >= 2)carta31.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 4 && singleton.filasPartida >= 3)carta32.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 4 && singleton.filasPartida >= 4)carta33.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 4 && singleton.filasPartida >= 5)carta34.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 4 && singleton.filasPartida >= 6)carta35.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 5 && singleton.filasPartida >= 1)carta40.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 5 && singleton.filasPartida >= 2)carta41.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 5 && singleton.filasPartida >= 3)carta42.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 5 && singleton.filasPartida >= 4)carta43.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 5 && singleton.filasPartida >= 5)carta44.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 5 && singleton.filasPartida >= 6)carta45.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 6 && singleton.filasPartida >= 1)carta50.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 6 && singleton.filasPartida >= 2)carta51.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 6 && singleton.filasPartida >= 3)carta52.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 6 && singleton.filasPartida >= 4)carta53.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 6 && singleton.filasPartida >= 5)carta54.setImage(singleton.barajaPartida.getImagenDorso());
        	if(singleton.columnasPartida >= 6 && singleton.filasPartida >= 6)carta55.setImage(singleton.barajaPartida.getImagenDorso());
            stackPane.setDisable(false);
        });
    	pause.play();
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
    	if(cartasGiradas == barajaPartidaCarta.getTamanyo()) {
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
    	if(singleton.limiteTiempoOn) {
    		contadorTiempo.parar();
    		puntuacion.sumarBonificacionVictoria(contadorTiempo.getTiempoRestante(), tableroPartida.getNumParejas());
    	}
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
    		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
    		if(isVictoria()) {
            	controladorResultadoPartida.iniciarResultado(primaryStage, puntuacionFinal, tiempoSobrante, true, "carta", singleton, "partidaCarta", esNiveles, nivel);
        	} else {
        		controladorResultadoPartida.iniciarResultado(primaryStage, puntuacionFinal, tiempoSobrante, false, "carta", singleton, "partidaCarta", esNiveles, nivel);
        	}
    		stage.show();
    	} catch (IOException e) {
    		
    	}
    }
 
    @FXML
    void pausarPartida(MouseEvent event) {
    	try {
    		puntuacion.stopTimeLine();
    		if(singleton.limiteTiempoOn) {
        		contadorTiempo.setEsPausa(true);
    		}
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
        	controladorMenuPausa.initDataPartidaCarta(primaryStage, tiempo.getText(), Integer.toString(puntuacion.getPuntos()), this, singleton, "partidaCarta");
        	stage.show();
        	stage.toFront();
    	} catch (IOException e) {
    		
    	}
    }
    
    public void reanudarPartida(boolean Sound, String ventanaAnterior) {
    	puntuacion.playTimeline();
    	corregirTamanyoVentana();
    	corregirPosicionVentana(ventanaAnterior);
    	primaryStage.show();
    	if(singleton.limiteTiempoOn) {
        	contadorTiempo.setEsPausa(false);
        	contadorTiempo.continuar();
    	}
    	singleton.soundOn = Sound;
    	actualizarSonido();
    	actualizarImagenSonido();  	
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
    
    public void corregirTamanyoVentana() {
    	if(singleton.filasPartida <= 4 && singleton.columnasPartida <= 4) {
    		thisStage.setHeight(950);
    		thisStage.setWidth(910);
    	}else {
    		thisStage.setHeight(1000);
    		thisStage.setWidth(1400);
    		iconoTiempo.setTranslateX(250);
    		tiempo.setTranslateX(270);
    		iconoPuntos.setTranslateX(350);
    		puntos.setTranslateX(370);
    		iconoSonido.setTranslateX(450);
    		pausa.setTranslateX(470);
    	}
    }

    public void corregirPosicionVentana(String ventanaAnterior) {
    	if(ventanaAnterior.equals("menuPrincipal")) {
        	if(singleton.filasPartida <= 4 && singleton.columnasPartida <= 4) {
        		thisStage.setX(singleton.posicionX + 50);
            	thisStage.setY(singleton.posicionY - 50);
        	} else {
        		thisStage.setX(250);
        		thisStage.setY(10);
        	}
    	} else if(ventanaAnterior.equals("menuPause")) {
    		if(singleton.filasPartida <= 4 && singleton.columnasPartida <= 4) {
        		thisStage.setX(singleton.posicionX);
            	thisStage.setY(singleton.posicionY - 50);
        	} else {
        		thisStage.setX(250);
        		thisStage.setY(10);
        	}
    	}  else if(ventanaAnterior.equals("resultadoPartida")) {
    		if(singleton.filasPartida <= 4 && singleton.columnasPartida <= 4) {
        		thisStage.setX(singleton.posicionX);
            	thisStage.setY(singleton.posicionY - 30);
        	} else {
        		thisStage.setX(250);
        		thisStage.setY(10);
        	}
    	} else if(ventanaAnterior.equals("seleccionNiveles")) {
    		if(singleton.filasPartida <= 4 && singleton.columnasPartida <= 4) {
        		thisStage.setX(singleton.posicionX + 50);
            	thisStage.setY(singleton.posicionY - 50);
            } else {
        		thisStage.setX(250);
        		thisStage.setY(10);
        	}
    	}
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