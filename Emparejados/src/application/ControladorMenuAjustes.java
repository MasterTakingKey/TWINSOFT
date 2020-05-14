package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorMenuAjustes {

	@FXML
    private AnchorPane anchorPane;

    @FXML
    private Button restablecerPredeterminados;

    @FXML
    private StackPane circuloSonido;

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
    private Button aplicar;

    @FXML
    private ChoiceBox<String> barajaPartida;

    @FXML
    private Button cancelarySalir;

    @FXML
    private TextField textFilas;

    @FXML
    private TextField textColumnas;

    @FXML
    private ChoiceBox<String> choiceSonoroCarta;

    @FXML
    private ChoiceBox<String> choiceSonoroPareja;

    @FXML
    private ChoiceBox<String> choiceVisualCarta;

    @FXML
    private ChoiceBox<String> choiceVisualPareja;

    @FXML
    private Button buttonTiempo;

    @FXML
    private TextField textTiempoPartida;

    @FXML
    private Button buttonMostrarCartas;

    @FXML
    private TextField textTiempoMostrarCartas;

    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Musica musicaFondo;
    
    private Image Sound0;
    
    private Image Sound1;
   
    private long tiempoMusica;
    
    private Singleton singleton;

    private String musica1;
    
	private String musica2;
	
	private String musica3;
	
	private String musica4;
	
	private String musica5;
	
	private String musica6;
	
	private String musica7;
	
	private String musica8;


    public void iniciarMenuAjustes(Stage stage, Singleton nuevoSingleton){
        primaryStage = stage;
        singleton = nuevoSingleton;
        inicializarVariables();
        inicializarChoiceBoxMusica();
        inicializarTemas();
        inicializarAjustesPartida();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana();
        actualizarEstilo();
    }

    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/"+ singleton.listaMusica[2] +".wav", 0L);
        musica1 = "Main Theme";
        musica2 = "Tema Tranquilo";
        musica3 = "Tema Hipster";
        musica4 = "Super Mario Bros Theme";
        musica5 = "Summer Remix";
    	musica6 = "Dubstep song ";
    	musica7 = "Wii Main Theme";
    	musica8 = "Wii Sports Theme";
        thisStage = (Stage) aplicar.getScene().getWindow();
    }
    
    public void inicializarChoiceBoxMusica() {
    	cargaMusica(musicaPartida, 0);
    	cargaMusica(musicaMenuP, 1);
    	cargaMusica(musicaPausa, 2); 
    }
    
        
    public void cargaMusica(ChoiceBox<String> menu, int musica) { 
    	
    	menu.setItems(FXCollections.observableArrayList(
    			musica1, musica2, musica3, musica4, musica5, musica6, musica7, musica8)
    		);
    	menu.setTooltip(new Tooltip("Selecciona la cancion que quieres para este menu"));
    	int select;
    	switch (singleton.listaMusica[musica]) {
    		case "Musica1": select = 0; break;
    		case "Musica2": select = 1; break;
    		case "Musica3": select = 2; break;
    		case "Musica4": select = 3; break;
    		case "Musica5": select = 4; break;
    		case "Musica6": select = 5; break;
    		case "Musica7": select = 6; break;
    		default: select = 7;
    		
    	}
    	menu.getSelectionModel().select(select);
    }
    
    public void inicializarTemas() {
    	tema.getItems().add("Azul");
    	tema.getItems().add("Rojo");
    	tema.getItems().add("Verde");
    	if(singleton.estilo.equals("Azul")) {
        	tema.getSelectionModel().select(0);
    	} else if(singleton.estilo.equals("Rojo")) {
        	tema.getSelectionModel().select(1);
    	} else {
        	tema.getSelectionModel().select(2);
    	}
    }

    
    public void inicializarAjustesPartida() {
    	
    	try {
        	int i = 0;
    		while(singleton.listaBarajas.get(i) != null) {
    			barajaPartida.getItems().add(singleton.listaBarajas.get(i).getNombre());
    			if(singleton.listaBarajas.get(i).getNombre().equals(singleton.barajaPartida.getNombre())) barajaPartida.getSelectionModel().select(i);
    			i++;
    		}	
    	} catch(Exception e) {}
    	
        ArrayList<String> clipsVoltear = new ArrayList<String>();
        clipsVoltear.add("Voltear");
        clipsVoltear.add("Voltear2");      
        ObservableList<String> audioVoltear = FXCollections.observableArrayList(clipsVoltear); 
        choiceSonoroCarta.setItems(audioVoltear);
        choiceSonoroCarta.setValue("Voltear");
        if(singleton.efectosSonorosVoltear.equals("Voltear")) {
            choiceSonoroCarta.getSelectionModel().select(0);
        } else {
        	choiceSonoroCarta.getSelectionModel().select(1);
        }
        
        ArrayList<String> clipsPareja = new ArrayList<String>();
        clipsPareja.add("Acierto");
        clipsPareja.add("Acierto2");
        ObservableList<String> audioPareja = FXCollections.observableArrayList(clipsPareja); 
        choiceSonoroPareja.setItems(audioPareja);
        choiceSonoroPareja.setValue("Acierto");
        if(singleton.efectosSonorosPareja.equals("Acierto")) {
            choiceSonoroPareja.getSelectionModel().select(0);
        } else {
        	choiceSonoroPareja.getSelectionModel().select(1);
        }
        
        ArrayList<String> animacionVoltear = new ArrayList<String>();
        animacionVoltear.add("Giro");
        ObservableList<String> animacionCarta = FXCollections.observableArrayList(animacionVoltear); 
        choiceVisualCarta.setItems(animacionCarta);
        choiceVisualCarta.setValue("Giro");
        if(singleton.efectosVisualesVoltear.equals("Giro")) {
            choiceVisualCarta.getSelectionModel().select(0);
        }
        
        ArrayList<String> animacionPareja = new ArrayList<String>();
        animacionPareja.add("Salto");
        animacionPareja.add("Salto doble");
        ObservableList<String> animacionCorrecta = FXCollections.observableArrayList(animacionPareja); 
        choiceVisualPareja.setItems(animacionCorrecta);
        choiceVisualPareja.setValue("Salto");
        if(singleton.efectosVisualesPareja.equals("Salto")) {
        	choiceVisualPareja.getSelectionModel().select(0);
        } else {
        	choiceVisualPareja.getSelectionModel().select(1);
        }
        
        if(singleton.limiteTiempoOn) {
        	buttonTiempo.setText("Activado");
        	textTiempoPartida.setText(Integer.toString(singleton.tiempoPartida));
        } else {
        	buttonTiempo.setText("Desactivado");
        	textTiempoPartida.setText("");
        }
        
        if(singleton.mostrarCartasOn) {
        	buttonMostrarCartas.setText("Activado");
        	textTiempoMostrarCartas.setText(Integer.toString(singleton.tiempoMostrarCartas));
        } else {
        	buttonMostrarCartas.setText("Desactivado");
        	textTiempoMostrarCartas.setText("");
        }
        
        textFilas.setText(Integer.toString(singleton.filasPartida));
        textColumnas.setText(Integer.toString(singleton.columnasPartida));
        
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
    	thisStage.setWidth(1050);
    	thisStage.setHeight(900);
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
    
    @FXML
    void editorBarajasHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/EditorBarajaDorso.fxml"));
            Parent root = myLoader.load();  
            Scene scene = new Scene(root); 
            Stage stage = new Stage();                       
            stage.setTitle("Seleccione el dorso");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            EditorBarajaDorsoController editorDorso = myLoader.<EditorBarajaDorsoController>getController(); 
            actualizaMusicas();
            singleton.estilo = tema.getSelectionModel().getSelectedItem();
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
            editorDorso.iniciarEditorDorso(primaryStage, singleton, true);
            stage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }
    }
    @FXML
    void cancelarYSalirHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
            Parent root = myLoader.load();  
            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Menu Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
            menuPrincipal.iniciarMenuPrincipal(primaryStage, false, singleton);
            primaryStage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }
    }

    @FXML
    void aplicarHandler(ActionEvent event) throws IOException {
    	musicaFondo.stopMusic();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
            Parent root = myLoader.load();  
            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Menu Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            actualizaMusicas();
            singleton.estilo = tema.getSelectionModel().getSelectedItem();
            singleton.barajaPartida = deNombreABaraja();
            singleton.filasPartida = Integer.parseInt(textFilas.getText());
            singleton.columnasPartida = Integer.parseInt(textColumnas.getText());
            singleton.efectosSonorosVoltear = choiceSonoroCarta.getValue();
            singleton.efectosSonorosPareja = choiceSonoroPareja.getValue();
            singleton.efectosVisualesVoltear = choiceVisualCarta.getValue();
            singleton.efectosVisualesPareja = choiceVisualPareja.getValue();
            if(buttonTiempo.getText().equals("Activado")) {
            	singleton.limiteTiempoOn = true;
            	singleton.tiempoPartida = Integer.parseInt(textTiempoPartida.getText());
            } else {
            	singleton.limiteTiempoOn = false;
            }
            if(buttonMostrarCartas.getText().equals("Activado")) {
            	singleton.mostrarCartasOn = true;
            	singleton.tiempoMostrarCartas = Integer.parseInt(textTiempoMostrarCartas.getText());
            } else {
            	singleton.mostrarCartasOn = false;
            }
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
            menuPrincipal.iniciarMenuPrincipal(primaryStage, false, singleton);
            primaryStage.show();
    	} catch (IOException e) {
                e.printStackTrace();
        }
    }
    

    @FXML
    void buttonTiempoHandler(ActionEvent event) {
    	if(buttonTiempo.getText().equals("Activado")) {
    		buttonTiempo.setText("Desactivado");
    		textTiempoPartida.setText("");
    	} else {
    		buttonTiempo.setText("Activado");
    		textTiempoPartida.setText(Integer.toString(singleton.tiempoPartida));
    	}
    }

    
    @FXML
    void buttonMostrarCartasHandler(ActionEvent event) {
    	if(buttonMostrarCartas.getText().equals("Activado")) {
    		buttonMostrarCartas.setText("Desactivado");
    		textTiempoMostrarCartas.setText("");
    	} else {
    		buttonMostrarCartas.setText("Activado");
    		textTiempoMostrarCartas.setText(Integer.toString(singleton.tiempoMostrarCartas));
    	}
    }

    @FXML
    void restablecerPredeterminadosHandler(ActionEvent event) {
    	
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
    	
    	inicializarAjustesPartida();
    }

    
    public void actualizaMusicas() {
    	singleton.listaMusica[0] = traduceMusica(musicaPartida.getSelectionModel().getSelectedItem());
    	singleton.listaMusica[1] = traduceMusica(musicaMenuP.getSelectionModel().getSelectedItem());
    	singleton.listaMusica[2] = traduceMusica(musicaPausa.getSelectionModel().getSelectedItem());
    }
    
    public String traduceMusica(String m) {
    	if (musica1 == m)
    		return "Musica1";
    	else if (musica2 == m)
    		return "Musica2";
    	else if (musica3 == m)
    		return "Musica3";
    	else if (musica4 == m)
    		return "Musica4";
    	else if (musica5 == m)
    		return "Musica5";
    	else if (musica6 == m)
    		return "Musica6";
    	else if (musica7 == m)
    		return "Musica7";
    	else
    		return "Musica8";
	}
     
    public Baraja deNombreABaraja() {
    	int i = 0;
    	while(singleton.listaBarajas.get(i) != null) {
    		if(barajaPartida.getSelectionModel().getSelectedItem().equals(singleton.listaBarajas.get(i).getNombre())) {
    			return singleton.listaBarajas.get(i);
    		}
    		i++;
    	}
    	return null;
    }
    
}
