package com.example.finalproject.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalproject.domain.Deck;
import com.example.finalproject.persistence.DB;

import java.util.ArrayList;
import java.util.List;

public class DeckRepository {
    private Context context;

    public DeckRepository(Context context) {
        this.context = context;
    }

    public Long saveDeck(Deck deck) {
        SQLiteDatabase db = new DB(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", deck.getName());
        values.put("description", deck.getDescription());

        return db.insertOrThrow("deck", null, values);
    }

    public List<Deck> getAllDecks() {
        SQLiteDatabase db = new DB(context).getWritableDatabase();
        Cursor cursor = db.query(
                "deck",   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Deck> result = new ArrayList<>();
        while(cursor.moveToNext()) {
            result.add(new Deck(
                    cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description"))
            ));
        }
        cursor.close();

        return result;
    }

    public Deck findDeckById(Long id) {
        SQLiteDatabase db = new DB(context).getReadableDatabase();
        String query = "SELECT * FROM deck WHERE id = " + id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext() == false) {
            return null;
        }

        db.close();

        return new Deck(
                cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getString(cursor.getColumnIndexOrThrow("description"))
        );
    }

    public void updateDeck(Long id, Deck deck) {
        SQLiteDatabase db = new DB(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", deck.getName());
        values.put("description", deck.getDescription());

        db.update("deck", values, "id=?", new String[]{id.toString()});
        db.close();
    }

    public void deleteDeck(Long id) {
        SQLiteDatabase db = new DB(context).getWritableDatabase();

        db.delete("deck","id=?", new String[]{id.toString()});
        db.close();
    }

}
