package sda.groupProject.knowledgeChecker.data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class JSONConnector {
    public List<Question> questionArrayList = new ArrayList<>();
    public Map<String, Integer> categoryMap;

    public JSONConnector() throws IOException {
        // Creating a jsonArray from https://public.andret.eu/questions.json
        URL questionsJsonURL = new URL("https://public.andret.eu/questions.json");
        HttpURLConnection connection = (HttpURLConnection) questionsJsonURL.openConnection();
        //connection.setRequestMethod("GET");
        //int responseCode = connection.getResponseCode();
        JSONTokener jsonTokener = new JSONTokener(connection.getInputStream());
        JSONArray jsonArray = new JSONArray(jsonTokener);

        // Iterating through JSONArray to assign ID's to each unique category value.
        int idForCategory = 1;
        categoryMap = new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String categoryName = jsonObject.getString("category");
            if (!categoryMap.containsKey(categoryName)) {
                categoryMap.put(categoryName, idForCategory++);
            }
        }

        // Iterating through JSONArray again to ...
        for (int i = 0; i < jsonArray.length(); i++) {
            // creating a JSONObject with key-value pairs from JSONArray
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            // creating an int variable and assigning value of that JSONObject's "id" key-value pair
            int questionID = jsonObject.getInt("id");

            // creating an enum object of type Advancement and assigning value of that JSONObject's "advancement" key-value pair
            Advancement questionAdvancement = (jsonObject.getString("advancement").equals("medium"))
                    ? Advancement.MEDIUM :
                    (jsonObject.getString("advancement").equals("basic"))
                            ? Advancement.BASIC : Advancement.EXPERT;

            // creating an object of type Category and assigning value of that JSONObject's "category" key-value pair
            String categoryName = jsonObject.getString("category");
            Category questionCategory = new Category(categoryName, categoryMap.get(categoryName));

            // creating an object of type String and assigning value of that JSONObject's "text" key-value pair
            String questionText = jsonObject.getString("text");

            // creating an array and assigning values of that JSONObject's "answers" key-value pair
            JSONArray JSONArrayAnswers = jsonObject.getJSONArray("answers");
            // creating a list of class type Answer
            List<Answer> questionListOfAnswers = new ArrayList<>();
            // Iterating through that created array
            for (int j = 0; j < JSONArrayAnswers.length(); j++) {
                // creating a JSONObject with key-value pairs for each entry in that array
                JSONObject jsonAnswerObject = JSONArrayAnswers.getJSONObject(j);
                // assigning values of newly created object's "text", "correct", and "explanation" key-value pairs
                String text = jsonAnswerObject.getString("text");
                boolean correct = jsonAnswerObject.getBoolean("correct");
                String explanation = jsonAnswerObject.getString("explanation");
                // creating an object of type Answer with retrieved values and adding that object to before created list
                questionListOfAnswers.add(new Answer(text, correct, explanation));
            }

            questionArrayList.add(new Question(
                    questionID,
                    questionAdvancement,
                    questionCategory,
                    questionText,
                    questionListOfAnswers));
        }
    }

    public ArrayList<Question> getListOfQuestions() {
        return (ArrayList<Question>) questionArrayList;
    }

    public String[] getCategoryNames () {
        String[] categoryNames = new String[categoryMap.size()];
        int i = 0;
        for(Map.Entry<String, Integer> entry : categoryMap.entrySet()) {
            categoryNames[i++] = entry.getKey();
        }
        return categoryNames;
    }

    public ArrayList<Question> getListOfQuestions(Advancement level, String[] categoryNames, int numberOfQuestions) {
        List<String> categoryNamesList = Arrays.asList(categoryNames);

        List<Question> filteredQuestions = questionArrayList
                .stream()
                .filter(question -> question.advancement().equals(level))
                .filter(question -> categoryNamesList.contains(question.category().categoryName()))
                .collect(Collectors.toList());

        Collections.shuffle(filteredQuestions);

        return new ArrayList<>(filteredQuestions.stream()
                .limit(numberOfQuestions)
                .collect(Collectors.toList()));
    }

}
