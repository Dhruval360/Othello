package com.example.othello.Model;

import android.widget.Button;

import com.example.othello.OthelloCell;

public class BoardSingleton {
    // Class object
    private static BoardSingleton boardObj = new BoardSingleton();

    // Game board attributes
    final private Button[][] buttons = new Button[8][8];
    private OthelloCell[][] board;
    private boolean player1Turn;

    private BoardSingleton(){

    }

    public static BoardSingleton getInstance(){
        if(boardObj==null){
            boardObj = new BoardSingleton();
        }
        return boardObj;
    }

}
