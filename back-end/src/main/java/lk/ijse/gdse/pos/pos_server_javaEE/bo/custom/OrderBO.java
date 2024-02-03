package lk.ijse.gdse.pos.pos_server_javaEE.bo.custom;

import lk.ijse.gdse.pos.pos_server_javaEE.bo.SuperBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderBO extends SuperBO {
    OrderDTO getOrder(String id,Connection connection) throws SQLException, ClassNotFoundException;
}
