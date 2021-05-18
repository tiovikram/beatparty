package com.beatparty.beatpartyapp.controller;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.beatparty.beatpartyapp.entity.Song;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * Integration test for interaction between SongController and database.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Transactional
public class SongControllerIntegrationTest {

    public static final String NAME = "name";
    public static final String ARTIST_NAME = "artistName";
    public static final Date UPLOAD_DATE = new Date();
    public static final String UPLOAD_LINK = "uploadLink";

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    private Song song;

    @BeforeEach
    private void init() {
        objectMapper = new ObjectMapper();
        song = new Song(NAME, ARTIST_NAME, UPLOAD_DATE, UPLOAD_LINK);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Test that empty database returns 0 songs on GetSongs API.
     */
    @Test
    public void getSongsNoSongs() throws Exception {
        // Check no songs are returned
        mvc.perform(get("/getSongs/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", empty()));
    }

    /**
     * Test database with one song returns it on getSongs API.
     */
    @Test
    public void getSongsOneSong() throws Exception {
        // Add a single song
        addSong();

        // Check it is returned
        int count = 1;
        mvc.perform(get("/getSongs/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(count)));
    }

    /**
     * Test no more than requested number of songs is returned by getSongs API.
     */
    @Test
    public void getSongsReturnsRequestedNumberOfSongs() throws Exception {
        // Add two songs
        addSong();
        addSong();

        // Check one song is returned
        int count = 1;
        mvc.perform(get("/getSongs/" + count))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(count)));
    }

    /**
     * Test getSongs API returns all songs when more songs than available are requested.
     */
    @Test
    public void getSongsReturnsAllSongsWhenGreaterCount() throws Exception {
        // Add two songs
        addSong();
        addSong();

        // Check two songs are returned
        int count = 3;
        mvc.perform(get("/getSongs/" + count))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    /**
     * Test getSongs returns BadRequest status code on negative count.
     */
    @Test
    public void getSongsReturnsBadRequest() throws Exception {
        int count = -1;
        mvc.perform(get("/getSongs/" + count))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test upvote registered.
     */
    @Test
    public void upvoteRegistered() throws Exception {
        addSong();
        int id = getId();

        // Perform a vote on the song
        boolean vote = true;
        mvc.perform(post("/vote/" + id + "/" + vote))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Check vote is registered
        mvc.perform(get("/getSongs/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].votes", is(1)));
    }

    /**
     * Test multiple upvotes registered.
     */
    @Test
    public void multipleUpvoteRegistered() throws Exception {
        addSong();
        int id = getId();

        // Perform two votes on the song
        boolean vote = true;
        for (int i = 1; i <= 2; i++) {
            mvc.perform(post("/vote/" + id + "/" + vote))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        // Check votes are registered
        mvc.perform(get("/getSongs/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].votes", is(2)));
    }

    /**
     * Test upvote registered.
     */
    @Test
    public void downvoteRegistered() throws Exception {
        addSong();
        int id = getId();

        // Perform a vote on the song
        boolean vote = false;
        mvc.perform(post("/vote/" + id + "/" + vote))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Check vote is registered
        mvc.perform(get("/getSongs/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].votes", is(-1)));
    }

    /**
     * Test multiple downvotes registered.
     */
    @Test
    public void multipleDownvotesRegistered() throws Exception {
        addSong();
        int id = getId();

        // Perform two votes on the song
        boolean vote = false;
        for (int i = 1; i <= 2; i++) {
            mvc.perform(post("/vote/" + id + "/" + vote))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        // Check votes are registered
        mvc.perform(get("/getSongs/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].votes", is(-2)));
    }

    /**
     * Test upvote and downvote registered.
     */
    @Test
    public void upvoteAndDownvote() throws Exception {
        addSong();
        int id = getId();

        // Perform a vote on the song
        mvc.perform(post("/vote/" + id + "/true"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mvc.perform(post("/vote/" + id + "/false"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Check vote is registered
        mvc.perform(get("/getSongs/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].votes", is(0)));
    }

    /**
     * Test vote returns BadRequest on invalid id.
     */
    @Test
    public void voteReturnsBadRequest() throws Exception {
        int id = 0;
        boolean vote = true;
        mvc.perform(post("/vote/" + id + "/" + vote))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test deleteSong works on one song.
     */
    @Test
    public void testDeleteSongOneSong() throws Exception {
        addSong();
        int id = getId();

        // Perform the delete
        mvc.perform(delete("/deleteSong/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Check no songs left
        mvc.perform(get("/getSongs/1"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", empty()));
    }

    /**
     * Test deleteSong returns BadRequest for invalid id.
     */
    @Test
    public void deleteSongReturnsBadRequest() throws Exception {
        int id = 0;
        mvc.perform(delete("/deleteSong/" + id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test deleteSong deletes specific songs.
     */
    @Test
    public void deleteSongSpecificSongs() throws Exception {
        // Add three songs
        addSong();
        addSong();
        int id = getId();
        addSong();

        // Delete one song
        mvc.perform(delete("/deleteSong/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Check id is different
        int newId = getId();
        Assertions.assertNotEquals(newId, id);

        // Delete new song
        mvc.perform(delete("/deleteSong/" + newId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Check last song is different from deleted ones
        int lastId = getId();
        Assertions.assertNotEquals(newId, lastId);
        Assertions.assertNotEquals(id, lastId);
    }

    /**
     * Test deleteAllSongs wipes the database.
     */
    public void deleteAllSongsTest() throws Exception {
        // add three songs
        for (int i = 1; i <= 3; i++) {
            addSong();
        }

        // Delete all songs
        mvc.perform(delete("/deleteAllsongs"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Check no songs left
        mvc.perform(get("/getSongs/1"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", empty()));
    }

    // Adds a generic song to the database
    private void addSong() throws Exception {
        mvc.perform(post("/uploadNewSong")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(song)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Returns id of a song in the database
    private int getId() throws Exception {
        String json = mvc.perform(get("/getSongs/1"))
                .andReturn().getResponse().getContentAsString();
        Song song = objectMapper.readValue(json, new TypeReference<List<Song>>(){}).get(0);
        return song.getId();
    }
}

