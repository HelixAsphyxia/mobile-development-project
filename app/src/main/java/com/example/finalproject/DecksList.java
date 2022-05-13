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
import com.example.finalproject.recyclers.DeckRecyclerAdapter;
import com.example.finalproject.repositories.DeckRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DecksList extends AppCompatActivity {
    private FloatingActionButton btnCreate;
    private RecyclerView list;

    private DeckRepository repository;

    private Class target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decks_list);

        target = EditDeck.class;
        repository = new DeckRepository(this);

        btnCreate = findViewById(R.id.btn_create);
        list = findViewById(R.id.list);

        btnCreate.setOnClickListener(this::goToCreateDeck);
        fillDecks(repository.getAllDecks(), list);

        if (getIntent().getBooleanExtra("practice", false)) {
            setTargetToPractice();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnCreate.setOnClickListener(this::goToCreateDeck);
        fillDecks(repository.getAllDecks(), list);
    }

    public void goToCreateDeck(View view) {
        Intent to = new Intent(this, EditDeck.class);

        startActivity(to);
    }

    private void fillDecks(List<Deck> decks, RecyclerView list) {
        DeckRecyclerAdapter adapter = new DeckRecyclerAdapter(decks, this, target);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());

        list.setLayoutManager(layout);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapter);
    }

    private void setTargetToPractice() {
        btnCreate.setVisibility(View.INVISIBLE);
        target = Practice.class;
    }

}