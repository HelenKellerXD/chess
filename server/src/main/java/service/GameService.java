package service;

import dataaccess.GameDAO;
import dataaccess.MemoryGameDAO;
import model.GameData;

import java.util.HashMap;

public class GameService {
    GameDAO gameDB = new MemoryGameDAO();

    public CreateGameResult createGame(CreateGameRequest createGameRequest) {
        return null;
    }
    public ListGamesResult listGames(ListGamesRequest gamesListRequest) {
        return null;
    }
    public void joinGame(JoinGameRequest gameJoinRequest) {}
    public void clear(){
        gameDB.clear();
    }
}
