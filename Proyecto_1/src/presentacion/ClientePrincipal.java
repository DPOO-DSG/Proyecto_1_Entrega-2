package presentacion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import excepciones.*;
import logica.*;
import persistencia.PersistenciaCafe;

public class ClientePrincipal extends Principal {
	
	 public ClientePrincipal(Cafe cafe) {
		    super(cafe);
		    iniciar();
	    }

	    private void iniciar() {
	        Cliente c = login();
	        if (c != null) {
	            menuCliente(c);
	        }
	    }

	    private Cliente login() {
	        System.out.print("Login: ");
	        String login = sc.nextLine();

	        System.out.print("Password: ");
	        String pass = sc.nextLine();

	        Object u = cafe.login(login, pass);

	        if (u instanceof Cliente) {
	            return (Cliente) u;
	        }

	        System.out.println("Credenciales inválidas");
	        return null;
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
		        System.out.println("12. Inscribirse a torneo");
		        System.out.println("13. Borrar inscripción a torneo");

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
		        else if (option == 12) {
		        	inscribirTorneo(c);
		        }
		        else if (option == 13) {
		        	eliminarInscripcion(c);
		        }
		    } while (option != 0);
	    }
	    
	    
	    
	    private void prestamoJuego(Cliente u) {

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
		        cafe.inscribirATorneo(torneo.getNombre(), u, cantidad);
		        System.out.println("Inscripción exitosa.");
		    } catch (Exception e) {
		        System.out.println("Error: " + e.getMessage());
		    }
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
	    public static void main(String[] args) {
	        Cafe cafe = PersistenciaCafe.cargar();
	        if (cafe == null) {
	            cafe = new Cafe(50, 10);
	            cafe.inicializarTurnos();
	            cafe.inicializarMesas(10, 4);
	        }

	        new ClientePrincipal(cafe);
	        PersistenciaCafe.guardar(cafe);
	    }
}
