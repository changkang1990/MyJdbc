package cn.magicsite.jdbc;

import java.nio.channels.NonWritableChannelException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class JdbcTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection connection = null;	
		PreparedStatement preparedStatementCreate = null;
		PreparedStatement preparedStatementInsert = null;
		PreparedStatement preparedStatementSelect= null;
		ResultSet resultSet = null;
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/test?characterEncoding=utf-8","root","123456");
			
			String sqlCreateTable = "create table IF NOT EXISTS t_user ("
					+ "`id` int(11) NOT NULL AUTO_INCREMENT,"
					+ "`username` varchar(32) NOT NULL COMMENT '用户名称',"
					+ "`birthday` date DEFAULT NULL COMMENT '用户生日',"
					+ "PRIMARY KEY (`id`)"
					+ ")ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;";
			
			preparedStatementCreate = connection.prepareStatement(sqlCreateTable);
			preparedStatementCreate.execute();
				
			String sqlInsert = "insert into t_user (`username`,`birthday`) values(?,?)";
			preparedStatementInsert = connection.prepareStatement(sqlInsert);
			preparedStatementInsert.setString(1, "张三");	
			preparedStatementInsert.setDate(2,new java.sql.Date(100, 10, 11));
			preparedStatementInsert.addBatch();
			preparedStatementInsert.setString(1, "李四");	
			preparedStatementInsert.setDate(2, new java.sql.Date(101, 2, 6));
			preparedStatementInsert.addBatch();
			preparedStatementInsert.executeBatch();
			
			String sqlSelect = "select * from t_user";	
			preparedStatementSelect = connection.prepareStatement(sqlSelect);		
			//preparedStatementSelect.setString(1, "张三");		
			resultSet = preparedStatementSelect.executeQuery();
			
			while(resultSet.next()){
				System.out.println(resultSet.getString("id"));
				System.out.println(resultSet.getString("username"));
				System.out.println(resultSet.getString("birthday"));
			}		
		}catch(Exception e){
			e.printStackTrace();		
		}finally {
			if(resultSet != null){
				try {
					resultSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(preparedStatementSelect != null){
				try {
					preparedStatementSelect.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(preparedStatementInsert != null){
				try {
					preparedStatementInsert.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(preparedStatementCreate != null){
				try {
					preparedStatementCreate.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}		
	}
}
