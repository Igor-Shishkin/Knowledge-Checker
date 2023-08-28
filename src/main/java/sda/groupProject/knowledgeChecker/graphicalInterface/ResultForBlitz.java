package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.Advancement;
import sda.groupProject.knowledgeChecker.data.JSONConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ResultForBlitz extends JFrame implements ActionListener {
    JSONConnector connect;
    List<GraficalElementsOfQuestion> el;
    double score, maxScore;
    int currentNumber;
    JPanel resultPanel, showQuestionPanel, reviewPanel;
    String[] listOfCategory;
    Advancement advancement;
    JButton exitButton, trainButton, seeTestButton, forwardButton, backButton;
    GridBagConstraints c;

    boolean isTimeOver = false;

    public ResultForBlitz(JSONConnector connect, List<GraficalElementsOfQuestion> el, double score, int currentNumber,
                          double maxScore) {
        this.connect = connect;
        this.el = el;
        this.score = score;
        this.currentNumber = currentNumber;
        this.maxScore = maxScore;

        setResultPanel();
        setReviewPanel();


        this.setLayout(new GridLayout(1, 1, 5, 5));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        this.add(scrollPane);


//        this.setSize(new Dimension(WIDTH_PANE, HEIGHT_PANE));
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
        c.gridy = 3;
        c.gridwidth = 3;
        resultPanel.add(reviewPanel, c);
    }

    private void setResultPanel() {
        exitButton = new JButton("EXIT");
        trainButton = new JButton("TRAIN");
        trainButton.setPreferredSize(new Dimension(100, 25));
        exitButton.addActionListener(this);
        trainButton.addActionListener(this);
        seeTestButton = new JButton("See my test");
        seeTestButton.addActionListener(this);



        String resultText;
        double percent = score / maxScore * 100;
        resultText = (percent >= 80) ? String.format(("<html>you answered %d questions<br>"
                        .concat("You scored %3.1f points out of %3.1f<br>or %3.1f percent.<br>")
                        .concat("This is a great result, congratulations!")),
                currentNumber , score, maxScore, percent)
                : String.format(("<html>you answered %d questions<br>"
                        .concat("You scored %3.1f points out of %3.1f<br>or %3.1f percent.<br>")
                        .concat("More work could be done :(<br>Good luck on your next try")),
                currentNumber , score, maxScore, percent);
        JLabel resultLabel = new JLabel(resultText);
        resultLabel.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.PLAIN, 30));



        JPanel buttonsPanel = new JPanel(new GridLayout(1,3,5,5));
        buttonsPanel.add(trainButton);
        buttonsPanel.add(exitButton);
        buttonsPanel.add(seeTestButton);

        resultPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10,10,10,10);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        resultPanel.add(resultLabel, c);


        c.insets = new Insets(10,10,10,10);
        c.gridx = 0;
        c.gridy = 1;
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

            reviewPanel.setVisible(true);

            showQuestionPanel.add(el.get(currentNumber-1).questionPanel());
            this.pack();








//            showQuestionPanel = new JPanel(new GridLayout(el.size(),1,5,5));
//
//            int length = 500;
//            for (GraficalElementsOfQuestion element : el) {
//                JPanel panelForTheQuestion = new JPanel(new GridBagLayout());
//
//                c.gridx = 0;
//                c.gridy = 0;
//                c.gridwidth = 1;
//                c.gridheight = 1;
//                panelForTheQuestion.add(element.questionLabel(), c);
//
//                c.gridx = 0;
//                c.gridy = 1;
//                panelForTheQuestion.add(element.answersPanel(), c);
//
//                length = panelForTheQuestion.getWidth();
//                showQuestionPanel.add(panelForTheQuestion);
//            }
//            JScrollPane scrollPane = new JScrollPane(showQuestionPanel);
//            scrollPane.setPreferredSize(new Dimension(370,length));
//
//            c.gridx = 0;
//            c.gridy = 2;
//            c.gridwidth = 2;
//            resultPanel.add(scrollPane, c);
//
//            this.setSize(length+50, length+50);

        }
    }
}



