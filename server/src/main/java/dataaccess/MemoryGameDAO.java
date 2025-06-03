package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class MemoryGameDAO implements GameDAO {
    private HashMap<Integer, GameData> gameDB = new HashMap<>();


    @Override
    public int createGame(String gameName) {
        int gameID = Math.abs(UUID.randomUUID().hashCode() % 40);

        while(gameDB.containsKey(gameID)){
            gameID += 1;
        }

        ChessGame chessGame = new ChessGame();
        GameData newGame = new GameData(gameID, null,null, gameName, chessGame);
        gameDB.put(gameID, newGame);
        return gameID;
    }

    @Override
    public GameData getGame(int gameID) {
        if(gameDB.containsKey(gameID)) {
            return gameDB.get(gameID);
        }
        else{
            return null;
        }
    }

    @Override
    public Collection<GameData> listGames() {
        return gameDB.values();
    }

    @Override
    public void addCaller(int gameID, String playerColor, String username) {
        if(playerColor.equalsIgnoreCase("WHITE")){
            gameDB.replace(gameID, getGame(gameID).addWhitePlayer(username));
        }
        else{
            gameDB.replace(gameID,getGame(gameID).addBlackPlayer(username));

        }

    }

    @Override
    public void updateGame(ChessGame chessGame) {

    }

    @Override
    public void clear() {
        gameDB.clear();
    }
}
