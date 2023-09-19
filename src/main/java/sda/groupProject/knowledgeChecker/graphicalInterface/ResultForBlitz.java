package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.Advancement;
import sda.groupProject.knowledgeChecker.data.JSONConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ResultForBlitz extends JFrame implements ActionListener {
    private final Font MAIN_FONT = new Font("Consolas", Font.PLAIN, 18);
    private final Color DARK_GREEN = new Color(0x0ABE00);
    private final int quantityOfQuestion;
    private final transient JSONConnector connect;
    private JLabel resultLabel;
    private JLabel infoLabel;
    private final transient List<GraphicalElementsOfQuestion> listOfPanels;
    private final double score;
    private final double maxScore;
    private int currentNumber;
    private JPanel resultPanel;
    private JPanel showQuestionPanel;
    private JPanel reviewPanel;
    private JButton exitButton;
    private JButton trainButton;
    private JButton seeTestButton;
    private JButton forwardButton;
    private JButton backButton;
    private GridBagConstraints c;
    private final String seeAnswers = "SEE MY ANSWERS";
    private JProgressBar progressBar;
    private final String textForProgressBar = "question %d out of %d";


    private ResultForBlitz(JSONConnector connect, List<GraphicalElementsOfQuestion> el, double score, int currentNumber,
                          double maxScore) {
        this.connect = connect;
        this.listOfPanels = el;
        this.score = score;
        this.currentNumber = currentNumber;
        this.maxScore = maxScore;

        quantityOfQuestion = currentNumber + 1;

        setResultPanel();
        setProgressBar();
        setReviewPanel();


        this.setLayout(new GridLayout(1, 1, 5, 5));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        this.add(scrollPane);


        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("RESULT OF TEST");
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==trainButton) {
            new GreetingWindow(connect);
            dispose();
        }
        if (e.getSource()==exitButton) {
            System.exit(1);
        }
        if (e.getSource() == seeTestButton) {

            if (seeTestButton.getText().equals(seeAnswers)) {
                actionIfSeeAnswersButtonIsClicked();
            }  else {
                actionIfSeeResultButtonIsClicked();
            }
        }
        if (e.getSource() == backButton) {
            actionIsBackButtonIsClicked();
        }
        if (e.getSource() == forwardButton) {
            actionIfForwardButtonIsClicked();
        }

    }

    private void actionIfForwardButtonIsClicked() {
        showQuestionPanel.remove(listOfPanels.get(currentNumber).questionPanel());
        currentNumber++;
        showQuestionPanel.add(listOfPanels.get(currentNumber).questionPanel());
        backButton.setEnabled(currentNumber>0);
        forwardButton.setEnabled(currentNumber< listOfPanels.size()-1);
        progressBar.setString(String.format(textForProgressBar, currentNumber+1, quantityOfQuestion));
        progressBar.setValue(currentNumber+1);
        this.pack();
    }

    private void actionIsBackButtonIsClicked() {
        showQuestionPanel.remove(listOfPanels.get(currentNumber).questionPanel());
        currentNumber--;
        showQuestionPanel.add(listOfPanels.get(currentNumber).questionPanel());
        backButton.setEnabled(currentNumber>0);
        forwardButton.setEnabled(currentNumber< listOfPanels.size()-1);
        progressBar.setString(String.format(textForProgressBar, currentNumber+1, quantityOfQuestion));
        progressBar.setValue(currentNumber+1);
        this.pack();
    }

    private void actionIfSeeResultButtonIsClicked() {
        reviewPanel.setVisible(false);
        resultLabel.setVisible(true);
        infoLabel.setVisible(true);

        showQuestionPanel.remove(listOfPanels.get(currentNumber).questionPanel());
        seeTestButton.setText(seeAnswers);

        this.pack();
    }

    private void actionIfSeeAnswersButtonIsClicked() {
        reviewPanel.setVisible(true);
        resultLabel.setVisible(false);
        infoLabel.setVisible(false);

        seeTestButton.setText("SEE MY RESULT");

        if (currentNumber == listOfPanels.size()) {
            currentNumber--;
        }
        forwardButton.setEnabled(currentNumber< listOfPanels.size()-1);
        showQuestionPanel.add(listOfPanels.get(currentNumber).questionPanel());
        this.pack();
    }


    private void setProgressBar() {
        progressBar = new JProgressBar(0, currentNumber);
        progressBar.setValue(currentNumber+1);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("MV Boli", Font.BOLD, 25));
        progressBar.setForeground(Color.red);
        progressBar.setBackground(Color.black);
        progressBar.setString(String.format(textForProgressBar, currentNumber+1, quantityOfQuestion));
    }

    private void setReviewPanel() {
        ImageIcon forwardIcon = new ImageIcon("src/main/resources/next.png");
        ImageIcon backIcon = new ImageIcon("src/main/resources/previous.png");
        forwardButton = new JButton(forwardIcon);
        forwardButton.addActionListener(this);
        backButton = new JButton(backIcon);
        backButton.addActionListener(this);
        forwardButton.setBackground(Color.GRAY);
        backButton.setBackground(Color.GRAY);

        JPanel nextBackButtonPanel = new JPanel(new GridLayout(1,2,5,5));
        nextBackButtonPanel.add(backButton);
        nextBackButtonPanel.add(forwardButton);

        reviewPanel = new JPanel(new GridBagLayout());

        c.gridx = 0;
        c.gridy = 0;
        reviewPanel.add(nextBackButtonPanel, c);

        showQuestionPanel = new JPanel(new GridLayout(1,1,5,5));

        c.gridx = 0;
        c.gridy = 1;
        reviewPanel.add(showQuestionPanel, c);
        reviewPanel.setVisible(false);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        reviewPanel.add(progressBar, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        resultPanel.add(reviewPanel, c);

    }

    private void setResultPanel() {
        exitButton = new JButton("EXIT");
        trainButton = new JButton("TRAIN");
        trainButton.setPreferredSize(new Dimension(100, 30));
        exitButton.addActionListener(this);
        trainButton.addActionListener(this);
        seeTestButton = new JButton(seeAnswers);
        seeTestButton.addActionListener(this);

        exitButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        exitButton.setForeground(Color.RED);
        trainButton.setBorder(BorderFactory.createLineBorder(DARK_GREEN, 3));
        trainButton.setForeground(DARK_GREEN);
        seeTestButton.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
        seeTestButton.setForeground(Color.blue);

        JPanel buttonsPanel = new JPanel(new GridLayout(1,3,5,5));
        buttonsPanel.add(trainButton);
        buttonsPanel.add(exitButton);
        buttonsPanel.add(seeTestButton);


        String resultText;
        String infoText;
        double percent = score / maxScore * 100;
        if (percent>=80 && score>=20) {
            resultText = "Super! You are hired";
            infoText = String.format(("<html>you answered %d questions<br>")
                    .concat("You scored %3.1f points out of %3.1f<br>or %3.1f percent.<br>")
                    .concat("This is a great result, congratulations!"),
                    currentNumber , score, maxScore, percent);
        } else if (percent>=0){
            resultText = "Thank you, we will call you back later ";
            infoText = String.format(("<html>you answered %d questions<br>")
                    .concat("You scored %3.1f points out of %3.1f<br>or %3.1f percent.<br>")
                    .concat("More work could be done :(<br>Good luck on your next try"),
                    currentNumber , score, maxScore, percent);
        }
        else {
            resultText = "Do you know anything about Java at all?";
            infoText = String.format(("<html>you answered %d questions<br>")
                            .concat("You scored %3.1f points out of %3.1f<br>or %3.1f percent.<br>")
                            .concat("<br>Good luck on your next try"),
                    currentNumber , score, maxScore, percent);
        }



        resultLabel = new JLabel(resultText);
        resultLabel.setFont(MAIN_FONT.deriveFont(Font.BOLD, 35));
        infoLabel = new JLabel(infoText);
        infoLabel.setFont(MAIN_FONT.deriveFont(Font.PLAIN, 30));



        resultPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10,10,10,10);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        resultPanel.add(resultLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        resultPanel.add(infoLabel, c);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        resultPanel.add(buttonsPanel, c);
    }

    public static class Builder {
        private JSONConnector connect;
        private double score;
        private int currentNumber;
        private double maxScore;
        private String[] listOfCategory;
        private List<GraphicalElementsOfQuestion> listOfPanels;

        public Builder withConnect (JSONConnector connect){
            this.connect = connect;
            return this;
        }
        public Builder withScore (int score){
            this.score = score;
            return this;
        }
        public Builder withCurrentNumber (int currentNumber){
            this.currentNumber = currentNumber;
            return this;
        }
        public Builder withScore (double score){
            this.score = score;
            return this;
        }
        public Builder withMaxScore (double maxScore){
            this.maxScore = maxScore;
            return this;
        }
        public Builder withListOfPanels (List<GraphicalElementsOfQuestion> listOfPanels){
            this.listOfPanels = listOfPanels;
            return this;
        }
        public ResultForBlitz build() {
            return new ResultForBlitz(connect, listOfPanels, score, currentNumber, maxScore);
        }

    }
}



