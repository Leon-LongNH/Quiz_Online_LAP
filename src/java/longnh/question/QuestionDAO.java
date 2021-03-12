/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.question;

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
public class QuestionDAO implements Serializable {

    private List<QuestionDTO> listQuestion;

    public List<QuestionDTO> getListQuestion() {
        return listQuestion;
    }

    public void getRandomQuestionBySubject(String subjectIDK, int questionAmount)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT top (?) questionID, question, createDate, subjectID "
                        + "FROM Question "
                        + "WHERE subjectID = ? AND status = 1 "
                        + "ORDER BY NEWID()";

                stm = con.prepareStatement(sql);
                stm.setInt(1, questionAmount);
                stm.setString(2, subjectIDK);

                rs = stm.executeQuery();
                while (rs.next()) {
                    int questionID = rs.getInt("questionID");
                    String question = rs.getString("question");
                    String createDate = rs.getString("createDate");

                    QuestionDTO questionDTO
                            = new QuestionDTO(questionID, question, createDate, subjectIDK, true);

                    if (this.listQuestion == null) {
                        this.listQuestion = new ArrayList<>();
                    }

                    this.listQuestion.add(questionDTO);
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

    public void loadListQuestion(int index)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Make Connection
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create SQL Statement
                String sql = "SELECT "
                        + "questionID, question, createDate, subjectID, status "
                        + "FROM "
                        + "( SELECT ROW_NUMBER() over (ORDER BY createDate DESC) as r, * "
                        + "FROM Question ) as x "
                        + "WHERE r BETWEEN ? * 10 - 9 and ? * 10";

                //3. Create Statemanet
                stm = con.prepareStatement(sql);
                stm.setInt(1, index);
                stm.setInt(2, index);
                //4. Query data
                rs = stm.executeQuery();
                while (rs.next()) {
                    int questionID = rs.getInt("questionID");
                    String question = rs.getString("question");
                    String createDate = rs.getString("createDate");
                    String subjectID = rs.getString("subjectID");
                    boolean status = rs.getBoolean("status");

                    QuestionDTO questionDTO
                            = new QuestionDTO(questionID, question, createDate, subjectID, status);

                    if (this.listQuestion == null) {
                        this.listQuestion = new ArrayList<>();
                    }//end if listFoods is null

                    this.listQuestion.add(questionDTO);
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

    public int countQuestion()
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Make Connection
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create SQL Statement
                String sql = "SELECT COUNT(*) FROM Question";

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

    public int countQuestionBysubjectID(String subjectID)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Make Connection
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create SQL Statement
                String sql = "SELECT COUNT(*) FROM Question WHERE subjectID = ? AND status = 1";

                //3. Create Statemanet
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);

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
    
    public void loadListQuestionBySearch(int index, String questionK, String subject, int statusK)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String a = "";
            //1. Make Connection
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create SQL Statement
                if (!questionK.equals("NO")) {
                    a = a + "question like ? and ";
                }
                if (!subject.equals("Subject:")) {
                    a = a + "subjectID = ? and ";
                }
                if (statusK != -1) {
                    a = a + "status = ?";
                }
                if (!a.equals("")) {
                    a = "WHERE " + a;
                }
                if (a.endsWith(" and ")) {
                    a = a.substring(0, a.length() - 5);
                }
                String sql = "SELECT "
                        + "questionID, question, createDate, subjectID, status "
                        + "FROM "
                        + "( SELECT ROW_NUMBER() over (ORDER BY createDate DESC) as r, * "
                        + "FROM Question " + a + ") as x "
                        + "WHERE r BETWEEN ? * 10 - 9 and ? * 10";
                //3. Create Statemanet
                stm = con.prepareStatement(sql);
                if (!questionK.equals("NO") && !subject.equals("Subject:") && statusK != -1) {
                    stm.setString(1, "%" + questionK + "%");
                    stm.setString(2, subject);
                    stm.setInt(3, statusK);
                    stm.setInt(4, index);
                    stm.setInt(5, index);
                }
                if (!questionK.equals("NO") && subject.equals("Subject:") && statusK != -1) {
                    stm.setString(1, "%" + questionK + "%");
                    stm.setInt(2, statusK);
                    stm.setInt(3, index);
                    stm.setInt(4, index);
                }
                if (!questionK.equals("NO") && subject.equals("Subject:") && statusK == -1) {
                    stm.setString(1, "%" + questionK + "%");
                    stm.setInt(2, index);
                    stm.setInt(3, index);
                }
                if (!questionK.equals("NO") && !subject.equals("Subject:") && statusK == -1) {
                    stm.setString(1, "%" + questionK + "%");
                    stm.setString(2, subject);
                    stm.setInt(3, index);
                    stm.setInt(4, index);
                }
                if (questionK.equals("NO") && !subject.equals("Subject:") && statusK != -1) {
                    stm.setString(1, subject);
                    stm.setInt(2, statusK);
                    stm.setInt(3, index);
                    stm.setInt(4, index);
                }
                if (questionK.equals("NO") && subject.equals("Subject:") && statusK != -1) {
                    stm.setInt(1, statusK);
                    stm.setInt(2, index);
                    stm.setInt(3, index);
                }
                if (questionK.equals("NO") && subject.equals("Subject:") && statusK == -1) {
                    stm.setInt(1, index);
                    stm.setInt(2, index);
                }
                if (questionK.equals("NO") && !subject.equals("Subject:") && statusK == -1) {
                    stm.setString(1, subject);
                    stm.setInt(2, index);
                    stm.setInt(3, index);
                }
                //4. Query data
                rs = stm.executeQuery();
                while (rs.next()) {
                    int questionID = rs.getInt("questionID");
                    String question = rs.getString("question");
                    String createDate = rs.getString("createDate");
                    String subjectID = rs.getString("subjectID");
                    boolean status = rs.getBoolean("status");

                    QuestionDTO questionDTO
                            = new QuestionDTO(questionID, question, createDate, subjectID, status);

                    if (this.listQuestion == null) {
                        this.listQuestion = new ArrayList<>();
                    }//end if listFoods is null

                    this.listQuestion.add(questionDTO);
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

    public int countQuestionSearch(String questionK, String subject, int statusK)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Make Connection
            con = DBHelper.makeConnection();
            String a = "";
            if (con != null) {
                //2. Create SQL Statement

                if (!questionK.equals("NO")) {
                    a = a + "question like ? and ";
                }
                if (!subject.equals("Subject:")) {
                    a = a + "subjectID = ? and ";
                }
                if (statusK != -1) {
                    a = a + "status = ?";
                }
                if (!a.equals("")) {
                    a = "WHERE " + a;
                }
                if (a.endsWith(" and ")) {
                    a = a.substring(0, a.length() - 5);
                }
                String sql = "SELECT COUNT(*) FROM Question " + a;

                //3. Create Statemanet
                stm = con.prepareStatement(sql);
                if (!questionK.equals("NO") && !subject.equals("Subject:") && statusK != -1) {
                    stm.setString(1, "%" + questionK + "%");
                    stm.setString(2, subject);
                    stm.setInt(3, statusK);
                }
                if (!questionK.equals("NO") && subject.equals("Subject:") && statusK != -1) {
                    stm.setString(1, "%" + questionK + "%");
                    stm.setInt(2, statusK);
                }
                if (!questionK.equals("NO") && subject.equals("Subject:") && statusK == -1) {
                    stm.setString(1, "%" + questionK + "%");
                }
                if (!questionK.equals("NO") && !subject.equals("Subject:") && statusK == -1) {
                    stm.setString(1, "%" + questionK + "%");
                    stm.setString(2, subject);
                }
                if (questionK.equals("NO") && !subject.equals("Subject:") && statusK != -1) {
                    stm.setString(1, subject);
                    stm.setInt(2, statusK);
                }
                if (questionK.equals("NO") && subject.equals("Subject:") && statusK != -1) {
                    stm.setInt(1, statusK);
                }
                if (questionK.equals("NO") && subject.equals("Subject:") && statusK == -1) {
                }
                if (questionK.equals("NO") && !subject.equals("Subject:") && statusK == -1) {
                    stm.setString(1, subject);
                }
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

    public boolean deleteQuestion(int questionID)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE Question "
                        + "SET status = 0 "
                        + "WHERE questionID = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, questionID);

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

    public QuestionDTO selectQuestionByID(int questionID) 
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Make Connection
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Create SQL Statement
                String sql = "SELECT "
                        + "question, createDate, subjectID, status "
                        + "FROM Question WHERE questionID = ?";
                
                //3. Create Statemanet
                stm = con.prepareStatement(sql);
                stm.setInt(1, questionID);
                //4. Query data
                rs = stm.executeQuery();
                while (rs.next()) {
                    String question = rs.getString("question");
                    String createDate = rs.getString("createDate");
                    String subjectID = rs.getString("subjectID");
                    boolean status = rs.getBoolean("status");

                    QuestionDTO questionDTO
                            = new QuestionDTO(questionID, question, createDate, subjectID, status);

                    return questionDTO;
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
        return null;
    }

    public boolean updateQuestion(int questionID, String question, 
            String subjectID, String createDate, boolean status)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE Question "
                        + "SET question = ?, subjectID = ?, createDate = ?, status = ? "
                        + "WHERE questionID = ?";

                stm = con.prepareStatement(sql);
                stm.setString(1, question);
                stm.setString(2, subjectID);
                stm.setString(3, createDate);
                stm.setBoolean(4, status);
                stm.setInt(5, questionID);

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
    
    public boolean createNewQuestion (int questionID, String question, 
            String subjectID, String createDate, boolean status)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Question (questionID, question, subjectID, "
                        + "createDate, status) "
                        + "VALUES (?, ?, ?, ?, ?)";

                stm = con.prepareStatement(sql);
                stm.setInt(1, questionID);
                stm.setString(2, question);
                stm.setString(3, subjectID);
                stm.setString(4, createDate);
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
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        QuestionDAO questionDAO = new QuestionDAO();
        questionDAO.loadListQuestionBySearch(1, "Giai", "NO", -1);
        List<QuestionDTO> listQuestion1 = questionDAO.getListQuestion();
        for (QuestionDTO questionDTO : listQuestion1) {
            System.out.println(questionDTO.toString());
        }
        System.out.println(questionDAO.countQuestionSearch("Giai", "NO", -1));
    }
}
