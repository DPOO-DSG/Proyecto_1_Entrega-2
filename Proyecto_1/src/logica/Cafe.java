package logica;

import java.util.ArrayList;
import java.util.HashMap;

public class Cafe {
	private int capacidad;
	private HashMap<Integer,CompraVenta> registroVentas;
	private HashMap<String,Prestamo> registroPrestamos;
	private InventarioVenta inventarioVentas;
	private InventarioPrestamo inventarioPrestamo;
	private HashMap<String,Empleado> empleados; 
	private HashMap<String,Cliente> clientes;
	private HashMap<String,Administrador> administradores;
	private HashMap<Integer,CambioDeTurno> solicitudesCambioTurno;
	
    private int contadorSolicitudes = 0;


	
	public Cafe() {
		this.capacidad = capacidad;
		this.registroVentas = registroVentas;
		this.registroPrestamos = registroPrestamos;
		this.inventarioVentas = inventarioVentas;
		this.inventarioPrestamo = inventarioPrestamo;
		this.empleados = new HashMap<String, Empleado>();
		this.clientes = new HashMap<String, Cliente>();
		this.administradores = new HashMap<String, Administrador>();
		this.solicitudesCambioTurno = new HashMap<Integer, CambioDeTurno>();
	}
	
	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public HashMap<Integer, CompraVenta> getRegistroVentas() {
		return registroVentas;
	}


	public HashMap<String, Prestamo> getRegistroPrestamos() {
		return registroPrestamos;
	}

	public InventarioVenta getInventarioVentas() {
		return inventarioVentas;
	}

	public void setInventarioVentas(InventarioVenta inventarioVentas) {
		this.inventarioVentas = inventarioVentas;
	}

	public InventarioPrestamo getInventarioPrestamo() {
		return inventarioPrestamo;
	}

	public void setInventarioPrestamo(InventarioPrestamo inventarioPrestamo) {
		this.inventarioPrestamo = inventarioPrestamo;
	}

	public HashMap<String, Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(HashMap<String, Empleado> empleados) {
		this.empleados = empleados;
	}

	public HashMap<String, Cliente> getClientes() {
		return clientes;
	}


	public void setClientes(HashMap<String, Cliente> clientes) {
		this.clientes = clientes;
	}

	public HashMap<String, Administrador> getAdministradores() {
		return administradores;
	}


	public void setAdministradores(HashMap<String, Administrador> administradores) {
		this.administradores = administradores;
	}

	public HashMap<Integer, CambioDeTurno> getSolicitudesCambioTurno() {
		return solicitudesCambioTurno;
	}

	public void setSolicitudesCambioTurno(HashMap<Integer, CambioDeTurno> solicitudesCambioTurno) {
		this.solicitudesCambioTurno = solicitudesCambioTurno;
	}

	//METODOS
	
	public int generarIdSolicitud() {
	    return contadorSolicitudes++;
	}//Genera id de solicitud
	
	public void crearCliente(String login, String password, ArrayList<Juego> juegosFavoritos, double puntosFidelidad) {
		Cliente nuevoCliente = new Cliente(login, password, juegosFavoritos, puntosFidelidad);
		this.clientes.put(login, nuevoCliente);
	}//Crea un nuevo cliente
	
	public void crearMesero (String login, String password, ArrayList<Juego> juegosFavoritos, 
			String codigoDescuento, Turno turno, ArrayList<Juego> juegosConocidos) {
		Mesero nuevoMesero = new Mesero(login, password, juegosFavoritos, codigoDescuento, turno, juegosConocidos);
		this.empleados.put(login, nuevoMesero);
	} //Crea un nuevo mesero
	
	public void crearCocinero (String login, String password, ArrayList<Juego> juegosFavoritos, String codigoDescuento,
			Turno turno) {
		Cocinero nuevoCocinero = new Cocinero(login, password, juegosFavoritos, codigoDescuento, turno);
		this.empleados.put(login, nuevoCocinero);
	}//Crea un cocinero
	
	public void crearAdministrador() {
		return;
	}
//Crea un administrador



//REQUERIMIENTO FUNCIONAL 1: CREAR SOLICITUD DE CAMBIO DE TURNO

//Agregar solicitud de cambio al mapa de solicitudes.
public boolean agregarSolicitud(CambioDeTurno solicitud) {

    if (solicitud == null) {
        return false;
    }
    
    solicitudesCambioTurno.put(solicitud.getId(), solicitud);
    return true;
    }

//Metodo para ver las solicitudes pendientes del mapa de solicitudes
public HashMap<Integer, CambioDeTurno> getSolicitudesPendientes() {
    HashMap<Integer, CambioDeTurno> pendientes = new HashMap<>();

    for (CambioDeTurno s : solicitudesCambioTurno.values()) {
        if (s.getEstado().equals("PENDIENTE")) {
            pendientes.put(s.getId(), s);
        }
    }

    return pendientes;
}

//Metodo usado por administrador para aprobar una solicitud 
public boolean aprobarSolicitud(int idSolicitud) {
	// TODO Auto-generated method stub
	CambioDeTurno solicitud = solicitudesCambioTurno.get(idSolicitud);

    if (solicitud == null || !solicitud.getEstado().equals("PENDIENTE")) {
        return false;
    }

    //aplicar cambio de turno
    Empleado empleado = solicitud.getEmpleado();

    empleado.cambiarTurno(
        solicitud.getTurnoOriginal(),
        solicitud.getTurnoCambio()
    );

    // cambiar estado
    solicitud.aprobar();

    return true;

	}

public boolean rechazarSolicitud(int idSolicitud) {
	CambioDeTurno solicitud = solicitudesCambioTurno.get(idSolicitud);

    if (solicitud == null || !solicitud.getEstado().equals("PENDIENTE")) {
        return false;
    }

    solicitud.rechazar();

    return true;
}

}


		