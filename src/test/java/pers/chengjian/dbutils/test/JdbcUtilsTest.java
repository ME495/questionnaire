package pers.chengjian.dbutils.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pers.chengjian.dbutils.JdbcUtils;

public class JdbcUtilsTest {

	@Test
	public void testJdbcUtils() {
		JdbcUtils jdbcUtils = new JdbcUtils();
		assertNotNull(jdbcUtils);
	}
	
	@Test
	public void testGetConnection() {
		JdbcUtils jdbcUtils = new JdbcUtils();
		Connection conn = jdbcUtils.getConnection();
		assertNotNull(conn);
	}

	@Test
	public void testupdateByPreparedStatement() {
		String sql = "insert into user(sex, age, experience) values(?, ?, ?)";
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("male");
		list.add(18);
		list.add("NoExperience");
		JdbcUtils jdbcUtils = new JdbcUtils();
		int result = jdbcUtils.updateByPreparedStatement(sql, list);
		assertNotEquals(result, -1);
	}
}
