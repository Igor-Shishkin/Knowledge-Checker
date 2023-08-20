package sda.groupProject.knowledgeChecker.graphicalInterface;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    JPanel greetingPanel, questionPanel, resultPanel;
    JButton startButton, doneButton, nextButton, exitButton;
    JRadioButton questionIRadioButton, questionIIRadioButton, questionIIIRadioButton, questionIVRadioButton;
    JComboBox<String> categoriesComboBox;
    JLabel questionLabel;
    int score;

    GameWindow() {
        setQuestionPanel();





        this.setLayout(new GridLayout(1,1,5,5));
        this.add(greetingPanel);

        setFontForComponents(this);

//        this.setSize(new Dimension(WIDTH_PANE, HEIGHT_PANE));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("CHECK YOUR KNOWLEDGE");
        this.setVisible(true);
    }

    private void setQuestionPanel() {

        questionIRadioButton = new JRadioButton("So so");
        questionIIRadioButton = new JRadioButton("Excellent");
        questionIIIRadioButton = new JRadioButton("It can be better");
        questionIVRadioButton = new JRadioButton("Fine! And you?");


        ButtonGroup answersGroupButton = new ButtonGroup();
        answersGroupButton.add(questionIRadioButton);
        answersGroupButton.add(questionIIRadioButton);
        answersGroupButton.add(questionIIIRadioButton);
        answersGroupButton.add(questionIVRadioButton);

        doneButton = new JButton("DONE");
        doneButton.setFont(new Font(null, Font.BOLD, 40));
        doneButton.setBackground(Color.GREEN);
        doneButton.setForeground(Color.WHITE);

        questionLabel = new JLabel("How's everything?");
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        questionPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,5,10,5);
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        questionPanel.add(questionLabel, c);

        c.insets = new Insets(3,5,3,5);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        questionPanel.add(questionIRadioButton, c);

        c.gridx = 0;
        c.gridy = 2;
        questionPanel.add(questionIIRadioButton, c);

        c.gridx = 0;
        c.gridy = 3;
        questionPanel.add(questionIIIRadioButton, c);

        c.gridx = 0;
        c.gridy = 3;
        questionPanel.add(questionIVRadioButton, c);

        c.insets = new Insets(7,5,7,5);
        c.gridx = 0;
        c.gridy = 4;
        questionPanel.add(new JSeparator(), c);

        c.insets = new Insets(20,20,20,20);
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 4;
        questionPanel.add(doneButton, c);


    }

    private void setFontForComponents(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel || component instanceof JComboBox<?>) {
                component.setFont(ConstantsForStyle.MAIN_FONT);
            }
            if (component instanceof JRadioButton) {
                component.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.BOLD, 19));
            }
            if (component instanceof Container) {
                setFontForComponents((Container) component);
            }
        }
    }
}
