package logica;

import java.util.ArrayList;

public class Cliente extends Usuario {
	private double puntosFidelidad;

	public Cliente(String login, String password, ArrayList<Juego> juegosFavoritos, double puntosFidelidad) {
		super(login, password, juegosFavoritos);
		this.puntosFidelidad = puntosFidelidad;
	}
	

}
