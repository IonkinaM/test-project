import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class RawData {
    public static void main(String[] args) {
        String dateString = "2023-10-01T12:30:00.000+03:00"; // Пример строки даты
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        // Устанавливаем временную зону, если необходимо
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date date = dateFormat.parse(dateString);
            System.out.println("Преобразованная дата: " + date);
        } catch (ParseException e) {
            System.out.println("Ошибка при парсинге даты: " + e.getMessage());
        }
    }
}
