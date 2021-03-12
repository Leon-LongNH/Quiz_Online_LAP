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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longnh.question.QuestionDAO;
import longnh.question.QuestionDTO;
import longnh.questionchoice.QuestionChoiceDAO;
import longnh.questionchoice.QuestionChoiceDTO;
import longnh.quiz.QuizDAO;
import longnh.subject.SubjectDAO;
import longnh.subject.SubjectDTO;
import longnh.user.UserDTO;
import longnh.userquestionanswer.UserQuestionAnswerDAO;

/**
 *
 * @author LongNH
 */
@WebServlet(name = "StartAQuizServlet", urlPatterns = {"/StartAQuizServlet"})
public class StartAQuizServlet extends HttpServlet {

    private final String QUIZ_PAGE = "quiz-page.jsp";
    private final String ERRORS = "errors-page.html";
    private final String USER_PAGE = "LoadSubjectServlet";

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

        String subjectID = request.getParameter("SubjectID");

        String url = QUIZ_PAGE;

        if (subjectID == null) {
            url = USER_PAGE;
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
        try {
            HttpSession session = request.getSession();

            UserDTO userDTO = (UserDTO) session.getAttribute("USER_INFOR");

            Map<QuestionDTO, Boolean> questionCorrectCheck = (Map<QuestionDTO, Boolean>) session.getAttribute("CHECK_QUESTION");

            if (questionCorrectCheck != null) {
                List<QuestionDTO> listQuestion = (List<QuestionDTO>) session.getAttribute("QUESTION_LIST");
                QuestionChoiceDAO questionChoiceDAO = new QuestionChoiceDAO();
                questionChoiceDAO.getChoiceByQuestion(listQuestion.get(0).getQuestionID());
                List<QuestionChoiceDTO> listQuestionChoice = questionChoiceDAO.getListQuestionChoice();

                request.setAttribute("QUESTION", listQuestion.get(0));
                request.setAttribute("ANSWER", listQuestionChoice);
                request.setAttribute("Q_NUMBER", 1);
            } else {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String startTime = dtf.format(now);

                SubjectDAO subjectDAO = new SubjectDAO();
                SubjectDTO subjectByID = subjectDAO.getSubjectByID(subjectID);

                String timeTakeQuiz = subjectByID.getTimeTakeQuiz();
                int point = 0;
                int questionAmount = subjectByID.getQuestionAmount();

                QuizDAO quizDAO = new QuizDAO();
                int quizID = quizDAO.getQuizAmount() + 1;
                boolean createNewQuiz = quizDAO.createNewQuiz(quizID, userDTO.getEmail(), startTime, timeTakeQuiz, point, questionAmount, subjectID);

                if (createNewQuiz) {

                    QuestionDAO questionDAO = new QuestionDAO();
                    questionDAO.getRandomQuestionBySubject(subjectID, questionAmount);
                    List<QuestionDTO> listQuestion = questionDAO.getListQuestion();

                    String choice = "NOT ANSWER";
                    boolean isCorrect = false;
                    UserQuestionAnswerDAO userQuestionAnswerDAO
                            = new UserQuestionAnswerDAO();
                    for (QuestionDTO questionDTO : listQuestion) {
                        userQuestionAnswerDAO.createUserQuestionAnswer(quizID, questionDTO.getQuestionID(), choice, isCorrect);
                    }

                    QuestionChoiceDAO questionChoiceDAO = new QuestionChoiceDAO();
                    questionChoiceDAO.getChoiceByQuestion(listQuestion.get(0).getQuestionID());
                    List<QuestionChoiceDTO> listQuestionChoice = questionChoiceDAO.getListQuestionChoice();

                    String[] hourMin = subjectByID.getTimeTakeQuiz().split(":");
                    int hour = Integer.parseInt(hourMin[0]);
                    int mins = Integer.parseInt(hourMin[1]);
                    int timeTake = hour * 60 + mins;
                    LocalDateTime end = now.plusMinutes(timeTake);

                    Map<QuestionDTO, String> questionAnswer = new HashMap<>();
                    for (QuestionDTO questionDTO : listQuestion) {
                        questionAnswer.put(questionDTO, "NOT ANSWER");
                    }

                    Map<QuestionDTO, Boolean> questionCorrect = new HashMap<>();
                    for (QuestionDTO questionDTO : listQuestion) {
                        questionCorrect.put(questionDTO, false);
                    }

                    session.setAttribute("CHECK_QUESTION", questionCorrect);
                    session.setAttribute("QUESTION_ANSWER", questionAnswer);
                    session.setAttribute("QUESTION_LIST", listQuestion);
                    session.setAttribute("QUIZID", quizID);
                    session.setAttribute("SUBJECT", subjectID);
                    session.setAttribute("AMOUNT", questionAmount);
                    session.setAttribute("TIME_END", dtf.format(end));
                    request.setAttribute("QUESTION", listQuestion.get(0));
                    request.setAttribute("ANSWER", listQuestionChoice);
                    request.setAttribute("Q_NUMBER", 1);
                }
            }
        } catch (SQLException ex) {
            log("StartAQuizServlet _ " + ex.getMessage());
            url = ERRORS;
        } catch (ClassNotFoundException ex) {
            log("StartAQuizServlet _ " + ex.getMessage());
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
