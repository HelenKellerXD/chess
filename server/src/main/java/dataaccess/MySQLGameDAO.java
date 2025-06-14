package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;
//import org.intellij.lang.annotations.Language;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MySQLGameDAO implements GameDAO{

    private DatabaseManager databaseManager;

    //@Language("SQL")
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS game (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(26) DEFAULT NULL,
              `blackUsername` varchar(26) DEFAULT NULL,
              `gameName` varchar(26) DEFAULT NULL,
              `game` TEXT NOT NULL,
              PRIMARY KEY (`gameID`),
              INDEX(gameName),
              INDEX(blackUsername),
              INDEX(whiteUsername)
            )
            """
    };

    private void configureTable() throws DataAccessException{
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()){
            for (var statement : createStatements){
                try (var preparedStatement = conn.prepareStatement(statement)){
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException (String.format("Unable to configure database: %s", e.getMessage()));
        }
    }

    public MySQLGameDAO() throws DataAccessException {
        try {
            configureTable();
            //System.out.println("Running configureTable() for game");
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    @Override
    public int createGame(String gameName) throws DataAccessException{
        var statement = "INSERT INTO game (whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?)";

        ChessGame chessGame = new ChessGame();
        Gson gson = new Gson();
        String chessBoard = gson.toJson(chessGame);

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1,null);
                preparedStatement.setString(2,null);
                preparedStatement.setString(3,gameName);
                preparedStatement.setString(4,chessBoard);

                preparedStatement.executeUpdate();

                try(var rs = preparedStatement.getGeneratedKeys()){
                    if (rs.next()){
                        return rs.getInt(1);
                    }
                    else{
                        throw new DataAccessException ("gameID is not in Database");
                    }
                }

            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        Gson gson = new Gson();

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int gameId = rs.getInt("gameID");
                        String whiteUsername = rs.getString("whiteUsername");
                        String blackUsername = rs.getString("blackUsername");
                        String gameName = rs.getString("gameName");
                        ChessGame chessGame = gson.fromJson(rs.getString("game"), ChessGame.class);

                        return new GameData(gameId,whiteUsername,blackUsername,gameName,chessGame);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException{
        Gson gson = new Gson();
        Collection<GameData> games = new ArrayList<>();

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game";
            try (var ps = conn.prepareStatement(statement)) {

                 var rs = ps.executeQuery();
                 while (rs.next()) {
                     int gameId = rs.getInt("gameID");
                     String whiteUsername = rs.getString("whiteUsername");
                     String blackUsername = rs.getString("blackUsername");
                     String gameName = rs.getString("gameName");
                     ChessGame chessGame = gson.fromJson(rs.getString("game"), ChessGame.class);

                     games.add(new GameData(gameId,whiteUsername,blackUsername,gameName,chessGame));
                 }

            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return games;
    }

    @Override
    public void addCaller(int gameID, String playerColor, String userName) throws DataAccessException {
        String statement;
        if(playerColor.equalsIgnoreCase("WHITE")){
            statement = "UPDATE game SET whiteUsername=? WHERE gameID=?";
        }
        else{
            statement = "UPDATE game SET blackUsername=? WHERE gameID=?";
        }
        try (var conn = DatabaseManager.getConnection()) {

            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1,userName);
                ps.setInt(2, gameID);
                ps.executeUpdate();

            }
        }
        catch (SQLException e) {
        throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "DELETE FROM game";

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

    }
}
