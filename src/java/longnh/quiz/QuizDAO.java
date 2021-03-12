/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.quiz;

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
public class QuizDAO implements Serializable {

    private List<QuizDTO> listQuiz;

    public List<QuizDTO> getListQuiz() {
        return listQuiz;
    }

    public int getQuizAmount()
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Make Connection
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create SQL Statement
                String sql = "SELECT COUNT(*) FROM Quiz";

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

    public boolean createNewQuiz(int quizID, String email, String dateTakeQuiz,
            String timeTakeQuiz, double point, int questionAmount, String subjectID)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Quiz (quizID, email, dateTakeQuiz, "
                        + "timeTakeQuiz, point, questionAmount, subjectID) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

                stm = con.prepareStatement(sql);
                stm.setInt(1, quizID);
                stm.setString(2, email);
                stm.setString(3, dateTakeQuiz);
                stm.setString(4, timeTakeQuiz);
                stm.setDouble(5, point);
                stm.setInt(6, questionAmount);
                stm.setString(7, subjectID);

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

    public boolean updatePointPlus(int quizID, double point)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE Quiz "
                        + "SET point = point + ? "
                        + "WHERE quizID = ?";

                stm = con.prepareStatement(sql);

                stm.setDouble(1, point);
                stm.setInt(2, quizID);

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

    public boolean updatePointMinus(int quizID, double point)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE Quiz "
                        + "SET point = point - ? "
                        + "WHERE quizID = ?";

                stm = con.prepareStatement(sql);

                stm.setDouble(1, point);
                stm.setInt(2, quizID);

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

    public QuizDTO getQuizByID(int quizID, String email)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT dateTakeQuiz, timeTakeQuiz, questionAmount, point, subjectID "
                        + "FROM Quiz "
                        + "WHERE quizID = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, quizID);

                rs = stm.executeQuery();

                while (rs.next()) {
                    String dateTakeQuiz = rs.getString("dateTakeQuiz");
                    String timeTakeQuiz = rs.getString("timeTakeQuiz");
                    int questionAmount = rs.getInt("questionAmount");
                    int point = rs.getInt("point");
                    String subjectID = rs.getString("subjectID");

                    QuizDTO quizDTO = new QuizDTO(quizID, email, dateTakeQuiz, timeTakeQuiz, point, questionAmount, subjectID);

                    return quizDTO;
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

    public void loadListQuizByEmail(int index, String email, String subject)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Make Connection
            String a = "";
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create SQL Statement
                if (!subject.equals("Subject:")) {
                    a = a + "and subjectID = ? ";
                }
                String sql = "SELECT "
                        + "quizID, dateTakeQuiz, timeTakeQuiz, questionAmount, point, subjectID "
                        + "FROM "
                        + "( SELECT ROW_NUMBER() over (ORDER BY dateTakeQuiz DESC) as r, * "
                        + "FROM Quiz WHERE email = ? " + a + ") as x "
                        + "WHERE r BETWEEN ? * 10 - 9 and ? * 10";

                //3. Create Statemanet
                stm = con.prepareStatement(sql);
                if (!subject.equals("Subject:")) {
                    stm.setString(1, email);
                    stm.setString(2, subject);
                    stm.setInt(3, index);
                    stm.setInt(4, index);
                } else {
                    stm.setString(1, email);
                    stm.setInt(2, index);
                    stm.setInt(3, index);
                }
                //4. Query data
                rs = stm.executeQuery();
                while (rs.next()) {
                    int quizID = Integer.parseInt(rs.getString("quizID"));
                    String dateTakeQuiz = rs.getString("dateTakeQuiz");
                    String timeTakeQuiz = rs.getString("timeTakeQuiz");
                    int questionAmount = rs.getInt("questionAmount");
                    int point = rs.getInt("point");
                    String subjectID = rs.getString("subjectID");

                    QuizDTO quizDTO = new QuizDTO(quizID, email, dateTakeQuiz, timeTakeQuiz, point, questionAmount, subjectID);

                    if (this.listQuiz == null) {
                        this.listQuiz = new ArrayList<>();
                    }

                    this.listQuiz.add(quizDTO);
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
    }

    public int getQuizAmountByEmail(String email)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Make Connection
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create SQL Statement
                String sql = "SELECT COUNT(*) FROM Quiz WHERE email = ?";

                //3. Create Statemanet
                stm = con.prepareStatement(sql);
                stm.setString(1, email);

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

    public static void main(String args[]) {
        String str1 = new String("This is a test String ");
        String str2 = new String("Test ABC");
        boolean var1 = str1.endsWith("String ");
        boolean var2 = str1.endsWith("ABC");
        boolean var3 = str2.endsWith("String");
        boolean var4 = str2.endsWith("ABC");
        System.out.println("str1 ends with String: " + var1);
        System.out.println("str1 ends with ABC: " + var2);
        System.out.println("str2 ends with String: " + var3);
        System.out.println("str2 ends with ABC: " + var4);
    }
}
