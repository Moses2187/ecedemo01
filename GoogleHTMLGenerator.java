import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;

public class GoogleHTMLGenerator {
    public static void main(String[] args) {
        try {
            // Create URL object for Google
            URL url = new URL("https://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set request properties to mimic a browser
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            connection.setRequestMethod("GET");
            
            // Get response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine).append("\n");
                }
                
                // Write to file
                BufferedWriter writer = new BufferedWriter(new FileWriter("google-homepage.html"));
                writer.write(content.toString());
                writer.close();
                
                in.close();
                
                System.out.println("Google homepage saved to google-homepage.html");
                System.out.println("File size: " + content.length() + " characters");
            } else {
                System.err.println("Failed to fetch Google. Response code: " + responseCode);
            }
            
            connection.disconnect();
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
