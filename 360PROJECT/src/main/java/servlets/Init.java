/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.InitDatabase;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giann
 */
@WebServlet(name = "Init", urlPatterns = {"/Init"})
public class Init extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintStream fileOut = new PrintStream(new File("C:\\CSD\\PENDING\\HY-360\\CSD360-PROJECT\\360PROJECT\\src\\main\\webapp\\js\\logfile.txt"));
        System.setOut(fileOut);

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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
