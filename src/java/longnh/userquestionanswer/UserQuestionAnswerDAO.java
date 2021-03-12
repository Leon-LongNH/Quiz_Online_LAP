/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.userquestionanswer;

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
public class UserQuestionAnswerDAO implements Serializable {

    private List<UserQuestionAnswerDTO> list;

    public List<UserQuestionAnswerDTO> getList() {
        return list;
    }

    public boolean createUserQuestionAnswer(int quizID, int questionID,
            String choice, boolean isCorrect)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO User_Question_Answer (quizID, questionID,"
                        + " choice, isCorrect) "
                        + "VALUES (?, ?, ?, ?)";

                stm = con.prepareStatement(sql);
                stm.setInt(1, quizID);
                stm.setInt(2, questionID);
                stm.setString(3, choice);
                stm.setBoolean(4, isCorrect);

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

    public boolean updateAnswer(int quizID, int questionID,
            String choice, boolean isCorrect)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE User_Question_Answer "
                        + "SET choice = ?, isCorrect = ? "
                        + "WHERE quizID = ? AND questionID = ?";

                stm = con.prepareStatement(sql);
                stm.setString(1, choice);
                stm.setBoolean(2, isCorrect);
                stm.setInt(3, quizID);
                stm.setInt(4, questionID);

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

    public void getByQuizID(int quizID)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT questionID, choice, isCorrect "
                        + "FROM User_Question_Answer "
                        + "WHERE quizID = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, quizID);

                rs = stm.executeQuery();

                while (rs.next()) {
                    int questionID = rs.getInt("questionID");
                    String choice = rs.getString("choice");
                    boolean isCorrect = rs.getBoolean("isCorrect");

                    UserQuestionAnswerDTO answerDTO = new UserQuestionAnswerDTO(quizID, questionID, choice, isCorrect);

                    if (this.list == null) {
                        this.list = new ArrayList<>();
                    }
                    this.list.add(answerDTO);
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
}
