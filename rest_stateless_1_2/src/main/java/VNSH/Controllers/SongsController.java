package VNSH.Controllers;

import VNSH.Classes.ArtistSongs;
import VNSH.Classes.Error;
import VNSH.Classes.Song;
import VNSH.Repository.SongsRepositoryimpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class SongsController {
    SongsRepositoryimpl repository = new SongsRepositoryimpl();

    @GetMapping("/songs")
    ResponseEntity<?> getSongs() throws SQLException, ClassNotFoundException{
        // Возвращает все песни
        return ResponseEntity
                .status(200)
                .body(repository.findAll());
    }

    @PostMapping("/songs")
    ResponseEntity<?> createSong(@RequestBody Song newSong)throws SQLException, ClassNotFoundException {
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
    ResponseEntity<?> getSongById(@PathVariable int id) throws SQLException, ClassNotFoundException{
        //404
        // Метод getById возвращает null, когда не найдена песня по id
        ArtistSongs songById = repository.getById(id);
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
    ResponseEntity<?> updateSongById(@RequestBody Song song, @PathVariable int id) throws SQLException, ClassNotFoundException{
        //400
        Error er = new Error();
        ResponseEntity<?> error = er.Error400(song);
        // Когда в методе вернулся не null, значит была найдена ошибка
        if(error != null){
            return error;
        }

        //404
        ArtistSongs songById = repository.getById(id);
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
    ResponseEntity<?> deleteSongById(@PathVariable int id) throws SQLException, ClassNotFoundException {
        //404
        // Метод getById возвращает null, когда не найдена песня по id
        ArtistSongs songById = repository.getById(id);
        if(songById == null){
            Error er = new Error("Song not found");
            return ResponseEntity
                    .status(404)
                    .body(er);
        }

        //200
        ArtistSongs result = repository.deleteById(id);
        return ResponseEntity
                .status(200)
                .body(result);
    }
}
