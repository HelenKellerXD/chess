import chess.*;
import server.Server;

public class Main {
    public static void main(String[] args) {
        try {
            Server newServer = new Server();
            newServer.run(8080);
            var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            System.out.println("♕ 240 Chess Server: " + piece);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex ){
            System.err.println("Specifiy the port number as a command line parameter");
        }
    }
}