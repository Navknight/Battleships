import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameHelper {
    static final int HORIZONTAL_INCREMENT = 1; // A better way to represent these two
    private static final String ALPHABET = "abcdefg";
    private static final int GRID_LENGTH = 7;
    static final int VERTICAL_INCREMENT = GRID_LENGTH; // things is an enum (see Appendix B)
    private static final int GRID_SIZE = 49;
    private static final int MAX_ATTEMPTS = 200;
    private final int[] grid = new int[GRID_SIZE];
    private final Random random = new Random();
    private int shipCount = 0;

    public String getUserInput(String prompt) {
        System.out.print(prompt + ": ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toLowerCase();
    } //end getUserInput

    public ArrayList<String> placeship(int shipSize) {
        // holds index to grid (0 - 48)
        int[] shipCoords = new int[shipSize]; // current candidate co-ordinates
        int attempts = 0; // current attempts counter
        boolean success = false; // flag = found a good location?
        shipCount++; // nth ship to place
        int increment = getIncrement(); // alternate vert & horiz alignment
        while (!success & attempts++ < MAX_ATTEMPTS) { // main search loop
            int location = random.nextInt(GRID_SIZE); // get random starting point
            for (int i = 0; i < shipCoords.length; i++) { // create array of proposed coords
                shipCoords[i] = location; // put current location in array
                location += increment; // calculate the next location
            }
            // System.out.println("Trying: " + Arrays.toString(shipCoords));
            if (shipFits(shipCoords, increment)) { // ship fits on the grid?
                success = coordsAvailable(shipCoords); // ...and locations aren't taken?
            } // end loop
        } // end while
        savePositionToGrid(shipCoords); // coords passed checks, save
        ArrayList<String> alphaCells = convertCoordsToAlphaFormat(shipCoords);
        // System.out.println("Placed at: "+ alphaCells);
        return alphaCells;
    } //end placeship

    private boolean shipFits(int[] shipCoords, int increment) {
        int finalLocation = shipCoords[shipCoords.length - 1];
        if (increment == HORIZONTAL_INCREMENT) {
            // check end is on same row as start
            return calcRowFromIndex(shipCoords[0]) == calcRowFromIndex(finalLocation);
        } else {
            return finalLocation < GRID_SIZE; // check end isn't off the bottom
        }
    } //end shipFits

    private boolean coordsAvailable(int[] shipCoords) {
        for (int coord : shipCoords) { // check all potential positions
            if (grid[coord] != 0) { // this position already taken
                // System.out.println("position: " + coord + " already taken.");
                return false; // NO success
            }
        }
        return true; // there were no clashes, yay!
    } //end coordsAvailable

    private void savePositionToGrid(int[] shipCoords) {
        for (int index : shipCoords) {
            grid[index] = 1; // mark grid position as 'used'
        }
    } //end savePositionToGrid

    private ArrayList<String> convertCoordsToAlphaFormat(int[] shipCoords) {
        ArrayList<String> alphaCells = new ArrayList<String>();
        for (int index : shipCoords) { // for each grid coordinate
            String alphaCoords = getAlphaCoordsFromIndex(index); // turn it into an "a0" style
            alphaCells.add(alphaCoords); // add to a list
        }
        return alphaCells; // return the "a0"-style coords
    } // end convertCoordsToAlphaFormat

    private String getAlphaCoordsFromIndex(int index) {
        int row = calcRowFromIndex(index); // get row value
        int column = index % GRID_LENGTH; // get numeric column value
        String letter = ALPHABET.substring(column, column + 1); // convert to letter
        return letter + row;
    } // end getAlphaCoordsFromIndex

    private int calcRowFromIndex(int index) {
        return index / GRID_LENGTH;
    } // end calcRowFromIndex

    private int getIncrement() {
        if (shipCount % 2 == 0) { // if EVEN ship
            return HORIZONTAL_INCREMENT; // place horizontally
        } else { // else ODD
            return VERTICAL_INCREMENT; // place vertically
        }
    } //end getIncrement
} //end class