package pers.chengjian.dbutils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class JdbcUtils {
	private String username, password, driver, url;
	
	private Connection connection;
	
	private PreparedStatement pstmt;
	
	private ResultSet resultSet;
	
	public JdbcUtils() {
		Properties properties = new Properties();
		InputStream in = JdbcUtils.class.getResourceAsStream("/pers/chengjian/dbutils/config.properties");
		try {
			properties.load(in);
		} catch (IOException e) {
			System.out.println("载入数据库配置文件失败");
			e.printStackTrace();
		}
		username = properties.getProperty("username");
		password = properties.getProperty("password");
		driver = properties.getProperty("driver");
		url = properties.getProperty("url");
//		System.out.println(url);
//		System.out.println(username);
//		System.out.println(password);
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("注册jdbc驱动失败!");
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("数据库连接异常！");
			e.printStackTrace();
		}
		return connection;
	}
	
    /** 
     * 完成对数据库标的增加删除和修改的操作 
     *  
     * @param sql 
     * @param params 
     * @return 
     * @throws SQLException 
     */  
    public int updateByPreparedStatement(String sql, List<Object> params) {   
        int result = -1;// 表示当用户执行增加删除和修改的操作影响的行数  
        int index = 1; // 表示 占位符 ，从1开始  
        try {
			pstmt = connection.prepareStatement(sql);  
			if (params != null && !params.isEmpty()) {  
			    for (int i = 0; i < params.size(); i++) {  
			        pstmt.setObject(index++, params.get(i)); // 填充占位符  
			    }  
			}  
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("数据库更新失败！");
			e.printStackTrace();
		}
        return result;
    }  
    
    public int updateByPreparedStatementRuturnGeneratedValue(String sql, List<Object> params) {    
        int index = 1; // 表示 占位符 ，从1开始 
        int value = -1;
        try {
			pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
			if (params != null && !params.isEmpty()) {  
			    for (int i = 0; i < params.size(); i++) {  
			        pstmt.setObject(index++, params.get(i)); // 填充占位符  
			    }  
			}  
			pstmt.executeUpdate();
			resultSet = pstmt.getGeneratedKeys();
			if (resultSet.next()) {
				value = resultSet.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("数据库更新失败！");
			e.printStackTrace();
		}
        return value;
    }  
    
    /**关闭数据库访问 
     * @throws SQLException 
     */  
    public void releaseConnection() throws SQLException{  
        if (resultSet!=null) {  
            resultSet.close();  
        }  
        if (pstmt!=null) {  
            pstmt.close();  
        }  
        if (connection!=null) {  
            connection.close();  
        }  
    }  
}
