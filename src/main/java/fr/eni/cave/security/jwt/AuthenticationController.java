package fr.eni.cave.security.jwt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caveavin/auth")
public class AuthenticationController {
private AuthenticationService authenticationService;

public void AuthentificationController(AuthenticationService authenticationService) {
this.authenticationService = authenticationService;
}

@PostMapping
public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
return ResponseEntity.ok(authenticationService.authenticate(request));
}
}
