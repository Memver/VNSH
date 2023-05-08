package VNSH.Controllers;

import VNSH.Classes.ArtistSongs;
import VNSH.Classes.Error;
import VNSH.Classes.InputJson;
import VNSH.Classes.Song;
import VNSH.Repository.SongsRepositoryimpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class ListenController {
    SongsRepositoryimpl repository = new SongsRepositoryimpl();

    @GetMapping("/songs/listen")
    ResponseEntity<?> getSortedSongsByAuditions(@RequestParam(defaultValue = "5") int limit)throws SQLException, ClassNotFoundException{
        // 400
        // limit не должен быть <= 0
        if(limit <= 0){
            return ResponseEntity
                    .status(400)
                    .body(new Error("Invalid input parameters"));
        }

        // 200
        return ResponseEntity
                .status(200)
                .body(repository.getSortedSongsByAuditions(limit));
    }

    @PostMapping("/songs/listen")
    ResponseEntity<?> ListenSongsByIds(@RequestBody InputJson json)throws SQLException, ClassNotFoundException{
        //400
        if(json.getAuditions() < 1 || json.getSongs() == null){
            return ResponseEntity
                    .status(400)
                    .body(new Error("Invalid input parameters"));
        }

        //404
        // Проверка на наличие песен
        int[] array = json.getSongs();
        for (int i = 0; i < array.length; i++) {
            int id = array[i];
            ArtistSongs songById = repository.getById(id);
            if(songById == null){
                Error err = new Error("Song not found");
                return ResponseEntity
                        .status(404)
                        .body(err);
            }
        }

        //200
        int countOfListen = json.getAuditions();
        ArtistSongs[] songs = new ArtistSongs[array.length];

        // Прослушивание
        for (int i = 0; i < array.length; i++) {
            int id = array[i];
            songs[i] = repository.listenSongById(id, countOfListen);
        }
        return ResponseEntity
                .status(200)
                .body(songs);
    }

    @PostMapping("/songs/{id}/listen")
    ResponseEntity<?> ListenSongById(@PathVariable int id, @RequestBody Song song)throws SQLException, ClassNotFoundException{
        //400
        if(song.getAuditions() < 1){
            return ResponseEntity
                    .status(400)
                    .body(new Error("Invalid input parameters"));
        }

        //404
        ArtistSongs songById = repository.getById(id);
        if(songById == null){
            Error err = new Error("Song not found");
            return ResponseEntity
                    .status(404)
                    .body(err);
        }

        //200
        return ResponseEntity
                .status(200)
                .body(repository.listenSongById(id, song.getAuditions()));
        // Прослушивание песни по id
    }
}
