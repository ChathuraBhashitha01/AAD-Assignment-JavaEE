package lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.impl;

import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.CustomerBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.CrudDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.DAOFactory;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.CustomerDTO;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CrudDAO customerDAO= DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.CUSTOMER_DAO);

    @Override
    public ArrayList<CustomerDTO> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll(connection);
        ArrayList<CustomerDTO> customerDTO=new ArrayList<>();
        for (Customer c:customers) {
            customerDTO.add(new CustomerDTO(c.getId(),c.getName(),c.getAddress(),c.getSalary()));
        }
        return customerDTO;
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto, Connection connection) throws SQLException, ClassNotFoundException {
        return  customerDAO.save(new Customer(dto.getId(),dto.getName(),dto.getAddress(),dto.getSalary()),connection);

    }

    @Override
    public boolean updateCustomer(CustomerDTO dto, Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getId(),dto.getName(),dto.getAddress(),dto.getSalary()),connection);
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id,connection);
    }
}
