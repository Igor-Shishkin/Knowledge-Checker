package sda.groupProject.knowledgeChecker.graphicalInterface;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    static int WIDTH_PANE = 450;
    static int HEIGHT_PANE = 320;
    JLayeredPane mainPane;
    JPanel greetingPanel, questionPanel, resultPanel;
    JButton startButton, doneButton, nextButton, exitButton;
    JRadioButton levelBasicRadioButton, levelMediumRadioButton, levelExpertRadioButton,
        questionIRadioButton, questionIIRadioButton, questionIIIRadioButton, questionIVRadioButton;
    JComboBox<String> categoriesComboBox;
    JLabel greetingLabel;

    public MainWindow() {


        setGreetingPanel();





        this.setLayout(new GridLayout(1,1,5,5));
        this.add(greetingPanel);

        setFontForComponents(this);

        this.setSize(new Dimension(WIDTH_PANE, HEIGHT_PANE));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("CHECK TESTER");
        this.setVisible(true);
    }

    private void setFontForComponents(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel ||
                    component instanceof JRadioButton || component instanceof JComboBox<?>) {
                component.setFont(ConstantsForStyle.MAIN_FONT);
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
                    "DESIGN_PATTERNS", "SPRING"});

        startButton = new JButton("START!");
        startButton.setFont(new Font(null, Font.BOLD, 30));
        startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.WHITE);

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

        c.gridx = 0;
        c.gridy = 4;
        greetingPanel.add(new JSeparator(), c);

        c.gridx = 0;
        c.gridy = 5;
        greetingPanel.add(categoriesComboBox, c);


        c.insets = new Insets(20,20,20,20);
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 4;
        greetingPanel.add(startButton, c);


    }

}
