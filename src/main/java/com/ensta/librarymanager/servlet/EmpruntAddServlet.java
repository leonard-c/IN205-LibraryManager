package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

@WebServlet("/emprunt_add")
public class EmpruntAddServlet extends HttpServlet {
	
	LivreService livreService = LivreService.getInstance();
	MembreService membreService = MembreService.getInstance();
	EmpruntService empruntService = EmpruntService.getInstance();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			request.setAttribute("livresDispos", this.livreService.getListDispo());
			request.setAttribute("membres", this.membreService.getListMembreEmpruntPossible());
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp").forward(request, response);

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		String idLivre = request.getParameter("idLivre");
		String idMembre = request.getParameter("idMembre");
		
		try {
			Livre livre = livreService.getById(Integer.valueOf(idLivre));
			Membre membre = membreService.getById(Integer.valueOf(idMembre));
			Emprunt emprunt = new Emprunt(0, membre, livre, LocalDate.now(), null);
			empruntService.create(emprunt);
			response.sendRedirect("/TP3Ensta/emprunt_list");
		} catch (NumberFormatException | ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		

	}
	
	
}
