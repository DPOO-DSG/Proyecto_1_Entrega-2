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
}//Crea un administrador
		