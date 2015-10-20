package com.virtusa.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.virtusa.databaseservices.OracleConnection;
import com.virtusa.services.DbServices;

public class TestMain {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 */
	public static void main(String[] args) throws SQLException,
			NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InstantiationException {

		 Employee employee = new Employee(201, "password", "Dhoni", 100.00,
		 4);
		 DbServices dbServices = new DbServices(employee.getClass());
//		 dbServices.saveToDb(employee);
		 dbServices.deleteFromDb(201);

		// Class Employee = Employee.class;
		//
		// CreateTable CREATETABLEAnnotation = (CreateTable) Employee
		// .getAnnotation(CreateTable.class);
		// System.out.println(CREATETABLEAnnotation.value());
		//
		// String tableName = CREATETABLEAnnotation.value();
		//
		// Field primaryKey = null;
		//
		// for (Field field : Employee.getDeclaredFields()) {
		// Class type = field.getType();
		// String name = field.getName();
		// Annotation[] annotations = field.getDeclaredAnnotations();
		// if (annotations.length > 0) {
		// for (int i = 0; i < annotations.length; i++) {
		//
		// if (annotations[i].annotationType().getSimpleName()
		// .equals("PrimaryKey")) {
		// primaryKey = field;
		// }
		// }
		// }
		// }
		//
		// System.out.println(primaryKey.getName());
		//
		// // PrimaryKey primaryKeyAnnotation =
		// // (PrimaryKey)Employee.getAnnotation(PrimaryKey.class);
		// // System.out.println(primaryKeyAnnotation);
		//
		// // get all declared fields
		// Field[] publicFields = Employee.getDeclaredFields();
		// // for (int i = 0; i < publicFields.length; ++i) {
		// // String fieldName = publicFields[i].getName();
		// // Class typeClass = publicFields[i].getType();
		// //
		// // System.out.println("Field: " + fieldName + " of type "
		// // + typeClass.getName()+" "+publicFields[i].getGenericType());
		// // }
		//
		// String[] types = new String[publicFields.length];
		//
		// for (int i = 0; i < publicFields.length; ++i) {
		//
		// Class typeClass = publicFields[i].getType();
		// System.out.println(typeClass.getSimpleName());
		// if (typeClass.getSimpleName().equals("String")) {
		// if (publicFields[i].equals(primaryKey))
		// types[i] = "varchar(20) not null primary key";
		// else
		// types[i] = "varchar(20)";
		// } else if (typeClass.getSimpleName().equals("int")) {
		// if (publicFields[i].equals(primaryKey))
		// types[i] = "number(8) not null primary key";
		// else
		// types[i] = "number(8)";
		// } else if (typeClass.getSimpleName().equals("double")) {
		// types[i] = "float";
		// } else {
		// types[i] = publicFields[i].getType().toString();
		// }
		//
		// }
		//
		// String query = "create table " + tableName + " (";
		//
		// for (int i = 0; i < types.length; i++) {
		// query += publicFields[i].getName() + " " + types[i] + ",";
		// }
		//
		// query = query.substring(0, query.length() - 1);
		//
		// query += ")";
		//
		// System.out.println(query);
		// Connection connection = OracleConnection.getConnection();
		// // CallableStatement callableStatement;
		// Statement statement = connection.createStatement();
		// boolean result = statement.execute(query);
		// System.out.println(result);

//    	DbServices dbServices = new DbServices(Employee.class);
//		Employee employee = (Employee) dbServices.fetchObject("200");
//		employee.setEmployeeName("Abhishek Panda");
//		employee.setEmployeeSalary(100.25);
//		employee.setPassword("Dubaara Mat Pucchna");
//		System.out.println(employee);
		//update(employee);
		// System.out.println(employee.getEmployeeName());
		// System.out.println(employee.getEmployeeCode());
		// System.out.println(employee.getEmployeeSalary());
//
//		DbServices dbServices = new DbServices(Employee.class);
//		Employee employee = new Employee(200,"pass","Sachin",123.45,3);
//		dbServices.saveToDb(employee);
	}

	

	private static void update(Object object) throws IllegalArgumentException,
			IllegalAccessException, SQLException {
		// TODO Auto-generated method stub
		CreateTable CREATETABLEAnnotation = (CreateTable) object.getClass()
				.getAnnotation(CreateTable.class);
		String query = "update " + CREATETABLEAnnotation.value() + " set ";

		Field[] publicFields = object.getClass().getDeclaredFields();
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

		Connection connection = OracleConnection.getConnection();
		// CallableStatement callableStatement;
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);

	}

	private static Object fetchObject(Class class1, int pKey)
			throws SQLException, IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException,
			InstantiationException {
		// TODO Auto-generated method stub
		Object object = class1.newInstance();

		Field[] publicFields = class1.getDeclaredFields();
		CreateTable CREATETABLEAnnotation = (CreateTable) class1
				.getAnnotation(CreateTable.class);

		Field primaryKey = null;

		for (Field field : class1.getDeclaredFields()) {
			Class type = field.getType();
			String name = field.getName();
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

		String sql = "Select * from " + CREATETABLEAnnotation.value()
				+ " where " + primaryKey.getName() + " = " + pKey;

		for (int i = 0; i < publicFields.length; i++)

		{
			publicFields[i].setAccessible(true);

		}

		System.out.println(sql);

		Connection connection = OracleConnection.getConnection();
		Statement statement = connection.createStatement();
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

	public static int save(Object object) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, SQLException {

		Field[] publicFields = object.getClass().getDeclaredFields();
		
		CreateTable CREATETABLEAnnotation = (CreateTable) object.getClass()
				.getAnnotation(CreateTable.class);
		String sql = "Insert into " + CREATETABLEAnnotation.value()
				+ " values(";

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

		Connection connection = OracleConnection.getConnection();
		// CallableStatement callableStatement;
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);

		return 0;
	}

	public static int delete(Object object) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, SQLException {

		Field[] publicFields = object.getClass().getDeclaredFields();
		CreateTable CREATETABLEAnnotation = (CreateTable) object.getClass()
				.getAnnotation(CreateTable.class);
		String sql = "Insert into " + CREATETABLEAnnotation.value()
				+ " values(";

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

		Connection connection = OracleConnection.getConnection();
		// CallableStatement callableStatement;
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);

		return 0;
	}

}
