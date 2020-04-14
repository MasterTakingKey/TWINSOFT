package application;

public class Tablero {

	Carta[][] tablero;
	int tamaño;
	
	public Tablero(int t) {
		tamaño = t;
		inicializarTablero(tamaño);
	}
	
	public int getTamaño() {
		return tamaño;
	}
	
	public Carta getCarta(int x, int y) {
		return tablero[x][y];
	}
	
	public void setTamaño(int nuevoTamaño) {
		tamaño = nuevoTamaño;
		inicializarTablero(tamaño);
	}
	
	public void setCarta(Carta nuevaCarta, int x, int y) {
		tablero[x][y] = nuevaCarta;
	}
	
	public void inicializarTablero(int tamaño) {
		for(int i = 0; i < tamaño; i++) {
			for(int j = 0; j < tamaño; j++) {
				tablero[i][j] = null;
			}
		}
	}
	
    public void llenarTablero(Baraja baraja) {
    	int index = 0;
    	for(int x = 0; x < tamaño; x++) {
    		for(int y = 0; y < tamaño; y++)	{
        			tablero[x][y] = baraja.getCarta(index++);
    		}
    	}
    }
}
