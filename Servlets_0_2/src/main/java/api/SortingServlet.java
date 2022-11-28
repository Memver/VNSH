package api;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class SortingServlet extends HttpServlet {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String algorithm = req.getParameter("algorithm");

        if(req.getParameter("algorithm") == null){
            algorithm = "bubble";
        }

        //400
        if(!req.getContentType().equals("application/json")){
            resp.setStatus(400);
            mapper.writeValue(resp.getWriter(), Map.of("errorMessage", "Array is null"));
            return;
        }
        // Запись из запроса в json объект
        Json inputJson = mapper.readValue(req.getInputStream(), Json.class);

        if (inputJson == null || inputJson.getValues() == null) {
            resp.setStatus(400);
            mapper.writeValue(resp.getWriter(), Map.of("errorMessage", "Array is null"));
            return;
        }

        //404
        if(!algorithm.equals("bubble") & !algorithm.equals("insertion")){
            resp.setStatus(404);
            mapper.writeValue(resp.getWriter(), Map.of("errorMessage", "Array is null"));
            return;
        }

        //200
        resp.setStatus(200);
        Date md1 = new Date();
        int time1 = (int) md1.getTime();

        // Запись из объекта в массив
        int[] arr = inputJson.getValues();

        // Сортировка значений в массиве
        if (algorithm.equals("bubble")) {
            BubbleSorter array = new BubbleSorter();
            arr = array.bubbleSorter(arr);
        }
        else {
            InsertionSorter array = new InsertionSorter();
            arr = array.insertionSorter(arr);
        }
        // Установка прошедшего времени
        Date md2 = new Date();
        int time2 = (int) md2.getTime();
        int time = time2 - time1;

        inputJson.setTime(time);
        inputJson.setValues(arr);

        // Преобразование json объекта в строку
        String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(inputJson);
        mapper.writeValue(resp.getOutputStream(), result);
    }
}
