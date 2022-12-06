package VNSH.Classes;

import org.springframework.http.ResponseEntity;

public class Error {
    private String errorMessage;

    public Error() {
    }

    public Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    //400
    public ResponseEntity<?> Error400(Song newSong){
        if(newSong.getArtistName() == null || newSong.getName() == null || newSong.getAuditions() < 0){
            Error er = new Error("Invalid input parameters");
            return ResponseEntity
                    .status(400)
                    .body(er);
        }
        // Удаление пробелов
        String str1 = newSong.getArtistName().replaceAll(" ","");
        String str2 = newSong.getName().replaceAll(" ","");
        // Проверка на пустоту
        if(str1.equals("") || str2.equals("")){
            Error err = new Error("Invalid input parameters");
            return ResponseEntity
                    .status(400)
                    .body(err);
        }
        // Когда всё хорошо возвращает null
        return null;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
