package sda.groupProject.knowledgeChecker.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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

    @Test
    void testCategoryMapNotEmpty() {
        assertNotNull(jsonConnector.categoryMap);
        assertFalse(jsonConnector.categoryMap.isEmpty());
    }

    @Test
    void testQuestionArrayListNotEmpty() {
        assertNotNull(jsonConnector.questionArrayList);
        assertFalse(jsonConnector.questionArrayList.isEmpty());
    }

    @Test
    void testQuestionArrayListContainsQuestions() {
        List<Question> questionArrayList = jsonConnector.questionArrayList;
        assertNotNull(questionArrayList);

        assertFalse(questionArrayList.isEmpty());
    }

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
}