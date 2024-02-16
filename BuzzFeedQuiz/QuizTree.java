// Lewis Going
// CSE 123
// P03: Huffman Code
// TAs: Logan Dinh && Ken Oh
// 6/2/2023

import java.util.*;
import java.io.*;

// This class represents a quiz tree as a set of questions
// and answers that can be traversed to determine a result.
public class QuizTree {
    private QuizTreeNode root;

    // Constructs a quiz tree from the given file.
    // The input file should be structured with one question per line.
    // Question lines are represented with two choices separated by a slash (/).
    // The first represents the left path and the second represents the right path.
    // Lines beginning with "END:" represent a result.
    public QuizTree(Scanner inputFile) {

        root = buildTree(inputFile);

    }

    // Constructs a quiz tree from the given file.
    // The input file should be structured with one question per line.
    // Question lines are represented with two choices separated by a slash (/).
    // The first represents the left path and the second represents the right path.
    // Lines beginning with "END:" represent a result.
    private QuizTreeNode buildTree(Scanner inputFile) {
        if (!inputFile.hasNext()) {
            return null;
        }

        String question = inputFile.nextLine();

        if (question.contains("END:")) {

            return new QuizTreeNode(question.substring(4));

        } else {

            QuizTreeNode left = buildTree(inputFile);
            QuizTreeNode right = buildTree(inputFile);
            return new QuizTreeNode(question, left, right);

        }
    }

    // Traverses the quiz tree and prompts the user with questions
    // until a result is reached. Once a result is reached, the result is printed.
    // The questions are not case-sensitive. If the user enters an invalid response,
    // the question is repeated until a valid response is entered.
    public void takeQuiz(Scanner console) {

        traverseQuiz(console, root);

    }

    // Traverses the quiz tree and prompts the user with questions
    // until a result is reached. Once a result is reached, the result is printed.
    // The questions are not case-sensitive. If the user enters an invalid response,
    // the question is repeated until a valid response is entered.
    private void traverseQuiz(Scanner console, QuizTreeNode node) {
        if (!node.isLeaf()) {
            String[] options = node.data.split("/");
            String left = options[0];
            String right = options[1];

            System.out.print("Do you prefer " + left + " or " + right + "? ");
            String answer = console.nextLine();

            if (answer.equalsIgnoreCase(left)) {
                traverseQuiz(console, node.left);
            } else if (answer.equalsIgnoreCase(right)) {
                traverseQuiz(console, node.right);
            } else {
                System.out.println("Invalid response; try again.");
                traverseQuiz(console, node);
            }

        } else {
            System.out.println("Your result is: " + node.data);
        }
    }

    /* EXTENSIONS */

    // Exports the quiz tree to the given file with one question / result per line
    // Question lines are represented with two choices separated by a slash (/).
    // The first represents the left path and the second represents the right path.
    // Lines beginning with "END:" represent a result.
    public void export(PrintStream outputFile) {
        export(outputFile, root);
    }

    // Exports the quiz tree to the given file.
    // Question lines are represented with two choices separated by a slash (/).
    // The first represents the left path and the second represents the right path.
    // Lines beginning with "END:" represent a result.
    private void export(PrintStream outputFile, QuizTreeNode node) {
        if (node != null) {

            if (node.isLeaf()) {
                outputFile.println("END:" + node.data);
            } else {
                outputFile.println(node.data);
            }

            export(outputFile, node.left);
            export(outputFile, node.right);

        }
    }

    // Adds a question to the quiz tree. The question should be added to the
    // first leaf node that contains the result toReplace. The left choice
    // should lead to leftResult, and the right choice should lead to rightResult.
    // If the result toReplace is not found in the tree, the tree will not be modified.
    public void addQuestion(String toReplace, String leftChoice,
                            String rightChoice, String leftResult, String rightResult) {

        root = addQuestion(root, toReplace, leftChoice, rightChoice, leftResult, rightResult);

    }

    // Adds a question to the quiz tree. The question should be added to the
    // first leaf node that contains the result toReplace. The left choice
    // should lead to leftResult, and the right choice should lead to rightResult.
    // If the result toReplace is not found in the tree, the tree will not be modified.
    private QuizTreeNode addQuestion(QuizTreeNode node, String toReplace, String leftChoice,
                                     String rightChoice, String leftResult, String rightResult) {
        if (node != null) {
            if (node.isLeaf() && node.data.equalsIgnoreCase(toReplace)) {

                String choices = leftChoice + "/" + rightChoice;

                QuizTreeNode newLeft = new QuizTreeNode(leftResult);
                QuizTreeNode newRight = new QuizTreeNode(rightResult);
                QuizTreeNode newQuestion = new QuizTreeNode(choices, newLeft, newRight);

                return newQuestion;
            } else {

                node.left = addQuestion(node.left, toReplace,
                        leftChoice, rightChoice, leftResult, rightResult);
                node.right = addQuestion(node.right, toReplace,
                        leftChoice, rightChoice, leftResult, rightResult);

            }
        }

        return node;
    }

    // This class represents a question/result in a quiz tree.
    // A question has two answers, each of which leads to another question.
    // A result is indicated by a null left and right child.
    public static class QuizTreeNode {

        public String data;
        public QuizTreeNode left;
        public QuizTreeNode right;

        // Constructs a leaf node with the given question.
        public QuizTreeNode(String question) {
            this.data = question;
        }

        // Constructs a branch node with the given question and left and right children.
        public QuizTreeNode(String question, QuizTreeNode left, QuizTreeNode right) {
            this.data = question;
            this.left = left;
            this.right = right;
        }

        // Returns true if this node is a leaf node (has no children).
        public boolean isLeaf() {
            return left == null && right == null;
        }
    }
}
