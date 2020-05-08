package application;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ControladorMenuAjustes {

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private StackPane circuloSonido;
    
	@FXML
	private Button editorBarajas;

	@FXML
	private ImageView iconoSonido;

	@FXML
	private ChoiceBox<String> musicaPartida;

	@FXML
	private ChoiceBox<String> musicaMenuP;

	@FXML
	private ChoiceBox<String> musicaPausa;

	@FXML
	private ChoiceBox<String> tema;

	@FXML
	private Button salir;

    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Musica musicaFondo;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private boolean SoundOn;
    
    private long tiempoMusica;
    
    private String estilo;

    public void iniciarMenuAjustes(Stage stage, boolean soundOn, boolean primeraVez, double anteriorX, double anteriorY, String estilo){
        primaryStage = stage;
        SoundOn = soundOn;
        inicializarVariables();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana(anteriorX, anteriorY);
        actualizarEstilo(estilo);
        inicializarChoiceBox();
    }
    public void inicializarChoiceBox() {
    	System.out.println("Llega hasta inicializaChoiceBox");
    	tema.getItems().add("Azul");
    	tema.getItems().add("Rojo");
    	tema.getItems().add("Verde");
    	if(estilo.equals("Azul")) {
        	tema.getSelectionModel().select(0);
    	} else if(estilo.equals("Rojo")) {
        	tema.getSelectionModel().select(1);
    	} else {
        	tema.getSelectionModel().select(2);
    	}
    	/**tema = new ChoiceBox<String>(FXCollections.observableArrayList(
    		    "Azul",
                "Verde",
                "Rojo"));
		tema.setValue("Azul");
    	tema.getItems().addAll(
                "Azul",
                "Verde",
                "Rojo",
                "Amarillo"
            );  */
    	musicaMenuP= new ChoiceBox<String>();
    	cargaMusica(musicaMenuP);
    	musicaPartida= new ChoiceBox<String>();
    	cargaMusica(musicaPartida);
    	musicaPausa= new ChoiceBox<String>();
    	cargaMusica(musicaPausa); 
    }
    
    public void cargaMusica(ChoiceBox<String> menu) {  
    	System.out.println("Llega hasta cargaMusica");
    	menu.setItems(FXCollections.observableArrayList(
    		    "Musica1", "Musica2", "Musica3", "Musica4")
    		);
    	menu.setTooltip(new Tooltip("Selecciona la cancion que quieres para este menu"));
    }
    
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/Musica2.wav", 0L);
        thisStage = (Stage) salir.getScene().getWindow();
    }
    
    public void actualizarSonido() {
    	if(SoundOn) {
    		musicaFondo.getClip().setMicrosecondPosition(tiempoMusica);
    		musicaFondo.playMusic();
    	}
    	else {
    		musicaFondo.stopMusic();
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
    
    @FXML
    void editorBarajasHandler(ActionEvent event) {

    }
   
    @FXML
    void salirHandler(ActionEvent event) throws IOException {
    	musicaFondo.stopMusic();
    	System.out.println(tema.getSelectionModel().getSelectedItem());
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
            Parent root = myLoader.load();  
            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Menu Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            menuPrincipal.iniciarMenuPrincipal(primaryStage, SoundOn, false, thisStage.getX(), thisStage.getY(), tema.getSelectionModel().getSelectedItem());
            primaryStage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public void corregirTamanyoVentana() {
    	thisStage.setWidth(900);
    	thisStage.setHeight(650);
    }
    
    public void corregirPosicionVentana(double anteriorX, double anteriorY) {
    	thisStage.setX(anteriorX);
    	thisStage.setY(anteriorY);
    }
    
    public void centrarVentana() {  
       Rectangle2D screen = Screen.getPrimary().getVisualBounds();
       primaryStage.setX((screen.getWidth() - primaryStage.getWidth()) / 2);
       primaryStage.setY((screen.getHeight() - primaryStage.getHeight()) / 2);
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
    		circuloSonido.getStylesheets().remove(temaRojo);
    		circuloSonido.getStylesheets().remove(temaVerde);
    		circuloSonido.getStylesheets().add(temaAzul);
    	} else if(estilo.equals("Rojo")) {
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
