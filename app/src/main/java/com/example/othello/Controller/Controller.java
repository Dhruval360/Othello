package com.example.othello.Controller;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.othello.Models.GameModel;
//import com.example.othello.Models.GameModel;
import com.example.othello.Models.OthelloCell;
import com.example.othello.R;
import com.example.othello.Views.GameActivity;


public class Controller extends AppCompatActivity {
    // From the GameActivity
    // Use these to show the current scores
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView textViewTurn;

    private static Controller controllerObj = new Controller();
    private GameModel gameModel = GameModel.getInstance();

    // gameMode keeps track of which mode the user is playing in
    protected int gameMode = 1;

    /**
     * Constructor that initializes the gameMode to 1
     * gameMode = 1 = Multiplayer on local phone
     */
    private Controller(){ }

    /**
     * Controller is a singleton class
     */
    public static Controller getInstance() { return controllerObj; }

//    /**
//     * This method is invoked when the game is started. It initializes
//     * the board and starts the game based on the gameMode selected
//     */
//    public void onStartGame(){
//        // Creating the board
//        GameModel bObj = GameModel.getInstance();
//
//
//        if(gameMode==1){
//
//        }
//        else if(gameMode==2){
//
//        }
//        else{
//
//        }
//    }

//    /**
//     * Responds to button click. Plays a tile if the move is valid.
//     */
//    public void onButtonClick(){
//
//    }
//
//    public void aiGameMode(){
//
//    }

    public void resetGame() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.gameModel.buttons[i][j].setBackgroundResource(R.drawable.empty_cell);
                this.gameModel.board[i][j].resetIt(); // This button hasn't been set yet
            }
        }

        this.setInitState();
    }

    private void setInitState() {
        // Setting the center circles
        this.gameModel.board[3][3].playIt();
        this.gameModel.board[3][3].setBlack(true);
        this.gameModel.buttons[3][3].setBackgroundResource(R.drawable.black_circle);
        this.gameModel.board[4][4].playIt();
        this.gameModel.board[4][4].setBlack(true);
        this.gameModel.buttons[4][4].setBackgroundResource(R.drawable.black_circle);
        this.gameModel.board[4][3].playIt();
        this.gameModel.board[4][3].setBlack(false);
        this.gameModel.buttons[4][3].setBackgroundResource(R.drawable.white_circle);
        this.gameModel.board[3][4].playIt();
        this.gameModel.board[3][4].setBlack(false);
        this.gameModel.buttons[3][4].setBackgroundResource(R.drawable.white_circle);

        // Initial scores
        textViewPlayer1.setText("Black: 2");
        textViewPlayer2.setText("White: 2");

        // Initial Turn
        textViewTurn.setText("White's Turn");
        gameModel.player1Turn = false;

        // Mark Initial Valid moves
        gameModel.buttons[2][3].setBackgroundResource(R.drawable.green_dot);
        gameModel.buttons[5][4].setBackgroundResource(R.drawable.green_dot);
        gameModel.buttons[3][2].setBackgroundResource(R.drawable.green_dot);
        gameModel.buttons[4][5].setBackgroundResource(R.drawable.green_dot);
    }

    public void initGame(Button[][] buttons, TextView textViewPlayer1, TextView textViewPlayer2, TextView textViewTurn) {
        this.textViewPlayer1 = textViewPlayer1;
        this.textViewPlayer2 = textViewPlayer2;
        this.textViewTurn = textViewTurn;
        this.gameModel.buttons = buttons;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j].setOnClickListener(v -> {onClick(v);});
                this.gameModel.board[i][j] = new OthelloCell(i,j); // Meaning, this button hasn't been set yet
                this.gameModel.buttons[i][j].setBackgroundResource(R.drawable.empty_cell);
                this.gameModel.board[i][j].resetIt(); // This button hasn't been set yet
            }
        }

        this.setInitState();
    }

    public void onClick(View v) {
        final String tag = ((Button) v).getTag().toString();
        final int pos = Integer.parseInt(tag);
        int x = pos/10;
        int y = pos%10;
        if(false){
            if(isValidMove(x, y)) {
                playAndFlipTiles(v,x,y);
//                if(gameModel.player1Turn) textViewTurn.setText("White's Turn");
//                else textViewTurn.setText("Black's Turn");
                gameModel.player1Turn = !gameModel.player1Turn;
            }
        }
        else{
            if(isValidMove(x, y)){
                playAndFlipTiles(v,x,y);
                if(gameModel.player1Turn) textViewTurn.setText("White's Turn");
                else textViewTurn.setText("Black's Turn");
                gameModel.player1Turn = !gameModel.player1Turn;
            }}
        markValidMoves();
        String[] textVals = countScoreAndDrawScoreBoard();
        textViewPlayer1.setText(textVals[0]);
        textViewPlayer2.setText(textVals[1]);
        if(textVals[2] != null) textViewTurn.setText(textVals[2]);
    }

    // Doesn't work
    public void switchView(View buttonId) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    /**
     *  Marks all the valid moves for the given player.
     */
    void markValidMoves() {
        // TODO



        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(gameModel.board[i][j].hasBeenPlayed()) continue;
                if(isPlayable(i, j))  gameModel.buttons[i][j].setBackgroundResource(R.drawable.green_dot);
                else gameModel.buttons[i][j].setBackgroundResource(R.drawable.empty_cell);
            }
        }
    }

    /**
     * Checks if the current user can click on this cell for a valid move
     * @param x     The horizontal coordinate of the cell
     * @param y     The vertical coordinate of the cell
     * @return      Returns true if the current user can click on this cell for a valid move
     */
    boolean isPlayable(int x, int y) {
        // TODO
        return false;
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
        if (gameModel.board[xt][yt].hasBeenPlayed()) { //Cant place on top of a played board
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
            if (!gameModel.board[xt][yt].hasBeenPlayed()) {
                System.out.println("Invalid move");
                return false;
            }
            else if (gameModel.board[xt][yt].getBlackStatus()== gameModel.player1Turn) {
                System.out.println("Invalid move");
                return false;
            }
            else {
                while(xt >= 0 && xt <= 7 && yt >= 0 && yt <= 7) {
                    if (gameModel.board[xt][yt].hasBeenPlayed() && gameModel.board[xt][yt].getBlackStatus()== gameModel.player1Turn) {
                        System.out.println("\nValid move\n");
                        return true;
                    }
                    else if (!gameModel.board[xt][yt].hasBeenPlayed()) return false;
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
        if (gameModel.player1Turn) v.setBackgroundResource(R.drawable.black_circle);
        else v.setBackgroundResource(R.drawable.white_circle);

        if(false){
            // Pass to AI
            // returns X and Y
        }
        else{
            gameModel.board[x][y].setBlack(gameModel.player1Turn);
            gameModel.board[x][y].playIt();
        }

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
        while(gameModel.board[xt][yt].getBlackStatus() != gameModel.player1Turn) { // As long as the tile is not equal to the players loop through and flip it
            gameModel.board[xt][yt].setBlack(gameModel.player1Turn);
            gameModel.board[xt][yt].playIt();
            if(gameModel.player1Turn) gameModel.buttons[xt][yt].setBackgroundResource(R.drawable.black_circle);
            else gameModel.buttons[xt][yt].setBackgroundResource(R.drawable.white_circle);
            System.out.println("\nflipped\n");
            xt += i;
            yt += j;
        }

    }

    /**
     *  Counts the white pieces on the board, and the black pieces on the board.
     *  Displays these numbers toward the top of the board, for the current state
     *  of the board.  Also prints whether it is "BLACK'S TURN" or "WHITE'S TURN"
     *  or "GAME OVER".
     */
    public String[] countScoreAndDrawScoreBoard() {
        int whiteCount = 0, blackCount = 0;

        for(int x = 0; x<8; x++) {
            for(int y = 0; y<8; y++) {
                if (gameModel.board[x][y].hasBeenPlayed()) {
                    if (gameModel.board[x][y].getBlackStatus()) blackCount++;
                    else whiteCount++;
                }
            }
        }
        return drawScoresAndMessages(whiteCount, blackCount);
    }

    /**
     *  A helper method for countScoreAndDrawScoreBoard.  Draws the scores
     *  and messages.
     *  @param  whiteCount      The current count of the white pieces on the board.
     *  @param  blackCount      The current count of the black pieces on the board.
     */
    public String[] drawScoresAndMessages(int whiteCount, int blackCount) {
        String turnText = null;

        if(checkTurnAndGameOver()) { // Game Over
            if(blackCount == whiteCount) {
                if(whiteCount > blackCount) turnText = "White Wins!";
                else if(whiteCount < blackCount) turnText = "Black Wins!";
                else turnText = "Draw!";
            }
        }
        return new String[]{"Black: " + blackCount, "White: " + whiteCount, turnText};
    }

    /**
     *  Checks to see if black can play.  Checks to see if white can play.
     *  If neither can play, the game is over.  If black can't go, then set
     *  blackTurn to false.  If white can't go, set blackTurn to true.
     *  @return         Returns true if the game is over, false otherwise.
     */
    public boolean checkTurnAndGameOver() {
        boolean whiteCanGo = false, blackCanGo = false;

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
                    gameModel.player1Turn = false;
                    return false;
                }
            }
        }
        return true;
    }

}
