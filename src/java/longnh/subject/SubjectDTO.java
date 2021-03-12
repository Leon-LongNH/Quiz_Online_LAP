/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.subject;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author LongNH
 */
public class SubjectDTO implements Serializable {
    private String subjectID;
    private String subjectName;
    private String timeTakeQuiz;
    private int questionAmount;
    private boolean status;

    public SubjectDTO() {
    }

    public SubjectDTO(String subjectID, String subjectName, String timeTakeQuiz, int questionAmount, boolean status) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.timeTakeQuiz = timeTakeQuiz;
        this.questionAmount = questionAmount;
        this.status = status;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTimeTakeQuiz() {
        return timeTakeQuiz;
    }

    public void setTimeTakeQuiz(String timeTakeQuiz) {
        this.timeTakeQuiz = timeTakeQuiz;
    }

    public int getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(int questionAmount) {
        this.questionAmount = questionAmount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.subjectID);
        hash = 61 * hash + Objects.hashCode(this.subjectName);
        hash = 61 * hash + Objects.hashCode(this.timeTakeQuiz);
        hash = 61 * hash + this.questionAmount;
        hash = 61 * hash + (this.status ? 1 : 0);
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
        final SubjectDTO other = (SubjectDTO) obj;
        if (this.questionAmount != other.questionAmount) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.subjectID, other.subjectID)) {
            return false;
        }
        if (!Objects.equals(this.subjectName, other.subjectName)) {
            return false;
        }
        if (!Objects.equals(this.timeTakeQuiz, other.timeTakeQuiz)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SubjectDTO{" + "subjectID=" + subjectID + ", subjectName=" + subjectName + ", timeTakeQuiz=" + timeTakeQuiz + ", questionAmount=" + questionAmount + ", status=" + status + '}';
    }
    
}
