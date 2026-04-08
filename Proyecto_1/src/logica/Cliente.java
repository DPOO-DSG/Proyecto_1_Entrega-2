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


 
	public void realizarCompra(Cafe cafe, ArrayList<Platillo> platillos, ArrayList<Juego> juegos,double propina, boolean usarPuntos, String codigoDescuento, Mesa mesa) 
	{

		cafe.crearFactura(this, platillos, juegos, propina, usarPuntos, codigoDescuento, mesa);
}

	
	
	
	
}
