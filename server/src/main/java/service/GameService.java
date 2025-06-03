package service;

import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.MemoryGameDAO;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class GameService {
    GameDAO gameDB = new MemoryGameDAO();

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws DataAccessException {
        int gameID = gameDB.createGame(createGameRequest.gameName());
        return new CreateGameResult(gameID);
    }
    public ListGamesResult listGames(ListGamesRequest gamesListRequest) throws DataAccessException {
        Collection<GameData> listGamesResult = gameDB.listGames();
        return new ListGamesResult(listGamesResult);
    }

    public void addCaller(JoinGameRequest joinGameRequest) throws DataAccessException {
        String playerColor = joinGameRequest.playerColor();
        GameData gameData = gameDB.getGame(joinGameRequest.gameID());
        if(playerColor.equalsIgnoreCase("WHITE")){
            if (gameData.whiteUsername() == null){
                gameDB.addCaller(joinGameRequest.gameID(), playerColor, joinGameRequest.username());
            }
            else {
                throw new DataAccessException("Error: already taken");
            }
        }
        else{
            if (gameData.blackUsername() ==  null){
                gameDB.addCaller(joinGameRequest.gameID(), playerColor, joinGameRequest.username());
            }
            else {
                throw new DataAccessException("Error: already taken");
            }
        }

    }

    public void joinGame(JoinGameRequest joinGameRequest) throws DataAccessException {
        String teamColor = joinGameRequest.playerColor();
        if (teamColor == null){
            throw new DataAccessException("Error: bad request");
        }
        if (teamColor.equalsIgnoreCase("WHITE") || teamColor.equalsIgnoreCase("BLACK")){

            if(gameDB.getGame(joinGameRequest.gameID()) != null){
                addCaller(joinGameRequest);
            }
            else{
                throw new DataAccessException("Error: bad request");
            }
        }
        else{
            throw new DataAccessException("Error: bad request");
        }
    }


    public void clear(){
        gameDB.clear();
    }
}
