package VNSH.Controllers;

import VNSH.Classes.*;
import VNSH.Classes.Error;
import VNSH.Repository.SongsRepositoryimpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class ArtistsController{
    SongsRepositoryimpl repository = new SongsRepositoryimpl();

    @GetMapping("/artists")
    ResponseEntity<?> getArtists()throws SQLException, ClassNotFoundException{
        // Возвращает всех артистов
        return ResponseEntity
                .status(200)
                .body(repository.getArtists());
    }

    @GetMapping("/artists/{id}")
    ResponseEntity<?> getArtistById(@PathVariable int id) throws SQLException, ClassNotFoundException{
        //404
        // Метод getArtistById возвращает null, когда не найден артист по id
        Artist ArtistById = repository.getArtistById(id);
        if(ArtistById == null){
            Error er = new Error("Artist not found");
            return ResponseEntity
                    .status(404)
                    .body(er);
        }

        //200
        return ResponseEntity
                .status(200)
                .body(ArtistById);
    }

    @GetMapping("/artists/{id}/songs")
    ResponseEntity<?> getArtistSongsById(@PathVariable int id) throws SQLException, ClassNotFoundException{
        //404
        // Метод getArtistById возвращает null, когда не найден артист по id
        ArrayList<ArtistSongs> ArtistSongsById = repository.getArtistSongsById(id);
        if(ArtistSongsById == null){
            Error er = new Error("Artist not found");
            return ResponseEntity
                    .status(404)
                    .body(er);
        }

        //200
        return ResponseEntity
                .status(200)
                .body(ArtistSongsById);
    }

}
