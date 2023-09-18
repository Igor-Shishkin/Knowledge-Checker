package sda.groupProject.knowledgeChecker.data;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FilterListOfQuestionsTest {

    @Test
    void getListOfQuestions() {
        Category category1 = new Category("General", 1);
        Category category2 = new Category("Java", 2);
        Category category3 = new Category("Design", 3);

        Question question1 =
                new Question(1, Advancement.MEDIUM, category1, "Question", "Code", null);
        Question question2 =
                new Question(2, Advancement.MEDIUM, category2, "Question", "Code", null);
        Question question3 =
                new Question(3, Advancement.EXPERT, category1, "Question", null, null);
        Question question4 =
                new Question(4, Advancement.EXPERT, category3, "Question", null, null);
        Question question5 =
                new Question(5, Advancement.EXPERT, category2, "Question", null, null);
        Question question6 =
                new Question(6, Advancement.BASIC, category2, "Question", null, null);
        Question question7 =
                new Question(7, Advancement.EXPERT, category2, "Question", null, null);

        List<Question> listOfQuestions = new ArrayList<>(List.of
                (question1, question2, question3, question4, question5, question6, question7));

        FilterListOfQuestions filterListOfQuestions = new FilterListOfQuestions(listOfQuestions);
        List<Question> filteredList = filterListOfQuestions
                .getListOfQuestions(Advancement.EXPERT, new String[]{"General", "Java"}, 5);

        assertThat(filteredList)
                .isNotEqualTo(listOfQuestions)
                .hasSize(3)
                .containsOnly(question5, question7, question3);
    }

    @Test
    void getShuffledListOfQuestion() {

        Category category1 = new Category("General", 1);
        Category category2 = new Category("Java", 2);
        Category category3 = new Category("Design", 3);

        Question question1 =
                new Question(1, Advancement.MEDIUM, category1, "Question", "Code", null);
        Question question2 =
                new Question(2, Advancement.MEDIUM, category2, "Question", "Code", null);
        Question question3 =
                new Question(3, Advancement.EXPERT, category1, "Question", null, null);
        Question question4 =
                new Question(4, Advancement.EXPERT, category3, "Question", null, null);
        Question question5 =
                new Question(5, Advancement.EXPERT, category2, "Question", null, null);
        Question question6 =
                new Question(6, Advancement.BASIC, category2, "Question", null, null);
        Question question7 =
                new Question(7, Advancement.EXPERT, category2, "Question", null, null);

        List<Question> listOfQuestions = new ArrayList<>(List.of
                (question1, question2, question3, question4, question5, question6, question7));

        List<Question> checkList = new ArrayList<>(List.of
                (question1, question2, question3, question4, question5, question6, question7));

        FilterListOfQuestions filterListOfQuestions = new FilterListOfQuestions(listOfQuestions);
        List<Question> filteredList = filterListOfQuestions.getShuffledListOfQuestion();

        assertThat(filteredList)
                .isNotEqualTo(checkList)
                .hasSize(7);


    }
}