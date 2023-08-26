package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.Main;
import sda.groupProject.knowledgeChecker.data.Advancement;
import sda.groupProject.knowledgeChecker.data.Answer;
import sda.groupProject.knowledgeChecker.data.JSONConnector;
import sda.groupProject.knowledgeChecker.data.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.System.*;

public class BlitzWindow extends JFrame implements ActionListener {
    int TIME_FOR_TEST = 20;
    JPanel buttonsPanel, reviewPanel;
    JSeparator separator1, separator2;
    JButton nextButton;
    JLabel isRightAnswer;
    double score, maxScore;
    int currentNumber = 0, chosenAnswer;
    JSONConnector connect;
    List<Question> listOfQuestions;
    GridBagConstraints c = new GridBagConstraints();
    JProgressBar progressBar;
    int MAX_LENGTH = 60;
    int ANSWER_LENGTH = 50;
    boolean isEnd = false;
    List<GraficalElementsOfQuestion> el = new ArrayList<>();


    BlitzWindow(JSONConnector connect,  List<Question> listOfQuestions) {
        this.connect = connect;
        this.listOfQuestions = listOfQuestions;

        setButtonsPanel();
        setProgressBar();

        addNewElementsOfQuestion();

        reviewPanel = new JPanel(new GridBagLayout());

        Timer timer = new Timer();
        TimerTask repeatedTask = new TimerForProgressBar(progressBar, el, nextButton, currentNumber);
        timer.scheduleAtFixedRate(repeatedTask, 1000,1000);


        this.setLayout(new GridLayout(1, 1, 5, 5));
        this.add(el.get(currentNumber).scrollPane());

//        this.setSize(new Dimension(WIDTH_PANE, HEIGHT_PANE));
        this.pack();
        this.setLayout(new GridLayout(1, 1, 10, 10));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("CHECK YOUR KNOWLEDGE");
        this.setVisible(true);
    }

    private void setProgressBar() {
        progressBar = new JProgressBar(0, TIME_FOR_TEST);
        progressBar.setValue(TIME_FOR_TEST);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("MV Boli", Font.BOLD, 25));
        progressBar.setForeground(Color.red);
        progressBar.setBackground(Color.black);
        progressBar.setString("DONE: 0 from " + listOfQuestions.size());
    }

    private void addNewElementsOfQuestion() {
        Question question = listOfQuestions.get(currentNumber);
        ButtonGroup answersGroupButton = new ButtonGroup();
        List<JRadioButton> answerRadioButtons = new ArrayList<>();
//        setElementsToExplanationPanel();


        String text = changeTextToHTML(listOfQuestions.get(0).text(), MAX_LENGTH);
//        JLabel questionLabel = new JLabel(text);


        JPanel answersPanel = new JPanel(new GridBagLayout());

        List<Answer> listAnswersForTheQuestion = question.answers();
        c.anchor = GridBagConstraints.WEST;
        for (int i = 0; i < listAnswersForTheQuestion.size(); i++) {
            answerRadioButtons
                    .add(new JRadioButton(changeTextToHTML(listAnswersForTheQuestion.get(i).text(), ANSWER_LENGTH)));
            int finalI = i;
            answerRadioButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chosenAnswer = finalI;
                    nextButton.setEnabled(true);
                    out.println("Hello");
                }
            });
            answersGroupButton.add(answerRadioButtons.get(i));
            c.gridx = 0;
            c.gridy = i;
            c.gridwidth = 1;
            c.gridheight = 1;
            answersPanel.add(answerRadioButtons.get(i), c);
        }
        answersPanel.repaint();
        setFontForComponents(answersPanel);

        JPanel explanationPanel = new JPanel(new GridBagLayout());

        JLabel rightExplanation = new JLabel();
        JLabel chosenExplanation = new JLabel();
        rightExplanation.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.PLAIN, 20));
        chosenExplanation.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.PLAIN, 20));
        rightExplanation.setForeground(Color.green);
        chosenExplanation.setForeground(Color.red);

        c.gridx = 0;
        c.gridy = 0;
        explanationPanel.add(rightExplanation, c);

        c.gridx = 0;
        c.gridy = 1;
        explanationPanel.add(chosenExplanation, c);

        explanationPanel.setBorder(BorderFactory.createTitledBorder("WHY"));
        explanationPanel.setVisible(false);

        JPanel codePanel = new JPanel(new GridLayout(1, 1, 5, 5));
        codePanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        JLabel codeLabel = new JLabel(changeTextToHTML(question.code(), MAX_LENGTH));
        codePanel.add(codeLabel);
        codePanel.setVisible(question.code() != null);

        JLabel questionLabel = new JLabel(changeTextToHTML(question.text(), MAX_LENGTH));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.BOLD, 20));



        JPanel questionPanel = new JPanel(new GridBagLayout());

        separator1 = new JSeparator();
        separator1.setVisible(false);

        separator2 = new JSeparator();
        separator2.setVisible(true);

        c.insets = new Insets(10, 5, 10, 5);
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 10;
        questionPanel.add(questionLabel, c);

        c.insets = new Insets(6, 10, 6, 5);
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 9;
        questionPanel.add(codePanel, c);

        c.insets = new Insets(6, 10, 6, 5);
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = listAnswersForTheQuestion.size();
        c.gridwidth = 9;
        questionPanel.add(answersPanel, c);

        c.insets = new Insets(2, 5, 2, 5);
        c.gridx = 9;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = listAnswersForTheQuestion.size() - 1;
        questionPanel.add(buttonsPanel, c);

        c.insets = new Insets(3, 5, 3, 5);
        c.gridx = 0;
        c.gridy = listAnswersForTheQuestion.size() + 1;
        c.gridheight = 1;
        c.gridwidth = 10;
        questionPanel.add(separator1, c);

        c.insets = new Insets(3, 5, 3, 5);
        c.gridx = 0;
        c.gridy = listAnswersForTheQuestion.size() + 2;
        c.gridwidth = 10;
        c.gridheight = 3;
        questionPanel.add(explanationPanel, c);

        c.gridx = 0;
        c.gridy = listAnswersForTheQuestion.size() + 6;
        c.gridwidth = 10;
        c.gridheight = 1;
        questionPanel.add(separator2, c);

        c.gridy = listAnswersForTheQuestion.size() + 7;
        c.gridwidth = 10;
        c.gridheight = 1;
        questionPanel.add(progressBar, c);

        JScrollPane scrollPane = new JScrollPane(questionPanel);

        el.add(new GraficalElementsOfQuestion(question,
                explanationPanel,
                answersPanel,
                codePanel,
                answerRadioButtons,
                questionLabel,
                rightExplanation,
                chosenExplanation,
                answersGroupButton,
                listAnswersForTheQuestion,
                questionPanel,
                scrollPane));
    }

    private void setButtonsPanel() {

        nextButton = new JButton("Next");
        nextButton.setFont(new Font(null, Font.BOLD, 35));
        nextButton.setBackground(Color.BLUE);
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(this);
        nextButton.setEnabled(false);

        isRightAnswer = new JLabel();
        isRightAnswer.setVisible(false);
        isRightAnswer.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.BOLD, 35));
        isRightAnswer.setHorizontalAlignment(SwingConstants.CENTER);

        buttonsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonsPanel.add(isRightAnswer);
        buttonsPanel.add(nextButton);
    }

    private void setFontForComponents(Container container) {
        for (Component component : container.getComponents()) {
//            if (component instanceof JLabel || component instanceof JComboBox<?>) {
//                component.setFont(ConstantsForStyle.MAIN_FONT);
//            }
            if (component instanceof JRadioButton) {
                component.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.BOLD, 19));
            }
            if (component instanceof Container) {
                setFontForComponents((Container) component);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String textForScoreLabel = "";
        if (e.getSource() == nextButton) {
            if (nextButton.getText().equals("<html>TIME IS<br>OVER</html>")) {
                new ResultForBlitz(connect, el, score, currentNumber, maxScore);
                nextButton.setEnabled(false);
                dispose();

            } else {
                int maxPoints = (el.get(currentNumber).question().advancement() == Advancement.BASIC) ? 1
                        : (el.get(currentNumber).question().advancement() == Advancement.MEDIUM) ? 2
                        : 3;
                maxScore = maxScore + maxPoints;
                if (el.get(currentNumber).listAnswersForTheQuestion().get(chosenAnswer).correct()) {

                    int points = (el.get(currentNumber).question().advancement() == Advancement.BASIC) ? 1
                            : (el.get(currentNumber).question().advancement() == Advancement.MEDIUM) ? 2
                            : 3;

                    textForScoreLabel = score + " + " + points;
                    isRightAnswer.setText(textForScoreLabel);
                    isRightAnswer.setForeground(Color.GREEN);
                    isRightAnswer.setVisible(true);

                    score += points;

                    el.get(currentNumber).rightExplanation().setText(changeTextToHTML
                            (el.get(currentNumber).listAnswersForTheQuestion().get(chosenAnswer).explanation(), MAX_LENGTH));
                    el.get(currentNumber).explanationPanel().setVisible(true);
                    separator1.setVisible(true);

                    for (int i = 0; i < el.get(currentNumber).listAnswersForTheQuestion().size(); i++) {
                        el.get(currentNumber).answerRadioButtons().get(i)
                                .setToolTipText(el.get(currentNumber).listAnswersForTheQuestion().get(i).explanation());
                    }
                    el.get(currentNumber).answerRadioButtons()
                            .get(chosenAnswer)
                            .setForeground(Color.GREEN);
                    el.get(currentNumber).questionPanel().repaint();
                    el.get(currentNumber).scrollPane().repaint();

                    currentNumber++;


                } else {

                    double points = (el.get(currentNumber).question().advancement() == Advancement.BASIC) ? -0.5
                            : (el.get(currentNumber).question().advancement() == Advancement.MEDIUM) ? -1
                            : -1.5;

                    textForScoreLabel = score + " - " + Math.abs(points);
                    isRightAnswer.setText(textForScoreLabel);
                    isRightAnswer.setForeground(Color.RED);
                    isRightAnswer.setVisible(true);

                    score += points;

                    el.get(currentNumber).answerRadioButtons().get(chosenAnswer).setForeground(Color.RED);
                    separator1.setVisible(true);

                    for (Answer answer : el.get(currentNumber).listAnswersForTheQuestion()) {
                        if (answer.correct()) {
                            el.get(currentNumber).rightExplanation()
                                    .setText(changeTextToHTML(answer.explanation(), MAX_LENGTH));
                            el.get(currentNumber).answerRadioButtons()
                                    .get(el.get(currentNumber).listAnswersForTheQuestion().indexOf(answer))
                                    .setForeground(Color.GREEN);
                        }
                    }
                    el.get(currentNumber).answerRadioButtons()
                            .get(chosenAnswer)
                            .setForeground(Color.RED);
                    el.get(currentNumber).chosenExplanation().setVisible(true);
                    el.get(currentNumber).chosenExplanation().setText(changeTextToHTML
                            (el.get(currentNumber).listAnswersForTheQuestion()
                                    .get(chosenAnswer).explanation(), MAX_LENGTH));
                    el.get(currentNumber).explanationPanel().setVisible(true);

                    for (int i = 0; i < el.get(currentNumber).listAnswersForTheQuestion().size(); i++) {
                        el.get(currentNumber).answerRadioButtons().get(i)
                                .setToolTipText(el.get(currentNumber).listAnswersForTheQuestion().get(i).explanation());
                    }
                    el.get(currentNumber).questionPanel().repaint();
                    el.get(currentNumber).scrollPane().repaint();
                    currentNumber++;
                }

                el.get(currentNumber - 1).questionPanel().remove(buttonsPanel);
                el.get(currentNumber - 1).questionPanel().remove(separator1);
                el.get(currentNumber - 1).questionPanel().remove(separator2);
                el.get(currentNumber - 1).questionPanel().remove(progressBar);

                addNewElementsOfQuestion();

                this.remove(el.get(currentNumber - 1).scrollPane());
                this.add(el.get(currentNumber).scrollPane());
                this.pack();
            }

        }
    }

    public String changeTextToHTML(String text, int lineLength) {
        if (text == null || text.isEmpty() || lineLength <= 0) {
            return text;
        }

        StringBuilder result = new StringBuilder("<html>");
        int startIndex = 0;

        while (startIndex < text.length()) {
            int endIndex = Math.min(startIndex + lineLength, text.length());
            String chunk = text.substring(startIndex, endIndex);

            if (endIndex < text.length()) {
                int lastSpaceIndex = chunk.lastIndexOf(' ');

                if (lastSpaceIndex != -1) {
                    endIndex = startIndex + lastSpaceIndex;
                    chunk = chunk.substring(0, lastSpaceIndex);
                }
            }

            result.append(chunk).append("<br>");
            startIndex = endIndex + 1;
        }

        result.append("</html>");
        return result.toString();
    }

}


