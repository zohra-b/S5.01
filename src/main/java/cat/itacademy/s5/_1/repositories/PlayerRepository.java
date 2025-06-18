package cat.itacademy.s5._1.repositories;

import cat.itacademy.s5._1.dto.PlayerDTO;
import cat.itacademy.s5._1.entities.Player;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends ReactiveCrudRepository<Player, UUID> {

    Mono<Player> findByPlayerEmail(String playerEmail);
    Flux<Player> findAllByOrderByTotalScoreDesc();


    // save(T entity)	Sauvegarde ou met à jour un enregistrement	Mono<T>
    // saveAll(Iterable<T> / Publisher<T>)	Sauvegarde plusieurs objets	Flux<T>
    // findById(ID id)	Recherche un enregistrement par son ID	Mono<T>
    // findAll()	Récupère tous les enregistrements	Flux<T>
    // existsById(ID id)	Vérifie si un enregistrement existe par ID	Mono<Boolean>
    // count()	Retourne le nombre total d’enregistrements	Mono<Long>
    // deleteById(ID id)	Supprime un enregistrement par ID	Mono<Void>
    // delete(T entity)	Supprime un enregistrement donné	Mono<Void>
    // deleteAll()	Supprime tous les enregistrements	Mono<Void>
    // deleteAll(Iterable<? extends T>)
}
