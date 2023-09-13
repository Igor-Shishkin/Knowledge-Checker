package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

public class GameWindow extends JFrame implements ActionListener {
    private final Font MAIN_FONT = new Font("Consolas", Font.PLAIN, 18);
    private final Color DARK_GREEN = new Color(0x066C00);
    private final int MAX_NUMBER_OF_QUESTION;
    private final int MAX_LENGTH = 60;
    private final int ANSWER_LENGTH = 50;
    private JPanel buttonsPanel;
    private JSeparator separator1;
    private JSeparator separator2;
    private JButton doneButton;
    private JButton nextButton;
    private JLabel isRightAnswer;
    private final Advancement advancement;
    private int score;
    private int currentNumber = 0;
    private int chosenAnswer;
    private final String[] chosenCategory;
    private final JSONConnector connect;
    private final List<Question> listOfQuestions;
    private final GridBagConstraints c = new GridBagConstraints();
    private JProgressBar progressBar;

    private final List<GraficalElementsOfQuestion> el = new ArrayList<>();


    GameWindow(String[] chosenCategory, Advancement advancement, int quantityQuestions, JSONConnector connect,
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
        this.add(el.get(currentNumber).scrollPane());

        this.pack();
        this.setLayout(new GridLayout(1, 1, 10, 10));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("CHECK YOUR KNOWLEDGE");
        this.setVisible(true);
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
            answerRadioButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chosenAnswer = finalI;
                    doneButton.setEnabled(true);
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
        codePanel.setVisible(question.code() != null);

        JLabel questionLabel = new JLabel(HTMLConverter.changeTextToHTML(question.text(), MAX_LENGTH));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setFont(MAIN_FONT.deriveFont(Font.BOLD, 20));

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

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == doneButton) {
            doneButton.setVisible(false);
            doneButton.setEnabled(false);
            isRightAnswer.setVisible(true);
            nextButton.setVisible(true);
            el.get(currentNumber).explanationPanel().setVisible(true);

            String progress = String.format("DONE: %d from %d", currentNumber+1, MAX_NUMBER_OF_QUESTION);
            progressBar.setString(progress);
            progressBar.setValue(currentNumber+1);

            boolean isCorrectAnswer = el.get(currentNumber).listAnswersForTheQuestion().get(chosenAnswer).correct();
            if (isCorrectAnswer) {

                score++;
                isRightAnswer.setText("RIGHT");
                isRightAnswer.setForeground(DARK_GREEN);


                el.get(currentNumber)
                        .rightExplanation()
                        .setText(HTMLConverter.changeTextToHTML(el
                                .get(currentNumber)
                                .listAnswersForTheQuestion()
                                .get(chosenAnswer)
                                .explanation()
                                    , MAX_LENGTH));
                separator1.setVisible(true);

                for (int i = 0; i < el.get(currentNumber).listAnswersForTheQuestion().size(); i++) {
                    el.get(currentNumber)
                            .answerRadioButtons()
                            .get(i)
                            .setToolTipText(el.get(currentNumber)
                                    .listAnswersForTheQuestion()
                                    .get(i)
                                    .explanation());
                }
                el.get(currentNumber).answerRadioButtons()
                        .get(chosenAnswer)
                        .setForeground(DARK_GREEN);

                currentNumber++;
                if (currentNumber == MAX_NUMBER_OF_QUESTION) {
                    nextButton.setText("SEE RESULT");
                }
                this.pack();
            } else {
                isRightAnswer.setText("WRONG");
                isRightAnswer.setForeground(Color.RED);

                el.get(currentNumber).answerRadioButtons().get(chosenAnswer).setForeground(Color.RED);
                separator1.setVisible(true);

                for (Answer answer : el.get(currentNumber).listAnswersForTheQuestion()) {
                    if (answer.correct()) {
                        //set explanation for correct answer
                        el.get(currentNumber).rightExplanation()
                                .setText(HTMLConverter.changeTextToHTML(answer.explanation(), MAX_LENGTH));
                        //set foreground for correct answer
                        el.get(currentNumber).answerRadioButtons()
                                .get(el.get(currentNumber).listAnswersForTheQuestion().indexOf(answer))
                                .setForeground(DARK_GREEN);
                    }
                }
                //set foreground for chosen answer
                el.get(currentNumber).answerRadioButtons()
                        .get(chosenAnswer)
                        .setForeground(Color.RED);
                el.get(currentNumber).chosenExplanation().setVisible(true);
                el.get(currentNumber)
                        .chosenExplanation()
                        .setText(HTMLConverter.changeTextToHTML
                                (el.get(currentNumber)
                                                .listAnswersForTheQuestion()
                                                .get(chosenAnswer)
                                                .explanation(),
                                        MAX_LENGTH));


                for (int i = 0; i < el.get(currentNumber).listAnswersForTheQuestion().size(); i++) {
                    el.get(currentNumber)
                            .answerRadioButtons().get(i)
                            .setToolTipText(el
                                    .get(currentNumber)
                                    .listAnswersForTheQuestion()
                                    .get(i)
                                    .explanation());
                }

                currentNumber++;

                if (currentNumber == MAX_NUMBER_OF_QUESTION) {
                    nextButton.setText("SEE RESULT");
                }
                this.pack();
            }
        }
        if (e.getSource() == nextButton) {
            if (currentNumber >= MAX_NUMBER_OF_QUESTION) {
                new ResultWindow.Builder()
                        .withAdvancement(advancement)
                        .withConnect(connect)
                        .withScore(score)
                        .withQuantityQuestions(MAX_NUMBER_OF_QUESTION)
                        .withListOfCategory(chosenCategory)
                        .withListOfPanels(el)
                        .build();

                dispose();
            } else {
                isRightAnswer.setVisible(false);
                nextButton.setVisible(false);
                doneButton.setVisible(true);

                addNewElementsOfQuestionToEL();

                this.remove(el.get(currentNumber - 1).scrollPane());
                this.add(el.get(currentNumber).scrollPane());
                this.pack();
            }
        }
    }
}
