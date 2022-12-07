package VNSH.Repository;

import VNSH.Classes.Song;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class SongsRepositoryimpl implements SongsRepository {
    private final Map<Integer, Song> data = new ConcurrentHashMap<>();
    private final AtomicInteger autoId = new AtomicInteger(0);

    @Override
    public List<Song> findAll() {
        // Возвращает массив песен
        return new ArrayList<>(data.values());
    }

    @Override
    public Song getById(Integer id) {
        // Находит песню по id
        Song song = data.get(id);
        // Если не находит песню по id, то возвращает null
        return song;
    }

    @Override
    public Song save(Song song) {
        // Присваивает песне новый id
        int id = autoId.incrementAndGet();
        song.setId(id);
        // Записывает в данные песню по id
        data.put(id, song);
        return song;
    }

    @Override
    public Song updateById(Integer id, Song song) {
        // Присваивает поступившей песне поступивший id
        song.setId(id);
        // Записывает в данные песню по id
        data.put(id, song);
        return song;
    }

    @Override
    public Song deleteById(Integer id) {
        Song song = data.get(id);
        data.remove(id);
        return song;
    }

    @Override
    public Song listenSongById(Integer id, Integer count){
        // Находит песню по id
        Song song = data.get(id);
        // Прибавляет прослушивания песне
        song.setAuditions(song.getAuditions() + count);
        return song;
    }
}
