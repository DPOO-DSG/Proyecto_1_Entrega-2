package logica;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Reserva {
    private LocalDateTime fechaReserva;
    private Cliente cliente; 
    private int cantidadPersonas;
    private boolean tieneNinos;  
    private boolean tieneJovenes;
    private ArrayList<Pedido> pedidos;
    
    //Constructor
	public Reserva(LocalDateTime fechaReserva, Cliente cliente, int cantidadPersonas,
			boolean tieneNinos, boolean tieneJovenes) {
		this.fechaReserva = fechaReserva;
		this.cliente = cliente;
		this.cantidadPersonas = cantidadPersonas;
		this.tieneNinos = tieneNinos;
		this.tieneJovenes = tieneJovenes;
	}
	//Getters y setters
	
	
	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}
	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}


	public void setPedidos(ArrayList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}


	public void setFechaReserva(LocalDateTime fechaReserva) {
		this.fechaReserva = fechaReserva;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public int getCantidadPersonas() {
		return cantidadPersonas;
	}
	public void setCantidadPersonas(int cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}
	public boolean isTieneNinos() {
		return tieneNinos;
	}
	public void setTieneNinos(boolean tieneNinos) {
		this.tieneNinos = tieneNinos;
	}
	public boolean isTieneJovenes() {
		return tieneJovenes;
	}
	public void setTieneJovenes(boolean tieneJovenes) {
		this.tieneJovenes = tieneJovenes;
	} 
}

    

