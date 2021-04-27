package com.beatparty.beatpartyapp.controller;

import com.beatparty.beatpartyapp.Song;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SongController maps http endpoints to their corresponding backend API calls
 */
@RestController
public class SongController {
    // list of all songs in application (for local testing purposes without database integration)
    Set<Song> uploadedSongs = new HashSet<>();

    /**
     * API to fetch the top 'count' number of songs
     *
     * @param count - The number of songs to fetch
     * @return list of songs, in descending order by number of votes
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getSongs/{count}")
    public Set<Song> getSongs(@PathVariable int count) {
        if (uploadedSongs.isEmpty()) {
            uploadTestSongs();
        }
        return uploadedSongs;
    }

    /**
     * API to upload a new song
     *
     * @param name - The name of the song
     * @param artistName - The artist who created the song
     * @param uploadDate - The time the song was uploaded
     * @param songLink - The link where the song is hosted
     * @return "Upload successful"
     */
    @RequestMapping(method = RequestMethod.GET, value = "/uploadNewSong/{Song}")
    public String uploadSong(@PathVariable Song name, @PathVariable String artistName,
                             @PathVariable String uploadDate, @PathVariable String songLink) {
        Song toAdd = new Song(name, artistName, new Date(), songLink);
        if (!uploadedSongs.contains(toAdd)) {
            uploadedSongs.add(toAdd);
        }
        return "Upload successful";
    }

    /**
     * APi to up-vote or down-vote a particular song
     *
     * @param name - The name of the song
     * @param artistName - The artist who created the song
     * @param vote - A boolean where True represents and up-vote and False represents a down-vote
     * @return "Vote successful"
     */
    @RequestMapping(method = RequestMethod.GET, value = "/vote/{name}/{artistName}/{vote}")
    public String vote(@PathVariable String name, @PathVariable String artistName, @PathVariable boolean vote) {
        return "Vote successful";
    }

    /**
     * API to delete a song
     *
     * @param name - The name of the song
     * @param artistName - The artist who created the song
     * @return "Song deleted"
     */
    @RequestMapping(method = RequestMethod.GET, value = "/deleteSong/{name}/{artistName}")
    public String deleteSong(@PathVariable String name, @PathVariable String artistName) {
        return "Song deleted";
    }

    /**
     * API to clear all songs in the database
     *
     * @return "Songs deleted"
     */
    @RequestMapping(method = RequestMethod.GET, value = "/deleteAllSongs")
    public String deleteAllSongs() {
        uploadedSongs.clear();
        return "Songs deleted";
    }

    // Populates local "database" with dummy songs
    private void uploadTestSongs() {
        Song a = new Song("song1", "artist1", new Date(), "www.song1.com");
        Song b = new Song("song2", "artist2", new Date(), "www.song2.com");
        Song c = new Song("song3", "artist3", new Date(), "www.song3.com");
        Song d = new Song("song4", "artist4", new Date(), "www.song4.com");
        Song e = new Song("song5", "artist5", new Date(), "www.song5.com");
        uploadedSongs.add(a);
        uploadedSongs.add(b);
        uploadedSongs.add(c);
        uploadedSongs.add(d);
        uploadedSongs.add(e);
    }
}
