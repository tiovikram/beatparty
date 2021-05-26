
package com.beatparty.beatpartyapp.controller;

import com.beatparty.beatpartyapp.dao.SongDao;
import com.beatparty.beatpartyapp.dao.UserVotesDao;
import com.beatparty.beatpartyapp.entity.GoogleUser;
import com.beatparty.beatpartyapp.entity.Song;
import com.beatparty.beatpartyapp.entity.UserVote;
import com.beatparty.beatpartyapp.entity.UserVoteId;
import com.beatparty.beatpartyapp.entity.Vote;
import com.beatparty.beatpartyapp.util.GoogleUserHelper;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

/**
 * Unit testing for SongController.java.
 *
 * <p> Note: These unit tests are meant to run independently from the database and are
 * thus rather simplistic. Testing the actual database mutations can only be done with
 * integration testing.</p>
 */
@SpringBootTest
public class SongControllerUnitTest {

    public static final List<Song> songList = new ArrayList();
    public static final String USER_ID = "UserId";
    public static final String USER_ID_TOKEN = "UserIdToken";

    @InjectMocks
    private SongController controller;

    @Mock
    private SongDao songDao;

    @Mock
    private UserVotesDao userVotesDao;

    @Mock
    private GoogleUserHelper googleUserHelper;

    @Mock
    private GoogleUser googleUser;

    @Mock
    private Song song;

    @Before
    public void initMocks() throws GeneralSecurityException, IOException {
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
     * Verify call to getSongs API is relayed to the songDao appropriately.
     */
    @Test
    public void testGetSongsBasic() {
        int count = 0;
        Mockito.when(songDao.getSongs(count)).thenReturn(songList);
        List<Song> actual = controller.getSongs(count);
        Assertions.assertEquals(actual, songList);
    }

    /**
     * Verify getSongs throws ResponseStatusException when given a negative count.
     */
    @Test
    public void testGetSongsThrowsResponseStatusException() {
        Assertions.assertThrows(ResponseStatusException.class, () -> controller.getSongs(-1));
    }


    /**
     * Verify call to getShuffledSongs API call is relayed to songDao appropriately.
    */
    @Test
    public void testGetShuffledSongsBasic() {
        int count = 1;
        Mockito.when(songDao.getShuffledSongs(1)).thenReturn(songList);
        List<Song> actual = controller.getShuffledSongs(count);
        Assertions.assertEquals(actual, songList);
    }

    /**
     * Verify getSongs throws ResponseStatusException when given a negative count.
     */
    @Test
    public void testGetShuffledSongsThrowsResponseStatusException() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> controller.getShuffledSongs(-1));
    }

    /**
     * Verify call to upload a new song is relayed to the songDao appropriately.
    */
    @Test
    public void testUploadSongBasic() {
        controller.uploadSong(song);
        Mockito.verify(songDao, Mockito.times(1)).save(song);
    }

    /**
     * Verify upvote is reflected in specified song.
     */
    @Test
    public void testUpvote() throws GeneralSecurityException, IOException {
        int songId = 1;
        UserVoteId userVoteId = new UserVoteId(USER_ID, songId);

        Mockito.when(googleUserHelper.getGoogleUser(USER_ID_TOKEN)).thenReturn(googleUser);
        Mockito.when(googleUser.getId()).thenReturn(USER_ID);
        Mockito.when(songDao.getOne(songId)).thenReturn(song);
        Mockito.when(userVotesDao.existsById(userVoteId)).thenReturn(false);

        Vote vote = new Vote(USER_ID_TOKEN, songId, true);
        controller.vote(vote);
        Mockito.verify(song, Mockito.times(1)).upvote();
        UserVote userVote = new UserVote(USER_ID, songId);
        Mockito.verify(userVotesDao, Mockito.times(1)).save(userVote);
        Mockito.verify(songDao, Mockito.times(1)).saveAndFlush(song);
    }

    /**
     * Test upvote when already voted.
     */
    @Test
    public void testUpvoteAlreadyVoted() throws GeneralSecurityException, IOException {
        int songId = 1;

        Mockito.when(googleUserHelper.getGoogleUser(USER_ID_TOKEN)).thenReturn(googleUser);
        Mockito.when(googleUser.getId()).thenReturn(USER_ID);
        Mockito.when(songDao.getOne(songId)).thenReturn(song);
        UserVoteId userVoteId = new UserVoteId(USER_ID, songId);
        Mockito.when(userVotesDao.existsById(userVoteId)).thenReturn(true);

        Vote vote = new Vote(USER_ID_TOKEN, songId, true);
        controller.vote(vote);
        Mockito.verify(song, Mockito.times(0)).upvote();
        UserVote userVote = new UserVote(USER_ID, songId);
        Mockito.verify(userVotesDao, Mockito.times(0)).save(userVote);
    }

    /**
     * Verify downvote is reflected in specified song.
     */
    @Test
    public void testDownvote() throws GeneralSecurityException, IOException {
        int songId = 1;
        UserVoteId userVoteId = new UserVoteId(USER_ID, songId);

        Mockito.when(googleUserHelper.getGoogleUser(USER_ID_TOKEN)).thenReturn(googleUser);
        Mockito.when(googleUser.getId()).thenReturn(USER_ID);
        Mockito.when(songDao.getOne(songId)).thenReturn(song);
        Mockito.when(userVotesDao.existsById(userVoteId)).thenReturn(true);

        Vote vote = new Vote(USER_ID_TOKEN, songId, false);
        controller.vote(vote);
        Mockito.verify(song, Mockito.times(1)).downvote();
        Mockito.verify(userVotesDao, Mockito.times(1)).deleteById(userVoteId);
        Mockito.verify(songDao, Mockito.times(1)).saveAndFlush(song);
    }

    /**
     * Test downvote when not already upvoted.
     */
    @Test
    public void testDownvoteHaventUpvoted() throws GeneralSecurityException, IOException {
        int songId = 1;
        UserVoteId userVoteId = new UserVoteId(USER_ID, songId);

        Mockito.when(googleUserHelper.getGoogleUser(USER_ID_TOKEN)).thenReturn(googleUser);
        Mockito.when(googleUser.getId()).thenReturn(USER_ID);
        Mockito.when(songDao.getOne(songId)).thenReturn(song);
        Mockito.when(userVotesDao.existsById(userVoteId)).thenReturn(false);

        Vote vote = new Vote(USER_ID_TOKEN, songId, false);
        controller.vote(vote);
        Mockito.verify(song, Mockito.times(0)).downvote();
    }

    /**
     * Verify vote API throws ResponseStatusException when invalid id is given.
     */
    @Test
    public void testVoteThrowsResponseStatusExceptionWhenInvalidId() {

        int id = 1;
        Vote vote = new Vote(USER_ID_TOKEN, id, true);
        Assertions.assertThrows(ResponseStatusException.class, () -> controller.vote(vote));
    }

    /**
     * Verify vote API throws ResponseStatusException when authentication fails.
     */
    @Test
    public void testVoteThrowsResponseStatusExceptionWhenAuthenticationFails()
            throws GeneralSecurityException, IOException {
        Mockito.when(googleUserHelper.getGoogleUser(USER_ID_TOKEN))
                .thenThrow(GeneralSecurityException.class);
        int id = 1;
        Vote vote = new Vote(USER_ID_TOKEN, id, true);
        Assertions.assertThrows(ResponseStatusException.class, () -> controller.vote(vote));
    }

    /**
     * Verify DELETE call to delete a specific song is relayed appropriately to the songDao.
    */
    @Test
    public void testDeleteSongBasic() {
        int id = 1;
        controller.deleteSong(id);
        Mockito.verify(songDao, Mockito.times(1)).deleteById(id);
    }

    /**
     * Verify deleteSong API throws ResponseStatusException when invalid ID given.
     */
    @Test
    public void testDeleteSongThrowsResponseStatusException() {
        int id = -1;
        Assertions.assertThrows(ResponseStatusException.class, () -> controller.deleteSong(id));
    }

    /**
     * A DELETE call to delete all songs from the application.
    */
    @Test
    public void testDeleteAllSongsBasic() {
        controller.deleteAllSongs();
        Mockito.verify(songDao, Mockito.times(1)).deleteAllInBatch();
    }
}
