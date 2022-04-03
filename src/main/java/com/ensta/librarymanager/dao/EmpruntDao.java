package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class EmpruntDao implements IEmpruntDao {

	private static EmpruntDao instance;

	private EmpruntDao() {
	}

	public static EmpruntDao getInstance() {

		if (instance == null) {
			instance = new EmpruntDao();
		}
		return instance;
	}

	@Override
	public List<Emprunt> getList() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC");

			ResultSet rs = pstmt.executeQuery();

			List<Emprunt> emprunts = new ArrayList<Emprunt>();
			while (rs.next()) {
				int id = rs.getInt("id");

				int idMembre = rs.getInt("idMembre");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String adresse = rs.getString("adresse");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				Abonnement abonnement = Abonnement.valueOf(rs.getString("abonnement"));

				int idLivre = rs.getInt("idLivre");
				String titre = rs.getString("titre");
				String auteur = rs.getString("auteur");
				String ISBN = rs.getString("isbn");

				LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
				java.sql.Date dateRetourSQL = rs.getDate("dateRetour");
				LocalDate dateRetour = (dateRetourSQL==null)? null : dateRetourSQL.toLocalDate();

				Membre membre = new Membre(idMembre, nom, prenom, adresse, email, telephone, abonnement); // Attention :
																											// possibles
																											// doublons
				Livre livre = new Livre(idLivre, titre, auteur, ISBN);
				Emprunt emprunt = new Emprunt(id, membre, livre, dateEmprunt, dateRetour);
				emprunts.add(emprunt);
			}

			return emprunts;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrent() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL");

			ResultSet rs = pstmt.executeQuery();

			List<Emprunt> emprunts = new ArrayList<Emprunt>();
			while (rs.next()) {
				int id = rs.getInt("id");

				int idMembre = rs.getInt("idMembre");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String adresse = rs.getString("adresse");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				Abonnement abonnement = Abonnement.valueOf(rs.getString("abonnement"));

				int idLivre = rs.getInt("idLivre");
				String titre = rs.getString("titre");
				String auteur = rs.getString("auteur");
				String ISBN = rs.getString("isbn");

				LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
				LocalDate dateRetour = null;

				Membre membre = new Membre(idMembre, nom, prenom, adresse, email, telephone, abonnement); // Attention :
																											// possibles
																											// doublons
				Livre livre = new Livre(idLivre, titre, auteur, ISBN);
				Emprunt emprunt = new Emprunt(id, membre, livre, dateEmprunt, dateRetour);
				emprunts.add(emprunt);
			}

			return emprunts;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?");

			pstmt.setInt(1, idMembre);

			ResultSet rs = pstmt.executeQuery();

			List<Emprunt> emprunts = new ArrayList<Emprunt>();

			while (rs.next()) {
				int id = rs.getInt("id");

				int idLivre = rs.getInt("idLivre");
				String titre = rs.getString("titre");
				String auteur = rs.getString("auteur");
				String ISBN = rs.getString("isbn");
				
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String adresse = rs.getString("adresse");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				Abonnement abonnement = Abonnement.valueOf(rs.getString("abonnement"));
				Membre membre = new Membre(idMembre, nom, prenom, adresse, email, telephone, abonnement);

				LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
				LocalDate dateRetour = null;

				Livre livre = new Livre(idLivre, titre, auteur, ISBN);
				Emprunt emprunt = new Emprunt(id, membre, livre, dateEmprunt, dateRetour);
				emprunts.add(emprunt);
			}

			return emprunts;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?");

			pstmt.setInt(1, idLivre);
			ResultSet rs = pstmt.executeQuery();
			
			List<Emprunt> emprunts = new ArrayList<Emprunt>();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				
				String titre = rs.getString("titre");
				String auteur = rs.getString("auteur");
				String ISBN = rs.getString("isbn");
				Livre livre = new Livre(idLivre, titre, auteur, ISBN);

				int idMembre = rs.getInt("idMembre");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String adresse = rs.getString("adresse");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				Abonnement abonnement = Abonnement.valueOf(rs.getString("abonnement"));

				LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
				LocalDate dateRetour = null;

				Membre membre = new Membre(idMembre, nom, prenom, adresse, email, telephone, abonnement); // Attention :
																											// possibles
																											// doublons
				Emprunt emprunt = new Emprunt(id, membre, livre, dateEmprunt, dateRetour);
				emprunts.add(emprunt);
			}

			return emprunts;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public Emprunt getById(int id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn
					.prepareStatement("SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email,\n"
							+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,\n" + "dateRetour\n"
							+ "FROM emprunt AS e\n" + "INNER JOIN membre ON membre.id = e.idMembre\n"
							+ "INNER JOIN livre ON livre.id = e.idLivre\n" + "WHERE e.id = ?");

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			rs.next();

			int idMembre = rs.getInt("idMembre");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String adresse = rs.getString("adresse");
			String email = rs.getString("email");
			String telephone = rs.getString("telephone");
			Abonnement abonnement = Abonnement.valueOf(rs.getString("abonnement"));

			int idLivre = rs.getInt("idLivre");
			String titre = rs.getString("titre");
			String auteur = rs.getString("auteur");
			String ISBN = rs.getString("isbn");

			LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
			java.sql.Date dateRetourSQL = rs.getDate("dateRetour");
			LocalDate dateRetour = (dateRetourSQL==null)? null : dateRetourSQL.toLocalDate();

			Membre membre = new Membre(idMembre, nom, prenom, adresse, email, telephone, abonnement);
			Livre livre = new Livre(idLivre, titre, auteur, ISBN);
			Emprunt emprunt = new Emprunt(id, membre, livre, dateEmprunt, dateRetour);

			return emprunt;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void create(Emprunt emprunt) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?)");

			pstmt.setInt(1, emprunt.getMembre().getId());
			pstmt.setInt(2, emprunt.getLivre().getId());
			pstmt.setDate(3, java.sql.Date.valueOf(emprunt.getDateEmprunt()));
			pstmt.setDate(4, (emprunt.getDateRetour()==null)? null : java.sql.Date.valueOf(emprunt.getDateRetour()));

			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

	}

	@Override
	public void update(Emprunt emprunt) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?");
			pstmt.setInt(1, emprunt.getMembre().getId());
			pstmt.setInt(2, emprunt.getLivre().getId());
			pstmt.setDate(3, java.sql.Date.valueOf(emprunt.getDateEmprunt()));
			pstmt.setDate(4, java.sql.Date.valueOf(emprunt.getDateRetour()));
			pstmt.setInt(5, emprunt.getId());

			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public int count() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(id) AS count FROM emprunt");

			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt("count");

			return count;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

}
