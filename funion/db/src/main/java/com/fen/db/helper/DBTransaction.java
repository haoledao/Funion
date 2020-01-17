package com.fen.db.helper;

import android.database.sqlite.SQLiteDatabase;

import com.fen.db.tool.SQLiteTool;

/**
 * 事物支持
 * @author Fei
 */
public class DBTransaction {

	private DBTransaction() {
	}
	
	/**
	 * executes sqls in a transction
	 */
	public static void transact(SQLiteTool db, DBTransactionInterface transctionInterface){
		if(transctionInterface!=null){
			SQLiteDatabase sqliteDb = db.getSQLiteDatabase();
			sqliteDb.beginTransaction();
			try{
				transctionInterface.onTransact();
				sqliteDb.setTransactionSuccessful();
			}finally{
				sqliteDb.endTransaction();
			}
		}
	}
	
	public interface DBTransactionInterface{
   	 void onTransact();
   }
}