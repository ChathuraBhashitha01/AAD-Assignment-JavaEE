package lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.impl;

import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.OrderDetail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderaDetailDAOImpl implements OrderDetailsDAO {

    @Override
    public ArrayList<OrderDetail> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> orders=new ArrayList<>();
        ResultSet resultSet= SQLUtil.execute("SELECT * FROM orderdetail",connection);
        while (resultSet.next()){
            orders.add(new OrderDetail(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4)));
        }
        return orders;
    }

    @Override
    public boolean save(OrderDetail entity, Connection connection) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO orderdetail(orderID, itemCode, qty,price) VALUES (?,?,?,?)";
        return SQLUtil.execute(sql,connection,entity.getOrderID(),entity.getItemCode(),entity.getQty(),entity.getPrice() );
    }

    @Override
    public boolean update(OrderDetail entity, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail search(String id, Connection connection) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM orderdetail WHERE orderID=?";
        ResultSet resultSet= SQLUtil.execute(sql,connection,id);
        if(resultSet.next()){
            return new OrderDetail(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4));
        }
        return null;
    }
}
