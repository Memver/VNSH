package Arrays02;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class JsonChanger {
    public ChangedJson jsonChanger() {
        // Запись текущего времени
        Date md1 = new Date();
        int time1 = (int) md1.getTime();

        ObjectMapper mapper = new ObjectMapper();

        try {
            InputJson inputJson;
            // Запись из файла в объект
            inputJson = mapper.readValue(new File("/Files/File.json"), InputJson.class);
            // Запись из объекта в массив
            int[] arr = inputJson.getValues();
            // Сортировка значений в массиве
            if (inputJson.getAlgorithm().equals("bubble")) {
                BubbleSorter array = new BubbleSorter();
                arr = array.bubbleSorter(arr);
            }
            else if (inputJson.getAlgorithm().equals("insertion")) {
                InsertionSorter array = new InsertionSorter();
                arr = array.insertionSorter(arr);
            }
            // Установка прошедшего времени
            Date md2 = new Date();
            int time2 = (int) md2.getTime();
            int time = time2 - time1;
            Json json = new Json();
            json.setTime(time);
            json.setArray(arr);

            // Вывод прошедшего времени и отсортированного массива
            return json;

        } catch (NullPointerException e) {
            Error error = new Error();
            error.setErrorMessage("Array is null");
            return error;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}