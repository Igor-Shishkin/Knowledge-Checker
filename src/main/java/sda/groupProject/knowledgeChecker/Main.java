package sda.groupProject.knowledgeChecker;

import sda.groupProject.knowledgeChecker.data.*;
import sda.groupProject.knowledgeChecker.graphicalInterface.GreetingWindow;

import java.io.IOException;

public class Main
{
    public static void main( String[] args ) throws IOException {

        JSONConnector connect = new JSONConnector();

        new GreetingWindow(connect);
    }
}
