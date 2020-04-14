package application;

public class Tablero {

	Carta[][] tablero;
	int tama�o;
	
	public Tablero(int t) {
		tama�o = t;
		inicializarTablero(tama�o);
	}
	
	public int getTama�o() {
		return tama�o;
	}
	
	public Carta getCarta(int x, int y) {
		return tablero[x][y];
	}
	
	public void setTama�o(int nuevoTama�o) {
		tama�o = nuevoTama�o;
		inicializarTablero(tama�o);
	}
	
	public void setCarta(Carta nuevaCarta, int x, int y) {
		tablero[x][y] = nuevaCarta;
	}
	
	public void inicializarTablero(int tama�o) {
		for(int i = 0; i < tama�o; i++) {
			for(int j = 0; j < tama�o; j++) {
				tablero[i][j] = null;
			}
		}
	}
	
    public void llenarTablero(Baraja baraja) {
    	int index = 0;
    	for(int x = 0; x < tama�o; x++) {
    		for(int y = 0; y < tama�o; y++)	{
        			tablero[x][y] = baraja.getCarta(index++);
    		}
    	}
    }
}
