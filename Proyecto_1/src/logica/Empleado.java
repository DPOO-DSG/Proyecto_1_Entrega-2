package logica;

import java.util.ArrayList;

public abstract class Empleado extends Usuario {
	private String codigoDescuento;
	private Turno turno;
	
	//Constructor
	public Empleado(String login, String password, ArrayList<Juego> juegosFavoritos, String codigoDescuento,
			Turno turno) {
		super(login, password, juegosFavoritos);
		this.codigoDescuento = codigoDescuento;
		this.turno = turno;
	}
	//getters y setters
	public String getCodigoDescuento() {
		return codigoDescuento;
	}

	public void setCodigoDescuento(String codigoDescuento) {
		this.codigoDescuento = codigoDescuento;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
	
	//Metodo en el que el empleado crea la solicitud de cambio
	
	public boolean solicitarCambioTurno(Cafe cafe, Turno actual, Turno nuevo) {
	    return cafe.crearSolicitudCambio(this, actual, nuevo);
	}
	
	public void cambiarTurno(Turno nuevoTurno) {
	    this.turno = nuevoTurno;
	}
	public boolean estaEnTurno() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
