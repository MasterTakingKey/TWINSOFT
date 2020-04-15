package application;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorPartida {

    @FXML
    private Label tiempo;

    @FXML
    private Label puntos;

    @FXML
    private Button pausa;

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
    
    private Baraja barajaPartida;
    
    private Tablero tableroPartida;
    
    private Carta primeraCarta;
    
    private Carta segundaCarta;
    
    private ImageView primeraImagen;
    
    private ImageView segundaImagen;
    
    private int cartasGiradas;
    
    private Image imagenDorso;
    
    private boolean esPrimeraCarta;
    
    private Stage primaryStage;
    
    private Scene primaryScene;
    
    private String primaryTitle;
    
    @FXML
    public void initialize() {
    	crearBaraja();
    	crearTablero();
    	esPrimeraCarta = true;
    	cartasGiradas = 0;
    } 
    
    public void iniciarPartida(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
    }
    
    public void crearBaraja() {
    	imagenDorso = new Image("..\\imagenes\\dorso_aldeano.png");
    	barajaPartida = new Baraja("Animales", imagenDorso, 16);
    	int index = 0;
    	for(int i = 0; i < 2; i++) {
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("..\\imagenes\\elefante.png"), 0), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("..\\imagenes\\hipopotamo.png"), 1), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("..\\imagenes\\jirafa.png"), 2), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("..\\imagenes\\leon.png"), 3), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("..\\imagenes\\mono.png"), 4), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("..\\imagenes\\rinoceronte.png"), 5), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("..\\imagenes\\serpiente.png"), 6), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImagenDorso(), new Image("..\\imagenes\\zebra.png"), 7), index++);
    	}
    	barajaPartida.barajar();
    }
    
    public void crearTablero() {
    	tableroPartida = new Tablero(4);
    	tableroPartida.llenarTablero(barajaPartida);
    }

    @FXML
    void muestraCarta(MouseEvent event) {
    	//Sonido carta girada
    	cartasGiradas++;
    	ImageView imagenSeleccionada = (ImageView) event.getSource();
    	String nombreCarta = imagenSeleccionada.getId();
    	int posicionX = Integer.parseInt(nombreCarta.substring(5, 5));
    	int posicionY = Integer.parseInt(nombreCarta.substring(6, 6));
    	Carta cartaSeleccionada = tableroPartida.getCarta(posicionX, posicionY);
    	imagenSeleccionada.setImage(cartaSeleccionada.imagenFrente);
    	if(esPrimeraCarta) {
    		primeraCarta = cartaSeleccionada;
    		primeraImagen = imagenSeleccionada;
    		esPrimeraCarta = false;
    	} else {
    		segundaCarta = cartaSeleccionada;
    		segundaImagen = imagenSeleccionada;
    		if(primeraCarta.getId() == segundaCarta.getId()) {
    			parejaCorrecta();
    		} else {
    			parejaIncorrecta();
    		}
    		esPrimeraCarta = true;
    	}
    }
    
    public void parejaCorrecta() {
    	//Sonido pareja correcta
    	//Sumar puntos
    	if(cartasGiradas == barajaPartida.getTama�o()) {
    		victoria();
    	}
    }
    
    public void parejaIncorrecta() {
    	//Sonido pareja incorrecta
    	//Restar puntos
    	primeraImagen.setImage(barajaPartida.getImagenDorso());
    	segundaImagen.setImage(barajaPartida.getImagenDorso());
    	cartasGiradas-= 2;
    }
    
    public void victoria() {
    	//Sonido victoria
    	//Mensaje victoria
    }

    @FXML
    void pausarPartida(ActionEvent event) {
    	try {
            Stage newStage = new Stage();
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("MenuPausa.fxml"));
            Parent root = myLoader.load();
            
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            newStage.setScene(scene);
            newStage.setTitle("Menu de Pausa");
            newStage.setResizable(false);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();  
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
