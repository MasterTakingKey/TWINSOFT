package application;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.stage.Stage;

	

class ControladorEditorBarajaParejasTest {
	File f1 = new File("/imagenes/baraja_animales/dorso_aldeano.png");
	File f2 = new File("/imagenes/baraja_animales/elefante.png");
	File f3 = new File("/imagenes/baraja_animales/hipopotamo.png");
	File f4 = new File("/imagenes/baraja_animales/jirafa.png");
	File f5 = new File("/imagenes/baraja_animales/leon.png");
	File f6 = new File("/imagenes/baraja_animales/mono.png");
	File f7 = new File("/imagenes/baraja_animales/rinoceronte.png");
	File f8 = new File("/imagenes/baraja_animales/serpiente.png");
	File f9 = new File("/imagenes/baraja_animales/zebra.png");
	
	String nombre = "Test";
	ControladorEditorBarajaParejas editorBaraja = new ControladorEditorBarajaParejas();
	ArrayList<File> listaImagenes = new ArrayList<File>();
	
	void addImages(ArrayList<File> listaImagenes) {
		listaImagenes.add(f1);
		listaImagenes.add(f2);
		listaImagenes.add(f3);
		listaImagenes.add(f4);
		listaImagenes.add(f5);
		listaImagenes.add(f6);
		listaImagenes.add(f7);
		listaImagenes.add(f8);		
		listaImagenes.add(f9);
	}
	
	@Test
    public void test() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel(); 
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {}
                });
            }
        });
        thread.start();
        Thread.sleep(1000); 
                                
        
    }	
	@Test
	void testComprobarNombre() {
		ArrayList<File> listaImagenes = new ArrayList<File>();
		addImages(listaImagenes);				
						
		Baraja barajaCreada = editorBaraja.crearBaraja(nombre, listaImagenes);
		assertTrue(barajaCreada.getNombre().equals(nombre));		
	}
	@Test
	void testComprobarTamanyo() {
		ArrayList<File> listaImagenes = new ArrayList<File>();
		addImages(listaImagenes);				
						
		Baraja barajaCreada = editorBaraja.crearBaraja(nombre, listaImagenes);
		assertTrue(barajaCreada.getTamanyo() == 16);	
	}	

}
