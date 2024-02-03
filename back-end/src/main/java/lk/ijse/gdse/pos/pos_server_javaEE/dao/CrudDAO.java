package lk.ijse.gdse.pos.pos_server_javaEE.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T,ID> extends SuperDAO{
    ArrayList<T> getAll(Connection connection) throws SQLException, ClassNotFoundException;

    boolean save(T entity, Connection connection) throws SQLException, ClassNotFoundException;

    boolean update(T entity, Connection connection) throws SQLException, ClassNotFoundException;

    boolean delete(ID id,Connection connection) throws SQLException, ClassNotFoundException;

    T search(ID id,Connection connection) throws SQLException, ClassNotFoundException;
}
