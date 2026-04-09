package logica;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CompraVenta {
	private int numeroFactura;
	private LocalDateTime fecha;

	private double iva;
	private double subtotal;
	private double propina;
	private double total;
	private int puntosGenerados;
	

	private Reserva reserva;
	private Usuario usuario;
	
	public CompraVenta(int numeroFactura, Usuario usuario, double propina, Reserva reserva) {
	    this.numeroFactura = numeroFactura;
	    this.usuario = usuario;
	    this.reserva = reserva;
	    this.propina = propina;
	    this.fecha = LocalDateTime.now();
	}
	public void calcularValores() {

	    double suma = 0;

	    for (Pedido ped : reserva.getPedidos()) {

	        for (Platillo p : ped.getPlatillos()) {
	            suma += p.getprecio();
	        }

	        for (Juego j : ped.getJuegos()) {
	            suma += j.getprecio();
	        }
	    }

	    this.subtotal = suma;
	    this.iva = subtotal * 0.19;
	    this.total = subtotal + iva + propina;

	    // Puntos (ejemplo)
	    this.puntosGenerados = (int) (total / 1000);
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
	
	

}
