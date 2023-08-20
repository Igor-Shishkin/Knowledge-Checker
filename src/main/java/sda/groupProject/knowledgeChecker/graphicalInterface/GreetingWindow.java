package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.Advancement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GreetingWindow extends JFrame implements ActionListener {
    static int CURRENT_LEVEL;
    static String CURRENT_CATEGORY;

    JLayeredPane mainPane;
    JPanel greetingPanel, questionPanel, resultPanel;
    JButton startButton, doneButton, nextButton, exitButton;
    JRadioButton levelBasicRadioButton, levelMediumRadioButton, levelExpertRadioButton,
        questionIRadioButton, questionIIRadioButton, questionIIIRadioButton, questionIVRadioButton;
    JComboBox<String> categoriesComboBox;
    JLabel greetingLabel;

    public GreetingWindow() {


        setGreetingPanel();





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

    private void setGreetingPanel() {
        levelBasicRadioButton = new JRadioButton("BASIC");
        levelMediumRadioButton = new JRadioButton("MEDIUM");
        levelExpertRadioButton = new JRadioButton("EXPERT");

        ButtonGroup levelGroupButton = new ButtonGroup();
        levelGroupButton.add(levelBasicRadioButton);
        levelGroupButton.add(levelMediumRadioButton);
        levelGroupButton.add(levelExpertRadioButton);

        categoriesComboBox = new JComboBox<>(new String[]{"JAVA_LANGUAGE", "GENERAL",
                    "DESIGN_PATTERNS", "SPRING", "ALL"});

        startButton = new JButton("START!");
        startButton.setFont(new Font(null, Font.BOLD, 40));
        startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(this);

        greetingLabel = new JLabel
                ("<html>Greeting!<br>Are you ready to check your knowledge?"
                        .concat("<br>Choose level and category</html>"));
        greetingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        greetingPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        greetingPanel.add(greetingLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        greetingPanel.add(levelBasicRadioButton, c);

        c.gridx = 0;
        c.gridy = 2;
        greetingPanel.add(levelMediumRadioButton, c);

        c.gridx = 0;
        c.gridy = 3;
        greetingPanel.add(levelExpertRadioButton, c);

        c.insets = new Insets(7,5,7,5);
        c.gridx = 0;
        c.gridy = 4;
        greetingPanel.add(new JSeparator(), c);

        c.insets = new Insets(5,5,15,5);
        c.gridx = 0;
        c.gridy = 5;
        greetingPanel.add(categoriesComboBox, c);


        c.insets = new Insets(20,20,20,20);
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 5;
        greetingPanel.add(startButton, c);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==startButton) {
            Advancement advancement = (levelBasicRadioButton.isSelected()) ? Advancement.BASIC
                    : (levelMediumRadioButton.isSelected()) ? Advancement.MEDIUM
                    : Advancement.EXPERT;
            new GameWindow(categoriesComboBox.getSelectedIndex(), advancement);
            dispose();
        }
    }
}
