import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        boolean isWork = true;

        while (isWork) {
            Scanner console = new Scanner(System.in);

            String date = Main.enterString("Input date (default: empty): ", console);
            String startDate = "";
            String endDate = "";
            int count = 0;
            boolean thumbs = false;

            if (date.isEmpty()) {
                startDate = Main.enterString("Input start date (default: empty): ", console);

                if (!startDate.isEmpty()) {
                    endDate = Main.enterString("Input end date (default: empty): ", console);
                }
            }

            if (date.isEmpty() && startDate.isEmpty()) {
                count = Main.enterInt("Input count (default: 0): ", console);
            }

            thumbs = Main.enterBool("Select thumbs (y/N): ", console);

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                NasaHttpClient nasaHttpClient = new NasaHttpClient(httpClient);
                nasaHttpClient.downloadImage(date, startDate, endDate, count, thumbs);
            }

            isWork = Main.enterBool("Would you like to repeat? ", console);
        }
    }

    public static String enterString(String message, @NotNull Scanner console) {
        System.out.print(message);
        return console.nextLine();
    }

    public static Integer enterInt(String message, @NotNull Scanner console) {
        int result = 0;

        System.out.print(message);
        String input = console.nextLine();

        if (!input.isEmpty()) {
            result = Integer.parseInt(input);
        }

        return result;
    }

    public static Boolean enterBool(String message, @NotNull Scanner console) {
        System.out.print(message);
        String input = console.nextLine();

        return Objects.equals(input.toLowerCase(), "y");
    }
}
