package com.prueba.almi.utilitarios;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtilidad {

        private static final long EXPIRATION_TIME = 864_000_000;
        private SecretKey secretKey;

        public String generateToken(String username) throws NoSuchAlgorithmException {
            Map<String, Object> claims = new HashMap<>();
            return createToken(claims, username);
        }

        private String createToken(Map<String, Object> claims, String subject) throws NoSuchAlgorithmException {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
            SecureRandom secureRandom = new SecureRandom();
            keyGen.init(256, secureRandom);
            secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);


            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();
        }


        public boolean validateToken(String token) {
            try {
                Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
                return true;
            } catch (Exception e) {
                return false;
            }
        }



}
