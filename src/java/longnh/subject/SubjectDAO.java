/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.subject;

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
public class SubjectDAO implements Serializable {

    private List<SubjectDTO> listSubject;

    public List<SubjectDTO> getListSubject() {
        return listSubject;
    }

    public void getAllSubject()
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT subjectID, subjectName,"
                        + " status, timeTakeQuiz, questionAmount "
                        + "FROM Subject";

                stm = con.prepareStatement(sql);

                rs = stm.executeQuery();
                while (rs.next()) {
                    String subjectID = rs.getString("subjectID");
                    String subjectName = rs.getString("subjectName");
                    boolean status = rs.getBoolean("status");
                    String timeTakeQuiz = rs.getString("timeTakeQuiz");
                    int questionAmount = rs.getInt("questionAmount");

                    SubjectDTO subjectDTO
                            = new SubjectDTO(subjectID, subjectName, timeTakeQuiz, questionAmount, status);

                    if (this.listSubject == null) {
                        this.listSubject = new ArrayList<>();
                    }

                    this.listSubject.add(subjectDTO);
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

    public SubjectDTO getSubjectByID(String subjectID)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT subjectName,"
                        + " status, timeTakeQuiz, questionAmount "
                        + "FROM Subject "
                        + "WHERE subjectID = ?";

                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String subjectName = rs.getString("subjectName");
                    boolean status = rs.getBoolean("status");
                    String timeTakeQuiz = rs.getString("timeTakeQuiz");
                    int questionAmount = rs.getInt("questionAmount");

                    SubjectDTO subjectDTO
                            = new SubjectDTO(subjectID, subjectName, timeTakeQuiz, questionAmount, status);

                    return subjectDTO;
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

    public boolean createNewSubject(String subjectID, String subjectName,
            String timeTakeQuiz, int questionAmount, boolean status)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Subject (subjectID, subjectName, timeTakeQuiz, "
                        + "questionAmount, status) "
                        + "VALUES (?, ?, ?, ?, ?)";

                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                stm.setString(2, subjectName);
                stm.setString(3, timeTakeQuiz);
                stm.setInt(4, questionAmount);
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

    public boolean updateSubject(String subjectID, String subjectName,
            String timeTakeQuiz, int questionAmount, boolean status)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE Subject "
                        + "SET subjectName = ?, timeTakeQuiz = ?, questionAmount = ?, status = ? "
                        + "WHERE subjectID = ?";

                stm = con.prepareStatement(sql);
                stm.setString(5, subjectID);
                stm.setString(1, subjectName);
                stm.setString(2, timeTakeQuiz);
                stm.setInt(3, questionAmount);
                stm.setBoolean(4, status);

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
        SubjectDAO subjectDAO = new SubjectDAO();
        subjectDAO.getAllSubject();
        List<SubjectDTO> listSubject1 = subjectDAO.getListSubject();

        for (SubjectDTO subjectDTO : listSubject1) {
            String[] hourMin = subjectDTO.getTimeTakeQuiz().split(":");
            int hour = Integer.parseInt(hourMin[0]);
            int mins = Integer.parseInt(hourMin[1]);
            int hoursInMins = hour * 60 + mins;
            System.out.println(hoursInMins);
        }
    }
}
