package Funcionalidades;

import BaseDeDatos.FuncionesObligatorias;
import BaseDeDatos.GestorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Vehiculos implements FuncionesObligatorias<Vehiculos> {
    private int vehiculo_id;
    private int user_id;
    private String nombre_vehiculo;
    private Connection conexion;

    public Vehiculos() {
        this.conexion = GestorBD.getConexion();
    }

    public Vehiculos(int vehiculo_id, int user_id, String nombre_vehiculo) {
        this.vehiculo_id = vehiculo_id;
        this.user_id = user_id;
        this.nombre_vehiculo = nombre_vehiculo;
        this.conexion = GestorBD.getConexion();
    }

    public int getVehiculoId() {
        return vehiculo_id;
    }

    public void setVehiculoId(int vehiculo_id) {
        this.vehiculo_id = vehiculo_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getNombreVehiculo() {
        return nombre_vehiculo;
    }

    public void setNombreVehiculo(String nombre_vehiculo) {
        this.nombre_vehiculo = nombre_vehiculo;
    }

    @Override
    public boolean create(Vehiculos vehiculo) {
        try {
            String consulta = "INSERT INTO Vehiculos (user_id, nombre_vehiculo) VALUES (?, ?)";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, vehiculo.getUserId());
            consultaE.setString(2, vehiculo.getNombreVehiculo());
            consultaE.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Vehiculos read(int vehiculo_id) {
        try {
            String consulta = "SELECT * FROM Vehiculos WHERE vehiculo_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, vehiculo_id);
            ResultSet resultado = consultaE.executeQuery();
            if (resultado.next()) {
                return new Vehiculos(
                        resultado.getInt("vehiculo_id"),
                        resultado.getInt("user_id"),
                        resultado.getString("nombre_vehiculo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Vehiculos vehiculo) {
        try {
            String consulta = "UPDATE Vehiculos SET user_id = ?, nombre_vehiculo = ? WHERE vehiculo_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, vehiculo.getUserId());
            consultaE.setString(2, vehiculo.getNombreVehiculo());
            consultaE.setInt(3, vehiculo.getVehiculoId());
            int filasAfectadas = consultaE.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int vehiculo_id) {
        try {
            String consulta = "DELETE FROM Vehiculos WHERE vehiculo_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, vehiculo_id);
            int filasAfectadas = consultaE.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Vehiculos> readAll() {
        List<Vehiculos> vehiculos = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM Vehiculos";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            ResultSet resultado = consultaE.executeQuery();
            while (resultado.next()) {
                vehiculos.add(new Vehiculos(
                        resultado.getInt("vehiculo_id"),
                        resultado.getInt("user_id"),
                        resultado.getString("nombre_vehiculo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculos;
    }

    @Override
    public String toString() {
        return "Vehículo ID: " + vehiculo_id +
                ", Usuario ID: " + user_id +
                ", Nombre del Vehículo: " + nombre_vehiculo;
    }
}