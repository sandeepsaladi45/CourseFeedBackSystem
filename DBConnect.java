package feedbacksystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

    public static Connection getConnection() {

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/feedback_system",
                    "root",
                    "S@ndeep45"   // <-- change if needed
            );

            System.out.println("=================================");
            System.out.println("[INFO] Database Connected");
            System.out.println("=================================");
            return conn;

        } catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }

        return null;
    }
}