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

            //Is there a separator after the current character
            if (text.charAt(i + 1) != '±')
                throw new IllegalArgumentException("Missing ± sign");

            int j = i + 2;
            StringBuilder number = new StringBuilder();
            while (j < text.length() && (text.charAt(j) >= '0' && text.charAt(j) <= '9')) {
                number.append(text.charAt(j));
                j++;
            }

            //j didn't move -> there are no numbers of repeating for the current character.
            if (j == i + 2)
                throw new IllegalArgumentException("Unknown number of a character");

            /*
                If it stopped because ± is encountered: Either ± is a part of the original text
                or a digit is.
                j < length of text -> check if the next character is the ±. If it is
                ± is a part of the original text, otherwise digit is a part of the original text
                and last digit from the number should be removed.
             */
            if(j < text.length() && text.charAt(j) == '±') {
                if (j + 1 < text.length() && text.charAt(j + 1) != '±') {
                    number.deleteCharAt(number.length() - 1);
                    j--;
                }
            }

            /*Append substring to the text
              number will never be empty or contain anything besides digits because
              only digits can be added, and digit is removed only if
            */
            decompressed.append(String.valueOf(text.charAt(i))
                    .repeat(Integer.parseInt(number.toString())));

            i = j;
        }

        //If i didn't reach the end (i + 2 < text.length) -> something is missing
        if (i < text.length())
            throw new IllegalArgumentException("Missing one or more characters");

        return decompressed.toString();
    }
}
