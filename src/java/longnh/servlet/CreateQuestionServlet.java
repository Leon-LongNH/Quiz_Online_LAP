/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longnh.question.QuestionDAO;
import longnh.questionchoice.QuestionChoiceDAO;

/**
 *
 * @author LongNH
 */
@WebServlet(name = "CreateQuestionServlet", urlPatterns = {"/CreateQuestionServlet"})
public class CreateQuestionServlet extends HttpServlet {

    private final String CREATE_PAGE = "LoadSubjectForCreateServlet";
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
        
        String subjectID = request.getParameter("subjectList");
        
        String question = request.getParameter("question");
        
        List<String> listAns = new ArrayList<String>();
        byte[] bytes = request.getParameter("answer1").getBytes("ISO-8859-1");
        String answer1 = new String(bytes, "UTF-8");
        listAns.add(answer1);
        bytes = request.getParameter("answer2").getBytes("ISO-8859-1");
        String answer2 = new String(bytes, "UTF-8");
        listAns.add(answer2);
        bytes = request.getParameter("answer3").getBytes("ISO-8859-1");
        String answer3 = new String(bytes, "UTF-8");
        listAns.add(answer3);
        bytes = request.getParameter("answer4").getBytes("ISO-8859-1");
        String answer4 = new String(bytes, "UTF-8");
        listAns.add(answer4);

        String Correct = request.getParameter("correct");

        String url = CREATE_PAGE;
        try {
            bytes = question.getBytes("ISO-8859-1");
            question = new String(bytes, "UTF-8");
            bytes = Correct.getBytes("ISO-8859-1");
            Correct = new String(bytes, "UTF-8");

            QuestionDAO questionDAO = new QuestionDAO();
            QuestionChoiceDAO questionChoiceDAO = new QuestionChoiceDAO();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String today = dtf.format(now);
            int countQuestion = questionDAO.countQuestion() + 1;
            questionDAO.createNewQuestion(countQuestion, question, subjectID, today, true);

            int count = 1;
            for (String listAn : listAns) {
                int countChoice = questionChoiceDAO.countChoice() + 1;
                if (count == Integer.parseInt(Correct)) {
                    questionChoiceDAO.createNewChoice(countQuestion, countChoice, listAn, true, true);
                } else {
                    questionChoiceDAO.createNewChoice(countQuestion, countChoice, listAn, false, true);
                }
                count++;
            }
        } catch (SQLException ex) {
            log("CreateQuestionServlet _ " + ex.getMessage());
            url = ERRORS;
        } catch (ClassNotFoundException ex) {
            log("CreateQuestionServlet _ " + ex.getMessage());
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
