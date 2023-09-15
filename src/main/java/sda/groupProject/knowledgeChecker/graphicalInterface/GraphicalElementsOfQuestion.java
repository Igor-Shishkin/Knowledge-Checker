package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.Answer;
import sda.groupProject.knowledgeChecker.data.Question;

import javax.swing.*;
import java.util.List;

     record GraphicalElementsOfQuestion(Question question, JPanel explanationPanel, JPanel answersPanel, JPanel codePanel,
                                        List<JRadioButton> answerRadioButtons, JLabel questionLabel, JLabel rightExplanation,
                                        JLabel chosenExplanation, ButtonGroup answersGroupButton, List<Answer> listAnswersForTheQuestion,
                                        JPanel questionPanel, JScrollPane scrollPane) {

}