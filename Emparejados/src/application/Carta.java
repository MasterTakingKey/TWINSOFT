package application;

import javafx.scene.image.Image;

public class Carta {
	
	Image imagenDorso;
	Image imagenFrente;
	int id;
	
	

	public Carta(Image imagenD, Image imagenF, int ident) {
		imagenDorso = imagenD;
		imagenFrente = imagenF;
		id = ident;
	}
	
	public Image getImagenDorso() {
		return imagenDorso;
	}
	
	public Image getImagenFrente() {
		return imagenFrente;
	}
	
	public int getId() {
		return id;
	}
	
	public void setImagenDorso(Image imagenD) {
		imagenDorso = imagenD;
	}
	
	public void setImagenFrente(Image imagenF) {
		imagenFrente = imagenF;
	}
	
	public void setId(int ident) {
		id = ident;
	}
}
