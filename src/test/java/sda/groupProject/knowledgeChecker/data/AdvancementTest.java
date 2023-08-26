package sda.groupProject.knowledgeChecker.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdvancementTest {

    @Test
    void testBasicAdvancement() {
        // given
        Advancement advancement = Advancement.BASIC;

        // then
        assertEquals(Advancement.BASIC, advancement);
    }

    @Test
    void testMediumAdvancement() {
        // given
        Advancement advancement = Advancement.MEDIUM;

        // then
        assertEquals(Advancement.MEDIUM, advancement);
    }

    @Test
    void testExpertAdvancement() {
        // given
        Advancement advancement = Advancement.EXPERT;

        // then
        assertEquals(Advancement.EXPERT, advancement);
    }
}