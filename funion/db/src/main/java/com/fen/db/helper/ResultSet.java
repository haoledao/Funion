package com.fen.db.helper;

import com.fen.db.tool.DateTool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fei
 */
public class ResultSet implements Serializable {

	private static final long serialVersionUID = 2510654675439416448L;

	private Map<String, Object> nameValueMap = new LinkedHashMap<String, Object>();

	private Map<Integer, Object> indexValueMap = new LinkedHashMap<Integer, Object>();

	private List<String> columnNameList = new ArrayList<String>();

	private int index = 0;

	/**
	 * 设置列值
	 */
	public void setValue(String columnName, Object columnValue) {
		columnName = columnName.toLowerCase();
		columnNameList.add(columnName);
		nameValueMap.put(columnName, columnValue);
		indexValueMap.put(index++, columnValue);
	}

	/**
	 * 根据索引修改列值
	 */
	public void changeValue(int index, Object value) {
		if (indexValueMap.containsKey(index)) {
			indexValueMap.put(index, value);
			nameValueMap.put(columnNameList.get(index), value);
		}
	}

	/**
	 * 根据列名获取值
	 */
	public Object getValue(String columnName) {
		return nameValueMap.get(columnName.toLowerCase());
	}

	/**
	 * 根据列名获取bool类型值
	 * @return 值为 '1' 或 'true' 返回 true; 为 'null' 或 '0' 或 'false' 返回 false
	 */
	public boolean getBooleanValue(String columnName) {
		Object value = getValue(columnName);
		if(value == null)
			return false;
		String strVal = value.toString().toLowerCase();
		if(strVal.equals("true") || strVal.equals("1")){
			return true;
		}else if(strVal.equals("false") || strVal.equals("0")){
			return false;
		}
		throw new ClassCastException(String.format("invalid boolean value : %s ", value));
	}

	/**
	 * 根据列名获取long类型值
	 */
	public long getLongValue(String columnName) {
		return (long) getDoubleValue(columnName);
	}

	/**
	 * 根据列名获取int类型值
	 */
	public int getIntValue(String columnName) {
		return (int) getLongValue(columnName);
	}

	/**
	 * 根据列名获取short类型值
	 */
	public short getShortValue(String columnName) {
		return (short) getIntValue(columnName);
	}

	/**
	 * 根据列名获取float类型值
	 */
	public float getFloatValue(String columnName) {
		return (float) getDoubleValue(columnName);
	}
	
	/**
	 * 根据列名获取double类型值, null返回0
	 */
	public double getDoubleValue(String columnName) {
		Object value = getValue(columnName);
		if(value == null){
			return 0;
		}else if (value instanceof Double) {
			return (Double) value;
		} else if (value instanceof Float) {
			return (Float) value;
		} else if (value instanceof Long) {
			return (Long) value;
		} else if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof Short) {
			return (Short) value;
		} else if (value instanceof String) {
			if (isNum((String) value)) {
				return Double.parseDouble((String) value);
			}
		}
		throw new ClassCastException(String.format("invalid number %s ", value));
	}

	/**
	 * 根据列名获取String类型值
	 */
	public String getStringValue(String columnName) {
		Object value = getValue(columnName);
		if(value != null){
			return value.toString();
		}else{
			return null;
		}
	}

	/**根据列名获取Date类型值
	 */
	public Date getDateValue(String columnName){
		String value = getStringValue(columnName);
		return value == null ? null : DateTool.parseStr2Date(value);
	}
	
	/**根据列名获取byte[]类型值
	 */
	public byte[] getBlobValue(String columnName){
		return (byte[])getValue(columnName);
	}
	
	/**
	 * 根据列索引获取值
	 */
	public Object getValue(int columnIndex) {
		return indexValueMap.get(columnIndex);
	}

	/**
	 * 列数目
	 */
	public int getSize() {
		return nameValueMap.size();
	}

	/**
	 * 是否为空
	 */
	public boolean isEmpty() {
		return nameValueMap.isEmpty();
	}

	/**
	 * 根据列索引获取列名
	 */
	public String getColumnName(int columnNum){
		return columnNameList.get(columnNum);
	}
	
	/**
	 * 根据列名获取列索引
	 */
	public int indexOfColumnName(String columnName){
		return columnNameList.indexOf(columnName.toLowerCase());
	}
	
	@Override
	public String toString() {
		return "Result [nameValueMap=" + nameValueMap + "]";
	}
	
	/**
	 * 是否为有效的数字
	 */
	private boolean isNum(String str) {
		return !str.equals("")&&str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
}