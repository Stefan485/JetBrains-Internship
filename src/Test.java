import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Test {

    private int passedCompressingTests = 0;
    private int passedDecompressingTests = 0;

    private final String[] originalText = ("a,aa,aabb,aa11,aa1,±," +
            "aa±,a±abb±,±±±±±a,111222±±±,aaabbbbccc±±±cccc,±±±±," +
            "±±aaa,abcd2222,±3±2±1±22±1,aaaaaaaaaaaaaaa").split(",");

    private final String[] compressedText = ("a±1,a±2,a±2b±2,a±21±2,a±21±1,±±1," +
            "a±2±±1,a±1±±1a±1b±2±±1,±±5a±1,1±32±3±±3,a±3b±4c±3±±3c±4," +
            "±±4,±±2a±3,a±1b±1c±1d±12±4,±±13±1±±12±1±±11±1±±12±2±±11±1,a±15").split(",");

    private final String[] wrongCompressionTest = ("a,aa,a±1±,±,±±,±±±±±±," +
            "±±13±1±±12±1±11±1±±12±2±±11±1,1±2±±,a±b±2,b±32±").split(",");

    public void  runTest() {
        testCompression();
        testDecompression();
        writeReport();
    }

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
            System.out.println("Error during writing compression test: " + e.getMessage());
        }
    }


    private void testDecompression() {

        try (PrintWriter out = new PrintWriter(new FileWriter("DecompressionTest.txt"))){

            //First test correct inputs, after that test wrong inputs
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
            System.out.println("Error during writing compression test: " + e.getMessage());
        }
    }

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
            System.out.println(ioe.getMessage());
        }
    }
}
