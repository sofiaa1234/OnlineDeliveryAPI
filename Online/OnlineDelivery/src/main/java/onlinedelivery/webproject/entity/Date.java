package onlinedelivery.webproject.entity;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class Date {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String randomDate = createRandomDate(2022, 2023);
            System.out.println(randomDate);
        }
    }

    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static String createRandomDate(int startYear, int endYear) {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(startYear, endYear);
        return "DATE OF ORDER IS : " + LocalDate.of(year, month, day);
    }
}