package fr.eni.cave.security.jwt;

import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;
import fr.eni.cave.bo.Utilisateur;
import fr.eni.cave.dal.UtilisateurRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthenticationService {
private UtilisateurRepository uRepository;
private AuthenticationManager authenticationManager;
private JwtService jwtService;
public AuthenticationResponse authenticate(AuthenticationRequest request) {
authenticationManager.authenticate(
new UsernamePasswordAuthenticationToken(
request.getPseudo(), request.getPassword()));
Utilisateur user = uRepository.findById(request.getPseudo()).orElseThrow();
String jwtToken = jwtService.generateToken(user);
AuthenticationResponse authResponse = new AuthenticationResponse();
authResponse.setToken(jwtToken);
return authResponse;
}
}