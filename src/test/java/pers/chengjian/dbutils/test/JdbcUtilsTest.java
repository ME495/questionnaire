package pers.chengjian.dbutils.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pers.chengjian.dbutils.JdbcUtils;

public class JdbcUtilsTest {
	
	@Test
	public void testGetConnection() {
		JdbcUtils jdbcUtils = new JdbcUtils();
		Connection conn = jdbcUtils.getConnection();
		assertNotNull(conn);
		try {
			System.out.println("ok");
			jdbcUtils.releaseConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testupdateByPreparedStatement() {
		String sql = "insert into user(sex, age, experience) values(?, ?, ?)";
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("male");
		list.add(18);
		list.add("NoExperience");
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		int result = jdbcUtils.updateByPreparedStatement(sql, list);
		try {
			jdbcUtils.releaseConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotEquals(result, -1);
	}
}
