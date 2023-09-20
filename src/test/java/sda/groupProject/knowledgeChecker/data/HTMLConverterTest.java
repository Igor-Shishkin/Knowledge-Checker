package sda.groupProject.knowledgeChecker.data;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HTMLConverterTest {

    @Test
    void changeTextToHTML() {
        String originalText = "Lorem\nipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor "
        .concat("incididunt ut labore et dolore magna aliqua.");

        String expectedText = "<html>Lorem<br>ipsum dolor sit amet,<br>consectetur adipiscing elit,<br>sed do eiusmod tempor<br>"
                .concat("incididunt ut labore et<br>dolore magna aliqua.<br></html>");

        assertThat(HTMLConverter.changeTextToHTML(originalText,30))
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expectedText);
    }
}