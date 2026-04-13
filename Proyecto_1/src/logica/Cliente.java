package logica;

import java.util.ArrayList;

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


 
	public void hacerPedido(Cafe cafe, Reserva reserva,ArrayList<Platillo> platillos,ArrayList<Juego> juegos) {
		cafe.crearPedido(reserva, this, platillos, juegos);
	}
	public void pagar(Cafe cafe,Reserva reserva,double propina,boolean usarPuntos, String codigoDescuento) {
		cafe.crearFactura(this, propina, usarPuntos, codigoDescuento, reserva);
}
	@Override
	public boolean solicitarPrestamo(Cafe cafe, Juego juego, Reserva reserva) {
	    return cafe.solicitarPrestamo(this, juego, reserva);
	}
	public double consultarPuntosFidelidad(Cafe cafe) {
		return cafe.consultarPuntosFidelidad(this);
	
	
	
	}
}
