package lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.impl;

import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.ItemDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {


    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Item> items=new ArrayList<>();
        ResultSet resultSet=SQLUtil.execute("SELECT * FROM item",connection);
        while (resultSet.next()){
            items.add(new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4)));
        }
        return items;
    }

    @Override
    public boolean save(Item entity, Connection connection) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO item(code, description, qtyOnHand,unitPrice) VALUES (?,?,?,?)";
        return SQLUtil.execute(sql,connection,entity.getCode(),entity.getDescription(),entity.getQtyOnHand(),entity.getUnitPrice());
    }

    @Override
    public boolean update(Item entity, Connection connection) throws SQLException, ClassNotFoundException {
        String sql="UPDATE item SET description=?, qtyOnHand=?, unitPrice=? WHERE code=?";
        return SQLUtil.execute(sql,connection,entity.getDescription(),entity.getQtyOnHand(),entity.getUnitPrice(),entity.getCode());
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM item WHERE code=?";
        return SQLUtil.execute(sql,connection,id);
    }

    @Override
    public Item search(String id, Connection connection) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM item WHERE code=?";
        ResultSet resultSet= SQLUtil.execute(sql,connection,id);
        if(resultSet.next()){
            return new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4));
        }
        return null;
    }

}
