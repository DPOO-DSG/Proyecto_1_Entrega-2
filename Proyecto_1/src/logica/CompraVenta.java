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

	private Pedido pedido;
	private Usuario usuario;
	
	public CompraVenta(int numeroFactura, Usuario usuario, Pedido pedido, double propina) {
	    this.numeroFactura = numeroFactura;
	    this.usuario = usuario;
	    this.pedido = pedido;
	    this.propina = propina;
	    this.fecha = LocalDateTime.now();
	}
	public void calcularValores() {

	    double suma = 0;

	    // Platillos
	    for (Platillo p : pedido.getPlatillos()) {
	        suma += p.getprecio();
	    }

	    // Juegos
	    for (Juego j : pedido.getJuegos()) {
	        suma += j.getprecio();
	    }

	    this.subtotal = suma;

	    // IVA 19%
	    this.iva = subtotal * 0.19;

	    // Total
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
