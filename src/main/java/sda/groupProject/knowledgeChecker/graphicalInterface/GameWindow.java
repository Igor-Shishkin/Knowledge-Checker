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
    JSeparator separator1, separator2;
    JButton doneButton, nextButton, exitButton;
    List<JRadioButton> answerRadioButtons;
    JLabel questionLabel, isRightAnswer, rightExplanation, chosenExplanation;
    Advancement advancement;
    int score, currentNumber = 0, chosenAnswer,  MAX_NUMBER_OF_QUESTION;
    ButtonGroup answersGroupButton;
    String[] chosenCategory;
    JSONConnector connect;
    List<Answer> listAnswersForTheQuestion;
    List<Question> listOfQuestions;
    GridBagConstraints c;
    JProgressBar progressBar;

    GameWindow(String[] chosenCategory, Advancement advancement, int quantityQuestions, JSONConnector connect) {
        this.connect = connect;
        this.chosenCategory = chosenCategory;
        this.advancement = advancement;
        this.MAX_NUMBER_OF_QUESTION = quantityQuestions;

        setQuestionPanel();


        this.setLayout(new GridLayout(1, 1, 5, 5));
        JScrollPane scrollPane = new JScrollPane(questionPanel);
        this.add(scrollPane);

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

        if (MAX_NUMBER_OF_QUESTION>listOfQuestions.size()) {
            MAX_NUMBER_OF_QUESTION = listOfQuestions.size();
            String message = String.format("There are only %d questions for the parameter you have chosen.",
                    MAX_NUMBER_OF_QUESTION);
            JOptionPane.showMessageDialog(this,
                    message, "Warning", JOptionPane.ERROR_MESSAGE);
        }


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

        separator1 = new JSeparator();
        separator1.setVisible(false);

        separator2 = new JSeparator();
        separator2.setVisible(true);

        progressBar = new JProgressBar(0, MAX_NUMBER_OF_QUESTION);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("MV Boli",Font.BOLD,25));
        progressBar.setForeground(Color.red);
        progressBar.setBackground(Color.black);
        progressBar.setValue(0);
        progressBar.setString("DONE: 0 from "+listOfQuestions.size());


        questionPanel = new JPanel(new GridBagLayout());

        c = new GridBagConstraints();
        c.insets = new Insets(10, 5, 10, 5);
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 10;
        questionPanel.add(questionLabel, c);

        c.insets = new Insets(10, 10, 10, 5);
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = listAnswersForTheQuestion.size();
        c.gridwidth = 9;
        questionPanel.add(answersPanel, c);

        c.insets = new Insets(3, 5, 3, 5);
        c.gridx = 0;
        c.gridy = listAnswersForTheQuestion.size()+1;
        c.gridheight = 1;
        c.gridwidth = 10;
        questionPanel.add(separator1, c);

        c.insets = new Insets(3, 5, 3, 5);
        c.gridx = 0;
        c.gridy = listAnswersForTheQuestion.size()+2;
        c.gridwidth = 10;
        c.gridheight = 3;
        questionPanel.add(explanationPanel, c);

        c.gridx = 0;
        c.gridy = listAnswersForTheQuestion.size()+6;
        c.gridwidth = 10;
        c.gridheight = 1;
        questionPanel.add(separator2, c);

        c.gridy = listAnswersForTheQuestion.size()+7;
        c.gridwidth = 10;
        c.gridheight = 1;
        questionPanel.add(progressBar, c);

        c.insets = new Insets(2, 5, 2, 5);
        c.gridx = 9;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 2;
        questionPanel.add(isRightAnswer, c);

        c.insets = new Insets(20, 20, 20, 20);
        c.gridx = 9;
        c.gridy = 3;
        c.gridheight = 2;
        questionPanel.add(doneButton, c);

        c.insets = new Insets(20, 20, 20, 20);
        c.gridx = 9;
        c.gridy = 3;
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
        rightExplanation.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.PLAIN, 20));
        chosenExplanation.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.PLAIN, 20));


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

            setFontForComponents(answersPanel);
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
            currentNumber++;
            progressBar.setValue(currentNumber);
            String progress = String.format("DONE: %d from %d", currentNumber, MAX_NUMBER_OF_QUESTION);
            progressBar.setString(progress);

            if (listAnswersForTheQuestion.get(chosenAnswer).correct()) {
                score++;
                isRightAnswer.setText("RIGHT");
                isRightAnswer.setForeground(Color.GREEN);
                isRightAnswer.setVisible(true);

                doneButton.setVisible(false);
                nextButton.setVisible(true);
                rightExplanation.setText(listAnswersForTheQuestion.get(chosenAnswer).explanation());
                explanationPanel.setVisible(true);
                separator1.setVisible(true);

                for (int i = 0; i < listAnswersForTheQuestion.size(); i++) {
                    answerRadioButtons.get(i).setToolTipText(listAnswersForTheQuestion.get(i).explanation());
                }
                questionPanel.repaint();
                int length = Math.max(
                        answersPanel.getWidth() + nextButton.getWidth(),
                        Math.max(questionLabel.getWidth(), explanationPanel.getWidth())) + 150;
                int height = answersPanel.getHeight() + explanationPanel.getHeight() +
                        questionLabel.getHeight() + progressBar.getHeight() +230;
                out.println(length + "   " + height);
//                questionPanel.repaint(0,0,length, height);
                this.setSize(length, height);
//                this.repaint(0,0,length,height);
            } else {
                isRightAnswer.setText("WRONG");
                isRightAnswer.setForeground(Color.RED);
                isRightAnswer.setVisible(true);
                answerRadioButtons.get(chosenAnswer).setForeground(Color.RED);
                separator1.setVisible(true);

                doneButton.setVisible(false);
                nextButton.setVisible(true);
                for (Answer answer : listAnswersForTheQuestion){
                    if (answer.correct()) {
                        rightExplanation.setText(answer.explanation());
                        answerRadioButtons.get(listAnswersForTheQuestion.indexOf(answer)).setForeground(Color.GREEN);
                    }
                }
                chosenExplanation.setVisible(true);
                chosenExplanation.setText(listAnswersForTheQuestion.get(chosenAnswer).explanation());
                explanationPanel.setVisible(true);

                for (int i = 0; i < listAnswersForTheQuestion.size(); i++) {
                    answerRadioButtons.get(i).setToolTipText(listAnswersForTheQuestion.get(i).explanation());
                }
                questionPanel.repaint();
                int length = Math.max(
                        answersPanel.getWidth() + nextButton.getWidth(),
                        Math.max(questionLabel.getWidth(), explanationPanel.getWidth())) + 150;
                int height = answersPanel.getHeight() + explanationPanel.getHeight() +
                        questionLabel.getHeight() + progressBar.getHeight() +250;
                out.println(length + "   " + height);
//                questionPanel.repaint(0,0,length, height);
                this.setSize(length, height);
//                this.repaint(0,0,length,height);
            }
        }
        if (e.getSource() == nextButton) {
            isRightAnswer.setVisible(false);
            nextButton.setVisible(false);
            doneButton.setVisible(true);
            questionPanel.remove(answersPanel);
            answerRadioButtons.get(chosenAnswer).setForeground(Color.BLACK);
            explanationPanel.setVisible(false);
            chosenExplanation.setVisible(false);
            separator1.setVisible(false);


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
            c.gridwidth = 9;
            questionPanel.add(answersPanel, c);

            questionLabel.setText(question.text());

            questionPanel.repaint();
            int length = Math.max(
                    answersPanel.getWidth() + nextButton.getWidth(),
                    Math.max(questionLabel.getWidth(), explanationPanel.getWidth())) + 150;
            int height = answersPanel.getHeight() + explanationPanel.getHeight() +
                    questionLabel.getHeight() + progressBar.getHeight() +150;
            out.println(length + "   " + height);
//                questionPanel.repaint(0,0,length, height);
            this.setSize(length, height);
        }
    }
}
