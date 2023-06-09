package nl.inholland.it2bank.util;

import io.jsonwebtoken.*;
import nl.inholland.it2bank.model.UserRoles;
import nl.inholland.it2bank.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    // Link to the documentation of the used JWT library:
    // https://github.com/jwtk/jjwt

    @Value("${application.token.validity}")
    private long validityInMicroseconds;
    private final UsersDetailsService usersDetailsService;
    private final JwtKeyProvider jwtKeyProvider;

    public JwtTokenProvider(UsersDetailsService usersDetailsService, JwtKeyProvider jwtKeyProvider) {
        this.usersDetailsService = usersDetailsService;
        this.jwtKeyProvider = jwtKeyProvider;
    }

    public String createToken(String email, UserRoles role) throws JwtException {

        /* The token will look something like this

        {
          "sub": "admin",
          "aut": [
            {
              "role": "ROLE_ADMIN"
            }
          ],
          "iat": 1684073744,
          "exp": 1684077344
        }

        */

        // We create a new Claims object for the token
        // The username is the subject
        Claims claims = Jwts.claims().setSubject(email);

        // And we add an array of the roles to the auth element of the Claims
        // Note that we only provide the role as information to the frontend
        // The actual role based authorization should always be done in the backend code
        claims.put("auth", role);

        // We decide on an expiration date
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMicroseconds);

        // And finally, generate the token and sign it. .compact() then turns it into a string that we can return.
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(jwtKeyProvider.getPrivateKey())
                .compact();
    }

    public Authentication getAuthentication(String token) {

        // We will get the username from the token
        // And then get the UserDetails for this user from our service
        // We can then pass the UserDetails back to the caller
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(jwtKeyProvider.getPrivateKey()).build().parseClaimsJws(token);
            String email = claims.getBody().getSubject();
            UserDetails userDetails = usersDetailsService.loadUserByUsername(email);
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Bearer token not valid");
        }
    }
}