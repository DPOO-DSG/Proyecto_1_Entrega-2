package logica;

import java.util.ArrayList;

public class Pedido {
	private Usuario comprador;
	private ArrayList<Platillo> platillos;
	private ArrayList<Juego> juegos;

	
	public Pedido(Usuario usuario, ArrayList<Platillo> platillos, ArrayList<Juego> juegos) {
		super();
		this.comprador = usuario; 
		this.platillos = platillos;
		this.juegos = juegos;

	}




	public Usuario getComprador() {
		return comprador;
	}




	public void setComprador(Usuario comprador) {
		this.comprador = comprador;
	}




	public ArrayList<Platillo> getPlatillos() {
		return platillos;
	}




	public void setPlatillos(ArrayList<Platillo> platillos) {
		this.platillos = platillos;
	}




	public ArrayList<Juego> getJuegos() {
		return juegos;
	}




	public void setJuegos(ArrayList<Juego> juegos) {
		this.juegos = juegos;
	}



	
	
	
	
}
