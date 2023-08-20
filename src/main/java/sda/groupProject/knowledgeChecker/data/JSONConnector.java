package sda.groupProject.knowledgeChecker.data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class JSONConnector {
    public static ArrayList<Question> questionArrayList = new ArrayList<>();
    public JSONConnector() throws IOException {

        URL questionsJsonURL = new URL("https://public.andret.eu/questions.json");
        HttpURLConnection connection = (HttpURLConnection) questionsJsonURL.openConnection();
        connection.setRequestMethod("GET");
        // int responseCode = connection.getResponseCode();

        JSONTokener jsonTokener = new JSONTokener(connection.getInputStream());


        JSONArray jsonArray = new JSONArray(jsonTokener);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            questionArrayList.add(new Question(
                    jsonObject.getInt("id"),
                    jsonObject.getString("advancement"),
                    //new Category("category"),
                    jsonObject.getString("text"),
                    jsonObject.getString("answers"),
            ))
        }
    }
}