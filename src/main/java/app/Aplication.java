package app;

import domains.Account;
import services.AccountDAO;
import services.IAccountDAO;

import java.util.Scanner;

public class Aplication {
    public static void main(String[] args) {
        runApp();
    }

    private static  void runApp() {
        var exit = false;
        var console = new Scanner(System.in);
        IAccountDAO accountDAO = new AccountDAO();
        while (!exit) {
            try {
                var option = showMenu(console);
                exit = executeOption(option, console, accountDAO);
            } catch (Exception e) {
                System.out.println("\n❌ Opcion Invalida ❌\n");
                continue;
            }
        }
    }

    private static int showMenu(Scanner console) {
        System.out.println("💥💥💥 Administrador de Cuentas Six 💥💥💥");
        System.out.print("""
            Selecciona una opcion para continuar
            1. Listar cuentas
            2. Buscar cuenta
            3. Agregar cuenta
            """);
        System.out.print("Selecciona una opcion para continuar:\s");
        return Integer.parseInt(console.nextLine());
    }

    private static boolean executeOption(int option, Scanner console, IAccountDAO accountDAO) {
        var exit = false;
        switch (option) {
            case 1 -> {
                var accounts = accountDAO.findAll();
                if (accounts.isEmpty()) {
                    System.out.println("⚠️ No existen cuentas aun. \n");
                } else {
                    System.out.println("‼️ LISTADO DE CUENTAS ‼️");
                    System.out.println("⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️");
                    System.out.println(Account.header());
                    accounts.forEach(System.out::println);
                    System.out.println();
                }
            }
            case 2 -> {
                System.out.print("""
                        ### Metodos de busqueda 🔎 ###
                        1. Nombre
                        2. Telefono
                        3. ID
                        """);
                System.out.print("Selecciona un metodo para comenzar:\s");
                methodOfSearch(console, accountDAO);
            }
            case 3 -> {
                System.out.println("### ➕ Nueva cuenta ###");
                System.out.print("$nombre: ");
                var name = console.nextLine();
                System.out.print("$telefono: ");
                var phone = console.nextLine();
                Account account = new Account(name, phone);
                accountDAO.save(account);
            }
        }
        return  exit;
    }

    private static void methodOfSearch(Scanner console, IAccountDAO accountDAO) {
        try {
            var method = Integer.parseInt(console.nextLine());
            switch (method) {
                case 1 -> {
                    System.out.print("Ingresa el nombre -->\s");
                    var name = console.nextLine();
                    var accounts = accountDAO.findByName(name);
                    if (accounts.isEmpty()) {
                        System.out.println("⚠️ No existen cuentas aun. \n");
                    } else {
                        System.out.println("‼️ LISTADO DE CUENTAS CON FILTRO ‼️");
                        System.out.println("⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️");
                        System.out.println(Account.header());
                        accounts.forEach(System.out::println);
                        System.out.println();
                    }
                }
                case 2 -> {
                    System.out.print("Ingresa el numero de telefono -->\s");
                    var phone = console.nextLine();
                    Account account = accountDAO.findByPhone(phone);
                    if (account != null) {
                        System.out.println(Account.header());
                        System.out.println(account);
                        System.out.println();
                    }
                    else
                        System.out.println("⚠️ No existe cuenta.\n");
                }
                default -> {
                    System.out.print("Ingresa el ID de la cuenta -->\s");
                    var accountId = Integer.parseInt(console.nextLine());
                    Account account = accountDAO.findById(accountId);
                    if (account != null){
                        System.out.println(Account.header());
                        System.out.println(account);
                        System.out.println();
                    }
                    else
                        System.out.println("⚠️ No existe cuenta.\n");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Metodo invalido\n");
            executeOption(2, console, accountDAO);
        }
    }
}
