package zuke.com.qxapp.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class userSQLiteOpenHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public userSQLiteOpenHelper(Context context) {
        super(context, "qingxiangAPP", null, 1);
        db = getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sql = new StringBuffer();
        sql.append("create table user(");
        sql.append("id integer primary key autoincrement,");
        sql.append("name varchar(20),");
        sql.append("phone varchar(11),");
        sql.append("psw varchar(20))");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void add(String name,String password){
        db.execSQL("INSERT INTO user (name,password) VALUES(?,?)",new Object[]{name,password});
    }
    public void delete(String name,String password){
        db.execSQL("DELETE FROM user WHERE name = AND password ="+name+password);
    }
    public void updata(String password){
        db.execSQL("UPDATE user SET password = ?",new Object[]{password});
    }




}
