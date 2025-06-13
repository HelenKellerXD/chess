package dataaccess;

import model.AuthData;
import model.UserData;
import org.intellij.lang.annotations.Language;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.UUID;

public class MySQLUserDAO implements UserDAO{

    private DatabaseManager databaseManager;

    @Language("SQL")
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS user (
              `username` varchar(24) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(50) NOT NULL,
              PRIMARY KEY (`username`),
              INDEX(email)
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

    public MySQLUserDAO() throws DataAccessException
    {
        try {
            configureTable();
            System.out.println("Running configureTable() for user");
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    @Override
    public void createUser(String username, String password, String email) throws DataAccessException{
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        var statement = "INSERT INTO user (username, password, email) VALUES (?, ?,?)";

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,hashedPassword);
                preparedStatement.setString(3,email);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

    }

    @Override
    public UserData getUser(String userName) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, userName);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String userNm = rs.getString("username");
                        String password = rs.getString("password");
                        String email = rs.getString("email");

                        return new UserData(userNm,password,email);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "DELETE FROM user";

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

    }
}
