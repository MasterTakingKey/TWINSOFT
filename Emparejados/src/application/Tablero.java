package application;

public class Tablero {

	Carta[][] tablero;
	int tamanyo;
	int filas;
	int columnas;
	
	public Tablero(int filas, int columnas) {
		this.tamanyo = filas*columnas;
		this.filas = filas;
		this.columnas = columnas;
		tablero = new Carta[filas][columnas];
	}
	
	public int getTamanyo() {
		return tamanyo;
	}
	public int getNumParejas() {
		return (filas * columnas) / 2;
	}
	
	public Carta getCarta(int posicionX, int posicionY) {
		return tablero[posicionX][posicionY];
	}
	
	public void setTamanyo(int filas, int columnas) {
		tamanyo = filas*columnas;
	}
	
	public void setCarta(Carta nuevaCarta, int posicionX, int posicionY) {
		tablero[posicionX][posicionY] = nuevaCarta;
	}
	
    public void llenarTablero(Baraja nuevaBaraja) {
    	int index = 0;
    	for(int x = 0; x < filas; x++) {
    		for(int y = 0; y < columnas; y++)	{
        			tablero[x][y] = nuevaBaraja.getCarta(index++);
    		}
    	}
    }
}
