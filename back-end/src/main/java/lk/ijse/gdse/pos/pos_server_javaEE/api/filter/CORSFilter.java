package lk.ijse.gdse.pos.pos_server_javaEE.api.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CORSFilter",urlPatterns = "/*")
public class CORSFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String origin = req.getHeader("origin");
//
//        if (origin==null){
//            res.sendError(HttpServletResponse.SC_BAD_REQUEST,"CORS Policy Violation");
//        }

        res.addHeader("Access-Control-Allow-Origin","*");
        res.addHeader("Access-Control-Allow-Headers","content-Type");
        res.addHeader("Access-Control-Allow-Methods","GET,POST,DELETE,PUT,OPTION");
         chain.doFilter(req,res);
    }
}
