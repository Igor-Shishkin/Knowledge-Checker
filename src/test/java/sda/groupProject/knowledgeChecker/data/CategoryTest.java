package sda.groupProject.knowledgeChecker.data;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    void createCategoryWithValidValues() {
        // given
        String categoryName = "Science";
        int categoryId = 1;

        // when
        Category category = new Category(categoryName, categoryId);

        // then
        assertThat(category.categoryName()).isEqualTo(categoryName);
        assertThat(category.categoryId()).isEqualTo(categoryId);
    }

    @Test
    void testEqualsAndHashCode() {
        Category category1 = new Category("Biology", 1);
        Category category2 = new Category("Biology", 1);
        Category category3 = new Category("Physic", 2);
        Category category4 = new Category("Physic", 3);

        // Test equality using AssertJ assertions
        assertThat(category1).isEqualTo(category2)
                .isNotEqualTo(category3);

        assertThat(category3).isNotEqualTo(category2)
                .isNotEqualTo(category4);

        // Test hash code using AssertJ assertions
        assertThat(category1.hashCode()).isEqualTo(category2.hashCode())
                .isNotEqualTo(category3.hashCode());
    }
}