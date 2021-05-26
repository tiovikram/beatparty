package com.beatparty.beatpartyapp.dao;

import com.beatparty.beatpartyapp.entity.UserVote;
import com.beatparty.beatpartyapp.entity.UserVoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserVotesDao provides an interface to interact with the UserVotes database which stores
 * each vote for each user.
 */
@Repository
public interface UserVotesDao extends JpaRepository<UserVote, UserVoteId> {
}
