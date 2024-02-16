// Lewis Going
// 04/19/2023
// CSE123
// C1: SurvivorChallenge
// TA: Logan Dinh / Ken Oh
import java.util.*;

// This class represents a strength task in the Survivor Challenge.
// The strength task requires a specific weight to be lifted a certain number of times.
public class StrengthTask extends EnduranceTask {

    private int weight;

    // Creates a new strength task with the given action, number of times needed
    // to complete the action, description, and weight needed to complete the task.
    // Includes a list of all possible actions.
    public StrengthTask(String action, int num, String description, int weight) {
        super(action, num, description);
        this.weight = weight;
        
    }

    // Returns a list of all possible actions that can be taken on this task as Strings.
    @Override
    public List<String> getActionOptions() {
        return new ArrayList<String>(Arrays.asList("50", "60", "70", "80",
                "90", "100", "<weight>"));
    }

    // Takes an action (number) as a String for the task.
    // If the action is "<weight>", the weight lifted is the weight needed to complete the task.
    // Returns true if the action was successful, false otherwise.
    // Throws an IllegalArgumentException if the action is not valid
    // or if the weight lifted is not enough to complete the task.
    @Override
    public boolean takeAction(String action) {
        int inputWeight = Integer.parseInt(action);
        if (inputWeight < weight) {
            throw new IllegalArgumentException("Lift not successful");
        }
        if (weight != inputWeight) {
            return false;
        } else {
            this.decrementNum();
            return true;
        }
    }
}
