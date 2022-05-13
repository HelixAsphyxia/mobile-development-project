package com.example.finalproject.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalproject.domain.Deck;
import com.example.finalproject.domain.Question;
import com.example.finalproject.persistence.DB;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {

    private Context context;

    public QuestionRepository(Context context) {
        this.context = context;
    }

    public Long saveQuestion(Question question) {
        SQLiteDatabase db = new DB(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("question", question.getQuestion());
        values.put("truth", question.getThruth());
        values.put("deck", question.getDeck());

        return db.insertOrThrow("question", null, values);
    }

    public List<Question> findQuestionsByDeckId(Long deckId) {
        SQLiteDatabase db = new DB(context).getReadableDatabase();
        Cursor cursor = db.query(
                "question",   // The table to query
                null,             // The array of columns to return (pass null to get all)
                "deck = ?",              // The columns for the WHERE clause
                new String[]{deckId.toString()},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Question> result = new ArrayList<>();
        while(cursor.moveToNext()) {
            result.add(new Question(
                    cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("question")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("truth")) != 0,
                    cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            ));
        }
        cursor.close();

        return result;
    }

    public Question findQuestionById(Long id) {
        SQLiteDatabase db = new DB(context).getReadableDatabase();
        String query = "SELECT * FROM question WHERE id = " + id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext() == false) {
            return null;
        }

        db.close();

        return new Question(
                cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("question")),
                cursor.getInt(cursor.getColumnIndexOrThrow("truth")) != 0,
                cursor.getLong(cursor.getColumnIndexOrThrow("deck"))
        );
    }

    public void updateQuestion(Long id, Question question) {
        SQLiteDatabase db = new DB(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("question", question.getQuestion());
        values.put("truth", question.getThruth());

        db.update("question", values, "id=?", new String[]{id.toString()});
        db.close();
    }

    public void deleteQuestion(Long id) {
        SQLiteDatabase db = new DB(context).getWritableDatabase();

        db.delete("question","id=?", new String[]{id.toString()});
        db.close();
    }

}
