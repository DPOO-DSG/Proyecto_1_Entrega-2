package presentacion;

import logica.*;
import java.util.*;

public class Principal {
	private Cafe cafe;
	private Scanner sc;
	public Principal() {
		this.cafe = new Cafe(50,10);
		this.sc = new Scanner (System.in);
		this.cafe.crearAdministrador("admin", "admin");
		this.menu();
		this.sc.close();
	}
	private void menu() {
		int option;
		do {
			System.out.println("Digite una opcion: \n"
					+ "1. Ingreso de cliente\n"
					+ "2. Ingreso de empleados\n"
					+ "3. Ingresar como administrador\n"
					+ "4. Registrar cliente\n"
					+ "0. Salir");
			option = sc.nextInt();
			sc.nextLine();
			if (option == 1) {
				this.menuCliente();
			}else if (option == 2) {
				this.menuEmpleado();
			}else if (option == 3) {
				this.loginAdmin();
			}else if( option == 4) {
				this.registroCliente();
			}
		}while (option != 0);
	}
	
	private void menuCliente() {
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        Object u = cafe.login(login, pass);
        if (u instanceof Cliente) {
            System.out.println("Bienvenido cliente");
        } else {
            System.out.println("Credenciales invalidas");
        }
	}
	private void menuEmpleado() {
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        Object u = cafe.login(login, pass);
        if (u instanceof Empleado) {
            System.out.println("Bienvenido empleado");
        } else {
            System.out.println("Credenciales invalidas");
        }
	}
    private void loginAdmin() {
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        Object u = cafe.login(login, pass);
        if (u instanceof Administrador) {
            System.out.println("Bienvenido admin");
            menuAdmin((Administrador) u);
        } else {
            System.out.println("Credenciales invalidas");
        }
    }
    private void menuAdmin(Administrador admin) {
        int option;
        do {
            System.out.println("MENU ADMINISTRADOR:\n"
            		+ "1. Ver solicitudes de cambio de turno\n"
            		+ "2. Ver solcitudes de platillo\n"
            		+ "3. Crear mesero\n"
            		+ "4. Crear cocinero\n"
            		+ "0. Salir");

            option = sc.nextInt();
            sc.nextLine();
            if (option == 1) {
                this.verSolicitudesTurno(admin);
            }else if (option == 2) {
                this.verSolicitudesPlatillo();
            } else if (option == 3) {
                this.crearMesero();
            } else if (option == 4) {
            	this.crearCocinero();
            }

        } while (option != 0);
    }
    private void verSolicitudesPlatillo() {
		// TODO Auto-generated method stub
		
	}
	private void verSolicitudesTurno(Administrador admin) {
		// TODO Auto-generated method stub
		
	}
	private void crearMesero() {
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        System.out.print("Código: ");
        String cod = sc.nextLine();
        Turno t = new Turno("mañana", new ArrayList<>(), new ArrayList<>());
        if (cafe.crearMesero(login, pass, new ArrayList<>(), cod, t, new ArrayList<>())) {
            System.out.println("Mesero creado");
        } else {
            System.out.println("Ya existe");
        }
    }

    private void crearCocinero() {
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        System.out.print("Código: ");
        String cod = sc.nextLine();
        Turno t = new Turno("mañana", new ArrayList<>(), new ArrayList<>());
        if (cafe.crearCocinero(login, pass, new ArrayList<>(), cod, t)) {
            System.out.println("Cocinero creado");
        } else {
            System.out.println("Ya existe");
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