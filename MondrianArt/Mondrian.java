// Lewis Going
// CSE 123
// 5.4.2023
// TA: Logan Dinh && Ken Oh

import java.util.*;
import java.awt.*;

// This class is a Mondrian painting generator. It can paint basic and complex
// Mondrian paintings from given 2D arrays of pixels.
public class Mondrian {
    private Random r;

    // Creates a new Mondrian object with a random number generator.
    public Mondrian() {
        r = new Random();
    }

    /** Paints basic Mondrian art onto the given 2D array of pixels.
     parameters:
     Color[][] pixels - 2D array of pixels
     **/
    public void paintBasicMondrian(Color[][] pixels) {
        paintMondrian(pixels, 0, 0, pixels.length , pixels[0].length);
    }

    /** Paints a set of rectangles onto the given 2D array of pixels, splitting
     * them randomly according to an algorithm. Then, it fills them in with
     * random colors from the colors white, red, yellow, and cyan and
     * draws a black border around each rectangle.
     parameters:
     - Color[][] pixels - 2D array of pixels
     - int x - starting point x-axis
     - int y - starting point y-axis
     - int width - width of region
     - int height - height of region
     **/
    private void paintMondrian(Color[][] pixels, int x, int y, int width, int height) {
        if (width < pixels[0].length / 4 && height < pixels.length / 4) { // Base Case
            fillRegion(pixels, x, y, width, height); // no split, fill region
        } else {
            // split vertically/horizontally
            if (width >= pixels[0].length / 4 && height >= pixels.length / 4) {
                int split = getRandomSplit(width);
                paintMondrian(pixels, x, y, split, height); // left side
                paintMondrian(pixels, x + split, y, width - split, height); // right side

                split = getRandomSplit(height);
                paintMondrian(pixels, x, y, width, split); // top side
                paintMondrian(pixels, x, y + split, width, height - split); // bottom side

            } else if (height >= pixels.length / 4) { // split horizontally

                int split = getRandomSplit(height);
                paintMondrian(pixels, x, y, width, split);
                paintMondrian(pixels, x, y + split, width, height - split);

            } else if (width >= pixels[0].length / 4) { // split vertically

                int split = getRandomSplit(width);
                paintMondrian(pixels, x, y, split, height);
                paintMondrian(pixels, x + split, y, width - split, height);

            }
        }
    }

    // Returns a random number between the given length - 1 and 1.
    // parameters:
    //  - int length
    private int getRandomSplit(int length) {
        return r.nextInt(length - 1) + 1;
    }

    /** Fills in the given region with random colors from the colors white, red,
     * yellow, and cyan and draws a black border around the region.
     parameters:
     - Color[][] pixels - 2D array of pixels
     - int x - starting point x-axis
     - int y - starting point y-axis
     - int width - width of region
     - int height - height of region
     **/
    private void fillRegion(Color[][] pixels, int x, int y, int width, int height) {
        Color[] colors = {Color.RED, Color.YELLOW, Color.CYAN, Color.WHITE};
        int randColor = r.nextInt(4);

        drawBorder(pixels, x, y, width, height);

        for (int i = x + 1; i < x + width - 1 && i < pixels.length - 1; i++) { // Fill in region
            for (int j = y + 1; j < y + height - 1 && j < pixels[i].length - 1; j++) {
                pixels[i][j] = colors[randColor];
            }
        }
    }

    /** Draws a black border around the given region.
     parameters:
     - Color[][] pixels - 2D array of pixels
     - int x - starting point x-axis
     - int y - starting point y-axis
     - int width - width of region
     - int height - height of region
     **/
    private void drawBorder(Color[][] pixels, int x, int y, int width, int height) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                pixels[i][j] = Color.BLACK;
            }
        }
    }

    /** Paints a Mondrian painting that determines the colors of the
     * rectangles based on their position in the painting. The top left
     * region is red, the top right region is green, the bottom left region
     * is orange, and the bottom right region is blue.
     parameters:
     - Color[][] pixels - 2D array of pixels
     **/
    public void paintComplexMondrian(Color[][] pixels) {
        paintComplexMondrian(pixels, 0, 0, pixels.length , pixels[0].length);
    }

    /** Paints a Mondrian painting that determines the colors of the
     * rectangles based on their position in the painting. The top left
     * region is red, the top right region is green, the bottom left region
     * is orange, and the bottom right region is blue.
     parameters:
     - Color[][] pixels - 2D array of pixels
     - int x - starting point x-axis
     - int y - starting point y-axis
     - int width - width of region
     - int height - height of region
     */
    private void paintComplexMondrian(Color[][] pixels, int x, int y, int width, int height) {
        if (width < pixels[0].length / 4 && height < pixels.length / 4) { // Base Case
            fillComplexRegion(pixels, x, y, width, height); // no split, fill region
        } else {
            // split vertically/horizontally
            if (width >= pixels[0].length / 4 && height >= pixels.length / 4) {
                int split = getRandomSplit(width);
                paintComplexMondrian(pixels, x, y, split, height); // left side
                paintComplexMondrian(pixels, x + split, y, width - split, height); // right side

                split = getRandomSplit(height);
                paintComplexMondrian(pixels, x, y, width, split); // top side
                paintComplexMondrian(pixels, x, y + split, width, height - split); // bottom side

            } else if (height >= pixels.length / 4) { // split horizontally

                int split = getRandomSplit(height);
                paintComplexMondrian(pixels, x, y, width, split);
                paintComplexMondrian(pixels, x, y + split, width, height - split);

            } else if (width >= pixels[0].length / 4) { // split vertically

                int split = getRandomSplit(width);
                paintComplexMondrian(pixels, x, y, split, height);
                paintComplexMondrian(pixels, x + split, y, width - split, height);

            }
        }
    }

    /** Returns a color based on the quadrant of the given pixel. The top left
    * region is red, the top right region is green, the bottom left region
    * is orange, and the bottom right region is blue.
     parameters:
     - Color[][] pixels - 2D array of pixels
     - int x - starting point x-axis
     - int y - starting point y-axis
     - int width - width of region
     - int height - height of region
     **/
    private Color getRegionColor(Color[][] pixels, int x, int y, int width, int height) {
        int xCenter = pixels.length / 2;
        int yCenter = pixels[0].length / 2;

        if (x + width / 2 < xCenter) {
            if (y + height / 2 < yCenter) {
                return Color.RED;
            } else {
                return Color.GREEN;
            }
        } else {
            if (y + height / 2 < yCenter) {
                return Color.BLUE;
            } else {
                return Color.ORANGE;
            }
        }
    }

    /** Fills in the given region with a color based on the quadrant of the
     * given pixel and draws a black border around the region.
     * The top left region is red, the top right region is green, the bottom
     * left region is orange, and the bottom right region is blue.
     parameters:
     - Color[][] pixels - 2D array of pixels
     - int x - starting point x-axis
     - int y - starting point y-axis
     - int width - width of region
     - int height - height of region
     **/
    private void fillComplexRegion(Color[][] pixels, int x, int y, int width, int height) {
        Color color = getRegionColor(pixels, x, y, width, height);

        drawBorder(pixels, x, y, width, height);

        for (int i = x + 1; i < x + width - 1 && i < pixels.length - 1; i++) { // Fill in region
            for (int j = y + 1; j < y + height - 1 && j < pixels[i].length - 1; j++) {
                pixels[i][j] = color;
            }
        }
    }
}
