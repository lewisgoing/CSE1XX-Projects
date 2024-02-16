// Lewis Going
// 5/3/2023
// CSE123
// P1: MiniGit

// This class represents a repository of commits.
// Each repository has a name and a head commit.

import java.util.*;

public class Repository {
    private String name;
    private Commit head;

    // Creates a new, empty repository with the specified String name
    // Throws IllegalArgumentException if name is null or empty
    public Repository(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        head = null;
    }


    // Creates a new commit with the specified message
    // and adds it to the repository as the head
    // commit, while maintaining the commit history.
    // Returns the ID of the new commit as a String.
    // Throws IllegalArgumentException if message is null or empty
    public String commit(String message) {
        if (message == null || message.equals("")) {
            throw new IllegalArgumentException();
        }

        head = new Commit(message, head);
        return head.id;
    }

    // Returns the ID of the head commit as a String. Returns null if the repository is empty.
    public String getRepoHead() {
        if (head == null) {
            return null;
        }

        return head.id;
    }

    // Returns a string representation of the repository.
    // If the repository is empty, returns "<name> - No commits".
    public String toString() {
        if (head == null) {
            return name + " - No commits";
        }

        return name + " - Current head: " + head.toString();
    }

    // Returns true if the repository contains a
    // commit with the specified ID String, false otherwise.
    public boolean contains(String targetId) {
        Commit curr = head;

        while (curr != null) {
            if (curr.id.equals(targetId)) {
                return true;
            }

            curr = curr.past;
        }

        return false;
    }

    // Returns a string representation of the commit history of the repository,
    // starting at the head commit and going backwards 'n' commits.
    // If 'n' is greater than the number of commits in the repository,
    // the entire history is returned.
    // Throws IllegalArgumentException if 'n' is less than or equal to 0.
    // If the repository is empty, returns an empty string.
    public String getHistory(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be a positive number.");
        }

        if (head == null) {
            return "";
        }

        Commit curr = head;
        int count = 0;
        String result = "";

        while (curr != null && count < n) {
            if (curr.past == null || count == n - 1) {
                result += curr.toString();
                return result;
            }

            result += curr.toString() + "\n";
            count++;
            curr = curr.past;
        }

        return result;
    }

    // Resets the head of this repository to be 'n' commits in the past of the current head.
    // If 'n' is greater than the number of commits in the repository,
    // the head is set to null.
    // Throws IllegalArgumentException if n is less than or equal to 0.
    public void reset(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        Commit curr = head;
        int count = 0;

        while (curr != null && count < n) {
            curr = curr.past;
            count++;
        }

        head = curr;
    }

    // Removes the commit with the specified ID String from the
    // repository while maintaining the commit history.
    // Returns true if the commit was removed,
    // false if no match was found in the repository.
    public boolean drop(String targetId) {
        if (head == null) { // empty case
            return false;
        }

        if (head.id.equals(targetId)) { // front case
            head = head.past;
            return true;
        }

        Commit curr = head;

        while (curr.past != null) {
            if (curr.past.id.equals(targetId)) {
                curr.past = curr.past.past;
                return true;
            } else {
                curr = curr.past;
            }
        }

        return false;
    }

    // Creates a new commit that combines the commit with the specified ID
    // with the one immediately before it and replaces the commit with ID
    // targetId with this new commit, preserving the rest of the history.
    // Both commit messages are combined with a "/" in between.
    // If the target commit has no commits before it, no changes are made.
    // Returns true if a valid squash occurred, false otherwise.
    public boolean squash(String targetId) {
        if (head == null || head.past == null) { // empty case
            return false;
        }

        if (head.id.equals(targetId)) { // front case
            head = new Commit("SQUASHED: " + head.message +
                                "/" + head.past.message, head.past.past);
            return true;
        }

        Commit curr = head;

        while (curr.past != null) {
            if (curr.past.id.equals(targetId) && curr.past.past != null) {
                curr.past = new Commit("SQUASHED: " + curr.past.message +
                                        "/" + curr.past.past.message, curr.past.past.past);
                return true;
            } else {
                curr = curr.past;
            }
        }
        return false;
    }

    // This class represents a commit in the repository.
    // Each commit has a unique ID String, a message String,
    // and a reference to the commit that came before it.
    public static class Commit {
        public final String id;
        public final String message;
        public Commit past;

        // Creates a new commit with the specified message and reference to the past commit.
        public Commit(String message, Commit past) {
            this.id = getNewId();
            this.message = message;
            this.past = past;
        }

        // Creates a new commit with the specified message and a past reference to null.
        public Commit(String message) {
            this(message, null);
        }

        // Returns a string representation of the commit.
        public String toString() {
            return id + ": " + message;
        }

        // Returns a new unique ID String.
        private static String getNewId() {
            return UUID.randomUUID().toString();
        }
    }
}
