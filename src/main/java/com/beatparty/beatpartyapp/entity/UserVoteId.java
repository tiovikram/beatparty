package com.beatparty.beatpartyapp.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * UserVoteId represents the composite primary key of song id and user id in the user_votes table.
 */
@Data
public class UserVoteId implements Serializable {
    private String userId;
    private int songId;

    // Default constructor required for serialization
    public UserVoteId() {
    }

    /**
     * Creates a new UserVoteId with the provided values.
     *
     * @param userId - the ID of the user voting
     * @param songId - the ID of the song the user is voting on
     */
    public UserVoteId(String userId, int songId) {
        this.userId = userId;
        this.songId = songId;
    }
}
