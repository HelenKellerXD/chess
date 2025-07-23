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
    void createGamePass(){}
    void createGameFail(){}


    @Test
    @Order(2)
    void getGamePass(){}
    void getGameFail(){}

    @Test
    @Order(3)
    void listGamesPass(){}
    void listGamesFail(){}

    @Test
    @Order(4)
    void addCallersPass(){}
    void addCallersFail(){}

    @Test
    @Order(5)
    void clearGamePass(){
        Assertions.assertDoesNotThrow(() ->
        {
            gameDAO.clear();
        });
        int dBSize = -1;
        Assertions.assertDoesNotThrow(() -> {
                    dBSize = gameDAO.listGames().size();
                });
        Assertions.assertEquals(0, dBSize);
    }
}
