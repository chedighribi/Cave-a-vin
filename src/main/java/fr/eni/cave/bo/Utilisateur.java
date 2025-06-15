package fr.eni.cave.bo;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class Utilisateur implements UserDetails{
	
	private static final long serialVersionUID = 1L;
@Id
@Column(name = "LOGIN", nullable = false, length = 255)
private String pseudo;
@Column(name = "PASSWORD", nullable = false, length = 68)
private String password;
@Column(name = "LAST_NAME", nullable = false, length = 90)
private String nom;
@Column(name = "FIRST_NAME", nullable = false, length = 150)
private String prenom;

@Column(length= 15)
private String authority;

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	return Arrays.asList(new  SimpleGrantedAuthority(authority));
}

@Override
public String getUsername() {
	return pseudo;
}
}