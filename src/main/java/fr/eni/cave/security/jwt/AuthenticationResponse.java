package fr.eni.cave.security.jwt;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AuthenticationResponse {
private String token;
}