package lk.ijse.gdse.pos.pos_server_javaEE.bo.custom;

import lk.ijse.gdse.pos.pos_server_javaEE.bo.SuperBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.ItemDTO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.OrderDTO;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBO {
    String getOrderID(Connection connection) throws ServletException, IOException, SQLException, ClassNotFoundException;
    boolean saveOrder(OrderDTO dto, Connection connection) throws SQLException, ClassNotFoundException;

}
