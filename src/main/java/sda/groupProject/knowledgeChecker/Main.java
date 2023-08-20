package sda.groupProject.knowledgeChecker;

import sda.groupProject.knowledgeChecker.data.Advancement;
import sda.groupProject.knowledgeChecker.data.Category;
import sda.groupProject.knowledgeChecker.data.JSONConnector;
import sda.groupProject.knowledgeChecker.graphicalInterface.GreetingWindow;

import java.io.IOException;
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
//        System.out.println(Arrays.toString(new ArrayList[]{connect.getListOfQuestions(Advancement.MEDIUM, new Category("JAVA_LANGUAGE", 1))}));
        new GreetingWindow(connect);
    }
}
