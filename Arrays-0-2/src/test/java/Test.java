import Arrays02.Error;
import Arrays02.JsonChanger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        JsonChanger namik = new JsonChanger();
        Error error = namik.name1();

        // Проверка на возврат ошибки
        if (error != null) {
            try {
                String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
                System.out.println(result);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
