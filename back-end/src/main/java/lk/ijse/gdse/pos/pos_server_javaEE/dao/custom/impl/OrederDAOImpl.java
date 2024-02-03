package lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.impl;

import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.OrderDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Placeorder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrederDAOImpl implements OrderDAO {

    @Override
    public ArrayList<Placeorder> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Placeorder entity, Connection connection) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO placeorder(orderID, date, customerID,total) VALUES (?,?,?,?)";
        return SQLUtil.execute(sql,connection,entity.getOrderID(),entity.getDate(),entity.getCustomerID(),entity.getTotal());
    }

    @Override
    public boolean update(Placeorder entity, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Placeorder search(String id, Connection connection) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM placeorder WHERE orderID=?";
        ResultSet resultSet= SQLUtil.execute(sql,connection,id);
        if(resultSet.next()){
            return new Placeorder(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4));
        }
        return null;
    }
}
