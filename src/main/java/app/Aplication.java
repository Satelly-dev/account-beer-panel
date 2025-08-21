package app;

import domains.Account;
import domains.Product;
import services.account.AccountDAO;
import services.account.IAccountDAO;
import services.product.IProductDAO;
import services.product.ProductDAO;

import java.util.Scanner;

//TODO: Realizar compras actualizando la cuenta tambien

public class Aplication {
    public static void main(String[] args) {
        runApp();
    }

    private static  void runApp() {
        var exit = false;
        var console = new Scanner(System.in);
        IAccountDAO accountDAO = new AccountDAO();
        IProductDAO productDAO = new ProductDAO();
        while (!exit) {
            try {
                var option = showMenu(console);
                exit = executeOption(option, console, accountDAO, productDAO);
            } catch (Exception e) {
                System.out.println("\n❌ Opcion Invalida ❌\n");
            }
        }
    }

    private static int showMenu(Scanner console) {
        System.out.println("💥💥💥 Administrador de Cuentas Six 💥💥💥");
        System.out.print("""
            Selecciona una opcion para continuar
            -------------------
            👁️ MOSTRAR
            1. Listar cuentas
            2. Buscar cuenta
            3. Listar productos
            -------------------
            ➕ AGREGAR
            4. Agregar cuenta
            5. Agregar producto
            -------------------
            🗑️ ELIMINAR
            6. Eliminar cuenta
            7. Eliminar producto
            -------------------
            ✏️ ACTUALIZACIONES
            8. Actualizar producto
            9. Actualizar cuenta
            -------------------
            🤑 COMPRAS
            10. Realizar compra
            -------------------
            """);
        System.out.print("Selecciona una opcion para continuar:\s");
        return Integer.parseInt(console.nextLine());
    }

    private static boolean executeOption(int option, Scanner console, IAccountDAO accountDAO, IProductDAO productDAO) {
        var exit = false;
        switch (option) {
            case 1 -> {
                var accounts = accountDAO.findAll();
                if (accounts.isEmpty()) {
                    System.out.println("⚠️ No existen cuentas aun. \n");
                } else {
                    System.out.println("\n‼️ LISTADO DE CUENTAS ‼️");
                    System.out.println("⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️");
                    System.out.println(Account.header());
                    accounts.forEach(System.out::println);
                    System.out.println();
                }
            }
            case 2 -> {
                System.out.println();
                searchAccount(option, console, accountDAO, productDAO);
            }
            case 3 -> {
                var products = productDAO.findAll();
                if (products.isEmpty()) {
                    System.out.println("⚠️ No existen productos aun. \n");
                } else {
                    System.out.println("\n‼️ LISTADO DE PRODUCTOS ‼️");
                    System.out.println("⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️");
                    System.out.println(Product.header());
                    products.forEach(System.out::println);
                    System.out.println();
                }
            }
            case 4 -> {
                System.out.println("\n### ➕ Nueva cuenta ###");
                System.out.print("$nombre: ");
                var name = console.nextLine();
                System.out.print("$telefono: ");
                var phone = console.nextLine();
                Account account = new Account(name, phone);
                accountDAO.save(account);
            }
            case 5 -> {
                System.out.println("\n### ➕🍺 Nuevo Producto ###");
                System.out.print("$nombre --> ");
                var name = console.nextLine();
                System.out.print("$precio --> ");
                var price = Double.parseDouble(console.nextLine());
                System.out.print("$cantidad --> ");
                var amount = Integer.parseInt(console.nextLine());
                Product product = new Product(name, price, amount);
                productDAO.save(product);
            }
            case 6 -> {
                System.out.println("\n🗑️ Eliminar cuenta");
                Account account = searchAccount(option, console, accountDAO, productDAO);
                if (account != null)
                    accountDAO.delete(account.getId());
            }
            case 7 -> {
                System.out.println("🗑️ Eliminar Producto");
                var product = searchProduct(option, console, accountDAO, productDAO);
                if (product != null) {
                    productDAO.delete(product.getId());
                }
                else {
                    System.out.println("⚠️ Cuenta no seleccionada");
                }
            }
            case 8 -> {
                System.out.println("✏️ Actualizar Producto");
                var product = searchProduct(option, console, accountDAO, productDAO);
                if (product != null) {
                    Product productToUpdate = new Product(product.getId(), product.getName(), product.getPrice(), product.getAmount());
                    boolean exitUpdate = false;
                    while (!exitUpdate) {
                        System.out.println("""
                        ¿Qué quieres actualizar?
                        1. Nombre
                        2. Precio
                        3. Cantidad
                        📌 Para salir [4]
                        """);
                        System.out.print("Selecciona una opción: ");
                        try {
                            int optionToUpdate = Integer.parseInt(console.nextLine());
                            switch (optionToUpdate) {
                                case 1 -> {
                                    System.out.print("Ingresa el nuevo nombre: ");
                                    String name = console.nextLine();
                                    productToUpdate.setName(name);
                                    productDAO.update(productToUpdate);
                                    System.out.println("✅ Nombre actualizado.");
                                }
                                case 2 -> {
                                    System.out.print("Ingresa el nuevo precio: ");
                                    Double price = Double.parseDouble(console.nextLine());
                                    productToUpdate.setPrice(price);
                                    productDAO.update(productToUpdate);
                                    System.out.println("✅ Precio actualizado.");
                                }
                                case 3 -> {
                                    System.out.print("Ingresa la nueva cantidad: ");
                                    Integer amount = Integer.parseInt(console.nextLine());
                                    productToUpdate.setAmount(amount);
                                    productDAO.update(productToUpdate);
                                    System.out.println("✅ Cantidad actualizada.");
                                }
                                case 4 -> exitUpdate = true;
                                default -> System.out.println("❌ Opción no válida.");
                            }
                        } catch (Exception e) {
                            System.out.println("❌ Opción inválida al seleccionar campo");
                            console.nextLine();
                        }
                    }
                }
                else {
                    System.out.println("⚠️ Producto no seleccionado");
                }
            }
            case 9 -> {
                System.out.println("✏️ Actualizar Cuenta");
                var account = searchAccount(option, console, accountDAO, productDAO);
                if (account != null) {
                    Account accountToUpdate = new Account(account.getId());
                    accountToUpdate.setName(account.getName());
                    accountToUpdate.setPhone(account.getPhone());
                    accountToUpdate.setVerified(account.getVerified());

                    boolean exitUpdate = false;
                    while (!exitUpdate) {
                        System.out.println("""
                        ¿Qué quieres actualizar?
                        1. Nombre
                        2. Teléfono
                        3. Verificado
                        📌 Para salir [4]
                        """);
                        System.out.print("Selecciona una opción: ");
                        try {
                            int optionToUpdate = Integer.parseInt(console.nextLine());
                            switch (optionToUpdate) {
                                case 1 -> {
                                    System.out.print("Ingresa el nuevo nombre: ");
                                    String name = console.nextLine();
                                    accountToUpdate.setName(name);
                                    accountDAO.update(accountToUpdate);
                                    System.out.println("✅ Nombre actualizado.");
                                }
                                case 2 -> {
                                    System.out.print("Ingresa el nuevo teléfono: ");
                                    String phone = console.nextLine();
                                    accountToUpdate.setPhone(phone);
                                    accountDAO.update(accountToUpdate);
                                    System.out.println("✅ Teléfono actualizado.");
                                }
                                case 3 -> {
                                    System.out.print("¿Verificado? (true/false): ");
                                    boolean verified = Boolean.parseBoolean(console.nextLine());
                                    accountToUpdate.setVerified(verified);
                                    accountDAO.update(accountToUpdate);
                                    System.out.println("✅ Verificado actualizado.");
                                }
                                case 4 -> exitUpdate = true;
                                default -> System.out.println("❌ Opción no válida.");
                            }
                        } catch (Exception e) {
                            System.out.println("❌ Opción inválida al seleccionar campo");
                            console.nextLine(); // limpiar buffer
                        }
                    }
                }
                else {
                    System.out.println("⚠️ Cuenta no seleccionada");
                }
            }
        }
        return  exit;
    }

    private static Account searchAccount(Integer option, Scanner console, IAccountDAO accountDAO, IProductDAO productDAO) {
        System.out.print("""
                        ### Metodos de busqueda 🔎 ###
                        1. Nombre
                        2. Telefono
                        3. ID
                        """);
        System.out.print("Selecciona un metodo para comenzar:\s");
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
                        System.out.println("‼️ CUENTAS QUE CONTIENEN\s" + name.toUpperCase() + "\s‼️");
                        System.out.println("⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️");
                        System.out.println(Account.header());
                        accounts.forEach(System.out::println);
                        System.out.print("📌 ¿Desea seleccionar una cuenta? (Y = Sí, N = No):\s");
                        var response = console.nextLine();
                        if (response.equalsIgnoreCase("y")) {
                            Account account = getByID(console, accountDAO);
                            if (account != null) {
                                System.out.println("✅ Cuenta seleccionada");
                                return account;
                            }
                        } else
                            return null;
                    }
                }
                case 2 -> {
                    System.out.print("Ingresa el numero de telefono -->\s");
                    var phone = console.nextLine();
                    Account account = accountDAO.findByPhone(phone);
                    if (account != null) {
                        System.out.println("📌 CUENTA ENCONTRADA:");
                        System.out.println(Account.header());
                        System.out.println(account);
                        System.out.println();
                        return account;
                    }
                    else
                        System.out.println("⚠️ No existe cuenta.\n");
                }
                default -> {
                    return getByID(console, accountDAO);
                }

            }
        } catch (Exception e) {
            System.out.println("❌ Metodo invalido\n");
            executeOption(option, console, accountDAO, productDAO);
        }
        return null;
    }

    private static Product searchProduct(Integer option, Scanner console, IAccountDAO accountDAO, IProductDAO productDAO) {
        System.out.print("""
                        ### Metodos de busqueda 🔎 ###
                        1. Nombre
                        2. ID
                        """);
        System.out.print("Selecciona un metodo para comenzar:\s");
        try {
            var method = Integer.parseInt(console.nextLine());
            switch (method) {
                case 1 -> {
                    System.out.print("Ingresa el nombre del producto -->\s");
                    var name = console.nextLine();
                    var products = productDAO.findByName(name);
                    if (products.isEmpty())
                        System.out.println("⚠️ No existen productos relacionados. \n");
                    else {
                        System.out.println("‼️ PRODUCTOS QUE CONTIENEN\s" + name.toUpperCase() + "\s‼️");
                        System.out.println("⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️");
                        System.out.println(Product.header());
                        products.forEach(System.out::println);
                        System.out.print("📌 ¿Desea seleccionar una cuenta? (Y = Sí, N = No):\s");
                        var response = console.nextLine();
                        if (response.equalsIgnoreCase("y")) {
                            System.out.print("Ingresa el ID del producto -->\s");
                            var productId = Integer.parseInt(console.nextLine());
                            Product product = productDAO.findById(productId);
                            if (product != null) {
                                System.out.println("\n✅ Producto seleccionado");
                                System.out.println(Product.header());
                                System.out.println(product);
                                System.out.println();
                                return product;
                            } else
                                System.out.println("⚠️ No existe producto relacionado. \n");
                        } else {
                            System.out.println();
                            return null;
                        }
                    }
                }
                case 2 -> {
                    System.out.print("Ingresa el ID del producto -->\s");
                    var productId = Integer.parseInt(console.nextLine());
                    Product product = productDAO.findById(productId);
                    if (product != null) {
                        System.out.println("✅ Producto encontrado");
                        System.out.println(Product.header());
                        System.out.println(product);
                        System.out.println();
                        return product;
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("❌ Metodo invalido\n");
            executeOption(option, console, accountDAO, productDAO);
        }
        return  null;
    }

    private static Account getByID(Scanner console, IAccountDAO accountDAO){
        System.out.print("Ingresa el ID de la cuenta -->\s");
        var accountId = Integer.parseInt(console.nextLine());
        Account account = accountDAO.findById(accountId);
        if (account != null){
            System.out.println("📌 CUENTA ENCONTRADA:");
            System.out.println(Account.header());
            System.out.println(account);
            System.out.println();
        }
        else
            System.out.println("⚠️ No existe cuenta.\n");
        return account;
    }
}
