
package com.beatparty.beatpartyapp;

import com.beatparty.beatpartyapp.controller.SongController;
import com.beatparty.beatpartyapp.dao.SongDao;
import com.beatparty.beatpartyapp.entity.Song;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

/**
 * Unit testing for SongController.java.
 *
 * <p> Note: These unit tests are meant to run independently from the database and are
 * thus rather simplistic. Testing the actual database mutations can only be done with
 * integration testing.</p>
 */
public class SongControllerUnitTest {

    public static final List<Song> songList = new ArrayList();

    @InjectMocks
    private SongController controller;

    @Mock
    private SongDao dao;

    @Mock
    private Song song;

    @Before
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Checks whether application context is non-null upon application start.
     */  
    @Test
    public void contextLoads() {
        Assertions.assertNotNull(controller);
    }

    /**
     * Verify call to getSongs API is relayed to the DAO appropriately.
     */
    @Test
    public void testGetSongsBasic() {
        int count = 0;
        Mockito.when(dao.getSongs(count)).thenReturn(songList);
        List<Song> actual = controller.getSongs(count);
        Assertions.assertEquals(actual, songList);
    }

    /**
     * Verify getSongs throws ResponseStatusException when given a negative count.
     */
    @Test(expected = ResponseStatusException.class)
    public void testGetSongsThrowsResponseStatusException() {
        controller.getSongs(-1);
    }


    /**
     * Verify call to getShuffledSongs API call is relayed to DAO appropriately.
    */
    @Test
    public void testGetShuffledSongsBasic() {
        int count = 1;
        Mockito.when(dao.getShuffledSongs(1)).thenReturn(songList);
        List<Song> actual = controller.getShuffledSongs(count);
        Assertions.assertEquals(actual, songList);
    }

    /**
     * Verify getSongs throws ResponseStatusException when given a negative count.
     */
    @Test(expected = ResponseStatusException.class)
    public void testGetShuffledSongsThrowsResponseStatusException() {
        controller.getShuffledSongs(-1);
    }

    /**
     * Verify call to upload a new song is relayed to the DAO appropriately.
    */
    @Test
    public void testUploadSongBasic() {
        controller.uploadSong(song);
        Mockito.verify(dao, Mockito.times(1)).save(song);
    }

    /**
     * Verify upvote is reflected in specified song.
     */
    @Test
    public void testUpvote() {
        int id = 1;
        Mockito.when(dao.getOne(id)).thenReturn(song);
        controller.vote(id, true);
        Mockito.verify(song, Mockito.times(1)).upvote();
        Mockito.verify(dao, Mockito.times(1)).saveAndFlush(song);
    }

    /**
     * Verify downvote is reflected in specified song.
     */
    @Test
    public void testDownnote() {
        int id = 1;
        Mockito.when(dao.getOne(id)).thenReturn(song);
        controller.vote(id, false);
        Mockito.verify(song, Mockito.times(1)).downvote();
        Mockito.verify(dao, Mockito.times(1)).saveAndFlush(song);
    }

    /**
     * Verify vote API throws ReponseStatusException when invalid id is given.
     */
    @Test(expected = ResponseStatusException.class)
    public void testVoteThrowsResponseStatusException() {
        int id = 0;
        controller.vote(id, true);
    }

    /**
     * Verify DELETE call to delete a specific song is relayed appropriately to the DAO.
    */
    @Test
    public void testDeleteSongBasic() {
        int id = 1;
        controller.deleteSong(id);
        Mockito.verify(dao, Mockito.times(1)).deleteById(id);
    }

    /**
     * Verify deleteSong API throws ResponseStatusException when invalid ID given.
     */
    @Test(expected = ResponseStatusException.class)
    public void testDeleteSongThrowsResponseStatusException() {
        int id = -1;
        controller.deleteSong(id);
    }

    /**
     * A DELETE call to delete all songs from the application.
    */
    @Test
    public void testDeleteAllSongsBasic() {
        controller.deleteAllSongs();
        Mockito.verify(dao, Mockito.times(1)).deleteAllInBatch();
    }
}
