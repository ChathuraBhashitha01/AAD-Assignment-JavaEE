package lk.ijse.gdse.pos.pos_server_javaEE.db;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener(value = "servletContextListener")
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("1234");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/pos_server");
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setInitialSize(2);
        basicDataSource.setMaxTotal(5);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dbcp",basicDataSource);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
