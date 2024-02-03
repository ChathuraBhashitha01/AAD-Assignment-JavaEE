package lk.ijse.gdse.pos.pos_server_javaEE.bo.custom;

import lk.ijse.gdse.pos.pos_server_javaEE.bo.SuperBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.ItemDTO;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAll( Connection connection) throws ServletException, IOException, SQLException, ClassNotFoundException;

    boolean saveItem(ItemDTO dto, Connection connection) throws ServletException, IOException, SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO dto, Connection connection) throws ServletException, IOException, SQLException, ClassNotFoundException;

    boolean deleteItem(String id, Connection connection) throws ServletException, IOException, SQLException, ClassNotFoundException;

}
