import java.util.*;

public class MadTrucker {
    private static Scanner scanner = new Scanner(System.in);
    private int n;
    private ArrayList<Integer> mileages;
    private ArrayList<Integer> locations;
    private boolean[] used;
    private HashSet<Integer> forbidden;


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
        this.forbidden = new HashSet<>(locations);

    }

    /**
     * Finds a valid order to pour the cans.
     *
     * @return a list of indices representing the pour order
     */
    ArrayList<Integer> solve() {
        ArrayList<Integer> pourOrder = new ArrayList<>(n);
        Arrays.fill(used, false);

        // Build once, greedily, with no backtracking.
        buildOrder(pourOrder, 0);

        if (pourOrder.size() == n) {
            return pourOrder;
            }
        // Per assignment, inputs are always solvable; reaching here would be unexpected.
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
        // Base case: done.
        if (pourOrder.size() == n) {
            return pourOrder;
        }

        // Find the next forbidden point ahead of our current position
        int nextForbidden = Integer.MAX_VALUE;
        
        for (int f : locations) {
            if (f > currentDistance && f < nextForbidden) nextForbidden = f;
        }
        boolean hasNextForbidden = (nextForbidden != Integer.MAX_VALUE);

        //a can that jumps over the next forbidden (if one exists) is preferred.
        // while still not landing exactly on any forbidden point
        int pick = -1;
        if (hasNextForbidden) {
            for (int i = 0; i < n; i++) {
                if (used[i]) continue;
                    int nextDistance = currentDistance + mileages.get(i);
                if (forbidden.contains(nextDistance)) continue;       // not stop on forbidden locations
                if (nextDistance > nextForbidden) {                   // Jump past the next forbidden
                    pick = i;
                    break;
                }
            }
        }

        //If we couldn't jump past, pick any safe can that doesn't land on forbidden.
        if (pick == -1) {
            for (int i = 0; i < n; i++) {
                if (used[i]) continue;
                int nextDistance = currentDistance + mileages.get(i);
                if (!forbidden.contains(nextDistance)) {
                    pick = i;
                    break;
                }
            }
        }

        if (pick == -1) {
            // error trigger
            throw new IllegalStateException("No valid next can found.");
        }

        
        used[pick] = true;
        pourOrder.add(pick);

        // Recurse for the remaining cans
        return buildOrder(pourOrder, currentDistance + mileages.get(pick));
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
