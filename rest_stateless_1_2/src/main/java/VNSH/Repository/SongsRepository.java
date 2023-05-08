package VNSH.Repository;

import VNSH.Classes.Artist;
import VNSH.Classes.ArtistSongs;
import VNSH.Classes.Song;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SongsRepository {
    ArrayList<ArtistSongs> findAll()throws SQLException, ClassNotFoundException;

    ArtistSongs getById(Integer id)throws SQLException, ClassNotFoundException;

    ArtistSongs save(Song song)throws SQLException, ClassNotFoundException;

    ArtistSongs updateById(Integer id, Song song)throws SQLException, ClassNotFoundException;

    ArtistSongs deleteById(Integer id)throws SQLException, ClassNotFoundException;

    ArtistSongs listenSongById(Integer id, Integer count)throws SQLException, ClassNotFoundException;

    ArrayList<ArtistSongs> getSortedSongsByAuditions(int limit)throws SQLException, ClassNotFoundException;

    ArrayList<Artist> getArtists() throws SQLException, ClassNotFoundException;

    Artist getArtistById(Integer id)throws SQLException, ClassNotFoundException;

    ArrayList<ArtistSongs> getArtistSongsById(Integer id)throws SQLException, ClassNotFoundException;
}
