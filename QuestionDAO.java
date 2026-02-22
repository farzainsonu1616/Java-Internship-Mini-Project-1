package com.quiz.app.dao;

import com.quiz.app.model.Question;
import com.quiz.app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public List<Question> getRandomQuestions(int quizId) {
        List<Question> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM questions WHERE quiz_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, quizId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Question q = new Question();
                q.setId(rs.getInt("id"));
                q.setQuestionText(rs.getString("question_text"));
                q.setOptionA(rs.getString("option_a"));
                q.setOptionB(rs.getString("option_b"));
                q.setOptionC(rs.getString("option_c"));
                q.setOptionD(rs.getString("option_d"));
                q.setCorrectOption(rs.getString("correct_option").charAt(0));

                list.add(q);
            }

            System.out.println("DEBUG: Loaded questions = " + list.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}