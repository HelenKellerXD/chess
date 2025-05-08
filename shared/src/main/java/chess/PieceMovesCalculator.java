package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class PieceMovesCalculator {

    private final ChessGame.TeamColor pieceColor;
    private final ChessBoard board;
    private final ChessPosition position;
    private final ChessPiece.PieceType type;
    Collection<ChessPosition> moves = new ArrayList<>();



    public PieceMovesCalculator(ChessBoard board, ChessPosition position, ChessPiece.PieceType type, ChessGame.TeamColor pieceColor){
        this.pieceColor = pieceColor;
        this.board = board;
        this.position = position;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PieceMovesCalculator that = (PieceMovesCalculator) o;
        return Objects.equals(board, that.board) && Objects.equals(position, that.position) && type == that.type && Objects.equals(moves, that.moves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, position, type, moves);
    }

    /**
     * write a check to see if piece is on the board
     */
    public boolean isOnBoard(ChessPosition newPos){
        return newPos.getColumn() < board.getColumnSize()+1 &&
                newPos.getRow() < board.getRowSize()+1 &&
                newPos.getColumn() >= 1 &&
                newPos.getRow() >= 1;
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
        System.out.println("Position Check: " + newPosition.getRow() + newPosition.getColumn());
        if (isOnBoard(newPosition)){
            //only add position if the type of the moving piece does not match the type of the piece on the location
            ChessGame.TeamColor otherColor;
            //if (board.getPiece(newPosition) != null) {
            //    otherColor = board.getPiece(newPosition).getTeamColor();
            //}
            if (board.getPiece(newPosition) == null || pieceColor != board.getPiece(newPosition).getTeamColor()) {
                moves.add(newPosition);
            }
            //TEST --> print out when added
            System.out.println( newPosition.getRow() + " " + newPosition.getColumn() + " - added");
            if (board.getPiece(newPosition) == null){
                //TEST --> print notice to move to the next space
                System.out.println( "Space is null, proceed...");
                recurMoves(newPosition, colPos, rowPos);
            }
        }
        else{
            System.out.println("offboard -- return");
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

    public void rookMovement(){

        // iterate from start position in different directions to edge of board

        // up right -> (row+1, col+1)
        recurMoves(position,0,1);
        // up left -> (row+1, col-1)
        recurMoves(position,0,-1);
        // down left -> ( row-1, col-1)
        recurMoves(position,1,0);
        // down right -> (row - 1, col +1)
        recurMoves(position,-1,0);
        return;
    }

    public void pawnMovement(){
        // create move adjusters
        int oneStep;
        int jump;
        int captureRight = 1;
        int captureLeft = -1;
        int jumpRow;

        // set move direction based on color
        if (pieceColor == ChessGame.TeamColor.WHITE){
            oneStep = 1;
            jump = 2;
            jumpRow = 2;
        }
        else {
            oneStep = -1;
            jump = -2;
            jumpRow = 7;
        }
        // Move -> forward basic
        // check to see if Pawn has room in front
        if (position.newPosition(oneStep,0) == null){
            moves.add(position.newPosition(oneStep,0));
            // Move -> Start 2 jump
            // check to see

        }
        // Move -> diagnol capture

        
    }
    public void kingMovement(){}
    public void knightMovement(){}





    /**
     * This function is what figures out which movement function to use
     */

    public Collection<ChessPosition> getMoves(){
        switch (type){
            case BISHOP:
                bishopMovement();
                break;
            case ROOK:
                rookMovement();
                break;
            case QUEEN:
                bishopMovement();
                rookMovement();
                break;
            case PAWN:
            case KING:
            case KNIGHT:
            default:
        }


        return moves;
    }



}
