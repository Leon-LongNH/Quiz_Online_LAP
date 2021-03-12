/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longnh.subject.SubjectDAO;
import longnh.subject.SubjectDTO;

/**
 *
 * @author LongNH
 */
@WebServlet(name = "CreateSubjectServlet", urlPatterns = {"/CreateSubjectServlet"})
public class CreateSubjectServlet extends HttpServlet {
    
    private final String CREATE_SUBJECT_PAGE = "create-subject-page.jsp";
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

        String subjectID = request.getParameter("subjectID");
        String subjectName = request.getParameter("subjectName");
        String hour = request.getParameter("hour");
        String minute = request.getParameter("minute");
        String amount = request.getParameter("questionAmount");
        
        String url = CREATE_SUBJECT_PAGE;
        
        try {
            SubjectDAO subjectDAO = new SubjectDAO();
            SubjectDTO subjectByID = subjectDAO.getSubjectByID(subjectID);
            if (subjectByID != null) {
                request.setAttribute("ERRORS", "Subject ID existed!!!!");
            } else {
                String timeTakeQuiz = hour + ":" + minute;
                subjectDAO.createNewSubject(subjectID, subjectName, timeTakeQuiz, Integer.parseInt(amount), true);
            }
            
            request.setAttribute("ID", subjectID);
            request.setAttribute("NAME", subjectName);
        } catch (SQLException ex) {
            log("CreateSubjectServlet _ " + ex.getMessage());
            url = ERRORS;
        } catch (ClassNotFoundException ex) {
            log("CreateSubjectServlet _ " + ex.getMessage());
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
