//package cat.itacademy.s5._1.mappers;
//
//
//import cat.itacademy.s5._1.dtos.PlayerDTO;
//import cat.itacademy.s5._1.entities.Player;
//
//import org.mapstruct.Mapper;
//
//
//@Mapper(componentModel = "spring")
//public interface PlayerMapper {
//
//
//    // @Mapping(target = "email", source = "playerEmail") // on peut ajouter cette annotation par exemple si les deux fields n'ont pas le même nom dans le DTO et dans l'entité
//    PlayerDTO convertPlayerToDto(Player player);
//    Player convertDtoToPlayer(PlayerDTO playerDTO);
//}
//    /**
//     * Convertit un PlayerDTO (reçu de l'API) en entité Player
//     // * @param dto L'objet de transfert reçu du front-end/API
//     * @return Entité Player prête à être persistée
//     // ET VICE VERSA **/
//
