Please note I made deliberate choices of the technology used and how I write the code
1. I have decided to use spock for unit testing for very good reasons.
 i) Unit Tests to me are living documentation, thus Spock allows me to write my test method names as sentences. My code is telling a story, by
 looking at the tests you can see the story I am telling. So this decision is deliberate.
 ii)Spock also allows me to write data driven test/parameterized tests very easily
 iii)With spock I only need one test framework as mocking comes standard with it.
 
2. On all questions I deliberately by design do not cater for  "The client does not have any qualifying accounts The system displays an error message."
This is because all my methods return an empty page in these cases to the UI.

3. On all questions as well I also deliberately by design not cater for  "The UI needs to be aware of how to format the amount"
 This is because I return to the UI the currency which has a field - decimal places. This is meant to be used by the ui to format the amount
 
4. The queries scripts were tested agains SQL Server.