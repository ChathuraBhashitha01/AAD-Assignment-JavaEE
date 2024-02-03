package lk.ijse.gdse.pos.pos_server_javaEE.api.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.BoFactory;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.ItemBO;
import lk.ijse.gdse.pos.pos_server_javaEE.dto.ItemDTO;
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

@WebServlet(name = "itemServlet",urlPatterns = "/items")
public class ItemServlet extends HttpServlet {
    ItemBO itemBO= BoFactory.getBoFactory().getBO(BoFactory.BOType.ITEM_BO);
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

        ArrayList<ItemDTO> items=new ArrayList<>();
        try(Connection connection=pool.getConnection()) {
            items=itemBO.getAll(connection);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        Jsonb jsonb = JsonbBuilder.create();
        jsonb.toJson(items,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Jsonb jsonb = JsonbBuilder.create();
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(),ItemDTO.class);

        if (itemDTO.getCode()==null||itemDTO.getCode().matches("/^(I00-)[0-9]{3}$/") ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Code is empty or invalid");
        }else if (itemDTO.getDescription()==null||itemDTO.getDescription().matches("/^[A-Za-z ]{2,}$/") ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Description is empty or invalid");
        }else {
            try(Connection connection=pool.getConnection()) {
                boolean isSaved = itemBO.saveItem(itemDTO, connection);

                if (isSaved){
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                }
                else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to save the item");
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
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(),ItemDTO.class);

        if (itemDTO.getCode()==null||itemDTO.getCode().matches("/^(I00-)[0-9]{3}$/") ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Code is empty or invalid");
        }else if (itemDTO.getDescription()==null||itemDTO.getDescription().matches("/^[A-Za-z ]{2,}$/") ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Description is empty or invalid");
        }else {
            try(Connection connection=pool.getConnection()) {
                boolean isUpdated = itemBO.updateItem(itemDTO, connection);

                if (isUpdated){
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                }
                else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to update the item");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        if (code==null||code.matches("/^(I00-)[0-9]{3}$/") ){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Code is empty or invalid");
        }else {
            try(Connection connection=pool.getConnection()) {
                boolean isDeleted = itemBO.deleteItem(code, connection);

                if (isDeleted){
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                }
                else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to delete the item");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
