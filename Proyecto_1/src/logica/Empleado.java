package logica;

import java.time.LocalDateTime;
import  excepciones.*;
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
	
	public void solicitarCambioTurno(Cafe cafe, Turno actual, Turno nuevo)
	        throws SolicitudInvalidaException, NoPerteneceTurnoException, PersonalInsuficienteException {

	    cafe.crearSolicitudCambio(this, actual, nuevo);
	}
	
	
	
	public boolean estaEnTurnoAhora() {
	    String hoy = LocalDateTime.now().getDayOfWeek().toString().toLowerCase();

	    for (Turno t : turnos) {
	        if (t.getJornada().equals(hoy)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	public ArrayList<Turno> consultarTurnos(Cafe cafe) {
	    return cafe.consultarTurnosEmpleado(this);
	}
	@Override
	
	public void solicitarPrestamo(Cafe cafe, Juego juego, Reserva reserva)throws Exception {
	     cafe.solicitarPrestamo(this, juego, null);
	}
	public void hacerPedido(Cafe cafe,
	        Reserva reserva,
	        ArrayList<Platillo> platillos,
	        ArrayList<Juego> juegos) throws ReservaNoEncontradaException, EmpleadoNoExisteException {

	    cafe.crearPedido(reserva, this, platillos, juegos);
	}
}
