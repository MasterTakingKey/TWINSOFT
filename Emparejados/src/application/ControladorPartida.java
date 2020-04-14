package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    private ImageView carta_0_0;

    @FXML
    private ImageView carta_1_0;

    @FXML
    private ImageView carta_2_0;

    @FXML
    private ImageView carta_0_1;

    @FXML
    private ImageView carta_3_0;

    @FXML
    private ImageView carta_1_1;

    @FXML
    private ImageView carta_2_1;

    @FXML
    private ImageView carta_3_1;

    @FXML
    private ImageView carta_0_2;

    @FXML
    private ImageView carta_1_2;

    @FXML
    private ImageView carta_2_2;

    @FXML
    private ImageView carta_3_2;

    @FXML
    private ImageView carta_1_3;

    @FXML
    private ImageView carta_2_3;

    @FXML
    private ImageView carta_3_3;
    
    private Baraja barajaPartida;
    
    private Tablero tableroPartida;
    
    private Image imagenDorso;
    
    @FXML
    public void initialize() {
    	crearBaraja();
    	crearTablero();
    } 
    
    public void crearBaraja() {
    	imagenDorso = new Image("..\\imagenes\\dorso1.jpg");
    	barajaPartida = new Baraja("Animales", imagenDorso, 16);
    	int index = 0;
    	for(int i = 0; i < 2; i++) {
    		barajaPartida.setCarta(new Carta(barajaPartida.getImaneDorso(), new Image("..\\imagenes\\elefante.png"), 0), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImaneDorso(), new Image("..\\imagenes\\hipopotamo.png"), 1), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImaneDorso(), new Image("..\\imagenes\\jirafa.png"), 2), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImaneDorso(), new Image("..\\imagenes\\leon.png"), 3), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImaneDorso(), new Image("..\\imagenes\\mono.png"), 4), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImaneDorso(), new Image("..\\imagenes\\rinoceronte.png"), 5), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImaneDorso(), new Image("..\\imagenes\\serpiente.png"), 6), index++);
    		barajaPartida.setCarta(new Carta(barajaPartida.getImaneDorso(), new Image("..\\imagenes\\zebra.png"), 7), index++);
    	}
    	barajaPartida.barajar();
    }
    
    public void crearTablero() {
    	tableroPartida = new Tablero(4);
    	tableroPartida.llenarTablero(barajaPartida);
    }

    @FXML
    void muestracarta(MouseEvent event) {
    	
    }

    @FXML
    void pausar_partida(ActionEvent event) {

    }

}
