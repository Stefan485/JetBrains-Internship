public class Algorithm {

    public static String compress(String text) {
        if (text == null) throw new IllegalArgumentException("Text can't be null");

        StringBuilder compressed = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            int j = i + 1;
            while (j < text.length() && text.charAt(i) == text.charAt(j)) j++;
            int timesRepeating = j - i;
            compressed.append(text.charAt(i))
                    .append("#").append(timesRepeating);
            i = j;
        }
        return  compressed.toString();
    }
}
