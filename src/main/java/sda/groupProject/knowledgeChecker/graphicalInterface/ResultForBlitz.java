package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.Advancement;
import sda.groupProject.knowledgeChecker.data.JSONConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ResultForBlitz extends JFrame implements ActionListener {
    JSONConnector connect;
    List<GraficalElementsOfQuestion> el;
    double score, maxScore;
    int currentNumber;
    JPanel resultPanel, showTestPanel;
    String[] listOfCategory;
    Advancement advancement;
    JButton exitButton, trainButton, seeTestButton;
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

    private void setResultPanel() {
        exitButton = new JButton("EXIT");
        trainButton = new JButton("TRAIN");
        trainButton.setPreferredSize(new Dimension(100, 25));
        exitButton.addActionListener(this);
        trainButton.addActionListener(this);
        seeTestButton = new JButton("See my test");
        seeTestButton.addActionListener(this);

        String resultText;
        double percent = (double) score / maxScore * 100;
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
        c.fill = GridBagConstraints.VERTICAL;
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
            showTestPanel = new JPanel(new GridBagLayout());

            int i = 3;
            for (GraficalElementsOfQuestion element : el) {
                c.gridx = 0;
                c.gridy = i;
                c.gridwidth = 2;
                c.gridheight = 1;
                resultPanel.add(element.questionLabel());
                i++;

                c.gridx = 0;
                c.gridy = i;
                c.gridwidth = 2;
                c.gridheight = 1;
                resultPanel.add(element.answersPanel());
                i++;
                System.out.println(i);
            }
//            JScrollPane scrollPane = new JScrollPane(showTestPanel);
//            scrollPane.setPreferredSize(new Dimension(500,500));



            this.setSize(800, 800);

        }
    }
}



