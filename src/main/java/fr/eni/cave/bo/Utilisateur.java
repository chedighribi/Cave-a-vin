package fr.eni.cave.bo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "pseudo" })
@ToString(of = { "pseudo", "nom", "prenom" })
@SuperBuilder

@Entity
@Table(name = "CAV_USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {
@Id
@Column(name = "LOGIN", nullable = false, length = 255)
private String pseudo;
@Column(name = "PASSWPORD", nullable = false, length = 68)
private String password;
@Column(name = "LAST_NAME", nullable = false, length = 90)
private String nom;
@Column(name = "FIRST_NAME", nullable = false, length = 150)
private String prenom;
}