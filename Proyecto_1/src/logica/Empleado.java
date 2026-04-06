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
	
	
	
}
