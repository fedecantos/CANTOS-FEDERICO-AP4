package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GestorBD {
    private static final String URL = "jdbc:mysql://localhost:3306/sistemaviaje";
    private static final String USER = "root";
    private static final String PASSWORD = "Brujo2024";

    private static Connection conexion;

    public GestorBD() {
        conectar();
        crearTablas();
    }

    // Método para establecer la conexión con la base de datos
    private void conectar() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para crear las tablas en la base de datos
    private void crearTablas() {
        try {
            Statement consultaE = conexion.createStatement();

            // Crear tabla Usuarios
            String sqlUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios (" +
                    "user_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "nombre VARCHAR(50) NOT NULL," +
                    "apellido VARCHAR(50) NOT NULL," +
                    "email VARCHAR(100) NOT NULL UNIQUE," +
                    "contrasena VARCHAR(100) NOT NULL" +
                    ")";
            consultaE.executeUpdate(sqlUsuarios);

            // Crear tabla Viajes
            String sqlViajes = "CREATE TABLE IF NOT EXISTS Viajes (" +
                    "viaje_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "user_id INT NOT NULL," +
                    "destino VARCHAR(100) NOT NULL," +
                    "fecha_salida DATE NOT NULL," +
                    "fecha_regreso DATE NOT NULL," +
                    "estado VARCHAR(50) NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES Usuarios(user_id)" +
                    ")";
            consultaE.executeUpdate(sqlViajes);

            // Crear tabla Gastos
            String sqlGastos = "CREATE TABLE IF NOT EXISTS Gastos (" +
                    "gasto_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "viaje_id INT NOT NULL," +
                    "concepto VARCHAR(255) NOT NULL," +
                    "monto DECIMAL(10, 2) NOT NULL," +
                    "fecha DATE NOT NULL," +
                    "aprobado BOOLEAN NOT NULL," +
                    "FOREIGN KEY (viaje_id) REFERENCES Viajes(viaje_id)" +
                    ")";
            consultaE.executeUpdate(sqlGastos);

            // Crear tabla Vehiculos
            String sqlVehiculos = "CREATE TABLE IF NOT EXISTS Vehiculos (" +
                    "vehiculo_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "user_id INT NOT NULL," +
                    "nombre_vehiculo VARCHAR(50) NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES Usuarios(user_id)" +
                    ")";
            consultaE.executeUpdate(sqlVehiculos);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener la conexión a la base de datos
    public static Connection getConexion() {
        if (conexion == null) {
            new GestorBD();
        }
        return conexion;
    }

    // Método para cerrar la conexión a la base de datos
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}