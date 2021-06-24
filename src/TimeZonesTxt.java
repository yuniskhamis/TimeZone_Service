import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeZonesTxt {
    private static final String[] urlList = {
            "http://worldtimeapi.org/api/timezone/Europe/London.txt",
            "http://worldtimeapi.org/api/timezone/America/New_York.txt",
            "http://worldtimeapi.org/api/timezone/America/Chicago.txt",
            "http://worldtimeapi.org/api/timezone/America/Los_Angeles.txt",
            "http://worldtimeapi.org/api/timezone/Asia/Tokyo.txt",
            "http://worldtimeapi.org/api/timezone/Australia/Sydney.txt"
    };

    public static void main(String[] args) throws IOException {
        for (String url : urlList) {
            URL a = new URL(url);
            URLConnection urlConnection = a.openConnection();
            HttpURLConnection conn = (HttpURLConnection) urlConnection;
            StringBuilder urlString = new StringBuilder();
            String current;
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((current = in.readLine()) != null) {
                    urlString.append(current).append("\n");
                }
                try {
                    PrintWriter pw = new PrintWriter("timezone.txt");
                    pw.println(urlString);
                    pw.close();
                } catch (Exception ex) {        // We’ll cover exceptions soon!
                    System.err.println("Couldn’t write the timezone info.");
                }

            } catch (IOException io) {
                System.err.println("Cannot read from URL");
            }

            try (BufferedReader br = new BufferedReader(new FileReader("timezone.txt"))) {
                String datetime = "";
                for (int i = 0; i < 3; i++) {
                    datetime = br.readLine();
                }
                try (BufferedReader ar = new BufferedReader(new FileReader("timezone.txt"))) {
                    String timezone = "";
                    for (int j = 0; j < 11; j++) {
                        timezone = ar.readLine();
                    }
                    System.out.printf("%20s%50s%n", timezone, fmtDateTime(datetime.substring(10, 42)));
                }
            }
        }
    }

    public static String fmtDateTime(String datetime) {
        datetime = datetime.replaceFirst("[+\\-]\\d\\d:\\d\\d$", "");
        LocalDateTime dt = LocalDateTime.parse(datetime);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm a MM/dd/yyyy");
        return dt.format(fmt);
    }
}
