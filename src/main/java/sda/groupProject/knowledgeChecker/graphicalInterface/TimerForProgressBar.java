package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.JSONConnector;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.TimerTask;

public class TimerForProgressBar extends TimerTask {
    JProgressBar progressBar;
    JSONConnector connect;
    List<GraficalElementsOfQuestion> el;
    double score, maxScore;
    int currentNumber;
    JButton nextButton;


    public TimerForProgressBar(JProgressBar progressBar, List<GraficalElementsOfQuestion> el, JButton nextButton,
                               int currentNumber) {
        this.progressBar = progressBar;
        this.el = el;
        this.nextButton = nextButton;
        this.currentNumber = currentNumber;
    }

    @Override
    public void run() {
        progressBar.setValue(progressBar.getValue()-1);

        String progress = (progressBar.getValue()>60)
                ? String.format("%d minutes and %d seconds left", progressBar.getValue()/60, progressBar.getValue()%60)
                : String.format("%d seconds left", progressBar.getValue()%60);
        progressBar.setString(progress);
        if (progressBar.getValue() == 0) {
            for (JRadioButton rb : el.get(currentNumber).answerRadioButtons()) {
                rb.setEnabled(false);
            }
            nextButton.setEnabled(true);
            nextButton.setText("<html>TIME IS<br>OVER</html>");
            nextButton.setFont(new Font(null, Font.BOLD, 15));
            nextButton.setBackground(Color.BLACK);
            this.cancel();
        }
    }
}
