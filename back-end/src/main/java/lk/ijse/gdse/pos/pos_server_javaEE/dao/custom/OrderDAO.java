package lk.ijse.gdse.pos.pos_server_javaEE.dao.custom;

import lk.ijse.gdse.pos.pos_server_javaEE.dao.CrudDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Item;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Placeorder;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Placeorder,String> {
    Placeorder searchByID(String id, Connection connection) throws SQLException, ClassNotFoundException;
}
