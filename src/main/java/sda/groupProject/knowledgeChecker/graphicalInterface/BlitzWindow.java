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
    private final int TIME_FOR_TEST_SECONDS = 20;
    private final int MAX_LENGTH = 60;
    private final int ANSWER_LENGTH = 50;

    private JPanel buttonsPanel;
    private JSeparator separator1;
    private JButton nextButton;
    private double score;
    private double maxScore;
    private int currentNumber = 0;
    private int chosenAnswer;
    private final transient JSONConnector connect;
    private final transient List<Question> listOfQuestions;
    private final GridBagConstraints c = new GridBagConstraints();
    private JProgressBar progressBar;

    private final List<GraficalElementsOfQuestion> el = new ArrayList<>();


    BlitzWindow(JSONConnector connect,  List<Question> listOfQuestions) {
        this.connect = connect;
        this.listOfQuestions = listOfQuestions;

        setButtonsPanel();
        setProgressBar();

        addNewElementsOfQuestion();

        Timer timer = new Timer();
        TimerTask repeatedTask = new TimerForProgressBar(progressBar, el, nextButton, currentNumber);
        timer.scheduleAtFixedRate(repeatedTask, 1000,1000);

        this.setLayout(new GridLayout(1, 1, 5, 5));
        this.add(el.get(currentNumber).scrollPane());

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

            if (nextButton.getText().equals("<html>TIME IS<br>OVER</html>")) {
                el.get(currentNumber).questionPanel().remove(progressBar);
                new ResultForBlitz(connect, el, score, currentNumber, maxScore);
                nextButton.setVisible(false);
                dispose();

            } else {

                nextButton.setEnabled(false);

                double maxPoints = getPointForCorrectAnswer();
                maxScore = maxScore + maxPoints;

                if (el.get(currentNumber).listAnswersForTheQuestion().get(chosenAnswer).correct()) {
                    actionIfAnswerIsCorrect();
                } else {
                    actionsIfAnswerIsNotCorrect();
                }

                el.get(currentNumber - 1).questionPanel().remove(buttonsPanel);
                el.get(currentNumber - 1).questionPanel().remove(progressBar);

                addNewElementsOfQuestion();

                this.remove(el.get(currentNumber - 1).scrollPane());
                this.add(el.get(currentNumber).scrollPane());
                this.pack();
            }

        }
    }

    private void actionsIfAnswerIsNotCorrect() {
        double points = getPointForWrongAnswer();

        JLabel scoreLabelForTheQuestion = new JLabel(Double.toString(points));
        JLabel levelLabel = new JLabel(el.get(currentNumber).question().advancement().toString());
        JLabel categoryLabel = new JLabel(el.get(currentNumber).question().category().categoryName());
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
        el.get(currentNumber).questionPanel().add(scoreLabelForTheQuestion, c);

        c.gridy = 3;
        el.get(currentNumber).questionPanel().add(levelLabel, c);

        c.gridy = 4;
        el.get(currentNumber).questionPanel().add(categoryLabel, c);

        score += points;

        el.get(currentNumber).answerRadioButtons().get(chosenAnswer).setForeground(Color.RED);
        separator1.setVisible(true);

        for (Answer answer : el.get(currentNumber).listAnswersForTheQuestion()) {
            if (answer.correct()) {
                el.get(currentNumber).rightExplanation()
                        .setText(HTMLConverter.changeTextToHTML(answer.explanation(), MAX_LENGTH));
                el.get(currentNumber).answerRadioButtons()
                        .get(el.get(currentNumber).listAnswersForTheQuestion().indexOf(answer))
                        .setForeground(MY_GREEN);
            }
        }
        el.get(currentNumber).answerRadioButtons()
                .get(chosenAnswer)
                .setForeground(Color.RED);
        el.get(currentNumber).chosenExplanation().setVisible(true);
        el.get(currentNumber).chosenExplanation().setText(HTMLConverter.changeTextToHTML
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

    private void actionIfAnswerIsCorrect() {
        double points = getPointForCorrectAnswer();

        addExplanationComponentsToQuestionPanel(points);



        score += points;

        el.get(currentNumber).rightExplanation().setText(HTMLConverter.changeTextToHTML
                (el.get(currentNumber).listAnswersForTheQuestion().get(chosenAnswer).explanation(), MAX_LENGTH));
        el.get(currentNumber).explanationPanel().setVisible(true);
        separator1.setVisible(true);

        for (int i = 0; i < el.get(currentNumber).listAnswersForTheQuestion().size(); i++) {
            el.get(currentNumber).answerRadioButtons().get(i)
                    .setToolTipText(el.get(currentNumber).listAnswersForTheQuestion().get(i).explanation());
        }
        el.get(currentNumber).answerRadioButtons()
                .get(chosenAnswer)
                .setForeground(MY_GREEN);
        el.get(currentNumber).questionPanel().repaint();
        el.get(currentNumber).scrollPane().repaint();

        currentNumber++;
    }

    private void addExplanationComponentsToQuestionPanel(double points) {
        JLabel scoreLabelForTheQuestion = new JLabel(Double.toString(points));
        JLabel levelLabel = new JLabel(el.get(currentNumber).question().advancement().toString());
        JLabel categoryLabel = new JLabel(el.get(currentNumber).question().category().categoryName());

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
        el.get(currentNumber).questionPanel().add(scoreLabelForTheQuestion, c);

        c.gridy = 3;
        el.get(currentNumber).questionPanel().add(levelLabel, c);

        c.gridy = 4;
        el.get(currentNumber).questionPanel().add(categoryLabel, c);
    }

    private double getPointForCorrectAnswer() {
        if (el.get(currentNumber).question().advancement() == Advancement.BASIC) {
            return 1;
        }
        if (el.get(currentNumber).question().advancement() == Advancement.MEDIUM) {
            return 2;
        }
        return 3;
    }

    private double getPointForWrongAnswer() {
        if (el.get(currentNumber).question().advancement() == Advancement.BASIC) {
            return -0.5;
        }
        if (el.get(currentNumber).question().advancement() == Advancement.MEDIUM) {
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

        separator1 = new JSeparator();
        separator1.setVisible(false);

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
        c.gridheight = listAnswersForTheQuestion.size()-1;
        questionPanel.add(buttonsPanel, c);

        c.insets = new Insets(3, 5, 3, 5);
        c.gridx = 0;
        c.gridy = listAnswersForTheQuestion.size() + 3;
        c.gridheight = 1;
        c.gridwidth = 10;
        questionPanel.add(separator1, c);

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

        buttonsPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        buttonsPanel.add(nextButton);
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


