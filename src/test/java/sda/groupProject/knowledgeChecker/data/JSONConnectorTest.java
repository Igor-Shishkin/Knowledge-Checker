package sda.groupProject.knowledgeChecker.data;

import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class JSONConnectorTest {

    @Test
    void testJsonParsing() throws Exception {

        String json = "[{\"id\": 1, \"advancement\": \"medium\", \"category\": \"General\", \"text\": \"What is computer?\","
                .concat(" \"answers\": [{\"text\": \"machine\", \"correct\": \"true\", \"explanation\": \"because\"}]},")
                .concat("{\"id\": 2, \"advancement\": \"basic\", \"category\": \"Java\", \"text\": \"What's JAVA?\",")
                .concat(" \"answers\": [{\"text\": \"language\", \"correct\": \"true\", \"explanation\": \"because\"}]}]");

        // Mock HttpURLConnection to return a custom JSON string
        HttpURLConnection mockedConnection = Mockito.mock(HttpURLConnection.class);

        InputStream inputStream = new ByteArrayInputStream(json.getBytes());
        Mockito.when(mockedConnection.getInputStream()).thenReturn(inputStream);

        URL mockedUrl = Mockito.mock(URL.class);
        Mockito.when(mockedUrl.openConnection()).thenReturn(mockedConnection);
        JSONConnector connect = new JSONConnector(mockedConnection);

        List<Question> testList = connect.getListOfQuestions();

        List<String> expectedListOfCategories = new ArrayList<>(List.of("General", "Java"));

        Question questionOne = new Question(1,
                Advancement.MEDIUM,
                new Category("General", 1),
                "What is computer?",
                null,
                List.of(new Answer("machine", true, "because")));
        Question questionTwo = new Question(2,
                Advancement.BASIC,
                new Category("Java", 2),
                "What's JAVA?",
                null,
                List.of(new Answer("language", true, "because")));
        List<Question> expectedListOfQuestion = new ArrayList<>(List.of(questionOne, questionTwo));

        assertThat(testList)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedListOfQuestion);
        assertThat(connect.getCategoryNames()).containsAnyElementsOf(expectedListOfCategories);
    }
}