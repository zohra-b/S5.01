package cat.itacademy.s5._1.repositories;

import cat.itacademy.s5._1.entities.Player;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends ReactiveCrudRepository<Player, String> {

    Mono<Player> findByPlayerEmail(String playerEmail);
    Flux<Player> findAllByOrderByTotalScoreDesc();

}
