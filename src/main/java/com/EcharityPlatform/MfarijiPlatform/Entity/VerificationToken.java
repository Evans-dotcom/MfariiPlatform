package com.EcharityPlatform.MfarijiPlatform.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "verification_token")
public class VerificationToken {

    /** The unique id for the record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /** The token that was sent to the user. */
    @Lob
    @Column(name = "token", nullable = false, unique = true)
    private String token;
    /** The timestamp of when the token was created. */
    @Column(name = "created_timestamp", nullable = false)
    private Timestamp createdTimestamp;
    /** The user this verification token is for. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "widow_id", nullable = false)
    private Widow  widow;


    /**
     * Gets the widow.
     * @return The widow.
     */
    public Widow getWidow() {
        return widow;
    }

    /**
     * Sets the widow.
     * @param widow The widow.
     */
    public void setWidow(Widow widow) {
        this.widow = widow;
    }

    /**
     * The timestamp when the token was created.
     * @return The timestamp.
     */
    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    /**
     * Sets the timestamp of when the token was created.
     * @param createdTimestamp The timestamp.
     */
    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    /**
     * Gets the token.
     * @return The token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token.
     * @param token The token.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the id.
     * @return The id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id The id.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
