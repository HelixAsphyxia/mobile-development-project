package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.persistence.DB;
import com.example.finalproject.repositories.DeckRepository;

public class MainActivity extends AppCompatActivity {
    private Button btnDecks, btnPractice;
    private Button btnConfiguration;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDecks = findViewById(R.id.btn_decks);
        btnPractice = findViewById(R.id.btn_practice);
        btnConfiguration = findViewById(R.id.btn_configuration);
        message = findViewById(R.id.txt_message);

        btnDecks.setOnClickListener(this::goToDecks);
        btnPractice.setOnClickListener(this::goToPractice);
        btnConfiguration.setOnClickListener(this::goToConfiguration);

        setMessage();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setMessage();
    }

    private void setMessage() {
        SharedPreferences preferences = getSharedPreferences("configuration", Context.MODE_PRIVATE);
        String name = preferences.getString("name", "Learner");

        message.setText("Welcome to a new day of learning " + name + "!!!");
    }

    public void goToDecks(View view) {
        Intent to = new Intent(this, DecksList.class);

        startActivity(to);
    }

    public void goToPractice(View view) {
        Intent to = new Intent(this, DecksList.class);

        to.putExtra("practice", true);
        startActivity(to);
    }

    public void goToConfiguration(View view) {
        Intent to = new Intent(this, Configuration.class);

        startActivity(to);
    }

}