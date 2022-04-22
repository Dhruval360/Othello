package com.example.othello.Models;

import android.view.View;
import com.example.othello.R;
import android.widget.Button;

public class GameModel{
    /**
     * A Singleton class containing the game state
     */
    private static GameModel obj = new GameModel();

    // Game board attributes
    public Button[][] buttons;
    public OthelloCell[][] board = new OthelloCell[8][8];
    public boolean player1Turn;

    private GameModel(){ }

    public static GameModel getInstance() {
        return obj;
    }

    public void setInitState() {
        // Setting the center circles
        this.board[3][3].playIt();
        this.board[3][3].setBlack(true);
        this.buttons[3][3].setBackgroundResource(R.drawable.black_circle);
        this.board[4][4].playIt();
        this.board[4][4].setBlack(true);
        this.buttons[4][4].setBackgroundResource(R.drawable.black_circle);
        this.board[4][3].playIt();
        this.board[4][3].setBlack(false);
        this.buttons[4][3].setBackgroundResource(R.drawable.white_circle);
        this.board[3][4].playIt();
        this.board[3][4].setBlack(false);
        this.buttons[3][4].setBackgroundResource(R.drawable.white_circle);

        // Mark Initial Valid moves
        this.buttons[2][3].setBackgroundResource(R.drawable.green_dot);
        this.buttons[5][4].setBackgroundResource(R.drawable.green_dot);
        this.buttons[3][2].setBackgroundResource(R.drawable.green_dot);
        this.buttons[4][5].setBackgroundResource(R.drawable.green_dot);
    }

    /**
     * Checks if the current user can click on this cell for a valid move
     * @param x     The horizontal coordinate of the cell
     * @param y     The vertical coordinate of the cell
     * @return      Returns true if the current user can click on this cell for a valid move
     */
    boolean isPlayable(int x, int y) {
        // TODO
        if(isValidMove(x,y)) return true;
        return false;
    }

    /**
     *  Marks all the valid moves for the given player.
     */
    public void markValidMoves() {
        // TODO



        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(this.board[i][j].hasBeenPlayed()) continue;
                if(isPlayable(i, j))  this.buttons[i][j].setBackgroundResource(R.drawable.green_dot);
                else this.buttons[i][j].setBackgroundResource(R.drawable.empty_cell);
            }
        }
    }

    /**
     *  Checks to see if a valid move can be made at the indicated OthelloCell,
     *  for the given player.
     *  //@param view
     *  @param  xt      The horizontal coordinate value in the board.
     *  @param  yt      The vertical coordinate value in the board.
     *  @return         Returns true if a valid move can be made for this player at
     *                  this position, false otherwise.
     */
    public boolean isValidMove(int xt, int yt) {
        if (this.board[xt][yt].hasBeenPlayed()) { //Cant place on top of a played board
            return false;
        }

        // Check the 8 possible directions.
        for (int i = -1; i <=1; i++) {
            for (int j = -1; j <=1; j++) {
                if (!(i==0&&j==0)&&directionValid(xt,yt,i,j)) {
                    return true; // If the direction is valid then return true
                }
            }
        }
        System.out.println();
        return false;
    }

    /**
     *  Checks to see if a valid move can be made at the indicated OthelloCell, in a
     *  particular direction (there are 8 possible directions). These are indicated by:
     *  (1,1) is up and right
     *  (1,0) is right
     *  (1,-1) is down and right
     *  (0,-1) is down
     *  (-1,-1) is down and left
     *  (-1,0) is left
     *  (-1,1) is left and up
     *  (0,1) is up
     *  @param  xt      The horizontal coordinate value in the board.
     *  @param  yt      The vertical coordinate value in the board.
     *  @param  i       -1 is left, 0 is neutral, 1 is right,
     *  @param  j       -1 is down, - is neutral, 1 is up.
     *  @return         Returns true if this direction has pieces to be flipped, false otherwise.
     */
    public boolean directionValid(int xt, int yt, int i, int j) {
        xt+=i;
        yt+=j;
        if(xt >= 0 && xt <= 7 && yt >= 0 && yt <= 7) {
            if (!this.board[xt][yt].hasBeenPlayed()) {
                System.out.println("Invalid move");
                return false;
            }
            else if (this.board[xt][yt].getBlackStatus()== this.player1Turn) {
                System.out.println("Invalid move");
                return false;
            }
            else {
                while(xt >= 0 && xt <= 7 && yt >= 0 && yt <= 7) {
                    if (this.board[xt][yt].hasBeenPlayed() && this.board[xt][yt].getBlackStatus()== this.player1Turn) {
                        System.out.println("\nValid move\n");
                        return true;
                    }
                    else if (!this.board[xt][yt].hasBeenPlayed()) return false;
                    xt+=i;
                    yt+=j;
                }
            }
        }
        return false;
    }

    /**
     *  Places a game piece on the current cell for the current player.  Also flips the
     *  appropriate neighboring game pieces, checking the 8 possible directions from the
     *  current cell.
     */
    public void playAndFlipTiles (View v, int x, int y) {
//        System.out.println("AI playAndFlipTiles: " + x + ", " + y);
        if (player1Turn) v.setBackgroundResource(R.drawable.black_circle);
        else v.setBackgroundResource(R.drawable.white_circle);

        board[x][y].setBlack(player1Turn);
        board[x][y].playIt();


        for (int i=-1; i<=1; i++) {
            for (int j=-1; j<=1; j++) {
                if (!(i==0&&j==0)&& directionValid(x,y,i,j)) {
                    System.out.println("Flip");
                    flipAllInThatDirection(v,x,y,i,j);
                }
            }
        }
    }

    /**
     *  A helper method for playAndFlipTiles.  Flips pieces in a given direction.  The
     *  directions are as follows:
     *  (1,1) is up and right
     *  (1,0) is right
     *  (1,-1) is down and right
     *  (0,-1) is down
     *  (-1,-1) is down and left
     *  (-1,0) is left
     *  (-1,1) is left and up
     *  (0,1) is up
     *  @param  v       View
     *  @param  xt      The horizontal coordinate value in the board.
     *  @param  yt      The vertical coordinate value in the board.
     *  @param  i       -1 is left, 0 is neutral, 1 is right,
     *  @param  j       -1 is down, - is neutral, 1 is up.
     */
    public void flipAllInThatDirection(View v, int xt, int yt, int i, int j)
    {
        xt+=i;
        yt+=j;
        while(this.board[xt][yt].getBlackStatus() != this.player1Turn) { // As long as the tile is not equal to the players loop through and flip it
            this.board[xt][yt].setBlack(this.player1Turn);
            this.board[xt][yt].playIt();
            if(this.player1Turn) this.buttons[xt][yt].setBackgroundResource(R.drawable.black_circle);
            else this.buttons[xt][yt].setBackgroundResource(R.drawable.white_circle);
            System.out.println("\nflipped\n");
            xt += i;
            yt += j;
        }

    }

    /**
     *  Checks to see if black can play.  Checks to see if white can play.
     *  If neither can play, the game is over.  If black can't go, then set
     *  blackTurn to false.  If white can't go, set blackTurn to true.
     *  @return         Returns true if the game is over, false otherwise.
     */
    public boolean checkTurnAndGameOver() {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (isValidMove(i, j)) {
                    return false;
                }
            }
        }

        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (isValidMove(i, j)) {
                    this.player1Turn = false;
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Counts the white and black pieces on the board.
     * Also checks if the Game is over
     * @return          A String array containing the Text to be printed in the score board
     */
    public String[] countScoreAndDrawScoreBoard() {
        int whiteCount = 0, blackCount = 0;

        for(int x = 0; x<8; x++) {
            for(int y = 0; y<8; y++) {
                if (this.board[x][y].hasBeenPlayed()) {
                    if (this.board[x][y].getBlackStatus()) blackCount++;
                    else whiteCount++;
                }
            }
        }

        String turnText = null;

        if(this.checkTurnAndGameOver()) { // Game Over
            if(blackCount == whiteCount) {
                if(whiteCount > blackCount) turnText = "White Wins!";
                else if(whiteCount < blackCount) turnText = "Black Wins!";
                else turnText = "Draw!";
            }
        }
        return new String[]{"Black: " + blackCount, "White: " + whiteCount, turnText};
    }

}