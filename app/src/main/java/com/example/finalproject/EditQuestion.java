package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.finalproject.domain.Question;
import com.example.finalproject.repositories.QuestionRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditQuestion extends AppCompatActivity {

    private FloatingActionButton btnSend;
    private FloatingActionButton btnDelete;

    private EditText txt_question;
    private Switch sw_truth;

    private QuestionRepository repository;

    private Long deckId;
    private Long questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        deckId = getIntent().getLongExtra("deckId", -1);
        questionId = getIntent().getLongExtra("questionId", -1);

        repository = new QuestionRepository(this);

        btnSend = findViewById(R.id.btn_send2);
        btnDelete = findViewById(R.id.btn_delete2);
        txt_question = findViewById(R.id.txt_question);
        sw_truth = findViewById(R.id.sw_truth);

        btnSend.setOnClickListener(this::createQuestion);
        populateEdit(questionId);
    }

    private void populateEdit(Long id) {
        if (id <= -1) {
            return;
        }

        Question question = repository.findQuestionById(id);

        txt_question.setText(question.getQuestion());
        sw_truth.setChecked(question.getThruth());
        btnSend.setOnClickListener(this::updateQuestion);
        btnDelete.setVisibility(View.VISIBLE);
        btnDelete.setOnClickListener(this::deleteQuestion);
    }

    public void createQuestion(View view) {
        String question = txt_question.getText().toString();
        Boolean truth = sw_truth.isChecked();

        if (validate() == false) {
            return;
        }

        try {
            repository.saveQuestion(new Question(
                    -1L,
                    question,
                    truth,
                    deckId
            ));

            Toast.makeText(this, "Question created successfully", Toast.LENGTH_SHORT).show();
        } catch(SQLiteConstraintException e) {
            Toast.makeText(this, "Error creating question", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private boolean validate() {
        String question = txt_question.getText().toString();
        Boolean truth = sw_truth.isChecked();

        if (question.length() == 0) {
            cancelWithMessage("The question should not be empty");
            return false;
        }

        return true;
    }

    private void cancelWithMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void updateQuestion(View view) {
        String question = txt_question.getText().toString();
        Boolean truth = sw_truth.isChecked();

        if (validate() == false) {
            return;
        }

        try {
            repository.updateQuestion(questionId, new Question(
                    questionId,
                    question,
                    truth,
                    deckId
            ));

            Toast.makeText(this, "Question updated successfully", Toast.LENGTH_SHORT).show();
        } catch(SQLiteConstraintException e) {
            Toast.makeText(this, "Error updating question", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    public void deleteQuestion(View view) {
        try {
            repository.deleteQuestion(questionId);
            Toast.makeText(this, "Question deleted successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error deleting question", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

}