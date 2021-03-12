/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.user;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import longnh.util.DBHelper;

/**
 *
 * @author LongNH
 */
public class UserDAO implements Serializable {

    public UserDTO checkUserLogin(String emailK, String passwordK)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT email, name, password, isAdmin "
                        + "FROM [User] "
                        + "WHERE status = 1 and email = ? and password = ?";

                stm = con.prepareStatement(sql);
                stm.setString(1, emailK);
                stm.setString(2, passwordK);

                rs = stm.executeQuery();
                while (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    boolean isAdmin = rs.getBoolean("isAdmin");

                    UserDTO userDTO = new UserDTO(email, name, password, isAdmin, true);
                    return userDTO;
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

    public boolean register(String email, String password, String name) 
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO [User] (email, name, password, "
                        + "isAdmin, status) "
                        + "VALUES (?, ?, ?, ?, ?)";

                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, name);
                stm.setString(3, password);
                stm.setBoolean(4, false);
                stm.setBoolean(5, true);

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
    
    public UserDTO checkUserExist(String emailK)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT email, name, password, isAdmin "
                        + "FROM [User] "
                        + "WHERE status = 1 and email = ?";

                stm = con.prepareStatement(sql);
                stm.setString(1, emailK);

                rs = stm.executeQuery();
                while (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    boolean isAdmin = rs.getBoolean("isAdmin");

                    UserDTO userDTO = new UserDTO(email, name, password, isAdmin, true);
                    return userDTO;
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
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String text = "Trucgay@0306";
        // Change this to UTF-16 if needed
        md.update(text.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        String hex = String.format("%064x", new BigInteger(1, digest));
        System.out.println(hex);
    }
}
