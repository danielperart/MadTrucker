import java.util.*;

public class MadTrucker {
    private static Scanner scanner = new Scanner(System.in);
    private int n;
    private ArrayList<Integer> mileages;
    private ArrayList<Integer> locations;
    private boolean[] used;

    /**
     * Initializes the puzzle with the given parameters.
     *
     * @param n number of cans
     * @param mileages list of mileage values for the cans
     * @param locations list of forbidden refuel locations
     */
    void setup(int n, ArrayList<Integer> mileages, ArrayList<Integer> locations) {
        this.n = n;
        this.mileages = mileages;
        this.locations = locations;
        this.used = new boolean[n];
    }

    /**
     * Finds a valid order to pour the cans.
     *
     * @return a list of indices representing the pour order
     */
    ArrayList<Integer> solve() {
        ArrayList<Integer> pourOrder = new ArrayList<>(n);

        Arrays.fill(used, false);
        if (buildOrder(pourOrder, 0).size() == n) {
            return pourOrder;
        }

        throw new IllegalStateException("No valid order found (input should be solvable).");
    }

    /**
     * Recursive helper method to construct the pour order.
     *
     * @param pourOrder the current order of indices
     * @param currentDistance distance traveled so far
     * @return the updated pour order
     */
    ArrayList<Integer> buildOrder(ArrayList<Integer> pourOrder, int currentDistance) {
        if (pourOrder.size() == n) {
            return pourOrder;
        }

        for (int i = 0; i < n; i++) {
            if (used[i]) continue;

            int nextDistance = currentDistance + mileages.get(i);
            if (locations.contains(nextDistance)) continue;

            used[i] = true;
            pourOrder.add(i);

            buildOrder(pourOrder, nextDistance);

            if (pourOrder.size() == n) return pourOrder;

            pourOrder.remove(pourOrder.size() - 1);
            used[i] = false;
        }
        return pourOrder;
    }

    /**
     * Reads input and solves exactly one test case.
     */
    void run() {
        n = scanner.nextInt();

        ArrayList<Integer> mileages = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            mileages.add(scanner.nextInt());
        }

        ArrayList<Integer> locations = new ArrayList<>(Math.max(0, n - 1));
        for (int i = 0; i < n - 1; i++) {
            locations.add(scanner.nextInt());
        }

        setup(n, mileages, locations);
        ArrayList<Integer> order = solve();

        for (int i = 0; i < order.size(); i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(order.get(i));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new MadTrucker().run();
    }
}
