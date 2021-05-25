package com.beatparty.beatpartyapp.util;

import com.beatparty.beatpartyapp.entity.GoogleUser;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * A helper class that helps construct instances of the GoogleUser class.
 *
 */
public class GoogleUserHelper {

    private static final GoogleIdTokenVerifier VERIFIER = new GoogleIdTokenVerifier
            .Builder(new NetHttpTransport(), new GsonFactory())
            // Suppress audience check by passing in null
            .setAudience(null)
            .build();

    /**
     * Returns a new GoogleUser using information retrieved from Google using the given ID token
     * string.
     *
     * @param idTokenString an ID token string associated with a Google user
     * @return a new GoogleUser with information about the account associated with the given
     *         ID token string if the ID token string is valid. Otherwise, returns null.
     * @throws GeneralSecurityException See Google API JavaDoc
     *                                  https://googleapis.dev/java/google-api-client/latest/
     * @throws IOException See Google API JavaDoc
     *                     https://googleapis.dev/java/google-api-client/latest/
     */
    public static GoogleUser getGoogleUser(String idTokenString)
            throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = VERIFIER.verify(idTokenString);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            String id = payload.getSubject();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            return new GoogleUser(id, email, name);
        } else {
            return null;
        }
    }
}
