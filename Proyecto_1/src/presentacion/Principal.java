package presentacion;

import logica.*;
import java.util.*;
import  excepciones.*;


public class Principal {
	private Cafe cafe;
	private Scanner sc;
	public Principal() {
		this.cafe = new Cafe(50,10);
		this.cafe.inicializarTurnos(); 
		this.sc = new Scanner (System.in);
		this.cafe.crearAdministrador("admin", "admin");
		this.menu();
		this.sc.close();
	}
	//MENU PRINCIPAL DE INGRESO
	private void menu() {
	    int option;
	    do {
	        System.out.println("\n=== SISTEMA CAFE ===");
	        System.out.println("1. Ingreso de cliente");
	        System.out.println("2. Ingreso de empleado");
	        System.out.println("3. Ingreso administrador");
	        System.out.println("4. Registrar cliente");
	        System.out.println("0. Salir");

	        option = sc.nextInt();
	        sc.nextLine();

	        if (option == 1) {
	            loginCliente();
	        } else if (option == 2) {
	            loginEmpleado();
	        } else if (option == 3) {
	            loginAdmin();
	        } else if (option == 4) {
	            registroCliente();
	        }

	    } while (option != 0);
	}

	//MENU DE CLIENTE
	private void loginCliente() {
	    System.out.print("Login: ");
	    String login = sc.nextLine();
	    System.out.print("Password: ");
	    String pass = sc.nextLine();

	    Object u = cafe.login(login, pass);

	    if (u instanceof Cliente) {
	        menuCliente((Cliente) u);
	    } else {
	        System.out.println("Credenciales inválidas");
	    }
	}
	private void menuCliente(Cliente c) {
	    int option;
	    do {
	        System.out.println("\n=== MENU CLIENTE ===");
	        System.out.println("1. Reservar mesa");
	        System.out.println("2. Consultar catálogo de juegos (prestamo)");
	        System.out.println("3. Solicitar préstamo de juego");
	        System.out.println("4. Ver pedidos");
	        System.out.println("5. Consultar puntos");
	        System.out.println("6. Guardar juego favorito");
	        System.out.println("7. Devolver juego prestado");
	        System.out.println("8. Consultar catálogo de juegos(ventas)");
	        //TODO ACEPTAR RESERVA (INDICAR QUE LLEGÓ)
	        System.out.println("0. Salir");

	        option = sc.nextInt();
	        sc.nextLine();

	        if (option == 1) {
	            reservarMesa(c); // TODO sebastian 
	        } else if (option == 2) {
	            consultarCatalogo(); // TODO daniel 
	        } else if (option == 3) {
	            prestamoJuego(c); // TODO
	        } else if (option == 4) {
	            verPedidos(c); // TODO
	        } else if (option == 5) {
	            consultarPuntos(c);
	        } else if (option == 6) {
	            guardarJuegoFav(c); 
	        }
	        else if (option == 7) {
	            devolverprestamo(c); // TODO
	        }
	        else if (option == 7) {
	            consultarcatalogoventas(c); // TODO
	        }

	    } while (option != 0);
	}
	
	private void consultarcatalogoventas(Cliente c) {
		System.out.println("===CATALOGO===");
		
		InventarioVenta inventario = cafe.getInventarioVentas();
		HashMap<Juego,Integer> stock = inventario.getStock();
		Set<Juego> juegos = stock.keySet();
		for (Juego juego: juegos) {
			System.out.println( juego.getNombre()+"\n");
			
		}

	}
	
	private void reservarMesa(Cliente c) {
		// TODO Auto-generated method stub		
	}
	
	private void consultarPuntos(Cliente c) {
		System.out.println("Cuentas con "+ c.consultarPuntosFidelidad(cafe)+ " puntos");
		
	}
	private void verPedidos(Cliente c) {
		// TODO sebastian
		System.out.println("TUS RESERVAS:\n");
		for() {
			

		 
		
	}
	
		
		
		
		
	}
	
	private void devolverprestamo(Cliente c) {

	    try {
	        // 1. pedir reserva (ejemplo simple)
	        System.out.println("Ingrese el id de la reserva:");
	        String idReserva = sc.nextLine();

	        Reserva reserva = cafe.buscarReservaPorId(idReserva); // necesitas este método

	        if (reserva == null) {
	            System.out.println("Reserva no encontrada");
	            return;
	        }

	        // 2. obtener préstamos
	        ArrayList<Prestamo> prestamos = cafe.getPrestamosClienteEnReserva(c, reserva);

	        if (prestamos.isEmpty()) {
	            System.out.println("No tienes préstamos activos en esta reserva");
	            return;
	        }

	        // 3. mostrar préstamos
	        System.out.println("Préstamos activos:");
	        for (Prestamo p : prestamos) {
	            System.out.println(p.getId() + " - " + p.getJuego().getNombre());
	        }

	        // 4. elegir cuál devolver
	        System.out.println("Ingrese el ID del préstamo a devolver:");
	        String idPrestamo = sc.nextLine();

	        // 5. devolver
	        cafe.devolverPrestamo(idPrestamo);

	        System.out.println("Juego devuelto correctamente");

	    } catch (PrestamoNoEncontradoException e) {
	        System.out.println(e.getMessage());

	    } catch (JuegoNoEncontradoException e) {
	        System.out.println(e.getMessage());

	    } catch (Exception e) {
	        System.out.println("Error inesperado: " + e.getMessage());
	    }
	}
	private void loginEmpleado() {
	    System.out.print("Login: ");
	    String login = sc.nextLine();
	    System.out.print("Password: ");
	    String pass = sc.nextLine();

	    Object u = cafe.login(login, pass);

	    if (u instanceof Empleado) {
	        menuEmpleado((Empleado) u);
	    } else {
	        System.out.println("Credenciales inválidas");
	    }
	}

	
	
	
	
	private void menuEmpleado(Empleado e) {
	    int option;
	    do {
	        System.out.println("\n=== MENU EMPLEADO ===");
	        System.out.println("1. Consultar turnos");
	        System.out.println("2. Consultar juegos favoritos");
	        System.out.println("3. Solicitar préstamo de juego");
	        System.out.println("4. Hacer pedido");
	        System.out.println("5. Solicitar platillo nuevo");
	        System.out.println("6. Solicitar cambio de turno");
	        System.out.println("7. Guardar juego favorito");
	        System.out.println("8. Devolver juego");
	        System.out.println("0. Salir");

	        option = sc.nextInt();
	        sc.nextLine();

	        if (option == 1) {
	            consultarTurnos(e); // TODO
	        } else if (option == 2) {
	            consultarJuegosFav(e); // TODO
	        } else if (option == 3) {
	            prestamoJuego(e); // TODO
	        } else if (option == 4) {
	            hacerPedido(e); // TODO
	        } else if (option == 5) {
	            solicitarPlatillo(e); // TODO
	        } else if (option == 6) {
	            pedirCambioTurno(e); // TODO
	        } else if (option == 7) {
	            guardarJuegoFav(e); // TODO
	        } else if (option == 8) {
	            devolverJuegoPrestado(e); // TODO
	        }
	        

	    } while (option != 0);
	}
	
	private void devolverJuegoPrestado(Empleado e) {
		// TODO Auto-generated method stub
		
		
	}
	private void guardarJuegoFav(Usuario u) {
		// TODO Auto-generated method stub
		System.out.println("Qué juego desea guardar como favorito?: ");
		ArrayList<Juego> juegosFav = u.getJuegosFavoritos();
		ArrayList<Juego> juegos = u.consultarCatalogoPrestamo(cafe);
		for (int i = 0; i < juegos.size(); i++) {
	        System.out.println((i + 1) + ". " + juegos.get(i));
	    }

	    try {
	        System.out.print("Seleccione un juego: ");
	        int seleccion = sc.nextInt(); 
	        
	        // 2. Validar que el número esté dentro del rango de la lista
	        if (seleccion > 0 && seleccion <= juegos.size()) {
	            Juego juegoElegido = juegos.get(seleccion - 1);
	            juegosFav.add(juegoElegido);
	            System.out.println("¡" + juegoElegido.getNombre() + " añadido a favoritos!");
	        } else {
	            System.out.println("Error: El número ingresado no está en la lista.");
	        }

	    } catch (Exception ex) {
	        System.out.println("Error: Por favor, ingrese un número válido.");
	        sc.nextLine(); 
	    }
		
	}
	
	private void solicitarPlatillo(Empleado e) {
		// TODO Auto-generated method stub
		
	}
	private void hacerPedido(Empleado e) {
		// TODO Auto-generated method stub
		
	}
	private void prestamoJuego(Usuario c) {
		System.out.println("Qué juego desea pedir?: ");
		ArrayList<Juego> juegos = c.consultarCatalogoPrestamo(cafe);
		for (int i = 0; i < juegos.size(); i++) {
	        System.out.println((i + 1) + ". " + juegos.get(i));
	    }

	    try {
	        System.out.print("Seleccione un juego: ");
	        int seleccion = sc.nextInt(); 
	        
	        // 2. Validar que el número esté dentro del rango de la lista
	        if (seleccion > 0 && seleccion <= juegos.size()) {
	            Juego juegoElegido = juegos.get(seleccion - 1);
	             boolean solicitud = c.solicitarPrestamo(cafe, juegoElegido, null);
	            if (!solicitud) {
		            System.out.println("No cumples los requisitos de préstamo. ");
	            }
	            else {
		            System.out.println("Juego "+ juegoElegido.getNombre()+ "añadido a tus prestamos ");

	            }
	        } else {
	            System.out.println("Error: El número ingresado no está en la lista.");
	        }

	    } catch (Exception ex) {
	        System.out.println("Error: Por favor, ingrese un número válido.");
	        sc.nextLine(); 
	    }
	}
	private void consultarJuegosFav(Empleado e) {
		// TODO Auto-generated method stub
		System.out.println("===TUS JUEGOS FAVORITOS ===");
		ArrayList<Juego> juegosFavoritos = e.getJuegosFavoritos();
		for (Juego juego: juegosFavoritos) {
			System.out.println(juego);
			
		}
				
	}
	private void pedirCambioTurno(Empleado emp) {

	    ArrayList<Turno> turnosEmpleado = emp.getTurnos();

	    if (turnosEmpleado.isEmpty()) {
	        System.out.println("No tienes turnos asignados.");
	        return;
	    }

	    System.out.println("=== TUS TURNOS ===");
	    for (int i = 0; i < turnosEmpleado.size(); i++) {
	        System.out.println(i + ". " + turnosEmpleado.get(i).getJornada());
	    }

	    System.out.print("Seleccione el turno que quiere cambiar: ");
	    int indexOrigen = sc.nextInt();
	    sc.nextLine();

	    if (indexOrigen < 0 || indexOrigen >= turnosEmpleado.size()) {
	        System.out.println("Indice invalido.");
	        return;
	    }

	    Turno turnoOrigen = turnosEmpleado.get(indexOrigen);

	    String[] dias = {"lunes","martes","miercoles","jueves","viernes","sabado","domingo"};
	    ArrayList<Turno> todosTurnos = new ArrayList<>();

	    for (String d : dias) {
	        Turno t = cafe.getTurnos().get(d);
	        if (t != null) {
	            todosTurnos.add(t);
	        }
	    }

	    System.out.println("=== TURNOS DISPONIBLES ===");
	    for (int i = 0; i < todosTurnos.size(); i++) {
	        System.out.println(i + ". " + todosTurnos.get(i).getJornada());
	    }

	    System.out.print("Seleccione el turno destino: ");
	    int indexDestino = sc.nextInt();
	    sc.nextLine();

	    if (indexDestino < 0 || indexDestino >= todosTurnos.size()) {
	        System.out.println("Índice inválido.");
	        return;
	    }

	    Turno turnoDestino = todosTurnos.get(indexDestino);

	    try {
	        emp.solicitarCambioTurno(cafe, turnoOrigen, turnoDestino);
	        System.out.println("Solicitud enviada correctamente ✅");

	    } catch (SolicitudInvalidaException e) {
	        System.out.println(e.getMessage());

	    } catch (NoPerteneceTurnoException e) {
	        System.out.println(e.getMessage());

	    } catch (PersonalInsuficienteException e) {
	        System.out.println(e.getMessage());
	    }
	}


	private void consultarTurnos(Empleado emp) {

	    ArrayList<Turno> turnos = cafe.consultarTurnosEmpleado(emp);
	    if (turnos.isEmpty()) {
	        System.out.println("No tiene turnos asignados");
	        return;
	    }
	    System.out.println("\n=== TURNOS DEL EMPLEADO ===");
	    for (Turno t : turnos) {
	        System.out.println("- " + t.mostrarSimple());

	    }
	}

  
	
	//MENU ADMINISTRADOR
	private void loginAdmin() {
	    System.out.print("Login: ");
	    String login = sc.nextLine();
	    System.out.print("Password: ");
	    String pass = sc.nextLine();

	    Object u = cafe.login(login, pass);

	    if (u instanceof Administrador) {
	        menuAdmin((Administrador) u);
	    } else {
	        System.out.println("Credenciales inválidas");
	    }
	}
	
	private void menuAdmin(Administrador a) {
	    int option;
	    do {
	        System.out.println("\n=== MENU ADMIN ===");
	        System.out.println("1. Ver solicitudes de cambio de turno");
	        System.out.println("2. Aprobar/Rechazar solicitudes");
	        System.out.println("3. Crear mesero");
	        System.out.println("4. Crear cocinero");
	        System.out.println("5. Ver historial de ventas");
	        System.out.println("6. Gestionar inventario");
	        System.out.println("7. Ver turnos del cafe");
	        System.out.println("0. Salir");

	        option = sc.nextInt();
	        sc.nextLine();

	        if (option == 1) {
	            verSolicitudesTurno(); 
	        } else if (option == 2) {
	            gestionarSolicitudes(); 
	        } else if (option == 3) {
	            crearMesero(); 
	        } else if (option == 4) {
	            crearCocinero(); 
	        } else if (option == 5) {
	            verHistorialVentas(); // TODO
	        } else if (option == 6) {
	            gestionarInventario(); // TODO
	        }else if (option == 7) {
	            verTurnosAdmin();
	        }

	    } while (option != 0);
	}


	private void verTurnosAdmin() {

	    System.out.println("\n=== TURNOS DEL CAFE ===");

	    HashMap<String, Turno> turnos = cafe.getTurnos();

	    if (turnos.isEmpty()) {
	        System.out.println("No hay turnos creados");
	        return;
	    }
	    String[] dias = {"lunes","martes","miercoles","jueves","viernes","sabado","domingo"};
	    for (String d : dias) {
	        Turno t = turnos.get(d);
	        if (t != null) {
	            System.out.println(t);
	        }
	    }

	  
	}
	private void gestionarSolicitudes() {
	    System.out.print("Ingrese el ID de la solicitud: ");
	    int id = sc.nextInt();
	    sc.nextLine();
	    System.out.println("1. Aprobar");
	    System.out.println("2. Rechazar");
	    int opcion = sc.nextInt();
	    sc.nextLine();
	    boolean resultado = false;
	    if (opcion == 1) {
	        resultado = cafe.aprobarSolicitud(id);
	    } else if (opcion == 2) {
	        resultado = cafe.rechazarSolicitud(id);
	    }
	    if (resultado) {
	        System.out.println("Operación realizada correctamente.");
	    } else {
	        System.out.println("No se pudo realizar la operación.");
	    }
	}
	private void gestionarInventario() {
		// TODO Auto-generated method stub
		
	}
	private void verHistorialVentas() {
		// TODO Auto-generated method stub
		HashMap<Integer,CompraVenta> ventas = cafe.getRegistroVentas();
		for (int id : ventas.keySet()) {
			CompraVenta factura = ventas.get(id);
			System.out.println(factura);//INFO FACTURA//
		}
		
	}
	private void verSolicitudesTurno() {

	    HashMap<Integer, CambioDeTurno> pendientes = cafe.getSolicitudesPendientes();

	    if (pendientes.isEmpty()) {
	        System.out.println("No hay solicitudes pendientes.");
	        return;
	    }

	    System.out.println("=== SOLICITUDES PENDIENTES ===");

	    for (CambioDeTurno s : pendientes.values()) {
	        System.out.println(
	            "ID: " + s.getId() +
	            " | Empleado: " + s.getEmpleado().getLogin() +
	            " | " + s.getTurnoOriginal().getJornada() +
	            " -> " + s.getTurnoCambio().getJornada()
	        );
	    }
	}


	private void crearMesero() {
	    System.out.print("Login: ");
	    String login = sc.nextLine();
	    System.out.print("Password: ");
	    String pass = sc.nextLine();
	    System.out.print("Código: ");
	    String cod = sc.nextLine();
	    System.out.print("Ingrese los días separados por coma (ej: lunes,martes): ");
	    String entrada = sc.nextLine();
	    String[] partes = entrada.split(",");
	    ArrayList<String> dias = new ArrayList<>();
	    for (String d : partes) {
	        dias.add(d.trim().toLowerCase());
	    }
	    try {
	        cafe.crearMesero(login, pass, cod, new ArrayList<>(), dias, new ArrayList<>());
	        System.out.println("Mesero creado correctamente");

	    } catch (UsuarioYaExisteException e) {
	        System.out.println(e.getMessage());

	    } catch (TurnoNoExisteException e) {
	        System.out.println(e.getMessage());
	    }

	}

	private void crearCocinero() {

	    System.out.print("Login: ");
	    String login = sc.nextLine();
	    System.out.print("Password: ");
	    String pass = sc.nextLine();
	    System.out.print("Codigo: ");
	    String cod = sc.nextLine();
	    System.out.print("Ingrese los días separados por coma (ej: lunes,martes): ");
	    String entrada = sc.nextLine();
	    String[] partes = entrada.split(",");
	    ArrayList<String> dias = new ArrayList<>();
	    for (String d : partes) {
	        dias.add(d.trim().toLowerCase());
	    }
	    try {
	        cafe.crearCocinero(login, pass, cod, new ArrayList<>(), dias);
	        System.out.println("Cocinero creado correctamente");

	    } catch (UsuarioYaExisteException e) {
	        System.out.println(e.getMessage());

	    } catch (TurnoNoExisteException e) {
	        System.out.println(e.getMessage());
	    }
	
	}


    private void registroCliente() {
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        if (cafe.crearCliente(login, pass, new ArrayList<>(), 0)) {
            System.out.println("Cliente creado");
        } else {
            System.out.println("Ya existe");
        }
    }
    
    
	public static void main(String[] args) {
		new Principal();
	}
	
	

    
}