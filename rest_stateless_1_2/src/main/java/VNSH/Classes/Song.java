package VNSH.Classes;

public class Song{
    private int id;
    private String artistName;
    private String name;
    private int auditions;

    public Song() {}

    public Song(int id, String artistName) {
        this.id = id;
        this.artistName = artistName;
    }

    public Song(String artistName, String name, int auditions) {
        this.artistName = artistName;
        this.name = name;
        this.auditions = auditions;
    }

    public Song(int id, String artistName, String name, int auditions) {
        this.id = id;
        this.artistName = artistName;
        this.name = name;
        this.auditions = auditions;
    }

    public Song(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
