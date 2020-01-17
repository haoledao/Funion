package com.fen.db.helper;

import android.content.ContentValues;

import com.fen.db.annotation.Column;
import com.fen.db.annotation.Table;
import com.fen.db.tool.DateTool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Fei
 */
public class SqlHelper {

	/**
	 * 根据类名创建表
	 */
	public static String getCreateTableSQL(Class<?> clazz) {
		StringBuilder builder = new StringBuilder();
		Table table = clazz.getAnnotation(Table.class);
		builder.append("CREATE TABLE IF NOT EXISTS ");
		if (table != null) {
			builder.append(table.name());
		}
		builder.append("(");
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!field.isAccessible())
				field.setAccessible(true);
			Column column = field.getAnnotation(Column.class);
			if (column == null)
				continue;
			builder.append(column.name() + " ");
			builder.append(column.type() + " ");
			if (!column.isNull()) {
				builder.append(" NOT NULL ");
			}
			if (column.isPrimaryKey()) {
				builder.append(" PRIMARY KEY ");
			}
			
			if(column.isUnique()){
				builder.append(" UNIQUE ");
			}
			
			if (!column.defaultValue().equals("null")) {
				builder.append(" DEFAULT " + column.defaultValue());
			}
			builder.append(",");
		}
		builder.deleteCharAt(builder.lastIndexOf(","));
		builder.append(")");
		return builder.toString();
	}

	/**
	 * 获取表名
	 */
	public static String getTableName(Class<?> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		return table.name();
	}

	/**
	 * 获取表版本
	 */
	public static int getTableVersion(Class<?> clazz){
		Table table = clazz.getAnnotation(Table.class);
		return table.version();
	}
	
	/**
	 * 获取表格列
	 */
	public static List<ColumnInfo> getTableColumnInfos(Class<?> clazz){
		Field[] fields = clazz.getDeclaredFields();
		List<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>();
		for (Field field : fields) {
			if (field.isAccessible() == false)
				field.setAccessible(true);
			Column column = field.getAnnotation(Column.class);
			if (column == null)
				continue;
			
			ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setName(column.name());
			columnInfo.setType(column.type());
			columnInfo.setNull(column.isNull());
			columnInfo.setPrimaryKey(column.isPrimaryKey());
			columnInfo.setUnique(column.isUnique());
			columnInfo.setDefaultValue(column.defaultValue());
			columnInfos.add(columnInfo);
		}
		return columnInfos;
	}
	
	/**
	 * 获取新增列的SQL
	 */
	public static String getAddColumnSql(String table ,ColumnInfo columnInfo){
		StringBuilder sbSql = new StringBuilder();
		sbSql.append(String.format("ALTER TABLE %s ADD %s %s ", table, columnInfo.getName(),columnInfo.getType()));
		if (!columnInfo.isNull()) {
			sbSql.append(" NOT NULL ");
		}
		if (columnInfo.isPrimaryKey()) {
			sbSql.append(" PRIMARY KEY ");
		}
		
		if(columnInfo.isUnique()){
			sbSql.append(" UNIQUE ");
		}
		
		if (!columnInfo.getDefaultValue().equals("null")) {
			sbSql.append(" DEFAULT " + columnInfo.getDefaultValue());
		}
		
		sbSql.append(";");
		
		return sbSql.toString();
	}
	
	/**
	 * 获取主键
	 */
	public static String getPrimaryKey(Class<?> clazz){
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAccessible() == false)
				field.setAccessible(true);
			Column column = field.getAnnotation(Column.class);
			if (column == null)
				continue;
			if (column.isPrimaryKey()) {
				return column.name();
			}
		}
		return null;
	}
	
	/**
	 * 使用反射解析值
	 */
	public static void parseModelToContentValues(Object model,
			ContentValues contentValues) {
		if (contentValues.size() > 0)
			contentValues.clear();

		Class<?> clazz = model.getClass();
		Field[] fields = clazz.getDeclaredFields();

		Class<?> fieldType = null;
		Object fieldVal = null;

		for (Field field : fields) {
			try {
				if (field.isAccessible() == false)
					field.setAccessible(true);
				Column column = field.getAnnotation(Column.class);
				fieldType = field.getType();
				fieldVal = field.get(model);
				if (column == null || fieldVal == null)
					continue;

				if (fieldType.equals(int.class)) {
					contentValues.put(column.name(), field.getInt(model));
				} else if (fieldType.equals(Integer.class)) {
					contentValues.put(column.name(), (Integer) field.get(model));
				} else if (fieldType.equals(short.class)) {
					contentValues.put(column.name(), field.getShort(model));
				} else if (fieldType.equals(Short.class)) {
					contentValues.put(column.name(), (Short) field.get(model));
				} else if (fieldType.equals(long.class)) {
					contentValues.put(column.name(), field.getLong(model));
				} else if (fieldType.equals(Long.class)) {
					contentValues.put(column.name(), (Long) field.get(model));
				} else if (fieldType.equals(float.class)) {
					contentValues.put(column.name(), field.getFloat(model));
				} else if (fieldType.equals(Float.class)) {
					contentValues.put(column.name(), (Float) field.get(model));
				} else if (fieldType.equals(double.class)) {
					contentValues.put(column.name(), field.getDouble(model));
				} else if (fieldType.equals(Double.class)) {
					contentValues.put(column.name(), (Double) field.get(model));
				} else if (fieldType.equals(boolean.class)) {
					if (field.getBoolean(model) == true) {
						contentValues.put(column.name(), "1");
					} else {
						contentValues.put(column.name(), "0");
					}
				} else if (fieldType.equals(Boolean.class)) {
					if ((Boolean) field.get(model) == true) {
						contentValues.put(column.name(), "1");
					} else {
						contentValues.put(column.name(), "0");
					}
				} else if (fieldType.equals(String.class)) {
					contentValues.put(column.name(), (String) field.get(model));
				} else if (fieldType.equals(byte[].class)) {
					contentValues.put(column.name(), (byte[]) field.get(model));
				} else if(fieldType.equals(Date.class)){
					Date date = (Date)field.get(model);
					contentValues.put(column.name(), DateTool.formatDate2Str(date));
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 使用反射解析结果并插入对象中
	 */
	public static void parseResultSetToModel(ResultSet queryResult,
											 Object model) {
		Class<?> clazz = model.getClass();
		Field[] fields = clazz.getDeclaredFields();

		Object fieldVal = null;
		Class<?> fieldType = null;
		try {
			for (Field field : fields) {
				if (field.isAccessible() == false)
					field.setAccessible(true);
				Column column = field.getAnnotation(Column.class);
				if (column == null)
					continue;
				String columnName = column.name();
				fieldVal = queryResult.getValue(columnName);
				fieldType = field.getType();
				if (fieldVal != null) {
					if (fieldType.equals(fieldVal.getClass())) {
						field.set(model, fieldVal);
					} else if (fieldType.equals(short.class)) {
						field.setShort(model,queryResult.getShortValue(columnName));
					} else if (fieldType.equals(Short.class)) {
						field.set(model, (Short) queryResult.getShortValue(columnName));
					} else if (fieldType.equals(int.class)) {
						field.setInt(model,queryResult.getIntValue(columnName));
					} else if (fieldType.equals(Integer.class)) {
						field.set(model, (Integer) queryResult.getIntValue(columnName));
					} else if (fieldType.equals(long.class)) {
						field.setLong(model,
								queryResult.getLongValue(columnName));
					} else if (fieldType.equals(Long.class)) {
						field.set(model, (Long) queryResult
								.getLongValue(columnName));
					} else if (fieldType.equals(float.class)) {
						field.setFloat(model,
								queryResult.getFloatValue(columnName));
					} else if (fieldType.equals(Float.class)) {
						field.set(model, (Float) queryResult
								.getFloatValue(columnName));
					} else if (fieldType.equals(double.class)) {
						field.setDouble(model,
								queryResult.getDoubleValue(columnName));
					} else if (fieldType.equals(Double.class)) {
						field.set(model, (Double) queryResult
								.getDoubleValue(columnName));
					} else if (fieldType.equals(boolean.class)) {
						field.setBoolean(model,
								queryResult.getBooleanValue(columnName));
					} else if (fieldType.equals(Boolean.class)) {
						field.set(model, (Boolean) queryResult
								.getBooleanValue(columnName));
					} else if (fieldType.equals(String.class)) {
						field.set(model,queryResult.getStringValue(columnName));
					} else if(fieldType.equals(Date.class)){
						field.set(model, queryResult.getDateValue(columnName));
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void parseResultSetListToModelList(
			List<ResultSet> queryResultList, List mList, Class<?> mdlType) {
		try {
			if (queryResultList == null || queryResultList.isEmpty())
				return;
			for (ResultSet queryResult : queryResultList) {
				Object model = mdlType.newInstance();
				parseResultSetToModel(queryResult, model);
				mList.add(model);
			}
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

}