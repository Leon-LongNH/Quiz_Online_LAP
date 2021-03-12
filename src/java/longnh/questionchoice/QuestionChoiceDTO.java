/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.questionchoice;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author LongNH
 */
public class QuestionChoiceDTO implements Serializable {
    private int choiceID;
    private int questionID;
    private boolean isCorrect;
    private String choice;
    private boolean status;

    public QuestionChoiceDTO() {
    }

    public QuestionChoiceDTO(int choiceID, int questionID, boolean isCorrect, String choice, boolean status) {
        this.choiceID = choiceID;
        this.questionID = questionID;
        this.isCorrect = isCorrect;
        this.choice = choice;
        this.status = status;
    }

    public int getChoiceID() {
        return choiceID;
    }

    public void setChoiceID(int choiceID) {
        this.choiceID = choiceID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
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
        hash = 29 * hash + this.choiceID;
        hash = 29 * hash + this.questionID;
        hash = 29 * hash + (this.isCorrect ? 1 : 0);
        hash = 29 * hash + Objects.hashCode(this.choice);
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
        final QuestionChoiceDTO other = (QuestionChoiceDTO) obj;
        if (this.choiceID != other.choiceID) {
            return false;
        }
        if (this.questionID != other.questionID) {
            return false;
        }
        if (this.isCorrect != other.isCorrect) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.choice, other.choice)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "QuestionChoiceDTO{" + "choiceID=" + choiceID + ", questionID=" + questionID + ", isCorrect=" + isCorrect + ", choice=" + choice + ", status=" + status + '}';
    }
    
}
