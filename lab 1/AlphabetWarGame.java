import java.util.Scanner;

public class AlphabetWarGame {
    private char[] leftLetters;
    private int[] leftStrengths;
    private char[] rightLetters;
    private int[] rightStrengths;

    // Default constructor with default strengths
    public AlphabetWarGame() {
        // Left-side letters and their strengths
        leftLetters = new char[] { 'w', 'p', 'b', 's' };
        leftStrengths = new int[] { 4, 3, 2, 1 };

        // Right-side letters and their strengths
        rightLetters = new char[] { 'm', 'q', 'd', 'z' };
        rightStrengths = new int[] { 4, 3, 2, 1 };
    }

    // Overloaded constructor to accept custom strengths
    public AlphabetWarGame(char[] leftLetters, int[] leftStrengths, char[] rightLetters, int[] rightStrengths) {
        this.leftLetters = leftLetters;
        this.leftStrengths = leftStrengths;
        this.rightLetters = rightLetters;
        this.rightStrengths = rightStrengths;
    }

    // Method overloading: accepts a single word
    public String alphabetWar(String word) {
        int leftTotal = 0;
        int rightTotal = 0;

        for (char c : word.toCharArray()) {
            int index;
            if ((index = indexOf(leftLetters, c)) != -1) {
                leftTotal += leftStrengths[index];
            } else if ((index = indexOf(rightLetters, c)) != -1) {
                rightTotal += rightStrengths[index];
            }
        }

        return determineWinner(leftTotal, rightTotal);
    }

    // Method overloading: accepts separate left and right words
    public String alphabetWar(String leftWord, String rightWord) {
        int leftTotal = 0;
        int rightTotal = 0;

        for (char c : leftWord.toCharArray()) {
            int index;
            if ((index = indexOf(leftLetters, c)) != -1) {
                leftTotal += leftStrengths[index];
            }
        }

        for (char c : rightWord.toCharArray()) {
            int index;
            if ((index = indexOf(rightLetters, c)) != -1) {
                rightTotal += rightStrengths[index];
            }
        }

        return determineWinner(leftTotal, rightTotal);
    }

    // Helper method to determine the winner
    private String determineWinner(int leftTotal, int rightTotal) {
        if (leftTotal > rightTotal) {
            return "Left side wins!";
        } else if (rightTotal > leftTotal) {
            return "Right side wins!";
        } else {
            return "Let's fight again!";
        }
    }

    // Helper method to find the index of a character in an array
    private int indexOf(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1; // Character not found
    }

    // Main method to test the game with user input
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlphabetWarGame game = null;

        boolean continuePlaying = true;

        // Initial setup: ask the user if they want to use default strengths or customize them
        while (continuePlaying) {
            System.out.println("Do you want to use default strengths? (yes/no)");
            String defaultStrengthsChoice = scanner.nextLine();

            if (defaultStrengthsChoice.equalsIgnoreCase("yes")) {
                // Use default strengths
                game = new AlphabetWarGame();
            } else if (defaultStrengthsChoice.equalsIgnoreCase("no")) {
                // Customize strengths
                game = createCustomGame(scanner);
                if (game == null) {
                    System.out.println("Invalid inputs. Exiting the game.");
                    scanner.close();
                    return;
                }
            } else {
                System.out.println("Invalid input. Exiting the game.");
                scanner.close();
                return;
            }

            boolean playAgain = true;
            while (playAgain) {
                // Ask the user which method they want to use
                System.out.println("Choose game mode:");
                System.out.println("1. Enter a single word");
                System.out.println("2. Enter separate left and right words");
                String choice = scanner.nextLine();

                String result = "";

                if (choice.equals("1")) {
                    // Using the method that accepts a single word
                    System.out.println("Enter the word(s) for the Alphabet War:");
                    String input = scanner.nextLine();

                    result = game.alphabetWar(input);
                    System.out.println(result);
                } else if (choice.equals("2")) {
                    // Using the method that accepts separate left and right words
                    System.out.println("Enter the word for the left side:");
                    String leftWord = scanner.nextLine();

                    System.out.println("Enter the word for the right side:");
                    String rightWord = scanner.nextLine();

                    result = game.alphabetWar(leftWord, rightWord);
                    System.out.println(result);
                } else {
                    System.out.println("Invalid choice.");
                    continue; // Go back to the beginning of the inner loop
                }

                // Ask the user if they want to play again
                System.out.println("Do you want to play another round? (yes/no)");
                String answer = scanner.nextLine();

                if (answer.equalsIgnoreCase("no")) {
                    playAgain = false;
                    System.out.println("Thank you for playing the Alphabet War!");
                    continuePlaying = false; // Exit the outer loop as well
                } else if (answer.equalsIgnoreCase("yes")) {
                    // Ask if the user wants to change the strengths
                    System.out.println("Do you want to change the strengths for the next round? (yes/no)");
                    String changeStrengths = scanner.nextLine();

                    if (changeStrengths.equalsIgnoreCase("yes")) {
                        // Re-initialize the game with new strengths
                        game = null;
                        game = createCustomGame(scanner);
                        if (game == null) {
                            System.out.println("Invalid inputs. Exiting the game.");
                            scanner.close();
                            return;
                        }
                        // Break out of the inner loop to reset game settings
                        break;
                    } else if (changeStrengths.equalsIgnoreCase("no")) {
                        // Continue with existing strengths
                        // The inner loop continues
                    } else {
                        System.out.println("Invalid input. Exiting the game.");
                        playAgain = false;
                        continuePlaying = false;
                    }
                } else {
                    // Handle invalid input by exiting the game
                    System.out.println("Invalid input. Exiting the game.");
                    playAgain = false;
                    continuePlaying = false;
                }
            }
        }
        scanner.close();
    }

    // Helper method to create a custom game
    private static AlphabetWarGame createCustomGame(Scanner scanner) {
        System.out.println("Enter the letters for the left side (no spaces):");
        String leftLettersInput = scanner.nextLine();
        System.out.println("Enter the strengths for the left letters (comma-separated):");
        String[] leftStrengthsInput = scanner.nextLine().split(",");

        System.out.println("Enter the letters for the right side (no spaces):");
        String rightLettersInput = scanner.nextLine();
        System.out.println("Enter the strengths for the right letters (comma-separated):");
        String[] rightStrengthsInput = scanner.nextLine().split(",");

        // Validate inputs
        if (leftLettersInput.length() != leftStrengthsInput.length || rightLettersInput.length() != rightStrengthsInput.length) {
            System.out.println("The number of letters and strengths must match for each side.");
            return null;
        }

        // Convert inputs to arrays
        char[] leftLetters = leftLettersInput.toCharArray();
        int[] leftStrengths = new int[leftStrengthsInput.length];
        for (int i = 0; i < leftStrengthsInput.length; i++) {
            try {
                leftStrengths[i] = Integer.parseInt(leftStrengthsInput[i].trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid strength value for left side.");
                return null;
            }
        }

        char[] rightLetters = rightLettersInput.toCharArray();
        int[] rightStrengths = new int[rightStrengthsInput.length];
        for (int i = 0; i < rightStrengthsInput.length; i++) {
            try {
                rightStrengths[i] = Integer.parseInt(rightStrengthsInput[i].trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid strength value for right side.");
                return null;
            }
        }

        // Create a game with custom strengths
        return new AlphabetWarGame(leftLetters, leftStrengths, rightLetters, rightStrengths);
    }
}
