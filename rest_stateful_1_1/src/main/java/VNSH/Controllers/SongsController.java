package VNSH.Controllers;

import VNSH.Classes.Error;
import VNSH.Classes.InputJson;
import VNSH.Classes.Song;
import VNSH.Repository.SongsRepositoryimpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
public class SongsController {
    SongsRepositoryimpl repository = new SongsRepositoryimpl();

    @GetMapping("/songs")
    List<Song> getSongs() {
        // Возвращает все песни
        return repository.findAll();
    }

    @PostMapping("/songs")
    ResponseEntity<?> createSong(@RequestBody Song newSong) {
        //400
        Error er = new Error();
        ResponseEntity<?> error = er.Error400(newSong);
        // Когда в методе вернулся не null, значит была найдена ошибка
        if(error != null){
            return error;
        }

        //200
        return ResponseEntity
                .status(200)
                .body(repository.save(newSong));
                // Сохраняет песню
    }


    @GetMapping("/songs/{id}")
    ResponseEntity<?> getSongById(@PathVariable int id) {
        //404
        Song songById = repository.getById(id);
        // Метод getById возвращает null, когда по id не найдена песня
        if(songById == null){
            Error er = new Error("Song not found");
            return ResponseEntity
                    .status(404)
                    .body(er);
        }

        //200
        return ResponseEntity
                .status(200)
                .body(songById);
    }

    @PutMapping("/songs/{id}")
    ResponseEntity<?> updateSongById(@RequestBody Song song, @PathVariable int id) {

        //400
        Error er = new Error();
        ResponseEntity<?> error = er.Error400(song);
        // Когда в методе вернулся не null, значит была найдена ошибка
        if(error != null){
            return error;
        }
        //404
        Song songById = repository.getById(id);
        // Метод getById возвращает null, когда по id не найдена песня
        if(songById == null){
            Error err = new Error("Song not found");
            return ResponseEntity
                    .status(404)
                    .body(err);
        }

        //200
        return ResponseEntity
                .status(200)
                .body(repository.updateById(id, song));
    }

    @DeleteMapping("/songs/{id}")
    ResponseEntity<?> deleteSongById(@PathVariable int id) {
        //404
        Song result = repository.deleteById(id);
        // Метод getById возвращает null, когда по id не найдена песня
        if(result == null){
            Error err = new Error("Song not found");
            return ResponseEntity
                    .status(404)
                    .body(err);
        }

        //200
        return ResponseEntity
                .status(200)
                .body(result);
    }

    @GetMapping("/songs/listen")
    ResponseEntity<?> getSortedSongsByAuditions(@RequestParam(defaultValue = "5") int limit){
        // Запись песен в List
        List<Song> songs = repository.findAll();
        // Когда просят показать песен больше, чем есть, тогда показывает все песни
        if(limit > songs.size()){
            limit = songs.size();
        }
        // Преобразование List в Array
        Song[] songs2 = songs.toArray(Song[]::new);
        // Обратная сортировка массива по прослушиваниям
        Arrays.sort(songs2, Comparator.comparing(Song::getAuditions).reversed());
        // Оставляем в массиве количество песен, указанное в limit
        Song[] songs3 = new Song[limit];
        for (int i = 0; i < limit; i++) {
            songs3[i] = songs2[i];
        }
        return ResponseEntity
                .status(200)
                .body(songs3);
    }

    @PostMapping("/songs/listen")
    ResponseEntity<?> ListenSongsByIds(@RequestBody InputJson json){
        //400
        if(json.getAuditions() < 1 || json.getSongs() == null){
            return ResponseEntity
                    .status(400)
                    .body(new Error("Invalid input parameters"));
        }

        //404
        int[] array = json.getSongs();
        // Проверяем каждый айди на то, что песня по этому айди существует
        for (int i = 0; i < array.length; i++) {
            int id = array[i];
            Song songById = repository.getById(id);
            if(songById == null){
                Error err = new Error("Song not found");
                return ResponseEntity
                        .status(404)
                        .body(err);
            }
        }

        //200
        int countOfListen = json.getAuditions();
        Song[] songs = new Song[array.length];

        // Прослушивание каждой песни
        for (int i = 0; i < array.length; i++) {
            int id = array[i];
            songs[i] = repository.listenSongById(id, countOfListen);
        }
        return ResponseEntity
                .status(200)
                .body(songs);
    }

    @PostMapping("/songs/{id}/listen")
    ResponseEntity<?> ListenSongById(@PathVariable int id, @RequestBody Song song){
        //400
        if(song.getAuditions() < 1){
            return ResponseEntity
                    .status(400)
                    .body(new Error("Invalid input parameters"));
        }

        //404
        Song songById = repository.getById(id);
        if(songById == null){
            Error err = new Error("Song not found");
            return ResponseEntity
                    .status(404)
                    .body(err);
        }

        //200
        int countOfListen = song.getAuditions();
        return ResponseEntity
                .status(200)
                .body(repository.listenSongById(id, countOfListen));
                // Прослушивание песни по id

    }



}
