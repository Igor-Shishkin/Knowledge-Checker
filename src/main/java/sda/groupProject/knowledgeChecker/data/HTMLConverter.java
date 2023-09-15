package sda.groupProject.knowledgeChecker.data;

public class HTMLConverter {

    private HTMLConverter() {}

    public static String changeTextToHTML(String text, int lineLength) {
        if (text == null || text.isEmpty() || lineLength <= 0) {
            return text;
        }

        StringBuilder result = new StringBuilder("<html>");
        int startIndex = 0;

        while (startIndex < text.length()) {
            int endIndex = Math.min(startIndex + lineLength, text.length());
            String chunk = text.substring(startIndex, endIndex);

            if (endIndex < text.length()) {
                int lastSpaceIndex = chunk.lastIndexOf(' ');

                if (lastSpaceIndex != -1) {
                    endIndex = startIndex + lastSpaceIndex;
                    chunk = chunk.substring(0, lastSpaceIndex);
                }
            }

            result.append(chunk).append("<br>");
            startIndex = endIndex + 1;
        }

        result.append("</html>");
        return result.toString();
    }
}

