package cat.itacademy.s5._1.repositories;

import cat.itacademy.s5._1.dtos.GameDTO;
import cat.itacademy.s5._1.entities.Game;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;

@Repository
public interface GameRepository extends ReactiveMongoRepository<Game, String> { //Game et String :ObjectID =

    Mono<Game> findById(ObjectId gameId);
    Flux<GameDTO> findByPlayerId(String playerId);




    // save(T entity)	Sauvegarde (insert ou update)	Mono<T>
    // saveAll(Iterable<T> entities)	Sauvegarde plusieurs éléments	Flux<T>
    // findById(ID id)	Cherche par identifiant	Mono<T>
    // findAll()	Renvoie tous les documents de la collection	Flux<T>
    // existsById(ID id)	Vérifie si un ID existe	Mono<Boolean>
    // count()	Compte tous les documents	Mono<Long>
    // deleteById(ID id)	Supprime un document par ID	Mono<Void>
    // delete(T entity)	Supprime un document	Mono<Void>
    // deleteAll()	Supprime toute la collection	Mono<Void>
    // deleteAll(Iterable<? extends T>)	Supprime plusieurs documents	Mono<Void>
}
