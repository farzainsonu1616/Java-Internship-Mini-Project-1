package com.quiz.app.ui;

import com.quiz.app.dao.UserDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginScreen extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginScreen() {
        setTitle("Online Quiz Login");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== MAIN BACKGROUND =====
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(240, 244, 248));
        add(background);

        // ===== CARD PANEL =====
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(320, 200));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(20, 25, 20, 25)
        ));
        card.setLayout(new GridBagLayout());

        background.add(card); // ⭐ centers automatically

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ===== USERNAME =====
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        card.add(userLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField();
        usernameField.setFont(fieldFont);
        usernameField.setPreferredSize(new Dimension(160, 30));
        card.add(usernameField, gbc);

        // ===== PASSWORD =====
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        card.add(passLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setPreferredSize(new Dimension(160, 30));
        card.add(passwordField, gbc);

        // ===== LOGIN BUTTON =====
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 8, 5, 8);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        loginButton.setBackground(new Color(0, 120, 215));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(120, 38));

        card.add(loginButton, gbc);

        loginButton.addActionListener(e -> loginUser());

        setVisible(true);
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        UserDAO dao = new UserDAO();

        if (dao.login(username, password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            new QuizScreen();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials!");
        }
    }
}