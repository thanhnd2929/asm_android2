package com.example.thanhndph45160_ass.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper {

    static String name = "db_congviec";
    static int version = 1;

    public MyDbHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tb_user = "CREATE TABLE User (" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    username TEXT    NOT NULL," +
                "    email    TEXT    NOT NULL," +
                "    password TEXT    NOT NULL," +
                "    fullname TEXT    NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(tb_user);

        String tb_task = "CREATE TABLE Tasks (" +
                "    id         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    name       TEXT    NOT NULL," +
                "    content    TEXT    NOT NULL," +
                "    status     INTEGER NOT NULL," +
                "    start_task TEXT    NOT NULL," +
                "    end_task   TEXT    NOT NULL," +
                "    user_id    INTEGER REFERENCES User (id) " +
                "                       NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(tb_task);

        String insert_user = "INSERT INTO User (username, email, password, fullname)" +
                "VALUES('namnv21', 'namnv@gmail.com', 'nam123', 'Nguyen Van Nam')," +
                " ('tuannv', 'tuannv@gmail.com', 'tuan79', 'Nguyen Van Tuan');";
        sqLiteDatabase.execSQL(insert_user);

        String insert_tasks = "INSERT INTO Tasks(name, content, status, start_task, end_task, user_id)" +
                "VALUES('cv1', 'content_1', 0, '17/11/2023', '20/11/2023', 1), ('cv2', 'content_2', 1, '17/11/2023', '20/11/2023', 1)," +
                " ('cv3', 'content_3', -1, '17/11/2023', '20/11/2023', 1), ('cv4', 'content_4', 2, '17/11/2023', '20/11/2023', 1), ('cv5', 'content_5', 2, '17/11/2023', '20/11/2023', 1);";
        sqLiteDatabase.execSQL(insert_tasks);


    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
