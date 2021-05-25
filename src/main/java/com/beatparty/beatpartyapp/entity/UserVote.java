package com.beatparty.beatpartyapp.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * UserVote represents an entry in the user_votes table.
 */
@Entity
@Data
@Table(name = "user_votes")
@IdClass(UserVoteId.class)
public class UserVote {
    @Id
    private String userId;

    @Id
    private int songId;

    // No-arg constructor needed for entity classes
    public UserVote() {
    }

    /**
     * Create a new UserVote with the provided parameters.
     *
     * @param userId - the id of the user voting
     * @param songId - the id of the song the user is voting on
     */
    public UserVote(String userId, int songId) {
        this.userId = userId;
        this.songId = songId;
    }
}
