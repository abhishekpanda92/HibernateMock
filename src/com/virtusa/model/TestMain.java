package com.virtusa.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.virtusa.databaseservices.OracleConnection;

public class TestMain {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) throws SQLException,
			NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {

		Employee employee = new Employee(101, "password", "Abhishek", 100.25, 4);
		save(employee);

		// Class Employee = employee.getClass();
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
				sql += "'"+publicFields[i].get(object)+"'" + " ,";
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
