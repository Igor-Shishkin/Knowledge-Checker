package sda.groupProject.knowledgeChecker.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {
    @Test
    void constructAnswerAndVerifyProperties() {
        // given
        String answerText = "This is an answer.";
        boolean isCorrect = true;
        String explanation = "Explanation for the correct answer.";

        // when
        Answer answer = new Answer(answerText, isCorrect, explanation);

        // then
        assertEquals(answerText, answer.text());
        assertTrue(answer.correct());
        assertEquals(explanation, answer.explanation());
    }

    @Test
    void compareEqualAnswers() {
        // given
        Answer answer1 = new Answer("Answer text.", true, "Explanation 1");
        Answer answer2 = new Answer("Answer text.", true, "Explanation 1");

        // then
        assertEquals(answer1, answer2);
    }

    @Test
    void compareDifferentAnswers() {
        // given
        Answer answer1 = new Answer("Answer text.", true, "Explanation 1");
        Answer answer3 = new Answer("Different text.", true, "Explanation 1");

        // then
        assertNotEquals(answer1, answer3);
    }

    @Test
    void compareHashCodeOfEqualAnswers() {
        // given
        Answer answer1 = new Answer("Answer text.", true, "Explanation 1");
        Answer answer2 = new Answer("Answer text.", true, "Explanation 1");

        // then
        assertEquals(answer1.hashCode(), answer2.hashCode());
    }
}