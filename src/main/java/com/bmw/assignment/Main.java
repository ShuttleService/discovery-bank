package com.bmw.assignment;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static String lastLetters(String word) {
        // Write your code here
        if (word == null || word.length() < 2 || word.length() > 100) {
            throw new IllegalArgumentException("Word must be between 2 and 100 characters long");
        }
        final String lastTwoCharacters = word.substring(word.length() - 2);
        return lastTwoCharacters.substring(1) + " " + lastTwoCharacters.substring(0, 1);
    }

    public static boolean containsDuplicates(List<Integer> integers) {
        Set<Integer> set = new HashSet<>(integers);
        return set.size() != integers.size();
    }

    public static int getMinimumUniqueSum(List<Integer> arr) {
        // Write your code here
        List<Integer> copy = new ArrayList<>(arr);

        boolean containsDuplicate;
        do {
            containsDuplicate = containsDuplicates(copy);
            doubleDuplicate(copy);
        }
        while (containsDuplicate);

        return copy.stream().reduce(Integer::sum).get();
    }

    public static List<Integer> doubleDuplicate(List<Integer> copy) {
        copy.sort(Comparator.comparingInt(a -> a));

        for (int i = 1; i < copy.size(); i++) {
            if (copy.get(i - 1).equals(copy.get(i))) {
                doubleIntegerAt(copy, i);
            }
        }

        return copy;
    }

    public static void doubleIntegerAt(List<Integer> integers, int i) {
        integers.set(i, integers.get(i) * 2);
    }

    public static void main(String... ar) {
        System.out.println(commonPrefix(Arrays.asList("abcabcd", "ababaa")));
    }

    public static List<Integer> commonPrefix(List<String> inputs) {
        // Write your code here
        List<List<Integer>> commonPrefixLengths = new ArrayList<>();

        inputs.forEach((string) -> {
            List<String> suffixes = suffixes(string);

            List<Integer> commonPrefixLength = new ArrayList<>();

            suffixes.forEach((suffix -> {
                        List<String> prefixes = prefixes(suffix);
                        List<PrefixAndString> prefixAndStrings = new ArrayList<>();
                        prefixes.forEach(prefix -> prefixAndStrings.add(new PrefixAndString(prefix, string)));
                        commonPrefixLength.add(commonPrefixLength(prefixAndStrings));
                    })

            );
            commonPrefixLengths.add(commonPrefixLength);
        });

        return commonPrefixLengths.stream().map(cpl -> sum(cpl)).collect(Collectors.toUnmodifiableList());
    }

    public static int sum(List<Integer> ints) {
        return ints == null ? 0 : ints.stream().reduce(Integer::sum).orElse(0);
    }

    public static List<String> suffixes(String originalString) {
        List<String> suffixes = new ArrayList<>();
        for (int i = 0; i <= originalString.length(); i++) {
            String prefix = originalString.substring(0, originalString.length() - i);
            String suffix = originalString.substring(prefix.length());
            suffixes.add(suffix);
        }
        return suffixes;
    }

    private static class PrefixAndString {
        private final String suffix;
        private final String originalString;

        public PrefixAndString(String suffix, String originalString) {
            this.originalString = originalString;
            this.suffix = suffix;
        }
    }

    public static boolean commonPrefix(PrefixAndString prefixAndString) {
        final String originalString = prefixAndString.originalString;
        final String suffix = prefixAndString.suffix;
        final int indexOfSuffix = originalString.indexOf(suffix);
        return indexOfSuffix == 0;
    }

    public static List<String> prefixes(String string) {
        List<String> prefixes = new ArrayList<>();
        for (int i = 0; i <= string.length(); i++) {
            prefixes.add(string.substring(0, i));
        }
        return prefixes;
    }

    public static int commonPrefixLength(List<PrefixAndString> prefixAndStrings) {
        int longestCommonPrefix = 0;
        for (PrefixAndString prefixAndString : prefixAndStrings) {
            final List<String> prefixes = prefixes(prefixAndString.suffix);

            for (String string : prefixes) {
                PrefixAndString prefixAndStringWithPrefixes = new PrefixAndString(string, prefixAndString.originalString);
                if (commonPrefix(prefixAndStringWithPrefixes)) {

                    longestCommonPrefix = string.length();
                }
            }

        }
        return longestCommonPrefix;
    }
}
