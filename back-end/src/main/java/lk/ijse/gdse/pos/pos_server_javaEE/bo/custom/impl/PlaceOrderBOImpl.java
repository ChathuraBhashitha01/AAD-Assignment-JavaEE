package lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.impl;

import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.PlaceOrderBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.CrudDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.DAOFactory;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.SuperDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.ItemDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.OrderDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.ItemDTO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.OrderDTO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.OrderDetailDTO;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Item;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.OrderDetail;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Placeorder;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    ItemDAO itemDAO= DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.ITEM_DAO);
    OrderDAO placeOrderDAO=DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.ORDER_DAO);
    OrderDetailsDAO orderDetailDAO=DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.ORDER_DETAIL_DAO);

    @Override
    public String getOrderID(Connection connection) throws ServletException, IOException, SQLException, ClassNotFoundException {
        return placeOrderDAO.getNextId(connection);
    }

    @Override
    public boolean saveOrder(OrderDTO dto,Connection connection) throws SQLException, ClassNotFoundException {

        try {
            connection.setAutoCommit(false);

            boolean isSaved = placeOrderDAO.save(new Placeorder(dto.getOrderID(),dto.getDate(),dto.getCustomerID(),dto.getTotal()),connection);
            if (!isSaved){
               connection.rollback();
               connection.setAutoCommit(true);
               return false;
            }
            for (OrderDetailDTO entity:dto.getOrderDetails()) {
                boolean orderDetailSaved = orderDetailDAO.save(new OrderDetail(entity.getOrderID(), entity.getItemCode(), entity.getQty(), entity.getPrice()), connection);
                if (!orderDetailSaved){
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

                Item item=itemDAO.searchByID(entity.getItemCode(), connection);
                item.setQtyOnHand(item.getQtyOnHand()-entity.getQty());

                boolean isUpdate = itemDAO.update(item, connection);
                if (!isUpdate){
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
