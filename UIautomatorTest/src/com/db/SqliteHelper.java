package com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {
	
	 private static final String DB_NAME = "fudu.db";  
	 private static final String TBL_NAME = "MessageHistory";  
	 private static final String CREATE_TBL = " create table "  
	            + " MessageHistory(_id integer primary key autoincrement,dsec text,time text, userName text) ";  

	 private SQLiteDatabase db; 
	public SqliteHelper(Context context) {
		super(context, DB_NAME, null, 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		this.db = db;
		db.execSQL(CREATE_TBL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	public void insert(ContentValues values) {  
        SQLiteDatabase db = getWritableDatabase();  
        db.insert(TBL_NAME, null, values);  
        db.close();  
    }  
	
	//删除最先的信息
	public void delOldest(){
		
	}
	
	public boolean isExist(String desc){
		Boolean b = false;
		Cursor cursor = db.query(SqliteHelper.TBL_NAME, null, "desc"
                + "=?", new String[]{desc}, null, null, null );
       b = cursor.moveToFirst();
       //Log. e("HaveUserInfo", b.toString());
       cursor.close();
		return b;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
