package VNSH.Classes;

public class InputJson {
    private int auditions;
    private int[] songs;

    public InputJson() {
    }


    public InputJson(int auditions, int[] songs) {
        this.auditions = auditions;
        this.songs = songs;
    }

    public int getAuditions() {
        return auditions;
    }

    public void setAuditions(int auditions) {
        this.auditions = auditions;
    }

    public int[] getSongs() {
        return songs;
    }

    public void setSongs(int[] songs) {
        this.songs = songs;
    }

}
