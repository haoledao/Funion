package com.fen.db.helper;


import com.fen.db.tool.SQLiteTool;

/**
 * 数据访问对象创建工厂
 * @author Fei
 */
public class DaoFactory {

	@SuppressWarnings("unchecked")
	public static <T> IBaseDao<T> createGenericDao(SQLiteTool db, Class<?> modelClazz){
		return new GenericDao<T>(db, modelClazz);
	}  

}