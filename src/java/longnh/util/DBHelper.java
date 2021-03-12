/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author LongNH
 */
public class DBHelper {
    public static Connection makeConnection() throws SQLException, ClassNotFoundException {

        //1.load driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        //2.create connectionString
        String url = "jdbc:sqlserver://localhost:1433;databaseName=QUIZONLINE_LAB";

        //3.Open connection
        Connection con = DriverManager.getConnection(url, "sa", "123456");

        return con;
    }
}
