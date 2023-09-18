package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GreetingWindow extends JFrame implements ActionListener {
    private final Font MAIN_FONT = new Font("Consolas", Font.PLAIN, 18);
    private JPanel greetingPanel;
    private JPanel categoryPanel;
    private JPanel levelPanel;
    private JPanel quantityQuestionPanel;
    private JButton startButton;
    private JRadioButton levelBasicRadioButton;
    private JRadioButton levelMediumRadioButton;
    private JRadioButton levelExpertRadioButton;
    private JRadioButton quantityQuestion5RadioButton;
    private JRadioButton quantityQuestion10RadioButton;
    private JRadioButton quantityQuestion15RadioButton;
    private List<JCheckBox> categoriesCheckBoxList;
    private String[] chosenCategory;
    private transient List<Question> listOfQuestions;
    private int quantityOfQuestions;
    private Advancement advancement;
    private final transient JSONConnector connect;
    private String[] listOfCategory;
    private JButton blitzButton;
    private boolean isLevelChosen = false;
    private boolean isCategoryChosen = false;
    private boolean isQuantityChosen = false;
    private JMenuBar myMenuBar;
    private JMenuItem testRulesItem;
    private JMenuItem interviewRulesItem;
    private JMenuItem aboutTheProjectItem;


    public GreetingWindow(JSONConnector connect) {
        this.connect = connect;

        setGreetingPanel();

        this.setLayout(new GridLayout(1, 1, 5, 5));
        this.add(greetingPanel);

        setFontForComponents(this);
        setMyMenuBar();

        this.setJMenuBar(myMenuBar);
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("CHECK YOUR KNOWLEDGE");
        this.setVisible(true);
    }

    private void setMyMenuBar() {
        testRulesItem = new JMenuItem("Test");
        interviewRulesItem = new JMenuItem("Interview");
        JMenu rulesMenu = new JMenu("Rules");
        rulesMenu.add(testRulesItem);
        rulesMenu.add(interviewRulesItem);

        aboutTheProjectItem = new JMenuItem("About the project");
        JMenu informationMenu = new JMenu("Information");
        informationMenu.add(aboutTheProjectItem);

        testRulesItem.addActionListener(this);
        interviewRulesItem.addActionListener(this);
        aboutTheProjectItem.addActionListener(this);

        myMenuBar = new JMenuBar();
        myMenuBar.add(rulesMenu);
        myMenuBar.add(informationMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            actionIfStartButtonIsPressed();


            if (quantityOfQuestions > listOfQuestions.size()) {
                actionIfQuestionAreLessThenRequired();

            } else {
                new GameWindow.Builder()
                        .withChosenCategory(chosenCategory)
                        .withAdvancement(advancement)
                        .withConnect(connect)
                        .withQuantityQuestions(quantityOfQuestions)
                        .withListOfQuestions(listOfQuestions)
                        .build();

                dispose();
            }
        }
        if (e.getSource() == levelBasicRadioButton ||
                e.getSource() == levelMediumRadioButton ||
                e.getSource() == levelExpertRadioButton) {
            isLevelChosen = true;
            if (isCategoryChosen && isQuantityChosen) {
                startButton.setEnabled(true);
            }
        }
        if (e.getSource() == quantityQuestion5RadioButton ||
                e.getSource() == quantityQuestion15RadioButton ||
                e.getSource() == quantityQuestion10RadioButton) {
            isQuantityChosen = true;
            if (isCategoryChosen && isLevelChosen) {
                startButton.setEnabled(true);
            }
        }
        if (e.getSource() == blitzButton) {
            actionIfBlitzButtonIsPressed();
        }
        if (e.getSource() == aboutTheProjectItem) {
            String textAboutProject = "<html>Autors:<br>Igor Shishkin<br>Arthur Pososhko<br><br>"
                    .concat("Mentor:<br>Andrzej Chmiel<br><br>Test created by:<br>Andrzej Chmiel");
            JLabel labelAboutProject = new JLabel(textAboutProject);
            labelAboutProject.setFont(MAIN_FONT.deriveFont(Font.PLAIN, 23));
            labelAboutProject.setBorder(BorderFactory.createLineBorder(new Color(0x79ABA9), 3));
            JOptionPane.showMessageDialog(this,
                    labelAboutProject, "KNOWLEDGE-CHECKER", JOptionPane.PLAIN_MESSAGE);
        }
        if (e.getSource() == testRulesItem) {
            String textAboutUsualGame = "You can select the category, difficulty level and number of questions. "
                    .concat("After each answer, you can see the correct option and explanations for the selected ")
                    .concat("answer and for the correct one.<br>")
                    .concat("To see explanations for other questions, you need to hover your mouse over them.<br>")
                    .concat("After the test, you can see your result and review your test again.");
            JLabel labelUsualGameRules = new JLabel(HTMLConverter.changeTextToHTML(textAboutUsualGame, 60));
            labelUsualGameRules.setFont(MAIN_FONT.deriveFont(Font.PLAIN, 23));
            labelUsualGameRules.setBorder(BorderFactory.createLineBorder(new Color(0x79ABA9), 3));
            JOptionPane.showMessageDialog(this,
                    labelUsualGameRules, "TEST - RULES", JOptionPane.PLAIN_MESSAGE);
        }
        if (e.getSource() == interviewRulesItem) {
            String textInterviewRules = "<html>You have three minutes<br>to answer as many questions as possible.<br>"
                    .concat("The questions are in random order,<br>of all categories and levels.<br><br>")
                    .concat("For the correct answer you will be awarded:<br>")
                    .concat("basic level: 1 point<br>")
                    .concat("medium level: 2 points<br>")
                    .concat("expert level: 3 points<br><br>")
                    .concat("For an incorrect answer the following will be deducted:<br>")
                    .concat("basic level: 0.5 point<br>")
                    .concat("medium level: 1 point<br>")
                    .concat("expert level: 1.5 points<br><br>")
                    .concat("To be hired, you need to score at<br>least 80 percent of the possible score and<br>")
                    .concat("at the same time at least 20 points.<br>")
                    .concat("Good luck!</html>");
            JLabel labelInterviewRules = new JLabel(textInterviewRules);
            labelInterviewRules.setFont(MAIN_FONT.deriveFont(Font.PLAIN, 23));
            labelInterviewRules.setBorder(BorderFactory.createLineBorder(new Color(0x79ABA9), 3));
            JOptionPane.showMessageDialog(this,
                    labelInterviewRules, "INTERVIEW - RULES", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void actionIfBlitzButtonIsPressed() {
        FilterListOfQuestions filterListOfQuestions = new FilterListOfQuestions(connect.getListOfQuestions());
        listOfQuestions = filterListOfQuestions.getShuffledListOfQuestion();

        new BlitzWindow(connect, listOfQuestions);
        dispose();
    }

    private void actionIfQuestionAreLessThenRequired() {
        if (!listOfQuestions.isEmpty()) {
            quantityOfQuestions = listOfQuestions.size();
            String message = String.format("There are only %d questions for the parameter you have chosen.",
                    quantityOfQuestions);
            JOptionPane.showMessageDialog(this,
                    message, "Warning", JOptionPane.ERROR_MESSAGE);


            new GameWindow.Builder()
                    .withChosenCategory(chosenCategory)
                    .withAdvancement(advancement)
                    .withConnect(connect)
                    .withQuantityQuestions(quantityOfQuestions)
                    .withListOfQuestions(listOfQuestions)
                    .build();

            dispose();
        } else {
            String message = "There aren't questions for\nthe parameter you have chosen.";
            JOptionPane.showMessageDialog(this,
                    message, "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actionIfStartButtonIsPressed() {
        quantityOfQuestions = getQuantityOfQuestions();

        int quantityChosenCategories = 0;
        for (JCheckBox checkBox : categoriesCheckBoxList) {
            quantityChosenCategories = (checkBox.isSelected())
                    ? quantityChosenCategories + 1 : quantityChosenCategories;
        }

        chosenCategory = new String[quantityChosenCategories];
        int index = 0;
        for (JCheckBox checkBox : categoriesCheckBoxList) {
            if (checkBox.isSelected()) {
                chosenCategory[index++] = checkBox.getText();
            }
        }
        advancement = getAdvancement();

        FilterListOfQuestions filterListOfQuestions = new FilterListOfQuestions(connect.getListOfQuestions());
        listOfQuestions = filterListOfQuestions.getListOfQuestions(advancement, chosenCategory, quantityOfQuestions);
    }

    private Advancement getAdvancement() {
        if (levelBasicRadioButton.isSelected()) {
            return Advancement.BASIC;
        } else if (levelMediumRadioButton.isSelected()) {
            return Advancement.MEDIUM;
        }
        return Advancement.EXPERT;
    }

    private int getQuantityOfQuestions() {
        if (quantityQuestion5RadioButton.isSelected()) {
            return 5;
        } else if (quantityQuestion10RadioButton.isSelected()) {
            return 10;
        }
        return 15;
    }


    private void setFontForComponents(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel || component instanceof JComboBox<?>) {
                component.setFont(MAIN_FONT);
            }
            if (component instanceof JRadioButton) {
                component.setFont(MAIN_FONT.deriveFont(Font.BOLD, 19));
            }
            if (component instanceof JCheckBox) {
                component.setFont(MAIN_FONT.deriveFont(Font.BOLD, 19));
            }
            if (component instanceof Container innerContainer) {
                setFontForComponents(innerContainer);
            }
        }
    }

    private void setGreetingPanel() {
        JLabel greetingLabel;

        setComponentsForLevelPanel();
        setComponentsForQuantityQuestionsPanel();
        setComponentsForCategoryPanel();

        startButton = new JButton("START!");
        startButton.setFont(new Font(null, Font.BOLD, 30));
        startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(this);
        startButton.setEnabled(false);

        blitzButton = new JButton("<html>&nbsp;&nbsp;&nbsp;JOB<br>interview</html>");
        blitzButton.setFont(new Font(null, Font.BOLD, 30));
        blitzButton.setBackground(Color.RED);
        blitzButton.setForeground(Color.WHITE);
        blitzButton.addActionListener(this);
        blitzButton.setEnabled(true);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonPanel.add(startButton);
        buttonPanel.add(blitzButton);


        greetingLabel = new JLabel
                ("<html>Greeting!<br>Are you ready to check your knowledge?"
                        .concat("<br>Choose level, category and how many questions<br>would you like</html>"));
        greetingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        greetingPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
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

        c.gridx = 2;
        c.gridy = 2;
        greetingPanel.add(quantityQuestionPanel, c);


        c.insets = new Insets(20, 20, 20, 20);
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 2;
        c.gridheight = (listOfCategory.length > 4) ? listOfCategory.length - 2 : 3;
        greetingPanel.add(buttonPanel, c);


    }

    private void setComponentsForCategoryPanel() {
        listOfCategory = connect.getCategoryNames();
        categoryPanel = new JPanel(new GridLayout(listOfCategory.length, 1, 5, 5));
        categoryPanel.setBorder(BorderFactory.createTitledBorder("Choose categories"));

        categoriesCheckBoxList = new ArrayList<>();

        for (int i = 0; i < listOfCategory.length; i++) {
            categoriesCheckBoxList.add(new JCheckBox(listOfCategory[i]));
            categoriesCheckBoxList.get(i).addActionListener(e -> {
                isCategoryChosen = false;
                for (JCheckBox checkBox : categoriesCheckBoxList) {
                    if (checkBox.isSelected()) {
                        isCategoryChosen = true;
                    }
                }
                startButton.setEnabled(isCategoryChosen && isLevelChosen && isQuantityChosen);
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

        quantityQuestionPanel = new JPanel(new GridLayout(1, 3, 5, 15));
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

        levelPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        levelPanel.setBorder(BorderFactory.createTitledBorder("Choose level"));
        levelPanel.add(levelBasicRadioButton);
        levelPanel.add(levelMediumRadioButton);
        levelPanel.add(levelExpertRadioButton);
    }
}

