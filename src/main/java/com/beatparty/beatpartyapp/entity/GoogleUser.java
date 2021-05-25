package com.beatparty.beatpartyapp.entity;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import java.io.*;
import java.security.GeneralSecurityException;

public class GoogleUser {

    private final String id;
    private final String email;
    private final String name;

    public GoogleUser(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
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

    public String getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }
}
