package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreDao implements ILivreDao {
	
	private static LivreDao instance;
	
	private LivreDao() {}
	
	public static LivreDao getInstance() {
		
		if (instance==null) {
			instance = new LivreDao();
		}
		return instance;
	}

	@Override
	public List<Livre> getList() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement("SELECT id, titre, auteur, isbn FROM livre");
			
			ResultSet rs = pstmt.executeQuery();
			
			List<Livre> livres = new ArrayList<Livre>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String titre = rs.getString("titre");
				String auteur = rs.getString("auteur");
				String ISBN = rs.getString("isbn");
	
				Livre livre = new Livre(id, titre, auteur, ISBN);
				livres.add(livre);
			}

			return livres;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public Livre getById(int id) throws DaoException {

		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement("SELECT titre, auteur, isbn FROM livre WHERE id = ?");
			// "?" important dans le prepareStatement : bonne protection contre les
			// injections SQL

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String titre = rs.getString("titre");
			String auteur = rs.getString("auteur");
			String ISBN = rs.getString("isbn");

			Livre livre = new Livre(id, titre, auteur, ISBN);

			return livre;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public int create(Livre livre) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO livre(titre, auteur, isbn) VALUES(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, livre.getTitre());
			pstmt.setString(2, livre.getAuteur());
			pstmt.setString(3, livre.getIsbn());

			pstmt.execute();
			ResultSet generatedKey = pstmt.getGeneratedKeys();
			generatedKey.next();

			return generatedKey.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void update(Livre livre) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn
					.prepareStatement("UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?");
			pstmt.setString(1, livre.getTitre());
			pstmt.setString(2, livre.getAuteur());
			pstmt.setString(3, livre.getIsbn());
			pstmt.setInt(4, livre.getId());

			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void delete(int id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM livre WHERE id = ?");
			pstmt.setInt(1, id);

			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public int count() throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(id) AS count FROM livre");

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
