package cat.itacademy.s5._1.repositories;

import cat.itacademy.s5._1.entities.Game;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;

@Repository
public interface GameRepository extends ReactiveMongoRepository<Game, String> { //Game et String :ObjectID =

    Mono<Game> findById(ObjectId gameId);
    Flux<Game> findByPlayerId(String playerId);

}
