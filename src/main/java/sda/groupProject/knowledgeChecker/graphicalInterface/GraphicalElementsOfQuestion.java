package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.Answer;
import sda.groupProject.knowledgeChecker.data.Question;

import javax.swing.*;
import java.util.List;

     record GraphicalElementsOfQuestion(Question question,
                                        JPanel explanationPanel,
                                        JPanel answersPanel,
                                        List<JRadioButton> answerRadioButtons,
                                        JLabel rightExplanation,
                                        JLabel chosenExplanation,
                                        List<Answer> listAnswersForTheQuestion,
                                        JPanel questionPanel) {

}