/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.quiz;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author LongNH
 */
public class QuizDTO implements Serializable {

    private int quizID;
    private String email;
    private String dateTakeQuiz;
    private String timeTakeQuiz;
    private double point;
    private int questionAmount;
    private String subjectID;
    
    public QuizDTO() {
    }

    public QuizDTO(int quizID, String email, String dateTakeQuiz, String timeTakeQuiz, double point, int questionAmount, String subjectID) {
        this.quizID = quizID;
        this.email = email;
        this.dateTakeQuiz = dateTakeQuiz;
        this.timeTakeQuiz = timeTakeQuiz;
        this.point = point;
        this.questionAmount = questionAmount;
        this.subjectID = subjectID;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateTakeQuiz() {
        return dateTakeQuiz;
    }

    public void setDateTakeQuiz(String dateTakeQuiz) {
        this.dateTakeQuiz = dateTakeQuiz;
    }

    public String getTimeTakeQuiz() {
        return timeTakeQuiz;
    }

    public void setTimeTakeQuiz(String timeTakeQuiz) {
        this.timeTakeQuiz = timeTakeQuiz;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public int getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(int questionAmount) {
        this.questionAmount = questionAmount;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.quizID;
        hash = 79 * hash + Objects.hashCode(this.email);
        hash = 79 * hash + Objects.hashCode(this.dateTakeQuiz);
        hash = 79 * hash + Objects.hashCode(this.timeTakeQuiz);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.point) ^ (Double.doubleToLongBits(this.point) >>> 32));
        hash = 79 * hash + this.questionAmount;
        hash = 79 * hash + Objects.hashCode(this.subjectID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QuizDTO other = (QuizDTO) obj;
        if (this.quizID != other.quizID) {
            return false;
        }
        if (Double.doubleToLongBits(this.point) != Double.doubleToLongBits(other.point)) {
            return false;
        }
        if (this.questionAmount != other.questionAmount) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.dateTakeQuiz, other.dateTakeQuiz)) {
            return false;
        }
        if (!Objects.equals(this.timeTakeQuiz, other.timeTakeQuiz)) {
            return false;
        }
        if (!Objects.equals(this.subjectID, other.subjectID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "QuizDTO{" + "quizID=" + quizID + ", email=" + email + ", dateTakeQuiz=" + dateTakeQuiz + ", timeTakeQuiz=" + timeTakeQuiz + ", point=" + point + ", questionAmount=" + questionAmount + ", subjectID=" + subjectID + '}';
    }

}
