import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Tests the compression/decompression algorithm.
 * Compression is tested by compressing strings in originalText array and comparing results to the
 * corresponding compressed string in compressedText array.
 * Complete report is written in 3 files:
 *      1 -> Compression tests
 *      2 -> Decompression tests
 *      3 -> Number of tests passed
 */
public class Test {

    private int passedCompressingTests = 0;
    private int passedDecompressingTests = 0;

    /*
    originalText[i] when compressed corresponds with compressedText[i]
    wrongCompressionTest -> array of invalid inputs for decompression
    First test is the empty string
    For easier reading of tests, if the original text is in the first line
    then the compressed text is also in the first line
     */
    private final String[] originalText = (",a,aa,aabb,aabbaa,aa11bb,aa11aa,aa1,aa11,±," +
            "aa±,aa±±±±±,aa±±bb,aa±±±±aa,±±±±±aaaaa,111222±±±,aaabbbbccc±±±cccc,±±±±," +
            "±±aaa,abcd2222,±3±2±1±22±1," +
            //tests where one or more characters are repeating more than 9 times consecutively
            "aaaaaaaaaaaaaaa,aaaaaaaaaaaaaaa12bvaaaaaaaaaaaaaaa," +
            "±±±±±±±±±±±±±±±aaaaaaaaaaaaaaa±±±±±±±±±±±±±±±,±±±±±±±±±±±±±±±111111111111±±±±±±±±±±±±±±±," +
            "111111111111±±±±±±±±±±±±±±±aaaaaaaaaaaaaaa," +
            "111111111111±aaaaaaaaaaaaaaa,111111111111±±±±±±±±±±±±±±±").split(",");

    private final String[] compressedText = (",a±1,a±2,a±2b±2,a±2b±2a±2,a±21±2b±2,a±21±2a±2,a±21±1,a±21±2,±±1," +
            "a±2±±1,a±2±±5,a±2±±2b±2,a±2±±4a±2,±±5a±5,1±32±3±±3,a±3b±4c±3±±3c±4,±±4," +
            "±±2a±3,a±1b±1c±1d±12±4,±±13±1±±12±1±±11±1±±12±2±±11±1," +
            "a±15,a±151±12±1b±1v±1a±15," +
            "±±15a±15±±15,±±151±12±±15," +
            "1±12±±15a±15," +
            "1±12±±1a±15,1±12±±15").split(",");

    private final String[] wrongCompressionTest = ("a,aa,a±1±,±,±±,±±±±±±," +
            "±±13±1±±12±1±11±1±±12±2±±11±1,1±2±±,a±b±2,b±32±,a±±±2,1±12±±,1±f,1±34f±22±±," +
            "33±1,3±±2,a±±2,a±a,±±23±,a±3±").split(",");

    /**
     * Begin test.
     */
    public void  runTest() {
        testCompression();
        testDecompression();
        writeReport();
    }

    /**
     * Writes the result of each compression test.
     */
    private void testCompression() {

        try (PrintWriter out = new PrintWriter(new FileWriter("CompressionTest.txt"))){

            for (int i = 0; i < originalText.length; i++) {

                String algorithmResult = Algorithm.compress(originalText[i]);

                StringBuilder message = new StringBuilder();
                message.append("Compression test case: ")
                        .append(originalText[i]);

                if (algorithmResult.equals(compressedText[i])) {
                    message.append(": PASSED :");
                    passedCompressingTests++;
                } else {
                    message.append(": FAILED :");
                }

                message.append("expected output: ")
                        .append(compressedText[i])
                        .append(" : compression result: ")
                        .append(algorithmResult);

                out.println(message);
            }

        } catch (IOException e) {
            System.out.println("Error during the writing of compression test: " + e.getMessage());
        }
    }

    /**
     * Writes the result of each decompression test. First for loop tests correct inputs,
     * second one tests wrong inputs.
     */
    private void testDecompression() {

        try (PrintWriter out = new PrintWriter(new FileWriter("DecompressionTest.txt"))){

            for (int i = 0; i < compressedText.length; i++) {

                try {
                    String algorithmResult = Algorithm.decompress(compressedText[i]);

                    StringBuilder message = new StringBuilder();
                    message.append("Decompression test case: ")
                            .append(compressedText[i]);

                    if (algorithmResult.equals(originalText[i])) {
                        message.append(": PASSED :");
                        passedDecompressingTests++;

                    } else {
                        message.append(": FAILED :");
                    }

                    message.append("expected output: ")
                            .append(originalText[i])
                            .append(" : compression result: ")
                            .append(algorithmResult);

                    out.println(message);

                } catch (IllegalArgumentException iae) {
                    out.println("Decompression test case: " + compressedText[i] +
                            ": FAILED :" + "expected output: " + originalText[i]
                            + " : compression result: " + iae.getMessage());
                }
            }

            //If an error is caught for the wrong input test is passed
            for (String test : wrongCompressionTest) {
                try {
                    Algorithm.decompress(test);
                    out.println("Decompression test case:" + test + " :" +
                            "FAILED : Error not caught");

                } catch (IllegalArgumentException iae) {
                    out.println("Decompression test case: " + test +
                            ": PASSED : Error caught: " + iae.getMessage());
                    passedDecompressingTests++;
                }
            }

        } catch (IOException e) {
            System.out.println("Error during the writing of compression test: " + e.getMessage());
        }
    }

    /**
     * Writes the amount of the passed tests.
     */
    private void writeReport() {
        try (PrintWriter out = new PrintWriter(new FileWriter("TestReport.txt"))){

            String number = String.format("%d (%.2f%%)", passedCompressingTests,
                    (float) passedCompressingTests / originalText.length * 100);
            out.println("Compression tests passed: " + number);

            number = String.format("%d (%.2f%%)", passedDecompressingTests,
                    (float) passedDecompressingTests / (compressedText.length + wrongCompressionTest.length) * 100);
            out.println("Compression tests passed: " + number);

            out.println("Detailed report for each test in CompressionTest\\DecompressionTest file");

        } catch (IOException ioe) {
            System.out.println("Error during the writing of the report: " + ioe.getMessage());
        }
    }
}
