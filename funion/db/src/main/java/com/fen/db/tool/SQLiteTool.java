package com.fen.db.tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fen.db.helper.DBTransaction;
import com.fen.db.helper.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据操作工具
 * 当不使用时，需掉用{@link #closeDB()}关闭连接
 * @author Fei
 */
public class SQLiteTool {
	
	private Context mContext;
	
	private SQLiteDatabase mSQLiteDatabase;

	private String dbPath;

	/**
	 * @param dbPath 数据文件地址
	 */
	public SQLiteTool(Context context, String dbPath) {
		this.mContext = context;
		this.dbPath = dbPath;
		openDB();
	}

	public SQLiteTool(Context context, SQLiteDatabase db){
		this.mContext = context;
		this.mSQLiteDatabase = db;
		this.dbPath = db.getPath();
		openDB();
	}
	
	public SQLiteDatabase getSQLiteDatabase(){
		return mSQLiteDatabase;
	}
	
	public Context getContext(){
		return mContext;
	}
	
	/**
	 * 更新一条记录
	 * @param table 需更新的表
	 * @param values 需要修改的值
	 * @param whereClause 更新条件
	 * @param whereArgs 条件值
	 * @return 影响行数
	 */
	public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
		try {
			openDB();
			return mSQLiteDatabase.update(table, values, whereClause, whereArgs);
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	/**
	 * 插入值
	 * @return ID
	 */
	public long insert(String table, ContentValues values) {
		try {
			openDB();
			return mSQLiteDatabase.insertOrThrow(table, null, values);
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	/**
	 * 插入或修改
	 * @return ID
	 */
	public long insertOrReplace(String table, ContentValues values){
		try {
			openDB();
			return mSQLiteDatabase.replaceOrThrow(table, null, values);
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	/**
	 * 批量插入
	 */
	public boolean batchInsert(final String table,final List<ContentValues> listVal){
		try {
			openDB();
			DBTransaction.transact(this , new DBTransaction.DBTransactionInterface(){
				@Override
				public void onTransact() {
					for (ContentValues contentValues : listVal) {
						mSQLiteDatabase.insertOrThrow(table, null, contentValues);
					}
				}
			});
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	/**
	 * 根据条件删除
	 * @return 影响行数
	 */
	public int delete(String table, String whereClause, String[] whereArgs) {
		try {
			openDB();
			return mSQLiteDatabase.delete(table, whereClause, whereArgs);
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * 查询列表
	 * 
	 * @param table 表名
	 * @param columns 需要返回的列，为空返回全部
	 * @param selection  A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
	 * @param groupBy  A filter declaring how to group rows, formatted as an SQL GROUP BY clause (excluding the GROUP BY itself). Passing null will cause the rows to not be grouped.
	 * @param having A filter declare which row groups to include in the cursor, if row grouping is being used, formatted as an SQL HAVING clause (excluding the HAVING itself). Passing null will cause all row groups to be included, and is required when row grouping is not being used.
	 * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * @param selectionArgs selectionArgs You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
	 * @return if exceptions happen or no match records, then return null
	 */
	public List<ResultSet> query(String table, String[] columns,
								 String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = null;
		try {
			openDB();
			cursor = mSQLiteDatabase.query(table, columns, selection,
					selectionArgs, groupBy, having, orderBy);
			if(cursor.getCount() < 1){
				return null;
			}else{
				List<ResultSet>  resultList = new ArrayList<ResultSet>();
				parseCursorToResult(cursor, resultList);
				return resultList;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if(cursor!=null)
				cursor.close();
		}
	}

	/**
	 * a simple query by condition
	 */
	public List<ResultSet> query(String table, String[] columns,
			String selection, String[] selectionArgs) {
		return query(table, columns, selection, selectionArgs, null, null, null);
	}

	/**
	 * paging query
	 * @param orderBy cann't be null if define page and pageSize 
	 * @param page first page is 1
	 * @param pageSize
	 * @return
	 */
	public PagingTool<ResultSet> pagingQuery(String table, String[] columns,
											 String selection, String[] selectionArgs, String groupBy, String having, String orderBy, int page, int pageSize){
		
		if(orderBy == null && pageSize != 0)
			throw new SQLException("orderBy cann't be null if define page and pageSize");
		
		String orderWithLimit;
		if(orderBy != null && pageSize != 0){
			orderWithLimit = String.format("%s LIMIT %s , %s", orderBy, (page-1)*pageSize, pageSize);
		}else{
			orderWithLimit = orderBy;
		}
		
		Cursor cursor = null;
		Cursor totalCursor = null;
		try {
			openDB();
			
			PagingTool<ResultSet> resultList = new PagingTool<ResultSet>();
			
			totalCursor = mSQLiteDatabase.query(table, new String[]{"count(*) as totalSize"}, selection,
					selectionArgs, groupBy, having,null);
			
			if(totalCursor.moveToNext()){
				int totalSize = totalCursor.getInt(0);
				resultList.setTotalSize(totalSize);
			}
			
			cursor = mSQLiteDatabase.query(table, columns, selection,
					selectionArgs, groupBy, having, orderWithLimit);
			
			if(cursor.getCount() < 1){
				return resultList;
			}else{
				parseCursorToResult(cursor, resultList);
				return resultList;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if(cursor!=null)
				cursor.close();
			if(totalCursor != null)
				totalCursor.close();
		}
	}
	
	/**
	 * Execute a single SQL statement that is NOT a SELECT/INSERT/UPDATE/DELETE.
	 */
	public boolean execSQL(String sql, Object... bindArgs) {
		try {
			openDB();
			mSQLiteDatabase.execSQL(sql, bindArgs);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * execute raw query sql
	 * 
	 * @param sql the SQL query. The SQL string must not be ; terminated
	 * @param bindArgs You may include ?s in where clause in the query, which will be replaced by the values from selectionArgs. The values will be bound as Strings.
	 * @return return result as List or null
	 */
	public List<ResultSet> execQuerySQL(String sql, String... bindArgs){
		Cursor cursor = null;
		try{
			openDB();
			cursor = mSQLiteDatabase.rawQuery(sql, bindArgs);
			if(cursor.getCount() < 1){
				return null;
			}
			List<ResultSet>  resultList = new ArrayList<ResultSet>();
			parseCursorToResult(cursor, resultList);
			return resultList;
		}catch(SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}finally{
			if(cursor!=null)
				cursor.close();
		}
		
	}
	
	/**
	 * open database
	 */
	public void openDB() {
		if (mSQLiteDatabase == null || mSQLiteDatabase.isOpen() == false)
			mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
	}

	/**
	 * close database
	 */
	public void closeDB() {
		if (mSQLiteDatabase != null && mSQLiteDatabase.isOpen()) {
			mSQLiteDatabase.close();
		}
	}


	public String getDbPath() {
		return dbPath;
	}

	/**
	 * set data in cursor to ResultSet List
	 * @param resultList the data will set in it
	 */
	private void parseCursorToResult(Cursor cursor,List<ResultSet> resultList){
		int columnCount;
		int columnType;
		Object columnVal;
		while (cursor.moveToNext()) {
			columnCount = cursor.getColumnCount();
			ResultSet result = new ResultSet();
			for (int index = 0; index < columnCount; ++index) {
				columnType = cursor.getType(index);
				switch (columnType) {
				case Cursor.FIELD_TYPE_BLOB:
					columnVal = cursor.getBlob(index);
					break;
				case Cursor.FIELD_TYPE_FLOAT:
					columnVal = cursor.getDouble(index);
					break;
				case Cursor.FIELD_TYPE_INTEGER:
					columnVal = cursor.getLong(index);
					break;
				case Cursor.FIELD_TYPE_NULL:
					columnVal = null;
					break;
				default:
					columnVal = cursor.getString(index);
					break;
				}
				result.setValue(cursor.getColumnName(index), columnVal);
			}
			resultList.add(result);
		}
	}
}