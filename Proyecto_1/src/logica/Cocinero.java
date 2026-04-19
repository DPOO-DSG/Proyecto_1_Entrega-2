package logica;

import java.io.Serializable;
import java.util.ArrayList;

public class Cocinero extends Empleado implements Serializable {
	//Constructor
	public Cocinero(String login, String password, ArrayList<Juego> juegosFavoritos, String codigoDescuento,
			ArrayList<Turno> turno) {
		super(login, password, juegosFavoritos, codigoDescuento, turno);
	}
}
