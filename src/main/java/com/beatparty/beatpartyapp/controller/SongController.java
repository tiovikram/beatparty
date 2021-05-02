package com.beatparty.beatpartyapp.controller;

import com.beatparty.beatpartyapp.dao.SongDao;
import com.beatparty.beatpartyapp.entity.Song;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * SongController maps http endpoints to their corresponding backend API calls.
 */
@RestController
public class SongController {

    @Autowired
    SongDao songDao;

    /**
     * API to fetch the top 'count' number of songs.
     *
     * @param count - The number of songs to fetch
     * @return list of songs in descending order by number of votes
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getSongs/{count}")
    public List<Song> getSongs(@PathVariable int count) {
        return songDao.getSongs(count);
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
     * APi to up-vote or down-vote a particular song.
     *
     * @param id - the id of the song to perform the vote on
     * @param vote - A boolean where True represents and up-vote and False represents a down-vote
     * @return "Vote successful"
     */
    @RequestMapping(method = RequestMethod.POST, value = "/vote/{id}/{vote}")
    public String vote(@PathVariable int id, @PathVariable boolean vote) {
        return "Vote successful";
    }

    /**
     * API to delete a song.
     *
     * @param id - the id of the song to delete
     * @return "Song deleted"
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteSong/id")
    public String deleteSong(@PathVariable int id) {
        return "Song deleted";
    }

    /**
     * API to clear all songs in the database.
     *
     * @return "Songs deleted"
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteAllSongs")
    public String deleteAllSongs() {
        return "All songs deleted";
    }
}
