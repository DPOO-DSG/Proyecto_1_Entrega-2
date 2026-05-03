package presentacion;

import java.util.ArrayList;
import java.util.HashMap;

import excepciones.CopiasInsuficientesException;
import excepciones.JuegoNoEncontradoException;
import excepciones.TorneoYaExisteException;
import excepciones.TurnoNoExisteException;
import excepciones.UsuarioYaExisteException;
import logica.Administrador;
import logica.Bebida;
import logica.Cafe;
import logica.CambioDeTurno;
import logica.Cliente;
import logica.CompraVenta;
import logica.Empleado;
import logica.Juego;
import logica.Pasteleria;
import logica.Platillo;
import logica.Prestamo;
import logica.Torneo;
import logica.Turno;
import logica.Usuario;

public class AdministradorPrincipal extends Principal{
	public AdministradorPrincipal(Cafe cafe) {
		super(cafe);
        iniciar();
	}

		private void iniciar() {	
			loginAdmin();
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
		        System.out.println("1. Crear mesero");
		        System.out.println("2. Crear cocinero");
		        System.out.println("3. Ver turnos del cafe");
		        System.out.println("4. Ver solicitudes de cambio de turno");
		        System.out.println("5. Aprobar/Rechazar solicitudes");
		        System.out.println("6. Añadir platillo a menu");
		        System.out.println("7. Ver solicitudes de platillo");
		        System.out.println("8. Aprobar/rechazar solicitudes de platillo");
		        System.out.println("9. Consultar menu");
		        System.out.println("10.  Añadir juego ");
		        System.out.println("11. Gestionar inventario");
		        System.out.println("12. Ver historial de ventas");
		        System.out.println("13. Ver historial de prestamos");
		        System.out.println("14. Ver empleados");
		        System.out.println("15. Ver clientes");
		        System.out.println("16. Crear Torneo");
		        System.out.println("17.  Otorgar premio de torneo amistoso");
		        System.out.println("0. Salir");

		        option = sc.nextInt();
		        sc.nextLine();

		        if (option == 4) {
		            verSolicitudesTurno(); 
		        } else if (option == 5) {
		            gestionarSolicitudes(); 
		        } else if (option == 1) {
		            crearMesero(); 
		        } else if (option == 2) {
		            crearCocinero(); 
		        } else if (option == 12) {
		            verHistorialVentas();
		        } else if (option == 11) {
		            gestionarInventario(); 
		        }else if (option == 3) {
		            verTurnosAdmin();
		        }else if (option == 6) {
		            añadirPlatilloAmenu();
		        }else if (option == 7) {
		            verSolicitudesPlatillo(); 
		        }else if (option == 8) {
		            gestionarSolicitudesPlatillo();
		        }else if (option == 10) {
		            añadirJuego();
		        }else if (option == 13) {
		            historialPrestamos();
		        }else if (option == 9) {
		            verMenu();
		        }else if (option == 14) {
		        	verEmpleados();
		        }else if (option == 15) {
		        	verClientes();
		        }
		        else if (option == 16) {
		        	crearTorneo();
		        }
		        else if (option == 17) {
		        	otorgarPremio(); 
		        }
		        
		    } while (option != 0);
		}

		private void verMenu() {

		    ArrayList<Platillo> menu = cafe.consultarMenu();

		    if (menu.isEmpty()) {
		        System.out.println("No hay platillos disponibles en el menú");
		        return;
		    }

		    System.out.println("\n=== MENÚ DEL CAFÉ ===");

		    for (int i = 0; i < menu.size(); i++) {
		        System.out.println((i+1) + ". " + menu.get(i));
		    }
		}
		private void otorgarPremio() {
			System.out.println("\n=== TORNEOS ===");

		    HashMap<String, Torneo> torneos = cafe.getTorneos();

		    if (torneos.isEmpty()) {
		        System.out.println("No hay torneos registrados.");
		        return;
		    }

		    ArrayList<Torneo> listaTorneos = new ArrayList<>(torneos.values());

		    for (int i = 0; i < listaTorneos.size(); i++) {
		        System.out.println((i + 1) + ". " + listaTorneos.get(i).getNombre());
		    }

		    System.out.print("Escoja el torneo: ");
		    int numTorneo = Integer.parseInt(sc.nextLine());

		    if (numTorneo < 1 || numTorneo > listaTorneos.size()) {
		        System.out.println("Opción inválida.");
		        return;
		    }
	 
		    Torneo torneo = listaTorneos.get(numTorneo - 1);

		    System.out.println("\n=== PARTICIPANTES ===");

		    HashMap<Usuario, Integer> inscripciones = torneo.getInscripciones();

		    if (inscripciones.isEmpty()) {
		        System.out.println("No hay participantes.");
		        return;
		    }

		    ArrayList<Usuario> listaUsuarios = new ArrayList<>(inscripciones.keySet());

		    for (int i = 0; i < listaUsuarios.size(); i++) {
		        Usuario u = listaUsuarios.get(i);
		        int cupos = inscripciones.get(u);

		        System.out.println((i + 1) + ". " + u.getLogin() + " (" + cupos + " cupos)");
		    }

		    System.out.print("Seleccione el ganador: ");
		    int numGanador = Integer.parseInt(sc.nextLine());

		    if (numGanador < 1 || numGanador > listaUsuarios.size()) {
		        System.out.println("Opción inválida.");
		        return;
		    }

		    Usuario ganador = listaUsuarios.get(numGanador - 1);

		    torneo.otorgarPremio(ganador);

		    System.out.println("Premio otorgado a: " + ganador.getLogin());
	        
		}
		private void crearTorneo() {

		    try {
		        System.out.println("\n=== CREAR TORNEO ===");

		        System.out.println("1. Amistoso");
		        System.out.println("2. Competitivo");
		        System.out.print("Opción: ");
		        int tipo = Integer.parseInt(sc.nextLine());

		        if (tipo != 1 && tipo != 2) {
		            System.out.println("Opción inválida.");
		            return;
		        }

		        System.out.print("Nombre del torneo: ");
		        String nombre = sc.nextLine();

		        System.out.print("Nombre del juego: ");
		        String nombreJuego = sc.nextLine();

		        System.out.print("Número de participantes: ");
		        int cupos = Integer.parseInt(sc.nextLine());

		        System.out.print("Día del torneo: ");
		        String dia = sc.nextLine();

		        if (tipo == 1) {
		        	
		            cafe.crearTorneoAmistoso(nombre, nombreJuego, cupos, dia);
		            System.out.println("Torneo amistoso creado correctamente.");
		        } 
		        else {
		            System.out.print("Costo de entrada: ");
		            int costo = Integer.parseInt(sc.nextLine());

		            System.out.print("Premio: ");
		            int premio = Integer.parseInt(sc.nextLine());

		            cafe.crearTorneoCompetitivo(nombre, nombreJuego, cupos, dia, costo, premio);
		            System.out.println("Torneo competitivo creado correctamente.");
		        }

		    } 
		    catch (TorneoYaExisteException e) {
		        System.out.println("Error: " + e.getMessage());
		    } 
		    catch (JuegoNoEncontradoException e) {
		        System.out.println("No se creó el torneo: " + e.getMessage());
		    } 
		    catch (CopiasInsuficientesException e) {
		        System.out.println("Error: " + e.getMessage());
		    } 
		    catch (NumberFormatException e) {
		        System.out.println("Error: debes ingresar números válidos.");
		    }
		}
		private void verEmpleados() {

		    HashMap<String, Empleado> empleados = cafe.getEmpleados();

		    if (empleados.isEmpty()) {
		        System.out.println("No hay empleados registrados");
		        return;
		    }

		    System.out.println("=== EMPLEADOS ===");

		    for (Empleado e : empleados.values()) {
		        System.out.println("- " + e.getLogin());
		    }
		}
		
		private void verClientes() {
			HashMap<String, Cliente> clientes = cafe.getClientes();
			if (clientes.isEmpty()) {
				System.out.println("No hay clientes registrados");
				return;
			}
			System.out.println("=== CLIENTES ===");
			
			for(Cliente c : clientes.values()) {
				System.out.println("- " + c.getLogin());
			}
		}
			
		
		private void historialPrestamos() {

		    HashMap<String, Prestamo> prestamos = cafe.getRegistroPrestamos();
		    if (prestamos.isEmpty()) {
		        System.out.println("No hay préstamos registrados.");
		        return;
		    }
		    System.out.println("\n=== HISTORIAL DE PRÉSTAMOS ===");
		    for (Prestamo p : prestamos.values()) {
		        System.out.println(
		            "ID: " + p.getId() +
		            " | Usuario: " + p.getUsuario().getLogin() +
		            " | Juego: " + p.getJuego().getNombre() +
		            " | Estado: " + (p.isDevuelto() ? "Devuelto" : "Activo")
		        );
		    }
		}
		
		private void añadirJuego() {

		    try {
		        System.out.print("Nombre del juego: ");
		        String nombre = sc.nextLine();

		        System.out.print("Categoría (Tablero, Cartas o Accion): ");
		        String categoria = sc.nextLine();

		        System.out.print("Precio: ");
		        int precio = sc.nextInt();

		        System.out.print("Año de publicación: ");
		        int anio = sc.nextInt();

		        System.out.print("Cantidad inicial: ");
		        int cantidad = sc.nextInt();

		        System.out.print("Mínimo de jugadores: ");
		        int minJug = sc.nextInt();

		        System.out.print("Máximo de jugadores: ");
		        int maxJug = sc.nextInt();
		        sc.nextLine();

		        System.out.print("Empresa matriz: ");
		        String empresa = sc.nextLine();

		        System.out.print("Restricción de edad: ");
		        String restriccion = sc.nextLine();

		        System.out.print("¿Es difícil? (true/false): ");
		        boolean dificil = sc.nextBoolean();
		        sc.nextLine();
		        
		        
		        System.out.println("Elija el inventario donde va a agregar el juego");
		        System.out.println("1. Inventario de venta");
		        System.out.println("2. Inventario de préstamo");

		        int opcion = sc.nextInt();
		        sc.nextLine();
		        
		        String tipoInventario;

		        if (opcion == 1) {
		            tipoInventario = "VENTA";
		        } else if (opcion == 2) {
		            tipoInventario = "PRESTAMO";
		        } else {
		            System.out.println("Opción inválida");
		            return;
		        }
		        
		        boolean fueCreado = cafe.crearJuego(categoria, nombre, cantidad, precio, anio, empresa, minJug, maxJug, restriccion, dificil, tipoInventario);

		        if (fueCreado) {
		            if (tipoInventario.equals("VENTA")) {
		                System.out.println("Juego añadido a inventario de ventas");
		            } else {
		                System.out.println("Juego añadido a inventario de préstamos");
		            }
		        } else {
		            System.out.println("No se pudo añadir el juego");
		        }

		    } catch (Exception e) {
		        System.out.println("Error: " + e.getMessage());
		    }
		}
		private void gestionarSolicitudesPlatillo() {

		    System.out.print("Ingrese ID de la solicitud: ");
		    int id = sc.nextInt();
		    sc.nextLine();

		    System.out.println("1. Aprobar");
		    System.out.println("2. Rechazar");

		    int opcion = sc.nextInt();
		    sc.nextLine();

		    boolean resultado = false;

		    if (opcion == 1) {
		        resultado = cafe.aprobarSolicitudPlatillo(id);
		    } else if (opcion == 2) {
		        resultado = cafe.rechazarSolicitudPlatillo(id);
		    }

		    if (resultado) {
		        System.out.println("Solicitud realizada correctamente");
		    } else {
		        System.out.println("No se pudo realizar la solicitud");
		    }
		}
		private void verSolicitudesPlatillo() {

		    HashMap<Integer, Platillo> solicitudes = cafe.getSolicitudesPendientesPlatillos();

		    if (solicitudes.isEmpty()) {
		        System.out.println("No hay solicitudes de platillos pendientes");
		        return;
		    }

		    System.out.println("=== SOLICITUDES DE PLATILLO ===");

		    for (Integer id : solicitudes.keySet()) {
		        Platillo p = solicitudes.get(id);
		        System.out.println("ID: " + id + " | " + p);
		    }
		}
		private void añadirPlatilloAmenu() {
			try {
		        System.out.println("Tipo de platillo:");
		        System.out.println("1. Bebida");
		        System.out.println("2. Pastelería");

		        int tipo = sc.nextInt();
		        sc.nextLine();

		        System.out.print("Nombre: ");
		        String nombre = sc.nextLine();

		        System.out.print("Precio: ");
		        int precio = sc.nextInt();
		        sc.nextLine();

		        Platillo p = null;

		        // BEBIDA
		        if (tipo == 1) {

		            System.out.print("Tipo de bebida (fria/caliente): ");
		            String tipoBebida = sc.nextLine();

		            System.out.print("¿Es alcohólica? (true/false): ");
		            boolean alcohol = sc.nextBoolean();
		            sc.nextLine();

		            p = new Bebida(nombre, precio, tipoBebida, alcohol);
		        }

		        // PASTELERÍA
		        else if (tipo == 2) {

		            ArrayList<String> alergenos = new ArrayList<>();

		            System.out.print("¿Cuántos alérgenos?: ");
		            int n = sc.nextInt();
		            sc.nextLine();

		            for (int i = 0; i < n; i++) {
		                System.out.print("Alérgeno " + (i+1) + ": ");
		                alergenos.add(sc.nextLine());
		            }

		            p = new Pasteleria(nombre, precio, alergenos);
		        }

		        else {
		            System.out.println("Tipo inválido");
		            return;
		        }

		        boolean agregado = cafe.anadirAMenu(p);

		        if (agregado) {
		            System.out.println("Platillo añadido correctamente al menú ");
		        } else {
		            System.out.println("No se pudo añadir (posible duplicado) ");
		        }

		    } catch (Exception e) {
		        System.out.println("Error: " + e.getMessage());
		    }
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

		    int opcion;

		    do {
		        System.out.println("\n=== GESTIÓN INVENTARIO ===");
		        System.out.println("1. Ver inventario de préstamos");
		        System.out.println("2. Ver inventario de ventas");
		        System.out.println("3. Agregar stock a préstamo");
		        System.out.println("4. Agregar stock a venta");
		        System.out.println("0. Volver");

		        opcion = sc.nextInt();
		        sc.nextLine();

		        if (opcion == 1) {

		            HashMap<Juego, Integer> stock = cafe.getInventarioPrestamo().getStock();

		            for (Juego j : stock.keySet()) {
		                System.out.println(j.getNombre() + " -> " + stock.get(j));
		            }

		        } else if (opcion == 2) {

		            HashMap<Juego, Integer> stock = cafe.getInventarioVentas().getStock();

		            for (Juego j : stock.keySet()) {
		                System.out.println(j.getNombre() + " -> " + stock.get(j));
		            }

		        } else if (opcion == 3 || opcion == 4) {

		            System.out.print("Nombre del juego: ");
		            String nombre = sc.nextLine();

		            System.out.print("Cantidad a agregar: ");
		            int cantidad = sc.nextInt();
		            sc.nextLine();

		            Juego juegoEncontrado = null;

		            HashMap<Juego, Integer> stock = 
		                (opcion == 3) ? cafe.getInventarioPrestamo().getStock()
		                              : cafe.getInventarioVentas().getStock();

		            for (Juego j : stock.keySet()) {
		                if (j.getNombre().equalsIgnoreCase(nombre)) {
		                    juegoEncontrado = j;
		                    break;
		                }
		            }

		            if (juegoEncontrado == null) {
		                System.out.println("Juego no encontrado ");
		            } else {
		                int actual = stock.get(juegoEncontrado);
		                stock.put(juegoEncontrado, actual + cantidad);
		                System.out.println("Stock actualizado ");
		            }

		        }

		    } while (opcion != 0);
		}
		private void verHistorialVentas() {
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
		        if (s.getEmpleadoDestino() != null) {
		            System.out.println("ID: " + s.getId() + " | Intercambio: " +
		                s.getEmpleado().getLogin() + " <-> " + s.getEmpleadoDestino().getLogin() +" | " +
		                s.getTurnoOriginal().getJornada() +" <-> " +s.getTurnoCambio().getJornada());
		        }
		        else {

		            System.out.println(
		                "ID: " + s.getId() +
		                " | Cambio: " +
		                s.getEmpleado().getLogin() +
		                " | " +
		                s.getTurnoOriginal().getJornada() +
		                " -> " +
		                s.getTurnoCambio().getJornada()
		            );
		        }
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


	    
}
