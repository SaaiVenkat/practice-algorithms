package internship.amgen;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class PrimePawnJump {

    static class Solution {
        private boolean isValidPrime(int number) {
            if (number % 10 != 3) {
                return false;
            }

            if (number == 3) {
                return true;
            }

            if (number % 3 == 0) {
                return false;
            }

            for (int i = 5; i < Math.sqrt(number); i = i + 6) {
                if (number % i == 0 || number % (i + 2) == 0) {
                    return false;
                }
            }

            return true;
        }

        public int maxGameScore(List<Integer> cell) {
            Map<Integer, Integer> scores = new HashMap<>();
            List<Integer> primes = new ArrayList<>();
            primes.add(1);
            for (int i = 1; i <= cell.size(); i++) {
                if (isValidPrime(i)) {
                    primes.add(i);
                }
            }

            for (int i = 0; i < cell.size(); i++) {
                for (int j : primes) {
                    if (i + j < cell.size()) {
                        int existingScore = scores.getOrDefault(i + j, 0);
                        int currentScore = scores.getOrDefault(i, 0) + cell.get(i + j);
                        if (!scores.containsKey(i + j)) {
                            scores.put(i + j, currentScore);
                        } else if (currentScore > existingScore) {
                            scores.put(i + j, currentScore);
                        }
                    }
                }
            }

            return scores.get(cell.size() - 1);
        }
    }

    static class TestCase {

        private final int testCaseNumber;
        private final String inputPath;
        private final String outputPath;

        public TestCase(int testCaseNumber) {
            this.testCaseNumber = testCaseNumber;
            String commonPath = Paths.get(File.separator, PrimePawnJump.class.getName().replace(".", File.separator), "test-case-").toString();
            this.inputPath = Paths.get(commonPath.concat(String.valueOf(testCaseNumber)), "input.txt").toString();
            this.outputPath = Paths.get(commonPath.concat(String.valueOf(testCaseNumber)), "output.txt").toString();
        }

        public List<Integer> getInput() {
            try (InputStream is = PrimePawnJump.class.getResourceAsStream(this.inputPath)) {
                if (is == null) {
                    throw new IOException(String.format("Unable to read input file: %s", this.inputPath));
                }
                List<String> inputStr = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
                List<Integer> input = new ArrayList<>(Integer.parseInt(inputStr.get(0)));
                for (int i = 1; i < inputStr.size(); i++) {
                    input.add(Integer.parseInt(inputStr.get(i)));
                }

                return input;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public int getOutput() {
            try (InputStream is = PrimePawnJump.class.getResourceAsStream(this.outputPath)) {
                if (is == null) {
                    throw new IOException(String.format("Unable to read output file: %s", this.outputPath));
                }
                String outputStr = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).readLine();
                return Integer.parseInt(outputStr);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        List<Integer> cells = Arrays.asList(0, -10, 100, -20);
        System.out.println(solution.maxGameScore(cells));

        TestCase testCase1 = new TestCase(1);
        if (testCase1.getOutput() == solution.maxGameScore(testCase1.getInput())) {
            System.out.printf("Test Case: %s Passed%n", testCase1.testCaseNumber);
        } else {
            System.out.printf("Test Case: %s Failed%n", testCase1.testCaseNumber);
        }
    }
}
