package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ControladorSeleccionNiveles {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private StackPane circuloSonido;

    @FXML
    private ImageView iconoSonido;

    @FXML
    private Button volver;

    @FXML
    private Button nivel1;

    @FXML
    private Button nivel2;

    @FXML
    private Button nivel3;

    @FXML
    private Button nivel4;

    @FXML
    private Button nivel5;

    @FXML
    private Button nivel6;

    @FXML
    private Button nivel7;

    @FXML
    private Button nivel8;

    @FXML
    private Button nivel9;

    @FXML
    private Button nivel10;
    
    @FXML
    private ImageView tick1;

    @FXML
    private ImageView tick2;

    @FXML
    private ImageView tick3;

    @FXML
    private ImageView tick4;

    @FXML
    private ImageView tick5;

    @FXML
    private ImageView tick6;

    @FXML
    private ImageView tick7;

    @FXML
    private ImageView tick8;

    @FXML
    private ImageView tick9;

    @FXML
    private ImageView tick10;
    
    private Stage primaryStage;
    
    private Stage thisStage;
    
    private Musica musicaFondo;
    
    private Image Sound0;
    
    private Image Sound1;
    
    private long tiempoMusica;
    
    private ConfiguracionPartida singleton;
    
    public void iniciarSeleccionNiveles(Stage stage, ConfiguracionPartida nuevoSingleton, String ventanaAnterior){
        primaryStage = stage;
        singleton = nuevoSingleton;
        inicializarVariables();
		actualizarSonido();
        actualizarImagenSonido();
        corregirTamanyoVentana();
        corregirPosicionVentana(ventanaAnterior);
        actualizarEstilo();
        actualizarNiveles();
    }
    
    public void inicializarVariables() {
    	Sound0 = new Image("/imagenes/sonido_off.png");
        Sound1 = new Image("/imagenes/sonido_on.png");
        musicaFondo = new Musica("src/sonidos/"+ singleton.listaMusica[2] +".wav", 0L);
    	thisStage = (Stage) nivel1.getScene().getWindow();
    }

    public void actualizarNiveles() {
    	nivel1.setDisable(true);
    	nivel2.setDisable(true);
    	nivel3.setDisable(true);
    	nivel4.setDisable(true);
    	nivel5.setDisable(true);
    	nivel6.setDisable(true);
    	nivel7.setDisable(true);
    	nivel8.setDisable(true);
    	nivel9.setDisable(true);
    	nivel10.setDisable(true);
    	tick1.setVisible(false);
    	tick2.setVisible(false);
    	tick3.setVisible(false);
    	tick4.setVisible(false);
    	tick5.setVisible(false);
    	tick6.setVisible(false);
    	tick7.setVisible(false);
    	tick8.setVisible(false);
    	tick9.setVisible(false);
    	tick10.setVisible(false);
    	if(singleton.nivelesDesbloqueados > 0) {
    		nivel1.setDisable(false);
    		if(singleton.nivelesDesbloqueados > 1) {
        		nivel2.setDisable(false);
        		tick1.setVisible(true);
        		if(singleton.nivelesDesbloqueados > 2) {
            		nivel3.setDisable(false);
            		tick2.setVisible(true);
            		if(singleton.nivelesDesbloqueados > 3) {
                		nivel4.setDisable(false);
                		tick3.setVisible(true);
                		if(singleton.nivelesDesbloqueados > 4) {
                    		nivel5.setDisable(false);
                    		tick4.setVisible(true);
                    		if(singleton.nivelesDesbloqueados > 5) {
                        		nivel6.setDisable(false);
                        		tick5.setVisible(true);
                        		if(singleton.nivelesDesbloqueados > 6) {
                            		nivel7.setDisable(false);
                            		tick6.setVisible(true);
                            		if(singleton.nivelesDesbloqueados > 7) {
                                		nivel8.setDisable(false);
                                		tick7.setVisible(true);
                                		if(singleton.nivelesDesbloqueados > 8) {
                                    		nivel9.setDisable(false);
                                    		tick8.setVisible(true);
                                    		if(singleton.nivelesDesbloqueados > 9) {
                                        		nivel10.setDisable(false);
                                        		tick9.setVisible(true);
                                        		if(singleton.nivelesDesbloqueados > 10) {
                                            		tick10.setVisible(true);
                                            	}
                                        	}
                                    	}
                                	}
                            	}
                        	}
                    	}
                	}
            	}
        	}
    	}
    }

    @FXML
    void nivel1Handler(ActionEvent event) {
    	
    	actualizarSingleton(0, 2, 4, true, 60, true, 2);
    	musicaFondo.stopMusic();
    	
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaEstandar.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaEstandar controladorPartida = myLoader.<ControladorPartidaEstandar>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Nivel 1");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartida.iniciarPartidaEstandar(primaryStage, singleton, "seleccionNiveles", true, 1);
      		PlantillaPartidas partida = controladorPartida;
      		partida.ventana = "seleccionNiveles";
      		partida.InicializarPartida();
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void nivel2Handler(ActionEvent event) {
    	actualizarSingleton(0, 3, 4, true, 50, true, 2);
    	musicaFondo.stopMusic();
    	
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaEstandar.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaEstandar controladorPartida = myLoader.<ControladorPartidaEstandar>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Nivel 2");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartida.iniciarPartidaEstandar(primaryStage, singleton, "seleccionNiveles", true, 2);
      		PlantillaPartidas partida = controladorPartida;
      		partida.ventana = "seleccionNiveles";
      		partida.InicializarPartida();
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void nivel3Handler(ActionEvent event) {
    	actualizarSingleton(0, 4, 4, true, 50, true, 2);
    	musicaFondo.stopMusic();
    	
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaEstandar.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaEstandar controladorPartida = myLoader.<ControladorPartidaEstandar>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Nivel 3");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartida.iniciarPartidaEstandar(primaryStage, singleton, "seleccionNiveles", true, 3);
      		PlantillaPartidas partida = controladorPartida;
      		partida.ventana = "seleccionNiveles";
      		partida.InicializarPartida();
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void nivel4Handler(ActionEvent event) {
    	actualizarSingleton(0, 4, 4, true, 40, true, 2);
    	musicaFondo.stopMusic();
    	
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaEstandar.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaEstandar controladorPartida = myLoader.<ControladorPartidaEstandar>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Nivel 4");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartida.iniciarPartidaEstandar(primaryStage, singleton, "seleccionNiveles", true, 4);
      		PlantillaPartidas partida = controladorPartida;
      		partida.ventana = "seleccionNiveles";
      		partida.InicializarPartida();
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void nivel5Handler(ActionEvent event) {
    	actualizarSingleton(1, 4, 5, true, 50, false, 0);
    	musicaFondo.stopMusic();
    	
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaEstandar.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaEstandar controladorPartida = myLoader.<ControladorPartidaEstandar>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Nivel 5");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartida.iniciarPartidaEstandar(primaryStage, singleton, "seleccionNiveles", true, 5);
      		PlantillaPartidas partida = controladorPartida;
      		partida.ventana = "seleccionNiveles";
      		partida.InicializarPartida();
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void nivel6Handler(ActionEvent event) {
    	actualizarSingleton(1, 4, 6, true, 50, false, 0);
    	musicaFondo.stopMusic();
    	
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaEstandar.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaEstandar controladorPartida = myLoader.<ControladorPartidaEstandar>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Nivel 6");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartida.iniciarPartidaEstandar(primaryStage, singleton, "seleccionNiveles", true, 6);
      		PlantillaPartidas partida = controladorPartida;
      		partida.ventana = "seleccionNiveles";
      		partida.InicializarPartida();
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void nivel7Handler(ActionEvent event) {
    	actualizarSingleton(1, 4, 5, true, 90, false, 0);
    	musicaFondo.stopMusic();
    	
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaCarta.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaCarta controladorPartida = myLoader.<ControladorPartidaCarta>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Nivel 7");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartida.iniciarPartidaCarta(primaryStage, singleton, "seleccionNiveles", true, 7);
      		PlantillaPartidas partida = controladorPartida;
      		partida.ventana = "seleccionNiveles";
      		partida.InicializarPartida();
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void nivel8Handler(ActionEvent event) {
    	actualizarSingleton(1, 4, 6, true, 90, false, 0);
    	musicaFondo.stopMusic();
    	
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaCarta.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaCarta controladorPartida = myLoader.<ControladorPartidaCarta>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Nivel 8");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartida.iniciarPartidaCarta(primaryStage, singleton, "seleccionNiveles", true, 8);
      		PlantillaPartidas partida = controladorPartida;
      		partida.ventana = "seleccionNiveles";
      		partida.InicializarPartida();
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void nivel9Handler(ActionEvent event) {
    	actualizarSingleton(2, 5, 6, true, 90, false, 0);
    	musicaFondo.stopMusic();
    	
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaCarta.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaCarta controladorPartida = myLoader.<ControladorPartidaCarta>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Nivel 9");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartida.iniciarPartidaCarta(primaryStage, singleton, "seleccionNiveles", true, 9);
      		PlantillaPartidas partida = controladorPartida;
      		partida.ventana = "seleccionNiveles";
      		partida.InicializarPartida();
      		primaryStage.show();
      	} catch (IOException e) {}
    }

    @FXML
    void nivel10Handler(ActionEvent event) {
    	actualizarSingleton(2, 6, 6, true, 90, false, 0);
    	musicaFondo.stopMusic();
    	
      	try {
      		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/PartidaCarta.fxml"));
      		Parent root = (Parent) myLoader.load();
      		ControladorPartidaCarta controladorPartida = myLoader.<ControladorPartidaCarta>getController();
      		Scene scene = new Scene(root);
      		primaryStage.setScene(scene);
      		primaryStage.setTitle("Nivel 10");
      		primaryStage.setResizable(false);
      		singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
      		controladorPartida.iniciarPartidaCarta(primaryStage, singleton, "seleccionNiveles", true, 10);
      		PlantillaPartidas partida = controladorPartida;
      		partida.ventana = "seleccionNiveles";
      		partida.InicializarPartida();
      		primaryStage.show();
      	} catch (IOException e) {}
    }
    
    public void actualizarSingleton(int baraja, int filas, int columnas, boolean tiempoOn, int tiempo, boolean mostrarOn, int mostrar) {
    	singleton.barajaPartida = singleton.listaBarajas.get(baraja);
    	
    	singleton.filasPartida = filas;
    	singleton.columnasPartida = columnas;
    	
    	singleton.limiteTiempoOn = tiempoOn;
    	singleton.tiempoPartida = tiempo;
    	
    	singleton.mostrarCartasOn = mostrarOn;
    	singleton.tiempoMostrarCartas = mostrar;
    }
    
    @FXML
    void volverHandler(ActionEvent event) {
    	musicaFondo.stopMusic();
    	try {
    		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
            Parent root = myLoader.load();  
            Scene scene = new Scene(root);                        
            primaryStage.setTitle("Menu Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController(); 
            singleton.posicionX = thisStage.getX();
      		singleton.posicionY = thisStage.getY();
            menuPrincipal.iniciarMenuPrincipal(primaryStage, false, singleton, "seleccionNiveles");
            primaryStage.show();
    	} catch (IOException e) {}
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
    	thisStage.setHeight(700);
    }
    
    public void corregirPosicionVentana(String ventanaAnterior) {
    	if(ventanaAnterior.equals("menuPrincipal")) {
        	thisStage.setX(singleton.posicionX);
        	thisStage.setY(singleton.posicionY + 100);
    	} else if(ventanaAnterior.equals("resultadoPartida")) {
    		thisStage.setX(singleton.posicionX - 50);
        	thisStage.setY(singleton.posicionY + 20);
    	}
    }
    
    public void actualizarEstilo() {
    	String temaAzul = getClass().getResource("estiloAzulNiveles.css").toExternalForm();
        String temaRojo = getClass().getResource("estiloRojoNiveles.css").toExternalForm();
        String temaVerde = getClass().getResource("estiloVerdeNiveles.css").toExternalForm();
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
