import Arrays02.ChangedJson;
import Arrays02.JsonChanger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        JsonChanger changer = new JsonChanger();
        ChangedJson json = changer.jsonChanger();

        // Проверка на возврат ошибки
        if (json != null) {
            try {
                String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
                System.out.println(result);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
