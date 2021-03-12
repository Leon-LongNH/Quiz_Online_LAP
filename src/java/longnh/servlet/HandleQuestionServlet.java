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
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longnh.question.QuestionDTO;
import longnh.questionchoice.QuestionChoiceDAO;
import longnh.questionchoice.QuestionChoiceDTO;
import longnh.quiz.QuizDAO;
import longnh.userquestionanswer.UserQuestionAnswerDAO;

/**
 *
 * @author LongNH
 */
@WebServlet(name = "HandleQuestionServlet", urlPatterns = {"/HandleQuestionServlet"})
public class HandleQuestionServlet extends HttpServlet {

    private final String QUIZ_PAGE = "quiz-page.jsp";
    private final String ERRORS = "errors-page.html";
    private final String FINISH_PAGE = "LoadDonePageServlet";

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

        int questionNumber = Integer.parseInt(request.getParameter("QuestionNumber"));
        String userAnswer = request.getParameter("Answer");
        if (userAnswer == null) {
            userAnswer = "NOT ANSWER";
        }
        String action = request.getParameter("btAction");
        String url = QUIZ_PAGE;

        try {
            if (!action.equals("Time out")) {
                QuizDAO quizDAO = new QuizDAO();
                //1. Get list question, quizID, map question.
                HttpSession session = request.getSession(false);

                int quizID = (int) session.getAttribute("QUIZID");

                List<QuestionDTO> listQuestion = (List<QuestionDTO>) session.getAttribute("QUESTION_LIST");

                Map<QuestionDTO, Boolean> questionCorrect = (Map<QuestionDTO, Boolean>) session.getAttribute("CHECK_QUESTION");

                Map<QuestionDTO, String> questionAnswer = (Map<QuestionDTO, String>) session.getAttribute("QUESTION_ANSWER");

                if (listQuestion != null) {
                    //2. Check answer is correct or not

                    QuestionChoiceDAO questionChoiceDAO = new QuestionChoiceDAO();
                    boolean checkChoice = questionChoiceDAO.checkChoice(userAnswer, listQuestion.get(questionNumber - 1).getQuestionID());

                    //3. update answer from db
                    UserQuestionAnswerDAO userQuestionAnswerDAO = new UserQuestionAnswerDAO();
                    userQuestionAnswerDAO.updateAnswer(quizID,
                            listQuestion.get(questionNumber - 1).getQuestionID(), userAnswer, checkChoice);

                    //4. update point from quiz
                    //get point for every answer
                    double pointOfanswer = 10 / Double.parseDouble(listQuestion.size()+"");
                    System.out.println(10 / listQuestion.size());
                    System.out.println(pointOfanswer);
                    if (checkChoice == true && questionCorrect.get(listQuestion.get(questionNumber - 1)) == false) {
                        quizDAO.updatePointPlus(quizID, pointOfanswer);
                    } else if (checkChoice == true && questionCorrect.get(listQuestion.get(questionNumber - 1)) == true) {
                        //do notthing
                    } else if (checkChoice == false && questionCorrect.get(listQuestion.get(questionNumber - 1)) == false) {
                        //do nothing
                    } else if (checkChoice == false && questionCorrect.get(listQuestion.get(questionNumber - 1)) == true) {
                        quizDAO.updatePointMinus(quizID, pointOfanswer);
                    }
                    //5. set a new value for map
                    questionCorrect.put(listQuestion.get(questionNumber - 1), checkChoice);
                    questionAnswer.put(listQuestion.get(questionNumber - 1), userAnswer);

                    //6. send next question
                    if (!action.equals("Finished")) {
                        if (action.equals("Next Question")) {

                        } else if (action.equals("Previoust Question")) {
                            questionNumber--;
                            questionNumber--;
                        }
                        questionChoiceDAO.getChoiceByQuestion(listQuestion.get(questionNumber).getQuestionID());
                        List<QuestionChoiceDTO> listQuestionChoice = questionChoiceDAO.getListQuestionChoice();
                        session.setAttribute("CHECK_QUESTION", questionCorrect);
                        session.setAttribute("QUESTION_ANSWER", questionAnswer);
                        request.setAttribute("QUESTION", listQuestion.get(questionNumber));
                        request.setAttribute("ANSWER", listQuestionChoice);
                        request.setAttribute("Q_NUMBER", questionNumber + 1);
                    } else {
                        request.setAttribute("QUIZ_ID", quizID);
                        session.removeAttribute("CHECK_QUESTION");
                        session.removeAttribute("QUESTION_ANSWER");
                        session.removeAttribute("QUESTION_LIST");
                        session.removeAttribute("QUIZID");
                        session.removeAttribute("SUBJECT");
                        session.removeAttribute("AMOUNT");
                        session.removeAttribute("TIME_END");
                        url = FINISH_PAGE;
                    }
                }
            } else {
                HttpSession session = request.getSession(false);
                int quizID = (int) session.getAttribute("QUIZID");
                request.setAttribute("QUIZID", quizID);
                session.removeAttribute("CHECK_QUESTION");
                session.removeAttribute("QUESTION_ANSWER");
                session.removeAttribute("QUESTION_LIST");
                session.removeAttribute("QUIZID");
                session.removeAttribute("SUBJECT");
                session.removeAttribute("AMOUNT");
                session.removeAttribute("TIME_END");
                url = FINISH_PAGE;
            }
        } catch (SQLException ex) {
            log("HandleQuestionServlet _ " + ex.getMessage());
            url = ERRORS;
        } catch (ClassNotFoundException ex) {
            log("HandleQuestionServlet _ " + ex.getMessage());
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
