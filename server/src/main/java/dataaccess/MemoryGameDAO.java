package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;

public class MemoryGameDAO implements GameDAO {
    ArrayList<GameData> gameDB = new ArrayList<>();


    @Override
    public void createGame(String gameName) {

    }

    @Override
    public void getGame(int gameID) {

    }

    @Override
    public void listGames() {

    }

    @Override
    public void addCaller(String playerColor) {

    }

    @Override
    public void updateGame(ChessGame chessGame) {

    }

    @Override
    public void clear() {
        gameDB.clear();
    }
}
