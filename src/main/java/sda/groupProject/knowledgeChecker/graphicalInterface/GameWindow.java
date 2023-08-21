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
    JPanel questionPanel, explanationPanel, answersPanel;
    JSeparator separator;
    JButton doneButton, nextButton, exitButton;
    List<JRadioButton> answerRadioButtons;
    JLabel questionLabel, isRightAnswer, rightExplanation, chosenExplanation;
    Advancement advancement;
    int score, currentNumber = 1, chosenAnswer;
    ButtonGroup answersGroupButton;
    String[] chosenCategory;
    int MAX_NUMBER_OF_QUESTION;
    JSONConnector connect;
    List<Answer> listAnswersForTheQuestion;
    List<Question> listOfQuestions;
    GridBagConstraints c;

    GameWindow(String[] chosenCategory, Advancement advancement, int quantityQuestions, JSONConnector connect) {
        this.connect = connect;
        this.chosenCategory = chosenCategory;
        this.advancement = advancement;
        this.MAX_NUMBER_OF_QUESTION = quantityQuestions;
        setQuestionPanel();


        this.setLayout(new GridLayout(1, 1, 5, 5));
        this.add(questionPanel);

        setFontForComponents(this);

//        this.setSize(new Dimension(WIDTH_PANE, HEIGHT_PANE));
        this.pack();
        this.setLayout(new GridLayout(1,1,10,10));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("CHECK YOUR KNOWLEDGE");
        this.setVisible(true);
    }

    private void setQuestionPanel() {
        listOfQuestions = connect.getListOfQuestions();
        listAnswersForTheQuestion = listOfQuestions.get(0).answers();


        answersGroupButton = new ButtonGroup();
        answerRadioButtons = new ArrayList<>();
        setElementsToAnswerPanel();
        setElementsToExplanationPanel();
        setButtons();

        String text = "<html>".concat(listOfQuestions.get(0).text()).concat("</html>");
        questionLabel = new JLabel(text);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.BOLD, 20));

        isRightAnswer = new JLabel();
        isRightAnswer.setVisible(false);
        isRightAnswer.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.BOLD, 35));

        separator = new JSeparator();
        separator.setVisible(false);

        questionPanel = new JPanel(new GridBagLayout());

        c = new GridBagConstraints();
        c.insets = new Insets(10, 5, 10, 5);
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        questionPanel.add(questionLabel, c);


        c.insets = new Insets(5, 10, 3, 5);
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = listAnswersForTheQuestion.size();
        c.gridwidth = 2;
        questionPanel.add(answersPanel, c);


//        listOfAnswers = new ArrayList<>(List.of(
//                new Answer("answer1", false, "explanation1"),
//                new Answer("answer2", true, "explanation2"),
//                new Answer("answer3", false, "explanation3"),
//                new Answer("answer4", false, "explanation4"),
//                new Answer("answer5", false, "explanation5"),
//                new Answer("Very long ANSWER.      Really VERY LONG!", false, "explanation5")   ));

        c.insets = new Insets(3, 5, 3, 5);
        c.gridx = 0;
        c.gridy = listAnswersForTheQuestion.size() + 1;
        c.gridheight = 1;
        questionPanel.add(separator, c);

        c.insets = new Insets(3, 5, 3, 5);
        c.gridx = 0;
        c.gridwidth = 4;
        c.gridy = listAnswersForTheQuestion.size() + 2;
        c.gridheight = 3;
        questionPanel.add(explanationPanel, c);

        c.insets = new Insets(2, 5, 2, 5);
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        questionPanel.add(isRightAnswer, c);

        c.insets = new Insets(20, 20, 20, 20);
        c.gridx = 2;
        c.gridy = 2;
        c.gridheight = 2;
        questionPanel.add(doneButton, c);

        c.insets = new Insets(20, 20, 20, 20);
        c.gridx = 2;
        c.gridy = 2;
        c.gridheight = 2;
        questionPanel.add(nextButton, c);


    }

    private void setButtons() {
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
    }

    private void setElementsToExplanationPanel() {
        explanationPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        rightExplanation = new JLabel();
        chosenExplanation = new JLabel();
        rightExplanation.setForeground(Color.GREEN);
        chosenExplanation.setForeground(Color.RED);

        explanationPanel.add(rightExplanation);
        explanationPanel.add(chosenExplanation);
        explanationPanel.setBorder(BorderFactory.createTitledBorder("WHY"));
        explanationPanel.setVisible(false);

    }

    private void setElementsToAnswerPanel() {
        answersPanel = new JPanel(new GridLayout(listAnswersForTheQuestion.size(), 1, 5, 5));

        for (int i = 0; i < listAnswersForTheQuestion.size(); i++) {
            answerRadioButtons.add(new JRadioButton(listAnswersForTheQuestion.get(i).text()));
            int finalI = i;
            answerRadioButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chosenAnswer = finalI;
                    doneButton.setEnabled(true);
                }
            });
            answersGroupButton.add(answerRadioButtons.get(i));
            answersPanel.add(answerRadioButtons.get(i));
        }
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
        if (e.getSource() == doneButton) {
            doneButton.setVisible(false);
            doneButton.setEnabled(false);


            if (listAnswersForTheQuestion.get(chosenAnswer).correct()) {
                isRightAnswer.setText("RIGHT");
                isRightAnswer.setForeground(Color.GREEN);
                isRightAnswer.setVisible(true);

                doneButton.setVisible(false);
                nextButton.setVisible(true);
                rightExplanation.setText(listAnswersForTheQuestion.get(chosenAnswer).explanation());
                explanationPanel.setVisible(true);
                separator.setVisible(true);

                for (int i = 0; i < listAnswersForTheQuestion.size(); i++) {
                    answerRadioButtons.get(i).setToolTipText(listAnswersForTheQuestion.get(i).explanation());
                }
                questionPanel.repaint();
                int length = Math.max(
                        answersPanel.getWidth() + doneButton.getWidth(),
                            Math.max(questionLabel.getWidth(), explanationPanel.getWidth())) + 50;
                int height = answersPanel.getHeight() + doneButton.getHeight()+150;
                out.println(length + "   " + height);
                questionPanel.repaint(0,0,length, height);
                this.setSize(length, height);
                this.repaint();
            } else {
                isRightAnswer.setText("WRONG");
                isRightAnswer.setForeground(Color.RED);
                isRightAnswer.setVisible(true);
                answerRadioButtons.get(chosenAnswer).setForeground(Color.RED);
                separator.setVisible(true);

                doneButton.setVisible(false);
                nextButton.setVisible(true);
                for (Answer answer : listAnswersForTheQuestion){
                    if (answer.correct()) {
                        rightExplanation.setText(answer.explanation());
                        answerRadioButtons.get(listAnswersForTheQuestion.indexOf(answer)).setForeground(Color.GREEN);
                    }
                }
                chosenExplanation.setText(listAnswersForTheQuestion.get(chosenAnswer).explanation());
                explanationPanel.setVisible(true);

                for (int i = 0; i < listAnswersForTheQuestion.size(); i++) {
                    answerRadioButtons.get(i).setToolTipText(listAnswersForTheQuestion.get(i).explanation());
                }
                questionPanel.repaint();
                int length = Math.max(
                        answersPanel.getWidth() + doneButton.getWidth(),
                        Math.max(questionLabel.getWidth(), explanationPanel.getWidth())) + 50;
                int height = answersPanel.getHeight() + doneButton.getHeight()+150;
                out.println(length + "   " + height);
//                questionPanel.repaint(0,0,length, height);
                this.setSize(length, height);
                this.repaint();
            }
        }
        if (e.getSource() == nextButton) {
            currentNumber++;
            out.println(1234);
            isRightAnswer.setVisible(false);
            nextButton.setVisible(false);
            doneButton.setVisible(true);
            questionPanel.remove(answersPanel);
            answerRadioButtons.get(chosenAnswer).setForeground(Color.BLACK);
            explanationPanel.setVisible(false);

            for (int i = listAnswersForTheQuestion.size()-1; i >=0; i--) {
                listAnswersForTheQuestion.remove(listAnswersForTheQuestion.get(i));
                answerRadioButtons.remove(answerRadioButtons.get(i));
            }
            Question question = listOfQuestions.get(currentNumber);
            listAnswersForTheQuestion = question.answers();


            out.println(listAnswersForTheQuestion.toString());

            setElementsToAnswerPanel();
            c.insets = new Insets(5, 10, 3, 5);
            c.gridx = 0;
            c.gridy = 1;
            c.gridheight = listAnswersForTheQuestion.size();
            c.gridwidth = 2;
            questionPanel.add(answersPanel, c);

            questionLabel.setText(question.text());

            questionPanel.repaint();
            int length = Math.max(
                    answersPanel.getWidth() + doneButton.getWidth(),
                    Math.max(questionLabel.getWidth(), explanationPanel.getWidth())) + 50;
            int height = answersPanel.getHeight() + doneButton.getHeight()+150;
            out.println(length + "   " + height);
                questionPanel.repaint(0,0,length, height);
            this.setSize(length, height);
            this.repaint();
        }
    }
}
