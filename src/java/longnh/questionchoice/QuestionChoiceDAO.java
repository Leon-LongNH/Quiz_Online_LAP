/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.questionchoice;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import longnh.util.DBHelper;

/**
 *
 * @author LongNH
 */
public class QuestionChoiceDAO implements Serializable {
    private List<QuestionChoiceDTO> listQuestionChoice;

    public List<QuestionChoiceDTO> getListQuestionChoice() {
        return listQuestionChoice;
    }
    
    public void getChoiceByQuestion(int questionID)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT choiceID, isCorrect, choice "
                        + "FROM Question_Choice "
                        + "WHERE status = 1 AND questionID = ?";
                
                stm = con.prepareStatement(sql);
                stm.setInt(1, questionID);
                
                rs = stm.executeQuery();
                while (rs.next()) {
                    int choiceID = rs.getInt("choiceID");
                    boolean isCorrect = rs.getBoolean("isCorrect");
                    String choice = rs.getString("choice");
                    
                    QuestionChoiceDTO questionChoiceDTO =
                            new QuestionChoiceDTO(choiceID, questionID, isCorrect, choice, true);
                    
                    if (this.listQuestionChoice == null) {
                        this.listQuestionChoice = new ArrayList<>();
                    }
                    
                    this.listQuestionChoice.add(questionChoiceDTO);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public boolean checkChoice(String choice, int questionID) 
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT isCorrect "
                        + "FROM Question_Choice "
                        + "WHERE questionID = ? AND choice = ?";
                
                stm = con.prepareStatement(sql);
                stm.setInt(1, questionID);
                stm.setString(2, choice);
                
                rs = stm.executeQuery();
                while(rs.next()) {
                    return rs.getBoolean("isCorrect");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
    public QuestionChoiceDTO getCorrectChoiceByQuestion(int questionID)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT choiceID, isCorrect, choice "
                        + "FROM Question_Choice "
                        + "WHERE status = 1 AND questionID = ? AND isCorrect = 1";
                
                stm = con.prepareStatement(sql);
                stm.setInt(1, questionID);
                
                rs = stm.executeQuery();
                while (rs.next()) {
                    int choiceID = rs.getInt("choiceID");
                    boolean isCorrect = rs.getBoolean("isCorrect");
                    String choice = rs.getString("choice");
                    
                    QuestionChoiceDTO questionChoiceDTO =
                            new QuestionChoiceDTO(choiceID, questionID, isCorrect, choice, true);
                    
                    return questionChoiceDTO;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }
    
    public boolean updateChoice(int questionID, int choiceID, 
            String choice, boolean isCorrect)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE Question_Choice "
                        + "SET isCorrect = ?, choice = ? "
                        + "WHERE questionID = ? AND choiceID = ?";

                stm = con.prepareStatement(sql);
                stm.setBoolean(1, isCorrect);
                stm.setString(2, choice);
                stm.setInt(3, questionID);
                stm.setInt(4, choiceID);

                int executeUpdate = stm.executeUpdate();

                if (executeUpdate > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
    public boolean createNewChoice(int questionID, int choiceID, 
            String choice, boolean isCorrect, boolean status)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Question_Choice (choiceID, questionID, isCorrect, "
                        + "choice, status) "
                        + "VALUES (?, ?, ?, ?, ?)";

                stm = con.prepareStatement(sql);
                stm.setInt(1, choiceID);
                stm.setInt(2, questionID);
                stm.setBoolean(3, isCorrect);
                stm.setString(4, choice);
                stm.setBoolean(5, status);

                int executeUpdate = stm.executeUpdate();

                if (executeUpdate > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
    public int countChoice()
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Make Connection
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create SQL Statement
                String sql = "SELECT COUNT(*) FROM Question_Choice";

                //3. Create Statemanet
                stm = con.prepareStatement(sql);

                //4. Query data
                rs = stm.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
                }//end while rs is not null
            }//end if con is not null
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return 0;
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        QuestionChoiceDAO aO = new QuestionChoiceDAO();
        aO.getChoiceByQuestion(5);
        List<QuestionChoiceDTO> listQuestionChoice1 = aO.getListQuestionChoice();
        for (QuestionChoiceDTO questionChoiceDTO : listQuestionChoice1) {
            System.out.println(questionChoiceDTO.toString());
        }
        
    }
}
