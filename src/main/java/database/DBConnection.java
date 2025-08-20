package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection connection = null;
        var nameDb = "six_db";
        var url = "jdbc:mysql://localhost:3306/" + nameDb;
        var user = "root";
        var passowrd = "FelixRomero15";
        try {
            // Cargar el nombre de la clase para poder conectarse
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, passowrd);

        } catch (Exception e) {
            System.out.println("Error al conectarse ala DB: " + e.getMessage());
        }
        return connection;
    }

    public static void main(String[] args) {
        var fetch = DBConnection.getConnection();
        if (fetch != null)
            System.out.println("Conexion exitosa");
        else
            System.out.println("Error en la conexion");
    }
}
