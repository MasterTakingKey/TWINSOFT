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
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class ControladorResultadoPartida {

    @FXML
    private ImageView resultado;

    @FXML
    private Label puntuacionFinal;

    @FXML
    private Label tiempoRestante;

    @FXML
    private Button jugar;

    @FXML
    private Button salir;
    
    private AudioClip victoria;
    
    private AudioClip derrota;
    
    private Stage primaryStage;
    
    private boolean SoundOn;
    
    private Image icon;
    
    public void iniciarVictoria(Stage stage, boolean soundOn, String puntuacion, String tiempo){
        primaryStage = stage;
        SoundOn = soundOn;
        inicializarVariables(puntuacion, tiempo);
        if(soundOn)
        victoria.play();
        resultado.setImage(new Image("/imagenes/resultado_victoria.png"));
    }
    
    public void iniciarDerrota(Stage stage, boolean soundOn, String puntuacion, String tiempo){
        primaryStage = stage;
        SoundOn = soundOn;
        inicializarVariables(puntuacion, tiempo);
        if(soundOn)
        derrota.play();
        resultado.setImage(new Image("/imagenes/resultado_derrota.png"));
    }
    
    public void inicializarVariables(String puntuacion, String tiempo) {
        victoria = new AudioClip(getClass().getResource("/sonidos/victoria.mp3").toString());
        derrota = new AudioClip(getClass().getResource("/sonidos/derrota1.mp3").toString());
        puntuacionFinal.setText(puntuacionFinal.getText() + puntuacion);
        String minutos = tiempo.substring(0, tiempo.length() - 3);
        String segundos = tiempo.substring(tiempo.length() - 2);
        tiempoRestante.setText(tiempoRestante.getText() + minutos + " min y " + segundos + "s");
        icon = new Image("/imagenes/Icon.png");
        Stage stage = (Stage) jugar.getScene().getWindow();
        stage.getIcons().add(icon);
    }

    @FXML
    void jugarHandler(ActionEvent event) throws IOException {
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/partida.fxml"));
        Parent root = (Parent) myLoader.load();
        ControladorPartida controladorPartida = myLoader.<ControladorPartida>getController();
        Stage stage = (Stage) salir.getScene().getWindow();
        controladorPartida.iniciarPartida(primaryStage, SoundOn);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Partida Estandar");
        primaryStage.setResizable(false);
        primaryStage.setX(stage.getX());
        primaryStage.setY(stage.getY());
        primaryStage.show();
    	stage.close();
    }

    @FXML
    void salirHandler(ActionEvent event) throws IOException {
    	primaryStage.close();
    	FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/Vista/MenuPrincipal.fxml"));
        Parent root = myLoader.load();  
        ControladorMenuPrincipal menuPrincipal = myLoader.<ControladorMenuPrincipal>getController();
        menuPrincipal.iniciarMenuPrincipal(primaryStage, SoundOn);
        Scene scene = new Scene(root);
        Stage stage = (Stage) salir.getScene().getWindow();
        primaryStage.setTitle("Men� Principal");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setX(stage.getX());
        primaryStage.setY(stage.getY());
        primaryStage.show();
        cerrarVentana();   
    }

    public void cerrarVentana() {
    	Stage stage = (Stage) salir.getScene().getWindow();
    	stage.close();
    }

}
