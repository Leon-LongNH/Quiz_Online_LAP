/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longnh.user.UserDAO;

/**
 *
 * @author LongNH
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    private final String LOGIN_PAGE = "login-page_1.html";
    private final String REGISTER_PAGE = "register-page.jsp";
    private final String ERRORS = "errors-page.html";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String name = request.getParameter("name");
        byte[] bytes = name.getBytes("ISO-8859-1");
        name = new String(bytes, "UTF-8");
        
        String password = request.getParameter("psw");
        String passwordre = request.getParameter("psw-repeat");

        int check = 1;
        String url = LOGIN_PAGE;

        try {
            UserDAO userDAO = new UserDAO();
            if (email.trim().length() > 50 || email.trim().length() < 6) {
                request.setAttribute("EMAIL_ERROR", "The email is out of range (max 50 char, min 6)");

                check = 0;
                url = REGISTER_PAGE;
            } else if (userDAO.checkUserExist(email.trim()) != null) {
                request.setAttribute("EMAIL_ERROR", "The email has already existed");
                check = 0;
                url = REGISTER_PAGE;
            }
            if (name.trim().length() > 50 || name.trim().length() < 2) {
                request.setAttribute("NAME_ERROR", "Your name is out of range (max 50 char, min 2)");
                check = 0;
                url = REGISTER_PAGE;
            }
            if (password.trim().length() < 6 || password.trim().length() > 50) {
                request.setAttribute("PASS_ERROR", "The password is out of range (max 50 char, min 6)");
                check = 0;
                url = REGISTER_PAGE;
            }
            if (!password.trim().equals(passwordre.trim())) {
                request.setAttribute("PASSRE_ERROR", "Not match");
                check = 0;
                url = REGISTER_PAGE;
            }
            if (check == 1) {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                // Change this to UTF-16 if needed
                md.update(password.getBytes(StandardCharsets.UTF_8));
                byte[] digest = md.digest();
                String hex = String.format("%064x", new BigInteger(1, digest));
                userDAO.register(email.trim(), hex.trim(), name.trim());
            } else {
                request.setAttribute("EMAIL", email);
                request.setAttribute("NAME", name);
                request.setAttribute("PASS", password);
                request.setAttribute("PASSRE", passwordre);
            }
        } catch (SQLException ex) {
            log("RegisterServlet _ " + ex.getMessage());
            url = ERRORS;
        } catch (ClassNotFoundException ex) {
            log("RegisterServlet _ " + ex.getMessage());
            url = ERRORS;
        } catch (NoSuchAlgorithmException ex) {
            log("RegisterServlet _ " + ex.getMessage());
            url = ERRORS;
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
