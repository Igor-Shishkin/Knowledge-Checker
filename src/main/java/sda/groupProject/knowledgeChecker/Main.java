package sda.groupProject.knowledgeChecker;

import sda.groupProject.knowledgeChecker.data.*;
import sda.groupProject.knowledgeChecker.graphicalInterface.GreetingWindow;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

public class Main
{
    public static void main( String[] args ) throws IOException {

        // Testing changeTextToHTML method
        String input = "Który wzorzec projektowy rozwiązuje problem polegający na potrzebie opcjonalnych argumentów konstruktora?";
        int lineLength = 20;

        String output = HTMLConverter.changeTextToHTML(input, lineLength);
        System.out.println(output);

        // Testing JSONConnector class methods
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
