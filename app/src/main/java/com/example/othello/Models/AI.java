package com.example.othello.Models;
import com.example.othello.Models.OthelloCell;

public class AI {
    private int bestx;
    private int besty;
    private int lenvalidmoves;
    private OthelloCell [][] board;
    private int x, y;
    private boolean blackTurn;
    public AI(){
        blackTurn = true;
        lenvalidmoves = 0;
        bestx = -1;
        besty = -1;
    }
    public int[] minimaxChoice(OthelloCell [][]modelboard)
    {
        int depth=1;
        blackTurn = true;
        board = new OthelloCell[8][8];
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j< 8; j++)
            {
                board[i][j] = modelboard[i][j].clones();
            }
        }
        minimax(depth,blackTurn);
        int [] ans = new int[2];
        ans[0] = bestx;
        ans[1] = besty;
        return ans;
    }

    public int minimax(int depth, boolean bTurn)
    {
        int score = getScore();
        if(depth == 0)
        {
            return score;
        }
        int bestVal = -999999;
        int [][] moves = getValidMoves(bTurn);
        for(int i = 0; i < lenvalidmoves; i++)
        {
            x = moves[i][0];
            y = moves[i][1];
            playAndFlipTiles();
            int val = minimax(depth-1,!bTurn);
            if(val>bestVal)
            {
                bestVal = val;
                bestx = x;
                besty = y;
            }
        }
        return score;
    }

    public int[][] getValidMoves(boolean bTurn)
    {
        int[][] validMoves = new int[1024][1024];
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(isValidMove(i,j,bTurn))
                {	int len = lenvalidmoves++;
                    validMoves[len][0] = i;
                    validMoves[len][1] = j;
                }
            }
        }
        return validMoves;
    }

    public int getScore() {
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
        return (whiteCount - blackCount);
    }

    public boolean isValidMove(int xt, int yt, boolean bTurn)
    {
        if (board[xt][yt].hasBeenPlayed()) //Cant place on top of a played board
        {
            return false;
        }
        /// check the 8 possible directions.
        for (int i = -1; i <=1; i++)
        {
            for (int j = -1; j <=1; j++)
            {
                if (!(i==0&&j==0)&&directionValid(xt,yt,i,j,bTurn))
                {
                    return true; ///If the direction is valid then return true
                }
            }
        }
        System.out.println();
        return false;
    }

    public boolean directionValid(int xt, int yt, int i, int j, boolean bTurn)
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

    public void playAndFlipTiles ( )
    {
        board[x][y].setBlack(blackTurn);
        board[x][y].playIt();

        for (int i=-1; i<=1; i++)
        {
            for (int j=-1; j<=1; j++)
            {
                if (!(i==0&&j==0)&& directionValid(x,y,i,j,blackTurn))
                {
                    //System.out.println("Flip");
                    flipAllInThatDirection(x,y,i,j);
                }
            }
        }
    }

    public void flipAllInThatDirection(int xt, int yt, int i, int j)
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
