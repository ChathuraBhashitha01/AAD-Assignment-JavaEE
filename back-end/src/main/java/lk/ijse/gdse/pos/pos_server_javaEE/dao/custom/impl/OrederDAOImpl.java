package lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.impl;

import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.OrderDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Item;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Placeorder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrederDAOImpl implements OrderDAO {

    @Override
    public ArrayList<Placeorder> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Placeorder> placeorders=new ArrayList<>();
        ResultSet resultSet=SQLUtil.execute("SELECT * FROM placeorder",connection);
        while (resultSet.next()){
            placeorders.add(new Placeorder(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4)));
        }
        return placeorders;
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
    public ArrayList<Placeorder> search(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Placeorder searchByID(String id, Connection connection) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM placeorder WHERE orderID=?";
        ResultSet resultSet= SQLUtil.execute(sql,connection,id);
        if(resultSet.next()){
            return new Placeorder(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4));
        }
        return null;
    }

    @Override
    public String getNextId(Connection connection) throws SQLException {
        String sql="SELECT orderID FROM placeorder ORDER BY orderID DESC LIMIT 1";
        ResultSet resultSet=SQLUtil.execute(sql,connection);
        if (resultSet.next()){
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String id) {
        if(id != null) {
            String[] strings = id.split("OR00-00");
            int ids = Integer.parseInt(strings[1]);
            if(ids==9){
                String[] strings2 = id.split("OR00-0");
                int ids2 = Integer.parseInt(strings2[1]);
                if(ids2==99){
                    String[] strings3 = id.split("OR00-");
                    int ids3 = Integer.parseInt(strings2[1]);
                    ids3++;
                    return "OR00-" + ids3;
                }
                ids2++;
                return "OR00-0" + ids2;
            }
            ids++;
            return "OR00-00" + ids;
        }
        return "OR00-001";
    }
}
