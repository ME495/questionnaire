package pers.chengjian.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pers.chengjian.dbutils.JdbcUtils;

/**
 * Servlet implementation class SaveResult
 */
public class SaveResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveResult() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private int saveUser(HttpSession session, JdbcUtils jdbcUtils) {
    	String sql = "insert into user(sex, age, experience) values(?, ?, ?)";
    	ArrayList<Object> list = new ArrayList<Object>();
    	list.add(session.getAttribute("sex"));
    	list.add(Integer.valueOf((String) session.getAttribute("age")));
    	list.add(session.getAttribute("experience"));
    	return jdbcUtils.updateByPreparedStatementRuturnGeneratedValue(sql, list);
    }
    
    private void saveImagePreference(HttpSession session, JdbcUtils jdbcUtils, int user_id) {
    	String sql = "insert into user_image(user_id, image_id, method) values(?, ?, ?)";
    	ArrayList<Integer> result = (ArrayList<Integer>) session.getAttribute("result");
    	for (int i=0;i<result.size();++i) {
    		ArrayList<Object> list = new ArrayList<Object>();
        	list.add(user_id);
        	list.add(i+1);
        	list.add(result.get(i));
        	jdbcUtils.updateByPreparedStatement(sql, list);
    	}
    }
    
    private void saveResult(HttpSession session) throws SQLException {
    	JdbcUtils jdbcUtils = new JdbcUtils();
    	jdbcUtils.getConnection();
    	int user_id = saveUser(session, jdbcUtils);
    	saveImagePreference(session, jdbcUtils, user_id);
    	jdbcUtils.releaseConnection();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int index = (int) session.getAttribute("index");
		ArrayList<Integer> result = (ArrayList<Integer>) session.getAttribute("result");
		String method = request.getParameter("method");
		PrintWriter out = response.getWriter();
		result.add(Integer.valueOf(method));
		++index;
		System.out.println("SaveResult: "+index);
		if (index>3) {
			try {
				saveResult(session);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print("exit");
			session.invalidate();
		} else {
			out.print("continue");
			session.setAttribute("index", index);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
