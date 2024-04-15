package com.pragma.user.configuration.jwt;

import com.pragma.user.domain.models.User;
import com.pragma.user.domain.spi.IJwtServicePort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceAdapter implements IJwtServicePort, IJwtServiceAuthorization {

	@Value("${security.key.secret}")
	private String secretKey;

	@Value("${security.jwt.expiration.minutes}")
	private Long expirationTime;

	@Override
	public String generateToken(User subject) {
		return getToken(extraClaims(subject), subject);
	}

	private String getToken(Map<String, ?> extraClaims, User subject) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(subject.getEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(experationDate())
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();

	}

	private Map<String, Object> extraClaims(User subject) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("role", subject.getRole().getRol());
		claims.put("firstName", subject.getFirstName());

		return claims;
	}

	private Date experationDate() {
		return new Date(System.currentTimeMillis() + (expirationTime * 60 * 1000));
	}

	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	//AUTHORIZATION

	@Override
	public String getSubjectFromToken(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims allClaim = extractAllClaim(token);
		return claimsResolver.apply(allClaim);
	}

	private Claims extractAllClaim(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

}
