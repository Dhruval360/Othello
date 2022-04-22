package com.example.othello.Models;
import android.util.Pair;
import java.util.ArrayList;

public class AI {
    private int bestx, besty,  maxdepth;
    private boolean aiTurn;

    public AI() {
        bestx = -1;
        besty = -1;
        maxdepth = 3; // Needs to come from the user
    }

    public Pair<Integer, Integer> minimaxChoice(OthelloCell [][]modelBoard, boolean aiTurn) {
        boolean blackTurn = aiTurn;
        this.aiTurn = aiTurn;
        OthelloCell [][] board = new OthelloCell[8][8];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j< 8; j++) {
                board[i][j] = modelBoard[i][j].clones();
            }
        }
        minimax(board, maxdepth, blackTurn);
        return new Pair<Integer, Integer>(bestx, besty);
    }

    public int minimax(OthelloCell [][]board, int depth, boolean bTurn) {
        int score = getScore(board);
        if(depth == 0) return score;

        int bestVal;
        if(bTurn) bestVal = -999999;
        else bestVal = 999999;

        ArrayList<Pair<Integer, Integer>> moves = getValidMoves(board, bTurn);
        for(Pair<Integer, Integer> validMove : moves) {
            int x = validMove.first;
            int y = validMove.second;
            OthelloCell [][] tempBoard = new OthelloCell[8][8];
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j< 8; j++) {
                    tempBoard[i][j] = board[i][j].clones();
                }
            }
            playAndFlipTiles(tempBoard,x,y,bTurn);

            int val = minimax(tempBoard,depth-1,!bTurn);

            if( (bTurn && val>bestVal) || (!bTurn && val < bestVal) ) {
                bestVal = val;
                if(depth == maxdepth) {
                    bestx = x;
                    besty = y;
                }
            }
        }
        return bestVal;
    }

    public ArrayList<Pair<Integer, Integer>> getValidMoves(OthelloCell [][]board, boolean bTurn) {
        ArrayList<Pair<Integer, Integer>> validMoves = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(isValidMove(board,i,j,bTurn)) {
                    Pair<Integer, Integer> validMove = new Pair(i, j);
                    validMoves.add(validMove);
                }
            }
        }
        return validMoves;
    }

    public int getScore(OthelloCell [][] board) {
        int whiteCount = 0, blackCount = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y].hasBeenPlayed()) {
                    if (board[x][y].getBlackStatus()) ++blackCount;
                    else ++whiteCount;
                }
            }
        }
        return blackCount - whiteCount;
    }

    public boolean isValidMove(OthelloCell [][]board, int xt, int yt, boolean bTurn) {
        if (board[xt][yt].hasBeenPlayed()) return false;
        // Check the 8 possible directions.
        for (int i = -1; i <=1; i++) {
            for (int j = -1; j <=1; j++) {
                if ( !( i == 0 && j == 0 ) && directionValid(board, xt, yt, i, j, bTurn)) return true;
            }
        }
        return false;
    }

    public boolean directionValid(OthelloCell [][]board, int xt, int yt, int i, int j, boolean bTurn) {
        xt += i;
        yt += j;
        if( (xt >= 0) && (xt <= 7) && (yt >= 0) && (yt <= 7) ) { // loop through the direction
            if (!board[xt][yt].hasBeenPlayed() || board[xt][yt].getBlackStatus()==bTurn) return false;
            else {
                while( (xt >= 0) && (xt <= 7) && (yt >= 0) && (yt <= 7) ) {
                    if (board[xt][yt].hasBeenPlayed() &&board[xt][yt].getBlackStatus()==bTurn) return true;
                    else if (!board[xt][yt].hasBeenPlayed()) return false;
                    xt += i;
                    yt += j;
                }
            }
        }
        return false;
    }

    public void playAndFlipTiles (OthelloCell [][]board, int x, int y, boolean blackTurn) {
        board[x][y].setBlack(blackTurn);
        board[x][y].playIt();

        for (int i=-1; i<=1; i++) {
            for (int j=-1; j<=1; j++) {
                if (!(i==0&&j==0)&& directionValid(board, x, y, i, j, blackTurn)) {
                    //System.out.println("Flip");
                    flipAllInThatDirection(board, x, y, i, j, blackTurn);
                }
            }
        }
    }

    public void flipAllInThatDirection(OthelloCell [][]board, int xt,  int yt,  int i,  int j, boolean blackTurn) {
        xt += i;
        yt += j;
        while(board[xt][yt].getBlackStatus()!=blackTurn) { // As long as the tile is not equal to the players loop through and flip it
            board[xt][yt].setBlack(blackTurn);
            board[xt][yt].playIt();
            xt += i;
            yt += j;
        }
    }
}
