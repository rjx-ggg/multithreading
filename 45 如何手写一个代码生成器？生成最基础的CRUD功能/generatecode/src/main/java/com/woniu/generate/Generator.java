package com.woniu.generate;


import java.sql.*;

import java.util.*;

/**
 * 如何手写一个代码生成器
 * 生成最基础的CRUD功能
 */
public class Generator {

	public static void main(String[] args) throws Exception {
		//mysql
		Class.forName("com.mysql.cj.jdbc.Driver");
		Properties props = new Properties();  
        props.put("useInformationSchema", "true"); //mysql获取表注释需要加上这个属性 
        props.put("user", "root");  
        props.put("password", "123456");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ds1database?serverTimezone=GMT%2B8&characterEncoding=utf-8&autoReconnect=true"
				,props);
		System.out.println("========映射表信息==============");
		DatabaseMetaData meta = con.getMetaData(); 
		ResultSet tables = meta.getTables("ds1database", "%", "t_user", new String[] {"TABLE"});
		while(tables.next()) {
			ResultSetMetaData metaData = tables.getMetaData();
			System.out.println(metaData.getColumnCount());
			for(int i = 1 ; i <= metaData.getColumnCount(); i ++) {
				System.out.println(metaData.getColumnName(i)+" ==> "+tables.getString(metaData.getColumnName(i)));
			}
			
			System.out.println(tables.getString("TABLE_NAME")+" --->>> "+tables.getString("REMARKS"));
		}
		System.out.println("========映射列信息==============");
		ResultSet columns = meta.getColumns("ds1database", "%", "t_user", "%");
		System.out.println("columnName|columnType|remarks");
		while(columns.next()) {
			String columnName = columns.getString("COLUMN_NAME"); 
			String columnType = columns.getString("TYPE_NAME"); 
			String remarks = columns.getString("REMARKS");
			System.out.println(columnName+"|"+columnType+"|"+remarks);
		}
		System.out.println("========映射主键信息==============");
		ResultSet primaryKeys = meta.getPrimaryKeys("ds1database", "%", "t_user");
		while(primaryKeys.next()) {
			ResultSetMetaData metaData = primaryKeys.getMetaData();
			System.out.println("属性个数："+metaData.getColumnCount());
			for(int i = 1 ; i <= metaData.getColumnCount(); i ++) {
				System.out.println(metaData.getColumnName(i)+" ==> "+primaryKeys.getString(metaData.getColumnName(i)));
			}
		}
	}
}
