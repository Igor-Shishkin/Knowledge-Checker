package sda.groupProject.knowledgeChecker.data;

public class Category {
    private int categoryId;
    private String categoryName;

    public Category(String categoryName, int categoryId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName() {
        this.categoryName = categoryName;
    }

}
