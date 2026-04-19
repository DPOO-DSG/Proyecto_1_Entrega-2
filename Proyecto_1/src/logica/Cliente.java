package logica;

import java.io.Serializable;
import java.util.ArrayList;


public class Cliente extends Usuario implements Serializable {
	private double puntosFidelidad;

	public Cliente(String login, String password, ArrayList<Juego> juegosFavoritos, double puntosFidelidad) {
		super(login, password, juegosFavoritos);
		this.puntosFidelidad = puntosFidelidad;
	}
	
	
	public double getPuntosFidelidad() {
		return puntosFidelidad;
	}


	public void setPuntosFidelidad(double puntosFidelidad) {
		this.puntosFidelidad = puntosFidelidad;
	}


	@Override
	public void solicitarPrestamo(Cafe cafe, Juego juego, Reserva reserva) throws Exception{
	    cafe.solicitarPrestamo(this, juego, reserva);
	}
	public double consultarPuntosFidelidad(Cafe cafe) {
		return cafe.consultarPuntosFidelidad(this);
	
	
	}
}
