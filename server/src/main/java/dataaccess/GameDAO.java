package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

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
    int createGame(String gameName);


    /**
     * getGame: Retrieve a specified game with the given game ID.
     */

    GameData getGame(int gameID);

    /**
     * glistGames: Retrieve all games.
     */

    Collection<GameData> listGames();

    /**
     * updateGame:
     * - Updates a chess game. It should replace the chess game string corresponding to a given gameID.
     * - This is used when players join a game or when a move is made.
     */
    void addCaller(int gameID, String playerColor, String userName);


    /**
     * clearGames: deletes all games from the database
     */
    void clear();


}
