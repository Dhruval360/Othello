package com.example.othello;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final private Button[][] buttons = new Button[8][8];
    private OthelloCell [][] board;
    private boolean player1Turn;

    // Use these to show the current scores
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    private void setInitialState() {
        board[3][3].playIt();
        board[3][3].setBlack(true);
        buttons[3][3].setBackgroundResource(R.drawable.black_circle);
        board[4][4].playIt();
        board[4][4].setBlack(true);
        buttons[4][4].setBackgroundResource(R.drawable.black_circle);
        board[4][3].playIt();
        board[4][3].setBlack(false);
        buttons[4][3].setBackgroundResource(R.drawable.white_circle);
        board[3][4].playIt();
        board[3][4].setBlack(false);
        buttons[3][4].setBackgroundResource(R.drawable.white_circle);

        // Initial scores
        textViewPlayer1.setText("Black: " + 2);
        textViewPlayer2.setText("White: " + 2);

        player1Turn = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Prevent screen rotation

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        board = new OthelloCell[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setBackgroundResource(R.drawable.empty_cell);
                board[i][j] = new OthelloCell(i,j); // Meaning, this button hasn't been set yet
            }
        }

        setInitialState();

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(v -> {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    buttons[i][j].setBackgroundResource(R.drawable.empty_cell);
                    board[i][j].resetIt(); // This button hasn't been set yet
                }
            }

            setInitialState();
        });
    }

    @Override
    public void onClick(View v) {
        final String tag = ((Button) v).getTag().toString();
        final int pos = Integer.parseInt(tag);
        int x = pos/10;
        int y = pos%10;
        if(isValidMove(x, y)){
            playAndFlipTiles(v,x,y);
            player1Turn = !player1Turn;
        }
        countScoreAndDrawScoreBoard();
    }
/*******************************************************************************************************************************************/

    /**
     *  Checks to see if a valid move can be made at the indicated OthelloCell,
     *  for the given player.
     *  //@param view
     *  @param  xt      The horizontal coordinate value in the board.
     *  @param  yt      The vertical coordinate value in the board.
     *  @return         Returns true if a valid move can be made for this player at
     *                  this position, false otherwise.
     */
    public boolean isValidMove(int xt, int yt){
        if (board[xt][yt].hasBeenPlayed()) //Cant place on top of a played board
        {
            return false;
        }
        /// check the 8 possible directions.
        for (int i = -1; i <=1; i++)
        {
            for (int j = -1; j <=1; j++)
            {
                if (!(i==0&&j==0)&&directionValid(xt,yt,i,j))
                {
                    return true; ///If the direction is valid then return true
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

    public boolean directionValid(int xt, int yt, int i, int j)
    {
        xt+=i;
        yt+=j;
        if(xt>=0&&xt<=7 && yt>=0&&yt<=7) //loop through the direction
        {
            if (!board[xt][yt].hasBeenPlayed())
            {
                System.out.println("Invalid move");
                return false;
            }
            else if (board[xt][yt].getBlackStatus()==player1Turn)
            {
                System.out.println("Invalid move");
                return false;
            }
            else
            {
                while(xt>=0&&xt<=7 && yt>=0&&yt<=7)
                {
                    if (board[xt][yt].hasBeenPlayed() &&board[xt][yt].getBlackStatus()==player1Turn)
                    {
                        System.out.println("\nValid move\n");
                        return true;
                    }
                    else if (!board[xt][yt].hasBeenPlayed())
                    {
                        return false;
                    }
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
    public void playAndFlipTiles (View v, int x, int y)
    {
        if (player1Turn) v.setBackgroundResource(R.drawable.black_circle);
        else v.setBackgroundResource(R.drawable.white_circle);
        board[x][y].setBlack(player1Turn);
        board[x][y].playIt();


        for (int i=-1; i<=1; i++)
        {
            for (int j=-1; j<=1; j++)
            {
                if (!(i==0&&j==0)&& directionValid(x,y,i,j))
                {
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
        while(board[xt][yt].getBlackStatus()!=player1Turn) //as long as the tile is not equal to the players loop through and flip it
        {
            board[xt][yt].setBlack(player1Turn);
            board[xt][yt].playIt();
            if(player1Turn) {
                buttons[xt][yt].setBackgroundResource(R.drawable.black_circle);
            }
            else{
                buttons[xt][yt].setBackgroundResource(R.drawable.white_circle);
            }
            System.out.println("\nflipped\n");
            xt+=i;
            yt+=j;
        }

    }

    /**
     *  Counts the white pieces on the board, and the black pieces on the board.
     *  Displays these numbers toward the top of the board, for the current state
     *  of the board.  Also prints whether it is "BLACK'S TURN" or "WHITE'S TURN"
     *  or "GAME OVER".
     */
    public void countScoreAndDrawScoreBoard ( )
    {
        int whiteCount = 0, blackCount = 0;

        for(int x = 0; x<8; x++)
        {
            for(int y = 0; y<8; y++)
            {
                if (board[x][y].hasBeenPlayed())
                {
                    if (board[x][y].getBlackStatus())
                    {
                        blackCount++;
                    }
                    else
                    {
                        whiteCount++;
                    }
                }
            }
        }
        drawScoresAndMessages(whiteCount,blackCount);
    }

    /**
     *  A helper method for countScoreAndDrawScoreBoard.  Draws the scores
     *  and messages.
     *  @param  whiteCount      The current count of the white pieces on the board.
     *  @param  blackCount      The current count of the black pieces on the board.
     */
    public void drawScoresAndMessages(int whiteCount, int blackCount)
    {
        //TODO: Update the TextView
        textViewPlayer1.setText("Black: " + whiteCount);
        textViewPlayer2.setText("White: " + blackCount);
    }

    /**
     *  Checks to see if black can play.  Checks to see if white can play.
     *  If neither can play, the game is over.  If black can't go, then set
     *  blackTurn to false.  If white can't go, set blackTurn to true.
     *  @return         Returns true if the game is over, false otherwise.
     */
    public boolean checkTurnAndGameOver ( )
    {
        boolean whiteCanGo = false, blackCanGo = false;

        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                if (isValidMove(i, j))
                {
                    return false;
                }
            }
        }

        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                if (isValidMove(i, j))
                {
                    player1Turn=false;
                    return false;
                }
            }
        }
        return true;
    }
}


/**
 * Represents a single cell in the game of Othello.  By default, a cell is black, and
 * has not been played.  When a game piece is "placed" on the board, the boolean played
 * is set to true.  If the game piece is black, then the boolean black is true, and if
 * the game piece is white, then the boolean black is false.  The ints x and y
 * represent the coordinate values of the cell within the game board, with the lower
 * left at (0,0) and the upper right at (7,7).
 */
class OthelloCell{
    private int x, y;

    private boolean played, black;
    /**
     *  Creates an OthelloCell object, at the given coordinate pair.
     *  @param  i      The horizontal coordinate value for the cell on the board.
     *  @param  j      The vertical coordinate value for the cell on the board.
     */
    public OthelloCell(int i, int j)
    {
        played = false;
        x = i;
        y = j;
        black = true;
    }

    /**
     *  Sets the piece to black (black true) or white (black false).
     *  @param  bool      The value to be assigned to the game piece.
     */
    public void setBlack(boolean bool)
    {
        black = bool;
    }

    /**
     *  Return the status of black; true for a black piece, false for a white piece.
     *  @return            Returns true for a black piece, false for a white piece.
     */
    public boolean getBlackStatus ( )
    {
        return black;
    }

    /**
     *  Sets the value of played to true, to indicate that a piece has been placed on this cell.
     */
    public void playIt ( )
    {
        played = true;
    }

    /**
     *
     */
    public void resetIt() {
        played = false;
    }

    /**
     *  Return the status of played, indicating whether or not there is a game piece on this cell.
     *  @return            Returns true if a game piece is on this cell, false otherwise.
     */
    public boolean hasBeenPlayed ( )
    {
        return played;
    }
}