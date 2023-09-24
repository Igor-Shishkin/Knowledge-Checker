package sda.groupProject.knowledgeChecker.graphicalInterface;

import sda.groupProject.knowledgeChecker.data.Answer;
import sda.groupProject.knowledgeChecker.data.JSONConnector;
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

          public static class Builder {
               private JPanel explanationPanel;
               private JPanel answersPanel;
               private List<JRadioButton> answerRadioButtons;
               private JLabel rightExplanation;
               private JLabel chosenExplanation;
               private List<Answer> listAnswersForTheQuestion;
               private JPanel questionPanel;
               private Question question;

               public Builder withQuestion (Question question){
                    this.question = question;
                    return this;
               }
               public Builder withExplanationPanel (JPanel explanationPanel){
                    this.explanationPanel = explanationPanel;
                    return this;
               }
               public Builder withAnswersPanel (JPanel answersPanel){
                    this.answersPanel = answersPanel;
                    return this;
               }
               public Builder withAnswerRadioButtons (List<JRadioButton> answerRadioButtons){
                    this.answerRadioButtons = answerRadioButtons;
                    return this;
               }
               public Builder withRightExplanation (JLabel rightExplanation){
                    this.rightExplanation = rightExplanation;
                    return this;
               }
               public Builder withChosenExplanation (JLabel chosenExplanation){
                    this.chosenExplanation = chosenExplanation;
                    return this;
               }
               public Builder withListAnswersForTheQuestion (List<Answer> listAnswersForTheQuestion){
                    this.listAnswersForTheQuestion = listAnswersForTheQuestion;
                    return this;
               }
               public Builder withQuestionPanel (JPanel questionPanel){
                    this.questionPanel = questionPanel;
                    return this;
               }
               public GraphicalElementsOfQuestion build() {
                    return new GraphicalElementsOfQuestion(question,
                            explanationPanel,
                            answersPanel,
                            answerRadioButtons,
                            rightExplanation,
                            chosenExplanation,
                            listAnswersForTheQuestion,
                            questionPanel);
               }
          }

}