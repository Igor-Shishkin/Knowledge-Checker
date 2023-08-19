package sda.groupProject.knowledgeChecker.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonParsingExample {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://public.andret.eu/questions.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON array
                JSONArray jsonArray = new JSONArray(response.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int id = jsonObject.getInt("id");
                    String advancement = jsonObject.getString("advancement");
                    String category = jsonObject.getString("category");
                    String text = jsonObject.getString("text");

                    JSONArray answersArray = jsonObject.getJSONArray("answers");
                    for (int j = 0; j < answersArray.length(); j++) {
                        JSONObject answerObject = answersArray.getJSONObject(j);
                        String answerText = answerObject.getString("text");
                        boolean isCorrect = answerObject.getBoolean("correct");
                        String explanation = answerObject.getString("explanation");

                        System.out.println("Answer Text: " + answerText);
                        System.out.println("Is Correct: " + isCorrect);
                        System.out.println("Explanation: " + explanation);
                        System.out.println();
                    }
                }
            } else {
                System.out.println("HTTP request failed with response code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
