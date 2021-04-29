/*
 * Title: SongControllerUnitTest
 * Author: Vikram Guhan Subbiah
 * Date: Wednesday, 28th April, 2021
 */

package com.beatparty.beatpartyapp;

import com.beatparty.beatpartyapp.controller.SongController;
import com.beatparty.beatpartyapp.Song;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

/**
 * Unit testing for SongController.java
 */
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SongControllerUnitTest {

    public static Random random = new Random();
    public static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SongController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    /**
     * Checks whether application context is non-null upon application start.
     */  
    @Test
    public void contextLoads() {
        Assert.assertNotNull(controller);
    }

    /**
     * GET call to the application and checks if the number of songs returned
     * is the same as the number of songs requested.
     */
    @Test
    public void testGetSongs() throws JsonProcessingException {
        for (int i = 0; i < 10; i++) {
            int numSongs = random.nextInt(5);
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/getSongs/" + numSongs, String.class);
            Set<SongResponse> songs = objectMapper.readValue(response.getBody(), new TypeReference<Set<SongResponse>>(){ });
            Assert.assertEquals(songs.size(), numSongs);
        }
    }

    /**
     *
     */
    @Test
    public void testUploadSong() {
       String testName = "Dancing in the Dark";
       String testArtist = "Bruce Springsteen";
       String testUrl = "https://open.spotify.com/track/7FwBtcecmlpc1sLySPXeGE?si=0db4905b56f3419a";
       ResponseEntity<String> response = (ResponseEntity<String>) restTemplate.getForEntity("http://localhost:" + port + "/uploadNewSong/" +
                                         testName + "/" + testArtist + "/" + testUrl, String.class);
       System.out.println(response.getStatusCode()); 
    }

    /**
     *
     */
    @Test
    public void testDeleteAllSongs() throws JsonProcessingException {
	
    } 
}
