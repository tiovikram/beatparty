import java.util.Date;

/**
 * This class serves a model of abstraction for the data needed for the BeatParty platform.
 * Each instance of the Song class acts as a song uploaded to the platform. Each Song contains
 * information on the song's name, the artist's name, how many votes the song has, the date and
 * time the song was uploaded, and a link to the song a third-party streaming service
 * (Spotify, Soundcloud, etc.).
 */
public class Song {

    private final String name;
    private final String artistName;
    private int votes;
    private final Date uploadDate;
    private final String songLink;

    /**
     * Creates a song with the given name by the given artist. A link to the song is also required
     *
     * @param name the name of the song
     * @param artistName the name of the artist
     * @param songLink a link to the song on a third-party streaming service
     */
    public Song(String name, String artistName, String songLink) {
        this.name = name;
        this.artistName = artistName;
        this.votes = 0;
        this.uploadDate = new Date();
        this.songLink = songLink;
    }

    public String getSongName() {
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

    // Returns upload date in the format "mon dd yyyy hh:mm:ss"
    public String getUploadDate() {
        String[] splitDate = uploadDate.toString().split(" ");
        return splitDate[1] + " " + splitDate[2] + " " + splitDate[5] + " " + splitDate[3];
    }

    public void upvote() {
        votes++;
    }

    public void downvote() {
        votes--;
    }

    /*

    Could be used in the future if we want to sort by artist

    private class Artist {
        private String firstName;
        private String lastName;
        private Set<Song> songs;

        private Artist(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.songs = new HashSet<>();
        }


    } */
}
