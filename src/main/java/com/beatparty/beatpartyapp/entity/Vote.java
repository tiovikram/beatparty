package com.beatparty.beatpartyapp.entity;

/**
 * Vote is a POJO containing all the information necessary to perform a vote on a Song.
 */
public class Vote {
    private String userIdToken;
    private int songId;
    private boolean vote;

    /**
     * Default constructor for JSON mapping.
     */
    public Vote() {
    }

    /**
     * Creates a new Vote with the provided values.
     *
     * @param userIdToken - the ID token of an authenticated user performing a vote
     * @param songId - the ID of the song being voted on
     * @param vote - a boolean where true is an upvote and false is a downvote
     */
    public Vote(String userIdToken, int songId, boolean vote) {
        this.userIdToken = userIdToken;
        this.songId = songId;
        this.vote = vote;
    }

    /**
     * Returns the userIDToken.
     */
    public String getUserIdToken() {
        return userIdToken;
    }

    /**
     * Returns the songId.
     */
    public int getSongId() {
        return songId;
    }

    /**
     * Returns the vote value.
     */
    public boolean getVote() {
        return vote;
    }
}
