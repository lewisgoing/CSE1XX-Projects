// Lewis Going
// 04/19/2023
// CSE123
// C1: SurvivorChallenge
// TA: Logan Dinh / Ken Oh

import java.util.*;

// This class represents an endurance task in the Survivor Challenge.
// The endurance task requires a specific action to be completed a certain number of times.
public class EnduranceTask extends Task  {

    private String action;
    private int num;
    private List<String> actions;
    private List<String> previousActions;

    // Creates a new endurance task with the given action,
    // number of times needed to complete the action, and description.
    // Includes a list of all possible actions.
    public EnduranceTask(String action, int num, String description) {
        super(description);
        this.action = action;
        this.num = num;
        actions = new ArrayList<String>(Arrays.asList("jump", "run", "swim", "crawl", "climb"));
        previousActions = new ArrayList<>();
    }

    // Returns a list of all possible actions that can be taken on this task as Strings.
    public List<String> getActionOptions() {
        return actions;
    }

    // Returns true if the task is complete, false otherwise.
    public boolean isComplete() {
        return num == 0;
    }

    // Takes an action as a String for the task.
    // Returns true if the action was successful, false otherwise.
    // Throws an IllegalArgumentException if the action is not valid.
    public boolean takeAction(String action) {
        if (!actions.contains(action)) {
            throw new IllegalArgumentException("Invalid action.");
        }
        if (action.equals(this.action)) {
            previousActions.add(action);
            num--;
            return true;
        }
        return false;
    }

    // Decrements the number of times the action needs to be completed.
    protected void decrementNum() {
        num--;
    }
}
