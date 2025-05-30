package dataaccess;

import chess.ChessGame;

/**
 * run CRUD on this
 *  - create
 *  - read (get/find)
 *  - update
 *  - delete
 */
public interface GameDAO {
    /**
     * createGame: Create a new game.
     */
    void createGame(String gameName);


    /**
     * getGame: Retrieve a specified game with the given game ID.
     */

    void getGame(int gameID);

    /**
     * glistGames: Retrieve all games.
     */

    void listGames();

    /**
     * updateGame:
     * - Updates a chess game. It should replace the chess game string corresponding to a given gameID.
     * - This is used when players join a game or when a move is made.
     */
    void addCaller(String playerColor);

    void updateGame(ChessGame chessGame);

    /**
     * clearGames: deletes all games from the database
     */
    void clear();


}
