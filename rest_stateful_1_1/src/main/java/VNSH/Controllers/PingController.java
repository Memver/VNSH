package VNSH.Controllers;

import VNSH.Classes.Ping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    public static final String JSON_VALUE = "application/json";

    @GetMapping("/ping")
    public ResponseEntity<?> healthCheck(){
        Ping pong = new Ping("UP");
        return ResponseEntity
                .status(200)
                .header(HttpHeaders.CONTENT_TYPE, JSON_VALUE)
                .body(pong);
    }
}
