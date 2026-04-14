package logica;

import java.util.HashMap;

public class Administrador {
	
	
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
		return cafe.añadirAMenu(platillo);
	}
	public boolean crearJuego(Cafe cafe, Juego juego,String tipoInventario) {
		return cafe.crearJuego(juego, tipoInventario );
	}
		
	
}

