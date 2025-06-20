package cat.itacademy.s5._1.menus;

import cat.itacademy.s5._1.dtos.PlayerDTO;
import cat.itacademy.s5._1.services.PlayerService;
import cat.itacademy.s5._1.validations.ValidateInputs;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class MainMenu {
    private final PlayerService playerService;
    private UUID currentPlayerID;
    private String currentPlayerEmail;

    public MainMenu(PlayerService playerService) {
        this.playerService = playerService;
    }



    // 2 - show players game : findAllGamesByPlayerId()
    //                          --> submenu : gameById() --> 1 showGameDetails: If not ended :
    //                                                   --> 2 resumeGame
    //                                                    --> 3 deleteGame


    // 3 - show players ranking

    public Mono<Void> start(){
        System.out.println("""
                WELCOME TO OUR BLACK-JACK GAME !!!
                
                """);

        return loginOrCreatePlayer()
                .then(mainMenuManager());
    }




//    private Mono<PlayerDTO> loginOrCreatePlayer(){
//        String currentPlayerEmail = ValidateInputs.validateEmail("Enter your email");
//        Mono<UUID> currentPlayerId = playerService.getIdByPlayerEmail(currentPlayerEmail);
//        if (currentPlayerId!=null){
//                System.out.printf("Welcome back %s !",  playerService.findByID(currentPlayerID).map(Player::getPlayerName));
//                return playerService.findByID(currentPlayerId.block());
//        } else {
//            String currentPlayerName = ValidateInputs.validateString("Enter your name to create your profile");
//            Mono <PlayerDTO> newPlayerDto =
//            System.out.printf("Welcome %s !", currentPlayerName);
//                return newPlayerDto;
//        }
        /// ////// START ENTER YOUR EMAIL, CREATE,  reste la suite



    public static int mainMenuOptions() {
            // if ! existsByEmail(welcomeMenu)   playerService.createPlayer()
        return ValidateInputs.validateIntegerBetweenOnRange(""" 
                
                1.  Initiate a game
                2.  Display players ranking
                3.  Display the player's games
                4.  Delete the player
                
                Please enter a valid option number (0–4):
                """, 0, 4);
    }

    public Mono<Void> mainMenuManager() {
        return Mono.defer(()-> {

            switch (mainMenuOptions()) {
                case 1:
                    System.out.println("Initiating a new game...");
                    // TODO: Implement game initiation
                    return Mono.empty().then(mainMenuManager()); // to be replaced
                case 2:
//                  TODO : playerService.getPlayersSortedByScore() : to be implemented in Playercontroller
                    return Mono.empty().then(mainMenuManager());
                case 3:
                    System.out.println("Displaying your games...");
                    // TODO: Implement GamesByPlayerDTO .
                    return Mono.empty().then(mainMenuManager()); // to be replaced
                case 4:
                    System.out.printf("Deleting player profile for %s...%n", currentPlayerEmail);
                    // TODO : playerService.deletePlayerByEmail(currentPlayerEmail) to be implemented in Controller
                    return Mono.empty().then(mainMenuManager());
                case 0:
                    System.out.println("Exiting the game. Goodbye!");
                    return Mono.empty().then(mainMenuManager()); // Exit the application flow
                default:
                    System.out.println("Invalid option. Please try again.");
                    return mainMenuManager();
            }
        } );
    }



    private Mono<PlayerDTO> loginOrCreatePlayer() {
        this.currentPlayerEmail = ValidateInputs.validateEmail("Enter your email");
        return playerService.getIdByPlayerEmail(currentPlayerEmail)
                .flatMap(playerID -> {
                    this.currentPlayerID = playerID;
                    return playerService.findByID(playerID)
                            .doOnNext(player ->
                                    System.out.printf("Welcome back %s !%n", player.playerName()));
                })
                .switchIfEmpty(Mono.defer(() -> {   //Mono.defer() (au lieu de Mono.just() ensures that the player creation logic runs only if needed (lazily)
                            String currentPlayerName = ValidateInputs.validateString("Enter your name to create your profile");
                            System.out.printf("Welcome %s ! %n", currentPlayerName);
                            return playerService.createNewPlayer(currentPlayerName, currentPlayerEmail);
                        }
                ));
    }



}