/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testenglish;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Nguyen Do Trong
 */
public class ConnectMySQl {

    public static Connection getConnection() {
        String DB_URL = "jdbc:mysql://localhost:3306/test_english";
        String USER_NAME = "root";
        String PASSWORD = "Tao@nha@123";
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);

        } catch (Exception ex) {
            System.out.println("connect fail!");

        }
        return conn;
    }
}
