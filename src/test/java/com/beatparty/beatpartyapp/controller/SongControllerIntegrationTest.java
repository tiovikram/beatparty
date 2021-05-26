package com.beatparty.beatpartyapp.controller;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.beatparty.beatpartyapp.entity.GoogleUser;
import com.beatparty.beatpartyapp.entity.Song;
import com.beatparty.beatpartyapp.entity.Vote;
import com.beatparty.beatpartyapp.util.GoogleUserHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    public static final String USER_ID = "UserId";
    public static final String USER_ID_TOKEN = "UserIdToken";
    public static final String SECOND_USER_ID = "SecondUserId";
    public static final String SECOND_USER_ID_TOKEN = "SecondUserIdToken";

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    private Song song;

    @MockBean
    GoogleUserHelper googleUserHelper;
    @Mock
    GoogleUser googleUser1;
    @Mock
    GoogleUser googleUser2;

    @BeforeEach
    private void init() throws GeneralSecurityException, IOException {
        objectMapper = new ObjectMapper();
        song = new Song(NAME, ARTIST_NAME, UPLOAD_DATE, UPLOAD_LINK);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Set up authentication mock
        Mockito.when(googleUserHelper.getGoogleUser(USER_ID_TOKEN)).thenReturn(googleUser1);
        Mockito.when(googleUser1.getId()).thenReturn(USER_ID);
        Mockito.when(googleUserHelper.getGoogleUser(SECOND_USER_ID_TOKEN)).thenReturn(googleUser2);
        Mockito.when(googleUser2.getId()).thenReturn(SECOND_USER_ID);
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
     * Test getSongsVotedByUser API when no songs voted.
     */
    @Test
    public void getSongsVotedByUserNoSongs() throws Exception {
        mvc.perform(get("/getSongsVotedByUser/" + USER_ID_TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", empty()));
    }

    /**
     * Test getSongsVotedByUser API when user has voted for a single song.
     */
    @Test
    public void getSongsVotedByUserOneSong() throws Exception {
        // vote for a song
        addSong();
        int id = getIds(1).get(0);
        Vote vote = new Vote(USER_ID_TOKEN, id, true);
        mvc.perform(post("/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vote)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Check voted song is returned
        mvc.perform(get("/getSongsVotedByUser/" + USER_ID_TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", is(id)));
    }

    /**
     * Test getSongsVotedByUser API when user has voted for multiple songs.
     */
    @Test
    public void getSongsVotedByUserMultipleSongs() throws Exception {
        // vote for two songs
        addSong();
        addSong();
        for (int id : getIds(2)) {
            Vote vote = new Vote(USER_ID_TOKEN, id, true);
            mvc.perform(post("/vote")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(vote)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        // Check voted songs are returned
        mvc.perform(get("/getSongsVotedByUser/" + USER_ID_TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    /**
     * Test getSongsVotedByUser API when other users have also voted.
     */
    @Test
    public void getSongsVotedByUserMultipleUsers() throws Exception {
        // Vote on two different songs by two different user
        addSong();
        addSong();
        List<Integer> ids = getIds(2);
        Vote vote = new Vote(USER_ID_TOKEN, ids.get(0), true);
        mvc.perform(post("/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vote)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        vote = new Vote(SECOND_USER_ID_TOKEN, ids.get(1), true);
        mvc.perform(post("/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vote)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Check single song is returned for each user
        mvc.perform(get("/getSongsVotedByUser/" + USER_ID_TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
        mvc.perform(get("/getSongsVotedByUser/" + SECOND_USER_ID_TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    /**
     * Test upvote registered.
     */
    @Test
    public void upvoteRegistered() throws Exception {
        addSong();
        int id = getIds(1).get(0);

        // Perform a vote on the song
        Vote vote = new Vote(USER_ID_TOKEN, id, true);
        mvc.perform(post("/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vote)))
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
        int id = getIds(1).get(0);

        // Perform two votes on the song from different users
        Vote vote = new Vote(USER_ID_TOKEN, id, true);
        mvc.perform(post("/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vote)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        vote = new Vote(SECOND_USER_ID_TOKEN, id, true);
        mvc.perform(post("/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vote)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Check votes are registered
        mvc.perform(get("/getSongs/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].votes", is(2)));
    }

    /**
     * Test multiple upvotes for different songs are registered.
     */
    @Test
    public void multipleUpvoteDiffSongsRegistered() throws Exception {
        // Vote for two songs from the same user
        addSong();
        addSong();
        for (Integer id : getIds(2)) {
            Vote vote = new Vote(USER_ID_TOKEN, id, true);
            mvc.perform(post("/vote")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(vote)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        // Check votes are registered
        mvc.perform(get("/getSongs/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].votes", is(1)))
                .andExpect(jsonPath("$[1].votes", is(1)));
    }

    /**
     * Test second upvote by same user prevented.
     */
    @Test
    public void duplicateUpvotePrevented() throws Exception {
        addSong();
        int id = getIds(1).get(0);

        // Perform two votes on the song from the same users
        for (int i = 1; i <= 2; i++) {
            Vote vote = new Vote(USER_ID_TOKEN, id, true);
            mvc.perform(post("/vote")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(vote)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        // Check only one vote is registered
        mvc.perform(get("/getSongs/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].votes", is(1)));
    }

    /**
     * Test downvote registered.
     */
    @Test
    public void downvoteRegistered() throws Exception {
        addSong();
        int id = getIds(1).get(0);

        // Perform a downvote on the song (first have to upvote)
        Vote vote = new Vote(USER_ID_TOKEN, id, true);
        mvc.perform(post("/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vote)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        vote = new Vote(USER_ID_TOKEN, id, false);
        mvc.perform(post("/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vote)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Check votes are registered
        mvc.perform(get("/getSongs/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].votes", is(0)));

        // Check voted songs is empty
        mvc.perform(get("/getSongsVotedByUser/" + USER_ID_TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", empty()));
    }

    /**
     * Test multiple downvotes registered.
     */
    @Test
    public void multipleDownvotesRegistered() throws Exception {
        addSong();
        int id = getIds(1).get(0);

        // Perform a downvote on the song (first have to upvote)
        for (int i = 1; i <= 2; i++) {
            Vote vote = new Vote(USER_ID_TOKEN, id, true);
            mvc.perform(post("/vote")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(vote)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
        for (int i = 1; i <= 2; i++) {
            Vote vote = new Vote(USER_ID_TOKEN, id, false);
            mvc.perform(post("/vote")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(vote)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        // Check votes are registered
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
        Vote vote = new Vote(USER_ID_TOKEN, id, true);
        mvc.perform(post("/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vote)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Test vote returns Unauthorized on failed authentication.
     */
    @Test
    public void voteReturnsUnauthorized() throws Exception {
        int id = 1;
        Vote vote = new Vote("BadIdToken", id, true);
        mvc.perform(post("/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vote)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    /**
     * Test deleteSong works on one song.
     */
    @Test
    public void testDeleteSongOneSong() throws Exception {
        addSong();
        int id = getIds(1).get(0);

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
        int id = getIds(1).get(0);
        addSong();

        // Delete one song
        mvc.perform(delete("/deleteSong/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Check id is different
        int newId = getIds(1).get(0);
        Assertions.assertNotEquals(newId, id);

        // Delete new song
        mvc.perform(delete("/deleteSong/" + newId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Check last song is different from deleted ones
        int lastId = getIds(1).get(0);
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

    // Returns ids of 'count' number of songs in the database
    private List<Integer> getIds(int count) throws Exception {
        String json = mvc.perform(get("/getSongs/" + count))
                .andReturn().getResponse().getContentAsString();
        List<Integer> ids = new ArrayList<>();
        List<Song> songs = objectMapper.readValue(json, new TypeReference<>(){});
        for (Song song : songs) {
            ids.add(song.getId());
        }
        return ids;
    }
}

