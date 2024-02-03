package lk.ijse.gdse.pos.pos_server_javaEE.bo.custom;

import lk.ijse.gdse.pos.pos_server_javaEE.bo.SuperBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface PlaceOrderBO extends SuperBO {
    boolean saveOrder(OrderDTO dto, Connection connection) throws SQLException, ClassNotFoundException;

}
