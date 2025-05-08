package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
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
        Collection<ChessPosition> validPositions = new ArrayList<>();

        // TEST --> see what the user sided position is of the myPosition
        System.out.println("User Piece Position: " + myPosition.stringPosition());

        //adjust the position entered by the user by a row-1, col-1
        //myPosition = myPosition.newPosition(-1, -1);

        // TEST --> see what the computer sided position is of the myPosition
        System.out.println("Computer Piece Position: " + myPosition.stringPosition());



        // TEST --> print out full board
        System.out.println("Board Layout: ");
        for (int i = board.getRowSize(); i > 0; i--){
            for (int j = 1; j < board.getColumnSize()+1; j++){
                ChessPosition thePos = new ChessPosition(i,j);
                ChessPiece piece = board.getPiece(thePos);
                if (piece != null) {
                    System.out.print("| " + board.getPiece(thePos).getPieceType() + " " + (i) + " " + (j) + " |");
                }
                else {
                    System.out.print("| NULL " + (i) + " " + (j) + " |");  // Empty square
                }
            }
            System.out.println("\n");
        }

        //get the valid moves and pull the list of possible positions and store them into validPositions
        PieceMovesCalculator calc = new PieceMovesCalculator(board, myPosition, type, pieceColor);
        validPositions = calc.getMoves();

        // TEST --> print the piece type and current position
        System.out.println("Piece type: " + type + "\n" + "Piece position: " + myPosition.getRow() + ", " + myPosition.getColumn());
        System.out.println("Possible Moves: ");

        //parse through valid positions to create a new list for pieceMoves to store in validMoves
        for (ChessPosition endPosition : validPositions){
            ChessMove newMove = new ChessMove(myPosition, endPosition, null);
            // TEST --> print out each new possible move
            System.out.println(endPosition.getRow() + ", " + endPosition.getColumn());
            validMoves.add(newMove);
        }

        // TEST --> print out the validMoves list
        System.out.println("Possible Moves ");
        for (ChessMove moves : validMoves){
            System.out.println(moves.stringPath());
        }


        //return the list of validMoves
        return validMoves;
    }
}
