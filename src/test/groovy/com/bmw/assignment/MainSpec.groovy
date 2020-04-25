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

    def 'Doubling integer @ multiplies the integers at the given indexex'() {
        given:
        List<Integer> integers = [0, 1, 2, 3, 4, 5]
        when:
        Main.doubleIntegerAt(integers, [1, 4])
        then:
        integers[1] == 2
        integers[0] == 0
        integers[2] == 2
        integers[3] == 3
        integers[4] == 8
        integers[5] == 5
        integers.size() == 6
    }

    def 'duplicates gives the positions of the first duplicate integers '() {
        given:
        List<Integer> integers = [1, 2, 1]
        expect:
        Main.duplicates(integers) == [0]
        integers.size() == 3
        Main.duplicates([1, 2, 3]) == []
        Main.duplicates([1, 10, 2, 3, 2, 3, 4, 4, 6, 6, 7, 4, 5, 10]) == [1, 3, 5, 9, 12]
        //This will be 1 2 2 3 3 4 4 4 5 6 6 7 10 10

    }

    def 'get minimum sum for given integers'() {
        expect:
        Main.getMinimumUniqueSum([0]) == 0
        Main.getMinimumUniqueSum([1]) == 1
        Main.getMinimumUniqueSum([1, 2]) == 3
        Main.getMinimumUniqueSum([1, 1]) == 3
        Main.getMinimumUniqueSum([1, 1, 3]) == 6
        Main.getMinimumUniqueSum([1, 1, 2]) == 7
        Main.getMinimumUniqueSum([1, 1, 2, 2]) == 15
        Main.getMinimumUniqueSum([2, 1, 2, 1]) == 9
    }

}


