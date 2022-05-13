package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.choosers.QuestionChooser;
import com.example.finalproject.choosers.QuestionChooserFactory;
import com.example.finalproject.choosers.RandomQuestionChooser;
import com.example.finalproject.domain.Question;
import com.example.finalproject.repositories.QuestionRepository;

import java.util.List;

public class Practice extends AppCompatActivity {
    private TextView question;

    private Button btnTrue;
    private Button btnFalse;

    private Long deckId;
    private Boolean answerShouldBeTrue;

    private QuestionRepository repository;

    private List<Question> questions;
    private QuestionChooser chooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        deckId = getIntent().getLongExtra("deckId", -1);

        SharedPreferences preferences = getSharedPreferences("configuration", Context.MODE_PRIVATE);
        repository = new QuestionRepository(this);
        questions = repository.findQuestionsByDeckId(deckId);
        chooser = QuestionChooserFactory.create(preferences.getString("order", "Random"), questions.size());

        question = findViewById(R.id.txt_statement);
        btnTrue = findViewById(R.id.btn_true);
        btnFalse = findViewById(R.id.btn_false);

        btnTrue.setOnClickListener(this::onClickTrue);
        btnFalse.setOnClickListener(this::onClickFalse);

        setNextQuestion();
    }

    private void setNextQuestion() {
        setQuestion(chooser.nextQuestion());
    }

    private void setQuestion(int index) {
        if (index < 0 || index >= questions.size()) {
            question.setText("There are no questions in this deck");
            btnTrue.setVisibility(View.INVISIBLE);
            btnFalse.setVisibility(View.INVISIBLE);
            return;
        }

        Question toSet = questions.get(index);

        question.setText(toSet.getQuestion());
        answerShouldBeTrue = toSet.getThruth();
    }

    public void onClickTrue(View view) {
        if (answerShouldBeTrue) {
            Toast.makeText(this, "Correct answer", Toast.LENGTH_SHORT).show();
            setNextQuestion();
            return;
        }

        Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
    }

    public void onClickFalse(View view) {
        if (answerShouldBeTrue) {
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Correct answer", Toast.LENGTH_SHORT).show();
        setNextQuestion();
    }
}