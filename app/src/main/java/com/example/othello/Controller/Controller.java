package com.example.othello.Controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.example.othello.R;
import android.widget.Button;
import android.widget.TextView;
import com.example.othello.Models.AI;
//import androidx.appcompat.app.AppCompatActivity;

import com.example.othello.Models.GameModel;
import com.example.othello.Models.OthelloCell;
import com.example.othello.Models.Stats;
import com.example.othello.Views.GameActivity;

public class Controller{ //extends AppCompatActivity {
    // From the GameActivity
    // Use these to show the current scores
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView textViewTurn;

    private static final Controller controllerObj = new Controller();
    private final GameModel gameModel = GameModel.getInstance();
    private final Stats statistics = new Stats();

    // gameMode keeps track of which mode the user is playing in
    protected int gameMode;

    private AI ai;
    public void setGameMode(int x)
    {
        gameMode = x;
        if(gameMode == 1)
        {
            ai = new AI();
        }
    }

    /**
     * Constructor that initializes the gameMode to 1
     * gameMode = 1 = Multiplayer on local phone
     */
    private Controller() {}

    /**
     * Controller is a singleton class
     */
    public static Controller getInstance() {
        return controllerObj;
    }

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

    /**
     * Resets the Board for a fresh start
     */
    public void resetGame() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.gameModel.buttons[i][j].setBackgroundResource(R.drawable.empty_cell);
                this.gameModel.board[i][j].resetIt(); // This button hasn't been set yet
            }
        }

        this.setInitState();
    }

    /**
     * Sets the initial game state
     */
    private void setInitState() {
        // Initial scores
        textViewPlayer1.setText("Black: 2");
        textViewPlayer2.setText("White: 2");

        // Initial Turn
        textViewTurn.setText("White's Turn");
        gameModel.player1Turn = false;
        this.gameModel.setInitState();
    }

    /**
     * Initializes the Othello board
     *
     * @param buttons         Clickable Othello Board Cells
     * @param textViewPlayer1 Text View to show the First Player's score
     * @param textViewPlayer2 Text View to show the Second Player's score
     * @param textViewTurn    Text View to show whose turn it is
     */
    public void initGame(Button[][] buttons, TextView textViewPlayer1, TextView textViewPlayer2, TextView textViewTurn){
        this.textViewPlayer1 = textViewPlayer1;
        this.textViewPlayer2 = textViewPlayer2;
        this.textViewTurn = textViewTurn;
        this.gameModel.buttons = buttons;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j].setOnClickListener(this::onClick);
                this.gameModel.board[i][j] = new OthelloCell(i, j); // Meaning, this button hasn't been set yet
                this.gameModel.buttons[i][j].setBackgroundResource(R.drawable.empty_cell);
                this.gameModel.board[i][j].resetIt(); // This button hasn't been set yet
            }
        }

        this.setInitState();
    }

    public void onClick(View v){
        final String tag = ((Button) v).getTag().toString();
        final int pos = Integer.parseInt(tag);
        int x = pos / 10;
        int y = pos % 10;
        if (gameMode == 1) {

            if (this.gameModel.isValidMove(x, y)) {
                this.gameModel.playAndFlipTiles(v, x, y);
                if (gameModel.player1Turn) textViewTurn.setText("White's Turn");
                else textViewTurn.setText("Black's Turn (AI)");
                gameModel.player1Turn = !gameModel.player1Turn;


                int[] move = ai.minimaxChoice(this.gameModel.board,gameModel.player1Turn);
                x = move[0];
                y = move[1];
//
                System.out.println("AI x : "+x+" AI y : "+y);
//
                this.gameModel.playAndFlipTiles(this.gameModel.buttons[x][y], x, y);
                if (gameModel.player1Turn) textViewTurn.setText("White's Turn");
                else textViewTurn.setText("Black's Turn");
                gameModel.player1Turn = !gameModel.player1Turn;
            }
        } else {
            if (this.gameModel.isValidMove(x, y)) {
                this.gameModel.playAndFlipTiles(v, x, y);
                if (gameModel.player1Turn) textViewTurn.setText("White's Turn");
                else textViewTurn.setText("Black's Turn");
                gameModel.player1Turn = !gameModel.player1Turn;
            }
        }

        this.gameModel.markValidMoves();
        String[] textVals = this.gameModel.countScoreAndDrawScoreBoard();
        textViewPlayer1.setText(textVals[0]);
        textViewPlayer2.setText(textVals[1]);
        if (textVals[2] != null) textViewTurn.setText(textVals[2]);
    }

////    // Doesn't work
//    public void setGameMode(int mode) {
//       this.gameMode = mode;
//       System.out.println(mode);
//    }

    public void getStatistics(){
        return;
    }
}