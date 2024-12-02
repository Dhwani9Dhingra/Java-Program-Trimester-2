import java.util.Scanner;

interface WaterConservationSystem {
    public int calculateTrappedWater(int[] blockHeights);
}

abstract class RainySeasonConservation implements WaterConservationSystem {
    public abstract int calculateTrappedWater(int[] blockHeights);
}

public class CityBlockConservation extends RainySeasonConservation {
    public int calculateTrappedWater(int[] blockHeights) {
        int n = blockHeights.length;
        if (n == 0) {
            return 0;
        }

        // Arrays to store the maximum height to the left and right of each block
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        // Fill the leftMax array
        leftMax[0] = blockHeights[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], blockHeights[i]);
        }

        // Fill the rightMax array
        rightMax[n - 1] = blockHeights[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], blockHeights[i]);
        }

        // Calculate the water trapped at each block
        int trappedWater = 0;
        for (int i = 0; i < n; i++) {
            trappedWater += Math.min(leftMax[i], rightMax[i]) - blockHeights[i];
        }

        return trappedWater;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of blocks:");
        int n = scanner.nextInt();

        int[] blockHeights = new int[n];

        System.out.println("Enter the block heights:");
        for (int i = 0; i < n; i++) {
            blockHeights[i] = scanner.nextInt();
        }

        CityBlockConservation cityBlockConservation = new CityBlockConservation();

        System.out.println("Trapped water: " + cityBlockConservation.calculateTrappedWater(blockHeights));
        scanner.close();
    }
}
