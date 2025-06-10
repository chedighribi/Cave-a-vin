package fr.eni.cave.bo.client;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@ToString

@Builder
@Entity
@Table(name = "CAV_SHOPPING_CART")
public class Panier {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "SHOPPING_CART_ID")
private Integer id;

@Column(name = "ORDER_NUMBER", length = 200)
private String numCommande;

@Column(name = "TOTAL_PRICE", precision=2)
private float prixTotal;

@Column(name = "PAID")
private boolean paye;

@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
@JoinColumn(name="SHOPPING_CART_ID")
private @Builder.Default List<LignePanier> lignesPanier = new ArrayList<>();

@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
CascadeType.REFRESH }, fetch = FetchType.EAGER)
@JoinColumn(name = "CLIENT_ID")
private Client client;

}