package application;

public class Tablero {

	Carta[][] tablero;
	int tamanyo;
	
	public Tablero(int tamanyo) {
		this.tamanyo = tamanyo;
		tablero = new Carta[tamanyo][tamanyo];
	}
	
	public int getTamanyo() {
		return tamanyo;
	}
	public int getNumParejas() {
		return tamanyo * tamanyo / 2;
	}
	
	public Carta getCarta(int posicionX, int posicionY) {
		return tablero[posicionX][posicionY];
	}
	
	public void setTamanyo(int nuevoTamanyo) {
		tamanyo = nuevoTamanyo;
	}
	
	public void setCarta(Carta nuevaCarta, int posicionX, int posicionY) {
		tablero[posicionX][posicionY] = nuevaCarta;
	}
	
    public void llenarTablero(Baraja nuevaBaraja) {
    	int index = 0;
    	for(int x = 0; x < tamanyo; x++) {
    		for(int y = 0; y < tamanyo; y++)	{
        			tablero[x][y] = nuevaBaraja.getCarta(index++);
    		}
    	}
    }
}
