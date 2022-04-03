package ensta;

import java.time.LocalDate;

import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;

public class ModeleTest {

	public static void main(String[] args) {
		Livre livre = new Livre(42, "The Ultimate Question of Life, the Universe and Everything",
				"Douglas Adams", "4-4242-4242-2");
		Membre membre = new Membre(1, "Solo", "Han", "Somewer in space", "han.solo@gmail.com",
				"07 30 42 12 24", Abonnement.VIP);
		Emprunt emprunt = new Emprunt(1, membre, livre, LocalDate.of(2022, 2, 1), LocalDate.of(2022, 2, 15));
		
		System.out.println(livre);
		System.out.println(membre);
		System.out.println(emprunt);
	}

}
