package VNSH.Classes;

public class ArtistSongs {
    private int id;
    private int artistId;
    private String artistName;
    private String name;
    private int auditions;

    public ArtistSongs() {}

    public ArtistSongs(int id, int artistId, String artistName, String name, int auditions) {
        this.id = id;
        this.artistId = artistId;
        this.artistName = artistName;
        this.name = name;
        this.auditions = auditions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuditions() {
        return auditions;
    }

    public void setAuditions(int auditions) {
        this.auditions = auditions;
    }
}
