package logica;

import java.util.ArrayList;

public class TorneoAmistoso extends Torneo{

	public TorneoAmistoso(String nombre, Juego juego, int cuposMaximos, String dia) {
		super(nombre, juego, cuposMaximos, dia);
	}

	@Override
	public void otorgarPremio(Usuario ganador) {

	    if (ganador instanceof Cliente) {
	        Cliente c = (Cliente) ganador;
	        c.agregarBonoDescuento();
	    }

	}
	
	

	
	
	
	
	
}
