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
    private final Color DARK_GREEN = new Color(0x066C00);
    private final JSONConnector connect;
    private JLabel resultLabel;
    private final List<GraficalElementsOfQuestion> el;
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
        reviewPanel.setVisible(false);

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
        double percent = score / maxScore * 100;
        resultText = (percent >= 80) ? String.format(("<html>you answered %d questions<br>"
                        .concat("You scored %3.1f points out of %3.1f<br>or %3.1f percent.<br>")
                        .concat("This is a great result, congratulations!")),
                currentNumber , score, maxScore, percent)
                : String.format(("<html>you answered %d questions<br>"
                        .concat("You scored %3.1f points out of %3.1f<br>or %3.1f percent.<br>")
                        .concat("More work could be done :(<br>Good luck on your next try")),
                currentNumber , score, maxScore, percent);
        resultLabel = new JLabel(resultText);
        resultLabel.setFont(MAIN_FONT.deriveFont(Font.PLAIN, 30));



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

            if (seeTestButton.getText().equals(seeAnswers)) {

                reviewPanel.setVisible(true);
                resultLabel.setVisible(false);

                if (currentNumber == el.size()) {
                    currentNumber--;
                }
                forwardButton.setEnabled(currentNumber<el.size()-1);
                showQuestionPanel.add(el.get(currentNumber).questionPanel());
                this.pack();

                String seeResult = "SEE MY RESULT";
                seeTestButton.setText(seeResult);
            }  else {
                reviewPanel.setVisible(false);
                resultLabel.setVisible(true);

                showQuestionPanel.remove(el.get(currentNumber).questionPanel());
                seeTestButton.setText(seeAnswers);

                this.pack();
            }
        }
        if (e.getSource() == backButton) {
            showQuestionPanel.remove(el.get(currentNumber).questionPanel());
            currentNumber--;
            showQuestionPanel.add(el.get(currentNumber).questionPanel());
            backButton.setEnabled(currentNumber>0);
            forwardButton.setEnabled(currentNumber<el.size()-1);
            this.pack();
        }
        if (e.getSource() == forwardButton) {
            showQuestionPanel.remove(el.get(currentNumber).questionPanel());
            currentNumber++;
            showQuestionPanel.add(el.get(currentNumber).questionPanel());
            backButton.setEnabled(currentNumber>0);
            forwardButton.setEnabled(currentNumber<el.size()-1);
            this.pack();
        }

    }
}



