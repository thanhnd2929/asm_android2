package com.example.thanhndph45160_ass.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thanhndph45160_ass.DTO.TasksDTO;
import com.example.thanhndph45160_ass.DbHelper.MyDbHelper;

import java.util.ArrayList;

public class TasksDAO {
    MyDbHelper dbHelper;

    SQLiteDatabase db;

    public TasksDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }



    public ArrayList<TasksDTO> getList() {
        ArrayList<TasksDTO> tasksDTOS = new ArrayList<>();
        Cursor c = db.rawQuery("Select * from Tasks", null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                tasksDTOS.add(
                        new TasksDTO(
                                c.getInt(0),
                                c.getString(1),
                                c.getString(2),
                                c.getInt(3),
                                c.getString(4),
                                c.getString(5),
                                c.getInt(6)));
            } while (c.moveToNext());
        }
        return tasksDTOS;
    }

    public int addRow(TasksDTO tasksDTO) {
        ContentValues values = new ContentValues();
        values.put("name", tasksDTO.getName());
        values.put("content", tasksDTO.getContent());
        values.put("status", tasksDTO.getStatus());
        values.put("start_task", tasksDTO.getStart());
        values.put("end_task", tasksDTO.getEnd());
        values.put("user_id", tasksDTO.getUser_id());
        return (int) db.insert("Tasks", null, values);
    }

    public int update(TasksDTO tasksDTO) {
        ContentValues values = new ContentValues();
        values.put("name", tasksDTO.getName());
        values.put("content", tasksDTO.getContent());
        values.put("status", tasksDTO.getStatus());
        values.put("start_task", tasksDTO.getStart());
        values.put("end_task", tasksDTO.getEnd());
        values.put("user_id", tasksDTO.getUser_id());
        String[] id = new String[]{String.valueOf(tasksDTO.getId())};
        return db.update("Tasks", values, "id=?", id);
    }

    public int delete(TasksDTO tasksDTO) {
        String[] id = new String[]{String.valueOf(tasksDTO.getId())};
        return db.delete("Tasks", "id=?", id);
    }

}
