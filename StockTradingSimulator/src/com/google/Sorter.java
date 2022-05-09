package com.google;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

//Would be used in the searchbar to search for approximate stocks (fully functional but not used in the program)
public class Sorter {
    List<Stock> stocks = new ArrayList<Stock>();
    {

    }

    public List<Stock> findByTitle(String searchedTitle) {
        List<LevenshteinResult> levenshteinDistances = new ArrayList<>();

        for (Stock stock : StockMarket.getStocks()) {
            int levenshteinDistance = LevenshteinDistance.calculate(searchedTitle.toUpperCase(), stock.getName().toUpperCase());
            levenshteinDistances.add(new LevenshteinResult(stock, levenshteinDistance));
        }

        levenshteinDistances.sort(Comparator.comparing(LevenshteinResult::getDistance));

        List<Stock> validStocks = new ArrayList<>();

        levenshteinDistances.forEach(entry -> {
            // calculate the difference in percent,
            // based of the calculated distance and the length of either the blog title or the title searched for.
            float difference = (float) entry.getDistance() / Math.max(searchedTitle.length(),
                    (entry.getSource()).getName().length());
            // Retrieve all blogposts whose titles are at least 30% similar to the search string
            if (difference <= .40f) {
                validStocks.add( entry.getSource());
            }
        });
        return validStocks;
    }


    public class LevenshteinDistance {

        public static int calculate(String str1, String str2) {
            int[][] replacementCosts = new int[str1.length() + 1][str2.length() + 1];

            for (int str1Index = 0; str1Index <= str1.length(); str1Index++) {

                for (int str2Index = 0; str2Index <= str2.length(); str2Index++) {

                    // if str1Index == 0, the cost is the cost of adding all the characters of str2
                    if (str1Index == 0) {
                        replacementCosts[str1Index][str2Index] = str2Index;

                        // if str2Index == 0, the cost is the cost of adding all the characters of str1
                    } else if (str2Index == 0) {
                        replacementCosts[str1Index][str2Index] = str1Index;

                        // if the characters at str1Index and str2Index are the same, the cost is the cost of the previous cell
                    } else {
                        replacementCosts[str1Index][str2Index] = min(replacementCosts[str1Index - 1][str2Index - 1]
                                        + costOfReplacement(str1.charAt(str1Index - 1), str2.charAt(str2Index - 1)),
                                replacementCosts[str1Index - 1][str2Index] + 1,
                                replacementCosts[str1Index][str2Index - 1] + 1);
                    }
                }
            }
            return replacementCosts[str1.length()][str2.length()];
        }


        /**
         * @param a the first character
         * @param b the second character
         * @return 1 if a != b, 0 otherwise
         */
        public static int costOfReplacement(char a, char b) {
            return a == b ? 0 : 1;
        }

        /**
         * Gets the minimum of the provided values
         *
         * @param numbers the values to get the minimum of
         * @return the minimum of the provided values
         */
        public static int min(int... numbers) {
            return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
        }

        private LevenshteinDistance() {
        }
    }

}
