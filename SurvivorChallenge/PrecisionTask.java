// Lewis Going
// 04/19/2023
// CSE123
// C1: SurvivorChallenge
// TA: Logan Dinh / Ken Oh
import java.util.*;
// This class represents a precision task in the Survivor Challenge.
// The precision task requires a specific sequence of actions to be completed in order.
public class PrecisionTask extends Task {
    
    private List<String> actions;
    private List<String> requiredActions;
    private List<String> previousActions;

    // Creates a new precision task with the given list of required actions and description.
    // Includes a list of all possible actions.
    public PrecisionTask(List<String> requiredActions, String description) {
        super(description);
        this.requiredActions = requiredActions;
        previousActions = new ArrayList<>();
        actions = new ArrayList<>(Arrays.asList("jump", "run", "swim", "crawl", "climb"));
    }

    // Returns a list of all possible actions that can be taken on this task as Strings.
    public List<String> getActionOptions() {
        return actions;
    }

    // Returns true if the task is complete, false otherwise.
    public boolean isComplete() {
        return previousActions.equals(requiredActions);
    }

    // Takes an action as a String for the task.
    // Returns true if the action was successful, false otherwise.
    // Throws an IllegalArgumentException if the action is not valid.
    public boolean takeAction(String action) {
        if (!actions.contains(action)) {
            throw new IllegalArgumentException("Invalid action.");
        } else if (requiredActions.get(previousActions.size()).equals(action)) {
            previousActions.add(action);
            return true;
        } else {
            return false;
        }
    }
}
