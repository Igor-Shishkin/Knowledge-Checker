package sda.groupProject.knowledgeChecker.data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JsonParsingExample {
    public static void  main(String[] args) throws IOException {
        // More on java.net.URL Class in Java here: https://www.geeksforgeeks.org/java-net-url-class-in-java/
        URL questionsJsonURL = new URL("https://public.andret.eu/questions.json");
        // More on Java.net.HttpURLConnection Class in Java here: https://www.geeksforgeeks.org/java-net-httpurlconnection-class-java/
        HttpURLConnection connection = (HttpURLConnection) questionsJsonURL.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {

            JSONTokener jsonTokener = new JSONTokener(connection.getInputStream());
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuilder response = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                response.append(line);
//            }
//            reader.close();

            JSONArray jsonArray = new JSONArray(jsonTokener);


            Scanner scanner = new Scanner(System.in);

            List<Question> question = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String advancement = jsonObject.getString("advancement"); // - ENUM
                String category = jsonObject.getString("category"); // OBJECT


                // Choose the specific advancement and category you want to display
                if (advancement.equals("medium") && category.equals("JAVA_LANGUAGE")) {
                    String text = jsonObject.getString("text");
                    JSONArray answersArray = jsonObject.getJSONArray("answers");

                    System.out.println("Question: " + text);
                    for (int j = 0; j < answersArray.length(); j++) {
                        JSONObject answerObject = answersArray.getJSONObject(j);
                        String answerText = answerObject.getString("text");
                        System.out.println((j + 1) + ". " + answerText);
                    }

                    System.out.print("Enter your answer (number): ");
                    int userAnswer = scanner.nextInt();

                    JSONObject userAnswerObject = answersArray.getJSONObject(userAnswer - 1);
                    boolean isCorrect = userAnswerObject.getBoolean("correct");
                    String explanation = userAnswerObject.getString("explanation");

                    if (isCorrect) {
                        System.out.println("Correct!");
                    } else {
                        System.out.println("Incorrect.");
                    }
                    System.out.println("Explanation: " + explanation);
                    System.out.println();
                }
            }
            scanner.close();
        } else {
            System.out.println("HTTP request failed with response code: " + responseCode);
        }

        connection.disconnect();
    }
}
