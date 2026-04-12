package logica;

import java.time.LocalDateTime;
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
	private HashMap<String, String> credencialesAdmin;
	private HashMap<Integer,CambioDeTurno> solicitudesCambioTurno;
	private HashMap<Integer, Mesa> mesas;
	private HashMap<String, Turno> turnos;
	
    private int contadorSolicitudes = 0;

 
	
	public Cafe(int capacidad, int cantidadMesas) {
		this.capacidad = capacidad;
		this.registroVentas = new HashMap<>();
		this.registroPrestamos = new HashMap<>();
		this.inventarioVentas = new InventarioVenta();
		this.inventarioPrestamo = new InventarioPrestamo(); 
		this.empleados = new HashMap<String, Empleado>();
		this.clientes = new HashMap<String, Cliente>();
		this.administradores = new HashMap<String, Administrador>();
		this.solicitudesCambioTurno = new HashMap<Integer, CambioDeTurno>();
		this.mesas =new HashMap<Integer,Mesa>();
	    this.credencialesAdmin = new HashMap<>();
	    this.turnos = new HashMap<>();
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
	

	public HashMap<Integer, Mesa> getMesas() {
		return mesas;
	}

	public void setMesas(HashMap<Integer, Mesa> mesas) {
		this.mesas = mesas;
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
	
	public HashMap<String, Turno> getTurnos() {
		return turnos;
	}

	public void setTurnos(HashMap<String, Turno> turnos) {
		this.turnos = turnos;
	}
	

	//METODOS
	


	public int generarIdSolicitud() {
	    return contadorSolicitudes++;
	}//Genera id de solicitud
	private boolean existeLogin(String login) {
	    return clientes.containsKey(login) || empleados.containsKey(login) ||administradores.containsKey(login);
	}//Verificar la existencia del usuario
	
	public boolean crearCliente(String login, String password, ArrayList<Juego> juegosFavoritos, double puntosFidelidad) {
	    if (existeLogin(login)) return false;
	    Cliente nuevo = new Cliente(login, password, juegosFavoritos, puntosFidelidad);
	    clientes.put(login, nuevo);
	    return true;
	}
	//Crea un nuevo mesero
	public int crearMesero(String login, String password, String codigoDescuento, ArrayList<Juego> juegosFavoritos, 
			ArrayList<String> dias, ArrayList<Juego> juegosConocidos) {

	    if (existeLogin(login)) return 1;
	    ArrayList<Turno> turnosAsignar = new ArrayList<>();
	    for (String dia : dias) {
	        Turno t = turnos.get(dia.toLowerCase());
	        if (t == null) return 2;
	        turnosAsignar.add(t);
	    }
	    Mesero nuevo = new Mesero(login, password,  juegosFavoritos, codigoDescuento, new ArrayList<>(),  juegosConocidos);
	    for (Turno t : turnosAsignar) {
	        nuevo.getTurnos().add(t);
	        t.agregarEmpleado(nuevo);
	    }
	    empleados.put(login, nuevo);
	    return 0;
	}
	
	
	public int crearCocinero(String login, String password, String codigoDescuento,
	        ArrayList<Juego> juegosFavoritos,
	        ArrayList<String> dias) {
	    if (existeLogin(login)) return 1;
	    ArrayList<Turno> turnosAsignar = new ArrayList<>();
	    for (String dia : dias) {
	        Turno t = turnos.get(dia.toLowerCase());
	        if (t == null) return 2;
	        turnosAsignar.add(t);
	    }
	    Cocinero nuevo = new Cocinero(login, password, juegosFavoritos,codigoDescuento,
	        new ArrayList<>() );
	    for (Turno t : turnosAsignar) {
	        nuevo.getTurnos().add(t);
	        t.agregarEmpleado(nuevo);
	    }
	    empleados.put(login, nuevo);
	    return 0;
	}//nuevo cocinero
	
	public boolean crearAdministrador(String login, String password) {
	    if (existeLogin(login)) return false;
	    Administrador admin = new Administrador();
	    administradores.put(login, admin);
	    credencialesAdmin.put(login, password);
	    return true;
	} //Crear administrador
	
	public Object login(String login, String password) {

	    // cliente
	    if (clientes.containsKey(login)) {
	        Cliente c = clientes.get(login);
	        if (c.getPassword().equals(password)) return c;
	    }

	    // empleado
	    if (empleados.containsKey(login)) {
	        Empleado e = empleados.get(login);
	        if (e.getPassword().equals(password)) return e;
	    }

	    // administrador
	    if (administradores.containsKey(login)) {
	        String pass = credencialesAdmin.get(login);
	        if (pass != null && pass.equals(password)) return administradores.get(login);
	    }

	    return null;
	}
	//Asignacion inicial de turnos
	public void inicializarTurnos() {
	    turnos = new HashMap<>();
	    String[] dias = {
	        "lunes", "martes", "miercoles",
	        "jueves", "viernes", "sabado", "domingo"};
	    for (String dia : dias) {
	        turnos.put(dia, new Turno(dia, new ArrayList<>(), new ArrayList<>()));
	    }
	}
	
	public int asignarTurnoEmpleado(String loginEmpleado, String jornada) {
	    Empleado e = empleados.get(loginEmpleado);
	    if (e == null) {
	        return 1; // empleado no existe
	    }
	    Turno t = turnos.get(jornada.toLowerCase());
	    if (t == null) {
	        return 2; // turno no existe
	    }
	    if (e.getTurnos().contains(t)) {
	        return 3; // ya tiene ese turno
	    }
	    // asignación
	    e.getTurnos().add(t);
	    t.agregarEmpleado(e);

	    return 0; 
	}
	



// REQUERIMIENTO FUNCIONAL 1: CREAR SOLICITUD DE CAMBIO DE TURNO
	public boolean crearSolicitudCambio(Empleado empleado, Turno actual, Turno nuevo) {

	    if (empleado == null || actual == null || nuevo == null) {
	        throw new IllegalArgumentException("Datos de solicitud inválidos");
	    }

	    // Mirar que el empleado tenga ese turno
	    if (!empleado.getTurnos().contains(actual)) {
	        throw new IllegalStateException("El empleado no pertenece al turno indicado");
	    }

	    if (!puedeSalirDelTurno(empleado, actual)) {
	        throw new IllegalStateException("El empleado no puede salir  del turno actual");
	    }

	    int id = generarIdSolicitud();

	    CambioDeTurno solicitud = new CambioDeTurno(id, empleado, actual, nuevo);

	    solicitudesCambioTurno.put(id, solicitud);

	    return true;
	}

	private boolean puedeSalirDelTurno(Empleado empleado, Turno turno) {

    int numCocineros = turno.getCocineros().size();
    int numMeseros = turno.getMeseros().size();

    if (empleado instanceof Cocinero) {
        return numCocineros > 1; // mínimo 1 se queda
    }

    if (empleado instanceof Mesero) {
        return numMeseros > 1; // mínimo 1 se queda
    }

    return true;
    
	}

//Metodo para ver las solicitudes pendientes del mapa de solicitudes
	public HashMap<Integer, CambioDeTurno> getSolicitudesPendientes() {

	    HashMap<Integer, CambioDeTurno> pendientes = new HashMap<>();

	    for (CambioDeTurno s : solicitudesCambioTurno.values()) {
	        if (s.getEstado().equalsIgnoreCase("PENDIENTE")) {
	            pendientes.put(s.getId(), s);
	        }
	    }

	    return pendientes;
	}

	//Metodo usado por administrador para aprobar una solicitud 
	public boolean aprobarSolicitud(int idSolicitud) {

    CambioDeTurno solicitud = solicitudesCambioTurno.get(idSolicitud);

    if (solicitud == null || !solicitud.getEstado().equals("PENDIENTE")) {
        return false;
    }

    Empleado empleado = solicitud.getEmpleado();

    Turno turnoViejo = solicitud.getTurnoOriginal();
    Turno turnoNuevo = solicitud.getTurnoCambio();

    // actualizar
    actualizarTurno(empleado, turnoViejo, turnoNuevo);

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


	private void actualizarTurno(Empleado e, Turno turnoViejo, Turno turnoNuevo) {

	    if (e == null || turnoViejo == null || turnoNuevo == null) {
	        return;
	    }

	    turnoViejo.removerEmpleado(e);

	    e.getTurnos().remove(turnoViejo);
	    e.getTurnos().add(turnoNuevo);

	    turnoNuevo.agregarEmpleado(e);
	}

	public boolean intercambiarTurnos(Empleado e1, Empleado e2, Turno turno1, Turno turno2) {
	
	    if (e1 == null || e2 == null || turno1 == null || turno2 == null) {
	        return false;
	    }
	
	    // verifcar que ambos empleados tengan esos turnos
	    if (!e1.getTurnos().contains(turno1) || !e2.getTurnos().contains(turno2)) {
	        System.out.println("Alguno de los empleados no tiene al turno dentro de su horario.");
	        return false;
	    }
	
	    if (!puedeSalirDelTurno(e1, turno1) || !puedeSalirDelTurno(e2, turno2)) {
	        System.out.println("No se puede realizar el intercambio por falta de personal para el funcionamiento del cafe.");
	        return false;
	    }
	
	    actualizarTurno(e1, turno1, turno2);
	    actualizarTurno(e2, turno2, turno1);
	
	    return true;
	}
	
	
	
	// REQUERIMIENTO PARA CONSULTAR CATALOGO DE JUEGOS (PRESTAMO Y VENTA) DE PARTE DEL USUARIO 
	
	public ArrayList<Juego> consultarCatalogoPrestamo() {
	    return new ArrayList<>(inventarioPrestamo.getStock().keySet());
	}
	public ArrayList<Juego> consultarCatalogoVenta() {
	    return new ArrayList<>(inventarioVentas.getStock().keySet());
	}
	
	
	
	
	//REQUERIMIENTO CONSULTAR PUNTOS DE FIDELIDAD 
	public double consultarPuntosFidelidad(Cliente cliente) {
	    return cliente.getPuntosFidelidad();
	}
	
	
	
	
	//REQUERIMIENTO CONSULTAR TURNOS EMPLEADO 
	
	public ArrayList<Turno> consultarTurnosEmpleado(Empleado empleado) {
	    return empleado.getTurnos();
	}
	
	
	
	
	
	//REQUERIMIENTO DE SOLCITUD DE MESA
	//Reservar mesa
	public boolean agendarReserva(Cliente cliente, int cantidadPersonas, boolean ninos, boolean jovenes, LocalDateTime fechaDeseada) {
	    // Buscamos una mesa libre para la fecha solicitada
	    for (Mesa mesa : mesas.values()) {
	        if (mesa.getCapacidad() >= cantidadPersonas && mesa.estaDisponibleEnFecha(fechaDeseada)) {
	            Reserva nueva = new Reserva(fechaDeseada, cliente, cantidadPersonas, ninos, jovenes);
	            mesa.agregarReserva(nueva);
	            
	            System.out.println("Reserva para la mesa #" + mesa.getIdMesa() + " para la fecha: " + fechaDeseada);
	            return true;
	        }
	    }
	    System.out.println("No hay mesas disponibles para esa cantidad de personas en esa fecha.");
	    return false;
	}
	
	
	//REQUERIMIENTO DE REALIZAR COMPRA
	public void crearPedido(Reserva reserva,
	        Usuario usuario,
	        ArrayList<Platillo> platillos,
	        ArrayList<Juego> juegos) {
	
	Pedido pedido = new Pedido(usuario, platillos, juegos);
	
	reserva.getPedidos().add(pedido);
	}
	public boolean crearFactura(Usuario usuario,
	        double propina,
	        boolean usarPuntos,
	        String codigo,
	        Reserva reserva) {
	
	// VALIDACIONES (ahora con reserva)
	if (!validarAlcoholReserva(reserva)) return false;
	if (!validarCalienteConAccionReserva(reserva, usuario)) return false;
	
	// validar juegos en TODOS los pedidos
	for (Pedido ped : reserva.getPedidos()) {
	for (Juego j : ped.getJuegos()) {
	if (!inventarioVentas.estaDisponible(j)) return false;
	}
	}
	
	int id = registroVentas.size() + 1;
	
	CompraVenta compra = new CompraVenta(id, usuario, propina, reserva);
	
	// calcular base
	compra.calcularValores();
	
	double total = compra.getTotal();
	
	// descuento
	total = aplicarDescuento(usuario, total, codigo);
	
	// puntos
	if (usuario instanceof Cliente) {
	Cliente c = (Cliente) usuario;
	
	total = aplicarPuntos(c, total, usarPuntos);
	asignarPuntos(c, total);
	}
	
	compra.setTotal(total);
	
	// descontar inventario
	for (Pedido ped : reserva.getPedidos()) {
	for (Juego j : ped.getJuegos()) {
	inventarioVentas.registrarVenta(j);
	}
	}
	
	registroVentas.put(id, compra);
	
	return true;
	}
	private boolean validarAlcoholReserva(Reserva reserva) {
		boolean hayAlcohol = false;
	
	    // Revisar todos los pedidos
	    for (Pedido ped : reserva.getPedidos()) {
	        for (Platillo p : ped.getPlatillos()) {
	
	            if (p instanceof Bebida) {
	                Bebida b = (Bebida) p;
	
	                if (b.isAlcoholico()) {
	                    hayAlcohol = true;
	                    break;
	                }
	            }
	        }
	    }
	
	    if (!hayAlcohol) return true;
	
	    // Revisar si hay menores
	    if(reserva.isTieneNinos()) {
	    	return false;
	    }
	
	    return true;
	
	    
	}
	private boolean validarCalienteConAccionReserva(Reserva reserva, Usuario usuario) {
		boolean hayCaliente = false;
	
	    // Revisar pedidos
	    for (Pedido ped : reserva.getPedidos()) {
	        for (Platillo p : ped.getPlatillos()) {
	
	            if (p instanceof Bebida) {
	                Bebida b = (Bebida) p;
	
	                if (b.getTipo().equals("caliente")) {
	                    hayCaliente = true;
	                    break;
	                }
	            }
	        }
	    }
	
	    if (!hayCaliente) return true;
	
	    // Revisar préstamos activos del usuario
	    for (Prestamo pr : registroPrestamos.values()) {
	        if (pr.getUsuario().equals(usuario) && !pr.isDevuelto()) {
	
	            if (pr.getJuego().getCategoria().equals("accion")) {
	                return false;
	            }
	        }
	    }
	
	    return true;
	}
	private double aplicarDescuento(Usuario usuario, double subtotal, String codigo) {
	
	    if (codigo == null) return subtotal;
	
	    Empleado dueñoCodigo = buscarEmpleadoPorCodigo(codigo);
	
	    if (dueñoCodigo == null) return subtotal;
	
	    // mismo empleado
	    if (usuario.equals(dueñoCodigo)) {
	        return subtotal * 0.8; // 20%
	    }
	
	    // otro usuario
	    return subtotal * 0.9; // 10%
	}
	private Empleado buscarEmpleadoPorCodigo(String codigo) {
		// TODO Auto-generated method stub
		for(String code: empleados.keySet()) {
			if(code.equals(codigo)) {
				return empleados.get(code);
			}
			
			
		}
		return null;
	}
	
	private double aplicarPuntos(Cliente cliente, double total, boolean usarPuntos) {
	
	    if (!usarPuntos) return total;
	
	    double descuento = cliente.getPuntosFidelidad();
	
	    cliente.setPuntosFidelidad(0);
	
	    return Math.max(0, total - descuento);
	}
	private void asignarPuntos(Cliente cliente, double total) {
	    double puntos = total * 0.01;
	    cliente.setPuntosFidelidad(cliente.getPuntosFidelidad() + puntos);
	}
	
	//Requerimiento funcional gestion de inventario
	
	public boolean solicitarPrestamo(Usuario usuario, Juego juego, Reserva reserva) {
	
	    // 1. validar disponibilidad
	    if (!inventarioPrestamo.estaDisponible(juego)) {
	        return false;
	    }
	    
	    // 2. validar restricciones según tipo
	    if (usuario instanceof Cliente) {
	        if (reserva == null) return false;
	        if (!validarPrestamoCliente((Cliente) usuario, juego, reserva)) {
	            return false;
	        }
	    }
	
	    if (usuario instanceof Empleado) {
	        if (!validarPrestamoEmpleado((Empleado) usuario)) {
	            return false;
	        }
	    }
	
	    // 3. crear préstamo
	    String id = "P" + (registroPrestamos.size() + 1);
	
	    Prestamo prestamo = new Prestamo(id, usuario, juego);
	
	    // 4. registrar en el historial que puede ser visualizado por el administrador 
	    registroPrestamos.put(id, prestamo);
	
	    // 5. actualizar inventario
	    inventarioPrestamo.registrarPrestamo(juego);
	
	    return true;
	}
	
	private boolean validarPrestamoEmpleado(Empleado usuario) {
		// TODO Auto-generated method stub
		if (usuario.estaEnTurno()) {
	        return false;
	    }
	
		return true;
	}
	
	private boolean validarPrestamoCliente(Cliente cliente, Juego juego, Reserva reserva) {
	
	    // 1. máximo 2 préstamos activos
	    int activos = 0;
	
	    for (Prestamo p : registroPrestamos.values()) {
	        if (p.getUsuario().equals(cliente) && !p.isDevuelto()) {
	            activos++;
	        }
	    }
	
	    if (activos >= 2) return false;
	
	    // 2. bebidas calientes + acción
	    boolean hayCaliente = false;
	
	    for (Pedido ped : reserva.getPedidos()) {
	        for (Platillo p : ped.getPlatillos()) {
	            if (p instanceof Bebida) {
	                Bebida b = (Bebida) p;
	
	                if (b.getTipo().equals("caliente")) {
	                    hayCaliente = true;
	                    break;
	                }
	            }
	        }
	    }
	
	    if (hayCaliente && juego.getCategoria().equals("accion")) {
	        return false;
	    }
	
	    // 3. edad mínima
	    if (reserva.isTieneNinos() && juego.getEdadMinima() > 0) {
	        return false;
	    }
	
	    // 4. capacidad
	    if (reserva.getCantidadPersonas() > juego.getMaxJugadores()) {
	        return false;
	    }
	
	    return true;
	}
	
}
	
	
		