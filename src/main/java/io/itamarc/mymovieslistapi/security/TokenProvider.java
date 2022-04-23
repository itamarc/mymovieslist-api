package io.itamarc.mymovieslistapi.security;

import io.itamarc.mymovieslistapi.config.AppProperties;
import io.itamarc.mymovieslistapi.exception.OAuth2AuthenticationProcessingException;
import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

@Slf4j
@Service
public class TokenProvider {
    @Autowired
    private UserRepository userRepository;

    private final AppProperties appProperties;

    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal;
        Object principal = authentication.getPrincipal();
        if (principal instanceof DefaultOidcUser) {
            log.debug("TokenProvider: DefaultOidcUser received");
            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
            Optional<User> optionalUser = userRepository.findByEmail(oidcUser.getEmail());
            if (optionalUser.isPresent()) {
                userPrincipal = UserPrincipal.create(optionalUser.get(), oidcUser.getAttributes());
            } else {
                throw new OAuth2AuthenticationProcessingException("No registered user found with e-mail '" + oidcUser.getEmail() + "'.");
            }
        } else {
            log.debug("TokenProvider: UserPrincipal received");
            userPrincipal = (UserPrincipal) authentication.getPrincipal();
        }

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

        SecretKey secretKey = Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes());

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes());
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(secretKey)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes());
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (SecurityException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
