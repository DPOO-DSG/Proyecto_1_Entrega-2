package logica;

import java.util.ArrayList;

public class Mesero extends Empleado {

	private ArrayList<Juego> juegosConocidos;

	public Mesero(String login, String password, ArrayList<Juego> juegosFavoritos, String codigoDescuento, Turno turno,
			ArrayList<Juego> juegosConocidos) {
		super(login, password, juegosFavoritos, codigoDescuento, turno);
		this.juegosConocidos = juegosConocidos;
	}
	
	
}
