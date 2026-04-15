package logica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import excepciones.*;
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
	private ArrayList<Platillo> menu;
    private int contadorSolicitudes = 0;
    private HashMap<Integer, Platillo> solicitudesAnadirPlatillo;
    private HashMap<String, Reserva> reservas;

 
	
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
	    this.menu = new ArrayList<Platillo>();
	    this.solicitudesAnadirPlatillo = new HashMap<>();
	    this.reservas = new HashMap<>();
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
	public void crearMesero(String login, String password, String codigoDescuento, ArrayList<Juego> juegosFavoritos, 
			ArrayList<String> dias, ArrayList<Juego> juegosConocidos) 
			        throws UsuarioYaExisteException, TurnoNoExisteException {
	    if (existeLogin(login)) {
	    throw new UsuarioYaExisteException(login);
	    }
	    ArrayList<Turno> turnosAsignar = new ArrayList<>();
	    for (String dia : dias) {
	        Turno t = turnos.get(dia.toLowerCase());
	        if (t == null)  {
	            throw new TurnoNoExisteException(dia);
	        }
	        turnosAsignar.add(t);
	    }
	    Mesero nuevo = new Mesero(login, password,  juegosFavoritos, codigoDescuento, new ArrayList<>(),  juegosConocidos);
	    for (Turno t : turnosAsignar) {
	        nuevo.getTurnos().add(t);
	        t.agregarEmpleado(nuevo);
	    }
	    empleados.put(login, nuevo);
	}
	
	
	public void crearCocinero(String login, String password, String codigoDescuento,
	        ArrayList<Juego> juegosFavoritos, ArrayList<String> dias)
	        		throws UsuarioYaExisteException, TurnoNoExisteException {
 
	    if (existeLogin(login)) {
		    throw new UsuarioYaExisteException(login);
		    };
	    ArrayList<Turno> turnosAsignar = new ArrayList<>();
	    for (String dia : dias) {
	        Turno t = turnos.get(dia.toLowerCase());
	        if (t == null) {
	            throw new TurnoNoExisteException(dia);
	        };
	        turnosAsignar.add(t);
	    }
	    Cocinero nuevo = new Cocinero(login, password, juegosFavoritos,codigoDescuento,
	        new ArrayList<>() );
	    for (Turno t : turnosAsignar) {
	        nuevo.getTurnos().add(t);
	        t.agregarEmpleado(nuevo);
	    }
	    empleados.put(login, nuevo);
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
	
	//REQUERIMIENTO AÑADIR PLATILLO A MENU
	
	public boolean anadirAMenu(Platillo platillo) {

	    if (platillo == null) {
	        return false;
	    }

	    // evitar platos duplicados con el mismo nombre
	    for (Platillo p : menu) {
	        if (p.getnombre().equals(platillo.getnombre())) {
	            return false;
	        }
	    }

	    menu.add(platillo);
	    return true;
	}
	
	public void crearSolicitudSugerencia(Platillo platillo) {

	    if (platillo == null) {
	        return;
	    }

	    int id = generarIdSolicitud();

	    solicitudesAnadirPlatillo.put(id, platillo);
	}


	// APROBAR SOLICITUD
	public boolean aprobarSolicitudPlatillo(int idSolicitud) {

	    if (!solicitudesAnadirPlatillo.containsKey(idSolicitud)) {
	        return false;
	    }

	    Platillo platillo = solicitudesAnadirPlatillo.get(idSolicitud);

	    anadirAMenu(platillo);

	    solicitudesAnadirPlatillo.remove(idSolicitud);

	    return true;
	}


	// RECHAZAR SOLICITUD
	public boolean rechazarSolicitudPlatillo(int idSolicitud) {

	    if (!solicitudesAnadirPlatillo.containsKey(idSolicitud)) {
	        return false;
	    }

	    solicitudesAnadirPlatillo.remove(idSolicitud);

	    return true;
	}


	// VER SOLICITUDES PENDIENTES
	public HashMap<Integer, Platillo> getSolicitudesPendientesPlatillos() {
	    return solicitudesAnadirPlatillo;
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
	
	public void asignarTurnoEmpleado(String loginEmpleado, String jornada)
	        throws EmpleadoNoExisteException, TurnoNoExisteException, TurnoYaAsignadoException {
	    Empleado e = empleados.get(loginEmpleado);
	    if (e == null) {
	        throw new EmpleadoNoExisteException(loginEmpleado);
	    }
	    Turno t = turnos.get(jornada.toLowerCase());
	    if (t == null) {
	        throw new TurnoNoExisteException(jornada);
	    }
	    if (e.getTurnos().contains(t)) {
	        throw new TurnoYaAsignadoException(jornada);
	    }
	    e.getTurnos().add(t);
	    t.agregarEmpleado(e);
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
	public void inicializarMesas(int cantidadMesas, int capacidadPorMesa) {

	    for (int i = 1; i <= cantidadMesas; i++) {
	        Mesa mesa = new Mesa(i, capacidadPorMesa,
	                new ArrayList<>(),
	                new ArrayList<>(),
	                null);

	        mesas.put(i, mesa);
	    }
	}
	
	public Reserva agendarReserva(Cliente cliente, int cantidadPersonas,
			boolean ninos, boolean jovenes,
			LocalDateTime fechaDeseada)
					throws NoHayMesasDisponiblesException, DatosReservaInvalidosException {

		if (cliente == null || fechaDeseada == null || cantidadPersonas <= 0) {
			throw new DatosReservaInvalidosException();
		}

		for (Mesa mesa : mesas.values()) {

			if (mesa.getCapacidad() >= cantidadPersonas &&
					mesa.estaDisponibleEnFecha(fechaDeseada)) {
				String id = "R" + (reservas.size() + 1);
				Reserva nueva = new Reserva(id, fechaDeseada, cliente,
						cantidadPersonas, ninos, jovenes);
				nueva.setPedidos(new ArrayList<>());
				mesa.agregarReserva(nueva);
				reservas.put(id, nueva);
				return nueva;
			}
		}

		throw new NoHayMesasDisponiblesException();
	}
	
	public ArrayList<Reserva> getReservasCliente(Cliente c) {
	    ArrayList<Reserva> lista = new ArrayList<>();

	    for (Reserva r : reservas.values()) {
	        if (r.getCliente().equals(c)) {
	            lista.add(r);
	        }
	    }
	    return lista;
	}
	
	
	//REQUERIMIENTO DE REALIZAR COMPRA
	public void crearPedido(Reserva reserva,
	        Empleado empleado,
	        ArrayList<Platillo> platillos,
	        ArrayList<Juego> juegos) throws ReservaNoEncontradaException, EmpleadoNoExisteException {

	    if (reserva == null) {
	        throw new ReservaNoEncontradaException("La reserva no existe");
	    }

	    if (empleado == null) {
	        throw new EmpleadoNoExisteException("Empleado inválido");
	    }

	    Pedido pedido = new Pedido(empleado, platillos, juegos);

	    reserva.getPedidos().add(pedido);
	
	}
	public boolean crearFactura(
	        double propina,
	        boolean usarPuntos,
	        String codigo,
	        Reserva reserva) 
	        throws NoHayMesasDisponiblesException,
	               BebidaCalienteConAccionException,
	               JuegoNoDisponibleException, ReservaNoEncontradaException, DatosReservaInvalidosException {

	    if (reserva == null) {
	        throw new ReservaNoEncontradaException("Reserva inválida");
	    }

	    // 1. validar alcohol
	    validarAlcoholReserva(reserva);

	    // 2. validar bebida caliente + acción
	    validarCalienteConAccionReserva(reserva);

	    // 3. validar juegos disponibles
	    for (Pedido ped : reserva.getPedidos()) {
	        for (Juego j : ped.getJuegos()) {
	            if (!inventarioVentas.estaDisponible(j)) {
	                throw new JuegoNoDisponibleException("Juego no disponible: " + j.getNombre());
	            }
	        }
	    }

	    int id = registroVentas.size() + 1;

	    // 🔥 IMPORTANTE: el usuario ahora es el CLIENTE de la reserva
	    Usuario usuario = reserva.getCliente();

	    CompraVenta compra = new CompraVenta(id, usuario, propina, reserva);

	    compra.calcularValores();

	    double total = compra.getTotal();

	    // descuento
	    total = aplicarDescuento(usuario, total, codigo);

	    // puntos SOLO cliente
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
	private void validarAlcoholReserva(Reserva reserva) 
	        throws DatosReservaInvalidosException {

	    boolean hayAlcohol = false;

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

	    if (hayAlcohol && reserva.isTieneNinos()) {
	        throw new DatosReservaInvalidosException();
	    }
	}
	private void validarCalienteConAccionReserva(Reserva reserva) 
	        throws BebidaCalienteConAccionException {

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

	    if (!hayCaliente) return;

	    // revisa TODOS los préstamos de la reserva
	    for (Prestamo pr : registroPrestamos.values()) {

	        if (pr.getReserva().equals(reserva) && !pr.isDevuelto()) {

	            if (pr.getJuego().getCategoria().equals("accion")) {
	                throw new BebidaCalienteConAccionException(
	                    "No puedes tener juegos de acción con bebidas calientes"
	                );
	            }
	        }
	    }
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
	

	public void solicitarPrestamo(Usuario usuario, Juego juego, Reserva reserva)
	        throws EmpleadoEnTurnoException,
	               JuegoNoDisponibleException,
	               LimitePrestamosException,
	               BebidaCalienteConAccionException,
	               RestriccionEdadException,
	               CapacidadJuegoException,
	               ReservaRequeridaException,
	               JuegoNoEncontradoException {

	    // 1. disponibilidad
	    if (!inventarioPrestamo.estaDisponible(juego)) {
	        throw new JuegoNoDisponibleException("El juego no está disponible");
	    }

	    // 2. cliente
	    if (usuario instanceof Cliente) {
	        if (reserva == null) {
	            throw new ReservaRequeridaException("El cliente necesita reserva");
	        }
	        validarPrestamoCliente((Cliente) usuario, juego, reserva);
	    }

	    // 3. empleado
	    if (usuario instanceof Empleado) {
	        validarPrestamoEmpleado((Empleado) usuario);
	    }

	    // 4. inventario
	    inventarioPrestamo.registrarPrestamo(juego);

	    // 5. crear préstamo
	    String id = "P" + (registroPrestamos.size() + 1);
	    Prestamo prestamo = new Prestamo(id, usuario, juego, reserva);

	    registroPrestamos.put(id, prestamo);
	}
	
	private void validarPrestamoEmpleado(Empleado usuario) throws EmpleadoEnTurnoException {
	    if (usuario.estaEnTurnoAhora()) {
	        throw new EmpleadoEnTurnoException("No puedes pedir préstamo en turno");
	    }
	}
	
	private void validarPrestamoCliente(Cliente cliente, Juego juego, Reserva reserva)
	        throws LimitePrestamosException,
	               BebidaCalienteConAccionException,
	               RestriccionEdadException,
	               CapacidadJuegoException {

	    int activos = 0;

	    for (Prestamo p : registroPrestamos.values()) {
	        if (p.getUsuario().equals(cliente) && !p.isDevuelto()) {
	            activos++;
	        }
	    }

	    if (activos >= 2) {
	        throw new LimitePrestamosException("Máximo 2 préstamos activos");
	    }

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
	        throw new BebidaCalienteConAccionException("No puedes usar juegos de acción con bebidas calientes");
	    }

	    if (reserva.isTieneNinos() && juego.getEdadMinima() > 0) {
	        throw new RestriccionEdadException("El juego no es apto para menores");
	    }

	    if (reserva.getCantidadPersonas() > juego.getMaxJugadores()) {
	        throw new CapacidadJuegoException("Se excede la capacidad del juego");
	    }
	}
	public ArrayList<Prestamo> getPrestamosClienteEnReserva(Cliente cliente, Reserva reserva) {
	    
	    ArrayList<Prestamo> lista = new ArrayList<>();

	    for (Prestamo p : registroPrestamos.values()) {
	        if (p.getUsuario().equals(cliente) 
	            && p.getReserva().equals(reserva) 
	            && !p.isDevuelto()) {

	            lista.add(p);
	        }
	    }

	    return lista;
	}
	public void devolverPrestamo(String idPrestamo) throws PrestamoNoEncontradoException, JuegoNoEncontradoException {

	    Prestamo p = registroPrestamos.get(idPrestamo);

	    if (p == null || p.isDevuelto()) {
	        throw new PrestamoNoEncontradoException("El préstamo no existe o ya fue devuelto");
	    }

	    p.setDevuelto(true);

	    inventarioPrestamo.registrarDevolucion(p.getJuego());
	}
	
	//Requerimiento de mesero tiene juego conocidos y
	
	public void anadirJuegoAMesero(String login, Juego juego) {

	    if (login == null || juego == null) {
	        return;
	    }

	    Empleado e = empleados.get(login);

	    if (e instanceof Mesero) {
	        Mesero m = (Mesero) e;
	        m.anadirJuegosConocidos(juego);
	       
	    }
	    
	    else {
	        System.out.println("El empleado no es un mesero.");
	    }
	}
	
	public Mesero hayMeseroParaJuego(Juego juego, String jornada) {

	    if (juego == null) {
	        return null;
	    }

	    for (Empleado e : empleados.values()) {
	        if (e instanceof Mesero) {
	            Mesero mesero = (Mesero) e;

	            if (mesero.getJuegosConocidos().contains(juego)) {
	            	if(mesero.estaEnTurnoAhora())
	                return mesero;
	            }
	        }
	    }

	    return null;
	}
	    

	public Reserva buscarReservaPorId(String id) throws ReservaNoEncontradaException {
	    if (id == null || id.isEmpty()) {
	        throw new ReservaNoEncontradaException("ID inválido");
	    }
	    Reserva r = reservas.get(id);
	    if (r == null) {
	        throw new ReservaNoEncontradaException("Reserva no encontrada");
	    }
	    return r;
	}

	public boolean crearJuego(Juego juego, String tipoInventario) {
		// TODO Auto-generated method stub
		return false;
	}

	public CompraVenta getFacturaPorReserva(Reserva reserva) {
	    for (CompraVenta c : registroVentas.values()) {
	        if (c.getReserva().equals(reserva)) {
	            return c;
	        }
	    }
	    return null;
	}
}

	
	

		