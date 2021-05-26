package com.beatparty.beatpartyapp.dao;

import com.beatparty.beatpartyapp.entity.Song;
import com.beatparty.beatpartyapp.entity.UserVote;
import com.beatparty.beatpartyapp.entity.UserVoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * UserVotesDao provides an interface to interact with the UserVotes database which stores
 * each vote for each user.
 */
@Repository
public interface UserVotesDao extends JpaRepository<UserVote, UserVoteId> {

    @Query(value = "SELECT * from user_votes u WHERE u.user_id == (:id)", nativeQuery = true)
    List<UserVote> getSongsVotedByUser(@Param("id") String id);
    
}
