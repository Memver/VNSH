package VNSH.Repository;

import VNSH.Classes.Song;

import java.util.List;

public interface SongsRepository {
    List<Song> findAll();

    Song getById(Integer id);

    Song save(Song song);

    Song updateById(Integer id, Song song);

    Song deleteById(Integer id);

    Song listenSongById(Integer id, Integer count);
}
