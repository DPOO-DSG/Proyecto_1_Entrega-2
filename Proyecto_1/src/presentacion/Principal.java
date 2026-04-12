package presentacion;

import logica.*;
import java.util.*;

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
	        System.out.println("2. Consultar catálogo de juegos");
	        System.out.println("3. Solicitar préstamo de juego");
	        System.out.println("4. Ver pedidos");
	        System.out.println("5. Consultar puntos");
	        System.out.println("6. Guardar juego favorito");
	        System.out.println("0. Salir");

	        option = sc.nextInt();
	        sc.nextLine();

	        if (option == 1) {
	            reservarMesa(c); // TODO
	        } else if (option == 2) {
	            consultarCatalogo(); // TODO
	        } else if (option == 3) {
	            prestamoJuego(c); // TODO
	        } else if (option == 4) {
	            verPedidos(c); // TODO
	        } else if (option == 5) {
	            consultarPuntos(c); // TODO
	        } else if (option == 6) {
	            guardarJuegoFav(c); // TODO
	        }

	    } while (option != 0);
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
	        }

	    } while (option != 0);
	}
	
	private void guardarJuegoFav(Empleado e) {
		// TODO Auto-generated method stub
		
	}
	private void solicitarPlatillo(Empleado e) {
		// TODO Auto-generated method stub
		
	}
	private void hacerPedido(Empleado e) {
		// TODO Auto-generated method stub
		
	}
	private void prestamoJuego(Empleado e) {
		// TODO Auto-generated method stub
		
	}
	private void consultarJuegosFav(Empleado e) {
		// TODO Auto-generated method stub
		
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
	    System.out.print("Seleccione el índice del turno que quiere cambiar: ");
	    int indexOrigen = sc.nextInt();
	    sc.nextLine();
	    if (indexOrigen < 0 || indexOrigen >= turnosEmpleado.size()) {
	        System.out.println("Índice inválido.");
	        return;
	    }
	    Turno turnoOrigen = turnosEmpleado.get(indexOrigen);
	    String[] ordenDias = {
	        "lunes", "martes", "miercoles",
	        "jueves", "viernes", "sabado", "domingo"
	    };
	    ArrayList<Turno> todosTurnos = new ArrayList<>();
	    for (String dia : ordenDias) {
	        Turno t = cafe.getTurnos().get(dia);
	        if (t != null) {
	            todosTurnos.add(t);
	        }
	    }
	    System.out.println("=== TURNOS DISPONIBLES ===");
	    for (int i = 0; i < todosTurnos.size(); i++) {
	        System.out.println(i + ". " + todosTurnos.get(i).getJornada());
	    }
	    System.out.print("Seleccione el índice del turno al que quiere cambiar: ");
	    int indexDestino = sc.nextInt();
	    sc.nextLine();
	    if (indexDestino < 0 || indexDestino >= todosTurnos.size()) {
	        System.out.println("Índice inválido.");
	        return;
	    }
	    Turno turnoDestino = todosTurnos.get(indexDestino);
	    if (turnoOrigen.equals(turnoDestino)) {
	        System.out.println("No puedes cambiar al mismo turno.");
	        return;
	    }
	    boolean resultado = emp.solicitarCambioTurno(cafe, turnoOrigen, turnoDestino);
	    if (resultado) {
	        System.out.println("Solicitud de cambio enviada correctamente.");
	    } else {
	        System.out.println("No se pudo crear la solicitud.");
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
	    int resultado = cafe.crearMesero(login, pass, cod, new ArrayList<>(), dias, new ArrayList<>());
	    if (resultado == 0) {
	        System.out.println("Mesero creado correctamente");
	    } else if (resultado == 1) {
	        System.out.println("Ya existe ese login");
	    } else if (resultado == 2) {
	        System.out.println("Algún dia no existe");
	    }
	}

	private void crearCocinero() {

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
	    int resultado = cafe.crearCocinero(login,pass, cod, new ArrayList<>(),dias);
	    if (resultado == 0) {
	        System.out.println("Cocinero creado correctamente");
	    } else if (resultado == 1) {
	        System.out.println("Ya existe ese login");
	    } else if (resultado == 2) {
	        System.out.println("Algún día no existe");
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