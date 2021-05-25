package com.beatparty.beatpartyapp.entity;

/**
 * An immutable class that represents a Google user. This class stores the associated user's ID,
 * name and email.
 */
public class GoogleUser {

    private final String id;
    private final String email;
    private final String name;

    /**
     * Constructs a new GoogleUser for a user that has the given ID, email, and name.
     *
     * @param id the Google user's ID
     * @param email the Google user's email
     * @param name the Google user's name
     */
    public GoogleUser(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    /**
     * Returns this GoogleUser's ID.
     *
     * @return this GoogleUser's ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns this GoogleUser's email.
     *
     * @return this GoogleUser's email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Returns this GoogleUser's name.
     *
     * @return this GoogleUser's name
     */
    public String getName() {
        return this.name;
    }
}
