package com.example.othello.Controller;

import com.example.othello.Model.BoardSingleton;
import com.example.othello.Model.GameModel;
import com.example.othello.OthelloCell;

public class OthelloActivity implements IOthelloActivity{

    // gameMode keeps track of which mode the user is playing in
    protected int gameMode;

    /**
     * Constructor that initializes the gameMode to 1
     * gameMode = 1 = Multiplayer on local phone
     */
    public OthelloActivity(){
        gameMode = 1;
    }
    /**
     *
     * @param gameMode selects the logic for the game.
     */
    public OthelloActivity(int gameMode){
        gameMode = this.gameMode;
    }

    /**
     * This method is invoked when the game is started. It initializes
     * the board and starts the game based on the gameMode selected
     */
    public void onStartGame(){

        // Creating the board
        BoardSingleton bObj;
        bObj = bObj.getInstance();

        //
        GameModel gObj = new GameModel();

        if(gameMode==1){

        }
        else if(gameMode==2){

        }
        else{

        }
    }

    /**
     * Responds to button click. Plays a tile if the move is valid.
     */
    public void onButtonClick(){

    }

    public void aiGameMode(){

    }

}
