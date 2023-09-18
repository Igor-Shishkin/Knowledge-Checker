package sda.groupProject.knowledgeChecker.data;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AnswerTest {
    @Test
    void testAnswerConstructorAndGetters() {
        Answer answer = new Answer("Java", true, "Explanation");

        // Test getters using AssertJ assertions
        assertThat(answer.text()).isEqualTo("Java");
        assertThat(answer.correct()).isTrue();
        assertThat(answer.explanation()).isEqualTo("Explanation");
    }

    @Test
    void testEqualsAndHashCode() {
        Answer answer1 = new Answer("Java", true, "Explanation");
        Answer answer2 = new Answer("Java", true, "Explanation");
        Answer answer3 = new Answer("C++", false, "Different Explanation");

        // Test equality using AssertJ assertions
        assertThat(answer1).isEqualTo(answer2)
                .isNotEqualTo(answer3);

        // Test hash code using AssertJ assertions
        assertThat(answer1.hashCode()).isEqualTo(answer2.hashCode())
                .isNotEqualTo(answer3.hashCode());
    }
}