package lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.impl;

import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.OrderBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.CrudDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.DAOFactory;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.OrderDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.OrderDTO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.OrderDetailDTO;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.OrderDetail;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Placeorder;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    OrderDAO orderDAO= DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.ORDER_DAO);
    OrderDetailsDAO orderDetailDAO= DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.ORDER_DETAIL_DAO);


    @Override
    public ArrayList<OrderDTO> getAll(Connection connection) throws ServletException, IOException, SQLException, ClassNotFoundException {
        ArrayList<Placeorder> placeorders=orderDAO.getAll(connection);
        ArrayList<OrderDTO> orderDTOS=new ArrayList<>();
        for (Placeorder i:placeorders) {

            ArrayList<OrderDetailDTO> orderDetailDTOs=new ArrayList<>();
            ArrayList<OrderDetail> orderDetails=orderDetailDAO.search(i.getOrderID(),connection);

            for (OrderDetail orderDetail:orderDetails) {
                orderDetailDTOs.add(new OrderDetailDTO(orderDetail.getOrderID(),orderDetail.getItemCode(),orderDetail.getQty(),orderDetail.getPrice()));
            }

            orderDTOS.add(new OrderDTO(i.getOrderID(),i.getDate(),i.getCustomerID(),i.getTotal(),orderDetailDTOs));
        }
        return orderDTOS;
    }
}