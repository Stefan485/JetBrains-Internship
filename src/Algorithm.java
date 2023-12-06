public class Algorithm {

    public static String compress(String text) {
        if (text == null)
            throw new IllegalArgumentException("Text can't be null");

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

    public static String decompress(String text) {
        if (text == null)
            throw new IllegalArgumentException("Text can't be null");

        StringBuilder decompressed = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            if (text.charAt(i + 1) != '±')
                throw new IllegalArgumentException("Missing ± sign");

            char current = text.charAt(i);
            //skip ±
            int j = i + 2;
            StringBuilder number = new StringBuilder();
            while (j < text.length() && (text.charAt(j) >= '0' && text.charAt(j) <= '9')) {
                number.append(text.charAt(j));
                j++;
            }
            if (j == i + 2)
                throw new IllegalArgumentException("Invalid format of text: unknown number of character");

            //If charAt(j) is ± and j + 1 is not -> digit is part of compressed text
            //If j + 1 is ± -> ± is part of compressed text (no removing digits from the number)
            if(j < text.length() && (text.charAt(j) == '±' && text.charAt(j + 1) != '±')) {
                number.deleteCharAt(number.length() - 1);
                j--;
            }
            decompressed.append(String.valueOf(current)
                    .repeat(Integer.parseInt(number.toString())));

            i = j;
        }

        return decompressed.toString();
    }
}
