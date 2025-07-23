package dataaccess;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import passoff.model.*;
import server.Server;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class GameSQLDBTest {
    GameDAO gameDAO;

    @BeforeEach
    void initiate(){
        Assertions.assertDoesNotThrow(() ->
        {
            gameDAO = new MySQLGameDAO();
        });
    }

    @AfterEach
    void wipe(){
        Assertions.assertDoesNotThrow(() ->
        {
            gameDAO.clear();
        });
    }

    @Test
    @Order(1)
    void createGamePass(){
        Assertions.assertDoesNotThrow(() ->
        {
            gameDAO.createGame("johns game");
        });
    }
    @Test
    @Order(2)
    void createGameFail(){
        Assertions.assertDoesNotThrow(() ->
        {
            gameDAO.createGame("johns game");
        });
        Assertions.assertThrows( DataAccessException.class, () -> {
                    gameDAO.createGame("johns game");
                }
        );
    }


    @Test
    @Order(3)
    void getGamePass(){
        Assertions.assertDoesNotThrow(() ->
        {
            int gameID = gameDAO.createGame("johns game");
            gameDAO.getGame(gameID);
        });

    }

    @Test
    @Order(4)
    void getGameFail(){}

    @Test
    @Order(5)
    void listGamesPass(){}

    @Test
    @Order(6)
    void listGamesFail(){}

    @Test
    @Order(7)
    void addCallersPass(){}

    @Test
    @Order(8)
    void addCallersFail(){}

    @Test
    @Order(9)
    void clearGamePass(){
        Assertions.assertDoesNotThrow(() ->
        {
            gameDAO.clear();
        });
    }
}
