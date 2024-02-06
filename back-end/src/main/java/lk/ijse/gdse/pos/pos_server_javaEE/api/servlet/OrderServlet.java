package lk.ijse.gdse.pos.pos_server_javaEE.api.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.BoFactory;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.OrderBO;
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

@WebServlet(name = "orderServlet",urlPatterns = "/orders")
public class OrderServlet extends HttpServlet {
    OrderBO orderBO=BoFactory.getBoFactory().getBO(BoFactory.BOType.ORDER_BO);
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<OrderDTO> orderDTOS=new ArrayList<>();
        try(Connection connection=pool.getConnection()) {
            orderDTOS=orderBO.getAll(connection);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        Jsonb jsonb = JsonbBuilder.create();
        jsonb.toJson(orderDTOS,resp.getWriter());
    }
}