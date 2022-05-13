package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Configuration extends AppCompatActivity {
    private EditText name;
    private Spinner order;
    private Button save;

    private final static List<String> ORDER_OPTIONS = List.of("Random", "First to last");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        name = findViewById(R.id.txt_name);
        order = findViewById(R.id.spin_order);
        save = findViewById(R.id.btn_save);

        order.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ORDER_OPTIONS));

        save.setOnClickListener(this::onSave);

        setConfiguration();
    }

    private void setConfiguration() {
        SharedPreferences preferences = getSharedPreferences("configuration", Context.MODE_PRIVATE);
        String nameString = preferences.getString("name", "");
        String orderString = preferences.getString("order", "Random");

        if (nameString.length() != 0) {
            name.setText(nameString);
        }

        order.setSelection(ORDER_OPTIONS.indexOf(orderString));
    }

    public void onSave(View view) {
        SharedPreferences preferences = getSharedPreferences("configuration", Context.MODE_PRIVATE);
        String nameString = name.getText().toString();
        String orderString = order.getSelectedItem().toString();

        if (nameString.length() == 0) {
            nameString = null;
        }

        preferences
                .edit()
                .putString("name", nameString)
                .putString("order", orderString)
                .commit();

        finish();
    }
}