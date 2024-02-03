package lk.ijse.gdse.pos.pos_server_javaEE.api.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.BoFactory;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.CustomerBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.CustomerDTO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
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

@WebServlet(name = "customerServlet",urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {
    CustomerBO customerBO= BoFactory.getBoFactory().getBO(BoFactory.BOType.CUSTOMER_BO);
    DataSource pool;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext initialContext = new InitialContext();
            pool = (DataSource) initialContext.lookup("java:/comp/env/jdbc/pos");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArrayList<CustomerDTO> customerArray= new ArrayList<>();
        try(Connection connection= pool.getConnection()) {
            customerArray = customerBO.getAll(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Jsonb jsonb = JsonbBuilder.create();
        jsonb.toJson(customerArray,resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

        if (customerDTO.getId()==null||customerDTO.getId().matches("/^(C00-)[0-9]{3}$/") ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"ID is empty or invalid");
        }else if (customerDTO.getName()==null||customerDTO.getName().matches("/^[A-Za-z ]{2,}$/") ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Name is empty or invalid");
        }else if (customerDTO.getAddress()==null||customerDTO.getAddress().matches("/^[A-Za-z0-9 ]{5,}$/") ) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Address is empty or invalid");
        }else {
            try(Connection connection= pool.getConnection()) {
                boolean isSaved = customerBO.saveCustomer(customerDTO, connection);
                if (isSaved){
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                }
                else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to save the customer");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

        if (customerDTO.getId()==null||customerDTO.getId().matches("/^(C00-)[0-9]{3}$/") ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"ID is empty or invalid");
        }else if (customerDTO.getName()==null||customerDTO.getName().matches("/^[A-Za-z ]{2,}$/") ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Name is empty or invalid");
        }else if (customerDTO.getAddress()==null||customerDTO.getAddress().matches("/^[A-Za-z0-9 ]{5,}$/") ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Address is empty or invalid");
        }else {
            try(Connection connection= pool.getConnection()) {
                boolean isUpdated = customerBO.updateCustomer(customerDTO, connection);

                if (isUpdated){
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                }
                else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to update the customer");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id=req.getParameter("id");
        if (id==null||id.matches("/^(C00-)[0-9]{3}$/") ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"ID is empty or invalid");
        }else {
            try(Connection connection= pool.getConnection()) {
                boolean isDelete = customerBO.deleteCustomer(id, connection);

                if (isDelete){
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                }
                else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to delete the customer");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
