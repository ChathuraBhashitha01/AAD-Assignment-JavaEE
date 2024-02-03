package lk.ijse.gdse.pos.pos_server_javaEE.bo.custom;

import lk.ijse.gdse.pos.pos_server_javaEE.bo.SuperBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    ArrayList<CustomerDTO> getAll( Connection connection) throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDTO dto, Connection connection) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO dto, Connection connection) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id, Connection connection) throws SQLException, ClassNotFoundException;

}
