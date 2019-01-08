package pers.chengjian.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			out.print("exit");
			session.setMaxInactiveInterval(0);
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
