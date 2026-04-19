package logica;

import java.io.Serializable;
import java.util.HashMap;


public class Administrador implements Serializable {
	
	
	public HashMap<Integer, CambioDeTurno> verSolicitudes(Cafe cafe) {
	    return cafe.getSolicitudesPendientes();
	} 
	
	public boolean aprobarSolicitud(Cafe cafe, int idSolicitud) {
	    return cafe.aprobarSolicitud(idSolicitud);
	}
	
	public boolean rechazarSolicitud(Cafe cafe, int idSolicitud) {
	    return cafe.rechazarSolicitud(idSolicitud);
	}
	public boolean crearPlatillo(Cafe cafe, Platillo platillo) {
		return cafe.anadirAMenu(platillo);
	}
	public boolean crearJuego(Cafe cafe, String categoria, String nombre, int cantidad, int precio, int añoPublicacion, String empresaMatriz, int minJugadores, int maxJugadores, String restriccionEdad, boolean dificil, String tipoInventario) {	
		return cafe.crearJuego(categoria, nombre, cantidad, precio, añoPublicacion, empresaMatriz, minJugadores, maxJugadores, restriccionEdad, dificil, tipoInventario);
		
	}
		
	
}

