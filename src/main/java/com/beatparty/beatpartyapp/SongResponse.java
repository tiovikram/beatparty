/*
 * Title: SongResponse
 * Author: Vikram Guhan Subbiah
 * Date: Wednesday, 28th April, 2021
 */ 

package com.beatparty.beatpartyapp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

// TODO: Serialize Date class later
import java.util.Date;

/**
 * An immutable variant of Song.class for JSON deserialization
 */
@Data
public class SongResponse {

    private final String id;
    private final String artist;
    private final String date;
    private final String name;
    private final int upvotes;
    private final String url;

    /**
     * Constructor for JsonCreator object deserialization
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SongResponse(@JsonProperty("id") String id,
			@JsonProperty("artistName") String artist,
                        @JsonProperty("uploadDate") String date,
                        @JsonProperty("songName") String name,
                        @JsonProperty("votes") Integer upvotes,
                        @JsonProperty("songLink") String url) {
	this.id = id;
        this.artist = artist;
        this.date = date;
        this.name = name;
        this.upvotes = upvotes;
        this.url = url;
    }

}
