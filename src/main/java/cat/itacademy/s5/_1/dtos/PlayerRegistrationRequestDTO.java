package cat.itacademy.s5._1.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRegistrationRequestDTO {
    private String name;
    private String email;
}