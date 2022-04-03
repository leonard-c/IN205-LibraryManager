package ensta;

import java.time.LocalDate;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;

public class DaoTest {

	public static void main(String[] args) {
		
		LivreDao livreDao = LivreDao.getInstance();
		
		Livre livre = new Livre(0, "The Ultimate Question of Life, the Universe and Everything",
				"Douglas Adams", "4-4242-4242-2");
		
		try {
			System.out.println(livreDao.getList());
			System.out.println(livreDao.getById(1));
			int id = livreDao.create(livre);
			livre.setId(id);
			System.out.println(id);
			System.out.println(livreDao.getList());
			livreDao.delete(id);
			System.out.println(livreDao.getList());
			System.out.println(livreDao.count());
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		MembreDao membreDao = MembreDao.getInstance();
		
		Membre membre = new Membre(0, "Solo", "Han", "Somewer in space", "han.solo@gmail.com",
				"07 30 42 12 24", Abonnement.VIP);
		
		try {
			System.out.println(membreDao.getList());
			System.out.println(membreDao.getById(1));
			int id = membreDao.create(membre);
			membre.setId(id);
			System.out.println(id);
			System.out.println(membreDao.getList());
			membreDao.delete(id);
			System.out.println(membreDao.getList());
			System.out.println(membreDao.count());
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		EmpruntDao empruntDao = EmpruntDao.getInstance();
		Emprunt emprunt = new Emprunt(0, membre, livre, LocalDate.of(2022, 2, 1), LocalDate.of(2022, 2, 15));
		
		try {
			System.out.println(empruntDao.getList());
			System.out.println(empruntDao.getListCurrent());
			System.out.println(empruntDao.getById(1));
			System.out.println(empruntDao.count());
			empruntDao.create(emprunt);
			System.out.println(empruntDao.getList());
			System.out.println(empruntDao.getList());
			System.out.println(empruntDao.count());
		} catch (DaoException e) {
			e.printStackTrace();
		}

	}

}
