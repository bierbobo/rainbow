package com.bierbobo.rainbow.data.orm.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSetMetaData;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;


public class DAORowMapper implements RowMapper {

	private Class rowObjectClass;

	public DAORowMapper(Class rowObjectClass) {
		this.rowObjectClass = rowObjectClass;
	}

	public Object mapRow(ResultSet rs, int index) throws SQLException {

		Object object;
		try {
			object = rowObjectClass.newInstance();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		ResultSetWrappingSqlRowSetMetaData wapping = new ResultSetWrappingSqlRowSetMetaData(rs.getMetaData());

		Map valueMap = new TreeMap();
		for (int i = 1; i <= wapping.getColumnCount(); i++) {

//			String name = wapping.getColumnName(i);
			String name = wapping.getColumnLabel(i);
			int colType = wapping.getColumnType(i);

			if (colType == java.sql.Types.TIME
					|| colType == java.sql.Types.DATE
					|| colType == java.sql.Types.TIMESTAMP) {

				java.sql.Timestamp dd = rs.getTimestamp(i);
				long values = 0;
				if (dd != null) {
					values = dd.getTime();
					java.util.Date d = new java.util.Date();
					d.setTime(values);
					valueMap.put(name.toLowerCase(),d);
				} else {
					valueMap.put(name.toLowerCase(), null);
				}

			}else if(colType == java.sql.Types.CHAR) {

				String char_space = rs.getString(i);
				if(char_space != null && char_space.length() > 1){
					valueMap.put(name.toLowerCase(), char_space.trim());
				}else{
					valueMap.put(name.toLowerCase(), char_space);
				}
			}
			else {
				Object value = rs.getObject(i);
				valueMap.put(name.toLowerCase(), value);
			}
		}

		System.out.println(valueMap);
		try {
			MyBeanUtils.copyMap2Bean(object, valueMap);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return object;

	}

}
