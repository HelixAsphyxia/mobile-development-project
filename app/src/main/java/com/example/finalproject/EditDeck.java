package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.domain.Deck;
import com.example.finalproject.repositories.DeckRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditDeck extends AppCompatActivity {
    private FloatingActionButton btnSend;
    private FloatingActionButton btnDelete;

    private EditText txt_name;
    private EditText txt_description;

    private Button btnToQuestions;

    private DeckRepository repository;

    private Long deckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deck);

        deckId = getIntent().getLongExtra("deckId", -1);
        repository = new DeckRepository(this);

        btnSend = findViewById(R.id.btn_send);
        btnDelete = findViewById(R.id.btn_delete);
        btnToQuestions = findViewById(R.id.btn_toQuestions);
        txt_name = findViewById(R.id.txt_question);
        txt_description = findViewById(R.id.txt_description);

        btnSend.setOnClickListener(this::createDeck);
        btnToQuestions.setOnClickListener(this::goToQuestions);

        populateEdit(deckId);
    }

    private void populateEdit(Long id) {
        if (deckId <= -1) {
            return;
        }

        Deck deck = repository.findDeckById(id);

        txt_name.setText(deck.getName());
        txt_description.setText(deck.getDescription());
        btnSend.setOnClickListener(this::updateDeck);
        btnDelete.setVisibility(View.VISIBLE);
        btnDelete.setOnClickListener(this::deleteDeck);
        btnToQuestions.setVisibility(View.VISIBLE);
    }

    public void createDeck(View view) {
        String name = txt_name.getText().toString();
        String description = txt_description.getText().toString();

        if (validate() == false) {
            return;
        }

        try {
            repository.saveDeck(new Deck(
                    -1L,
                    name,
                    description
            ));

            Toast.makeText(this, "Deck created successfully", Toast.LENGTH_SHORT).show();
        } catch(SQLiteConstraintException e) {
            Toast.makeText(this, "Error creating deck", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    public void updateDeck(View view) {
        String name = txt_name.getText().toString();
        String description = txt_description.getText().toString();

        if (validate() == false) {
            return;
        }

        try {
            repository.updateDeck(deckId, new Deck(
                    deckId,
                    name,
                    description
            ));

            Toast.makeText(this, "Deck updated successfully", Toast.LENGTH_SHORT).show();
        } catch(SQLiteConstraintException e) {
            Toast.makeText(this, "Error updating deck", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private boolean validate() {
        String name = txt_name.getText().toString();
        String description = txt_description.getText().toString();

        if (name.length() == 0) {
            cancelWithMessage("Name should not be empty");
            return false;
        }

        return true;
    }

    private void cancelWithMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void deleteDeck(View view) {
        try {
            repository.deleteDeck(deckId);
            Toast.makeText(this, "Deck deleted successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error deleting deck", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    public void goToQuestions(View view) {
        Intent to = new Intent(this, QuestionsList.class);

        to.putExtra("deckId", deckId);
        startActivity(to);
    }

}