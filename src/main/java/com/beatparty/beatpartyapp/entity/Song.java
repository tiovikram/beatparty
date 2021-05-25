package com.beatparty.beatpartyapp.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 * Song is a POJO representing a song on the BeatParty platform.
 */
@Entity
@Data
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
     * Creates a song with the given name by the given artist. A link to the song is also required.
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

    /**
     * Returns the ID of this Song.
     *
     * @return this Song's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of this Song.
     *
     * @return this Song's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the name of this Song's artist.
     *
     * @return this Song's artist
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Returns the link to this Song.
     *
     * @return this Song's link
     */
    public String getSongLink() {
        return songLink;
    }

    /**
     * Returns the number of votes this song has.
     *
     * @return how many votes this Song has
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Returns this Song's upload date.
     *
     * @return this Song's upload date
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * Upvotes this song.
     */
    public void upvote() {
        votes++;
    }

    /**
     * Downvotes this song.
     */
    public void downvote() {
        votes--;
    }
}
