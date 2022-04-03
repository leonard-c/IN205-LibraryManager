package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;

@WebServlet("/livre_details")
public class LivreDetailsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	LivreService livreService = LivreService.getInstance();
	EmpruntService empruntService = EmpruntService.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			String id = request.getParameter("id");
			if (id==null)
				throw new ServletException("Erreur : id est nul.");
			Livre livre = this.livreService.getById(Integer.valueOf(id));
			if (livre==null)
				throw new ServletException("Erreur : l'id renseigné ne correspond à aucun membre.");
			request.setAttribute("livre", livre);
			List<Emprunt> emprunts = empruntService.getListCurrentByLivre(Integer.valueOf(id));
			request.setAttribute("empruntsEnCours", (emprunts.size()==0)? null : emprunts);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_details.jsp").forward(request, response);

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			request.setCharacterEncoding("UTF-8");
			String id = request.getParameter("id");
			String titre = request.getParameter("titre");
			String auteur = request.getParameter("auteur");
			String isbn = request.getParameter("isbn");
			if (id==null || titre==null || auteur==null || isbn==null)
				throw new ServletException("Erreur : une valeur est nulle.");
			Livre livre = livreService.getById(Integer.valueOf(id));
			livre.setTitre(titre);
			livre.setAuteur(auteur);
			livre.setIsbn(isbn);
			livreService.update(livre);
			
			response.sendRedirect("/TP3Ensta/livre_details?id="+id);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}

}
