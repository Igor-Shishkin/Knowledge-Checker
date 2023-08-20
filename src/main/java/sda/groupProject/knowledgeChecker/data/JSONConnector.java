package sda.groupProject.knowledgeChecker.data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONConnector {
    public List<Question> questionArrayList = new ArrayList<>();
    public JSONConnector() throws IOException {

        URL questionsJsonURL = new URL("https://public.andret.eu/questions.json");
        HttpURLConnection connection = (HttpURLConnection) questionsJsonURL.openConnection();
//        connection.setRequestMethod("GET");
        // int responseCode = connection.getResponseCode();
        JSONTokener jsonTokener = new JSONTokener(connection.getInputStream());
        JSONArray jsonArray = new JSONArray(jsonTokener);


        int idForCategory = 1;
        Map<String, Integer> categoryMap = new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String categoryName = jsonObject.getString("category");
            if (!categoryMap.containsKey(categoryName)) {
                categoryMap.put(categoryName, idForCategory++);
            }
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Advancement advancement = (jsonObject.getString("advancement").equals("medium"))
                    ? Advancement.MEDIUM :
                    (jsonObject.getString("advancement").equals("basic"))
                    ? Advancement.BASIC : Advancement.EXPERT;


            JSONArray JSONArrayAnswers = jsonObject.getJSONArray("answers");
            List<Answer> listOfAnswers = new ArrayList<>();
            //add new question
            for (int j = 0; j < JSONArrayAnswers.length(); j++) {

                JSONObject

                String text = ;
                boolean correct = ;
                String explanation = ;


                listOfAnswers.add(new Answer(text, correct, explanation))
            }


            String categoryName = jsonObject.getString("category");
            Category questionCategory =
                    new Category(categoryName, categoryMap.get(categoryName));


            questionArrayList.add(new Question(
                    jsonObject.getInt("id"),
                    advancement,
                    questionCategory,
                    jsonObject.getString("text"),
                    listOfAnswers));
        }
    }
//    int setIdCategory(String categoryName) {
//         if (questionArrayList.stream()
//                .anyMatch(question -> question.category().getCategoryName().equals(categoryName))) {
//             for (Question question : questionArrayList) {
//                 if (question.category().getCategoryName().equals(categoryName)) {
//                     return question.category().getCategoryId();
//                 }
//             }
//         } else {
//             idForCategory++;
//             return idForCategory;
//         };
//          return  0;
//    }
}