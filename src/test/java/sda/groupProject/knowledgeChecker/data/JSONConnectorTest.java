package sda.groupProject.knowledgeChecker.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JSONConnectorTest {
    private JSONConnector jsonConnector;

    @BeforeEach
    void setUp() {
        try {
            jsonConnector = new JSONConnector(); // This will test the constructor
        } catch (IOException e) {
            fail("Failed to create JSONConnector");
        }
    }

//    @Test
//    void testCategoryMapNotEmpty() {
//        assertNotNull(jsonConnector.categoryMap);
//        assertFalse(jsonConnector.categoryMap.isEmpty());
//    }

//    @Test
//    void testQuestionArrayListNotEmpty() {
//        assertNotNull(jsonConnector.questionArrayList);
//        assertFalse(jsonConnector.questionArrayList.isEmpty());
//    }

//    @Test
//    void testQuestionArrayListContainsQuestions() {
//        List<Question> questionArrayList = jsonConnector.questionArrayList;
//        assertNotNull(questionArrayList);
//
//        assertFalse(questionArrayList.isEmpty());
//    }

    @Test
    void testListOfQuestionsNotEmpty() {
        assertNotNull(jsonConnector.getListOfQuestions());
        assertFalse(jsonConnector.getListOfQuestions().isEmpty());
    }

    @Test
    void testGetCategoryNamesNotEmpty() {
        assertNotNull(jsonConnector.getCategoryNames());
        assertTrue(jsonConnector.getCategoryNames().length > 0);
    }

//    @Test
//    void testListOfQuestionsIsRandomAndFiltered() {
//        Advancement level = Advancement.BASIC;
//        String[] categoryNames = jsonConnector.getCategoryNames();
//        int numberOfQuestions = 5;
//
//        ArrayList<Question> filteredAndShuffledQuestions = jsonConnector.getListOfQuestions(level, categoryNames, numberOfQuestions);
//
//        assertNotNull(filteredAndShuffledQuestions);
//        assertEquals(numberOfQuestions, filteredAndShuffledQuestions.size());
//
//        List<String> categoryNamesList = Arrays.asList(categoryNames);
//
//        // Check if questions are of the specified level and category
//        for (Question question : filteredAndShuffledQuestions) {
//            assertEquals(level, question.advancement());
//            assertTrue(categoryNamesList.contains(question.category().categoryName()));
//        }
//
//        // Check if questions are shuffled (not in the original order)
//        boolean isShuffled = false;
//        for (int i = 1; i < filteredAndShuffledQuestions.size(); i++) {
//            if (!filteredAndShuffledQuestions.get(i).equals(filteredAndShuffledQuestions.get(i - 1))) {
//                isShuffled = true;
//                break;
//            }
//        }
//        assertTrue(isShuffled);
//    }
}