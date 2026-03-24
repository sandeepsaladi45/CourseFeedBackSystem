package feedbacksystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class FeedbackPage extends JFrame {

    JComboBox<String> studentBox;
    JComboBox<String> courseBox;
    JComboBox<Integer> ratingBox;
    JTextArea commentArea;

    public FeedbackPage() {
        setTitle("Course Feedback System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout());
        add(mainPanel);

        // Glass Panel
        JPanel glassPanel = new JPanel();
        glassPanel.setPreferredSize(new Dimension(500, 500));
        glassPanel.setBackground(new Color(255, 255, 255, 30));
        glassPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1));
        glassPanel.setLayout(new BorderLayout(20, 20));
        
        mainPanel.add(glassPanel);

        // Header inside Glass Panel
        JPanel header = new JPanel();
        header.setOpaque(false);
        JLabel title = new JLabel("Submit New Feedback");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        header.add(title);
        glassPanel.add(header, BorderLayout.NORTH);

        // Form Fields
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        styleLabelAndAdd(fieldsPanel, "Student (Reg No):", 0, gbc);
        studentBox = new JComboBox<>();
        styleComboBox(studentBox);
        gbc.gridx = 1; fieldsPanel.add(studentBox, gbc);

        styleLabelAndAdd(fieldsPanel, "Course Name:", 1, gbc);
        courseBox = new JComboBox<>();
        styleComboBox(courseBox);
        gbc.gridx = 1; fieldsPanel.add(courseBox, gbc);

        styleLabelAndAdd(fieldsPanel, "Rating:", 2, gbc);
        ratingBox = new JComboBox<>(new Integer[]{5, 4, 3, 2, 1});
        styleComboBox(ratingBox);
        gbc.gridx = 1; fieldsPanel.add(ratingBox, gbc);

        styleLabelAndAdd(fieldsPanel, "Comments:", 3, gbc);
        commentArea = new JTextArea(4, 20);
        commentArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        commentArea.setBackground(new Color(0, 0, 0, 50));
        commentArea.setForeground(Color.WHITE);
        commentArea.setCaretColor(Color.WHITE);
        commentArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255,255,255,50)),
            BorderFactory.createEmptyBorder(5,5,5,5)
        ));
        gbc.gridx = 1; fieldsPanel.add(new JScrollPane(commentArea), gbc);

        glassPanel.add(fieldsPanel, BorderLayout.CENTER);

        // Footer / Submit
        JButton submitBtn = new JButton("Submit Feedback");
        styleButton(submitBtn);
        submitBtn.addActionListener(e -> submitFeedback());
        
        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        footer.add(submitBtn);
        glassPanel.add(footer, BorderLayout.SOUTH);

        loadStudents();
        loadCourses();
        
        setVisible(true);
    }

    private void styleLabelAndAdd(JPanel panel, String text, int y, GridBagConstraints gbc) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(label, gbc);
    }

    private void styleComboBox(JComboBox<?> box) {
        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        box.setBackground(new Color(255, 255, 255, 200));
        box.setPreferredSize(new Dimension(200, 30));
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(0, 120, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(200, 40));
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, new Color(15, 32, 67), 0, getHeight(), new Color(30, 80, 160));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void loadStudents() {
        String sql = "SELECT registration_number FROM students";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                studentBox.addItem(rs.getString("registration_number"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadCourses() {
        String sql = "SELECT course_name FROM courses";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                courseBox.addItem(rs.getString("course_name"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void submitFeedback() {
        String regNo = (String) studentBox.getSelectedItem();
        String courseName = (String) courseBox.getSelectedItem();
        Integer rating = (Integer) ratingBox.getSelectedItem();
        String comments = commentArea.getText().trim();

        if (comments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide comments!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Get IDs
            int sId = 0, cId = 0;
            String sSql = "SELECT sno FROM students WHERE registration_number = ?";
            String cSql = "SELECT course_id FROM courses WHERE course_name = ?";
            
            try (PreparedStatement ps = conn.prepareStatement(sSql)) {
                ps.setString(1, regNo);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) sId = rs.getInt("sno");
            }
            try (PreparedStatement ps = conn.prepareStatement(cSql)) {
                ps.setString(1, courseName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) cId = rs.getInt("course_id");
            }

            Feedback feedback = new Feedback(sId, cId, rating, comments);
            if (new FeedbackDAO().addFeedback(feedback)) {
                JOptionPane.showMessageDialog(this, "Feedback Submitted!");
                commentArea.setText("");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}