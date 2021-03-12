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
import javax.servlet.http.HttpSession;
import longnh.quiz.QuizDAO;
import longnh.quiz.QuizDTO;
import longnh.subject.SubjectDAO;
import longnh.subject.SubjectDTO;
import longnh.user.UserDTO;

/**
 *
 * @author LongNH
 */
@WebServlet(name = "LoadHistoryPageServlet", urlPatterns = {"/LoadHistoryPageServlet"})
public class LoadHistoryPageServlet extends HttpServlet {

    private final String HISTORY_PAGE = "history-page.jsp";
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

        String index = request.getParameter("index");
        if (index == null) {
            index = "1";
        }
        String subject = request.getParameter("subjectList");
        if (subject == null || subject.equals("")) {
            subject = "Subject:";
        }
        String url = HISTORY_PAGE;
        try {
            HttpSession session = request.getSession(false);
            UserDTO userDTO = (UserDTO) session.getAttribute("USER_INFOR");
            QuizDAO quizDAO = new QuizDAO();
            quizDAO.loadListQuizByEmail(Integer.parseInt(index), userDTO.getEmail(), subject);
            List<QuizDTO> listQuiz = quizDAO.getListQuiz();

            request.setAttribute("LIST_QUIZ", listQuiz);

            int size = quizDAO.getQuizAmountByEmail(userDTO.getEmail());
            int sizePage = 10;
            int endPage = size / sizePage;
            if (size % sizePage != 0) {
                endPage++;
            }

            request.setAttribute("END_PAGE", endPage);
            
            SubjectDAO subjectDAO = new SubjectDAO();
            subjectDAO.getAllSubject();
            List<SubjectDTO> listSubject1 = subjectDAO.getListSubject();

            request.setAttribute("SUBJECT", listSubject1);
            request.setAttribute("SUBJECT_KEY", subject);
        } catch (SQLException ex) {
            log("LoadHistoryPageServlet _ " + ex.getMessage());
            url = ERRORS;
        } catch (ClassNotFoundException ex) {
            log("LoadHistoryPageServlet _ " + ex.getMessage());
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
