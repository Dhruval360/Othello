package com.example.othello.Models;

import android.widget.Button;

public class GameModel {
    /**
     * A Singleton class containing the game state
     */
    private static GameModel boardObj = new GameModel();

    // Game board attributes
    public Button[][] buttons;
    public OthelloCell[][] board = new OthelloCell[8][8];
    public boolean player1Turn;

    private GameModel(){ }

    public static GameModel getInstance() {
        return boardObj;
    }

}
