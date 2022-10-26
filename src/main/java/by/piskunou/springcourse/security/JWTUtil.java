package by.piskunou.springcourse.security;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JWTUtil {
	@Value("${jwt_secret}")
	private String secret;
	
	public String generateToken(String username) {
		return JWT.create()
				  .withSubject("User details")
				  .withClaim("username", username)
				  .withIssuedAt(Instant.now())
				  .withIssuer("cichan")
				  .withExpiresAt(Instant.now().plusSeconds(3600))
				  .sign(Algorithm.HMAC256(secret));
	}
	
	public String validateAndRetrieveClaim(String token) throws JWTVerificationException {
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
									 .withSubject("User details")
									 .withIssuer("cichan")
									 .build();
		DecodedJWT decodedJWT = jwtVerifier.verify(token);
		return decodedJWT.getClaim("username")
						 .asString();
	}
}
