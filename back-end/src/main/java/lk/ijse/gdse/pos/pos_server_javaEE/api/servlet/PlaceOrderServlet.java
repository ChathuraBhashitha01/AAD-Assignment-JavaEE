package lk.ijse.gdse.pos.pos_server_javaEE.api.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.BoFactory;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.PlaceOrderBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.ItemDTO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.OrderDTO;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "placeOrderServlet",urlPatterns = "/placeorders")
public class PlaceOrderServlet extends HttpServlet {
    PlaceOrderBO placeOrderBO= BoFactory.getBoFactory().getBO(BoFactory.BOType.PLACE_ORDER_BO);
    DataSource pool;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext initialContext = new InitialContext();
            pool= (DataSource) initialContext.lookup("java:/comp/env/jdbc/pos");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Jsonb jsonb = JsonbBuilder.create();
        OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);

        try(Connection connection = pool.getConnection()) {
            boolean isSaved = placeOrderBO.saveOrder(orderDTO,connection);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id=null;
        try(Connection connection = pool.getConnection()) {
          id =placeOrderBO.getOrderID(connection);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        resp.getWriter().write(id);
    }

}
