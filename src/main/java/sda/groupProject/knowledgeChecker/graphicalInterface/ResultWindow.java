package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.Advancement;
import sda.groupProject.knowledgeChecker.data.JSONConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ResultWindow extends JFrame implements ActionListener {


    private final Font MAIN_FONT = new Font("Consolas", Font.PLAIN, 18);
    private final Color DARK_GREEN = new Color(0x066C00);
    private final JSONConnector connect;
    private final List<GraficalElementsOfQuestion> listOfPanels;
    private final int score;
    private final int quantityQuestions;
    private int currentNumber;
    private JLabel resultLabel;
    private JLabel detailsLabel;
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
    String[] listOfCategory;
    Advancement advancement;



    private ResultWindow(JSONConnector connect, int score, int quantityQuestions, Advancement advancement,
                        String[] listOfCategory, List<GraficalElementsOfQuestion> listOfPanels) {
        this.connect = connect;
        this.score = score;
        this.quantityQuestions = quantityQuestions;
        this.advancement = advancement;
        this.listOfCategory = listOfCategory;
        this.listOfPanels = listOfPanels;

        currentNumber = quantityQuestions-1;

        setResultPanel();
        setReviewPanel();

        this.setLayout(new GridLayout(1, 1, 5, 5));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        this.add(scrollPane);


        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("RESULT OF TEST");
        this.setVisible(true);
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

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        resultPanel.add(reviewPanel, c);

        reviewPanel.setVisible(false);

    }

    private void setResultPanel() {
        exitButton = new JButton("EXIT");
        trainButton = new JButton("TRAIN");
        seeTestButton = new JButton(seeAnswers);

        trainButton.setPreferredSize(new Dimension(100, 40));
        exitButton.addActionListener(this);
        trainButton.addActionListener(this);
        seeTestButton.addActionListener(this);

        exitButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        exitButton.setForeground(Color.RED);
        trainButton.setBorder(BorderFactory.createLineBorder(DARK_GREEN, 3));
        trainButton.setForeground(DARK_GREEN);
        exitButton.setFont(MAIN_FONT.deriveFont(Font.BOLD, 35));
        trainButton.setFont(MAIN_FONT.deriveFont(Font.BOLD, 35));
        seeTestButton.setFont(MAIN_FONT.deriveFont(Font.BOLD, 35));
        seeTestButton.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
        seeTestButton.setForeground(Color.blue);

        JPanel buttonsPanel = new JPanel(new GridLayout(1,2,5,5));
        buttonsPanel.add(trainButton);
        buttonsPanel.add(exitButton);
        buttonsPanel.add(seeTestButton);

        String resultText;
        double percent = (double) score / quantityQuestions * 100;
        resultText = (percent >= 80) ? String.format("<html>You scored %d points out of %d<br>or %3.1f percent.<br>"
                        .concat("This is a great result, congratulations!"),
                        score, quantityQuestions, percent)
                : String.format("<html>You scored %d points out of %d<br>or %3.1f percent.<br>"
                        .concat("Much work to be done :(<br>Good luck on your next try"),
                score, quantityQuestions, percent);
        resultLabel = new JLabel(resultText);
        resultLabel.setFont(MAIN_FONT.deriveFont(Font.BOLD, 30));

        String categories = "";
        for (String category : listOfCategory) {
            categories = categories.concat(category).concat("<br>");
        }
        String detailsText = String.format("<html>Categories:<br> %s<br>Level: %s<br>Quantity of questions: %d</html>",
                categories.substring(0,categories.length()-4), advancement, quantityQuestions);
        detailsLabel = new JLabel(detailsText);
        detailsLabel.setBorder(BorderFactory.createTitledBorder("Details"));
        detailsLabel.setFont(MAIN_FONT.deriveFont(Font.PLAIN, 25));

        resultPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,10,10,10);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        resultPanel.add(resultLabel, c);

        c.insets = new Insets(20,10,10,10);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        resultPanel.add(detailsLabel, c);

        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        resultPanel.add(buttonsPanel, c);

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

                reviewPanel.setVisible(true);
                resultLabel.setVisible(false);
                detailsLabel.setVisible(false);

                if (currentNumber == listOfPanels.size()) {
                    currentNumber--;
                }
                forwardButton.setEnabled(currentNumber<listOfPanels.size()-1);
                showQuestionPanel.add(listOfPanels.get(currentNumber).questionPanel());
                this.pack();

                String seeResult = "SEE MY RESULT";
                seeTestButton.setText(seeResult);
            }  else {
                reviewPanel.setVisible(false);
                resultLabel.setVisible(true);
                detailsLabel.setVisible(true);

                showQuestionPanel.remove(listOfPanels.get(currentNumber).questionPanel());
                seeTestButton.setText(seeAnswers);

                this.pack();
            }
        }
        if (e.getSource() == backButton) {
            showQuestionPanel.remove(listOfPanels.get(currentNumber).questionPanel());
            currentNumber--;
            showQuestionPanel.add(listOfPanels.get(currentNumber).questionPanel());
            backButton.setEnabled(currentNumber>0);
            forwardButton.setEnabled(currentNumber<listOfPanels.size()-1);
            this.pack();
        }
        if (e.getSource() == forwardButton) {
            showQuestionPanel.remove(listOfPanels.get(currentNumber).questionPanel());
            currentNumber++;
            showQuestionPanel.add(listOfPanels.get(currentNumber).questionPanel());
            backButton.setEnabled(currentNumber>0);
            forwardButton.setEnabled(currentNumber<listOfPanels.size()-1);
            this.pack();
        }

    }



    public static class Builder {
        private JSONConnector connect;
        private int score;
        private int quantityQuestions;
        private Advancement advancement;
        private String[] listOfCategory;
        private List<GraficalElementsOfQuestion> listOfPanels;

        public Builder withConnect (JSONConnector connect){
            this.connect = connect;
            return this;
        }
        public Builder withScore (int score){
            this.score = score;
            return this;
        }
        public Builder withQuantityQuestions (int quantityQuestions){
            this.quantityQuestions = quantityQuestions;
            return this;
        }
        public Builder withAdvancement (Advancement advancement){
            this.advancement = advancement;
            return this;
        }
        public Builder withListOfCategory (String[] listOfCategory){
            this.listOfCategory = listOfCategory;
            return this;
        }
        public Builder withListOfPanels (List<GraficalElementsOfQuestion> listOfPanels){
            this.listOfPanels = listOfPanels;
            return this;
        }
        public ResultWindow build() {
            return new ResultWindow(connect, score, quantityQuestions, advancement, listOfCategory, listOfPanels);
        }

    }
}
