package sda.groupProject.knowledgeChecker.data;

import java.util.*;
import java.util.stream.Collectors;

public class FilterListOfQuestions {
    private final List<Question> questionArrayList;

    public FilterListOfQuestions(List<Question> questionArrayList) {
        this.questionArrayList = questionArrayList;
    }

    public List<Question> getListOfQuestions(Advancement level, String[] categoryNames, int numberOfQuestions) {
        List<String> categoryNamesList = Arrays.asList(categoryNames);

        List<Question> filteredQuestions = questionArrayList
                .stream()
                .filter(question -> question.advancement().equals(level))
                .filter(question -> categoryNamesList.contains(question.category().categoryName()))
                .collect(Collectors.toList());

        Collections.shuffle(filteredQuestions);

        return filteredQuestions.stream()
                .limit(numberOfQuestions)
                .toList();
    }
    public List<Question> getShuffledListOfQuestion(){
        Collections.shuffle(questionArrayList);
        return questionArrayList;
    }

}
