package presentacion;

import java.util.ArrayList;
import java.util.Scanner;

import logica.Cafe;
import persistencia.PersistenciaCafe;

public class Principal {

    protected Cafe cafe;
    protected Scanner sc;

    public Principal(Cafe cafe) {
        this.cafe = cafe;
        this.sc = new Scanner(System.in);
         
        }

    // =========================
    // MENU PRINCIPAL
    // =========================
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
                new ClientePrincipal(cafe);
            } 
            else if (option == 2) {
                new EmpleadoPresentacion(cafe);
            } 
            else if (option == 3) {
                new AdministradorPrincipal(cafe);
            } 
            else if (option == 4) {
                registroCliente();
            } 
            else if (option == 0) {
                PersistenciaCafe.guardar(cafe);
                System.out.println("Datos guardados correctamente");
            }

        } while (option != 0);
    }

    // =========================
    // REGISTRO CLIENTE
    // =========================
    private void registroCliente() {
        System.out.print("Login: ");
        String login = sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        boolean creado = cafe.crearCliente(login, pass, new ArrayList<>(), 0);

        if (creado) {
            System.out.println("Cliente creado correctamente");
        } else {
            System.out.println("El cliente ya existe");
        }
    }

    // =========================
    // MAIN
    // =========================
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

        new Principal(cafe).menu();
    }
}