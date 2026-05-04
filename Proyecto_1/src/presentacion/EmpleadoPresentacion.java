package presentacion;

import java.util.ArrayList;
import java.util.HashMap;
import excepciones.*;
import logica.*;
import persistencia.PersistenciaCafe;

public class EmpleadoPresentacion extends Principal{
	
	

	public EmpleadoPresentacion(Cafe cafe) {
		super(cafe);
        iniciar();
	}
	

    private void iniciar() {
        loginEmpleado();
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
	        System.out.println("2. Solicitar cambio de turno");
	        System.out.println("3. Hacer pedido");
	        System.out.println("4. Crear factura");
	        System.out.println("5. Solicitar platillo nuevo");
	        System.out.println("6. Solicitar préstamo de juego");
	        System.out.println("7. Devolver juego");
	        System.out.println("8. Guardar juego favorito");
	        System.out.println("9. Consultar juegos favoritos");
	        System.out.println("10. Aprender juego dificil");
	        System.out.println("11. Comprar Juego");
	        System.out.println("12. Inscribir a Torneo");
	        System.out.println("13. Eliminar Inscripción");

	        System.out.println("0. Salir");

	        option = sc.nextInt();
	        sc.nextLine();

	        if (option == 1) {
	            consultarTurnos(e); 
	        } else if (option == 9) {
	            consultarJuegosFav(e); 
	        } else if (option == 6) {
	            prestamoJuego(e); 
	        } else if (option == 3) {
	            hacerPedido(e); 
	        } else if (option == 5) {
	            solicitarPlatilloEmpleado(); 
	        } else if (option == 2) {
	            pedirCambioTurno(e); 
	        } else if (option == 8) {
	            guardarJuegoFav(e); 
	        } else if (option == 7) {
	        	devolverPrestamo(e); 
	        }else if (option == 4) {
	            crearFactura(e); 
	        }else if (option == 10) {
	            aprenderJuegoDificil(e); 
	        }else if (option == 11) {
	            comprarJuegoEmpleado(e); 
	        }else if (option == 12) {
	            inscribirTorneo(e); 
	        }else if (option == 13) {
	            eliminarInscripcion(e); 
	        }
	        
	        
	    } while (option != 0);
	}
	 private void eliminarInscripcion(Usuario u) {
			System.out.println("\n=== TORNEOS EN LOS QUE ESTÁS INSCRITO===");

		    HashMap<String, Torneo> torneos = cafe.getTorneos();

		    if (torneos.isEmpty()) {
		        System.out.println("No hay torneos disponibles.");
		        return;
		    }

		    ArrayList<Torneo> lista = new ArrayList<>(torneos.values());

		    for (int i = 0; i < lista.size(); i++) {
		        System.out.println((i + 1) + ". " + lista.get(i).getNombre());
		    }

		    System.out.print("Seleccione el torneo: ");
		    int opcion = Integer.parseInt(sc.nextLine());

		    if (opcion < 1 || opcion > lista.size()) {
		        System.out.println("Opción inválida.");
		        return;
		    } 

		    Torneo torneo = lista.get(opcion - 1);
		    try {
		        cafe.eliminarDeTorneo(torneo.getNombre(), u);
		        System.out.println("Eliminación exitosa.");
		    } catch (Exception e) {
		        System.out.println("Error: " + e.getMessage());
		    }
		    
			
		}
	private void inscribirTorneo(Usuario u) {

	    System.out.println("\n=== TORNEOS ===");

	    HashMap<String, Torneo> torneos = cafe.getTorneos();

	    if (torneos.isEmpty()) {
	        System.out.println("No hay torneos disponibles.");
	        return;
	    }

	    ArrayList<Torneo> lista = new ArrayList<>(torneos.values());

	    for (int i = 0; i < lista.size(); i++) {
	        System.out.println((i + 1) + ". " + lista.get(i).getNombre());
	    }

	    System.out.print("Seleccione el torneo: ");
	    int opcion = Integer.parseInt(sc.nextLine());

	    if (opcion < 1 || opcion > lista.size()) {
	        System.out.println("Opción inválida.");
	        return;
	    }

	    Torneo torneo = lista.get(opcion - 1);

	    System.out.print("Cantidad de cupos: ");
	    int cantidad = Integer.parseInt(sc.nextLine());

	    try {
	        CompraVenta factura = cafe.inscribirATorneo(torneo.getNombre(), u, cantidad);

	        if (factura != null) {
	            System.out.println("Inscripción exitosa.");
	            System.out.println("Factura generada. Total: $" + factura.getTotal());
	        } else {
	            System.out.println("Inscripción exitosa (entrada gratuita).");
	        }

	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}
	private void consultarJuegosFav(Usuario c) {
	    ArrayList<Juego> juegosFavoritos = c.getJuegosFavoritos();
	    if (juegosFavoritos == null || juegosFavoritos.isEmpty()) {
	        System.out.println("No tienes juegos favoritos guardados.");
	        return;
	    }
	    System.out.println("=== TUS JUEGOS FAVORITOS ===");

	    for (Juego juego : juegosFavoritos) {
	        System.out.println(juego);
	    }
	}
	private void devolverPrestamo(Usuario u) {

	    try {
	        ArrayList<Prestamo> prestamosUsuario = new ArrayList<>();

	        if (u instanceof Cliente) {

	            Cliente c = (Cliente) u;
	            ArrayList<Reserva> reservas = cafe.getReservasCliente(c);

	            for (Reserva r : reservas) {
	                prestamosUsuario.addAll(
	                    cafe.getPrestamosClienteEnReserva(c, r)
	                );
	            }
	        }

	        else if (u instanceof Empleado) {

	            for (Prestamo p : cafe.getRegistroPrestamos().values()) {
	                if (p.getUsuario().equals(u) && !p.isDevuelto()) {
	                    prestamosUsuario.add(p);
	                }
	            }
	        }

	        if (prestamosUsuario.isEmpty()) {
	            System.out.println("No tienes prestamos");
	            return;
	        }
	        System.out.println("=== TUS PRÉSTAMOS ===");
	        for (int i = 0; i < prestamosUsuario.size(); i++) {
	            Prestamo p = prestamosUsuario.get(i);
	            System.out.println((i+1) + ". ID: " + p.getId() +
	                               " | Juego: " + p.getJuego().getNombre());
	        }

	        System.out.print("Seleccione préstamo a devolver: ");
	        int seleccion = sc.nextInt();
	        sc.nextLine();

	        if (seleccion < 1 || seleccion > prestamosUsuario.size()) {
	            System.out.println("Selección inválida.");
	            return;
	        }

	        Prestamo p = prestamosUsuario.get(seleccion - 1);

	        cafe.devolverPrestamo(p.getId());

	        System.out.println("Préstamo devuelto correctamente ");

	    } catch (PrestamoNoEncontradoException | JuegoNoEncontradoException e) {
	        System.out.println(e.getMessage());

	    } catch (Exception e) {
	        System.out.println("Error en la entrada.");
	        sc.nextLine();
	    }
	    
	}
	private void comprarJuegoEmpleado(Empleado empleado) {
	    System.out.println("\n=== COMPRAR JUEGO (EMPLEADO) ===");
	    
	    HashMap<Juego, Integer> stock = cafe.getInventarioVentas().getStock();
	    
	    if (stock == null || stock.isEmpty()) {
	        System.out.println("No hay juegos registrados en el inventario de ventas.");
	        return;
	    }
	    
	    ArrayList<Juego> listaJuegos = new ArrayList<>(stock.keySet());
	    for (int i = 0; i < listaJuegos.size(); i++) {
	        Juego j = listaJuegos.get(i);
	        System.out.println((i + 1) + ". " + j.getNombre() + " - $" + j.getprecio() + " (Unidades: " + stock.get(j) + ")");
	    }
	    
	    System.out.print("\nSeleccione el número del juego que desea comprar (0 para cancelar): ");
	    int index = sc.nextInt();
	    sc.nextLine();
	    
	    if (index == 0) return; 
	    
	    if (index < 1 || index > listaJuegos.size()) {
	        System.out.println("Selección inválida.");
	        return;
	    }
	    
	    Juego juegoSeleccionado = listaJuegos.get(index - 1);
	    
	    System.out.print("Ingrese la propina (si no desea dar, ingrese 0): ");
	    double propina = sc.nextDouble();
	    sc.nextLine();
	    
	    try {
	        cafe.comprarJuegoEmpleado(empleado, juegoSeleccionado, propina);
	        System.out.println("¡Compra realizada con éxito!");
	        
	    } catch (JuegoNoDisponibleException | JuegoNoEncontradoException e) {
	        System.out.println("Error en la compra: " + e.getMessage());
	    }
	}
		
	private void aprenderJuegoDificil(Empleado e) {

	    ArrayList<Juego> juegos = cafe.consultarCatalogoPrestamo();

	    ArrayList<Juego> juegosDificiles = new ArrayList<>();

	    for (Juego j : juegos) {
	        if (j.isDificl()) {
	            juegosDificiles.add(j);
	        }
	    }

	    if (juegosDificiles.isEmpty()) {
	        System.out.println("No hay juegos difíciles disponibles para aprender");
	        return;
	    }

	    System.out.println("=== JUEGOS DIFÍCILES ===");

	    for (int i = 0; i < juegosDificiles.size(); i++) {
	        System.out.println((i+1) + ". " + juegosDificiles.get(i));
	    }

	    System.out.print("Seleccione un juego: ");
	    int op = sc.nextInt();
	    sc.nextLine();

	    if (op < 1 || op > juegosDificiles.size()) {
	        System.out.println("Opción inválida");
	        return;
	    }

	    Juego juego = juegosDificiles.get(op - 1);

	    cafe.anadirJuegoAMesero(e.getLogin(), juego);
	    
	    System.out.println("Ahora sabes explicar este juego ");
	}
	private void crearFactura(Empleado e) {
	    try {
	        System.out.print("Login del cliente: ");
	        String loginCliente = sc.nextLine();

	        Cliente c = cafe.getClientes().get(loginCliente);

	        if (c == null) {
	            System.out.println("Cliente no existe");
	            return;
	        }

	        ArrayList<Reserva> reservas = cafe.getReservasCliente(c);

	        if (reservas.isEmpty()) {
	            System.out.println("No tiene reservas");
	            return;
	        }

	        for (int i = 0; i < reservas.size(); i++) {
	            System.out.println((i+1) + ". ID: " + reservas.get(i).getId());
	        }

	        System.out.print("Seleccione reserva: ");
	        int index = sc.nextInt();
	        sc.nextLine();

	        if (index < 1 || index > reservas.size()) {
	            System.out.println("Selección inválida");
	            return;
	        }

	        Reserva r = reservas.get(index - 1);

	        System.out.print("Propina: ");
	        double propina = sc.nextDouble();

	        System.out.print("¿Usar puntos? (true/false): ");
	        boolean usarPuntos = sc.nextBoolean();
	        sc.nextLine();

	        System.out.print("Código descuento: ");
	        String codigo = sc.nextLine();
	        
	        System.out.print("Desea usar bonos de descuento de torneo?: ");
	        boolean bonoTorneo = sc.nextBoolean();

	        cafe.crearFactura(c, propina, usarPuntos,bonoTorneo, codigo, r);

	        System.out.println("Factura generada correctamente");

	    } 
	    catch (DatosFacturaInvalidosException ex) {
	        System.out.println("Error de datos: " + ex.getMessage());
	    } 
	    catch (AlcoholReservaException ex) {
	        System.out.println("Restricción: " + ex.getMessage());
	    } 
	    catch (BebidaCalienteConAccionException ex) {
	        System.out.println("Restricción: " + ex.getMessage());
	    } 
	    catch (JuegoNoDisponibleException ex) {
	        System.out.println("Inventario: " + ex.getMessage());
	    } 
	    catch (Exception ex) {
	        System.out.println("Error inesperado: " + ex.getMessage());
	    }
	}
	
	private void guardarJuegoFav(Usuario u) {
		
		System.out.println("Qué juego desea guardar como favorito?: ");
		ArrayList<Juego> juegosFav = u.getJuegosFavoritos();
		ArrayList<Juego> juegos = u.consultarCatalogoPrestamo(cafe);
		for (int i = 0; i < juegos.size(); i++) {
	        System.out.println((i + 1) + ". " + juegos.get(i));
	    }

	    try {
	        System.out.print("Seleccione un juego: ");
	        int seleccion = sc.nextInt(); 
	        
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
	
	private void solicitarPlatilloEmpleado() {

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

	        cafe.crearSolicitudSugerencia(p);

	        System.out.println("Solicitud enviada correctamente");

	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}
	
	private void hacerPedido(Empleado e) {

	    try {
	        System.out.print("Login del cliente: ");
	        String loginCliente = sc.nextLine();

	        Cliente c = cafe.getClientes().get(loginCliente);

	        if (c == null) {
	            System.out.println("Cliente no existe");
	            return;
	        }

	        ArrayList<Reserva> reservas = cafe.getReservasCliente(c);

	        if (reservas.isEmpty()) {
	            System.out.println("El cliente no tiene reservas");
	            return;
	        }

	        System.out.println("=== RESERVAS ===");
	        for (int i = 0; i < reservas.size(); i++) {
	            System.out.println((i+1) + ". ID: " + reservas.get(i).getId());
	        }

	        System.out.print("Seleccione reserva: ");
	        int rIndex = sc.nextInt();
	        sc.nextLine();

	        if (rIndex < 1 || rIndex > reservas.size()) {
	            System.out.println("Selección inválida");
	            return;
	        }

	        Reserva reserva = reservas.get(rIndex - 1);

	        ArrayList<Platillo> platillos = new ArrayList<>();
	        ArrayList<Platillo> menu = cafe.getMenu();

	        if (menu.isEmpty()) {
	            System.out.println("No hay platillos en el menú");
	            return;
	        }

	        System.out.println("=== MENÚ ===");
	        for (int i = 0; i < menu.size(); i++) {
	            System.out.println((i+1) + ". " + menu.get(i));
	        }

	        System.out.print("¿Cuántos platillos desea agregar?: ");
	        int nPlatillos1 = sc.nextInt();
	        sc.nextLine();

	        for (int i = 0; i < nPlatillos1; i++) {

	            System.out.print("Seleccione platillo #" + (i+1) + ": ");
	            int seleccion = sc.nextInt();
	            sc.nextLine();

	            if (seleccion < 1 || seleccion > menu.size()) {
	                System.out.println("Selección inválida");
	                i--;
	                continue;
	            }

	            platillos.add(menu.get(seleccion - 1));
	        }

	        ArrayList<Juego> juegos = new ArrayList<>();
	        ArrayList<Juego> catalogo = cafe.consultarCatalogoVenta();

	        System.out.println("=== CATÁLOGO DE JUEGOS ===");
	        for (int i = 0; i < catalogo.size(); i++) {
	            System.out.println((i+1) + ". " + catalogo.get(i));
	        }

	        System.out.print("¿Cuántos juegos?: ");
	        int nJuegos = sc.nextInt();
	        sc.nextLine();

	        for (int i = 0; i < nJuegos; i++) {

	            System.out.print("Seleccione juego #" + (i+1) + ": ");
	            int seleccion = sc.nextInt();
	            sc.nextLine();

	            if (seleccion < 1 || seleccion > catalogo.size()) {
	                System.out.println("Selección inválida");
	                i--;
	                continue;
	            }

	            juegos.add(catalogo.get(seleccion - 1));
	        }

	        cafe.crearPedido(reserva, e, platillos, juegos);

	        System.out.println("Pedido creado correctamente");

	    } catch (AlcoholReservaException ex) {
	        System.out.println("Error: " + ex.getMessage());

	    } catch (Exception ex) {
	        System.out.println("Error inesperado: " + ex.getMessage());
	    }
	}

	
	private void prestamoJuego(Usuario u) {

	    try {
	        ArrayList<Juego> juegos = cafe.consultarCatalogoPrestamo();

	        if (juegos.isEmpty()) {
	            System.out.println("No hay juegos disponibles para préstamo.");
	            return;
	        }

	        System.out.println("=== CATÁLOGO ===");
	        for (int i = 0; i < juegos.size(); i++) {
	            System.out.println((i + 1) + ". " + juegos.get(i));
	        }

	        System.out.print("Seleccione un juego: ");
	        int seleccion = sc.nextInt();
	        sc.nextLine();

	        if (seleccion < 1 || seleccion > juegos.size()) {
	            System.out.println("Selección inválida.");
	            return;
	        }

	        Juego juegoElegido = juegos.get(seleccion - 1);

	        Reserva reservaSeleccionada = null;

	        if (u instanceof Cliente) {

	            Cliente c = (Cliente) u;
	            ArrayList<Reserva> reservas = cafe.getReservasActivasCliente(c);

	            if (reservas.isEmpty()) {
	                System.out.println("No tienes reservas activas.");
	                return;
	            }

	            System.out.println("=== TUS RESERVAS ===");
	            for (int i = 0; i < reservas.size(); i++) {
	                System.out.println((i+1) + ". " + reservas.get(i).getId());
	            }

	            System.out.print("Seleccione reserva: ");
	            int index = sc.nextInt();
	            sc.nextLine();

	            if (index < 1 || index > reservas.size()) {
	                System.out.println("Selección inválida.");
	                return;
	            }

	            reservaSeleccionada = reservas.get(index - 1);
	        }

	        u.solicitarPrestamo(cafe, juegoElegido, reservaSeleccionada);

	        System.out.println("Préstamo realizado correctamente");

	    } catch (EmpleadoEnTurnoException |
	             JuegoNoDisponibleException |
	             LimitePrestamosException |
	             BebidaCalienteConAccionException |
	             RestriccionEdadException |
	             CapacidadJuegoException |
	             ReservaRequeridaException |
	             JuegoNoEncontradoException e) {

	        System.out.println(e.getMessage());

	    } catch (Exception ex) {
	        System.out.println("Error: ingrese un número válido.");
	        sc.nextLine();
	    }
	}
	
				
	private void pedirCambioTurno(Empleado emp) {

	    System.out.println("\n=== SOLICITUD DE CAMBIO DE TURNO ===");
	    System.out.println("1. Cambio de turno (mismo empleado)");
	    System.out.println("2. Intercambio con otro empleado");

	    int opcion = sc.nextInt();
	    sc.nextLine();

	    ArrayList<Turno> turnosEmpleado = emp.getTurnos();

	    if (turnosEmpleado.isEmpty()) {
	        System.out.println("No tienes turnos asignados.");
	        return;
	    }

	    System.out.println("=== TUS TURNOS ===");
	    for (int i = 0; i < turnosEmpleado.size(); i++) {
	        System.out.println(i + ". " + turnosEmpleado.get(i).getJornada());
	    }

	    System.out.print("Seleccione el turno origen: ");
	    int indexOrigen = sc.nextInt();
	    sc.nextLine();

	    if (indexOrigen < 0 || indexOrigen >= turnosEmpleado.size()) {
	        System.out.println("Índice inválido.");
	        return;
	    }

	    Turno turnoOrigen = turnosEmpleado.get(indexOrigen);
	    //CAMBIO NORMAL
	    if (opcion == 1) {

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

	        System.out.print("Seleccione turno destino: ");
	        int indexDestino = sc.nextInt();
	        sc.nextLine();

	        if (indexDestino < 0 || indexDestino >= todosTurnos.size()) {
	            System.out.println("Índice inválido.");
	            return;
	        }

	        Turno turnoDestino = todosTurnos.get(indexDestino);

	        try {
	            emp.solicitarCambioTurno(cafe, turnoOrigen, turnoDestino);
	            System.out.println("Solicitud enviada correctamente");

	        } catch (SolicitudInvalidaException |
	                 NoPerteneceTurnoException |
	                 PersonalInsuficienteException e) {

	            System.out.println(e.getMessage());
	        } catch (NoPuedeSalirTurnoException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	    //INTERCAMBIO
	    else if (opcion == 2) {

	        System.out.print("Login del otro empleado: ");
	        String login = sc.nextLine();

	        Empleado otro = cafe.getEmpleados().get(login);

	        if (otro == null) {
	            System.out.println("Empleado no encontrado.");
	            return;
	        }

	        ArrayList<Turno> turnosOtro = otro.getTurnos();

	        if (turnosOtro.isEmpty()) {
	            System.out.println("El otro empleado no tiene turnos.");
	            return;
	        }

	        System.out.println("=== TURNOS DEL OTRO EMPLEADO ===");
	        for (int i = 0; i < turnosOtro.size(); i++) {
	            System.out.println(i + ". " + turnosOtro.get(i).getJornada());
	        }

	        System.out.print("Seleccione el turno del otro empleado: ");
	        int indexOtro = sc.nextInt();
	        sc.nextLine();

	        if (indexOtro < 0 || indexOtro >= turnosOtro.size()) {
	            System.out.println("Índice inválido.");
	            return;
	        }

	        Turno turnoOtro = turnosOtro.get(indexOtro);

	        try {
	            cafe.crearSolicitudIntercambio(emp, otro, turnoOrigen, turnoOtro);
	            System.out.println("Solicitud de intercambio enviada");

	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	    else {
	        System.out.println("Opción inválida.");
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
	
	public static void main(String[] args) {
        Cafe cafe = PersistenciaCafe.cargar();

        if (cafe == null) {
            cafe = new Cafe(50, 10);
            cafe.inicializarTurnos();
            cafe.inicializarMesas(10, 4);
        }

        new EmpleadoPresentacion(cafe);

        PersistenciaCafe.guardar(cafe);
    }

}
