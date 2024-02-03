package lk.ijse.gdse.pos.pos_server_javaEE.api.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.BoFactory;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.OrderBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.OrderDTO;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "orderServlet",urlPatterns = "/orders")
public class OrderServlet extends HttpServlet {
    OrderBO orderBO=BoFactory.getBoFactory().getBO(BoFactory.BOType.ORDER_BO);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");
        Connection connection=null;
        String orderID = req.getParameter("orderID");

        OrderDTO orderDTO=new OrderDTO();
        try {
            connection=dbcp.getConnection();
            orderDTO=orderBO.getOrder(orderID,connection);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        Jsonb jsonb = JsonbBuilder.create();
        jsonb.toJson(orderDTO,resp.getWriter());
    }
}
