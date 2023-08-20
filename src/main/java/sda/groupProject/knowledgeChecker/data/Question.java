package sda.groupProject.knowledgeChecker.data;

import java.util.List;

public record Question(int id, Advancement advancement,
                       Category category,
                       String text,
                       List <Answer> answers) {

}