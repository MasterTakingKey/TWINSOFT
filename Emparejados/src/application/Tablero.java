package application;

public class Tablero {

	Carta[][] tablero;
	int tamanyo;
	
	public Tablero(int t) {
		tamanyo = t;
		tablero = new Carta[t][t];
		//inicializarTablero(tamanyo);
	}
	
	public int getTamanyo() {
		return tamanyo;
	}
	public int getNumParejas() {
		return tamanyo * tamanyo / 2;
	}
	
	public Carta getCarta(int x, int y) {
		return tablero[x][y];
	}
	
	public void setTamanyo(int nuevoTamanyo) {
		tamanyo = nuevoTamanyo;
		inicializarTablero(tamanyo);
	}
	
	public void setCarta(Carta nuevaCarta, int x, int y) {
		tablero[x][y] = nuevaCarta;
	}
	
	public void inicializarTablero(int tamanyo) {
		for(int i = 0; i < tamanyo; i++) {
			for(int j = 0; j < tamanyo; j++) {
				tablero[i][j] = null;
			}
		}
	}
	
    public void llenarTablero(Baraja baraja) {
    	int index = 0;
    	for(int x = 0; x < tamanyo; x++) {
    		for(int y = 0; y < tamanyo; y++)	{
        			tablero[x][y] = baraja.getCarta(index++);
    		}
    	}
    }
}
