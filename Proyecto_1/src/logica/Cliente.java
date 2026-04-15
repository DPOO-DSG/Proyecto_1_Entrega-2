package logica;

import java.util.ArrayList;

import excepciones.BebidaCalienteConAccionException;
import excepciones.DatosReservaInvalidosException;
import excepciones.JuegoNoDisponibleException;
import excepciones.NoHayMesasDisponiblesException;
import excepciones.ReservaNoEncontradaException;

public class Cliente extends Usuario {
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


 
	
	public void pagar(Cafe cafe,Reserva reserva,double propina,boolean usarPuntos, String codigoDescuento) throws NoHayMesasDisponiblesException, BebidaCalienteConAccionException, JuegoNoDisponibleException, ReservaNoEncontradaException, DatosReservaInvalidosException {
		cafe.crearFactura(propina, usarPuntos, codigoDescuento, reserva);
}
	@Override
	public void solicitarPrestamo(Cafe cafe, Juego juego, Reserva reserva) throws Exception{
	    cafe.solicitarPrestamo(this, juego, reserva);
	}
	public double consultarPuntosFidelidad(Cafe cafe) {
		return cafe.consultarPuntosFidelidad(this);
	
	
	
	}
}
