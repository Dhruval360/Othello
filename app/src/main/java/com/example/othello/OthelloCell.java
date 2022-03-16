package com.example.othello;

/**
 * Represents a single cell in the game of Othello.  By default, a cell is black, and
 * has not been played.  When a game piece is "placed" on the board, the boolean played
 * is set to true.  If the game piece is black, then the boolean black is true, and if
 * the game piece is white, then the boolean black is false.  The ints x and y
 * represent the coordinate values of the cell within the game board, with the lower
 * left at (0,0) and the upper right at (7,7).
 */
public class OthelloCell {
    private int x, y; // Haven't been used anywhere?
    private boolean played, black;
    /**
     *  Creates an OthelloCell object, at the given coordinate pair.
     *  @param  i      The horizontal coordinate value for the cell on the board.
     *  @param  j      The vertical coordinate value for the cell on the board.
     */
    public OthelloCell(int i, int j) {
        played = false;
        x = i;
        y = j;
        black = true;
    }

    /**
     *  Sets the piece to black (black true) or white (black false).
     *  @param  bool      The value to be assigned to the game piece.
     */
    public void setBlack(boolean bool) { black = bool; }

    /**
     *  Return the status of black; true for a black piece, false for a white piece.
     *  @return            Returns true for a black piece, false for a white piece.
     */
    public boolean getBlackStatus() { return black; }

    /**
     *  Sets the value of played to true, to indicate that a piece has been placed on this cell.
     */
    public void playIt() { played = true; }

    /**
     *  Resets the cell
     */
    public void resetIt() { played = false; }

    /**
     *  Return the status of played, indicating whether or not there is a game piece on this cell.
     *  @return            Returns true if a game piece is on this cell, false otherwise.
     */
    public boolean hasBeenPlayed() { return played; }
}