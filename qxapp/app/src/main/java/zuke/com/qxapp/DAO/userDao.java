package zuke.com.qxapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class userDao {
    private final userSQLiteOpenHelper helper;

    public userDao(Context context){ helper = new userSQLiteOpenHelper(context); }

    public long add (String name, String psw){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("psw", psw);
        Long id = db.insert("person", null, values);
        db.close();
        return id;
    }

    public long update(long id, String name,String psw){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",  name);
        values.put("psw", psw);
        int count = db.update("person", values, "id=?", new String[]{String.valueOf(id)});
        db.close();
        return count;
    }

    public long delete(long id){
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete("person", "id=?", new String[]{String.valueOf(id)});
        db.close();
        return count;
    }

    public boolean query(long id){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("person", null, "name=?", new String[]{String.valueOf(id)}, null, null, null);
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }
}
