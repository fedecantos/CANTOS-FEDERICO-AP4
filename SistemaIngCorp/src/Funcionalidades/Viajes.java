package Funcionalidades;

import BaseDeDatos.FuncionesObligatorias;
import BaseDeDatos.GestorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Viajes implements FuncionesObligatorias<Viajes> {
    private int viaje_id;
    private int user_id;
    private String destino;
    private String fecha_salida;
    private String fecha_regreso;
    private String estado;
    private Connection conexion;
    private List<Gastos> gastos;
    private List<Vehiculos> vehiculos;

    public Viajes() {
        this.conexion = GestorBD.getConexion();
    }

    public Viajes(int viaje_id, int user_id, String destino, String fecha_salida, String fecha_regreso, String estado) {
        this.viaje_id = viaje_id;
        this.user_id = user_id;
        this.destino = destino;
        this.fecha_salida = fecha_salida;
        this.fecha_regreso = fecha_regreso;
        this.estado = estado;
        this.conexion = GestorBD.getConexion();
    }

    public int getViajeId() {
        return viaje_id;
    }

    public void setViajeId(int viaje_id) {
        this.viaje_id = viaje_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getFechaSalida() {
        return fecha_salida;
    }

    public void setFechaSalida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getFechaRegreso() {
        return fecha_regreso;
    }

    public void setFechaRegreso(String fecha_regreso) {
        this.fecha_regreso = fecha_regreso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Gastos> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gastos> gastos) {
        this.gastos = gastos;
    }

    public List<Vehiculos> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculos> vehiculos) {
        this.vehiculos = vehiculos;
    }

    @Override
    public boolean create(Viajes viaje) {
        try {
            String consulta = "INSERT INTO Viajes (user_id, destino, fecha_salida, fecha_regreso, estado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement consultaE = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            consultaE.setInt(1, viaje.getUserId());
            consultaE.setString(2, viaje.getDestino());
            consultaE.setString(3, viaje.getFechaSalida());
            consultaE.setString(4, viaje.getFechaRegreso());
            consultaE.setString(5, viaje.getEstado());
            int affectedRows = consultaE.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = consultaE.getGeneratedKeys();
                if (generatedKeys.next()) {
                    viaje.setViajeId(generatedKeys.getInt(1));
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Viajes read(int viaje_id) {
        try {
            String consulta = "SELECT * FROM Viajes WHERE viaje_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, viaje_id);
            ResultSet resultado = consultaE.executeQuery();
            if (resultado.next()) {
                Viajes viaje = new Viajes(
                        resultado.getInt("viaje_id"),
                        resultado.getInt("user_id"),
                        resultado.getString("destino"),
                        resultado.getString("fecha_salida"),
                        resultado.getString("fecha_regreso"),
                        resultado.getString("estado")
                );

                // Recuperar gastos asociados
                List<Gastos> gastos = recuperarGastos(viaje_id);
                viaje.setGastos(gastos);

                // Recuperar vehículos asociados
                List<Vehiculos> vehiculos = recuperarVehiculos(viaje.getUserId());
                viaje.setVehiculos(vehiculos);

                return viaje;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Viajes viaje) {
        try {
            String consulta = "UPDATE Viajes SET user_id = ?, destino = ?, fecha_salida = ?, fecha_regreso = ?, estado = ? WHERE viaje_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, viaje.getUserId());
            consultaE.setString(2, viaje.getDestino());
            consultaE.setString(3, viaje.getFechaSalida());
            consultaE.setString(4, viaje.getFechaRegreso());
            consultaE.setString(5, viaje.getEstado());
            consultaE.setInt(6, viaje.getViajeId());
            int filasAfectadas = consultaE.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int viaje_id) {
        try {
            String consulta = "DELETE FROM Viajes WHERE viaje_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, viaje_id);
            int filasAfectadas = consultaE.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Viajes> readAll() {
        List<Viajes> viajes = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM Viajes";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            ResultSet resultado = consultaE.executeQuery();
            while (resultado.next()) {
                Viajes viaje = new Viajes(
                        resultado.getInt("viaje_id"),
                        resultado.getInt("user_id"),
                        resultado.getString("destino"),
                        resultado.getString("fecha_salida"),
                        resultado.getString("fecha_regreso"),
                        resultado.getString("estado")
                );

                // Recuperar gastos asociados
                List<Gastos> gastos = recuperarGastos(viaje.getViajeId());
                viaje.setGastos(gastos);

                // Recuperar vehículos asociados
                List<Vehiculos> vehiculos = recuperarVehiculos(viaje.getUserId());
                viaje.setVehiculos(vehiculos);

                viajes.add(viaje);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return viajes;
    }

    private List<Gastos> recuperarGastos(int viaje_id) {
        List<Gastos> gastos = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM Gastos WHERE viaje_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, viaje_id);
            ResultSet resultado = consultaE.executeQuery();
            while (resultado.next()) {
                gastos.add(new Gastos(
                        resultado.getInt("gasto_id"),
                        resultado.getInt("viaje_id"),
                        resultado.getString("concepto"),
                        resultado.getDouble("monto"),
                        resultado.getString("fecha"),
                        resultado.getBoolean("aprobado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gastos;
    }

    private List<Vehiculos> recuperarVehiculos(int user_id) {
        List<Vehiculos> vehiculos = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM Vehiculos WHERE user_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, user_id);
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
        return "Viaje ID: " + viaje_id +
                "\nUsuario ID: " + user_id +
                "\nDestino: " + destino +
                "\nFecha de Salida: " + fecha_salida +
                "\nFecha de Regreso: " + fecha_regreso +
                "\nEstado: " + estado +
                "\nGastos: " + (gastos != null ? gastos : "Ninguno") +
                "\nVehículos: " + (vehiculos != null ? vehiculos : "Ninguno");
    }
}