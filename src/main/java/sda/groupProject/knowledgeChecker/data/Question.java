package sda.groupProject.knowledgeChecker.data;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
public class Question {

    public static void  main(String[] args) throws IOException {
        // More on java.net.URL Class in Java here: https://www.geeksforgeeks.org/java-net-url-class-in-java/
        URL questionsJsonURL = new URL("https://public.andret.eu/questions.json");
        // More on Java.net.HttpURLConnection Class in Java here: https://www.geeksforgeeks.org/java-net-httpurlconnection-class-java/
        HttpURLConnection connection = (HttpURLConnection) questionsJsonURL.openConnection();
        connection.setRequestMethod("GET");

    }
}