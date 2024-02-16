// Lewis Going
// 04/19/2023
// CSE123
// C1: SurvivorChallenge
// TA: Logan Dinh / Ken Oh

import java.util.*;

// This class represents a puzzle task in the Survivor Challenge.
// The puzzle task requires a specific answer to be solved.
// The user can also request hints.
public class PuzzleTask extends Task {
    private String answer;
    private List<String> hints;
    private List<String> previousActions;
    private List<String> actions;

    // Creates a new puzzle task with the given answer, list of hints, and description.
    // Includes a list of all previous actions.
    public PuzzleTask(String answer, List<String> hints, String description) {
        super(description);
        this.answer = answer;
        this.hints = hints;
        previousActions = new ArrayList<String>();
        actions = new ArrayList<String>(Arrays.asList("hint", "solve <solution>"));
    }

    // Returns a list of all possible actions that can be taken on this task as Strings.
    // If the last action taken was a hint, the
    // description of the task will be appended with a hint.
    public String getDescription() {
        String description = super.getDescription();

        if (previousActions != null && !previousActions.isEmpty()) {
            String lastAction = previousActions.get(previousActions.size() - 1);

            if (lastAction != null && lastAction.startsWith("hint")) {
                description += "\n HINT: " + getHint();
            }
        }
        return description;
    }


    // Returns a list of all possible actions that can be taken on this task as Strings.
    public List<String> getActionOptions() {
        return actions;
    }

    // Returns true if the user answered correctly
    // ("solve <correct answer>"), false otherwise.
    public boolean isComplete() {
        return previousActions.contains("solve " + answer);
    }

    // Returns a hint from the list of hints.
    // Throws an IllegalStateException if there are no hints left.
    private String getHint() {
        if (hints.size() == 0) {
            throw new IllegalStateException("No hints left.");
        }
        return hints.remove(0);
    }

    // Takes an action on the task. Returns true if the action was successful, false otherwise.
    // Throws an IllegalArgumentException if the action is not valid.
    public boolean takeAction(String action) {
        if (!actions.contains(action) && !action.startsWith("solve")) {
            throw new IllegalArgumentException("Invalid action.");
        }

        boolean result = false;

        if (action.equals("solve " + answer)) {
            result = true;
        }
        else if (action.equals("hint")) {
            if (hints.size() > 0) {
                result = true;
            }
            else {
                result = false;
            }
        }
        
        previousActions.add(action);
        return result;
    }
}

