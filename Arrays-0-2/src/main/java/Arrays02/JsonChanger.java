package Arrays02;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class JsonChanger {
    public Error name1() {
        // Запись текущего времени
        Date md1 = new Date();
        int time1 = (int) md1.getTime();

        ObjectMapper mapper = new ObjectMapper();

        try {
            Helicopter helicopter;
            // Запись из файла в объект
            helicopter = mapper.readValue(new File("/Files/File.json"), Helicopter.class);
            // Запись из объекта в массив
            int[] arr = helicopter.getValues();
            // Сортировка значений в массиве
            if (helicopter.getAlgorithm().equals("bubble")) {
                BubbleSorter array = new BubbleSorter();
                arr = array.bubbleSorter(arr);
            }
            if (helicopter.getAlgorithm().equals("insertion")) {
                InsertionSorter array = new InsertionSorter();
                arr = array.insertionSorter(arr);
            }
            // Установка прошедшего времени
            Date md2 = new Date();
            int time2 = (int) md2.getTime();
            int time = time2 - time1;
            Plane plane = new Plane();
            plane.setTime(time);
            plane.setArray(arr);
            // Вывод прошедшего времени и отсортированного массива
            String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(plane);
            System.out.println(result);

        } catch (NullPointerException e) {
            Error error = new Error();
            error.setErrorMessage("Array is null");
            return error;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}