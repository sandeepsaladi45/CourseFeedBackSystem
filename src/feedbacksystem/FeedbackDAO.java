package feedbacksystem;

import java.sql.*;

public class FeedbackDAO {

    public boolean addFeedback(Feedback feedback) {

        String sql = "INSERT INTO feedback(student_sno, course_id, rating, comments) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, feedback.getStudentId());
            ps.setInt(2, feedback.getCourseId());
            ps.setInt(3, feedback.getRating());
            ps.setString(4, feedback.getComments());

            int result = ps.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet viewFeedback() {

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT s.name, c.course_name, f.rating, f.comments " +
                    "FROM feedback f " +
                    "JOIN students s ON f.student_sno = s.sno " +
                    "JOIN courses c ON f.course_id = c.course_id";

            Statement st = conn.createStatement();
            return st.executeQuery(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}