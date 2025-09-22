import java.util.*;


/**
 * Takes a list of distances and forbidden locations
 * then uses recursion to find any possible combination
 * where all the distances are used and no forbidden location is reached
 * and finally outputs the combination to the user.
 *
 * Purpose and usageL
 * Reads a list of numbers representing cans of fuel and locations which do not allow stopping.
 * The mad trucker does not have brakes so he has to use cans as distance measurements.
 * There are however some locations which do not allow stopping so the trucker has to avoid those.
 * The core idea of the program is to output any possible combination of cans
 * where every can ends up being used and the trucker did not stop on
 * any locations which do not allow stopping.
 *
 * Input:
 * The program reads from the following input
 * N
 * a1 a2 a3 a4  ... aN
 * b1 b2 b3 b4 ... b(N-1)
 * where;
 * - N is the amount of cans.
 * - a1...aN is the amount of distance each can can reach.
 * - b1...b(N-1) are the locations which do not allow stopping.
 * - every input is larger than 1 and smaller than 500000.
 * - inputs can be in a straight line, to improve readability they are explained
 * as if they are not in the same line.
 *
 * Output:
 * - A single line representing a possible combination of cans where every can is used
 * and no forbidden location is reached.
 *
 * @author Jan Modun
 * @ID 2281031
 * @author Daniel Pérez Artajona
 * @ID 2124378
 *
 */
public class MadTrucker {

    private static Scanner scanner = new Scanner(System.in);
    private int n;
    private ArrayList<Integer> mileages;
    private boolean[] used;
    private HashSet<Integer> forbidden;

    /**
     * This function initializes the puzzle with the given parameters.
     *
     * @param n number of cans
     * @param mileages list of mileage values for the cans
     * @param locations list of forbidden refuel locations
     */
    void setup(int n, ArrayList<Integer> mileages, ArrayList<Integer> locations) {
        this.n = n;
        this.mileages = mileages;
        this.used = new boolean[n];
        this.forbidden = new HashSet<>(locations);

    }

    /**
     * This function finds a valid order to pour the cans using the recursion helper function.
     *
     * @return a list of indices representing the pour order
     */
    ArrayList<Integer> solve() {
        ArrayList<Integer> pourOrder = new ArrayList<>(n);
        Arrays.fill(used, false);

        // total distance, by computing sum of cans.
        int total = 0;
        for (int v : mileages) {
            total += v;
        }

        // build order method
        buildOrder(pourOrder, total);

        if (pourOrder.size() == n) {
            return pourOrder;
        }

        throw new IllegalStateException("No valid order found (input should be solvable).");
    }


    /**
     * This function uses the remaining distance and the can index to check
     * if the can can be poured.
     *
     * @param remaining - the distance remaining
     * @param index - the can index
     * @return - boolean value which represents the can can be used
     */
    boolean validateCan(int remaining, int index) {
        if (used[index]) {
            return false;
        }

        int mileage = mileages.get(index);
        int prevStop = remaining - mileage;

        if (prevStop < 0) {
            return false;
        }

        return prevStop == 0 || !forbidden.contains(prevStop);
    }

    /**
     * This function picks a non-forbidden and valid can
     * to continue the can selection recursive process.
     *
     * @param remaining - the distance remaining
     * @return - the index of the picked can
     */
    int getValidCan(int remaining) {

        int pickedCan = -1;

        for (int i = 0; i < n; i++) {
            boolean isViable = validateCan(remaining, i);

            if (isViable) {
                return i;
            }
        }

        return pickedCan;
    }

    /**
     * This function is a recursive helper method to construct the pour order.
     *
     * @param pourOrder the current order of indices
     * @param remaining distance left to travel
     */
    void buildOrder(ArrayList<Integer> pourOrder, int remaining) {

        if (remaining == 0) {
            return;
        }

        // Then pick a non-forbidden can
        int pickedCan = getValidCan(remaining);

        if (pickedCan == -1) {
            throw new IllegalStateException("No valid next can found.");
        }

        // save and recurse
        used[pickedCan] = true;
        buildOrder(pourOrder, remaining - mileages.get(pickedCan));

        // append after recursion
        pourOrder.add(pickedCan);
    }

    /**
     * This function reads the input and solves exactly one test case.
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

        scanner.close();

        setup(n, mileages, locations);
        ArrayList<Integer> order = solve();

        for (int i = 0; i < order.size(); i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(order.get(i));
        }
        System.out.println();
    }

    /**
     * This function initializes the program and runs the main function.
     */
    public static void main(String[] args) {
        new MadTrucker().run();
    }
}
