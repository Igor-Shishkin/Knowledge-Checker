package sda.groupProject.knowledgeChecker;

import sda.groupProject.knowledgeChecker.data.*;
import sda.groupProject.knowledgeChecker.graphicalInterface.GreetingWindow;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main
{
    public static void main( String[] args ) throws IOException {

        URL questionsJsonURL = new URL("https://public.andret.eu/questions.json");
        HttpURLConnection connection = (HttpURLConnection) questionsJsonURL.openConnection();

        JSONConnector connect = new JSONConnector(connection);

        new GreetingWindow(connect);
    }
}
