package feedbacksystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardPage extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton activeButton = null;

    public DashboardPage() {

        setTitle("Course Feedback System");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // ================= HEADER =================
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Course Feedback System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        header.add(title, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        rightPanel.setOpaque(false);

        JLabel bell = new JLabel("🔔");
        bell.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));

        JLabel profile = new JLabel("👤 Student Portal");
        profile.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        profile.setForeground(Color.WHITE);

        rightPanel.add(bell);
        rightPanel.add(profile);

        header.add(rightPanel, BorderLayout.EAST);

        mainPanel.add(header, BorderLayout.NORTH);

        // ================= SIDEBAR =================
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBackground(new Color(10, 18, 35));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(40, 15, 40, 15));

        String[] menuItems = {
                "Dashboard",
                "Submit Feedback",
                "My Feedback",
                "Courses",
                "Profile",
                "Logout"
        };

        for (String item : menuItems) {

            JButton btn = createSidebarButton(item);
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(15));

            btn.addActionListener(e -> {
                setActiveButton(btn);
                switchPage(item);
            });

            if (item.equals("Dashboard")) {
                setActiveButton(btn);
            }
        }

        mainPanel.add(sidebar, BorderLayout.WEST);

        // ================= CONTENT =================
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setOpaque(false);

        contentPanel.add(createHomePage(), "Dashboard");
        contentPanel.add(createSimplePage("Submit Feedback Page"), "Submit Feedback");
        contentPanel.add(createSimplePage("My Feedback Page"), "My Feedback");
        contentPanel.add(createSimplePage("Courses Page"), "Courses");
        contentPanel.add(createSimplePage("Profile Page"), "Profile");

        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    // ================= SIDEBAR BUTTON =================
    private JButton createSidebarButton(String text) {

        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setForeground(new Color(180, 200, 230));
        button.setBackground(new Color(10, 18, 35));
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (button != activeButton) {
                    button.setForeground(Color.WHITE);
                }
            }

            public void mouseExited(MouseEvent e) {
                if (button != activeButton) {
                    button.setForeground(new Color(180, 200, 230));
                }
            }
        });

        return button;
    }

    // ================= ACTIVE BUTTON =================
    private void setActiveButton(JButton button) {

        if (activeButton != null) {
            activeButton.setBackground(new Color(10, 18, 35));
            activeButton.setForeground(new Color(180, 200, 230));
        }

        activeButton = button;

        activeButton.setBackground(new Color(30, 60, 120));
        activeButton.setForeground(Color.WHITE);
    }

    // ================= PAGE SWITCH =================
    private void switchPage(String pageName) {

        if (pageName.equals("Logout")) {
            JOptionPane.showMessageDialog(this, "Logged Out Successfully");
            System.exit(0);
        }

        cardLayout.show(contentPanel, pageName);
    }

    // ================= HOME PAGE WITH SPECIAL WELCOME =================
    private JPanel createHomePage() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JLabel welcome = new JLabel("");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 80));
        welcome.setForeground(Color.WHITE);

        panel.add(welcome);

        startWelcomeAnimation(welcome);

        return panel;
    }

    // ================= SPECIAL ANIMATION =================
    private void startWelcomeAnimation(JLabel label) {

        Timer timer = new Timer(150, null);

        timer.addActionListener(new ActionListener() {

            int stage = 0;
            int expandCount = 2;
            boolean expanding = true;

            @Override
            public void actionPerformed(ActionEvent e) {

                switch (stage) {

                    case 0:
                        label.setText("W");
                        stage++;
                        break;

                    case 1:
                        label.setText("We");
                        stage++;
                        break;

                    case 2:
                        label.setText("Wee");
                        stage++;
                        break;

                    case 3:
                        if (expanding) {
                            expandCount++;
                            label.setText("W" + "e".repeat(expandCount));
                            if (expandCount >= 8) {
                                expanding = false;
                            }
                        } else {
                            expandCount--;
                            label.setText("W" + "e".repeat(expandCount));
                            if (expandCount <= 2) {
                                stage++;
                            }
                        }
                        break;

                    case 4:
                        label.setText("Wel");
                        stage++;
                        break;

                    case 5:
                        label.setText("Welc");
                        stage++;
                        break;

                    case 6:
                        label.setText("Welco");
                        stage++;
                        break;

                    case 7:
                        label.setText("Welcom");
                        stage++;
                        break;

                    case 8:
                        label.setText("Welcome");
                        timer.stop();
                        break;
                }
            }
        });

        timer.start();
    }

    // ================= SIMPLE PAGE =================
    private JPanel createSimplePage(String text) {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setForeground(Color.WHITE);

        panel.add(label);
        return panel;
    }

    // ================= GRADIENT BACKGROUND =================
    class GradientPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            int w = getWidth();
            int h = getHeight();

            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(8, 20, 45),
                    0, h, new Color(15, 60, 130));

            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }
}