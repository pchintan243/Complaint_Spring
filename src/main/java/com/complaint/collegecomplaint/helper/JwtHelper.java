package com.complaint.collegecomplaint.helper;

import java.util.*;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.complaint.collegecomplaint.entities.AppUser;
import com.complaint.collegecomplaint.repositories.AccountRepository;

import io.jsonwebtoken.*;

@Component
public class JwtHelper {

    @Autowired
    private AccountRepository accountRepository;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    @SuppressWarnings("deprecation")
    private Claims getAllClaimsFromToken(String token) {
        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(secret)
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        AppUser user = accountRepository.findByEmail(userDetails.getUsername()); // Initialize user here

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        claims.put("role", user.getClaims().getClaims());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    @SuppressWarnings("deprecation")
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
