package presentacion;

import logica.*;
import persistencia.PersistenciaCafe;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import  excepciones.*;


public class Principal {
	private Cafe cafe;
	private Scanner sc;
	public Principal(Cafe cafe) {
	    this.cafe = cafe;
	    this.sc = new Scanner(System.in);
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
	        }else if (option == 0) {
	            PersistenciaCafe.guardar(cafe);
	            System.out.println("Datos guardados correctamente");
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
	        System.out.println("2. Consultar puntos");
	        System.out.println("3. Ver pedidos");
	        System.out.println("4. Consultar facturas");
	        System.out.println("5. Consultar menu");
	        System.out.println("6. Solicitar préstamo de juego");
	        System.out.println("7. Consultar catálogo de juegos (prestamo)");
	        System.out.println("8. Guardar juego favorito");
	        System.out.println("9. Devolver juego prestado");
	        System.out.println("10. Consultar catálogo de juegos(ventas)");
	        System.out.println("11. Consultar juegos favoritos");
	        System.out.println("0. Salir");

	        option = sc.nextInt();
	        sc.nextLine();

	        if (option == 1) {
	            reservarMesa(c); 
	        } else if (option == 7) {
	            consultarCatalogo(); 
	        } else if (option == 6) {
	            prestamoJuego(c); 
	        } else if (option == 3) {
	            verPedidos(c); 
	        } else if (option == 2) {
	            consultarPuntos(c);
	        } else if (option == 8) {
	            guardarJuegoFav(c); 
	        }
	        else if (option == 9) {
	            devolverPrestamo(c); 
	        }
	        else if (option == 10) {
	            consultarcatalogoventas(c); 
	        }
	        else if (option == 4) {
	            verFactura(c); 
	        }
	        else if (option == 5) {
	            verMenu(); 
	        }
	        else if (option == 11) {
	        	consultarJuegosFav(c);
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
	private void verFactura(Cliente c) {
		ArrayList<Reserva> reservas = cafe.getReservasCliente(c);

	    if (reservas.isEmpty()) {
	        System.out.println("No tienes reservas");
	        return;
	    }

	    for (int i = 0; i < reservas.size(); i++) {
	        System.out.println((i+1) + ". ID: " + reservas.get(i).getId());
	    }

	    System.out.print("Seleccione reserva: ");
	    int index = sc.nextInt();
	    sc.nextLine();

	    Reserva r = reservas.get(index - 1);

	    CompraVenta factura = cafe.getFacturaPorReserva(r);

	    if (factura == null) {
	        System.out.println("Aún no se ha generado factura");
	    } else {
	        System.out.println(factura);
	    }
	}
	private void consultarCatalogo() {
		System.out.println("===CATALOGO===");
		
		InventarioPrestamo inventario = cafe.getInventarioPrestamo();
		HashMap<Juego,Integer> stock = inventario.getStock();
		Set<Juego> juegos = stock.keySet();
		for (Juego juego: juegos) {
			System.out.println( juego.getNombre()+"\n");
		}
		
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

	    try {
	        System.out.print("Cantidad de personas: ");
	        int cantidadPersonas = sc.nextInt();
	        sc.nextLine();

	        int capacidadMaxMesa = 0;

	        for (Mesa m : cafe.getMesas().values()) {
	            if (m.getCapacidad() > capacidadMaxMesa) {
	                capacidadMaxMesa = m.getCapacidad();
	            }
	        }

	        if (cantidadPersonas > capacidadMaxMesa) {
	            System.out.println("No se puede hacer la reserva: ninguna mesa soporta esa cantidad de personas.\n"
	            		+ "Reserva otra mesa");
	            return;
	        }

	        System.out.print("¿Hay niños? (true/false): ");
	        boolean ninos = sc.nextBoolean();

	        System.out.print("¿Hay jóvenes? (true/false): ");
	        boolean jovenes = sc.nextBoolean();
	        sc.nextLine();

	        LocalDateTime fecha = null;
	        while (fecha == null) {
	            try {
	                System.out.print("Fecha (dd/MM/yyyy HH:mm): ");
	                String fechaStr = sc.nextLine();

	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	                fecha = LocalDateTime.parse(fechaStr, formatter);

	            } catch (Exception e) {
	                System.out.println("Formato inválido. Intente de nuevo.");
	            }
	        }

	        cafe.agendarReserva(c, cantidadPersonas, ninos, jovenes, fecha);

	        System.out.println("Reserva creada correctamente ");

	    } catch (NoHayMesasDisponiblesException e) {
	        System.out.println("No hay mesas disponibles en esa fecha.");

	    } catch (DatosReservaInvalidosException e) {
	        System.out.println(e.getMessage());

	    } catch (Exception e) {
	        System.out.println("Error: formato inválido.");
	    }
	}
	
	private void consultarPuntos(Cliente c) {
		System.out.println("Cuentas con "+ c.consultarPuntosFidelidad(cafe)+ " puntos");
		
	}
	private void verPedidos(Cliente c) {
	    ArrayList<Reserva> reservas = cafe.getReservasCliente(c);
	    if (reservas.isEmpty()) {
	        System.out.println("No tienes reservas");
	        return;
	    }
	    for (Reserva r : reservas) {
	        System.out.println("Reserva ID: " + r.getId());
	        System.out.println("Fecha: " + r.getFechaReserva());

	        for (Pedido p : r.getPedidos()) {
	            System.out.println("Pedido: " + p);
	        }

	        System.out.println("-------------------");
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
	        }
	        
	        
	    } while (option != 0);
	}
	
	private void comprarJuegoEmpleado(Empleado e) {

	    try {
	        ArrayList<Juego> catalogo = cafe.consultarCatalogoVenta();

	        if (catalogo.isEmpty()) {
	            System.out.println("No hay juegos disponibles para venta");
	            return;
	        }

	        System.out.println("=== CATÁLOGO DE JUEGOS ===");
	        for (int i = 0; i < catalogo.size(); i++) {
	            System.out.println((i+1) + ". " + catalogo.get(i));
	        }

	        System.out.print("¿Cuántos juegos desea comprar?: ");
	        int n = sc.nextInt();
	        sc.nextLine();

	        ArrayList<Juego> juegos = new ArrayList<>();

	        for (int i = 0; i < n; i++) {
	            System.out.print("Seleccione juego #" + (i+1) + ": ");
	            int op = sc.nextInt();
	            sc.nextLine();

	            if (op < 1 || op > catalogo.size()) {
	                System.out.println("Selección inválida");
	                i--;
	                continue;
	            }

	            juegos.add(catalogo.get(op - 1));
	        }

	        System.out.print("Propina: ");
	        double propina = sc.nextDouble();
	        sc.nextLine();

	        CompraVenta factura = cafe.comprarJuegosEmpleado(e, juegos, propina);

	        System.out.println("Compra realizada correctamente");
	        System.out.println(factura);

	    } catch (Exception ex) {
	        System.out.println("Error: " + ex.getMessage());
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

	        cafe.crearFactura(c, propina, usarPuntos, codigo, r);

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
	        // 1. buscar cliente
	        System.out.print("Login del cliente: ");
	        String loginCliente = sc.nextLine();

	        Cliente c = cafe.getClientes().get(loginCliente);

	        if (c == null) {
	            System.out.println("Cliente no existe");
	            return;
	        }

	        // 2. mostrar reservas del cliente
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

	        // 3. elegir platillos
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

	        // 4. elegir juegos
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

	        // 5. crear pedido (AQUÍ salta la excepción si hay alcohol con niños)
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
	        }
	        else if (option == 7) {
	            verSolicitudesPlatillo(); 
	        }else if (option == 8) {
	            gestionarSolicitudesPlatillo();
	        }else if (option == 10) {
	            añadirJuego();
	        }
	        else if (option == 13) {
	            historialPrestamos();
	        }
	        else if (option == 9) {
	            verMenu();
	        }else if (option == 14) {
	        	verEmpleados();
	        }else if (option == 15) {
	        	verClientes();
	        }
	        
	    } while (option != 0);
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
	        
	        
	        // Elegir inventario
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
	            System.out.println(
	                "ID: " + s.getId() +
	                " | Intercambio: " +
	                s.getEmpleado().getLogin() + " <-> " +
	                s.getEmpleadoDestino().getLogin() +
	                " | " +
	                s.getTurnoOriginal().getJornada() +
	                " <-> " +
	                s.getTurnoCambio().getJornada()
	            );
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
        Cafe cafe = PersistenciaCafe.cargar();

        if (cafe == null) {
            cafe = new Cafe(50, 10);
            cafe.inicializarTurnos();
            cafe.inicializarMesas(10, 4);
        }
        if (cafe.getAdministradores().isEmpty()) {
            cafe.crearAdministrador("admin", "admin");
        }

        new Principal(cafe);
    }
	
	

    
}