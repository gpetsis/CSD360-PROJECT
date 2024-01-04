package servlets;

import database.InitDatabase;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Init", urlPatterns = {"/Init"})
public class Init extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InitDatabase init = new InitDatabase();
        try {
            init.initDatabase();
            init.initTables();
            init.addToDatabaseExamples();
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(Init.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getHeader("Query");

        System.out.println(query);

        InitDatabase database = new InitDatabase();
        try {
            String responseQuery = database.ask(query);
            response.getWriter().write(responseQuery);
            response.setStatus(200);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Init.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: " + ex);
            response.setStatus(409);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
