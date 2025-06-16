package cat.itacademy.s5._1.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("players")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Builder.Default  // qd on appelle builder pour creer un nouveau joueur, il cree ça par defaut
    @Id
    private UUID playerID = UUID.randomUUID();

    private String playerName;
    private String playerEmail;

    @Builder.Default
    private Date createdAt = new Date();

}
