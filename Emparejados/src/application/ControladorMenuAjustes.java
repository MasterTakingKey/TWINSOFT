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
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ControladorMenuAjustes {


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

    public void iniciarMenuAjustes(Stage stage, boolean soundOn, boolean primeraVez, double anteriorX, double anteriorY){
        primaryStage = stage;
        SoundOn = soundOn;
        inicializarVariables();
        inicializaChoiceBox();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamañoVentana();
        if(primeraVez) { 
            centrarVentana();
        } else {
            corregirPosicionVentana(anteriorX, anteriorY);
        }
    }
    public void inicializaChoiceBox() {
    	System.out.println("Llega hasta inicializaChoiceBox");
    	tema = new ChoiceBox<String>(FXCollections.observableArrayList(
    		    "Azul",
                "Verde",
                "Rojo",
                "Amarillo"));
		tema.setValue("Azul");
    	/*tema.getItems().addAll(
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
    	menu.setTooltip(new Tooltip("Selecciona la canción que quieres para este menú"));
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
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/ConfirmacionSalirApp.fxml"));
        Parent root = (Parent) myLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirmación de salida");
        stage.setResizable(false);
        ControladorConfirmacionSalirApp controladorConfirmacionSalirApp = myLoader.<ControladorConfirmacionSalirApp>getController();
        controladorConfirmacionSalirApp.inicializarDatos(thisStage.getX(), thisStage.getY(), thisStage.getWidth(), thisStage.getHeight());
        stage.show();
    }
    
    public void corregirTamañoVentana() {
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
    
}
