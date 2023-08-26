package sda.groupProject.knowledgeChecker.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void createCategoryWithValidValues() {
        // given
        String categoryName = "Science";
        int categoryId = 1;

        // when
        Category category = new Category(categoryName, categoryId);

        // then
        assertEquals(categoryName, category.categoryName());
        assertEquals(categoryId, category.categoryId());
    }

    @Test
    void categoriesWithSameValuesShouldBeEqual() {
        // given
        Category category1 = new Category("Math", 2);
        Category category2 = new Category("Math", 2);

        // then
        assertEquals(category1, category2);
    }

    @Test
    void categoriesWithDifferentNamesShouldNotBeEqual() {
        // given
        Category category1 = new Category("Biology", 3);
        Category category2 = new Category("Physics", 3);

        // then
        assertNotEquals(category1, category2);
    }

    @Test
    void categoriesWithDifferentIDsShouldNotBeEqual() {
        // given
        Category category1 = new Category("Biology", 3);
        Category category2 = new Category("Biology", 4);

        // then
        assertNotEquals(category1, category2);
    }
}