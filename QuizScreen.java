package com.quiz.app.ui;

import com.quiz.app.dao.QuestionDAO;
import com.quiz.app.model.Question;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class QuizScreen extends JFrame {

    private JLabel questionLabel, timerLabel, progressLabel;
    private JRadioButton optA, optB, optC, optD;
    private JButton nextButton;
    private ButtonGroup group;

    private List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;
    private int timeLeft = 60;

    public QuizScreen() {
        setTitle("Online Quiz System");
        setSize(720, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        // TOP PANEL
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        progressLabel = new JLabel("Question 1");
        progressLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        timerLabel = new JLabel("Time: 60");
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        timerLabel.setForeground(Color.RED);
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        topPanel.add(progressLabel, BorderLayout.WEST);
        topPanel.add(timerLabel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // CARD PANEL
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(25, 25, 25, 25)
        ));
        cardPanel.setBackground(new Color(250, 250, 250));

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // QUESTION
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionLabel.setBorder(new EmptyBorder(0, 0, 25, 0));
        cardPanel.add(questionLabel);

        Font optionFont = new Font("Segoe UI", Font.PLAIN, 16);

        optA = createOption(optionFont);
        optB = createOption(optionFont);
        optC = createOption(optionFont);
        optD = createOption(optionFont);

        group = new ButtonGroup();
        group.add(optA);
        group.add(optB);
        group.add(optC);
        group.add(optD);

        cardPanel.add(optA);
        cardPanel.add(optB);
        cardPanel.add(optC);
        cardPanel.add(optD);

        // BOTTOM
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);

        nextButton = new JButton("Next →");
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nextButton.setPreferredSize(new Dimension(150, 45));
        nextButton.setBackground(new Color(0, 120, 215));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);

        bottomPanel.add(nextButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> nextQuestion());

        loadQuestions();
        startTimer();

        setVisible(true);
    }

    private JRadioButton createOption(Font font) {
        JRadioButton btn = new JRadioButton();
        btn.setFont(font);
        btn.setBackground(new Color(250, 250, 250));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBorder(new EmptyBorder(10, 5, 10, 5));
        return btn;
    }

    private void loadQuestions() {
        QuestionDAO dao = new QuestionDAO();
        questions = dao.getRandomQuestions(1);

        System.out.println("DEBUG loaded = " + questions.size());

        if (questions == null || questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions found in database!");
            dispose();
            return;
        }

        showQuestion();
    }

    private void showQuestion() {
        if (currentIndex >= questions.size()) {
            finishQuiz();
            return;
        }

        Question q = questions.get(currentIndex);

        progressLabel.setText("Question " + (currentIndex + 1) +
                " of " + questions.size());

        questionLabel.setText(
                "<html><body style='width:580px'>" +
                        (currentIndex + 1) + ". " +
                        q.getQuestionText() +
                        "</body></html>");

        optA.setText("<html>A. " + q.getOptionA() + "</html>");
        optB.setText("<html>B. " + q.getOptionB() + "</html>");
        optC.setText("<html>C. " + q.getOptionC() + "</html>");
        optD.setText("<html>D. " + q.getOptionD() + "</html>");

        group.clearSelection();
    }

    private void nextQuestion() {
        Question q = questions.get(currentIndex);
        char selected = getSelectedOption();

        if (selected == q.getCorrectOption()) {
            score++;
        }

        currentIndex++;
        showQuestion();
    }

    private char getSelectedOption() {
        if (optA.isSelected()) return 'A';
        if (optB.isSelected()) return 'B';
        if (optC.isSelected()) return 'C';
        if (optD.isSelected()) return 'D';
        return 'X';
    }

    private void startTimer() {
        new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);

            if (timeLeft <= 0) {
                ((Timer) e.getSource()).stop();
                finishQuiz();
            }
        }).start();
    }

    private void finishQuiz() {
        JOptionPane.showMessageDialog(this,
                "Quiz Finished!\nScore: " + score + " / " + questions.size());
        dispose();
    }
}