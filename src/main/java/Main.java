import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            Main main = new Main();
            WordsNumbers wordsNumbers = main.wordsNumbers(line);
            main.wordNumberPrinter(wordsNumbers);
        }

    }

    WordsNumbers wordsNumbers(String line) {
        List<String> input = Arrays.asList(line.split(","));
        List<Integer> numbers = input.stream().filter((word) -> isNumber(word)).map((integer) -> Integer.parseInt(integer)).collect(Collectors.toUnmodifiableList());
        List<String> words = input.stream().filter((word) -> !isNumber(word)).collect(Collectors.toUnmodifiableList());
        return new WordsNumbers(words, numbers);
    }

    void wordNumberPrinter(WordsNumbers wordsNumbers) {
        if (wordsNumbers.hasWords() && wordsNumbers.hasNumbers()) {
            String output = String.format("%s | %s", replaceSquareBrackets(wordsNumbers.words), replaceSquareBrackets(wordsNumbers.numbers));
            System.out.println(output);
        } else if (wordsNumbers.hasNumbers()) {
            String output = replaceSquareBrackets(wordsNumbers.numbers);
            System.out.println(output);
        } else if (wordsNumbers.hasWords()) {
            String output = replaceSquareBrackets(wordsNumbers.words);
            System.out.println(output);
        }
    }

    boolean isNumber(String word) {
        try {
            Integer.parseInt(word);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    String replaceSquareBrackets(List input) {
        return input.toString().replaceAll("(\\[|])", "");
    }

    class WordsNumbers {
        private final List<String> words;
        private final List<Integer> numbers;

        WordsNumbers(List<String> words, List<Integer> numbers) {
            this.words = words;
            this.numbers = numbers;
        }

        public List<String> getWords() {
            return words;
        }

        public List<Integer> getNumbers() {
            return numbers;
        }

        public boolean hasWords() {
            return !words.isEmpty();
        }

        public boolean hasNumbers() {
            return !numbers.isEmpty();
        }
    }


}

