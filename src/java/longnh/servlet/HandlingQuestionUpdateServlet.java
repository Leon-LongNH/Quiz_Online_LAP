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
import longnh.questionchoice.QuestionChoiceDTO;

/**
 *
 * @author LongNH
 */
@WebServlet(name = "HandlingQuestionUpdateServlet", urlPatterns = {"/HandlingQuestionUpdateServlet"})
public class HandlingQuestionUpdateServlet extends HttpServlet {

    private final String ADMIN_PAGE = "LoadAdminPageServlet";
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
        String questionID = request.getParameter("questionID");
        String subjectID = request.getParameter("subjectList");
        String question = request.getParameter("question");
        List<String> listAns = new ArrayList<String>();
        byte[] bytes = request.getParameter("newanswer1").getBytes("ISO-8859-1");
        String newanswer1 = new String(bytes, "UTF-8");
        listAns.add(newanswer1);
        bytes = request.getParameter("newanswer2").getBytes("ISO-8859-1");
        String newanswer2 = new String(bytes, "UTF-8");
        listAns.add(newanswer2);
        bytes = request.getParameter("newanswer3").getBytes("ISO-8859-1");
        String newanswer3 = new String(bytes, "UTF-8");
        listAns.add(newanswer3);
        bytes = request.getParameter("newanswer4").getBytes("ISO-8859-1");
        String newanswer4 = new String(bytes, "UTF-8");
        listAns.add(newanswer4);

        String newCorrect = request.getParameter("correct");

        String statusK = request.getParameter("status");
        boolean status = true;
        if (statusK.equals("Deleted")) {
            status = false;
        }
        String url = ADMIN_PAGE;
        try {
            bytes = question.getBytes("ISO-8859-1");
            question = new String(bytes, "UTF-8");
            bytes = newCorrect.getBytes("ISO-8859-1");
            newCorrect = new String(bytes, "UTF-8");

            QuestionDAO questionDAO = new QuestionDAO();
            QuestionChoiceDAO questionChoiceDAO = new QuestionChoiceDAO();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String today = dtf.format(now);
            
            questionDAO.updateQuestion(Integer.parseInt(questionID), question, subjectID, today, status);
            
            questionChoiceDAO.getChoiceByQuestion(Integer.parseInt(questionID));
            List<QuestionChoiceDTO> listQuestionChoice = questionChoiceDAO.getListQuestionChoice();
            int index = 0;
            for (QuestionChoiceDTO questionChoiceDTO : listQuestionChoice) {
                boolean correct = false;
                if (listAns.get(index).equals(newCorrect)) {
                    correct = true;
                }
                questionChoiceDAO.updateChoice(Integer.parseInt(questionID), questionChoiceDTO.getChoiceID(), listAns.get(index), correct);
                index++;
            }
        } catch (SQLException ex) {
            log("HandlingQuestionUpdateServlet _ " + ex.getMessage());
            url = ERRORS;
        } catch (ClassNotFoundException ex) {
            log("HandlingQuestionUpdateServlet _ " + ex.getMessage());
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
