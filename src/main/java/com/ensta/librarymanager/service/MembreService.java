package com.ensta.librarymanager.service;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Membre;

public class MembreService implements IMembreService {
	
	private static MembreService instance;

	private MembreService() {
	}

	public static MembreService getInstance() {

		if (instance == null) {
			instance = new MembreService();
		}
		return instance;
	}
	
	private MembreDao membreDao = MembreDao.getInstance();

	@Override
	public List<Membre> getList() throws ServiceException {
		try {
			return this.membreDao.getList();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
		try {
			List<Membre> membres = this.getList();
			List<Membre> membresDispos = new ArrayList<Membre>();
			EmpruntService empruntService = EmpruntService.getInstance();
			for (Membre membre : membres) {
				if (empruntService.isEmpruntPossible(membre))
					membresDispos.add(membre);
			}
			return membresDispos;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public Membre getById(int id) throws ServiceException {
		try {
			return membreDao.getById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int create(Membre membre) throws ServiceException {
		try {
			if (membre.getNom()!=null && membre.getPrenom()!=null) {
				membre.setNom(membre.getNom().toUpperCase());
				return membreDao.create(membre);
			}
			else
				throw new ServiceException();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void update(Membre membre) throws ServiceException {
		try {
			if (membre.getNom()!=null && membre.getPrenom()!=null) {
				membre.setNom(membre.getNom().toUpperCase());
				membreDao.update(membre);
			}
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
			membreDao.delete(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int count() throws ServiceException {
		try {
			return membreDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
