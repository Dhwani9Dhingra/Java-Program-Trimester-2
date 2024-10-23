import java.util.*;

public class TopKFrequentNumbers {
    // Static variable to store the input array
    static int[] numbers;

    // Static method to find the top K numbers with highest frequencies
    public static void findTopKFrequentNumbers(int K) {
        // HashMap to store the frequency of each number
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();

        // Populate the frequency map
        for (int num : numbers) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // Create a list from the elements of the frequency map
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(frequencyMap.entrySet());

        // Sort the list based on frequency and then by number value
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
                if (b.getValue().compareTo(a.getValue()) == 0) {
                    return b.getKey().compareTo(a.getKey());
                } else {
                    return b.getValue().compareTo(a.getValue());
                }
            }
        });

        // Print the top K numbers
        System.out.print("Top " + K + " numbers: ");
        for (int i = 0; i < K && i < list.size(); i++) {
            System.out.print(list.get(i).getKey() + " ");
        }
        System.out.println();
    }

    // Main method to run the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueInput = true;
        while (continueInput) {
            // Read the array from the user
            System.out.println("Enter the number of elements in the array:");
            int n = scanner.nextInt();
            numbers = new int[n];
            System.out.println("Enter " + n + " integers:");
            for(int i = 0; i < n; i++) {
                numbers[i] = scanner.nextInt();
            }

            // Read the value of K
            System.out.println("Enter the value of K:");
            int K = scanner.nextInt();

            // Check if K is valid
            if(K <= 0 || K > n) {
                System.out.println("Invalid value of K. It should be between 1 and " + n);
            } else {
                // Call the method to find top K frequent numbers
                findTopKFrequentNumbers(K);
            }

            // Ask the user if they want to input another array
            System.out.println("Do you want to input another array? (yes/no)");
            String answer = scanner.next();
            if(answer.equalsIgnoreCase("no")) {
                continueInput = false;
                System.out.println("Exiting the program.");
            }
        }
        scanner.close();
    }
}
