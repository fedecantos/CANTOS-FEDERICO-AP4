package Funcionalidades;

import BaseDeDatos.FuncionesObligatorias;
import BaseDeDatos.GestorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Gastos implements FuncionesObligatorias<Gastos> {
    private int gasto_id;
    private int viaje_id;
    private String concepto;
    private double monto;
    private String fecha;
    private boolean aprobado;
    private Connection conexion;

    public Gastos() {
        this.conexion = GestorBD.getConexion();
    }

    public Gastos(int gasto_id, int viaje_id, String concepto, double monto, String fecha, boolean aprobado) {
        this.gasto_id = gasto_id;
        this.viaje_id = viaje_id;
        this.concepto = concepto;
        this.monto = monto;
        this.fecha = fecha;
        this.aprobado = aprobado;
        this.conexion = GestorBD.getConexion();
    }

    public int getGastoId() {
        return gasto_id;
    }

    public void setGastoId(int gasto_id) {
        this.gasto_id = gasto_id;
    }

    public int getViajeId() {
        return viaje_id;
    }

    public void setViajeId(int viaje_id) {
        this.viaje_id = viaje_id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    @Override
    public boolean create(Gastos gasto) {
        try {
            String consulta = "INSERT INTO Gastos (viaje_id, concepto, monto, fecha, aprobado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, gasto.getViajeId());
            consultaE.setString(2, gasto.getConcepto());
            consultaE.setDouble(3, gasto.getMonto());
            consultaE.setString(4, gasto.getFecha());
            consultaE.setBoolean(5, gasto.isAprobado());
            consultaE.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Gastos read(int gasto_id) {
        try {
            String consulta = "SELECT * FROM Gastos WHERE gasto_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, gasto_id);
            ResultSet resultado = consultaE.executeQuery();
            if (resultado.next()) {
                return new Gastos(
                        resultado.getInt("gasto_id"),
                        resultado.getInt("viaje_id"),
                        resultado.getString("concepto"),
                        resultado.getDouble("monto"),
                        resultado.getString("fecha"),
                        resultado.getBoolean("aprobado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Gastos gasto) {
        try {
            String consulta = "UPDATE Gastos SET viaje_id = ?, concepto = ?, monto = ?, fecha = ?, aprobado = ? WHERE gasto_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, gasto.getViajeId());
            consultaE.setString(2, gasto.getConcepto());
            consultaE.setDouble(3, gasto.getMonto());
            consultaE.setString(4, gasto.getFecha());
            consultaE.setBoolean(5, gasto.isAprobado());
            consultaE.setInt(6, gasto.getGastoId());
            int filasAfectadas = consultaE.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int gasto_id) {
        try {
            String consulta = "DELETE FROM Gastos WHERE gasto_id = ?";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
            consultaE.setInt(1, gasto_id);
            int filasAfectadas = consultaE.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Gastos> readAll() {
        List<Gastos> gastos = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM Gastos";
            PreparedStatement consultaE = conexion.prepareStatement(consulta);
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

    @Override
    public String toString() {
        return "Gasto ID: " + gasto_id +
                ", Viaje ID: " + viaje_id +
                ", Concepto: " + concepto +
                ", Monto: " + monto +
                ", Fecha: " + fecha +
                ", Aprobado: " + aprobado;
    }}

