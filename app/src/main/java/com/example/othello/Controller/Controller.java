package com.example.othello.Controller;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
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

import java.util.ArrayList;

public class Controller{ //extends AppCompatActivity {
    // From the GameActivity
    // Use these to show the current scores
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView textViewTurn;
    private int x, y;
    private int AImode;
    private static final Controller controllerObj = new Controller();
    private final GameModel gameModel = GameModel.getInstance();
//    private final Stats statistics = new Stats();

    // gameMode keeps track of which mode the user is playing in - Single Player (0) or Two Player (1)
    protected int gameMode;

    private AI ai;
    public void setAImode(int x) {
        AImode = x;
    }
    public void setGameMode(int x) {
        gameMode = x;
        if(gameMode == 0) ai = new AI();
        // Future extensions: gameMode = 2 -> Two Players over the network
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
        if(this.gameMode == 0) {
            textViewPlayer1.setText("Black (AI) : 2");
            textViewPlayer2.setText("White (You): 2");
        }
        else {
            textViewPlayer1.setText("Black: 2");
            textViewPlayer2.setText("White: 2");
        }
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
        this.textViewTurn = textViewTurn;
        this.textViewPlayer1 = textViewPlayer1;
        this.textViewPlayer2 = textViewPlayer2;
        this.gameModel.buttons = buttons;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.gameModel.board[i][j] = new OthelloCell(i, j); // Meaning, this button hasn't been set yet
                this.gameModel.buttons[i][j].setBackgroundResource(R.drawable.empty_cell);
                this.gameModel.board[i][j].resetIt(); // This button hasn't been set yet
            }
        }

        if(gameMode == 0) {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    buttons[i][j].setOnClickListener(this::aiGame);
        }
        else {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    buttons[i][j].setOnClickListener(this::twoPlayerGame);
        }
        this.setInitState();
    }

    private Pair<Integer, Integer> getXY(String tag) {
        final int pos = Integer.parseInt(tag);
        return new Pair(pos / 10, pos % 10);
    }

    private void makeMove(View v, int x, int y) {
        this.gameModel.playAndFlipTiles(v, x, y);
        if (gameModel.player1Turn) textViewTurn.setText("White's Turn");
        else textViewTurn.setText("Black's Turn");
        gameModel.player1Turn = !gameModel.player1Turn;
        if(!this.gameModel.hasValidMove()) {
            gameModel.player1Turn = !gameModel.player1Turn;
            if (gameModel.player1Turn) textViewTurn.setText("White's Turn");
            else textViewTurn.setText("Black's Turn");
        }
        this.gameModel.markValidMoves();
        String[] textVals = this.gameModel.countScoreAndDrawScoreBoard();
        if(this.gameMode == 0) {
            textVals[0] = "Black (AI) : " + textVals[0];
            textVals[1] = "White (You): " + textVals[1];
        }
        else {
            textVals[0] = "Black: " + textVals[0];
            textVals[1] = "White: " + textVals[1];
        }
        textViewPlayer1.setText(textVals[0]);
        textViewPlayer2.setText(textVals[1]);
        if (textVals[2] != null) textViewTurn.setText(textVals[2]);
//        System.out.println("makeMove: " + x + ", " + y);
//        System.out.println("makeMove: Current Thread " + Thread.currentThread());
    }

    public void twoPlayerGame(View v) {
        final Pair<Integer, Integer> curPlay = getXY(((Button) v).getTag().toString());
        x = curPlay.first;
        y = curPlay.second;

        if (this.gameModel.isValidMove(x, y))  makeMove(v, x, y);
    }

    public void aiGame(View v) {
        Pair<Integer, Integer> curPlay = getXY(((Button) v).getTag().toString());
        x = curPlay.first;
        y = curPlay.second;

        if (this.gameModel.isValidMove(x, y)) {
//            System.out.println("aiGame: Valid Move!");
            if (!this.gameModel.player1Turn) { // Real user
                makeMove(v, x, y);
//                System.out.println("aiGame: player1Turn = " + gameModel.player1Turn);
                Pair<Integer, Integer> move = ai.minimaxChoice(this.gameModel.board,gameModel.player1Turn,AImode);

                x = move.first;
                y = move.second;
                this.gameModel.buttons[x][y].callOnClick();
            }
            else {
//                System.out.println("aiGame: AI's turn | " + this.gameModel.player1Turn);
//                try {
//                    Thread.sleep(1000);
//                }
//                catch(Exception e) {
//                    System.out.println("Sleep Exception: " + e);
//                }
                makeMove(v, x, y);
            }
        }
    }

//    public void getStatistics() {
//        return;
//    }
}