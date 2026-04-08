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
	
    private int contadorSolicitudes = 0;


	
	public Cafe(int capacidad, int cantidadMesas) {
		this.capacidad = capacidad;
		this.registroVentas = new HashMap<>();
		this.registroPrestamos = new HashMap<>();
		this.inventarioVentas = new InventarioVenta();
		this.inventarioPrestamo = inventarioPrestamo; //TODO
		this.empleados = new HashMap<String, Empleado>();
		this.clientes = new HashMap<String, Cliente>();
		this.administradores = new HashMap<String, Administrador>();
		this.solicitudesCambioTurno = new HashMap<Integer, CambioDeTurno>();
		this.mesas =new HashMap<Integer,Mesa>();
	    this.credencialesAdmin = new HashMap<>();
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
	
	public boolean crearMesero(String login, String password, ArrayList<Juego> juegosFavoritos,
	        String codigoDescuento, Turno turno, ArrayList<Juego> juegosConocidos) {
	    if (existeLogin(login)) return false;
	    Mesero nuevo = new Mesero(login, password, juegosFavoritos, codigoDescuento, turno, juegosConocidos);
	    empleados.put(login, nuevo);
	    return true;
	}//Crea un nuevo mesero
	
	public boolean crearCocinero(String login, String password, ArrayList<Juego> juegosFavoritos,
	        String codigoDescuento, Turno turno) {
	    if (existeLogin(login)) return false;
	    Cocinero nuevo = new Cocinero(login, password, juegosFavoritos, codigoDescuento, turno);
	    empleados.put(login, nuevo);
	    return true;
	} //Crea un nuevo cocinero
	
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



//REQUERIMIENTO FUNCIONAL 1: CREAR SOLICITUD DE CAMBIO DE TURNO

//Agregar solicitud de cambio al mapa de solicitudes.
public boolean crearSolicitudCambio(Empleado empleado, Turno actual, Turno nuevo) {

	if (!puedeSalirDelTurno(empleado, actual)) {
        return false;
    }

    int id = generarIdSolicitud();

    CambioDeTurno solicitud = new CambioDeTurno(id, empleado, actual, nuevo);

    solicitudesCambioTurno.put(id, solicitud);

    return true;
    }

private boolean puedeSalirDelTurno(Empleado empleado, Turno actual) {
	// TODO Auto-generated method stub
	Turno turno = empleado.getTurno();
	int numCocineros = turno.getCocineros().size();
    int numMeseros = turno.getMeseros().size();

    if (empleado instanceof Cocinero) {
        return numCocineros > 2; // mínimo 2
    }

    if (empleado instanceof Mesero) {
        return numMeseros > 1; // mínimo 1
    }

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
public boolean crearFactura(Usuario usuario,
        ArrayList<Platillo> platillos,
        ArrayList<Juego> juegos,
        double propina,
        boolean usarPuntos,
        String codigo,
        Mesa mesa) {

// VALIDACIONES
if (!validarAlcohol(platillos, mesa)) return false;
if (!validarCalienteConAccion(platillos, usuario)) return false;

for (Juego j : juegos) {
if (!inventarioVentas.estaDisponible(j)) return false;
}

//  CREAR PEDIDO
Pedido pedido = new Pedido(usuario,platillos, juegos);

int id = registroVentas.size() + 1;

CompraVenta compra = new CompraVenta(id, usuario, pedido, propina);

// CALCULAR BASE
compra.calcularValores();

double total = compra.getTotal(); 

// DESCUENTO
total = aplicarDescuento(usuario, total, codigo);

// PUNTOS
if (usuario instanceof Cliente) {
Cliente c = (Cliente) usuario;

total = aplicarPuntos(c, total, usarPuntos);
asignarPuntos(c, total);
}

compra.setTotal(total);

//  INVENTARIO
for (Juego j : juegos) {
inventarioVentas.registrarVenta(j);
}

registroVentas.put(id, compra);

return true;
}
private boolean validarAlcohol(ArrayList<Platillo> platillos, Mesa mesa) {

    for (Platillo p : platillos) {
        if (p instanceof Bebida) {
            Bebida b = (Bebida) p;

            if (b.isAlcoholico() && mesa.tieneMenores()) {
                return false;
            }
        }
    }

    return true;
}
private boolean validarCalienteConAccion(ArrayList<Platillo> platillos, Usuario usuario) {

    boolean hayCaliente = false;

    for (Platillo p : platillos) {
        if (p instanceof Bebida) {
            Bebida b = (Bebida) p;

            if (b.getTipo().equals("caliente")) {
                hayCaliente = true;
                break;
            }
        }
    }

    if (!hayCaliente) return true;

    // revisar préstamos
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



//Requerimiento funcional asignar pedido a mesa


public boolean asignarPedidoaMesa(Mesa mesa, Usuario usuario) {

    if (mesa == null) {
        return false;
    }

    // mirar si ya tiene un pedido activo, pues si si no sepuede crear otro
    if (mesa.getPedido() != null) {
        System.out.println("La mesa ya tiene un pedido activo.");
        return false;
    }

    Pedido pedido = new Pedido(usuario, null, null);

    mesa.setPedido(pedido);

    return true;
}

//Caso en el que se quiere agregar algo al pedido

public void agregarPlatillo(Mesa mesa, Platillo platillo) {

    Pedido pedido = mesa.getPedido();

    if (pedido != null) {
        pedido.getPlatillos().add(platillo);
    }
}


//Requerimiento funcional gestion de inventario

public boolean prestamo (Juego juego, Mesa mesa) {  //Verificar si se puede prestar un juego y si si asignarlo a la mesa
	 
	//1era restriccion - no mas de dos juegos por mesa:

	int mesaId = mesa.getIdMesa();
	if (mesa.getJuegosPrestados().size() == 2) {
		System.out.println("Ya hay dos juegos en esta mesa, no se puede prestar mas");
		return false;
	}
	
	//2da restriccion - verificar diponibilidad de juego:
	
	if(!inventarioPrestamo.estaDisponible(juego)) {
		System.out.println("No hay disponibilidad de juego en el inventario");
		return false;
	}
	
	//3ra restriccion - verificar si hay bebidas calientes en la mesa y si el juego que piden es de accion
	Pedido pedido =  mesa.getPedido();
	
	if (pedido == null) {
	    System.out.println("La mesa no tiene un pedido activo.");
	    return false;
	}
	
	
    ArrayList<Platillo> platillos = pedido.getPlatillos();

    for (Platillo platillo : platillos) {
        if (platillo instanceof Bebida) {
            Bebida bebida = (Bebida) platillo;

            if (bebida.getTipo().equals("CALIENTE") && juego.esDeAccion()) {
                System.out.println("No se puede prestar " + juego + " porque hay una bebida caliente en la mesa");
                return false;
            }
        }
    }

	
	//4ta restriccion - verificar la edad de los jugadores puesto que ls juegos tienen edad.
		
	if(mesa.tieneMenores() == true && juego.getRestriccionEdad().equals("+18")) {
		System.out.println("No se puede presatr el juego puesto que hay miembros que no cumplen con la edad necesaria");
		return false;
	}
	
	//5ta restriccion - verificar capacidad de perosnas jugando
	
	int numPersonas = mesa.getCapacidad();
	if(numPersonas < juego.getMinJugadores() || numPersonas > juego.getMaxJugadores()) {
		System.out.println("Este juego no se puede presatr puesto que no cumplen con la cantidad de personas requeridas para jugarlo");
		return false; // TODO
	}
	
	// Si cumple con las restricciones se registra el pestramo
	boolean prestado = inventarioPrestamo.registrarPrestamo(juego);
	if(prestado) {
		mesa.getJuegosPrestados().add(juego);
		System.out.println("El juego fue prestado correctamente.");
		HashMap<Juego, ArrayList<LocalDateTime>> historial = inventarioPrestamo.getHistorial();
		
		LocalDateTime ahora = LocalDateTime.now();
	
		if (historial.containsKey(juego)) {
		    historial.get(juego).add(ahora);
		} else {
		    ArrayList<LocalDateTime> fechas = new ArrayList<>();
		    fechas.add(ahora);
		    historial.put(juego, fechas);
		    
		    return true;
		}
	
    return false;
	
}
}
}

	