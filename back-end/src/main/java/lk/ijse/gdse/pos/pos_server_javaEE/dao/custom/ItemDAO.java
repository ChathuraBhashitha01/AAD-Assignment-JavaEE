package lk.ijse.gdse.pos.pos_server_javaEE.dao.custom;

import lk.ijse.gdse.pos.pos_server_javaEE.dao.CrudDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item,String> {
    Item searchByID(String id, Connection connection) throws SQLException, ClassNotFoundException;

}
