package com.ensta.librarymanager.service;

import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Membre;

public class EmpruntService implements IEmpruntService {
	
	private static EmpruntService instance;
	
	private EmpruntService() {};
	
	public static EmpruntService getInstance() {
		if (instance == null) {
			instance = new EmpruntService();
		}
		return instance;
	}
	
	private EmpruntDao empruntDao = EmpruntDao.getInstance();

	@Override
	public List<Emprunt> getList() throws ServiceException {
		try {
			return empruntDao.getList();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrent() throws ServiceException {
		try {
			return empruntDao.getListCurrent();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
		try {
			return empruntDao.getListCurrentByMembre(idMembre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
		try {
			return empruntDao.getListCurrentByLivre(idLivre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public Emprunt getById(int id) throws ServiceException {
		try {
			return empruntDao.getById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void create(Emprunt emprunt) throws ServiceException {
		try {
			empruntDao.create(emprunt);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void returnBook(int id) throws ServiceException {
		try {
			Emprunt emprunt = empruntDao.getById(id);
			emprunt.setDateRetour(LocalDate.now());
			empruntDao.update(emprunt);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int count() throws ServiceException {
		try {
			return empruntDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public boolean isLivreDispo(int idLivre) throws ServiceException {
		try {
			return (empruntDao.getListCurrentByLivre(idLivre).size()==0);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
		try {
			int maxEmprunt = 0;
			switch (membre.getAbonnement()) {
			case BASIC:
				maxEmprunt = 2;
				break;
			case PREMIUM:
				maxEmprunt = 5;
				break;
			case VIP:
				maxEmprunt = 20;
				break;
			}
			return (empruntDao.getListCurrentByMembre(membre.getId()).size()<maxEmprunt);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
