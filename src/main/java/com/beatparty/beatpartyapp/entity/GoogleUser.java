package com.beatparty.beatpartyapp.entity;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * A class that represents a Google user. Given an ID token string, this class stores the
 * associated user's ID, name and email.
 */
public class GoogleUser {

    private final String id;
    private final String email;
    private final String name;

    /**
     * Constructs a new GoogleUser for a user that has the given ID.
     *
     * @param idTokenString the Google user's ID token retrieved from the sign-in
     * @throws GeneralSecurityException See Google API JavaDoc
     *                                  https://googleapis.dev/java/google-api-client/latest/
     * @throws IOException See Google API JavaDoc
     *                     https://googleapis.dev/java/google-api-client/latest/
     */
    public GoogleUser(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), new GsonFactory())
                // Suppress audience check by passing in null
                .setAudience(null)
                .build();
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            this.id = payload.getSubject();
            this.email = payload.getEmail();
            this.name = (String) payload.get("name");
        } else {
            this.id = null;
            this.email = null;
            this.name = null;
        }
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
