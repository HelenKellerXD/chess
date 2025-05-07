package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

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
     */

    public Collection<ChessMove> bishopMovement(ChessBoard board, ChessPosition myPosition){

        // iterate from start position in different directions to edge of board
        ChessPosition newPosition = myPosition;

        // up right -> limit colMax | rowMax
        while (myPosition.getColumn() < board.getColumnSize() && myPosition.getRow() < board.getRowSize()){
            if
            myPosition
        }
        // up left -> limit colMax | 0
        // down left -> limit 0 | 0
        // down right -> limit 0 | rowMax



        return validMoves;
    }




    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // create the list to return
        Collection<ChessMove> validMoves = new ArrayList<>();

        //




        return validMoves;
    }
}
