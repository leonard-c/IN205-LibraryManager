package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.MembreService;


@WebServlet("/membre_delete")
public class MembreDeleteServlet extends HttpServlet {
	
	MembreService membreService = MembreService.getInstance();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			String id = request.getParameter("id");
			if (id==null)
				throw new ServletException("Erreur : id du livre nul.");
			request.setAttribute("id", Integer.valueOf(id));
			request.setAttribute("membre", membreService.getById(Integer.valueOf(id)));
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_delete.jsp").forward(request, response);

	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		String id = request.getParameter("id");
		if (id==null)
			throw new ServletException("Erreur : id du livre nul.");
		
		try {
			membreService.delete(Integer.valueOf(id));
			response.sendRedirect("/TP3Ensta/membre_list");
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		

	}
}
