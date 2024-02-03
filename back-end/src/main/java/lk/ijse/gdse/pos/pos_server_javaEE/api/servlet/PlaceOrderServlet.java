package lk.ijse.gdse.pos.pos_server_javaEE.api.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.BoFactory;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.PlaceOrderBO;
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

@WebServlet(name = "placeOrderServlet",urlPatterns = "/placeorders")
public class PlaceOrderServlet extends HttpServlet {
    PlaceOrderBO placeOrderBO= BoFactory.getBoFactory().getBO(BoFactory.BOType.PLACE_ORDER_BO);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");
        Connection connection=null;

        Jsonb jsonb = JsonbBuilder.create();
        OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);

        try {
            connection= dbcp.getConnection();
            boolean isSaved = placeOrderBO.saveOrder(orderDTO,connection);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
}
