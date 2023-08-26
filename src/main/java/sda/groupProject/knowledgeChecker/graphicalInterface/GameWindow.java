package sda.groupProject.knowledgeChecker.graphicalInterface;

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

import static java.lang.System.*;

public class GameWindow extends JFrame implements ActionListener {
    JPanel buttonsPanel, reviewPanel, buttonsReviewPanel;
    //    JPanel explanationPanel, answersPanel, codePanel;
    JSeparator separator1, separator2;
    JButton doneButton, nextButton, forwardButton, backButton;
    //    List<JRadioButton> answerRadioButtons;
    JLabel isRightAnswer;
    //    JLabel questionLabel, rightExplanation, chosenExplanation;
    Advancement advancement;
    int score, currentNumber = 0, chosenAnswer, MAX_NUMBER_OF_QUESTION;
    //    ButtonGroup answersGroupButton;
    String[] chosenCategory;
    JSONConnector connect;
    //    List<Answer> listAnswersForTheQuestion;
    List<Question> listOfQuestions;
    GridBagConstraints c = new GridBagConstraints();
    ;
    JProgressBar progressBar;
    int MAX_LENGTH = 60;
    int ANSWER_LENGTH = 50;
    boolean isEnd = false;
    List<GraficalElementsOfQuestion> el = new ArrayList<>();


    GameWindow(String[] chosenCategory, Advancement advancement, int quantityQuestions, JSONConnector connect,
               List<Question> listOfQuestions) {
        this.connect = connect;
        this.chosenCategory = chosenCategory;
        this.advancement = advancement;
        this.MAX_NUMBER_OF_QUESTION = quantityQuestions;
        this.listOfQuestions = listOfQuestions;

        setButtonsPanel();
        setProgressBar();

        addNewElementsOfQuestion();

        reviewPanel = new JPanel(new GridBagLayout());


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
        progressBar = new JProgressBar(0, MAX_NUMBER_OF_QUESTION);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("MV Boli", Font.BOLD, 25));
        progressBar.setForeground(Color.red);
        progressBar.setBackground(Color.black);
        progressBar.setValue(0);
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
                    doneButton.setEnabled(true);
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
        doneButton = new JButton("DONE");
        doneButton.setFont(new Font(null, Font.BOLD, 35));
        doneButton.setBackground(Color.GREEN);
        doneButton.setForeground(Color.WHITE);
        doneButton.addActionListener(this);
        doneButton.setEnabled(false);

        nextButton = new JButton("Next");
        nextButton.setFont(new Font(null, Font.BOLD, 35));
        nextButton.setBackground(Color.BLUE);
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(this);
        nextButton.setVisible(false);

        ImageIcon forwardIcon = new ImageIcon("src/main/resources/next.png");
        ImageIcon backIcon = new ImageIcon("src/main/resources/previous.png");
        forwardButton = new JButton(forwardIcon);
        forwardButton.setVisible(false);
        forwardButton.addActionListener(this);
        backButton = new JButton(backIcon);
        backButton.setVisible(false);
        backButton.addActionListener(this);

        JPanel nextBackPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        nextBackPanel.add(backButton);
        nextBackPanel.add(forwardButton);


        isRightAnswer = new JLabel();
        isRightAnswer.setVisible(false);
        isRightAnswer.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.BOLD, 35));
        isRightAnswer.setHorizontalAlignment(SwingConstants.CENTER);

        buttonsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonsPanel.add(isRightAnswer);
        buttonsPanel.add(doneButton);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(nextBackPanel);
    }


//    private void setElementsToAnswerPanel() {
//        answersPanel = new JPanel(new GridBagLayout());
//
////            c.anchor = GridBagConstraints.WEST;
//        for (int i = 0; i < listAnswersForTheQuestion.size(); i++) {
//            answerRadioButtons
//                    .add(new JRadioButton(changeTextToHTML(listAnswersForTheQuestion.get(i).text(), ANSWER_LENGTH)  ));
//            int finalI = i;
//            answerRadioButtons.get(i).addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    chosenAnswer = finalI;
//                    doneButton.setEnabled(true);
//                }
//            });
//            answersGroupButton.add(answerRadioButtons.get(i));
//            c.gridx = 0;
//            c.gridy = i;
//            c.gridwidth = 1;
//            c.gridheight = 1;
//            out.println(i);
//            answersPanel.add(answerRadioButtons.get(i), c);
//        }
//        answersPanel.repaint();
//        setFontForComponents(answersPanel);
//    }

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


        if (e.getSource() == doneButton) {
            doneButton.setVisible(false);
            doneButton.setEnabled(false);


            if (el.get(currentNumber).listAnswersForTheQuestion().get(chosenAnswer).correct()) {
                score++;
                out.println(chosenAnswer);
                isRightAnswer.setText("RIGHT");
                isRightAnswer.setForeground(Color.GREEN);
                isRightAnswer.setVisible(true);

                doneButton.setVisible(false);
                nextButton.setVisible(true);
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

                currentNumber++;
                String progress = String.format("DONE: %d from %d", currentNumber, MAX_NUMBER_OF_QUESTION);
                progressBar.setString(progress);
                progressBar.setValue(currentNumber);
                if (currentNumber == MAX_NUMBER_OF_QUESTION) {
                    currentNumber--;
                    nextButton.setText("SEE RESULT");
                    forwardButton.setVisible(true);
                    backButton.setVisible(true);
                }
                this.pack();
            } else {
                isRightAnswer.setText("WRONG");
                isRightAnswer.setForeground(Color.RED);
                isRightAnswer.setVisible(true);
                el.get(currentNumber).answerRadioButtons().get(chosenAnswer).setForeground(Color.RED);
                separator1.setVisible(true);

                doneButton.setVisible(false);
                nextButton.setVisible(true);
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

                currentNumber++;
                String progress = String.format("DONE: %d from %d", currentNumber, MAX_NUMBER_OF_QUESTION);
                progressBar.setString(progress);
                progressBar.setValue(currentNumber);
                if (currentNumber == MAX_NUMBER_OF_QUESTION) {
                    nextButton.setText("SEE RESULT");
                    forwardButton.setVisible(true);
                    backButton.setVisible(true);
                    currentNumber--;
                    forwardButton.setEnabled(false);
                }
                this.pack();
            }
        }
        if (e.getSource() == nextButton) {
            if (currentNumber >= MAX_NUMBER_OF_QUESTION || isEnd) {
                nextButton.setText("SEE RESULT");
                new ResultWindow(connect, score, MAX_NUMBER_OF_QUESTION, advancement, chosenCategory);
                dispose();
            } else {
                isRightAnswer.setVisible(false);
                nextButton.setVisible(false);
                doneButton.setVisible(true);

                addNewElementsOfQuestion();

                this.remove(el.get(currentNumber - 1).scrollPane());
                this.add(el.get(currentNumber).scrollPane());
                this.pack();
            }
        }
        if (e.getSource() == forwardButton) {

            reviewPanel.remove(el.get(currentNumber).scrollPane());
            currentNumber++;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = 1;
            reviewPanel.add(el.get(currentNumber).scrollPane(), c);

            forwardButton.setEnabled(currentNumber < MAX_NUMBER_OF_QUESTION - 1);
            backButton.setEnabled(true);
            progressBar.setValue(currentNumber+1);

            this.pack();
        }
        if (e.getSource() == backButton) {

            if (!isEnd) {
                this.remove(el.get(currentNumber).scrollPane());

                buttonsReviewPanel = new JPanel(new GridLayout(1,3,10,10));
                buttonsReviewPanel.add(backButton);
                buttonsReviewPanel.add(nextButton);
                buttonsReviewPanel.add(forwardButton);

                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 1;
                c.gridheight = 1;
                reviewPanel.add(el.get(currentNumber).scrollPane(), c);

                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 1;
                c.gridheight = 1;
                reviewPanel.add(buttonsReviewPanel, c);

                c.gridx = 0;
                c.gridy = 2;
                c.gridwidth = 1;
                c.gridheight = 1;
                reviewPanel.add(progressBar, c);

                this.add(reviewPanel);
                isEnd = true;
            }

            reviewPanel.remove(el.get(currentNumber).scrollPane());
            currentNumber--;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = 1;
            reviewPanel.add(el.get(currentNumber).scrollPane(), c);

            forwardButton.setEnabled(true);
            backButton.setEnabled(currentNumber > 0);
            progressBar.setValue(currentNumber+1);

            this.pack();
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
