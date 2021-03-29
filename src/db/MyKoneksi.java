/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author arifika.aop06542
 */
public class MyKoneksi {
    public Connection con;
    public ResultSet res;
    public Statement stat;
        String driverName = "com.mysql.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://";
        String user = "gtik8869";
        String password = "arifikaprimadam";
        String serverName = "localhost";
        String port = "3306";
    
    public void db() {
        try {
            Class.forName(driverName);
            con = DriverManager.getConnection(jdbcUrl+serverName+":"+port+"/", user, password);
            stat=con.createStatement();
            System.out.println("conection berhasil");
        } catch (Exception e) {
            System.out.println("conection gagal");
        }
    
    }
    
    public void close() throws SQLException {
        con.close();
    }

}
