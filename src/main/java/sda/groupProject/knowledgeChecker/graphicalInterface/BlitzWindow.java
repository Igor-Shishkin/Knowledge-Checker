package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BlitzWindow extends JFrame implements ActionListener {
    private final Font MAIN_FONT = new Font("Consolas", Font.PLAIN, 18);
    private final Color MY_GREEN = new Color(0x0BDC00);
    private final int TIME_FOR_TEST_SECONDS = 180;
    private final int MAX_LENGTH = 65;
    private final int ANSWER_LENGTH = 55;

    private JButton nextButton;
    private double score;
    private double maxScore;
    private int currentNumber = 0;
    private int chosenAnswer;
    private final transient List<Question> listOfQuestions;
    private final GridBagConstraints c = new GridBagConstraints();
    private JProgressBar progressBar;

    private final List<GraphicalElementsOfQuestion> listOfPanels = new ArrayList<>();


    BlitzWindow(JSONConnector connect, List<Question> listOfQuestions) {
        this.listOfQuestions = listOfQuestions;

        setButtonsPanel();
        setProgressBar();

        addNewElementsOfQuestion();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          progressBar.setValue(progressBar.getValue() - 1);

                                          String progress = (progressBar.getValue() > 60)
                                                  ? String.format("%d minutes and %d seconds left", progressBar.getValue() / 60, progressBar.getValue() % 60)
                                                  : String.format("%d seconds left", progressBar.getValue() % 60);
                                          progressBar.setString(progress);
                                          if (progressBar.getValue() == 0) {
                                              listOfPanels.get(currentNumber).questionPanel().remove(progressBar);
                                              nextButton.setVisible(false);

                                              new ResultForBlitz.Builder()
                                                      .withScore(score)
                                                      .withConnect(connect)
                                                      .withListOfPanels(listOfPanels)
                                                      .withMaxScore(maxScore)
                                                      .build();

                                              dispose();
                                              this.cancel();
                                          }
                                      }
                                  }
                , 1000, 1000);

        this.setLayout(new GridLayout(1, 1, 5, 5));
        this.add(listOfPanels.get(currentNumber).questionPanel());

        this.pack();
        this.setLayout(new GridLayout(1, 1, 10, 10));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("CHECK YOUR KNOWLEDGE");
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == nextButton) {

            nextButton.setEnabled(false);

            maxScore = maxScore + getPointForCorrectAnswer();

            if (listOfPanels.get(currentNumber).listAnswersForTheQuestion().get(chosenAnswer).correct()) {
                actionIfAnswerIsCorrect();
            } else {
                actionsIfAnswerIsNotCorrect();
            }

            listOfPanels.get(currentNumber - 1).questionPanel().remove(nextButton);
            listOfPanels.get(currentNumber - 1).questionPanel().remove(progressBar);

            addNewElementsOfQuestion();

            this.remove(listOfPanels.get(currentNumber - 1).questionPanel());
            this.add(listOfPanels.get(currentNumber).questionPanel());
            this.pack();
        }
    }

    private void actionsIfAnswerIsNotCorrect() {
        double points = getPointForWrongAnswer();

        addExplanationComponentsToQuestionPanelWrongAnswer(points);

        changeGraphicalElementsForReviewWrongAnswer();

        score += points;

        currentNumber++;
    }

    private void changeGraphicalElementsForReviewWrongAnswer() {
        listOfPanels.get(currentNumber)
                .answerRadioButtons()
                .get(chosenAnswer)
                .setForeground(Color.RED);

        for (Answer answer : listOfPanels.get(currentNumber).listAnswersForTheQuestion()) {
            if (answer.correct()) {
                listOfPanels.get(currentNumber)
                        .rightExplanation()
                        .setText(HTMLConverter
                                .changeTextToHTML(answer.explanation()
                                        , MAX_LENGTH));
                listOfPanels.get(currentNumber)
                        .answerRadioButtons()
                        .get(listOfPanels
                                .get(currentNumber)
                                .listAnswersForTheQuestion()
                                .indexOf(answer))
                        .setForeground(MY_GREEN);
            }
        }
        listOfPanels.get(currentNumber).answerRadioButtons()
                .get(chosenAnswer)
                .setForeground(Color.RED);
        listOfPanels.get(currentNumber)
                .chosenExplanation()
                .setVisible(true);
        listOfPanels.get(currentNumber)
                .chosenExplanation()
                .setText(HTMLConverter.changeTextToHTML
                        (listOfPanels.get(currentNumber)
                                        .listAnswersForTheQuestion()
                                        .get(chosenAnswer)
                                        .explanation()
                                , MAX_LENGTH));
        listOfPanels.get(currentNumber)
                .explanationPanel()
                .setVisible(true);

        for (int i = 0; i < listOfPanels.get(currentNumber).listAnswersForTheQuestion().size(); i++) {
            listOfPanels.get(currentNumber)
                    .answerRadioButtons()
                    .get(i)
                    .setToolTipText(listOfPanels
                            .get(currentNumber)
                            .listAnswersForTheQuestion()
                            .get(i)
                            .explanation());
        }
        listOfPanels.get(currentNumber).questionPanel().repaint();
        listOfPanels.get(currentNumber).questionPanel().repaint();
    }

    private void addExplanationComponentsToQuestionPanelWrongAnswer(double points) {
        JLabel scoreLabelForTheQuestion = new JLabel(Double.toString(points));
        JLabel levelLabel = new JLabel(listOfPanels
                .get(currentNumber)
                .question()
                .advancement()
                .toString());
        JLabel categoryLabel = new JLabel(listOfPanels
                .get(currentNumber)
                .question()
                .category()
                .categoryName());

        scoreLabelForTheQuestion.setFont(MAIN_FONT.deriveFont(Font.BOLD, 25));
        levelLabel.setFont(MAIN_FONT.deriveFont(Font.BOLD, 25));
        categoryLabel.setFont(MAIN_FONT.deriveFont(Font.BOLD, 25));
        scoreLabelForTheQuestion.setForeground(Color.RED);

        scoreLabelForTheQuestion.setHorizontalAlignment(SwingConstants.RIGHT);
        levelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        categoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        c.gridx = 9;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        listOfPanels.get(currentNumber).questionPanel().add(scoreLabelForTheQuestion, c);

        c.gridy = 3;
        listOfPanels.get(currentNumber).questionPanel().add(levelLabel, c);

        c.gridy = 4;
        listOfPanels.get(currentNumber).questionPanel().add(categoryLabel, c);
    }

    private void actionIfAnswerIsCorrect() {
        double points = getPointForCorrectAnswer();

        addExplanationComponentsToQuestionPanelCorrectAnswer(points);

        changeGraphicalElementsForReviewCorrectAnswer();

        currentNumber++;
        score += points;
    }

    private void changeGraphicalElementsForReviewCorrectAnswer() {
        listOfPanels.get(currentNumber)
                .rightExplanation()
                .setText(HTMLConverter
                        .changeTextToHTML(listOfPanels.get(currentNumber)
                                        .listAnswersForTheQuestion()
                                        .get(chosenAnswer)
                                        .explanation()
                                , MAX_LENGTH));
        listOfPanels.get(currentNumber).explanationPanel().setVisible(true);

        for (int i = 0; i < listOfPanels
                .get(currentNumber)
                .listAnswersForTheQuestion()
                .size();
             i++) {
            listOfPanels.get(currentNumber)
                    .answerRadioButtons()
                    .get(i)
                    .setToolTipText(listOfPanels
                            .get(currentNumber)
                            .listAnswersForTheQuestion()
                            .get(i)
                            .explanation());
        }
        listOfPanels.get(currentNumber)
                .answerRadioButtons()
                .get(chosenAnswer)
                .setForeground(MY_GREEN);
        listOfPanels.get(currentNumber).questionPanel().repaint();
        listOfPanels.get(currentNumber).questionPanel().repaint();
    }

    private void addExplanationComponentsToQuestionPanelCorrectAnswer(double points) {
        JLabel scoreLabelForTheQuestion = new JLabel(Double.toString(points));
        JLabel levelLabel = new JLabel(listOfPanels
                .get(currentNumber)
                .question()
                .advancement()
                .toString());
        JLabel categoryLabel = new JLabel(listOfPanels
                .get(currentNumber)
                .question()
                .category()
                .categoryName());

        scoreLabelForTheQuestion.setFont(MAIN_FONT.deriveFont(Font.BOLD, 25));
        levelLabel.setFont(MAIN_FONT.deriveFont(Font.BOLD, 25));
        categoryLabel.setFont(MAIN_FONT.deriveFont(Font.BOLD, 25));

        scoreLabelForTheQuestion.setHorizontalAlignment(SwingConstants.RIGHT);
        levelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        categoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        scoreLabelForTheQuestion.setForeground(MY_GREEN);

        c.gridx = 9;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        listOfPanels.get(currentNumber).questionPanel().add(scoreLabelForTheQuestion, c);

        c.gridy = 3;
        listOfPanels.get(currentNumber).questionPanel().add(levelLabel, c);

        c.gridy = 4;
        listOfPanels.get(currentNumber).questionPanel().add(categoryLabel, c);
    }

    private double getPointForCorrectAnswer() {
        if (listOfPanels.get(currentNumber).question().advancement() == Advancement.BASIC) {
            return 1;
        }
        if (listOfPanels.get(currentNumber).question().advancement() == Advancement.MEDIUM) {
            return 2;
        }
        return 3;
    }

    private double getPointForWrongAnswer() {
        if (listOfPanels.get(currentNumber).question().advancement() == Advancement.BASIC) {
            return -0.5;
        }
        if (listOfPanels.get(currentNumber).question().advancement() == Advancement.MEDIUM) {
            return -1;
        }
        return -1.5;
    }

    private void setProgressBar() {
        progressBar = new JProgressBar(0, TIME_FOR_TEST_SECONDS);
        progressBar.setValue(TIME_FOR_TEST_SECONDS);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("MV Boli", Font.BOLD, 25));
        progressBar.setForeground(Color.red);
        progressBar.setBackground(Color.black);
        progressBar.setString("START");
    }

    private void addNewElementsOfQuestion() {
        Question question = listOfQuestions.get(currentNumber);
        ButtonGroup answersGroupButton = new ButtonGroup();
        List<JRadioButton> answerRadioButtons = new ArrayList<>();

        JPanel answersPanel = new JPanel(new GridBagLayout());

        List<Answer> listAnswersForTheQuestion = question.answers();
        c.anchor = GridBagConstraints.WEST;
        for (int i = 0; i < listAnswersForTheQuestion.size(); i++) {
            answerRadioButtons
                    .add(new JRadioButton(HTMLConverter
                            .changeTextToHTML(listAnswersForTheQuestion.get(i).text(), ANSWER_LENGTH)));
            int finalI = i;
            answerRadioButtons.get(i).addActionListener(e -> {
                chosenAnswer = finalI;
                nextButton.setEnabled(true);
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
        rightExplanation.setFont(MAIN_FONT.deriveFont(Font.PLAIN, 20));
        chosenExplanation.setFont(MAIN_FONT.deriveFont(Font.PLAIN, 20));
        rightExplanation.setForeground(MY_GREEN);
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
        JLabel codeLabel = new JLabel(HTMLConverter.changeTextToHTML(question.code(), MAX_LENGTH));
        codeLabel.setFont(new Font(null, Font.PLAIN, 20));
        codeLabel.setFont(new Font(null, Font.PLAIN, 20));
        codePanel.add(codeLabel);
        codePanel.setVisible(question.code() != null);

        JLabel questionLabel = new JLabel(HTMLConverter.changeTextToHTML(question.text(), MAX_LENGTH));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setFont(MAIN_FONT.deriveFont(Font.BOLD, 20));

        JPanel questionPanel = new JPanel(new GridBagLayout());

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
        c.gridheight = listAnswersForTheQuestion.size();
        questionPanel.add(nextButton, c);

        c.insets = new Insets(3, 5, 3, 5);
        c.gridx = 0;
        c.gridy = listAnswersForTheQuestion.size() + 4;
        c.gridwidth = 10;
        c.gridheight = 3;
        questionPanel.add(explanationPanel, c);

        c.gridy = listAnswersForTheQuestion.size() + 9;
        c.gridwidth = 10;
        c.gridheight = 1;
        questionPanel.add(progressBar, c);

        //we record the panel with the answers in the listOfPanels so that later it can be viewed in the resulting window
        listOfPanels.add(new GraphicalElementsOfQuestion.Builder()
                .withExplanationPanel(explanationPanel)
                .withListAnswersForTheQuestion(listAnswersForTheQuestion)
                .withQuestionPanel(questionPanel)
                .withAnswersPanel(answersPanel)
                .withQuestion(question)
                .withAnswerRadioButtons(answerRadioButtons)
                .withChosenExplanation(chosenExplanation)
                .withRightExplanation(rightExplanation)
                .build());
    }

    private void setButtonsPanel() {

        nextButton = new JButton("Next");
        nextButton.setFont(new Font(null, Font.BOLD, 35));
        nextButton.setBackground(Color.BLUE);
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(this);
        nextButton.setEnabled(false);

    }

    private void setFontForComponents(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JRadioButton) {
                component.setFont(MAIN_FONT.deriveFont(Font.BOLD, 19));
            }
            if (component instanceof Container innerContainer) {
                setFontForComponents(innerContainer);
            }
        }
    }

}


