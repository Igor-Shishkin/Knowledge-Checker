package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.Advancement;
import sda.groupProject.knowledgeChecker.data.JSONConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GreetingWindow extends JFrame implements ActionListener {
    static int CURRENT_LEVEL;
    static String CURRENT_CATEGORY;

    JLayeredPane mainPane;
    JPanel greetingPanel, categoryPanel, levelPanel, quantityQuestionPanel;
    JButton startButton, doneButton, nextButton, exitButton;
     JRadioButton levelBasicRadioButton, levelMediumRadioButton, levelExpertRadioButton,
        quantityQuestion5RadioButton, quantityQuestion10RadioButton, quantityQuestion15RadioButton;
    List<JCheckBox> categoriesCheckBoxList;
    JLabel greetingLabel;
    JSONConnector connect;
    String[] listOfCategory;
    boolean isLevelChosen = false, isCategoryChosen = false, isQuantityChosen = false;

    public GreetingWindow(JSONConnector connect) {
        this.connect = connect;


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
            if (component instanceof JCheckBox) {
                component.setFont(ConstantsForStyle.MAIN_FONT.deriveFont(Font.BOLD, 19));
            }
            if (component instanceof Container) {
                setFontForComponents((Container) component);
            }
        }
    }

    private void setGreetingPanel() {

        setComponentsForLevelPanel();
        setComponentsForQuantityQuestionsPanel();
        setComponentsForCategoryPanel();


//        categoriesComboBox = new JComboBox<>(new String[]{"JAVA_LANGUAGE", "GENERAL",
//                    "DESIGN_PATTERNS", "SPRING", "ALL"});

        startButton = new JButton("START!");
        startButton.setFont(new Font(null, Font.BOLD, 40));
        startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(this);
        startButton.setEnabled(false);

        greetingLabel = new JLabel
                ("<html>Greeting!<br>Are you ready to check your knowledge?"
                        .concat("<br>Choose level, category and how many questions<br>would you like</html>"));
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
        c.gridheight = listOfCategory.length;
        greetingPanel.add(categoryPanel, c);

        c.gridx = 2;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 3;
        greetingPanel.add(levelPanel, c);

;       c.gridx = 2;
        c.gridy = 2;
        greetingPanel.add(quantityQuestionPanel, c);


        c.insets = new Insets(20,20,20,20);
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 2;
        c.gridheight = (listOfCategory.length>4) ? listOfCategory.length - 2 : 3;
        greetingPanel.add(startButton, c);


    }

    private void setComponentsForCategoryPanel() {
        listOfCategory = connect.getCategoryNames();
        categoryPanel = new JPanel(new GridLayout(listOfCategory.length, 1,5,5));
        categoryPanel.setBorder(BorderFactory.createTitledBorder("Choose categories"));

        System.out.println(Arrays.toString(listOfCategory));
        categoriesCheckBoxList = new ArrayList<>();

        for (int i = 0; i < listOfCategory.length; i++) {
            categoriesCheckBoxList.add(new JCheckBox(listOfCategory[i]));
            categoriesCheckBoxList.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isCategoryChosen=false;
                    System.out.println("category");
                    for (JCheckBox checkBox : categoriesCheckBoxList) {
                        if (checkBox.isSelected()) { isCategoryChosen=true; }
                    }
                    startButton.setEnabled(isCategoryChosen && isLevelChosen && isQuantityChosen);
                }
            });
            categoryPanel.add(categoriesCheckBoxList.get(i));
        }
    }

    private void setComponentsForQuantityQuestionsPanel() {
        quantityQuestion5RadioButton = new JRadioButton("5");
        quantityQuestion10RadioButton = new JRadioButton("10");
        quantityQuestion15RadioButton = new JRadioButton("15");

        quantityQuestion15RadioButton.addActionListener(this);
        quantityQuestion5RadioButton.addActionListener(this);
        quantityQuestion10RadioButton.addActionListener(this);

        ButtonGroup quantityGroupButton = new ButtonGroup();
        quantityGroupButton.add(quantityQuestion5RadioButton);
        quantityGroupButton.add(quantityQuestion10RadioButton);
        quantityGroupButton.add(quantityQuestion15RadioButton);

        quantityQuestionPanel = new JPanel(new GridLayout(1,3,5,15));
        quantityQuestionPanel.setBorder(BorderFactory.createTitledBorder("How many questions do you want?"));
        quantityQuestionPanel.add(quantityQuestion5RadioButton);
        quantityQuestionPanel.add(quantityQuestion10RadioButton);
        quantityQuestionPanel.add(quantityQuestion15RadioButton);

    }

    private void setComponentsForLevelPanel() {
        levelBasicRadioButton = new JRadioButton("BASIC");
        levelMediumRadioButton = new JRadioButton("MEDIUM");
        levelExpertRadioButton = new JRadioButton("EXPERT");

        levelExpertRadioButton.addActionListener(this);
        levelMediumRadioButton.addActionListener(this);
        levelBasicRadioButton.addActionListener(this);

        ButtonGroup levelGroupButton = new ButtonGroup();
        levelGroupButton.add(levelBasicRadioButton);
        levelGroupButton.add(levelMediumRadioButton);
        levelGroupButton.add(levelExpertRadioButton);

        levelPanel = new JPanel(new GridLayout(1,3,5,5));
        levelPanel.setBorder(BorderFactory.createTitledBorder("Choose level"));
        levelPanel.add(levelBasicRadioButton);
        levelPanel.add(levelMediumRadioButton);
        levelPanel.add(levelExpertRadioButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==startButton) {
            int quantityQuestions = (quantityQuestion5RadioButton.isSelected()) ? 5
                    : (quantityQuestion10RadioButton.isSelected()) ? 10
                    : 15;
            int quantityChosenCategories = 0;
            for (JCheckBox checkBox : categoriesCheckBoxList) {
                quantityChosenCategories = (checkBox.isSelected())
                        ? quantityChosenCategories+1 : quantityChosenCategories;
            }
            Advancement advancement = (levelBasicRadioButton.isSelected()) ? Advancement.BASIC
                    : (levelMediumRadioButton.isSelected()) ? Advancement.MEDIUM
                    : Advancement.EXPERT;
            String[] chosenCategory = new String[quantityChosenCategories];
            int index = 0;
            for (JCheckBox checkBox : categoriesCheckBoxList){
                if (checkBox.isSelected()) {
                    chosenCategory[index++] = checkBox.getText();
                }
            }
            new GameWindow(chosenCategory, advancement, quantityQuestions, connect);
            dispose();
        }
        if (e.getSource()==levelBasicRadioButton ||
                e.getSource()==levelMediumRadioButton ||
                e.getSource()==levelExpertRadioButton) {
            isLevelChosen = true;
            System.out.println("level");
            if (isCategoryChosen && isQuantityChosen) {
                startButton.setEnabled(true);
            }
        }
        if (e.getSource()==quantityQuestion5RadioButton ||
                e.getSource()==quantityQuestion15RadioButton ||
                e.getSource()==quantityQuestion10RadioButton) {
            isQuantityChosen = true;
            System.out.println("quantity");
            if (isCategoryChosen && isLevelChosen) {
                startButton.setEnabled(true);
            }
        }
    }
}
