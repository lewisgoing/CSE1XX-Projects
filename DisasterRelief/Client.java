import java.util.*;

public class Client {
    private static Random rand = new Random();

    public static void main(String[] args) throws Exception {
        // List<Location> scenario = createRandomScenario(10, 10, 100, 1000, 100000);
        List<Location> scenario = createSimpleScenario();
        System.out.println(scenario);

        double budget = 2000;
        Allocation allocation = allocateRelief(budget, scenario);
        printResult(allocation, budget);
    }

    /* This method returns an allocation of relief
    * funds from a given set of locations and a budget
    * It returns the allocation that maximizes the number of people helped
    * If there are multiple allocations that maximize the number of people helped,
    * then it returns the allocation that minimizes the cost
    * If the budget is less than or equal to 0, or the list of
    * locations is empty, then the method returns an empty allocation 
    */
    public static Allocation allocateRelief(double budget, List<Location> sites) {
        return allocation(budget, sites);
    }

    /* This method returns an allocation of relief
    * funds from a given set of locations and a budget
    * It returns the allocation that maximizes the number of people helped
    * If there are multiple allocations that maximize the number of people helped,
    * then it returns the allocation that minimizes the cost
    * If the budget is less than or equal to 0, or the list of
    * locations is empty, then the method returns an empty allocation 
    */
    private static Allocation allocation(double budget, List<Location> sites) {
        if (sites.isEmpty() || budget <= 0) { // Base case
            return new Allocation();
        } else {
            
            Location curr = sites.get(0);
            List<Location> restSites = new ArrayList<>(sites.subList(1, sites.size()));

            Allocation without = allocation(budget, restSites); // Recursive call 1

            if (curr.getCost() <= budget) {
                Allocation with = allocation( // Recursive call 2
                    budget - curr.getCost(), restSites).withLoc(curr);
                return compareAllocation(with, without); // compare
            } else {
                return without; // can't afford the current location
            }
        }
    }

    // This method compares two allocations and returns the allocation that helps more people
    // If the allocations have the same number of people helped, then it returns the cheaper allocation
    public static Allocation compareAllocation(Allocation a1, Allocation a2) {
        if (a1.totalPeople() > a2.totalPeople()) {
            return a1;
        } else if (a1.totalPeople() < a2.totalPeople()) {
            return a2;
        } else {
            return a1.totalCost() < a2.totalCost() ? a1 : a2;
        }
    }

    // PROVIDED HELPER METHODS - **DO NOT MODIFY ANYTHING BELOW THIS LINE!**

    public static void printResult(Allocation alloc, double budget) {
        System.out.println("Result: ");
        System.out.println("  " + alloc);
        System.out.println("  People helped: " + alloc.totalPeople());
        System.out.printf("  Cost: $%.2f\n", alloc.totalCost());
        System.out.printf("  Unused budget: $%.2f\n", (budget - alloc.totalCost()));
    }

    public static List<Location> createRandomScenario(int numLocs, int minPop, int maxPop, double minCostPer, double maxCostPer) {
        List<Location> result = new ArrayList<>();

        for (int i = 0; i < numLocs; i++) {
            int pop = rand.nextInt(minPop, maxPop + 1);
            double cost = rand.nextDouble(minCostPer, maxCostPer) * pop;
            result.add(new Location("Location #" + i, pop, round2(cost)));
        }

        return result;
    }

    public static List<Location> createSimpleScenario() {
        List<Location> result = new ArrayList<>();
        result.add(new Location("Location #1", 50, 500));
        result.add(new Location("Location #2", 100, 700));
        result.add(new Location("Location #3", 60, 1000));
        return result;
    }

    private static double round2(double num) {
        return Math.round(num * 100) / 100.0;
    }
}
