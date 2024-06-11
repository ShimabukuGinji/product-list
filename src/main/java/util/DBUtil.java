package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_user_management", "testuser", "test");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}