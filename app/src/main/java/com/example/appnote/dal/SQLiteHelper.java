package com.example.appnote.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.appnote.model.Note;
import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ghichu.db";
    private static int DATABASE_VERSION = 1;


    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE note(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tieude TEXT, ghichu TEXT,color TEXT,dateTime TEXT,daxoa TEXT)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS note";
        db.execSQL(sql);
        onCreate(db);
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Note> getAll() {
        ArrayList<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM note";
        SQLiteDatabase db = getReadableDatabase();
        String[] args = {};
        try (Cursor cursor = db.rawQuery(sql, args)) {
            while (cursor.moveToNext()) {
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setTieude(cursor.getString(1));
                note.setGhichu(cursor.getString(2));
                note.setColor(cursor.getInt(3));
                note.setDateTime(cursor.getString(4));
                note.setDaxoa(Boolean.parseBoolean(cursor.getString(5)));
                notes.add(note);
            }
        }
        return notes;
    }

    public long insert(Note n) {
        ContentValues values = new ContentValues();
        values.put("tieude", n.getTieude());
        values.put("ghichu", n.getGhichu());
        values.put("dateTime", n.getDateTime());
        values.put("color", n.getColor());
        values.put("daxoa", "false");
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("note", null, values);
    }

    public int update(Note n) {
        ContentValues values = new ContentValues();
        values.put("tieude", n.getTieude());
        values.put("ghichu", n.getGhichu());
        values.put("dateTime", n.getDateTime());
        values.put("color", n.getColor());
        values.put("daxoa", "false");
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = {Integer.toString(n.getId())};
        return sqLiteDatabase.update("note", values, whereClause, whereArgs);
    }

    public int delete(int id) {
        String whereClause = "id=?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("note", whereClause, whereArgs);

    }

    public int update_delete(Note n) {
        ContentValues values = new ContentValues();
        values.put("daxoa", "true");
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = {Integer.toString(n.getId())};
        return sqLiteDatabase.update("note", values, whereClause, whereArgs);
    }

    public int update_restore(Note n) {
        ContentValues values = new ContentValues();
        values.put("daxoa", "false");
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = {Integer.toString(n.getId())};
        return sqLiteDatabase.update("note", values, whereClause, whereArgs);
    }

    public List<Note> searchByTieuDe(String key) {
        List<Note> list = new ArrayList<>();
        String whereClause = "tieude like ?";
        String[] whereArgs = {"%" + key + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor cursor = st.query("note", null, whereClause, whereArgs, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tieude = cursor.getString(1);
            String ghichu = cursor.getString(2);
            int color = cursor.getInt(3);
            String datetime = cursor.getString(4);
            boolean daxoa = Boolean.parseBoolean(cursor.getString(5));
            if (daxoa == false) {
                list.add(new Note(id, color, tieude, ghichu, datetime, daxoa));
            }
        }
        return list;
    }

        public List<Note> searchByGhiChu(String key){
        List<Note> list = new ArrayList<>();
        String whereClause = "ghichu like ?";
        String[] whereArgs = {"%"+key+"%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor cursor = st.query("note",null,whereClause,whereArgs,null,null,null);
        while (cursor!=null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tieude = cursor.getString(1);
            String ghichu = cursor.getString(2);
            int color = cursor.getInt(3);
            String datetime = cursor.getString(4);
            boolean daxoa = Boolean.parseBoolean(cursor.getString(5));
            if (daxoa == false) {
                list.add(new Note(id, color, tieude, ghichu, datetime, daxoa));
            }
        }
        return list;
    }
    public List<Note> searchByDate(String from, String to) {
        List<Note> list = new ArrayList<>();
        String whereClause = "dateTime BETWEEN ? AND ?";
        String[] whereArgs = {from.trim(), to.trim()};
        SQLiteDatabase st = getReadableDatabase();
        Cursor cursor = st.query("note", null, whereClause, whereArgs, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tieude = cursor.getString(1);
            String ghichu = cursor.getString(2);
            int color = cursor.getInt(3);
            String datetime = cursor.getString(4);
            boolean daxoa = Boolean.parseBoolean(cursor.getString(5));
            if (daxoa == false) {
                list.add(new Note(id, color, tieude, ghichu, datetime, daxoa));
            }
        }
        return list;
    }
}

    //







