/*
 * Name: SongControllerUnitTest
 * Author: Vikram Guhan Subbiah
 * Date: Wednesday, 28th February, 2021
 */

package com.beatparty.beatpartyapp;

import com.beatparty.beatpartyapp.controller.SongController;
import com.beatparty.beatpartyapp.Song;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

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
	int numSongs = random.nextInt(100);
        String response = restTemplate.getForObject("http://localhost:" + port + "/getSongs/" + numSongs, String.class);
        List<Song> songs = objectMapper.readValue(response, new TypeReference<List<Song>>(){ });
        Assert.assertEquals(songs.size(), numSongs);
    }
}
