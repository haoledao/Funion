package com.fen.fund.mapper.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fen.fund.tool.annotation.Exclude;
import com.fen.fund.tool.annotation.PrimaryKey;
import com.fen.fund.tool.string.HumpTool;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.jdbc.SQL;

/**
 * 动态SQL提供者
 * 根据传入的对象动态获取表名和字段名生成动态的sql语句
 * @author Fei
 * @date 2020-01-09
 */
public class BaseSqlProvider<E> {

	private static final String FILED_ID = "id";
	private static final String CLASS = "__class";
	/** 最大行数 */
	private static final Integer LIMIT_ROW = 50000;

	/**
	 * 新增
	 * @param entity 实体
	 * @return 结果
	 */
	@Options
	public String insert(E entity) {
 
		SQL sql = new SQL();
		Class clazz = entity.getClass();
        String tableName = getTableName(clazz);
		sql.INSERT_INTO(tableName);
 
		List<Field> fields = getFields(clazz);
		for (Field field : fields) {
			field.setAccessible(true);
			String column = field.getName();
			sql.VALUES(HumpTool.humpToLine(column), String.format("#{" + column + ",jdbcType=VARCHAR}"));
		}
 
		return sql.toString();

	}

	/**
	 * 删除
	 * @param entity 实体
	 * @return 结果
	 */
	public String delete(E entity) {
		SQL sql = new SQL();
		Class clazz = entity.getClass();
        String tableName = getTableName(clazz);
		sql.DELETE_FROM(tableName);
 
		List<Field> primaryKeyField = getPrimaryKeyFields(clazz);
		sqlWhere(sql, primaryKeyField, false);

		return sql.toString();
	}

	/**
	 * 获取主键
	 * @param clazz 类
	 * @return 属性
	 */
	private List<Field> getPrimaryKeyFields(Class clazz) {
		List<Field> primaryKeyField = new ArrayList<>();
		List<Field> fields = getFields(clazz.getSuperclass());
		for (Field field : fields) {
			field.setAccessible(true);
			PrimaryKey key = field.getAnnotation(PrimaryKey.class);
			if (key != null) {
				primaryKeyField.add(field);
			}
		}
		return primaryKeyField;
	}

	/**
	 * 获取字段
	 * @param clazz 类
	 * @return 字段
	 */
	private List<Field> getFields(Class clazz) {

		List<Field> fieldList = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Exclude key = field.getAnnotation(Exclude.class);
			if (key == null) {
				fieldList.add(field);
			}
		}
		return fieldList;

	}

	/**
	 * 获取SQL
	 * @param entity E
	 * @return sql
	 */
	public String get(E entity) {
 
		SQL sql = new SQL();
        Class clazz = entity.getClass();
        String tableName = getTableName(clazz);
		sql.SELECT("*").FROM(tableName);
 
		List<Field> primaryKeyField = getPrimaryKeyFields(clazz);
		sqlWhere(sql, primaryKeyField, false);

		return sql.toString();

	}

	/**
	 * 更新SQL
	 * @param entity E
	 * @return sql
	 */
	public String update(E entity) {

		SQL sql = new SQL();
        Class clazz = entity.getClass();
        String tableName = getTableName(clazz);
		sql.UPDATE(tableName);
 
		List<Field> fields = getFields(clazz);
		for (Field field : fields) {
			field.setAccessible(true);
			String column = field.getName();
			if (column.equals(FILED_ID)) {
				continue;
			}
			sql.SET(HumpTool.humpToLine(column) + "=" + String.format("#{" + column + ",jdbcType=VARCHAR}"));
		}
 
		List<Field> primaryKeyField = getPrimaryKeyFields(clazz);
		sqlWhere(sql, primaryKeyField, false);

		return sql.toString();

	}

	public String updateOnly(Map<String, Object> map) {
		SQL sql = new SQL();
		Class clazz;
		if (!map.containsKey(CLASS)) {
			clazz = map.get("entity").getClass();
			map.put(CLASS, clazz);
		} else {
			clazz = (Class) map.get(CLASS);
		}
		String tableName = clazz.getSimpleName();
		String realTableName = HumpTool.humpToLine(tableName).replaceAll("_entity", "").substring(1);
		sql.UPDATE(realTableName);

		String[] properties = (String[]) map.get("properties");
		for (String prop : properties) {
			sql.SET(HumpTool.humpToLine(prop) + "=" + String.format("#{entity." + prop + ", jdbcType=VARCHAR}"));
		}

		List<Field> primaryKeyField = getPrimaryKeyFields(clazz);
		sqlWhere(sql, primaryKeyField, true);

		return sql.toString();
	}

    /**
     * 获取列表
     * @param entity 实体对象
     * @return 列表
     */
	public String getAll(E entity, String... orders) {

	    SQL sql = new SQL();
        Class clazz = entity.getClass();
        String tableName = getTableName(clazz);
        sql.SELECT("*").FROM(tableName);
        sql.ORDER_BY(orders);
        sql.LIMIT(LIMIT_ROW);

        return sql.toString();

    }

    /** 获取表名 */
    private String getTableName(Class clazz) {
        String tableName = clazz.getSimpleName();
        return HumpTool.humpToLine(tableName).replaceAll("_entity", "").substring(1);
    }

    private void sqlWhere(SQL sql, List<Field> primaryKeyField, boolean multiParam) {
		if (!primaryKeyField.isEmpty()) {
			for (Field pkField : primaryKeyField) {
				pkField.setAccessible(true);
				if (multiParam) {
					sql.WHERE(pkField.getName() + "=" + String.format("#{entity." + pkField.getName() + "}"));
				} else {
					sql.WHERE(pkField.getName() + "=" + String.format("#{" + pkField.getName() + "}"));
				}
			}
		} else {
			sql.WHERE(" 1= 2");
			throw new RuntimeException("对象中未包含PrimaryKey属性");
		}
	}

}