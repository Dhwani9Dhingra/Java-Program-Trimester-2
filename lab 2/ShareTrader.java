import java.util.*;

class ShareTrader {
    // Static variable to store the maximum profit
    static int maxProfit;

    // Static method to calculate the maximum profit with at most 2 transactions
    public static int findMaxProfit(int[] prices) {
        int n = prices.length;

        // If there are less than 2 prices, return 0 (no profit can be made)
        if (n < 2) {
            System.out.println("Not enough data to perform transactions.");
            return 0;
        }

        // Arrays to store the maximum profit by performing one transaction
        // before or on a given day and after or on a given day.
        int[] profit1 = new int[n];
        int[] profit2 = new int[n];

        // First transaction: Calculate maximum profit up to each day
        int minPrice = prices[0];
        profit1[0] = 0;
        System.out.println("\nCalculating maximum profit up to each day (First Transaction):");
        System.out.println("Day\tPrice\tMinPrice\tProfitUntilDay");
        for (int i = 1; i < n; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            profit1[i] = Math.max(profit1[i - 1], prices[i] - minPrice);
            System.out.println((i + 1) + "\t" + prices[i] + "\t" + minPrice + "\t\t" + profit1[i]);
        }

        // Second transaction: Calculate maximum profit from each day
        int maxPrice = prices[n - 1];
        profit2[n - 1] = 0;
        System.out.println("\nCalculating maximum profit from each day (Second Transaction):");
        System.out.println("Day\tPrice\tMaxPrice\tProfitFromDay");
        for (int i = n - 2; i >= 0; i--) {
            maxPrice = Math.max(maxPrice, prices[i]);
            profit2[i] = Math.max(profit2[i + 1], maxPrice - prices[i]);
            System.out.println((i + 1) + "\t" + prices[i] + "\t" + maxPrice + "\t\t" + profit2[i]);
        }

        // Combine the profits of both transactions to get the overall maximum profit
        maxProfit = 0;
        int buy1 = 0, sell1 = 0, buy2 = 0, sell2 = 0;
        System.out.println("\nCombining profits from both transactions:");
        System.out.println("Day\tProfitUntilDay\tProfitFromDay\tTotalProfit");
        for (int i = 0; i < n; i++) {
            int totalProfit = profit1[i] + profit2[i];
            System.out.println((i + 1) + "\t" + profit1[i] + "\t\t" + profit2[i] + "\t\t" + totalProfit);
            if (totalProfit > maxProfit) {
                maxProfit = totalProfit;
                // For simplicity, we can consider that the first transaction ends at day i
                // and the second transaction starts at day i+1
                sell1 = i;
                buy2 = i + 1;
            }
        }

        // Determine the days to buy and sell for each transaction
        // First transaction
        minPrice = prices[0];
        buy1 = 0;
        for (int i = 1; i <= sell1; i++) {
            if (prices[i] - minPrice == profit1[sell1]) {
                sell1 = i;
                break;
            }
            if (prices[i] < minPrice) {
                minPrice = prices[i];
                buy1 = i;
            }
        }

        // Second transaction
        if (buy2 < n) {
            maxPrice = prices[buy2];
            sell2 = buy2;
            for (int i = buy2 + 1; i < n; i++) {
                if (prices[i] - prices[buy2] == profit2[buy2]) {
                    sell2 = i;
                    break;
                }
                if (prices[i] > maxPrice) {
                    maxPrice = prices[i];
                    sell2 = i;
                }
            }
        } else {
            // No second transaction
            buy2 = -1;
            sell2 = -1;
        }

        // Print the buy and sell days
        System.out.println("\nTransactions to achieve maximum profit:");
        if (maxProfit > 0) {
            System.out.println("First Transaction: Buy on day " + (buy1 + 1) + " at price " + prices[buy1]
                    + ", Sell on day " + (sell1 + 1) + " at price " + prices[sell1]);
            if (buy2 != -1 && sell2 != -1 && buy2 < n) {
                System.out.println("Second Transaction: Buy on day " + (buy2 + 1) + " at price " + prices[buy2]
                        + ", Sell on day " + (sell2 + 1) + " at price " + prices[sell2]);
            }
        } else {
            System.out.println("No profitable transactions possible.");
        }

        System.out.println("\nMaximum Profit: " + maxProfit);
        return maxProfit;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueInput = true;

        while (continueInput) {
            // Read the number of days
            System.out.println("\nEnter the number of days:");
            int n = scanner.nextInt();

            // Validate the number of days
            if (n < 2) {
                System.out.println("At least two days are required to perform transactions.");
                continue;
            }

            // Read the stock prices for each day
            int[] prices = new int[n];
            System.out.println("Enter the stock prices for each day:");
            for (int i = 0; i < n; i++) {
                prices[i] = scanner.nextInt();
            }

            // Call the method to find maximum profit
            findMaxProfit(prices);

            // Ask the user if they want to input another set of prices
            System.out.println("\nDo you want to input another set of prices? (yes/no)");
            String answer = scanner.next();

            if (answer.equalsIgnoreCase("no")) {
                continueInput = false;
                System.out.println("Exiting the program.");
            } else if (!answer.equalsIgnoreCase("yes")) {
                System.out.println("Invalid input. Exiting the program.");
                continueInput = false;
            }
            // If the answer is "yes", the loop continues
        }

        scanner.close();
    }
}
