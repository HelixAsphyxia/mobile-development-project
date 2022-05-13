package com.example.finalproject.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "app.db";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String createDeckTable =
                "CREATE TABLE deck ("
                        + "id integer primary key,"
                        + "name text unique not null,"
                        + "description text)";
        String createQuestionTable =
                "CREATE TABLE question ("
                        + "id integer primary key,"
                        + "question text not null,"
                        + "truth integer not null,"
                        + "deck integer not null,"
                        + "foreign key (deck) references deck(id))";

        db.execSQL(createDeckTable);
        db.execSQL(createQuestionTable);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS deck");
        db.execSQL("DROP TABLE IF EXISTS question");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
