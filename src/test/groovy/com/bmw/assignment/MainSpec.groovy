package com.bmw.assignment

class MainSpec extends spock.lang.Specification {

    def 'sum calculates the integers correctly'() {
        expect:
        Main.sum([3, 2, 1, 5, 32]) == 43
    }

    def 'calculating empty list gives 0'() {
        expect:
        Main.sum([]) == 0
        Main.sum(null) == 0
    }

    def 'common prefixes gives true if the suffix and og string begin with the same string'() {
        expect:
        Main.commonPrefix(new Main.PrefixAndString('ab', 'abc'))
        Main.commonPrefix(new Main.PrefixAndString('abc', 'abc'))
        Main.commonPrefix(new Main.PrefixAndString('', 'abc'))
        !Main.commonPrefix(new Main.PrefixAndString('bc', 'abc'))
    }

    def 'prefixes gives all prefixes of a given string'() {
        when:
        List<String> prefixes = Main.prefixes('abcde')
        then:
        prefixes.size() == 6
        and:
        prefixes.contains('abcde')
        prefixes.contains('abcd')
        prefixes.contains('abc')
        prefixes.contains('ab')
        prefixes.contains('a')
        prefixes.contains('')
    }

    def 'common prefix length gives the correct prefix length'() {
        given:
        List<Main.PrefixAndString> suffixOriginalString = [new Main.PrefixAndString('ab', 'abcde'),
                                                           new Main.PrefixAndString('abcd', 'abcde'),
                                                           new Main.PrefixAndString('abcde', 'abcde')]
        expect:
        Main.commonPrefixLength(suffixOriginalString) == 5
    }

    def 'suffixes give all the suffixes'() {
        when:
        List<String> suffixes = Main.suffixes('abcde')
        then:
        suffixes.size() == 6
        suffixes.contains('abcde')
        suffixes.contains('bcde')
        suffixes.contains('cde')
        suffixes.contains('de')
        suffixes.contains('e')
        suffixes.contains('')
    }

    def 'common prefix gives a list of lengths of maximum suffixes whose are prefixes to the original strings'() {
        when:
        List<Integer> longestCommonPrefixesLengths = Main.commonPrefix(['ab', 'abc', 'abab', 'abcabcd', 'ababaa'])
        then:
        longestCommonPrefixesLengths[0] == 2
        longestCommonPrefixesLengths[1] == 3
        longestCommonPrefixesLengths[2] == 6
        longestCommonPrefixesLengths[3] == 10
        longestCommonPrefixesLengths[4] == 11
    }
}


