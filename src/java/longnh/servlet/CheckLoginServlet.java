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
import javax.servlet.http.HttpSession;
import longnh.user.UserDAO;
import longnh.user.UserDTO;

/**
 *
 * @author LongNH
 */
@WebServlet(name = "CheckLoginServlet", urlPatterns = {"/CheckLoginServlet"})
public class CheckLoginServlet extends HttpServlet {

    private final String USER_PAGE = "LoadSubjectServlet";
    private final String ADMIN_PAGE = "LoadAdminPageServlet";
    private final String LOGIN_PAGE = "login-page.jsp";
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

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String url = LOGIN_PAGE;

        try {
            UserDAO userDAO = new UserDAO();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Change this to UTF-16 if needed
            md.update(password.trim().getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            String hex = String.format("%064x", new BigInteger(1, digest));
            UserDTO checkUserLogin = userDAO.checkUserLogin(username, hex);
            if (checkUserLogin != null) {
                if (checkUserLogin.isIsAdmin()) {
                    url = ADMIN_PAGE;
                } else {
                    url = USER_PAGE;
                }
                HttpSession session = request.getSession();
                session.setAttribute("USER_INFOR", checkUserLogin);
            } else {
                request.setAttribute("loginResult", false);
            }
        } catch (SQLException ex) {
            log("CheckLoginServlet _ " + ex.getMessage());
            url = ERRORS;
        } catch (ClassNotFoundException ex) {
            log("CheckLoginServlet _ " + ex.getMessage());
            url = ERRORS;
        } catch (NoSuchAlgorithmException ex) {
            log("CheckLoginServlet _ " + ex.getMessage());
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
