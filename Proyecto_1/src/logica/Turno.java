package logica;

import java.util.ArrayList;

public class Turno {
	private String jornada;
	private ArrayList<Mesero> listaMeseros;
	private ArrayList<Cocinero> listaCocineros;
	//Constructor
	public Turno(String jornada, ArrayList<Mesero> listaMeseros,
			ArrayList<Cocinero> listaCocineros) {
		this.jornada = jornada;
		this.listaMeseros = listaMeseros;
		this.listaCocineros = listaCocineros;
	}
	//Getters
	public String getJornada() {
		return jornada;
	}
	public ArrayList<Mesero> getListaMeseros() {
		return listaMeseros;
	}
	public ArrayList<Cocinero> getListaCocineros() {
		return listaCocineros;
	}
	
}
