package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.MembreService;

@WebServlet("/membre_details")
public class MembreDetailsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MembreService membreService = MembreService.getInstance();
	EmpruntService empruntService = EmpruntService.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String id = request.getParameter("id");
			if (id == null)
				throw new ServletException("Erreur : id est nul.");
			Membre membre = this.membreService.getById(Integer.valueOf(id));
			if (membre == null)
				throw new ServletException("Erreur : l'id renseigné ne correspond à aucun membre.");
			request.setAttribute("membre", membre);
			List<Emprunt> emprunts = empruntService.getListCurrentByMembre(Integer.valueOf(id));
			request.setAttribute("empruntsEnCours", (emprunts.size() == 0) ? null : emprunts);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_details.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			request.setCharacterEncoding("UTF-8");
			String id = request.getParameter("id");
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String abonnement = request.getParameter("abonnement");
			String adresse = request.getParameter("adresse");
			String email = request.getParameter("email");
			String telephone = request.getParameter("telephone");
			if (id == null || nom == null || prenom == null || abonnement == null)
				throw new ServletException("Erreur : une valeur essentielle est nulle.");
			Membre membre = membreService.getById(Integer.valueOf(id));
			membre.setNom(nom);
			membre.setPrenom(prenom);
			membre.setAbonnement(Abonnement.valueOf(abonnement));
			membre.setAdresse(adresse);
			membre.setEmail(email);
			membre.setTelephone(telephone);
			membreService.update(membre);

			response.sendRedirect("/TP3Ensta/membre_details?id=" + id);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}

}
