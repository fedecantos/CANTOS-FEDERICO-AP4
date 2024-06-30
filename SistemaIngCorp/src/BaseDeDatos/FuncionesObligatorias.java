package BaseDeDatos;

import java.sql.Connection;
import java.util.List;

public interface FuncionesObligatorias<T> {
    boolean create(T obj);
    T read(int id);
    boolean update(T obj);
    boolean delete(int id);
    List<T> readAll();
}

