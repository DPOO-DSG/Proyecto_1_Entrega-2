package logica;

import java.util.ArrayList;

public class TorneoCompetitivo extends Torneo{

	private int costoEntrada;
	private int premio;
	
	public TorneoCompetitivo(String nombre, Juego juego, int cuposMaximos, String dia,
	        int costoEntrada, int premio) {
	    super(nombre, juego, cuposMaximos, dia);
	    this.costoEntrada = costoEntrada;
	    this.premio = premio;
	}
	
	
	
	
}
