/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longnh.question.QuestionDAO;
import longnh.question.QuestionDTO;
import longnh.subject.SubjectDAO;
import longnh.subject.SubjectDTO;

/**
 *
 * @author LongNH
 */
@WebServlet(name = "LoadAdminPageServlet", urlPatterns = {"/LoadAdminPageServlet"})
public class LoadAdminPageServlet extends HttpServlet {
    
    private final String ADMIN_HOME_PAGE = "admin-home-page.jsp";
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
        
        String url = ADMIN_HOME_PAGE;
        try {
            QuestionDAO questionDAO = new QuestionDAO();
            questionDAO.loadListQuestion(1);
            List<QuestionDTO> listQuestion = questionDAO.getListQuestion();
            request.setAttribute("LIST_ALL_QUESTION", listQuestion);
            
            SubjectDAO subjectDAO = new SubjectDAO();
            subjectDAO.getAllSubject();
            List<SubjectDTO> listSubject1 = subjectDAO.getListSubject();
            
            request.setAttribute("SUBJECT", listSubject1);
            
            int size = questionDAO.countQuestion();
            int sizePage = 10;
            int endPage = size / sizePage;
            if (size % sizePage != 0) {
                endPage++;
            }
            request.setAttribute("END_PAGE", endPage);
        } catch (SQLException ex) {
            log("LoadAdminPageServlet _ " + ex.getMessage());
            url = ERRORS;
        } catch (ClassNotFoundException ex) {
            log("LoadAdminPageServlet _ " + ex.getMessage());
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
