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
import longnh.user.UserDTO;
import longnh.userquestionanswer.UserQuestionAnswerDAO;
import longnh.userquestionanswer.UserQuestionAnswerDTO;

/**
 *
 * @author LongNH
 */
@WebServlet(name = "LoadDonePageServlet", urlPatterns = {"/LoadDonePageServlet"})
public class LoadDonePageServlet extends HttpServlet {

    private final String FINISH_PAGE = "done-page.jsp";
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

        int quizID = (int) request.getAttribute("QUIZ_ID");
        
        String url = FINISH_PAGE;
        try {
            HttpSession session = request.getSession(false);
            UserDTO email = (UserDTO) session.getAttribute("USER_INFOR");
            QuizDAO quizDAO = new QuizDAO();
            QuizDTO quizByID = quizDAO.getQuizByID(quizID, email.getName());

            request.setAttribute("QUIZ", quizByID);

            UserQuestionAnswerDAO userQuestionAnswerDAO = new UserQuestionAnswerDAO();
            userQuestionAnswerDAO.getByQuizID(quizID);
            List<UserQuestionAnswerDTO> list = userQuestionAnswerDAO.getList();
            int correct = 0;
            for (UserQuestionAnswerDTO userQuestionAnswerDTO : list) {
                if (userQuestionAnswerDTO.isIsCorrect()) {
                    correct++;
                }
            }
            request.setAttribute("QUIZ_CORRECT", correct);
            
        } catch (SQLException ex) {
            log("LoadDonePageServlet _ " + ex.getMessage());
            url = ERRORS;
        } catch (ClassNotFoundException ex) {
            log("LoadDonePageServlet _ " + ex.getMessage());
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
