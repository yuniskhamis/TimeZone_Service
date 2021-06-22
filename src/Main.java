import org.json.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final String[] urlList = {
            "http://worldtimeapi.org/api/timezone/Europe/London",
            "http://worldtimeapi.org/api/timezone/America/New_York",
            "http://worldtimeapi.org/api/timezone/America/Chicago",
            "http://worldtimeapi.org/api/timezone/America/Los_Angeles",
            "http://worldtimeapi.org/api/timezone/Asia/Tokyo",
            "http://worldtimeapi.org/api/timezone/Australia/Sydney"
    };
    public static void main(String[] args) throws IOException {
        System.out.println("=== Current Date/Times Around the World ===");
        for (String url : urlList) {
            extractCurrentTimeInfo(url);
        }
    }

    public static void extractCurrentTimeInfo(String urlstr) throws IOException {
        final String TIMEZONELBL = "timezone: ";
        final String DATETIMELBL = "datetime: ";
            URL url = new URL(urlstr);
            URLConnection urlConnection = url.openConnection();
        HttpURLConnection conn = (HttpURLConnection) urlConnection;

        StringBuilder urlString = new StringBuilder();
        String current;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((current = in.readLine()) != null) {
                urlString.append(current);
            }
        } catch (IOException io) {
            System.err.println("Cannot read from URL");
        }
        String a = urlString.toString();
        JSONObject b = new JSONObject(a);
       System.out.printf("%-2s%-27s%-2s%-40s%n", TIMEZONELBL,b.getString("timezone"),
             DATETIMELBL,fmtDateTime( b.getString("datetime") ));
    }
    public static String fmtDateTime(String datetime) {
        datetime = datetime.replaceFirst("[+\\-]\\d\\d:\\d\\d$","");
        LocalDateTime dt = LocalDateTime.parse(datetime);
        DateTimeFormatter fmt =DateTimeFormatter.ofPattern("hh:mm a MM/dd/yyyy");
        return dt.format(fmt);
    }


}
