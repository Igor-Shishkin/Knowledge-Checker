package sda.groupProject.knowledgeChecker.graphicalInterface;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

public class TimerForProgressBar extends TimerTask {
    private JProgressBar progressBar;
    private JButton nextButton;
    private final JFrame frame;


    public TimerForProgressBar(JProgressBar progressBar, JButton nextButton, JFrame frame) {
        this.progressBar = progressBar;
        this.nextButton = nextButton;
        this.frame = frame;
    }

    @Override
    public void run() {
        progressBar.setValue(progressBar.getValue()-1);

        String progress = (progressBar.getValue()>60)
                ? String.format("%d minutes and %d seconds left", progressBar.getValue()/60, progressBar.getValue()%60)
                : String.format("%d seconds left", progressBar.getValue()%60);
        progressBar.setString(progress);
        if (progressBar.getValue() == 0) {
            nextButton.setEnabled(true);
            nextButton.setText("<html>TIME IS<br>OVER</html>");
            nextButton.setFont(new Font(null, Font.BOLD, 15));
            nextButton.setBackground(Color.BLACK);
            frame.pack();
            this.cancel();
        }
    }
}
