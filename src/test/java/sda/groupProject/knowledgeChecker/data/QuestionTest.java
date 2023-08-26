package sda.groupProject.knowledgeChecker.data;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNotNull(question);
        assertEquals(1, question.id());
        assertEquals(advancement, question.advancement());
        assertEquals(category, question.category());
        assertEquals(text, question.text());
        assertEquals(code, question.code());
        assertEquals(answers, question.answers());
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
        assertNotNull(correctAnswer);
        assertTrue(correctAnswer.correct());
        assertEquals("Nile", correctAnswer.text());
    }

}