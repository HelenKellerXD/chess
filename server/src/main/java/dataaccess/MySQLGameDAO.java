package dataaccess;

import model.GameData;
import org.intellij.lang.annotations.Language;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class MySQLGameDAO implements GameDAO{

    private DatabaseManager databaseManager;

    @Language("SQL")
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

    public MySQLGameDAO()
    {
        try {
            configureTable();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int createGame(String gameName) {
        return 0;
    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        return List.of();
    }

    @Override
    public void addCaller(int gameID, String playerColor, String userName) {

    }

    @Override
    public void clear() {
        var statement = "DELETE FROM game";

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
