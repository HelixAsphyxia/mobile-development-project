package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.finalproject.domain.Deck;
import com.example.finalproject.domain.Question;
import com.example.finalproject.recyclers.DeckRecyclerAdapter;
import com.example.finalproject.recyclers.QuestionRecyclerAdapter;
import com.example.finalproject.repositories.QuestionRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class QuestionsList extends AppCompatActivity {
    private FloatingActionButton btnCreate;
    private RecyclerView list;

    private QuestionRepository repository;

    private Long deckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);

        deckId = getIntent().getLongExtra("deckId", -1);

        repository = new QuestionRepository(this);

        btnCreate = findViewById(R.id.btn_create2);
        list = findViewById(R.id.list);

        btnCreate.setOnClickListener(this::goToCreateQuestion);
        fillQuestions(repository.findQuestionsByDeckId(deckId), list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnCreate.setOnClickListener(this::goToCreateQuestion);
        fillQuestions(repository.findQuestionsByDeckId(deckId), list);
    }

    public void goToCreateQuestion(View view) {
        Intent to = new Intent(this, EditQuestion.class);

        to.putExtra("deckId", deckId);
        startActivity(to);
    }

    private void fillQuestions(List<Question> questions, RecyclerView list) {
        QuestionRecyclerAdapter adapter = new QuestionRecyclerAdapter(questions, this);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());

        list.setLayoutManager(layout);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapter);
    }
}