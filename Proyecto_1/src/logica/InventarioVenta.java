package logica;

import java.util.HashMap;

public class InventarioVenta {
	private HashMap<Juego,Integer> stock;
	
	
	
	
	public InventarioVenta() {
		super();
		this.stock = new HashMap<Juego,Integer>();
	}

	
	
	
	public HashMap<Juego, Integer> getStock() {
		return stock;
	}




	public void setStock(HashMap<Juego, Integer> stock) {
		this.stock = stock;
	}




	public boolean estaDisponible(Juego juego) {
		// TODO Auto-generated method stub
		return false;
	}

	public void registrarVenta(Juego j) {
		// TODO Auto-generated method stub
		
	}

}
