package com.carepath.configuration;

import java.text.ParseException;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.carepath.exception.AppException;
import com.carepath.exception.ErrorCode;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {
    @Value("${jwt.signerKey}")
    private String JWT_KEY;

    @Value("${jwt.valid_duration}")
    private long JWT_EXP_MS;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.JWT_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(UserDetailsImpl userPrincipal) {

        Date issuedAt = new Date();
        return Jwts.builder()
                .subject((userPrincipal.getUsername()))
                .issuedAt(issuedAt)
                .expiration(new Date(issuedAt.getTime() + JWT_EXP_MS))
                .signWith(this.getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public SignedJWT verifyToken(String token) throws ParseException,JOSEException{
        JWSVerifier verifier = new MACVerifier(JWT_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expDate = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
         if (!(verified && expDate.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATE);
         return signedJWT;
    }
}
