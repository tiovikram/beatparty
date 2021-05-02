package com.beatparty.beatpartyapp.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Song is a POJO representing a song on the BeatParty platform.
 */
@Entity
@Table(name = "Songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // primary key - auto generated

    private String name;
    private String artistName;
    private int votes;

    @Temporal(TemporalType.DATE)
    private Date uploadDate;

    private String songLink;

    /**
     * Default constructor for JSON mapping.
     */
    public Song() {
    }

    /**
     * Creates a song with the given name by the given artist. A link to the song is also required
     *
     * @param name the name of the song
     * @param artistName the name of the artist
     * @param songLink a link to the song on a third-party streaming service
     */
    public Song(String name, String artistName, Date uploadDate, String songLink) {
        this.name = name;
        this.artistName = artistName;
        this.votes = 0;
        this.uploadDate = uploadDate;
        this.songLink = songLink;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getSongLink() {
        return songLink;
    }

    public int getVotes() {
        return votes;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void upvote() {
        votes++;
    }

    public void downvote() {
        votes--;
    }
}
