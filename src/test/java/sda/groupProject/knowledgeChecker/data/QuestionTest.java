package sda.groupProject.knowledgeChecker.data;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

class QuestionTest {

    @Test
    void createQuestion() {
        // given
        Advancement advancement = Advancement.MEDIUM;
        Category category = new Category("Java", 1);
        String text = "What is the capital of France?";
        String code = "System.out.println(\"Hello, World!\");";
        List<Answer> answers = List.of(
                new Answer("Paris", true, "The capital of France indeed"),
                new Answer("Berlin", false, "Germany"),
                new Answer("Madrid", false, "Spain")
        );

        // when
        Question question = new Question(1, advancement, category, text, code, answers);

        // then
        assertThat(question).isNotNull();
        assertThat(question.id()).isEqualTo(1);
        assertThat(question.advancement()).isEqualTo(advancement);
        assertThat(question.category()).isEqualTo(category);
        assertThat(question.text()).isEqualTo(text);
        assertThat(question.code()).isEqualTo(code);
        assertThat(question.answers()).isEqualTo(answers);
    }

    @Test
    void testEqualsAndHashCode() {
        Category category1 = new Category("General", 1);
        Category category2 = new Category("Java", 2);

        List<Answer> answers1 = List.of(new Answer("This is a instruction for computer",
                true, "ExplanationA"));
        List<Answer> answers2 = List.of(new Answer("Java it is a coffee", false, "ExplanationB"));

        Question question1 =
                new Question(1, Advancement.MEDIUM, category1, "What is a program?", "Code", answers1);
        Question question2 =
                new Question(1, Advancement.MEDIUM, category1, "What is a program?", "Code", answers1);
        Question question3 =
                new Question(2, Advancement.EXPERT, category2, "What is Java?", null, answers2);

        // Test equality using AssertJ assertions
        assertThat(question1).isEqualTo(question2)
                .isNotEqualTo(question3);

        // Test hash code using AssertJ assertions
        assertThat(question1.hashCode()).isEqualTo(question2.hashCode())
                .isNotEqualTo(question3.hashCode());
    }

    @Test
    void getCorrectAnswer() {
        // given
        Advancement advancement = Advancement.BASIC;
        Category category = new Category("Geography", 1);
        String text = "Which river runs through Egypt?";
        String code = "";
        List<Answer> answers = List.of(
                new Answer("Nile", true, "The Nile is the longest river in the world."),
                new Answer("Amazon", false, ""),
                new Answer("Danube", false, "")
        );
        Question question = new Question(2, advancement, category, text, code, answers);

        // when
        Answer correctAnswer = question.answers().stream()
                .filter(Answer::correct)
                .findFirst()
                .orElse(null);

        // then
        assertThat(correctAnswer).isNotNull();
        assertThat(correctAnswer.correct()).isTrue();
        assertThat(correctAnswer.text()).isEqualTo("Nile");
    }

}