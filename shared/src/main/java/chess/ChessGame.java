package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor team = TeamColor.WHITE;
    private ChessBoard board = new ChessBoard();

    public ChessGame() {
        board.resetBoard();

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team = team;
    }

    /**
     * function swaps the team color
     */
    public void switchTurns(){
        if (getTeamTurn() == TeamColor.WHITE){
            setTeamTurn(TeamColor.BLACK);
        }
        else{
            setTeamTurn(TeamColor.WHITE);
        }
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        TeamColor pieceColor = board.getPiece(startPosition).getTeamColor();
        ChessPiece piece = new ChessPiece(pieceColor,getBoard().getPiece(startPosition).getPieceType());
        return piece.pieceMoves(board, startPosition);
    }

    public Collection<ChessPosition> endMoves(Collection<ChessPosition> attacks, ChessPosition attacker){
        Collection<ChessMove> fullAttacks = board.getPiece(attacker).pieceMoves(board, attacker);
        for (var i : fullAttacks){
            attacks.add(i.getEndPosition());
        }
        return attacks;
    }


    /**
     * make a movePiece function
     */
    public void movePiece(ChessBoard newBoard,ChessMove move){
        ChessPiece movingPiece = newBoard.getPiece(move.getStartPosition());
        newBoard.addPiece(move.getEndPosition(),movingPiece);
        newBoard.removePiece(move.getStartPosition());
    }



    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //check first to see if the piece at move.start has a valid move that matches the move.end
        Collection<ChessMove> validMove = new ArrayList<>();
        validMove = validMoves(move.getStartPosition());
        //filter out any moves that would result in the king entering check
        validMove = protectTheKing(validMove, board.getPiece(move.getStartPosition()).getTeamColor());
        if (validMove.contains(move)){
            // -> if true: call movePiece() to add the chess piece to end location and then delete it from starting location
            movePiece(board, move);
            // switch color to the other team
            switchTurns();

        }
        else {
            // -> if not, throw error
            throw new InvalidMoveException("Invalid move: " + move);
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        Collection<ChessPosition> attacks = new ArrayList<>();
        ChessPosition kingsPosition = null;
        //get kings position
        //get all possible moves (move.getEndPosition()) from all opposing team pieces
        for (int i = 1 ; i <= 8; i++){
            for (int j = 1 ; j <=8; j++){
                ChessPosition newPosition = new ChessPosition(i,j);
                // check if the spot is not null
                ChessPiece mysterPiece = board.getPiece(newPosition);
                if (mysterPiece != null){
                    //check if the piece is the king of teamcolor
                    if (mysterPiece.getTeamColor() == teamColor && mysterPiece.getPieceType() == ChessPiece.PieceType.KING) {
                        //if true -> save to kings position
                        kingsPosition = newPosition;
                    }
                    else {
                        if (mysterPiece.getTeamColor() != teamColor) {
                          // if false -> check to see if the piece is not teamcolor
                            endMoves(attacks, newPosition);

                            //if true -> save all the enemy pieces possible endmoves to attacks
                        }
                            //if false -> continue on

                    }
                }
            }
        }
        //if the king is found in any of the end moves, then the king is in check and return turn
        if (attacks.contains(kingsPosition)){
            return true;
        }
        else {
            //else return false
            return false;
        }
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // check first to see if king is even in check
        if (!isInCheck(teamColor)){
            return false;
        }

        // follow same steps as found in check but also save the moveable spaces around the king and see if those are also blocked
        Collection<ChessPosition> attacks = new ArrayList<>();
        Collection<ChessPosition> kingsPositions = new ArrayList<>();
        //get kings position
        //get all possible moves (move.getEndPosition()) from all opposing team pieces
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition newPosition = new ChessPosition(i, j);
                // check if the spot is not null
                ChessPiece mysteryPiece = board.getPiece(newPosition);
                if (mysteryPiece != null) {
                    //check if the piece is the king of teamcolor
                    if (mysteryPiece.getTeamColor() == teamColor && mysteryPiece.getPieceType() == ChessPiece.PieceType.KING) {
                        //if true -> save to kings position and all endPositions of king
                        kingsPositions.add(newPosition);
                        endMoves(kingsPositions, newPosition);
                    } else {
                        if (mysteryPiece.getTeamColor() != teamColor) {
                            // if false -> check to see if the piece is not teamcolor
                            endMoves(attacks, newPosition);

                            //if true -> save all the enemy pieces possible endmoves to attacks
                        }
                        //if false -> continue on

                    }
                }
            }
        }
        //if the king has any escape route, then the king is not in checkmate and return false, else true
        for (var i : kingsPositions) {
            if (!attacks.contains(i)) {
                return false;
            }
        }
        //else return true
        return true;
    }


    /**
     * Create function that looks at possible moves that keeps the king from being in check
     * -> check to see if the king can move out of check and return those moves
     * -> check to see what piece has put the king in check
     * -> -> check to see if any piece can block off or eliminate the piece that has the king in check
     * -> -> -> check to see if by moving saving piece, if the king will end up in check from another piece
     * -> return moves list
     */

    /**
     * Create a function that if the king is in check then only return the list of valid moves that get the king out of check
     */

    /**
     * create a function that iterates through the valid moves list and checks to see if any of move options would put the king in check
     * -> if the move does, then remove it from the valid moves list
     */

    // iterate through the list of attacks and create a board that shows the move at the new location
    // create a new board that has the piece moved
    // check to see if the king isInCheck() on the new board
    // if the king is in check, remove that move from the Collection of attacks
    // UPDATE: instead, return a list of valid attacks instead of removing
    public Collection<ChessMove> protectTheKing(Collection<ChessMove> attacks, TeamColor turn){
        Collection<ChessMove> validAttacks = new ArrayList<>();
        for (var attack : attacks){
            ChessBoard newBoard = board.copy();
            movePiece(newBoard, attack);
            if (!isInCheck(turn, newBoard)) {
                validAttacks.add(attack);
            }
        }
        return validAttacks;
    }

    /**
     * create a function that collects the possible moves of all opposing pieces after a piece moves
     */

    public boolean isInCheck(TeamColor teamColor, ChessBoard nextBoard) {
        Collection<ChessPosition> attacks = new ArrayList<>();
        ChessPosition kingsPosition = null;
        //get kings position
        //get all possible moves (move.getEndPosition()) from all opposing team pieces
        for (int i = 1 ; i <= 8; i++){
            for (int j = 1 ; j <=8; j++){
                ChessPosition newPosition = new ChessPosition(i,j);
                // check if the spot is not null
                ChessPiece mysteryPiece = nextBoard.getPiece(newPosition);
                if (mysteryPiece != null){
                    //check if the piece is the king of teamcolor
                    if (mysteryPiece.getTeamColor() == teamColor && mysteryPiece.getPieceType() == ChessPiece.PieceType.KING) {
                        //if true -> save to kings position
                        kingsPosition = newPosition;
                    }
                    else {
                        if (mysteryPiece.getTeamColor() != teamColor) {
                            // if false -> check to see if the piece is not teamcolor
                            endMoves(attacks, newPosition);

                            //if true -> save all the enemy pieces possible endmoves to attacks
                        }
                        //if false -> continue on

                    }
                }
            }
        }
        //if the king is found in any of the end moves, then the king is in check and return turn
        if (attacks.contains(kingsPosition)){
            return true;
        }
        else {
            //else return false
            return false;
        }
    }




    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return team == chessGame.team && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "team=" + team +
                ", board=" + board +
                '}';
    }
}
