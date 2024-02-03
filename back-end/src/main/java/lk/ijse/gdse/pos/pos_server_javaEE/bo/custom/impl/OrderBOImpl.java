package lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.impl;

import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.OrderBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.CrudDAO;
import lk.ijse.gdse.pos.pos_server_javaEE.dao.DAOFactory;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.OrderDTO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.OrderDetailDTO;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.OrderDetail;
import lk.ijse.gdse.pos.pos_server_javaEE.entity.Placeorder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    CrudDAO orderDAO= DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.ORDER_DAO);
    CrudDAO orderDetailDAO= DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.ORDER_DETAIL_DAO);
    @Override
    public OrderDTO getOrder(String id,Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO> orderDetailDTOs=new ArrayList<>();
        ArrayList<OrderDetail> orderDetails=orderDetailDAO.getAll(connection);
        for (OrderDetail orderDetail:orderDetails) {
            orderDetailDTOs.add(new OrderDetailDTO(orderDetail.getOrderID(),orderDetail.getItemCode(),orderDetail.getQty(),orderDetail.getPrice()));
        }

        Placeorder order = (Placeorder) orderDAO.search(id, connection);
        return new OrderDTO(order.getOrderID(),order.getDate(),order.getCustomerID(),order.getTotal(),orderDetailDTOs);
    }
}
