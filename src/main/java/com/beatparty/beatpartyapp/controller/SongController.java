package com.beatparty.beatpartyapp.controller;

import com.beatparty.beatpartyapp.dao.SongDao;
import com.beatparty.beatpartyapp.entity.Song;
import com.beatparty.beatpartyapp.entity.Vote;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * SongController maps http endpoints to their corresponding backend API calls.
 */
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class SongController {

    @Autowired
    SongDao songDao;

    /**
     * API to fetch the top 'count' number of songs. May return fewer that 'count' songs.
     *
     * @param count - The number of songs to fetch
     * @return list of songs in descending order by number of votes
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getSongs/{count}")
    public List<Song> getSongs(@PathVariable int count) {
        checkCount(count);
        return songDao.getSongs(count);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getShuffledSongs/{count}")
    public List<Song> getShuffledSongs(@PathVariable int count) {
        checkCount(count);
        return songDao.getShuffledSongs(count);
    }

    /**
     * API to upload a new song.
     *
     * @param song - the song to upload
     * @return "Upload successful"
     */
    @RequestMapping(method = RequestMethod.POST, value = "/uploadNewSong")
    public String uploadSong(@RequestBody Song song) {
        songDao.save(song);
        return "Upload successful";
    }

    /**
     * API to up-vote or down-vote a particular song.
     *
     * @param vote - the vote to perform
     * @throws ResponseStatusException (bad request) if song id < 1
     * @return "Vote successful" when vote is successful, "Vote unsuccessful" otherwise
     */
    @RequestMapping(method = RequestMethod.POST, value = "/vote")
    public String vote(@RequestBody Vote vote) {
        int songId = vote.getSongId();

        checkId(vote.getSongId());
        try {
            Song s = songDao.getOne(songId);
            String response;
            if (vote.getVote()) {
                s.upvote();
                response = "Upvote successful";
            } else {
                s.downvote();
                response = "Downvote successful";
            }
            songDao.saveAndFlush(s);
            return response;
        } catch (Exception e) {
            return "Vote unsuccessful: invalid id given";
        }
    }

    /**
     * API to delete a song.
     *
     * @param id - the id of the song to delete
     * @throws ResponseStatusException (bad request) if id < 1
     * @return "Song deleted"
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteSong/{id}")
    public String deleteSong(@PathVariable int id) {
        checkId(id);
        songDao.deleteById(id);
        return "Song deleted";
    }

    /**
     * API to clear all songs in the database.
     *
     * @return "Songs deleted"
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteAllSongs")
    public String deleteAllSongs() {
        songDao.deleteAllInBatch();
        return "All songs deleted";
    }

    // Throws a ResponseStatusException (bad request) if id < 1
    private void checkId(int id) {
        // id must be positive
        if (id < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
    }

    // Throws a ResponseStatusException (bad request) if count < 0
    private void checkCount(int count) {
        // count must be non-negative
        if (count < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid count");
        }
    }
}
