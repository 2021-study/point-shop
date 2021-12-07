/**
 * ===============================================================
 * File name : TokenProvider.java
 * Created by injeahwang on 2021-07-11
 * ===============================================================
 */
package product.demo.shop.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;


@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long tokenValidityInMilliseconds ;

    private Key key;

//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Value("${jwt.token-validity-in-seconds}")
//    private long  tokenValidityInMilliSeconds;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds:86400}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds  = tokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

//        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+09:00"));
//        LocalDateTime validity = LocalDateTime.ofInstant(Instant.ofEpochMilli(now + this.tokenValidityInMilliseconds), ZoneId.of("Asia/Seoul"));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        logger.info("Validity : " + validity);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
//                .setExpiration(Date.from(validity.atZone(ZoneId.systemDefault()).toInstant()))
                .compact();
    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        //UserDetails의 User임.
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return true;
    }
}
