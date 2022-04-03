package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.EmpruntService;

@WebServlet("/emprunt_return")
public class EmpruntReturnServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	EmpruntService empruntService = EmpruntService.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			String id = request.getParameter("id");
			request.setAttribute("id", (id==null)? null : Integer.valueOf(id));
			request.setAttribute("emprunts", empruntService.getListCurrent());
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp").forward(request, response);

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("idEmprunt");
		
		try {
			empruntService.returnBook(Integer.valueOf(id));
			response.sendRedirect("/TP3Ensta/emprunt_list");
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
}
