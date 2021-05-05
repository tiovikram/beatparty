/*
 * Title: SongControllerUnitTest
 * Author: Vikram Guhan Subbiah
 * Date: Wednesday, 28th April, 2021
 */

package com.beatparty.beatpartyapp;

import com.beatparty.beatpartyapp.controller.SongController;
import com.beatparty.beatpartyapp.dao.SongDao;
import com.beatparty.beatpartyapp.entity.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit testing for SongController.java
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SongControllerUnitTest {

    public static final List<Song> songList = new ArrayList();
    public static final Random random = new Random();
    public static final ObjectMapper objectMapper = new ObjectMapper();
    
    @InjectMocks
    private SongController controller;

    @MockBean
    private SongDao dao;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeClass
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

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
        songList.add(new Song());
        Mockito.when(dao.getSongs(1)).thenReturn(songList);
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/getSongs/" + "1", String.class);

        if (response.getStatusCodeValue() == 200) {
            List<Song> songs = objectMapper.readValue(response.getBody(),
                                                      new TypeReference<List<Song>>(){});
            Assert.assertEquals(songList, songs);
        } else if (response.getStatusCodeValue() != 400) {
            Assert.assertFalse(true);
        }

    }

    /**
     * A POST call to upload a song to the backend.
     */
    @Test
    public void testUploadSong() {
        Song testSong = new Song();
        ResponseEntity<String> response = (ResponseEntity<String>) restTemplate.postForEntity("http://localhost:" + port + "/uploadNewSong/",
                                           testSong, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Upload successful", response.getBody());
        Mockito.verify(dao, Mockito.times(1)).save(testSong);

    }

    /**
     * A DELETE call to delete all songs from the application.
     */
    @Test
    public void testDeleteAllSongs() throws JsonProcessingException {
        restTemplate.delete("http://localhost:" + port + "/deleteAllSongs");

        Mockito.verify(dao, Mockito.times(1)).deleteAllInBatch();

    } 
}
