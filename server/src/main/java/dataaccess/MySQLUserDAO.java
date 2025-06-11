package dataaccess;

import model.UserData;
import org.intellij.lang.annotations.Language;

import java.sql.SQLException;

public class MySQLUserDAO implements UserDAO{

    private DatabaseManager databaseManager;

    @Language("SQL")
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS users (
              `username` varchar(24) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(50) NOT NULL,
              PRIMARY KEY (`username`),
              INDEX(email)
            )
            """
    };

    private void configureTable() throws DataAccessException{
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

    public MySQLUserDAO()
    {
        try {
            configureTable();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void createUser(String username, String password, String email) {

    }

    @Override
    public UserData getUser(String userName) {
        return null;
    }

    @Override
    public void clear() {
        var statement = "DROP TABLE IF EXISTS user";

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
