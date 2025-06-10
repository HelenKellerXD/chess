package service;

import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MySQLGameDAO;
import model.GameData;

import java.util.Collection;

public class GameService {
    GameDAO gameDAO = new MySQLGameDAO();

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws DataAccessException {
        if (createGameRequest.gameName() == null){
            throw new DataAccessException("Error: Bad Request");
        }
        int gameID = gameDAO.createGame(createGameRequest.gameName());
        return new CreateGameResult(gameID);
    }
    public ListGamesResult listGames(Object object) throws DataAccessException {
        throw new DataAccessException("Error: object passed in to listGames as a parameter");
    }

    public ListGamesResult listGames() throws DataAccessException {
        Collection<GameData> listGamesResult = gameDAO.listGames();
        return new ListGamesResult(listGamesResult);
    }

    public void addCaller(JoinGameRequest joinGameRequest) throws DataAccessException {
        String playerColor = joinGameRequest.playerColor();
        GameData gameData = gameDAO.getGame(joinGameRequest.gameID());
        if(playerColor.equalsIgnoreCase("WHITE")){
            if (gameData.whiteUsername() == null){
                gameDAO.addCaller(joinGameRequest.gameID(), playerColor, joinGameRequest.username());
            }
            else {
                throw new DataAccessException("Error: already taken");
            }
        }
        else{
            if (gameData.blackUsername() ==  null){
                gameDAO.addCaller(joinGameRequest.gameID(), playerColor, joinGameRequest.username());
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

            if(gameDAO.getGame(joinGameRequest.gameID()) != null){
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
        gameDAO.clear();
    }
}
