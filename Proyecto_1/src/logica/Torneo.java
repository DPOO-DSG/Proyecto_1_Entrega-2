package logica;

import java.util.ArrayList;

public abstract class Torneo {

	private String nombre; 
	private Juego juego; 
	
	private String Dia;
	protected int cuposMaximos;
	protected int cuposReservados;
	protected int cuposReservadosOcupados;
	protected ArrayList<Usuario> participantes;
	
	public Torneo(String nombre, Juego juego, int cuposMaximos, String dia) {
	    this.nombre = nombre;
	    this.juego = juego;
	    this.cuposMaximos = cuposMaximos;
	    this.participantes = new ArrayList<>();

	    this.cuposReservados = (int) Math.ceil(cuposMaximos * 0.2);
	    this.cuposReservadosOcupados = 0;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public String getDia() {
		return Dia;
	}

	public void setDia(String dia) {
		Dia = dia;
	}

	public int getCuposMaximos() {
		return cuposMaximos;
	}

	public void setCuposMaximos(int cuposMaximos) {
		this.cuposMaximos = cuposMaximos;
	}

	public int getCuposReservados() {
		return cuposReservados;
	}

	public void setCuposReservados(int cuposReservados) {
		this.cuposReservados = cuposReservados;
	}

	public int getCuposReservadosOcupados() {
		return cuposReservadosOcupados;
	}

	public void setCuposReservadosOcupados(int cuposReservadosOcupados) {
		this.cuposReservadosOcupados = cuposReservadosOcupados;
	}

	public ArrayList<Usuario> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(ArrayList<Usuario> participantes) {
		this.participantes = participantes;
	}
	
	
	
	
	
}
