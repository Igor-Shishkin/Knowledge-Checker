package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.Advancement;
import sda.groupProject.knowledgeChecker.data.Answer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameWindow extends JFrame implements ActionListener {
    JPanel questionPanel, resultPanel;
    JButton doneButton, nextButton, exitButton;
    List<JRadioButton> answerRadioButtons;
    JLabel questionLabel, isRightAnswer;
    int idCategory;
    Advancement advancement;
    int score;
    ButtonGroup answersGroupButton;
    List<Answer> listOfAnswers;


    GameWindow(int idCategory, Advancement advancement) {
        this.idCategory = idCategory;
        this.advancement = advancement;
        setQuestionPanel();





        this.setLayout(new GridLayout(1,1,5,5));
        this.add(questionPanel);

        setFontForComponents(this);
        System.out.println(advancement);
        System.out.println(idCategory);

//        this.setSize(new Dimension(WIDTH_PANE, HEIGHT_PANE));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("CHECK YOUR KNOWLEDGE");
        this.setVisible(true);
    }

    private void setQuestionPanel() {
        answersGroupButton = new ButtonGroup();

        answerRadioButtons = new ArrayList<>();

        doneButton = new JButton("DONE");
        doneButton.setFont(new Font(null, Font.BOLD, 40));
        doneButton.setBackground(Color.GREEN);
        doneButton.setForeground(Color.WHITE);
        doneButton.addActionListener(this);

        questionLabel = new JLabel("How's everything?");
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.BOLD, 20));

        isRightAnswer = new JLabel();
        isRightAnswer.setVisible(false);
        isRightAnswer.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.BOLD, 35));

        questionPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,5,10,5);
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        questionPanel.add(questionLabel, c);

        listOfAnswers = new ArrayList<>(List.of(
                new Answer("answer1", false, "explanation1"),
                new Answer("answer2", true, "explanation2"),
                new Answer("answer3", false, "explanation3"),
                new Answer("answer4", false, "explanation4"),
                new Answer("answer5", false, "explanation5"),
                new Answer("Very long ANSWER.      Really VERY LONG!", false, "explanation5")   ));

        int lastGridY = 0;
        c.insets = new Insets(3,5,3,5);
        c.gridwidth = 2;
        for (int i = 0; i < listOfAnswers.size(); i++) {
            answerRadioButtons.add(new JRadioButton(listOfAnswers.get(i).text()));
            answersGroupButton.add(answerRadioButtons.get(i));
            c.gridy = i+1;
            questionPanel.add(answerRadioButtons.get(i), c);
            lastGridY = i+1;
        }

        c.insets = new Insets(2,5,2,5);
        c.gridx = 2;
        c.gridy = 1;
        questionPanel.add(isRightAnswer, c);

        c.insets = new Insets(20,20,20,20);
        c.gridx = 2;
        c.gridy = 2;
        c.gridheight = 2;
        questionPanel.add(doneButton, c);


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
        if (e.getSource()==doneButton) {
            for (int i = 0; i < answerRadioButtons.size(); i++) {
                if (answerRadioButtons.get(i).isSelected()) {
                    if (listOfAnswers.get(i).correct()) {
                        questionPanel.setBackground(new Color(0xCDFFD7));
                        isRightAnswer.setText("RIGHT");
                        isRightAnswer.setForeground(Color.GREEN);
                        isRightAnswer.setVisible(true);
                    } else {
                        questionPanel.setBackground(new Color(0xFFDDDD));
                        isRightAnswer.setText("WRONG");
                        isRightAnswer.setForeground(Color.RED);
                        isRightAnswer.setVisible(true);
                    }
                }

            }
        }
    }
}
