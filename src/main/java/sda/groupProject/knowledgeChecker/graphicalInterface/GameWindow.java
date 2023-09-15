package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameWindow extends JFrame implements ActionListener {
    private final Font MAIN_FONT = new Font("Consolas", Font.PLAIN, 18);
    private final Color DARK_GREEN = new Color(0x066C00);
    private final int MAX_NUMBER_OF_QUESTION;
    private final int MAX_LENGTH = 60;
    private final int ANSWER_LENGTH = 50;
    private JPanel buttonsPanel;
    private JButton doneButton;
    private JButton nextButton;
    private JLabel isRightAnswer;
    private final Advancement advancement;
    private int score;
    private int currentNumber = 0;
    private int chosenAnswer;
    private final String[] chosenCategory;
    private final transient JSONConnector connect;
    private final transient List<Question> listOfQuestions;
    private final GridBagConstraints c = new GridBagConstraints();
    private JProgressBar progressBar;

    private final List<GraphicalElementsOfQuestion> listOfPanels = new ArrayList<>();


    private GameWindow(String[] chosenCategory, Advancement advancement, int quantityQuestions, JSONConnector connect,
               List<Question> listOfQuestions) {
        this.connect = connect;
        this.chosenCategory = chosenCategory;
        this.advancement = advancement;
        this.MAX_NUMBER_OF_QUESTION = quantityQuestions;
        this.listOfQuestions = listOfQuestions;

        setButtonsPanel();
        setProgressBar();

        addNewElementsOfQuestionToEL();

        this.setLayout(new GridLayout(1, 1, 5, 5));
        this.add(listOfPanels.get(currentNumber).scrollPane());

        this.pack();
        this.setLayout(new GridLayout(1, 1, 10, 10));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("CHECK YOUR KNOWLEDGE");
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == doneButton) {
            actionIfDoneButtonIsClicked();

            boolean isCorrectAnswer = listOfPanels.get(currentNumber).listAnswersForTheQuestion().get(chosenAnswer).correct();
            if (isCorrectAnswer) {

                actionUfAnswerIsCorrect();

            } else {
                actionUfAnswerIsWrong();
            }
            currentNumber++;
            if (currentNumber == MAX_NUMBER_OF_QUESTION) {
                nextButton.setText("SEE RESULT");
            }
            this.pack();
        }
        if (e.getSource() == nextButton) {
            if (currentNumber >= MAX_NUMBER_OF_QUESTION) {
                actionForEndOfGame();
            } else {
                actionForNextQuestion();
            }
        }
    }

    private void actionForNextQuestion() {
        isRightAnswer.setVisible(false);
        nextButton.setVisible(false);
        doneButton.setVisible(true);

        addNewElementsOfQuestionToEL();

        this.remove(listOfPanels.get(currentNumber - 1).scrollPane());
        this.add(listOfPanels.get(currentNumber).scrollPane());
        this.pack();
    }

    private void actionForEndOfGame() {
        nextButton.setVisible(false);
        listOfPanels.get(currentNumber-1)
                .questionPanel().remove(progressBar);
        new ResultWindow.Builder()
                .withAdvancement(advancement)
                .withConnect(connect)
                .withScore(score)
                .withQuantityQuestions(MAX_NUMBER_OF_QUESTION)
                .withListOfCategory(chosenCategory)
                .withListOfPanels(listOfPanels)
                .build();
        dispose();
    }

    private void actionIfDoneButtonIsClicked() {
        doneButton.setVisible(false);
        doneButton.setEnabled(false);
        isRightAnswer.setVisible(true);
        nextButton.setVisible(true);
        listOfPanels.get(currentNumber).explanationPanel().setVisible(true);

        String progress = String.format("DONE: %d from %d", currentNumber+1, MAX_NUMBER_OF_QUESTION);
        progressBar.setString(progress);
        progressBar.setValue(currentNumber+1);
    }

    private void actionUfAnswerIsWrong() {
        isRightAnswer.setText("WRONG");
        isRightAnswer.setForeground(Color.RED);

        listOfPanels.get(currentNumber).answerRadioButtons().get(chosenAnswer).setForeground(Color.RED);

        for (Answer answer : listOfPanels.get(currentNumber).listAnswersForTheQuestion()) {
            if (answer.correct()) {
                //set explanation for correct answer
                listOfPanels.get(currentNumber).rightExplanation()
                        .setText(HTMLConverter.changeTextToHTML(answer.explanation(), MAX_LENGTH));
                //set foreground for correct answer
                listOfPanels.get(currentNumber).answerRadioButtons()
                        .get(listOfPanels.get(currentNumber).listAnswersForTheQuestion().indexOf(answer))
                        .setForeground(DARK_GREEN);
            }
        }
        //set foreground for chosen answer
        listOfPanels.get(currentNumber).answerRadioButtons()
                .get(chosenAnswer)
                .setForeground(Color.RED);
        listOfPanels.get(currentNumber).chosenExplanation().setVisible(true);
        listOfPanels.get(currentNumber)
                .chosenExplanation()
                .setText(HTMLConverter.changeTextToHTML
                        (listOfPanels.get(currentNumber)
                                        .listAnswersForTheQuestion()
                                        .get(chosenAnswer)
                                        .explanation(),
                                MAX_LENGTH));


        for (int i = 0; i < listOfPanels.get(currentNumber).listAnswersForTheQuestion().size(); i++) {
            listOfPanels.get(currentNumber)
                    .answerRadioButtons().get(i)
                    .setToolTipText(listOfPanels
                            .get(currentNumber)
                            .listAnswersForTheQuestion()
                            .get(i)
                            .explanation());
        }
    }

    private void actionUfAnswerIsCorrect() {
        score++;
        isRightAnswer.setText("RIGHT");
        isRightAnswer.setForeground(DARK_GREEN);


        listOfPanels.get(currentNumber)
                .rightExplanation()
                .setText(HTMLConverter.changeTextToHTML(listOfPanels
                                .get(currentNumber)
                                .listAnswersForTheQuestion()
                                .get(chosenAnswer)
                                .explanation()
                        , MAX_LENGTH));

        for (int i = 0; i < listOfPanels.get(currentNumber).listAnswersForTheQuestion().size(); i++) {
            listOfPanels.get(currentNumber)
                    .answerRadioButtons()
                    .get(i)
                    .setToolTipText(listOfPanels.get(currentNumber)
                            .listAnswersForTheQuestion()
                            .get(i)
                            .explanation());
        }
        listOfPanels.get(currentNumber).answerRadioButtons()
                .get(chosenAnswer)
                .setForeground(DARK_GREEN);
    }


    private void setProgressBar() {
        progressBar = new JProgressBar(0, MAX_NUMBER_OF_QUESTION);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("MV Boli", Font.BOLD, 25));
        progressBar.setForeground(Color.red);
        progressBar.setBackground(Color.black);
        progressBar.setValue(0);
        progressBar.setString("DONE: 0 from " + listOfQuestions.size());
    }

    private void addNewElementsOfQuestionToEL() {
        Question question = listOfQuestions.get(currentNumber);
        ButtonGroup answersGroupButton = new ButtonGroup();
        List<JRadioButton> answerRadioButtons = new ArrayList<>();

        JPanel answersPanel = new JPanel(new GridBagLayout());

        List<Answer> listAnswersForTheQuestion = question.answers();
        c.anchor = GridBagConstraints.WEST;
        for (int i = 0; i < listAnswersForTheQuestion.size(); i++) {
            answerRadioButtons
                    .add(new JRadioButton(HTMLConverter.changeTextToHTML(listAnswersForTheQuestion.get(i).text(), ANSWER_LENGTH)));
            int finalI = i;
            answerRadioButtons.get(i).addActionListener(e -> {
                chosenAnswer = finalI;
                doneButton.setEnabled(true);
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
        rightExplanation.setForeground(DARK_GREEN);
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
        codePanel.add(codeLabel);
        codeLabel.setFont(new Font(null, Font.PLAIN, 20));
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
        questionPanel.add(buttonsPanel, c);

        c.insets = new Insets(3, 5, 3, 5);
        c.gridx = 0;
        c.gridy = listAnswersForTheQuestion.size() + 3;
        c.gridwidth = 10;
        c.gridheight = 3;
        questionPanel.add(explanationPanel, c);

        c.gridy = listAnswersForTheQuestion.size() + 7;
        c.gridwidth = 10;
        c.gridheight = 1;
        questionPanel.add(progressBar, c);

        JScrollPane scrollPane = new JScrollPane(questionPanel);

        listOfPanels.add(new GraphicalElementsOfQuestion(question,
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
        doneButton = new JButton("DONE");
        doneButton.setFont(new Font(null, Font.BOLD, 35));
        doneButton.setBackground(DARK_GREEN);
        doneButton.setForeground(Color.WHITE);
        doneButton.addActionListener(this);
        doneButton.setEnabled(false);

        nextButton = new JButton("Next");
        nextButton.setFont(new Font(null, Font.BOLD, 35));
        nextButton.setBackground(Color.BLUE);
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(this);
        nextButton.setVisible(false);

        isRightAnswer = new JLabel();
        isRightAnswer.setVisible(false);
        isRightAnswer.setFont(MAIN_FONT.deriveFont(Font.BOLD, 35));
        isRightAnswer.setHorizontalAlignment(SwingConstants.CENTER);

        buttonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonsPanel.add(isRightAnswer);
        buttonsPanel.add(doneButton);
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





    public static class Builder {
        private JSONConnector connect;
        private int quantityQuestions;
        private Advancement advancement;
        private String[] chosenCategory;
        private List<Question> listOfQuestions;

        public Builder withConnect (JSONConnector connect){
            this.connect = connect;
            return this;
        }
        public Builder withQuantityQuestions (int quantityQuestions){
            this.quantityQuestions = quantityQuestions;
            return this;
        }
        public Builder withChosenCategory (String[] chosenCategory){
            this.chosenCategory = chosenCategory;
            return this;
        }
        public Builder withAdvancement (Advancement advancement){
            this.advancement = advancement;
            return this;
        }
        public Builder withListOfQuestions (List<Question> listOfQuestions){
            this.listOfQuestions = listOfQuestions;
            return this;
        }
        public GameWindow build() {
            return new GameWindow(chosenCategory, advancement, quantityQuestions, connect, listOfQuestions);
        }
    }
}
