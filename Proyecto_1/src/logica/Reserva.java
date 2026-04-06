package logica;

import java.time.LocalDateTime;

public class Reserva {
	private LocalDateTime fecha;
	private LocalDateTime fechaHora;
    private Cliente cliente; 
    private int cantidadPersonas;
    private boolean tieneNinos;  
    private boolean tieneJovenes; 
    private Mesa mesaAsignada;
    //Constructor
	public Reserva(LocalDateTime fecha, LocalDateTime fechaHora, Cliente cliente, int cantidadPersonas,
			boolean tieneNinos, boolean tieneJovenes, Mesa mesaAsignada) {
		this.fecha = fecha;
		this.fechaHora = fechaHora;
		this.cliente = cliente;
		this.cantidadPersonas = cantidadPersonas;
		this.tieneNinos = tieneNinos;
		this.tieneJovenes = tieneJovenes;
		this.mesaAsignada = mesaAsignada;
	}
	//getters y setters
	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
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
	public Mesa getMesaAsignada() {
		return mesaAsignada;
	}
	public void setMesaAsignada(Mesa mesaAsignada) {
		this.mesaAsignada = mesaAsignada;
	}

    
}
