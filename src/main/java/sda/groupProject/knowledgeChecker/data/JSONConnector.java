package sda.groupProject.knowledgeChecker.data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class JSONConnector {
   private final List<Question> questionArrayList = new ArrayList<>();
   private final Map<String, Integer> categoryMap;

    public JSONConnector() throws IOException {
        URL questionsJsonURL = new URL("https://public.andret.eu/questions.json");
        HttpURLConnection connection = (HttpURLConnection) questionsJsonURL.openConnection();

        JSONTokener jsonTokener = new JSONTokener(connection.getInputStream());
        JSONArray jsonArray = new JSONArray(jsonTokener);

        // for iterating through JSONArray to assign ID's to each unique category value.
        int idForCategory = 1;
        categoryMap = new HashMap<>();

        // Iterating through JSONArray again to ...
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            int questionID = jsonObject.getInt("id");

            Advancement questionAdvancement = determineLevel(jsonObject.getString("advancement"));

            // creating an object of type Category and assigning value of that JSONObject's "category" key-value pair
            String categoryName = jsonObject.getString("category");
            if (!categoryMap.containsKey(categoryName)) {
                categoryMap.put(categoryName, idForCategory++);
            }
            Category questionCategory = new Category(categoryName, categoryMap.get(categoryName));

            String questionText = jsonObject.getString("text");

            String questionCode = jsonObject.has("code") ? jsonObject.getString("code") : null;

            List<Answer> questionListOfAnswers = getListOfAnswersForQuestion(jsonObject.getJSONArray("answers"));

            questionArrayList.add(new Question(
                    questionID,
                    questionAdvancement,
                    questionCategory,
                    questionText,
                    questionCode,
                    questionListOfAnswers));
        }
    }

    private List<Answer> getListOfAnswersForQuestion(JSONArray answers) {
        List<Answer> questionListOfAnswers = new ArrayList<>();
        for (int j = 0; j < answers.length(); j++) {
            JSONObject jsonAnswerObject = answers.getJSONObject(j);
            String text = jsonAnswerObject.getString("text");
            boolean correct = jsonAnswerObject.getBoolean("correct");
            String explanation = jsonAnswerObject.getString("explanation");
            questionListOfAnswers.add(new Answer(text, correct, explanation));
        }
        Collections.shuffle(questionListOfAnswers);
        return questionListOfAnswers;
    }

    private Advancement determineLevel(String advancement) {
        if (advancement.equals("medium")) {
            return Advancement.MEDIUM;
        } else if (advancement.equals("basic")){
            return Advancement.BASIC;
        }
        return Advancement.EXPERT;
    }

    public List<Question> getListOfQuestions() {
        return questionArrayList;
    }

    public String[] getCategoryNames() {
        String[] categoryNames = new String[categoryMap.size()];
        int i = 0;
        for (Map.Entry<String, Integer> entry : categoryMap.entrySet()) {
            categoryNames[i++] = entry.getKey();
        }
        return categoryNames;
    }
}
