package lk.ijse.gdse.pos.pos_server_javaEE.bo.custom;

import lk.ijse.gdse.pos.pos_server_javaEE.bo.SuperBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.OrderDTO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.OrderDetailDTO;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
    OrderDTO getOrder(String id,Connection connection) throws SQLException, ClassNotFoundException;
}
