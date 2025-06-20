package cat.itacademy.s5._1;

import cat.itacademy.s5._1.menus.MainMenu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args)
				.getBean(MainMenu.class)
				.start()
				.subscribe( // <-- SEUL subscribe() de l'application !
						null,
						error -> System.err.println("Erreur : " + error),
						() -> System.out.println("Merci d'avoir joué !")
				);
	}

}
//public static void main(String[] args) {
//	ConfigurableApplicationContext context =
//			SpringApplication.run(Application.class, args);
//
//	MainMenu menu = context.getBean(MainMenu.class);
//
//	// Souscription explicite avec gestion des erreurs
//	menu.start().subscribe(
//			null,
//			error -> System.err.println("Erreur : " + error),
//			() -> System.out.println("Merci d'avoir joué !")
//	);
//
//	// Optionnel : Arrêter proprement Spring après la fin du menu
//	context.close();
//}
