package fr.eni.cave.bo.client;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = {"pseudo", "nom", "prenom"})
@EqualsAndHashCode(of = {"pseudo"})
@Builder

@Entity
@Table(name= "CAV_CLIENT")
public class Client {
	
	@Id
	@Column(name="LOGIN", nullable= false, length = 255)
	private String pseudo;
	
	@Column(name="PASSWORD", nullable= false, length = 68)
	private String password;
	
	@Column(name="NOM", nullable= false, length = 90)
	private String nom; 
	
	@Column(name="PRENOM", nullable= false, length = 150)
	private String prenom;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval =  true, fetch = FetchType.EAGER)
	@JoinColumn(name = "ADRESS_ID")
	private Adresse adresse;
}
