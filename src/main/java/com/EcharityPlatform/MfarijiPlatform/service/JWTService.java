package com.EcharityPlatform.MfarijiPlatform.service;

import com.EcharityPlatform.MfarijiPlatform.Entity.Widow;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {

    /**
     * The secret key to encrypt the JWTs with.
     */
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    /**
     * The issuer the JWT is signed with.
     */
    @Value("${jwt.issuer}")
    private String issuer;
    /**
     * How many seconds from generation should the JWT expire?
     */
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;
    /**
     * The algorithm generated post construction.
     */
    private Algorithm algorithm;
    /**
     * The JWT claim key for the username.
     */
    private static final String USERNAME_KEY = "USERNAME";
    private static final String EMAIL_KEY = "EMAIL";

    /**
     * Post construction method.
     */
    @PostConstruct
    public void postConstruct() throws UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    /**
     * Generates a JWT based on the given user.
     *
     * @param widow The user to generate for.
     * @return The JWT.
     */
    public String generateJWT(Widow widow) {
        return JWT.create()
                .withClaim(USERNAME_KEY, widow.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    /**
     * Generates a special token for verification of an email.
     *
     * @param widow The user to create the token for.
     * @return The token generated.
     */
    public String generateVerificationJWT(Widow widow) {
        return JWT.create()
                .withClaim(EMAIL_KEY, widow.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    /**
     * Gets the username out of a given JWT.
     *
     * @param token The JWT to decode.
     * @return The username stored inside.
     */
    public String getUsername(String token) {
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }
}
