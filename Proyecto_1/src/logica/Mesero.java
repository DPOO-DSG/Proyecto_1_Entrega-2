package logica;

import java.util.ArrayList;

public class Mesero extends Empleado {

	private ArrayList<Juego> juegosConocidos;

	public Mesero(String login, String password, ArrayList<Juego> juegosFavoritos, String codigoDescuento, ArrayList<Turno> turno,
			ArrayList<Juego> juegosConocidos) {
		super(login, password, juegosFavoritos, codigoDescuento, turno);
		this.juegosConocidos = new ArrayList<>();
	}

	public ArrayList<Juego> getJuegosConocidos() {
		return juegosConocidos;
	}

	public void setJuegosConocidos(ArrayList<Juego> juegos) {
		this.juegosConocidos = juegos;
	}
	
	public void anadirJuegosConocidos(Juego juego) {
		
	    if (juego != null && !juegosConocidos.contains(juego)) {
	        juegosConocidos.add(juego);
	    }
	}
}

