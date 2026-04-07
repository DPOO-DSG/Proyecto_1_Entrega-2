package logica;

import java.util.ArrayList;
import java.util.HashMap;

public class InventarioPrestamo {
	private ArrayList<Juego> juegos;
	private HashMap<Juego,Integer> stock;
	//constructor
		public InventarioPrestamo(ArrayList<Juego> juegos, HashMap<Juego, Integer> stock) {
			super();
			this.juegos = juegos;
			this.stock = stock;
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
			// TODO Auto-generated method stub
			return stock.containsKey(juego);
		}

		public boolean registrarPrestamo(Juego juego) {
			// TODO Auto-generated method stub
			if (!stock.containsKey(juego)) { 
		        return false;
		    } 

		    int cantidad = stock.get(juego);

		    if (cantidad == 1) {
		        stock.remove(juego); //  llave juego desaparece
		    } else {
		        stock.put(juego, cantidad - 1);
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
		
		

}