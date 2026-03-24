package feedbacksystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPage extends JFrame {

    JTextField nameField, emailField, regField;

    public LoginPage() {
        setTitle("Course Feedback Login");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // Top Bar for Reload Button
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topBar.setOpaque(false);
        JButton reloadBtn = new JButton("↻");
        reloadBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 24));
        reloadBtn.setForeground(Color.WHITE);
        reloadBtn.setContentAreaFilled(false);
        reloadBtn.setBorderPainted(false);
        reloadBtn.setFocusPainted(false);
        reloadBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        reloadBtn.setToolTipText("Refresh Form");
        reloadBtn.addActionListener(e -> {
            nameField.setText("");
            emailField.setText("");
            regField.setText("");
        });
        topBar.add(reloadBtn);
        mainPanel.add(topBar, BorderLayout.NORTH);

        // Center Wrapper
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);

        // Glass Panel
        JPanel glassPanel = new JPanel();
        glassPanel.setPreferredSize(new Dimension(380, 400));
        glassPanel.setBackground(new Color(255, 255, 255, 30));
        glassPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1));
        glassPanel.setLayout(new GridBagLayout());

        centerWrapper.add(glassPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("Course Feedback Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        glassPanel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Form Fields
        styleLabel(glassPanel, "Name:", 1, gbc);
        nameField = new JTextField(15);
        styleTextField(nameField);
        gbc.gridx = 1;
        glassPanel.add(nameField, gbc);

        styleLabel(glassPanel, "Email:", 2, gbc);
        emailField = new JTextField(15);
        styleTextField(emailField);
        gbc.gridx = 1;
        glassPanel.add(emailField, gbc);

        styleLabel(glassPanel, "Reg No:", 3, gbc);
        regField = new JTextField(15);
        styleTextField(regField);
        gbc.gridx = 1;
        glassPanel.add(regField, gbc);

        // Button
        JButton loginBtn = new JButton("LOGIN");
        styleButton(loginBtn);
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        glassPanel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> loginStudent());
    }

    private void styleLabel(JPanel panel, String text, int y, GridBagConstraints gbc) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(label, gbc);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setOpaque(true);
        field.setBackground(new Color(15, 45, 90));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 70)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(0, 123, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    }

    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, new Color(8, 20, 45), 0, getHeight(), new Color(15, 60, 130));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void loginStudent() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String regNo = regField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || regNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Every field is required for temporary access!");
            return;
        }

        // ---------------------------------------------------------
        // TEMPORARY BYPASS: Accept anyone who filled all fields
        // ---------------------------------------------------------
        JOptionPane.showMessageDialog(this, "Welcome " + name + "!");

        // Pass a temporary SNO (e.g., 999) and the entered name/email to Dashboard
        new DashboardPage(999, name, email).setVisible(true);
        dispose();

        /*
         * // ---------------------------------------------------------
         * // REAL DATABASE LOGIN (Uncomment this later & delete the bypass above)
         * // ---------------------------------------------------------
         * try (Connection conn = DBConnection.getConnection()) {
         * String sql = "SELECT * FROM students WHERE registration_number = ?";
         * PreparedStatement ps = conn.prepareStatement(sql);
         * ps.setString(1, regNo);
         * 
         * ResultSet rs = ps.executeQuery();
         * 
         * if (rs.next()) {
         * if (rs.getBoolean("login_status")) {
         * JOptionPane.showMessageDialog(this, "Already logged in!");
         * return;
         * }
         * 
         * int sno = rs.getInt("sno");
         * String dbName = rs.getString("name");
         * String dbEmail = rs.getString("email");
         * 
         * // Update status to logged in
         * String updateSql = "UPDATE students SET login_status = TRUE WHERE sno = ?";
         * PreparedStatement psUpdate = conn.prepareStatement(updateSql);
         * psUpdate.setInt(1, sno);
         * psUpdate.executeUpdate();
         * 
         * JOptionPane.showMessageDialog(this, "Login Successful!");
         * new DashboardPage(sno, dbName, dbEmail).setVisible(true);
         * dispose();
         * } else {
         * JOptionPane.showMessageDialog(this, "Invalid Registration Number!");
         * }
         * } catch (Exception e) {
         * e.printStackTrace();
         * JOptionPane.showMessageDialog(this, "Database error occurred.");
         * }
         */
    }
}
