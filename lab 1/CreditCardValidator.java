import java.util.Scanner;

public class CreditCardValidator {

    // Constructor
    public CreditCardValidator(long ccNumber) {
        String ccNumberStr = Long.toString(ccNumber);
        int length = ccNumberStr.length();

        if (length < 8 || length > 9) {
            System.out.println("Invalid credit card number");
        } else {
            // Step a: Remove the last digit of the ccNumber.
            long lastDigit = ccNumber % 10;
            long remainingNumber = ccNumber / 10;
            System.out.println("Step a: Last digit = " + lastDigit + ", remaining number = " + remainingNumber);

            // Step b: Reverse the remaining digits.
            long reversedNumber = reverseNumber(remainingNumber);
            System.out.println("Step b: Reverse the number: " + reversedNumber);

            // Step c: Double the digits in odd-numbered positions.
            String afterDoubling = processDigits(reversedNumber);
            System.out.println("Step c: After doubling the odd-positioned digits: " + afterDoubling);

            // Step d: Add up all the digits.
            int sumOfDigits = sumDigitsInString(afterDoubling);
            System.out.println("Step d: Sum of all digits = " + sumOfDigits);

            // Step e: Subtract the last digit obtained in step a from 10.
            int resultOfStepE = 10 - (sumOfDigits % 10);
            System.out.println("Step e: 10 - (" + sumOfDigits + " % 10) = " + resultOfStepE);

            // Step f: Compare the result of step e with the last digit obtained in step a.
            if (resultOfStepE == lastDigit) {
                System.out.println("Step f: " + resultOfStepE + " matches " + lastDigit + ", the card is valid.");
            } else {
                System.out.println("Step f: " + resultOfStepE + " does not match " + lastDigit + ", the card is invalid.");
            }
        }
    }

    private long reverseNumber(long number) {
        long reversed = 0;
        while (number != 0) {
            long digit = number % 10;
            reversed = reversed * 10 + digit;
            number /= 10;
        }
        return reversed;
    }

    private String processDigits(long number) {
        String numberStr = Long.toString(number);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numberStr.length(); i++) {
            int digit = Character.getNumericValue(numberStr.charAt(i));
            if ((i % 2) == 0) { // Odd positions (starting from index 0)
                int doubled = digit * 2;
                if (doubled > 9) {
                    doubled = sumOfDigits(doubled);
                }
                sb.append(doubled);
            } else {
                sb.append(digit);
            }
        }
        return sb.toString();
    }

    private int sumOfDigits(int number) {
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    private int sumDigitsInString(String numStr) {
        int sum = 0;
        for (int i = 0; i < numStr.length(); i++) {
            int digit = Character.getNumericValue(numStr.charAt(i));
            sum += digit;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("Enter the card number:");
        Scanner sc = new Scanner(System.in);
        long ccNumber = sc.nextLong();
        // Create an instance of CreditCardValidator
        new CreditCardValidator(ccNumber);
        sc.close();
    }
}
