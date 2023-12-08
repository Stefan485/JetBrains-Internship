public class Algorithm {

    /**
     * @param text text to be compressed
     * @return String - compressed text
     * @throws IllegalArgumentException when input text is null
     */
    public static String compress(String text) {
        if (text == null)
            throw new IllegalArgumentException("Text is null");

        StringBuilder compressed = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            int j = i + 1;
            while (j < text.length() && text.charAt(i) == text.charAt(j)) j++;
            int timesRepeating = j - i;

            compressed.append(text.charAt(i))
                    .append('±').append(timesRepeating);
            i = j;
        }
        return  compressed.toString();
    }

    /**
     * @param text text to be decompressed
     * @return String - decompressed text
     * @throws IllegalArgumentException when input is invalid (null, missing a sing...)
     */
    public static String decompress(String text) {
        if (text == null){
            throw new IllegalArgumentException("Text is null");
        }

        StringBuilder decompressed = new StringBuilder();
        int i = 0;
        while (i + 2 < text.length()) {
            //is there a separator between current character, and it's number of repeating
            if (text.charAt(i + 1) != '±')
                throw new IllegalArgumentException("Missing ± sign");

            //skip ±
            int j = i + 2;
            StringBuilder number = new StringBuilder();
            while (j < text.length() && (text.charAt(j) >= '0' && text.charAt(j) <= '9')) {
                number.append(text.charAt(j));
                j++;
            }

            //j didn't move -> there are no numbers of repeating for the current character
            if (j == i + 2)
                throw new IllegalArgumentException("Unknown number of a character");

            //If charAt(j) is ± and length of the number is 1 there is a character missing
            //Either it's the character from the original text (before the separator sign)
            //or the separator sign is missing (one separator represents the character from the original text
            //and the other is for separating)
            if(j < text.length() && text.charAt(j) == '±' && number.length() > 1) {
                number.deleteCharAt(number.length() - 1);
                j--;
            }

            decompressed.append(String.valueOf(text.charAt(i))
                    .repeat(Integer.parseInt(number.toString())));

            i = j;
        }
        if (i < text.length())
            throw new IllegalArgumentException("Missing one or more characters");

        return decompressed.toString();
    }
}
