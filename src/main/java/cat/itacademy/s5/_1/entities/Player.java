package cat.itacademy.s5._1.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("players")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player implements Persistable<String> {  //IMPLEMENTE PERSISTABLE

    @Id
    @Column("playerId")
    private String playerId;

    @Column("playerName")
    private String playerName;

    @Column("playerEmail")
    private String playerEmail;

    @Column("createdAt")
    private LocalDateTime createdAt;

    @Column("totalScore")
    private int totalScore;

    @Transient  //Ce champ n'existe QUE en mémoire Java, jamais dans la base de données
    @Builder.Default  //Valeur par défaut pour le pattern Builder de Lombok
    private boolean isNewEntity = false; // Le "drapeau" qui dit si l'entité est nouvelle ou pas


    public Player(String playerName, String playerEmail) {
        this.playerId = UUID.randomUUID().toString();
        this.playerName = playerName;
        this.playerEmail = playerEmail;
        this.createdAt = LocalDateTime.now();
        this.totalScore = 0;
        this.isNewEntity = true; ////MARQUER COMME NOUVEAU !
    }

    @Override   // METHODE OBLIGATOIRE DE PERSISTABLE
    public String getId() {
        return this.playerId;
    }

    @Override// METHODE OBLIGATOIRE DE PERSISTABLE
    public boolean isNew() {
        return this.isNewEntity;
    }
    //Si isNew() retourne true → INSERT
    //Si isNew() retourne false → UPDATE

    // Méthode pour marquer l'entité comme persistée
    public void markNotNew() {
        this.isNewEntity = false;
    }
}
