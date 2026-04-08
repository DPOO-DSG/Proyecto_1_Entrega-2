package logica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class InventarioPrestamo {
	private ArrayList<Juego> juegos;
	private HashMap<Juego,Integer> stock;
	private HashMap<Juego, ArrayList<LocalDateTime>> historial;
	
	//constructor
		public InventarioPrestamo(ArrayList<Juego> juegos, HashMap<Juego, Integer> stock, HashMap<Juego, ArrayList<LocalDateTime>> historial) {
			super();
			this.juegos = juegos;
			this.stock = stock;
			this.historial = historial;
		}

		//getters and setters
		public ArrayList<Juego> getJuegos() {
			return juegos;
		}

		public void setJuegos(ArrayList<Juego> juegos) {
			this.juegos = juegos;
		}

		public HashMap<Juego, Integer> getStock() {
			return stock;
		}

		public void setStock(HashMap<Juego, Integer> stock) {
			this.stock = stock;
		}

		public boolean estaDisponible(Juego juego) {
		    return stock.containsKey(juego) && stock.get(juego) > 0;
		}

		public boolean registrarPrestamo(Juego juego) {
			
		    int cantidad = stock.get(juego);

		    if (cantidad == 1) {
		        stock.remove(juego); //  llave juego desaparece
		    } else {
		        stock.put(juego, cantidad - 1);
		    }
		    
		    if (!stock.containsKey(juego)) { 
		        return false;
		    } 

		    return true;
		    }
		
		public void devolverJuego(Juego juego) {

		    if (stock.containsKey(juego)) {
		        int cantidad = stock.get(juego);
		        stock.put(juego, cantidad + 1);
		    } else {
		        stock.put(juego, 1); //  recrear la llave
		    }
		}

		public HashMap<Juego, ArrayList<LocalDateTime>> getHistorial() {
			return historial;
		}

		public void setHistorial(HashMap<Juego, ArrayList<LocalDateTime>> historial) {
			this.historial = historial;
		}
		
		

}