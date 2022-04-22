package com.example.othello.Models;
import com.example.othello.Models.OthelloCell;

import java.util.ArrayList;
import java.util.Arrays;

public class AI {
    private int bestx;
    private int besty;
    //private int lenvalidmoves;
    //private OthelloCell [][] board;
    //private int x, y;
    private boolean player1turn;
    private int maxdepth;
    public AI(){
        //blackTurn = true;
        //lenvalidmoves = 0;
        bestx = -1;
        besty = -1;
        maxdepth = 3;
    }
    public int[] minimaxChoice(OthelloCell [][]modelboard, boolean player1turn)
    {
        boolean blackTurn = player1turn;
        this.player1turn = player1turn;
        OthelloCell [][] board = new OthelloCell[8][8];
        //lenvalidmoves = 0;
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j< 8; j++)
            {
                board[i][j] = modelboard[i][j].clones();
            }
        }
        minimax(board,maxdepth,blackTurn);
        int [] ans = new int[2];
        ans[0] = bestx;
        ans[1] = besty;
        return ans;
    }

    public int minimax(OthelloCell [][]board, int depth, boolean bTurn) {
        int score = getScore(board);
        if(depth == 0) {
            System.out.println("Minimax: (Depth = 0) Player = " + bTurn + " | Score = " + score);
            return score;
        }

        int bestVal;
        if(bTurn) bestVal = -999999;
        else bestVal = 999999;

        ArrayList<ArrayList<Integer>> moves = getValidMoves(board, bTurn);
        for(ArrayList<Integer> validMove : moves) {
            int x = validMove.get(0);
            int y = validMove.get(1);
            System.out.println("Minimax: (Depth = " + depth + ") Player = " + bTurn + " | Move = " + x + ", " + y);
            OthelloCell [][] tempboard = new OthelloCell[8][8];
            for(int blah = 0; blah < 8; blah++) {
                for(int bleh = 0; bleh< 8; bleh++) {
                    tempboard[blah][bleh] = board[blah][bleh].clones();
                }
            }
            playAndFlipTiles(tempboard,x,y,bTurn);
            int val = minimax(tempboard,depth-1,!bTurn);

            if( (bTurn && val>bestVal) || (!bTurn && val < bestVal) ) {
                bestVal = val;
                if(depth == maxdepth) {
                    bestx = x;
                    besty = y;
                }
            }
        }
        System.out.println("Minimax: (Depth = " + depth + ") Player = " + bTurn + " | Score = " + bestVal + " | Best Move = " + bestx + ", " + besty);
        return bestVal;
//        }
//        else{
//            int bestVal = 999999;
//            ArrayList<ArrayList<Integer>> moves = getValidMoves(board, bTurn);
//            for(ArrayList<Integer> validMove : moves) {
//                int x = validMove.get(0);
//                int y = validMove.get(1);
//                OthelloCell [][] tempboard = new OthelloCell[8][8];
//                for(int blah = 0; blah < 8; blah++)
//                {
//                    for(int bleh = 0; bleh< 8; bleh++)
//                    {
//                        tempboard[blah][bleh] = board[blah][bleh].clones();
//                    }
//                }
//                playAndFlipTiles(tempboard,x,y,bTurn);
//                int val = minimax(tempboard,depth-1,!bTurn);
//                if(val<bestVal)
//                {
//                    bestVal = val;
//                    //if((depth == maxdepth)&&(player1turn == bTurn)){
//                    bestx = x;
//                    besty = y;
//                    //}
//
//                }
//            }
//            return bestVal;
//    }

    }

    public ArrayList<ArrayList<Integer>> getValidMoves(OthelloCell [][]board, boolean bTurn)
    {
        int len = 0;
        ArrayList<ArrayList<Integer>> validMoves = new ArrayList<>();
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(isValidMove(board,i,j,bTurn))
                {
                    ArrayList<Integer> validMove = new ArrayList<>();
                    validMove.add(i);
                    validMove.add(j);
                    validMoves.add(validMove);
                    len++;
                }
            }
        }
        //lenvalidmoves = len;
        return validMoves;
    }

    public int getScore(OthelloCell [][] board) {
        int whiteCount = 0, blackCount = 0;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y].hasBeenPlayed()) {
                    if (board[x][y].getBlackStatus()) {
                        blackCount++;
                    } else {
                        whiteCount++;
                    }
                }
            }
        }
        return -1 * (whiteCount - blackCount);
    }

    public boolean isValidMove(OthelloCell [][]board,int xt, int yt, boolean bTurn)
    {
        if (board[xt][yt].hasBeenPlayed()) //Cant place on top of a played board
        {
            System.out.println("isValidMove: " + xt + "," + yt + " is invalid");
            return false;
        }
        /// check the 8 possible directions.
        for (int i = -1; i <=1; i++)
        {
            for (int j = -1; j <=1; j++)
            {
                if (!(i==0&&j==0)&&directionValid(board,xt,yt,i,j,bTurn))
                {
                    System.out.println("isValidMove: " + xt + "," + yt + " is valid");
                    return true; ///If the direction is valid then return true
                }
            }
        }
        System.out.println("isValidMove: " + xt + "," + yt + " is invalid");
        return false;
    }

    public boolean directionValid(OthelloCell [][]board,int xt, int yt, int i, int j, boolean bTurn)
    {
        xt+=i;
        yt+=j;
        if(xt>=0&&xt<=7 && yt>=0&&yt<=7) //loop through the direction
        {
            if (!board[xt][yt].hasBeenPlayed())
            {
                //System.out.println("Invalid move");
                return false;
            }
            else if (board[xt][yt].getBlackStatus()==bTurn)
            {
                //System.out.println("Invalid move");
                return false;
            }
            else
            {
                while(xt>=0&&xt<=7 && yt>=0&&yt<=7)
                {
                    if (board[xt][yt].hasBeenPlayed() &&board[xt][yt].getBlackStatus()==bTurn)
                    {
                        //System.out.println("\nValid move\n");
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

    public void playAndFlipTiles (OthelloCell [][]board,int x,int y,boolean blackTurn)
    {
        board[x][y].setBlack(blackTurn);
        board[x][y].playIt();

        for (int i=-1; i<=1; i++)
        {
            for (int j=-1; j<=1; j++)
            {
                if (!(i==0&&j==0)&& directionValid(board,x,y,i,j,blackTurn))
                {
                    //System.out.println("Flip");
                    flipAllInThatDirection(board,x,y,i,j,blackTurn);
                }
            }
        }
    }

    public void flipAllInThatDirection(OthelloCell [][]board,int xt, int yt, int i, int j,boolean blackTurn)
    {
        xt+=i;
        yt+=j;
        while(board[xt][yt].getBlackStatus()!=blackTurn) //as long as the tile is not equal to the players loop through and flip it
        {
            board[xt][yt].setBlack(blackTurn);
            board[xt][yt].playIt();
            //System.out.println("\nfliped\n");
            xt+=i;
            yt+=j;
        }
    }
    }
