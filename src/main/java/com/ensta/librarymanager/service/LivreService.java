package com.ensta.librarymanager.service;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;

public class LivreService implements ILivreService{
	
	private static LivreService instance;
	
	private LivreService() {};
	
	public static LivreService getInstance() {
		
		if (instance==null) {
			instance = new LivreService();
		}
		
		return instance;
	}
	
	private LivreDao livreDao = LivreDao.getInstance();

	@Override
	public List<Livre> getList() throws ServiceException {
		try {
			return this.livreDao.getList();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Livre> getListDispo() throws ServiceException {
		try {
			List<Livre> livres = this.getList();
			List<Livre> livresDispos = new ArrayList<Livre>();
			EmpruntService empruntService = EmpruntService.getInstance();
			for (Livre livre : livres) {
				if (empruntService.isLivreDispo(livre.getId()))
					livresDispos.add(livre);
			}
			return livresDispos;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public Livre getById(int id) throws ServiceException {
		try {
			return this.livreDao.getById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int create(Livre livre) throws ServiceException {
		try {
			if (livre.getTitre() != null)
				return this.livreDao.create(livre);			// Question à propos de l'id ici ???
			else
				throw new ServiceException();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void update(Livre livre) throws ServiceException {
		try {
			if (livre.getTitre() != null)
				this.livreDao.update(livre);
			else
				throw new ServiceException();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		
	}

	@Override
	public void delete(int id) throws ServiceException {
		try {
			this.livreDao.delete(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int count() throws ServiceException {
		try {
			return livreDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	

}
