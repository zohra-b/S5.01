//package cat.itacademy.s5._1.mappers;
//
//import cat.itacademy.s5._1.dtos.GameDTO;
//import cat.itacademy.s5._1.entities.Game;
//import cat.itacademy.s5._1.entities.Player;
//import cat.itacademy.s5._1.services.PlayerService;
//import reactor.core.publisher.Mono;
//
//
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel = "spring")
//public interface GameMapper {
//
//    @Mapping(target = "playerName",  // Champ destination dans GameDTO
//            expression = "java(getPlayerName(game.getPlayerID()))" )
//
//                                    // Permet d'exécuter du code Java personnalisé pendant le mapping
//                                    // équivaut à : gameDTO.setPlayerName(getPlayerName(game.getPlayerID()))
//                                    // Récupère playerID depuis l'entité Game (MongoDB) et appelle une méthode getPlayerName()
//                                                        //  à implémenter dans un autre service
//
//    @Mapping(target = "playerEmail", expression = "java(getPlayerEmail(game.getPlayerID()))")
//    public abstract GameDTO convertGameToDto(Game game);
//    public abstract Game convertDtoToGame(GameDTO gameDTO);
//
//
//
//}
