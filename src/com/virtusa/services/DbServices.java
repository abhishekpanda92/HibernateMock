package com.virtusa.services;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.virtusa.databaseservices.OracleConnection;
import com.virtusa.model.CreateTable;

public class DbServices {

	private Field primaryKey;
	private String tableName;
	private Class entity;
	private Field[] publicFields;
	private Connection connection;
	private Statement statement;

	public DbServices(Class entity) throws SQLException {
		CreateTable CreateTableAnnotation = (CreateTable) entity
				.getAnnotation(CreateTable.class);
		this.entity = entity;
		tableName = CreateTableAnnotation.value();

		publicFields = entity.getDeclaredFields();

		for (Field field : publicFields) {
			Annotation[] annotations = field.getDeclaredAnnotations();
			if (annotations.length > 0) {
				for (int i = 0; i < annotations.length; i++) {

					if (annotations[i].annotationType().getSimpleName()
							.equals("PrimaryKey")) {
						primaryKey = field;
					}
				}
			}
		}

		connection = OracleConnection.getConnection();
		statement = connection.createStatement();
		DatabaseMetaData dbm = connection.getMetaData();
		ResultSet tables = dbm.getTables(null, null, tableName.toUpperCase(),
				null);

		if (!tables.next()) {

			System.out.println("Creating table...");
			String[] types = setColumnDataType();
			String query = createTableQuery(types);
			statement.execute(query);
		}
	}

	public int saveToDb(Object object) throws IllegalArgumentException,
			IllegalAccessException, SQLException {

		String sql = createInsertQuery(object);
		statement.executeUpdate(sql);
		return 0;
	}

	public Object fetchObject(Object primaryKeyValue)
			throws InstantiationException, IllegalAccessException, SQLException {

		Object object = entity.newInstance();

		String sql = null;

		if (primaryKeyValue.getClass().getSimpleName().equals("String")) {
			sql = "Select * from " + tableName + " where "
					+ primaryKey.getName() + " = " + "'" + primaryKeyValue
					+ "'";
		} else {
			sql = "Select * from " + tableName + " where "
					+ primaryKey.getName() + " = " + primaryKeyValue;
		}

		for (int i = 0; i < publicFields.length; i++)

		{
			publicFields[i].setAccessible(true);

		}

		System.out.println(sql);
		ResultSet rs = statement.executeQuery(sql);

		if (rs.next()) {
			for (int i = 0; i < publicFields.length; i++) {
				Object resultObject = rs.getObject(i + 1);
				if (resultObject instanceof java.math.BigDecimal) {
					if (publicFields[i].getType().getSimpleName().equals("int")) {
						publicFields[i].set(object,
								Integer.parseInt(resultObject.toString()));
					} else if (publicFields[i].getType().getSimpleName()
							.equals("double")) {
						publicFields[i].set(object,
								Double.parseDouble(resultObject.toString()));
					}
				} else
					publicFields[i].set(object, resultObject);
			}
		}

		return object;

	}

	public int updateinDb(Object object) throws IllegalArgumentException,
			IllegalAccessException, SQLException {

		String query = createUpdateQuery(object);
		statement.executeUpdate(query);
		return 0;
	}

	
	public int deleteFromDb(Object object) throws SQLException
	{
		String sql = "Delete from "+tableName +" where "+primaryKey.getName()+" = " +"'"+object +"'";
		System.out.println(sql);
		statement.executeUpdate(sql);
		return 0;
	}
	private String createUpdateQuery(Object object)
			throws IllegalArgumentException, IllegalAccessException {

		String query = "update " + tableName + " set ";
		for (int i = 1; i < publicFields.length; i++)

		{
			publicFields[i].setAccessible(true);

			if (publicFields[i].getType().getSimpleName().equals("String"))
				query += publicFields[i].getName() + " = '"
						+ publicFields[i].get(object) + "'" + " ,";
			else
				query += publicFields[i].getName() + " = "
						+ publicFields[i].get(object) + ",";
		}
		publicFields[0].setAccessible(true);
		query = query.substring(0, query.length() - 1);
		query += " where " + publicFields[0].getName() + " = "
				+ publicFields[0].get(object);
		System.out.println(query);
		return query;
	}

	private String createInsertQuery(Object object)
			throws IllegalArgumentException, IllegalAccessException {
		String sql = "Insert into " + tableName + " values(";

		for (int i = 0; i < publicFields.length; i++)

		{
			publicFields[i].setAccessible(true);
			if (publicFields[i].getType().getSimpleName().equals("String"))
				sql += "'" + publicFields[i].get(object) + "'" + " ,";
			else
				sql += publicFields[i].get(object) + ",";
		}

		sql = sql.substring(0, sql.length() - 1);
		sql += ")";
		System.out.println(sql);
		return sql;
	}

	private String[] setColumnDataType() {
		String[] types = new String[publicFields.length];
		for (int i = 0; i < publicFields.length; ++i) {

			Class typeClass = publicFields[i].getType();

			if (typeClass.getSimpleName().equals("String")) {
				if (publicFields[i].equals(primaryKey))
					types[i] = "varchar(20) not null primary key";
				else
					types[i] = "varchar(20)";
			} else if (typeClass.getSimpleName().equals("int")) {
				if (publicFields[i].equals(primaryKey))
					types[i] = "number(8) not null primary key";
				else
					types[i] = "number(8)";
			} else if (typeClass.getSimpleName().equals("double")) {
				types[i] = "float";
			} else {
				types[i] = publicFields[i].getType().toString();
			}

		}

		return types;
	}

	private String createTableQuery(String[] types) {
		// TODO Auto-generated method stub
		String query = "create table " + tableName + " (";
		for (int i = 0; i < types.length; i++) {
			query += publicFields[i].getName() + " " + types[i] + ",";
		}
		query = query.substring(0, query.length() - 1);
		query += ")";
		System.out.println(query);
		return query;
	}

}
