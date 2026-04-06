package logica;

import java.util.ArrayList;

public abstract class Usuario {
	private String login;
	private String password;
	private ArrayList<Juego> juegosFavoritos;
	//Constructor
	public Usuario(String login, String password, ArrayList<Juego> juegosFavoritos) {
		super();
		this.login = login;
		this.password = password;
		this.juegosFavoritos = juegosFavoritos;
	}
	//Getter y setter
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<Juego> getJuegosFavoritos() {
		return juegosFavoritos;
	}
	public void setJuegosFavoritos(ArrayList<Juego> juegosFavoritos) {
		this.juegosFavoritos = juegosFavoritos;
	}
	

}
