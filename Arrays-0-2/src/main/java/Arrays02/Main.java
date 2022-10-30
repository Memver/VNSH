package Arrays02;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        try{
            Helicopter helicopter = mapper.readValue(new File("/Files/File.json"), Helicopter.class);

            int[] arr = helicopter.getValues();












        } catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}