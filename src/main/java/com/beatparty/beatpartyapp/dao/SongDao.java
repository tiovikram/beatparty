package com.beatparty.beatpartyapp.dao;

import com.beatparty.beatpartyapp.entity.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * SongDao provides an interface through which the Songs database can be manipulated.
 */
@Repository
public interface SongDao extends JpaRepository<Song, Integer> {

    /**
     * Queries the database to retrieve the top 'count' number of songs in descending order by
     * number of votes.
     *
     * @param count the number of songs to fetch
     * @return a list of the top 'count' number of songs in descending order by number of votes
     */
    @Query(value = "SELECT TOP (:count) * from Songs s ORDER BY s.votes DESC", nativeQuery = true)
    List<Song> getSongs(@Param("count") int count);

    @Query(value = "SELECT TOP (:count) * FROM Songs ORDER BY NEWID()", nativeQuery = true)
    List<Song> getShuffledSongs(@Param("count") int count);

    @Query(value = "SELECT 1 from Songs s WHERE s.id == (:id)", nativeQuery = true)
    Song getSongByID(@Param("id") int id);

}
