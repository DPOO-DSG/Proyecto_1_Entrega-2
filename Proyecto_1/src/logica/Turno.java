package logica;

import java.util.ArrayList;

public class Turno {
	private String jornada;
	private ArrayList<Mesero> Meseros;
	private ArrayList<Cocinero> Cocineros;
	//Constructor
	public Turno(String jornada, ArrayList<Mesero> listaMeseros,
			ArrayList<Cocinero> listaCocineros) {
		this.jornada = jornada;
		this.Meseros = listaMeseros;
		this.Cocineros = listaCocineros;
	}
	//Getters
	public String getJornada() {
		return jornada;
	}
	public ArrayList<Mesero> getMeseros() {
		return Meseros;
	}
	public ArrayList<Cocinero> getCocineros() {
		return Cocineros;
	}
	
	public void agregarEmpleado(Empleado e) {
	    if (e instanceof Cocinero) {
	        Cocineros.add((Cocinero) e);
	    } else if (e instanceof Mesero) {
	        Meseros.add((Mesero) e);
	    }
	}

	public void removerEmpleado(Empleado e) {
	    if (e instanceof Cocinero) {
	        Cocineros.remove(e);
	    } else if (e instanceof Mesero) {
	        Meseros.remove(e);
	    }
	}
	
}
