package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.Advancement;
import sda.groupProject.knowledgeChecker.data.JSONConnector;
import sda.groupProject.knowledgeChecker.graphicalInterface.listeners.ShowDetailsLabelMouseListener;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ResultWindow extends JFrame implements ActionListener {
    Font MAIN_FONT = new Font("Consolas", Font.PLAIN, 18);
    Color DARK_GREEN = new Color(0x066C00);
    JPanel resultPanel;
    JSONConnector connect;
    String[] listOfCategory;
    int score, quantityQuestions;
    Advancement advancement;
    JButton exitButton, trainButton, detailsButton;


    public ResultWindow(JSONConnector connect, int score, int quantityQuestions,
                        Advancement advancement, String[] listOfCategory) {
        this.connect = connect;
        this.score = score;
        this.quantityQuestions = quantityQuestions;
        this.advancement = advancement;
        this.listOfCategory = listOfCategory;


        setResultPanel();


        this.setLayout(new GridLayout(1, 1, 5, 5));
        this.add(resultPanel);


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
        trainButton.setPreferredSize(new Dimension(100, 40));
        exitButton.addActionListener(this);
        trainButton.addActionListener(this);

        exitButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        exitButton.setForeground(Color.RED);
        trainButton.setBorder(BorderFactory.createLineBorder(DARK_GREEN, 3));
        trainButton.setForeground(DARK_GREEN);
        exitButton.setFont(MAIN_FONT.deriveFont(Font.BOLD, 35));
        trainButton.setFont(MAIN_FONT.deriveFont(Font.BOLD, 35));

        JPanel buttonsPanel = new JPanel(new GridLayout(1,2,5,5));
        buttonsPanel.add(trainButton);
        buttonsPanel.add(exitButton);

        String resultText;
        double percent = (double) score / quantityQuestions * 100;
        resultText = (percent >= 80) ? String.format("<html>You scored %d points out of %d<br>or %3.1f percent.<br>"
                        .concat("This is a great result, congratulations!"),
                score, quantityQuestions, percent)
                : String.format("<html>You scored %d points out of %d<br>or %3.1f percent.<br>"
                        .concat("More work to be done :(<br>Good luck on your next try"),
                score, quantityQuestions, percent);
        JLabel resultLabel = new JLabel(resultText);
        resultLabel.setFont(MAIN_FONT.deriveFont(Font.PLAIN, 30));



        String categories = "";
        for (String category : listOfCategory) {
            categories = categories.concat(category).concat("<br>");
        }
        String detailsText = String.format("<html>Categories:<br> %s<br>Level: %s<br>Quantity of questions: %d</html>",
                categories.substring(0,categories.length()-4), advancement, quantityQuestions);
        JLabel detailsLabel = new JLabel(detailsText);
//        detailsLabel.setVisible(false);
        detailsLabel.setBorder(BorderFactory.createTitledBorder("Details"));
        detailsLabel.setFont(MAIN_FONT.deriveFont(Font.PLAIN, 25));

//        resultLabel.addMouseListener(new ShowDetailsLabelMouseListener(this, detailsLabel, listOfCategory.length,
//                resultPanel));

        resultPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
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
        c.gridy = 4;
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
    }
}
