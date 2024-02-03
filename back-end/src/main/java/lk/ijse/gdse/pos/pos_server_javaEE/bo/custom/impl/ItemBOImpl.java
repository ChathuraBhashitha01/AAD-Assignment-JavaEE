package lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.impl;

import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.ItemBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.CrudDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.DAOFactory;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.ItemDTO;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    CrudDAO itemDAO= DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.ITEM_DAO);

    @Override
    public ArrayList<ItemDTO> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Item> items=itemDAO.getAll(connection);
        ArrayList<ItemDTO> itemDTO=new ArrayList<>();
        for (Item i:items) {
            itemDTO.add(new ItemDTO(i.getCode(),i.getDescription(),i.getQtyOnHand(),i.getUnitPrice()));
        }
        return itemDTO;
    }

    @Override
    public boolean saveItem(ItemDTO dto, Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(dto.getCode(),dto.getDescription(),dto.getQtyOnHand(),dto.getUnitPrice()),connection);
    }

    @Override
    public boolean updateItem(ItemDTO dto, Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getCode(),dto.getDescription(),dto.getQtyOnHand(),dto.getUnitPrice()),connection);
    }

    @Override
    public boolean deleteItem(String id, Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id,connection);
    }
}
