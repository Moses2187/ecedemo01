import java.io.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.util.Scanner;

public class HTTPClientFixed {
    public static void main(String[] args) { 
        String host ="google.com"; 
        String path = "/";
        int port = 443; // HTTPS port
        
        String outputfile = "google.html";

        System.out.println("Attempting to connect to " + host + ":" + port + "...");

        try {
            // Create SSL socket factory for HTTPS
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) factory.createSocket(host, port);

            System.out.println("SSL Connection established.");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner in = new Scanner(socket.getInputStream());
            
            FileOutputStream fileOutputStream = new FileOutputStream(outputfile);
            BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));

            // Construct HTTPS GET request
            String httpRequest = "GET " + path + " HTTP/1.1\r\n" +
                                 "Host: " + host + "\r\n" +
                                 "Connection: close\r\n" +
                                 "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36\r\n" +
                                 "\r\n";

            System.out.println("\nSending HTTPS GET request:\n" + httpRequest);
            
            out.print(httpRequest);
            out.flush();

            String line;
            boolean headersDone = false;
            while (in.hasNextLine()) {
                line = in.nextLine();
                
                // Skip HTTP headers
                if (line.isEmpty() && !headersDone) {
                    headersDone = true;
                    continue;
                }
                
                if (headersDone) {
                    fileWriter.write(line);
                    fileWriter.newLine();
                    System.out.println(line);
                }
            }

            System.out.println("Google webpage downloaded successfully to " + outputfile);
            
            fileWriter.close();
            fileOutputStream.close();
            socket.close();
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
