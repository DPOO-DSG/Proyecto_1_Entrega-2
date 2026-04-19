package logica;

import java.io.Serializable;
import java.util.HashMap;

public class InventarioVenta implements Serializable {
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
		return false;
	}

	public void registrarVenta(Juego j) {
		
	}

}
