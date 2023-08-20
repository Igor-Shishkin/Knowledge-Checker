package sda.groupProject.knowledgeChecker.data;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
public class Question {

    private int id;
    private Advancement advancement;
    private Category category;
    private String text;
    private Answer[] answers;

    public Question(int id, Advancement advancement, Category category, String text, Answer[] answers) {
        this.id = id;
        this.advancement = advancement;
        this.category = category;
        this.text = text;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Advancement getAdvancement() {
        return advancement;
    }

    public void setAdvancement(Advancement advancement) {
        this.advancement = advancement;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answer[] answers) {
        this.answers = answers;
    }
}