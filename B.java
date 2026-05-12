// Severity 8 — SQL Injection Vulnerability
import java.sql.*;

public class B {
    public static void main(String[] args) throws Exception {

        String userInput = "' OR '1'='1";

        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/test",
            "root",
            "password"
        );

        Statement stmt = con.createStatement();

        String query = "SELECT * FROM users WHERE username = '" + userInput + "'";

        ResultSet rs = stmt.executeQuery(query);

        while(rs.next()){
            System.out.println(rs.getString("username"));
        }
    }
}