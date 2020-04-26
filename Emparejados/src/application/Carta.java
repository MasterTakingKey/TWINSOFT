package application;

import javafx.scene.image.Image;

public class Carta {
	
	Image imagenDorso;
	Image imagenFrente;
	int id;
	
	public Carta(Image imagenDorso, Image imagenFrente, int id) {
		this.imagenDorso = imagenDorso;
		this.imagenFrente = imagenFrente;
		this.id = id;
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
	
	public void setImagenDorso(Image nuevaImagenDorso) {
		imagenDorso = nuevaImagenDorso;
	}
	
	public void setImagenFrente(Image nuevaImagenFrente) {
		imagenFrente = nuevaImagenFrente;
	}
	
	public void setId(int nuevaId) {
		id = nuevaId;
	}
}
