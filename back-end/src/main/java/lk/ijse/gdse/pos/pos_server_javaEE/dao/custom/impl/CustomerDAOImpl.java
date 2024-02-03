package lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.impl;

import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.CustomerDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.impl.util.SQLUtil;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException {
       ArrayList<Customer> customers=new ArrayList<>();
        ResultSet resultSet= SQLUtil.execute("SELECT * FROM customer",connection);
        while (resultSet.next()){
            customers.add(new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4)));
        }
        return customers;
    }

    @Override
    public boolean save(Customer entity, Connection connection) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO customer(id, name, address,salary) VALUES (?,?,?,?)";
        return SQLUtil.execute(sql,connection,entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());

    }

    @Override
    public boolean update(Customer entity, Connection connection) throws SQLException, ClassNotFoundException {
        String sql="UPDATE customer SET name=?, address=?, salary=? WHERE id=?";
        return SQLUtil.execute(sql,connection,entity.getName(),entity.getAddress(),entity.getSalary(),entity.getId());
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM customer WHERE id=?";
        return SQLUtil.execute(sql,connection,id);
    }

    @Override
    public Customer search(String id, Connection connection) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM customer WHERE id=?";
        ResultSet resultSet= SQLUtil.execute(sql,connection,id);
        if(resultSet.next()){
            return new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4));
        }
        return null;
    }

}
