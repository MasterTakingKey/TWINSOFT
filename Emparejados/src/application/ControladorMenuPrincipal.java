package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ControladorMenuPrincipal {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private StackPane circuloSonido;

	@FXML
	private ImageView iconoSonido;

	@FXML
	private Button salir;
	
	@FXML
	private Button partidaEstandar;

	@FXML
	private Button partidaCarta;

	@FXML
	private Button partidaNiveles;

	@FXML
	private Button partidaMultijugador;

	@FXML
	private Button editorBarajas;

	@FXML
	private Button ajustes;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Musica musicaFondo;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private long tiempoMusica;
    
    private ConfiguracionPartida singleton;
    
    String currentDirectory = System.getProperty("user.dir");
    
    Image imagen;
    Carta carta;
    
    
    private ArrayList<File> listaImagenes = new ArrayList<File>();

    public void iniciarMenuPrincipal(Stage stage, boolean primeraVez, ConfiguracionPartida nuevoSingleton, String ventanaAnterior){
        primaryStage = stage;
        if(primeraVez) { 
            inicializarSingleton();
            File[] files = new File(currentDirectory + "/src/imagenes/barajasPersonalizadas/").listFiles();
        	showFiles(files);
        } else {
            singleton = nuevoSingleton;
        }
        inicializarVariables();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana(ventanaAnterior);
        actualizarEstilo();
        
    }
    
    public void showFiles(File[] files) {
    	
        for (File file : files) {
            if (file.isDirectory()) {           	
            	for(File archivo : file.listFiles()) {            		
            		listaImagenes.add(archivo);
            	}
            	int i = 1;
            	for(File archivo : file.listFiles()) {
            		
            		String fileName = archivo.getName();
            		
            		int pos = fileName.lastIndexOf(".");
            		if (pos > 0 && pos < (fileName.length() - 1)) { 
            		    fileName = fileName.substring(0, pos);
            		}
            		
            		if(fileName.equals("dorso")){
            			listaImagenes.set(0, archivo);
            		} else {
            			listaImagenes.set(i, archivo); 
            			i++;
            		}
            	}
                montarBarajas(file.getName(), listaImagenes);
                listaImagenes.clear();
            }
        }
    }
    
    public void montarBarajas(String nombre, ArrayList<File> listaImagenes) {
    	Baraja nuevaBaraja = new Baraja();
    	nuevaBaraja.setNombre(nombre);
		nuevaBaraja.setTamanyo(2 * listaImagenes.size() - 2 );
		imagen = new Image(listaImagenes.get(0).toURI().toString());
		nuevaBaraja.setImagenDorso(imagen);
		
		int indice = 0;
        for (int i = 1; i < listaImagenes.size(); i++) { 
                    imagen = new Image(listaImagenes.get(i).toURI().toString());
                    carta = new Carta(nuevaBaraja.getImagenDorso(), imagen, i-1);
                    nuevaBaraja.getBaraja()[indice++] = carta;
         }
		
		
		singleton.listaBarajas.add(nuevaBaraja);
    }
    
    public void iniciarMenuPrincipalDesdeEditor(Stage stage, boolean primeraVez, ConfiguracionPartida nuevoSingleton, String ventanaAnterior, long tiempoCancion){
        primaryStage = stage;
        if(primeraVez) { 
            inicializarSingleton();
        } else {
            singleton = nuevoSingleton;
        }
        tiempoMusica = tiempoCancion;
        inicializarVariables();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana(ventanaAnterior);
        actualizarEstilo();
    }
    
	
	public void inicializarSingleton() {
		
    	singleton = ConfiguracionPartida.Instance();
    	
    	singleton.listaBarajas = new ArrayList<Baraja>();
    	
    	FabricaBarajas[] fabrica;
       	
        fabrica = new FabricaBarajas[3];
        fabrica[0] = new FabricaBarajaAnimales();
        fabrica[1] = new FabricaBarajaNintendo();     	
        fabrica[2] = new FabricaBarajaDeportes();
        
        BarajaTematica barajaAnimales = fabrica[0].barajaMetodoFabrica();
        barajaAnimales.crearBaraja();
        BarajaTematica barajaNintendo = fabrica[1].barajaMetodoFabrica();
        barajaNintendo.crearBaraja();
        BarajaTematica barajaDeportes = fabrica[2].barajaMetodoFabrica();
        barajaDeportes.crearBaraja();
        
    	singleton.listaBarajas.add(barajaAnimales);
    	singleton.listaBarajas.add(barajaNintendo);
    	singleton.listaBarajas.add(barajaDeportes);
    	
    	String[] musicas = new String[3];
        musicas[0] = "Musica1";
        musicas[1] = "Musica2";
        musicas[2] = "Musica3";
        singleton.listaMusica = musicas;
        
        singleton.soundOn = true;
    	
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        singleton.posicionX = (screen.getWidth() - 1050) / 2;
        singleton.posicionY = (screen.getHeight() - 900) / 2;
        	
    	singleton.barajaPartida = singleton.listaBarajas.get(0);
        
        singleton.filasPartida = 4;
    	singleton.columnasPartida = 4;
    	
    	singleton.limiteTiempoOn = true;
    	singleton.tiempoPartida = 60;
    	
    	singleton.mostrarCartasOn = true;
    	singleton.tiempoMostrarCartas = 2;
    	
    	singleton.efectosSonorosVoltear = "Voltear";
    	singleton.efectosSonorosPareja = "Acierto";
    	singleton.efectosVisualesVoltear = "Giro";
    	singleton.efectosVisualesPareja = "Salto";
    	
    	 try {
             this.singleton.nivelesDesbloqueados = (int) GuardarDatosPartida.load("niveles.save");
         }
         catch (Exception e) {
             singleton.nivelesDesbloqueados = 1;
         }
    	 
    	 try {
             this.singleton.estilo = (String) GuardarDatosPartida.load("estilo.save");
         }
         catch (Exception e) {
             singleton.estilo = "Azul";
         }

	}
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/"+ singleton.listaMusica[1] +".wav", 0L);
        thisStage = (Stage) anchorPane.getScene().getWindow();
    }

    @FXML
    void partidaEstandarHandler(ActionEvent event) {
      	musicaFondo.stopMusic();
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaEstandar.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaEstandar controladorPartida = myLoader.<ControladorPartidaEstandar>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Partida Estandar");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartida.iniciarPartidaEstandar(primaryStage, singleton, "menuPrincipal", false, 0);
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void partidaCartaHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaCarta.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaCarta controladorPartidaCarta = myLoader.<ControladorPartidaCarta>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Partida Por Carta");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartidaCarta.iniciarPartidaCarta(primaryStage, singleton, "menuPrincipal", false, 0);
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void partidaNivelesHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/SeleccionNiveles.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorSeleccionNiveles controladorSeleccionNiveles = myLoader.<ControladorSeleccionNiveles>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Seleccion de Niveles");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorSeleccionNiveles.iniciarSeleccionNiveles(primaryStage, singleton, "menuPrincipal");
      		primaryStage.show();
      	} catch (IOException e) {}
    }
    
    @FXML
    void partidaMultijugadorHandler(ActionEvent event) {
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/ElegirNombresMultijugador.fxml"));
            Parent root = myLoader.load();  
            Scene scene = new Scene(root);                   
            primaryStage.setTitle("Seleccione los nombres");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            ControladorElegirNombresMultijugador nombresMulti = myLoader.<ControladorElegirNombresMultijugador>getController(); 
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
            nombresMulti.iniciarElegirNombresMultijugador(primaryStage, singleton, musicaFondo, tiempoMusica);
            primaryStage.show();
    	} catch (IOException e) {e.printStackTrace();}
    }  
    
    @FXML
    void editorBarajasHandler(ActionEvent event) {
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/EditorBarajaOpciones.fxml"));
            Parent root = myLoader.load();  
            Scene scene = new Scene(root);                        
            primaryStage.setTitle("Editor de barajas");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            ControladorEditorBarajaOpciones editorOpciones = myLoader.<ControladorEditorBarajaOpciones>getController(); 
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
            editorOpciones.iniciarEditorOpciones(primaryStage, singleton, true, listaImagenes, musicaFondo, tiempoMusica);
            primaryStage.show();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    } 
    
    
    @FXML
    void ajustesHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
    	tiempoMusica = musicaFondo.getClip().getMicrosecondPosition();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuAjustes.fxml"));
    		Parent root = myLoader.load();  
    		ControladorMenuAjustes menuAjustes = myLoader.<ControladorMenuAjustes>getController();
    		Scene scene = new Scene(root);
    		primaryStage.setTitle("Menu Ajustes");
    		primaryStage.setScene(scene);
    		primaryStage.setResizable(false);
        
    		Image icono = new Image("/imagenes/Icon.png");
    		primaryStage.getIcons().add(icono);
        
    		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
    		menuAjustes.iniciarMenuAjustes(primaryStage, singleton);
    		primaryStage.show();
    	} catch(IOException e) {}
    }

    
    @FXML
    void salirHandler(ActionEvent event) throws IOException {
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/ConfirmacionSalirApp.fxml"));
        Parent root = (Parent) myLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirmacion de salida");
        stage.setResizable(false);
        singleton.posicionX = thisStage.getX();
  		singleton.posicionY = thisStage.getY();
        ControladorConfirmacionSalirApp controladorConfirmacionSalirApp = myLoader.<ControladorConfirmacionSalirApp>getController();
        controladorConfirmacionSalirApp.inicializarDatos(thisStage.getWidth(), thisStage.getHeight(), singleton);
        stage.show();
    }
    
    @FXML
    void sonidoHandler(MouseEvent event) {
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
    	}
    	else {
    		musicaFondo.stopMusic();
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
    	thisStage.setWidth(1050);
    	thisStage.setHeight(820);
    }
    
    public void corregirPosicionVentana(String ventanaAnterior) {
    	if(ventanaAnterior.equals("ajustes")) {
        	thisStage.setX(singleton.posicionX);
        	thisStage.setY(singleton.posicionY + 20);
    	} else if(ventanaAnterior.equals("menuPausa")) {
        	thisStage.setX(singleton.posicionX - 80);
        	thisStage.setY(singleton.posicionY - 80);
    	} else if(ventanaAnterior.equals("resultadoPartida")) {
        	thisStage.setX(singleton.posicionX);
        	thisStage.setY(singleton.posicionY);
    	} else if(ventanaAnterior.equals("seleccionNiveles")) {
        	thisStage.setX(singleton.posicionX);
        	thisStage.setY(singleton.posicionY - 100);
    	}else if(ventanaAnterior.equals("editorBarajaOpciones")) {
        	thisStage.setX(singleton.posicionX - 200);
        	thisStage.setY(singleton.posicionY - 120);
    	}else if(ventanaAnterior.equals("editorBarajaBorrar")) {
        	thisStage.setX(singleton.posicionX - 175);
        	thisStage.setY(singleton.posicionY - 145);
    	}else if(ventanaAnterior.equals("editorBarajaDorso")) {
        	thisStage.setX(singleton.posicionX - 175);
        	thisStage.setY(singleton.posicionY + 5);
    	} else {
        	thisStage.setX(singleton.posicionX);
        	thisStage.setY(singleton.posicionY);
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
    		circuloSonido.getStylesheets().remove(temaRojo);
    		circuloSonido.getStylesheets().remove(temaVerde);
    		circuloSonido.getStylesheets().add(temaAzul);
    	} else if(singleton.estilo.equals("Rojo")) {
    		anchorPane.getStylesheets().remove(temaAzul);
			anchorPane.getStylesheets().remove(temaVerde);
			anchorPane.getStylesheets().add(temaRojo);
			circuloSonido.getStylesheets().remove(temaAzul);
			circuloSonido.getStylesheets().remove(temaVerde);
			circuloSonido.getStylesheets().add(temaRojo);
    	} else {
    		anchorPane.getStylesheets().remove(temaAzul);
			anchorPane.getStylesheets().remove(temaRojo);
			anchorPane.getStylesheets().add(temaVerde);
			circuloSonido.getStylesheets().remove(temaAzul);
			circuloSonido.getStylesheets().remove(temaRojo);
			circuloSonido.getStylesheets().add(temaVerde);
    	}
    }
    
}
