package logica;

import java.util.ArrayList;

public abstract class Empleado extends Usuario {
	private String codigoDescuento;
	private ArrayList<Turno> turnos;
	
	//Constructor
	public Empleado(String login, String password, ArrayList<Juego> juegosFavoritos, String codigoDescuento,
			ArrayList<Turno> turnos) {
		super(login, password, juegosFavoritos);
		this.codigoDescuento = codigoDescuento;
		this.turnos = turnos;
	} 
	//getters y setters
	public String getCodigoDescuento() {
		return codigoDescuento;
	}

	public void setCodigoDescuento(String codigoDescuento) {
		this.codigoDescuento = codigoDescuento;
	}

	public ArrayList<Turno> getTurnos() {
	    return turnos;
	}

	public void setTurnos(ArrayList<Turno> turnos) {
	    this.turnos = turnos;
	}
	
	
	
	//Metodo en el que el empleado crea la solicitud de cambio
	
	public boolean solicitarCambioTurno(Cafe cafe, Turno actual, Turno nuevo) {
	    return cafe.crearSolicitudCambio(this, actual, nuevo);
	}
	

	
	public boolean estaEnTurno() {
		
		
		return false;
	}
	
	public ArrayList<Turno> consultarTurnos(Cafe cafe) {
	    return cafe.consultarTurnosEmpleado(this);
	}
}
