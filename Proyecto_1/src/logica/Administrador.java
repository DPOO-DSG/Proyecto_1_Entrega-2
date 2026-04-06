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
}

