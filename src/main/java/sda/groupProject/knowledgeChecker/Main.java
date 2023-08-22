package sda.groupProject.knowledgeChecker;

import sda.groupProject.knowledgeChecker.data.Advancement;
import sda.groupProject.knowledgeChecker.data.Category;
import sda.groupProject.knowledgeChecker.data.JSONConnector;
import sda.groupProject.knowledgeChecker.data.Question;
import sda.groupProject.knowledgeChecker.graphicalInterface.GreetingWindow;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args ) throws IOException {

        JSONConnector connect = new JSONConnector();

        List<String> selectedCategories = Arrays.asList("GENERAL", "DESIGN_PATTERNS");
        //List<String> selectedCategories = Arrays.asList("JAVA_LANGUAGE");
        Advancement selectedLevel = Advancement.MEDIUM;
        boolean randomSelection = true;
        int numberOfQuestions = 2;

        List<Question> filteredQuestions = connect.getListOfQuestions(selectedLevel, selectedCategories, randomSelection, numberOfQuestions);

        System.out.println(filteredQuestions);

//        new GreetingWindow(connect);
    }
}
