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
    JPanel questionPanel, buttonsPanel;
//    JPanel explanationPanel, answersPanel, codePanel;
    JSeparator separator1, separator2;
    JButton doneButton, nextButton, exitButton;
//    List<JRadioButton> answerRadioButtons;
    JLabel  isRightAnswer;
//    JLabel questionLabel, rightExplanation, chosenExplanation;
    Advancement advancement;
    int score, currentNumber = 0, chosenAnswer, MAX_NUMBER_OF_QUESTION;
//    ButtonGroup answersGroupButton;
    String[] chosenCategory;
    JSONConnector connect;
//    List<Answer> listAnswersForTheQuestion;
    List<Question> listOfQuestions;
    GridBagConstraints c = new GridBagConstraints();;
    JProgressBar progressBar;
    int MAX_LENGTH = 60;
    int ANSWER_LENGTH = 50;
    List<GraficalElementsOfQuestion> el = new ArrayList<>();


    GameWindow(String[] chosenCategory, Advancement advancement, int quantityQuestions, JSONConnector connect,
               List<Question> listOfQuestions) {
        this.connect = connect;
        this.chosenCategory = chosenCategory;
        this.advancement = advancement;
        this.MAX_NUMBER_OF_QUESTION = quantityQuestions;
        this.listOfQuestions = listOfQuestions;

        setElementsOfQuestion();
        setButtonsPanel();


        setQuestionPanel();


        this.setLayout(new GridLayout(1, 1, 5, 5));
        JScrollPane scrollPane = new JScrollPane(questionPanel);
        this.add(scrollPane);

//        this.setSize(new Dimension(WIDTH_PANE, HEIGHT_PANE));
        this.pack();
        this.setLayout(new GridLayout(1, 1, 10, 10));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("CHECK YOUR KNOWLEDGE");
        this.setVisible(true);
    }

    private void setElementsOfQuestion() {
        Question question = listOfQuestions.get(currentNumber);
        ButtonGroup answersGroupButton = new ButtonGroup();
        List<JRadioButton> answerRadioButtons = new ArrayList<>();
//        setElementsToExplanationPanel();
        setButtonsPanel();

        String text = changeTextToHTML(listOfQuestions.get(0).text(), MAX_LENGTH);
//        JLabel questionLabel = new JLabel(text);


        JPanel answersPanel = new JPanel(new GridBagLayout());

        List<Answer> listAnswersForTheQuestion = question.answers();
            c.anchor = GridBagConstraints.WEST;
        for (int i = 0; i < listAnswersForTheQuestion.size(); i++) {
            answerRadioButtons
                    .add(new JRadioButton(changeTextToHTML(listAnswersForTheQuestion.get(i).text(), ANSWER_LENGTH)  ));
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
        rightExplanation.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.PLAIN, 20));
        chosenExplanation.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.PLAIN, 20));

        c.gridx = 0;
        c.gridy = 0;
        explanationPanel.add(rightExplanation,c);

        c.gridx = 0;
        c.gridy = 1;
        explanationPanel.add(chosenExplanation,c);

        explanationPanel.setBorder(BorderFactory.createTitledBorder("WHY"));
        explanationPanel.setVisible(false);

        JPanel codePanel = new JPanel(new GridLayout(1,1,5,5));
        codePanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        JLabel codeLabel = new JLabel(changeTextToHTML(question.code(), MAX_LENGTH));
        codePanel.add(codeLabel);
        if (question.code()==null) { codePanel.setVisible(false); } else  { codePanel.setVisible(true);}

        JLabel questionLabel = new JLabel(changeTextToHTML(question.text(), MAX_LENGTH));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.BOLD, 20));

        el.add(new GraficalElementsOfQuestion(question,
                explanationPanel,
                answersPanel,
                codePanel,
                answerRadioButtons,
                questionLabel,
                rightExplanation,
                chosenExplanation,
                answersGroupButton,
                listAnswersForTheQuestion));

    }

    private void setQuestionPanel() {




        separator1 = new JSeparator();
        separator1.setVisible(false);

        separator2 = new JSeparator();
        separator2.setVisible(true);

        progressBar = new JProgressBar(0, MAX_NUMBER_OF_QUESTION);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("MV Boli", Font.BOLD, 25));
        progressBar.setForeground(Color.red);
        progressBar.setBackground(Color.black);
        progressBar.setValue(0);
        progressBar.setString("DONE: 0 from " + listOfQuestions.size());


        questionPanel = new JPanel(new GridBagLayout());

        c.insets = new Insets(10, 5, 10, 5);
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 10;
        questionPanel.add(el.get(currentNumber).questionLabel(), c);

        c.insets = new Insets(6, 10, 6, 5);
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 9;
        questionPanel.add(el.get(currentNumber).codePanel(), c);

        c.insets = new Insets(6, 10, 6, 5);
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = el.get(currentNumber).listAnswersForTheQuestion().size();
        c.gridwidth = 9;
        questionPanel.add(el.get(currentNumber).answersPanel(), c);

        c.insets = new Insets(3, 5, 3, 5);
        c.gridx = 0;
        c.gridy = el.get(currentNumber).listAnswersForTheQuestion().size() + 1;
        c.gridheight = 1;
        c.gridwidth = 10;
        questionPanel.add(separator1, c);

        c.insets = new Insets(3, 5, 3, 5);
        c.gridx = 0;
        c.gridy = el.get(currentNumber).listAnswersForTheQuestion().size() + 2;
        c.gridwidth = 10;
        c.gridheight = 3;
        questionPanel.add(el.get(currentNumber).explanationPanel(), c);

        c.gridx = 0;
        c.gridy = el.get(currentNumber).listAnswersForTheQuestion().size() + 6;
        c.gridwidth = 10;
        c.gridheight = 1;
        questionPanel.add(separator2, c);

        c.gridy = el.get(currentNumber).listAnswersForTheQuestion().size() + 7;
        c.gridwidth = 10;
        c.gridheight = 1;
        questionPanel.add(progressBar, c);

        c.insets = new Insets(2, 5, 2, 5);
        c.gridx = 9;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = el.get(currentNumber).listAnswersForTheQuestion().size()-1;
        questionPanel.add(buttonsPanel, c);

//        c.insets = new Insets(20, 20, 20, 20);
//        c.gridx = 9;
//        c.gridy = 3;
//        c.gridheight = 2;
//        questionPanel.add(doneButton, c);
//
//        c.insets = new Insets(20, 20, 20, 20);
//        c.gridx = 9;
//        c.gridy = 3;
//        c.gridheight = 2;
//        questionPanel.add(nextButton, c);


    }

    private void setButtonsPanel() {
        doneButton = new JButton("DONE");
        doneButton.setFont(new Font(null, Font.BOLD, 40));
        doneButton.setBackground(Color.GREEN);
        doneButton.setForeground(Color.WHITE);
        doneButton.addActionListener(this);
        doneButton.setEnabled(false);

        nextButton = new JButton("Next");
        nextButton.setBackground(Color.BLUE);
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(this);
        nextButton.setVisible(false);

        isRightAnswer = new JLabel();
        isRightAnswer.setVisible(false);
        isRightAnswer.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.BOLD, 35));
        isRightAnswer.setHorizontalAlignment(SwingConstants.CENTER);

        buttonsPanel = new JPanel(new GridLayout(3,1,10,10));
        buttonsPanel.add(isRightAnswer);
        buttonsPanel.add(doneButton);
        buttonsPanel.add(nextButton);
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
            out.println(listOfQuestions.get(currentNumber).category());
            out.println(listOfQuestions.get(currentNumber).advancement());
            doneButton.setVisible(false);
            doneButton.setEnabled(false);
            if (currentNumber == MAX_NUMBER_OF_QUESTION) {
                nextButton.setText("SEE RESULT");
            }
            progressBar.setValue(currentNumber);
            String progress = String.format("DONE: %d from %d", currentNumber, MAX_NUMBER_OF_QUESTION);
            progressBar.setString(progress);

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
                questionPanel.repaint();
                this.pack();
                currentNumber++;
            } else {
                out.println(chosenAnswer);

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
                                .setText(changeTextToHTML(answer.explanation(),MAX_LENGTH));
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
                questionPanel.repaint();
                this.pack();
                currentNumber++;
            }
        }
        if (e.getSource() == nextButton) {
            if (currentNumber >= MAX_NUMBER_OF_QUESTION) {
//                nextButton.setText("SEE RESULT");
//                new ResultWindow(connect, score, MAX_NUMBER_OF_QUESTION, advancement, chosenCategory);
//                dispose();
//            } else {
//                isRightAnswer.setVisible(false);
//                nextButton.setVisible(false);
//                doneButton.setVisible(true);
//                questionPanel.remove(el.get(currentNumber).answersPanel());
//                el.get(currentNumber).answerRadioButtons.get(chosenAnswer).setForeground(Color.BLACK);
//                explanationPanel.setVisible(false);
//                chosenExplanation.setVisible(false);
//                separator1.setVisible(false);
//
//
//                for (int i = listAnswersForTheQuestion.size() - 1; i >= 0; i--) {
//                    listAnswersForTheQuestion.remove(listAnswersForTheQuestion.get(i));
//                    answerRadioButtons.remove(answerRadioButtons.get(i));
//                }
//                Question question = listOfQuestions.get(currentNumber);
//                listAnswersForTheQuestion = question.answers();
//
////                out.println(listAnswersForTheQuestion.toString());
//
//                setElementsToAnswerPanel();
//                c.insets = new Insets(2, 10, 3, 5);
//                c.gridx = 0;
//                c.gridy = 1;
//                c.gridheight = listAnswersForTheQuestion.size();
//                c.gridwidth = 9;
//                questionPanel.add(answersPanel, c);
//
//                questionPanel.remove(explanationPanel);
//                questionPanel.remove(separator2);
//                questionPanel.remove(progressBar);
//
//
//                c.insets = new Insets(3, 5, 3, 5);
//                c.gridx = 0;
//                c.gridy = listAnswersForTheQuestion.size() + 2;
//                c.gridwidth = 10;
//                c.gridheight = 3;
//                questionPanel.add(explanationPanel, c);
//
//                c.gridx = 0;
//                c.gridy = listAnswersForTheQuestion.size() + 6;
//                c.gridwidth = 10;
//                c.gridheight = 1;
//                questionPanel.add(separator2, c);
//
//                c.gridy = listAnswersForTheQuestion.size() + 7;
//                c.gridwidth = 10;
//                c.gridheight = 1;
//                questionPanel.add(progressBar, c);
//
//                questionLabel.setText(changeTextToHTML(question.text(),MAX_LENGTH));
//                questionPanel.repaint();
//                this.pack();
            }
        }
    }
    public static String changeTextToHTML(String text, int lineLength) {
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
