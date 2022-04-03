package ensta;

import java.time.LocalDate;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

public class ServiceTest {
	
public static void main(String[] args) {
		
		LivreService livreService = LivreService.getInstance();
		
		Livre livre = new Livre(0, "The Ultimate Question of Life, the Universe and Everything",
				"Douglas Adams", "4-4242-4242-2");
		
		try {
			System.out.println(livreService.getList());
			System.out.println(livreService.getListDispo());
			System.out.println(livreService.getById(1));
			int id = livreService.create(livre);
			livre.setId(id);
			System.out.println(id);
			System.out.println(livreService.getList());
			livreService.delete(id);
			System.out.println(livreService.getList());
			System.out.println(livreService.count());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		MembreService membreService = MembreService.getInstance();
		
		Membre membre = new Membre(0, "Solo", "Han", "Somewer in space", "han.solo@gmail.com",
				"07 30 42 12 24", Abonnement.VIP);
		
		try {
			System.out.println(membreService.getList());
			System.out.println(membreService.getListMembreEmpruntPossible());
			System.out.println(membreService.getById(1));
			int id = membreService.create(membre);
			membre.setId(id);
			System.out.println(id);
			System.out.println(membreService.getList());
			membreService.delete(id);
			System.out.println(membreService.getList());
			System.out.println(membreService.count());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		EmpruntService empruntService = EmpruntService.getInstance();
		Emprunt emprunt = new Emprunt(0, membre, livre, LocalDate.of(2022, 2, 1), LocalDate.of(2022, 2, 15));
		
		try {
			System.out.println(empruntService.getList());
			System.out.println(empruntService.getListCurrent());
			System.out.println(empruntService.getById(1));
			System.out.println(empruntService.count());
			empruntService.create(emprunt);
			System.out.println(empruntService.getList());
			System.out.println(empruntService.getList());
			System.out.println(empruntService.count());
			System.out.println(empruntService.isLivreDispo(2));
			System.out.println(empruntService.isEmpruntPossible(membreService.getById(2)));
		} catch (ServiceException e) {
			e.printStackTrace();
		}

	}

}
