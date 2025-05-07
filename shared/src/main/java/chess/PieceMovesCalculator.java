package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMovesCalculator {

    private final ChessBoard board;
    private final ChessPosition position;
    private final ChessPiece.PieceType type;
    Collection<ChessPosition> moves = new ArrayList<>();

    public PieceMovesCalculator(ChessBoard board, ChessPosition position, ChessPiece.PieceType type){
        this.board = board;
        this.position = position;
        this.type = type;
    }

    /**
     * write a check to see if piece is on the board
     */
    public boolean isOnBoard(ChessPosition newPos){
        return newPos.getColumn() < board.getColumnSize() &&
                newPos.getRow() < board.getRowSize() &&
                newPos.getColumn() >= 0 &&
                newPos.getRow() >= 0;
    }
    public boolean isOnBoard(){
        return isOnBoard(position);
    }




        /**
         * WRITE OUT THE FUNCTIONS FOR MOVING EACH PIECE
         */

    /**
     * Calculate all the moves a bishop can make up to board size
     * Steps
     *      find piece initial location on the board
     *      move to next space the piece could move to and check to see if it is still on the board
     *          if the next space is off of the board then break recursion
     *
     *      Check to see if the space is null
     *          if it is not null, then return the position as the final position and break the loop
     *          if it is null, return the position and use recursion to call the function again at the next space
     *
     *
     *   step 1: check to make sure move is valid
     *   step 2: record move if valid, break if not
     *   step 3: check the space in
     */

    // Pieces that need recursion: bishop, rook, Queen (bishop+rook)

    // recursive function: continues to add positions in a certain direction until no longer valid
    public void recurMoves(ChessPosition location, int colPos, int rowPos){
        // check location to see if it is on the board
        ChessPosition newPosition = new ChessPosition(location.getRow()+rowPos, location.getColumn()+colPos);
        //TEST --> print out the new position
        //System.out.println("Position Check: " + newPosition.getRow() + newPosition.getColumn());
        if (isOnBoard(newPosition)){
            moves.add(newPosition);
            //TEST --> print out when added
            //System.out.println( newPosition.getRow() + " " + newPosition.getColumn() + " - added");
            if (board.getPiece(newPosition) == null){
                //TEST --> print notice to move to the next space
                //System.out.println( "Space is null, proceed...");
                recurMoves(newPosition, colPos, rowPos);
            }
        }
        else{
            //System.out.println("offboard -- return");
        }



        return;
    }




    /**
     * Calculate all the moves a bishop can make up to board size
     * Steps
     *      find piece initial location on the board
     *      recurMoves in each diagnol location
     *
     */

    public void bishopMovement(){

        // iterate from start position in different directions to edge of board

        // up right -> (row+1, col+1)
        recurMoves(position,1,1);
        // up left -> (row+1, col-1)
        recurMoves(position,1,-1);
        // down left -> ( row-1, col-1)
        recurMoves(position,-1,-1);
        // down right -> (row - 1, col +1)
        recurMoves(position,-1,1);




        return;
    }




    /**
     * This function is what figures out which movement function to use
     */

    public Collection<ChessPosition> getMoves(){
        switch (type){
            case BISHOP:
                bishopMovement();
                break;
        }


        return moves;
    }



}
