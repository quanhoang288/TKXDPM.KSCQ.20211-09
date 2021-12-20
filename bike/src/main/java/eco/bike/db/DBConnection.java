package eco.bike.db;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {
    private static Connection connect;
    public static Connection getConnection(){
        if (connect != null) return connect;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Ecobike?useSSL=false";
            String user = "root";
            String password = "123456";
            connect = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {

        }
        return connect;
    }
    public static void main(String[] args) {
        DBConnection.getConnection();
    }
}
