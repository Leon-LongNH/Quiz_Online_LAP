/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.question;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author LongNH
 */
public class QuestionDTO implements Serializable {
    private int questionID;
    private String question;
    private String createDate;
    private String subjectID;
    private boolean status;

    public QuestionDTO() {
    }

    public QuestionDTO(int questionID, String question, String createDate, String subjectID, boolean status) {
        this.questionID = questionID;
        this.question = question;
        this.createDate = createDate;
        this.subjectID = subjectID;
        this.status = status;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.questionID;
        hash = 29 * hash + Objects.hashCode(this.question);
        hash = 29 * hash + Objects.hashCode(this.createDate);
        hash = 29 * hash + Objects.hashCode(this.subjectID);
        hash = 29 * hash + (this.status ? 1 : 0);
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
        final QuestionDTO other = (QuestionDTO) obj;
        if (this.questionID != other.questionID) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.question, other.question)) {
            return false;
        }
        if (!Objects.equals(this.createDate, other.createDate)) {
            return false;
        }
        if (!Objects.equals(this.subjectID, other.subjectID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "QuestionDTO{" + "questionID=" + questionID + ", question=" + question + ", createDate=" + createDate + ", subjectID=" + subjectID + ", status=" + status + '}';
    }
    
}
