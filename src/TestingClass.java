import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.virtusa.databaseservices.OracleConnection;
import com.virtusa.model.Employee;

public class TestingClass {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection connection = OracleConnection.getConnection();
		// CallableStatement callableStatement;
		 Statement statement = connection.createStatement();
		// String sql = ("SELECT * FROM EMPLOYEES where employeeId = " + 8018);
		// ResultSet rs = statement.executeQuery(sql);
		//
		// while (rs.next()) {
		// int employeeId = rs.getInt(1);
		// String employeeName = rs.getString(2);
		// double employeeSalary = rs.getDouble(3);
		// System.out.println(employeeId + " " + employeeName + " "
		// + employeeSalary);
		// }

		// Class aClass = Employee.class;
		// Field field[] = aClass.getDeclaredFields();
		// System.out.println(field.length);
		//
		// for (int i = 0; i < field.length; i++) {
		// System.out.println(field[i].getName());
		// }

		Class aClass = Employee.class;

		// get all declared fields
		Field[] publicFields = aClass.getDeclaredFields();
//		for (int i = 0; i < publicFields.length; ++i) {
//			String fieldName = publicFields[i].getName();
//			Class typeClass = publicFields[i].getType();
//
//			System.out.println("Field: " + fieldName + " of type "
//					+ typeClass.getName()+" "+publicFields[i].getGenericType());
//		}

		String[] types = new String[publicFields.length];
		
		for (int i = 0; i < publicFields.length; ++i) {
	    
			Class typeClass = publicFields[i].getType();
			System.out.println(typeClass.getSimpleName());
			if(typeClass.getSimpleName().equals("String"))
			{
				types[i] = "varchar(20)";
			}
			else if(typeClass.getSimpleName().equals("int"))
			{
				types[i] = "number(8)";
			}
			else if(typeClass.getSimpleName().equals("double"))
			{
				types[i] = "float";
			}
			else
			{
				types[i] = publicFields[i].getType().toString();
			}

	}
		

		
		String query = "create table emptable2 (";
		
		for(int i = 0 ; i < types.length ; i++)
		{
			query += publicFields[i].getName() +" "+types[i] +",";
		}
		
		query = query.substring(0, query.length() -1);
		
		query += ")";
		
		//System.out.println(query);
		boolean result = statement.execute(query);
		System.out.println(result);
		
		
	}

}
