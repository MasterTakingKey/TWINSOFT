package application;

import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class ControladorMultijugador extends PlantillaPartidas{
	@FXML
    private GridPane tablero;
	
	@FXML
    private Label tiempo = new Label();
    
    @FXML
    private Label puntosAnyadidos;
    
    @FXML
    private ImageView iconoTiempo;
    
    @FXML
    private ImageView iconoPuntos;
    
    @FXML
    private ImageView iconoSonido;

    @FXML
    private ImageView pausa;
    
    @FXML
    private StackPane stackPane;

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
    private Label nombreJ1;
    
    @FXML
    private Label nombreJ2;
    
    @FXML
    private Label puntosJ1 = new Label();
    
    @FXML
    private Label puntosJ2 = new Label();
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Tablero tableroPartida;
    
    private Baraja barajaPartidaEstandar;
  
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
    
    private int puntosAnterioresJ1;
    
    private int puntosAnterioresJ2;
    
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
    
    private Puntuacion puntuacionJ1;
    
    private Puntuacion puntuacionJ2;
    
    private ConfiguracionPartida singleton;
    
    private Animaciones animacionVoltear;
    
    private Animaciones animacionParejaCorrecta;
    
    private Animaciones animacionParejaIncorrecta;
    
    private Animaciones animacionFinTurno;
    
    private int turnoActual;
    
    private boolean turnoTerminado;

    public void iniciarMultijugador(Stage stage, ConfiguracionPartida nuevoSingleton, String ventanaAnterior, boolean niveles, int nuevoNivel, String nombreJ1, String nombreJ2){
    	primaryStage = stage;
        singleton = nuevoSingleton;
        esNiveles = niveles;
        nivel = nuevoNivel;
        cartas = singleton.filasPartida*singleton.columnasPartida;
        this.nombreJ1.setText(nombreJ1);
        this.nombreJ2.setText(nombreJ2);
        this.nombreJ1.setTextFill(Color.GREEN);
    }
    
    @Override
    public void inicializarBaraja() {
    	barajaPartidaEstandar = new Baraja(singleton.barajaPartida.getNombre(), singleton.barajaPartida.getImagenDorso(), cartas);
        int cartasInsertadas = 0;
        Carta aInsertar;
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < cartas/2; j++) {
                aInsertar = new Carta(singleton.barajaPartida.getCarta(j).getImagenDorso(), singleton.barajaPartida.getCarta(j).getImagenFrente(), j);
                barajaPartidaEstandar.setCarta(aInsertar, cartasInsertadas++);
            }
        }
    	barajaPartidaEstandar.barajar();
    }
    
    @Override
    protected void inicializarTablero() {
    	tableroPartida = new Tablero(singleton.filasPartida, singleton.columnasPartida);
    	tableroPartida.llenarTablero(barajaPartidaEstandar);
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
    
    @Override
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
 
   @Override
   public void inicializarVariables() {
   	cartasGiradas = 0;
   	esPrimeraCarta = true;
   	esVictoria = false;
   	esDerrota = false;
   	parejasFalladas = new ArrayList<Carta>(tableroPartida.getNumParejas());
   	musicaFondo = new Musica("/sonidos/"+ singleton.listaMusica[0] +".wav", 0L);
   	Sound0 = new Image("/imagenes/sonido_off_2.png");
       Sound1 = new Image("/imagenes/sonido_on_2.png");
       puntosAnyadidos.setVisible(false);
       thisStage = (Stage) carta00.getScene().getWindow();
    turnoActual = 1;
    turnoTerminado = false;
   }
   
   @Override
   public void inicializarAudioClips() {
   	voltearCarta = new AudioClip(getClass().getResource("/sonidos/" + singleton.efectosSonorosVoltear + ".mp3").toString());
       error = new AudioClip(getClass().getResource("/sonidos/Error1.mp3").toString());
       acierto = new AudioClip(getClass().getResource("/sonidos/" + singleton.efectosSonorosPareja + ".mp3").toString());
       mismaCarta = new AudioClip(getClass().getResource("/sonidos/Error2.mp3").toString());
   }
   
   @Override
   public void inicializarContadorTiempo() {
   	if(singleton.limiteTiempoOn) {
	        contadorTiempo = new ContadorTiempo();
	    	contadorTiempo.iniciarTiempoPartida(tiempo, singleton.tiempoPartida);
	        tiempo.textProperty().addListener((ChangeListener<? super String>) (o, oldVal, newVal) -> {
	        	int minutos = Integer.parseInt(tiempo.getText().substring(0, tiempo.getText().length()-3));
	        	int segundos = Integer.parseInt( tiempo.getText().substring(tiempo.getText().length() - 2));
	        	if(minutos + segundos == 0) derrota();
			});
   	}else {
   		tiempo.setText("0:00");
   	}
   }
   
   @Override
   public void inicializarPuntuacion() {
   	puntuacionJ1 = new Puntuacion();
   	puntuacionJ2 = new Puntuacion();
   	puntuacionJ1.setNombre(nombreJ1);
   	puntuacionJ2.setNombre(nombreJ2);
   	puntuacionJ1.iniciarTiempoEntreTurnosMulti(singleton.soundOn);
   	puntuacionJ1.getPuntosCambiados().addListener((ChangeListener<? super Boolean>) (o, oldVal, newVal) -> {
   		puntosJ1.setText(Integer.toString(puntuacionJ1.getPuntos()));
       	mostrarPuntos(puntuacionJ1.getPuntos() - puntosAnterioresJ1);
		});
   	puntuacionJ2.getPuntosCambiados().addListener((ChangeListener<? super Boolean>) (o, oldVal, newVal) -> {
   		puntosJ2.setText(Integer.toString(puntuacionJ2.getPuntos()));
       	mostrarPuntos(puntuacionJ2.getPuntos() - puntosAnterioresJ2);
		});
   	puntuacionJ1.getCambioTurno().addListener((ChangeListener<? super Boolean>) (o, oldVal, newVal) -> {
   		puntosAnterioresJ1 = puntuacionJ1.getPuntos();
   		if(!esPrimeraCarta) {
			animacionFinTurno.imagen1 = primeraImagen;
			animacionFinTurno.crearAnimacion();
			cartasGiradas--;
			esPrimeraCarta = true;
		}
   			nombreJ2.setTextFill(Color.GREEN);
   			nombreJ1.setTextFill(Color.BLACK);
   			turnoActual = 2;
   			puntuacionJ2.iniciarTiempoEntreTurnosMulti(singleton.soundOn);
		});
   	puntuacionJ2.getCambioTurno().addListener((ChangeListener<? super Boolean>) (o, oldVal, newVal) -> {
   		puntosAnterioresJ2 = puntuacionJ2.getPuntos();
   		if(!esPrimeraCarta) {
			animacionFinTurno.imagen1 = primeraImagen;
			animacionFinTurno.crearAnimacion();
			cartasGiradas--;
			esPrimeraCarta = true;
		}
   			nombreJ1.setTextFill(Color.GREEN);
   			nombreJ2.setTextFill(Color.BLACK);
   			turnoActual = 1;
   			puntuacionJ1.iniciarTiempoEntreTurnosMulti(singleton.soundOn);
		});
   }
   
    @Override
	public void inicializarAnimaciones() {
       FabricaAnimaciones[] fabrica;
   	
       fabrica = new FabricaAnimaciones[4];
       fabrica[0] = new FabricaAnimacionVoltear();
       if(singleton.efectosVisualesPareja == "Salto") {
       	fabrica[1] = new FabricaAnimacionParejaCorrecta();
       } else if(singleton.efectosVisualesPareja == "Salto doble") {
       	fabrica[1] = new FabricaAnimacionParejaCorrecta2();       	
       }
       fabrica[2] = new FabricaAnimacionParejaIncorrecta();
       fabrica[3] = new FabricaAnimacionFinTurno(); 
       
       animacionVoltear = fabrica[0].animacionesMetodoFabrica();
       animacionVoltear.stackPane = stackPane;
       animacionVoltear.baraja = singleton.barajaPartida;
       
       animacionParejaCorrecta = fabrica[1].animacionesMetodoFabrica();
       animacionParejaCorrecta.stackPane = stackPane;
       animacionParejaCorrecta.baraja = singleton.barajaPartida;
       
       animacionParejaIncorrecta = fabrica[2].animacionesMetodoFabrica();
       animacionParejaIncorrecta.stackPane = stackPane;
       animacionParejaIncorrecta.baraja = singleton.barajaPartida;
       
       animacionFinTurno = fabrica[3].animacionesMetodoFabrica();
       animacionFinTurno.stackPane = stackPane;
       animacionFinTurno.baraja = singleton.barajaPartida;
       
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
    		if(turnoActual == 1) {
    			puntosAnterioresJ1 = puntuacionJ1.getPuntos();
    		} else if (turnoActual == 2) {
    			puntosAnterioresJ2 = puntuacionJ2.getPuntos();
    		}
    		primeraCarta = cartaSeleccionada;
    		primeraImagen = imagenSeleccionada;
    		animacionVoltear.imagen1 = imagenSeleccionada;
    		animacionVoltear.carta = cartaSeleccionada;
    		animacionVoltear.crearAnimacion();
    		esPrimeraCarta = false;
    	} else {
    		if(turnoActual == 1) {
    			puntuacionJ1.getTimeline().stop();
    		} else if (turnoActual == 2) {
    			puntuacionJ2.getTimeline().stop();
    		}
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
    	if(turnoActual == 1) {
			puntosAnterioresJ1 = puntuacionJ1.getPuntos();
			puntuacionJ1.sumaPuntos(10, false, 0);
			puntuacionJ1.setCambioTurno(!puntuacionJ1.getCambioTurno().getValue());
		} else if (turnoActual == 2) {
			puntosAnterioresJ2 = puntuacionJ2.getPuntos();
			puntuacionJ2.sumaPuntos(10, false, 0);
			puntuacionJ2.setCambioTurno(!puntuacionJ2.getCambioTurno().getValue());
		}
    	acierto.play();
    	animacionParejaCorrecta.imagen1 = primeraImagen;
		animacionParejaCorrecta.imagen2 = segundaImagen;
		animacionParejaCorrecta.crearAnimacion();
    	primeraImagen.setDisable(true);
    	segundaImagen.setDisable(true);
    	if(cartasGiradas == cartas) {
    		victoria();
    	}
    }
    
    public void parejaIncorrecta() {
    	if(turnoActual == 1) {
			puntosAnterioresJ1 = puntuacionJ1.getPuntos();
			puntuacionJ1.sumaPuntos(-1, true, numeroVecesGirada(primeraCarta));
			puntuacionJ1.setCambioTurno(!puntuacionJ1.getCambioTurno().getValue());
		} else if (turnoActual == 2) {
			puntosAnterioresJ2 = puntuacionJ2.getPuntos();
			puntuacionJ2.sumaPuntos(-1, true, numeroVecesGirada(primeraCarta));
			puntuacionJ2.setCambioTurno(!puntuacionJ2.getCambioTurno().getValue());
		}
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
    		if(turnoActual == 1) {
    			puntuacionJ2.sumarBonificacionVictoria(contadorTiempo.getTiempoRestante(), tableroPartida.getNumParejas());
    		} else if (turnoActual == 2) {
    			puntuacionJ1.sumarBonificacionVictoria(contadorTiempo.getTiempoRestante(), tableroPartida.getNumParejas());
    		}
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
    		puntuacionJ1.getTimeline().stop();
    		puntuacionJ2.getTimeline().stop();
        	String puntuacionFinalJ1 = Integer.toString(puntuacionJ1.getPuntos());
        	String puntuacionFinalJ2 = Integer.toString(puntuacionJ2.getPuntos());
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
            	controladorResultadoPartida.iniciarResultado(primaryStage, puntuacionFinalJ1, puntuacionFinalJ2, tiempoSobrante, true, "multi", singleton, "partidaEstandar", esNiveles, nivel, nombreJ1.getText(), nombreJ2.getText());
        	} else {
        		controladorResultadoPartida.iniciarResultado(primaryStage, puntuacionFinalJ1, puntuacionFinalJ2, tiempoSobrante, false, "multi", singleton, "partidaEstandar", esNiveles, nivel, nombreJ1.getText(), nombreJ2.getText());
        	}
    		stage.show();
    	} catch (IOException e) {
    		
    	}
    }
 
    @FXML
    void pausarPartida(MouseEvent event) {
    	try {
    		puntuacionJ1.stopTimeLine();
    		puntuacionJ2.stopTimeLine();
    		if(singleton.limiteTiempoOn) {
        		contadorTiempo.setEsPausa(true);
    		}
    		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
    		musicaFondo.stopMusic();
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPausaMultijugador.fxml"));
    		Parent root = (Parent) myLoader.load();
    		ControladorMenuPausaMultijugador controladorMenuPausaMultijugador = myLoader.<ControladorMenuPausaMultijugador>getController();
    		Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		stage.setScene(scene);
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.setResizable(false);
        	stage.setOnCloseRequest((WindowEvent event1) -> {controladorMenuPausaMultijugador.reanudarMulti();});
        	primaryStage.hide();
        	singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
        	controladorMenuPausaMultijugador.initDataMultijugador(primaryStage, tiempo.getText(), Integer.toString(puntuacionJ1.getPuntos()), Integer.toString(puntuacionJ2.getPuntos()), this, singleton, "partidaEstandar", esNiveles, turnoActual);
        	stage.show();
        	stage.toFront();
    	} catch (IOException e) {
    		
    	}
    }
    
    public void reanudarPartida(boolean Sound, String ventanaAnterior, int turno) {
    	turnoActual = turno;
    	if(turnoActual == 1) {
            puntuacionJ1.playTimeline();
        } else if (turnoActual == 2) {
            puntuacionJ2.playTimeline();
        }
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
    
    @Override
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
    
    @Override
    public void actualizarImagenSonido() {
        if(singleton.soundOn) {
        	iconoSonido.setImage(Sound1);
        } else {
        	iconoSonido.setImage(Sound0);
        }
    }
    
    @Override
    public void corregirTamanyoVentana() {
    	if(singleton.filasPartida <= 4 && singleton.columnasPartida <= 4) {
    		thisStage.setHeight(800);
    		thisStage.setWidth(950);
    	}else {
    		thisStage.setHeight(860);
    		thisStage.setWidth(1400);
    		iconoTiempo.setTranslateX(250);
    		tiempo.setTranslateX(270);
    		iconoPuntos.setTranslateX(350);
    		puntosJ1.setTranslateX(370);
    		puntosJ2.setTranslateX(400);
    		iconoSonido.setTranslateX(450);
    		pausa.setTranslateX(470);
    	}
    }

    @Override
    public void corregirPosicionVentana(String ventanaAnterior) {
    	if(ventanaAnterior.equals("menuPausa")) {
            if(singleton.filasPartida <= 4 && singleton.columnasPartida <= 4) {
                thisStage.setX(singleton.posicionX);
                thisStage.setY(singleton.posicionY);
            } else {
                thisStage.setX(250);
                thisStage.setY(100);
            }
        }else if(ventanaAnterior.equals("resultadoPartida")) {
            if(singleton.filasPartida <= 4 && singleton.columnasPartida <= 4) {
                thisStage.setX(singleton.posicionX);
                thisStage.setY(singleton.posicionY);
            } else {
                thisStage.setX(250);
                thisStage.setY(100);
            }
        }  else {
            if(singleton.filasPartida <= 4 && singleton.columnasPartida <= 4) {
                thisStage.setX(singleton.posicionX - 180);
                thisStage.setY(singleton.posicionY - 200);
            } else {
                thisStage.setX(250);
                thisStage.setY(100);
            }
        }
    }
    
    @Override
    public void actualizarEstilo() {
    	String tema1 = getClass().getResource("/application/estilo1.css").toExternalForm();
        tablero.getStylesheets().add(tema1);
    }
    
    @Override
    void inicializaMostrarCartas(){
    	if(singleton.mostrarCartasOn) {
    		mostrarCartas();
        }
    }
}