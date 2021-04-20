import java.util.*;

public class Song {

    private final String name;
    private final String artistName;
    private int votes;
    private final Date uploadDate;
    private final String songLink;

    public Song(String name,String artistName, String songLink) {
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
