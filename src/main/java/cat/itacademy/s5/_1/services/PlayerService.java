package cat.itacademy.s5._1.services;

import cat.itacademy.s5._1.repositories.PlayerRepository;

import org.springframework.stereotype.Service;

@Service
public class PlayerService {

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
